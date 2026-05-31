/**
 * TheKnife – Modulo Server
 * Gestore delle operazioni sul database PostgreSQL tramite JDBC.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */

package it.uninsubria.theknife.server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import it.uninsubria.theknife.common.model.Recensione;
import it.uninsubria.theknife.common.model.Ristorante;
import it.uninsubria.theknife.common.model.Utente;

/**
 * Fornisce tutti i metodi di accesso al database PostgreSQL per la
 * piattaforma TheKnife.
 * <p>
 * Ogni istanza di {@code DatabaseManager} mantiene una singola connessione
 * JDBC dedicata. Il pattern adottato è <b>una connessione per thread</b>:
 * ogni {@link ClientHandler} crea il proprio {@code DatabaseManager} alla
 * connessione del client e lo chiude alla disconnessione. Questo elimina
 * race condition senza necessità di sincronizzazione esplicita.
 * </p>
 *
 * <p>Tutte le query usano {@link PreparedStatement} per prevenire
 * SQL injection.</p>
 *
 * <p>Uso tipico:</p>
 * <pre>{@code
 * DatabaseManager db = new DatabaseManager("localhost", 5432, "theknife", "user", "pass");
 * List<Ristorante> lista = db.cercaRistoranti("Roma", null, 0, 100, null, null, 0);
 * db.close();
 * }</pre>
 */
public class DatabaseManager implements AutoCloseable {

    /** Connessione JDBC dedicata a questa istanza (e al thread che la usa). */
    private final Connection conn;

    // -------------------------------------------------------------------------
    // Costruttore e chiusura
    // -------------------------------------------------------------------------

    /**
     * Apre la connessione JDBC verso il database PostgreSQL.
     *
     * @param host     indirizzo del server PostgreSQL (es. {@code "localhost"})
     * @param porta    porta del server (default PostgreSQL: {@code 5432})
     * @param database nome del database (es. {@code "theknife"})
     * @param utente   username PostgreSQL
     * @param password password PostgreSQL
     * @throws SQLException se la connessione non riesce
     */
    public DatabaseManager(String host, int porta, String database,
                           String utente, String password) throws SQLException {
        String url = "jdbc:postgresql://" + host + ":" + porta + "/" + database;
        this.conn = DriverManager.getConnection(url, utente, password);
        // Disabilita l'auto-commit per gestire le transazioni manualmente
        // dove necessario (es. operazioni multi-query atomiche)
        this.conn.setAutoCommit(true);
    }

    /**
     * Chiude la connessione JDBC.
     * Implementa {@link AutoCloseable} per l'uso in try-with-resources.
     */
    @Override
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("[DatabaseManager] Errore nella chiusura della connessione: "
                               + e.getMessage());
        }
    }

    // =========================================================================
    // AUTENTICAZIONE
    // =========================================================================

    /**
     * Verifica le credenziali di un utente.
     *
     * @param username    username da verificare
     * @param passwordHash hash SHA-256 della password inserita
     * @return l'oggetto {@link Utente} senza password se le credenziali sono
     *         corrette, {@code null} se username o password non corrispondono
     * @throws SQLException in caso di errore JDBC
     */
    public Utente login(String username, String passwordHash) throws SQLException {
        String sql = """
                SELECT username, nome, cognome, data_nascita, domicilio, ruolo
                FROM utenti
                WHERE username = ? AND password_hash = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUtente(rs);
                }
            }
        }
        return null; // credenziali errate
    }

    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param u oggetto {@link Utente} con tutti i campi valorizzati,
     *          inclusa la password già hashata
     * @throws SQLException             in caso di errore JDBC
     * @throws IllegalArgumentException se lo username è già presente nel DB
     */
    public void registrazione(Utente u) throws SQLException {
        String sql = """
                INSERT INTO utenti (username, nome, cognome, password_hash,
                                    data_nascita, domicilio, ruolo)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getCognome());
            ps.setString(4, u.getPasswordHash());
            // data_nascita è opzionale
            if (u.getDataNascita() != null && !u.getDataNascita().isBlank()) {
                ps.setDate(5, Date.valueOf(u.getDataNascita()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, u.getDomicilio());
            ps.setString(7, u.getRuolo());
            ps.executeUpdate();
        }
        // Se lo username esiste già, PostgreSQL lancia una SQLException
        // con SQLState "23505" (unique_violation): il chiamante la gestisce.
    }

    // =========================================================================
    // RISTORANTI – operazioni di lettura (disponibili anche per guest)
    // =========================================================================

    /**
     * Ricerca ristoranti applicando i filtri forniti.
     * <p>
     * Tutti i parametri opzionali possono essere {@code null} o {@code 0}
     * (per i numerici) per indicare "nessun filtro su questo campo".
     * </p>
     *
     * @param citta        città di ricerca (obbligatoria, case-insensitive)
     * @param tipoCucina   tipo di cucina (opzionale)
     * @param prezzoMin    prezzo minimo in euro, {@code 0} = nessun limite inferiore
     * @param prezzoMax    prezzo massimo in euro, {@code 0} = nessun limite superiore
     * @param delivery     {@code true} filtra solo con delivery, {@code null} = indifferente
     * @param prenotazione {@code true} filtra solo con prenotazione, {@code null} = indifferente
     * @param stelleMin    media stelle minima, {@code 0} = nessun filtro
     * @return lista di {@link Ristorante} corrispondenti ai criteri
     * @throws SQLException in caso di errore JDBC
     */
    public List<Ristorante> cercaRistoranti(String citta, String tipoCucina,
                                             double prezzoMin, double prezzoMax,
                                             Boolean delivery, Boolean prenotazione,
                                             double stelleMin) throws SQLException {

        // ══ DEBUG ════════════════════════════════════════════════════════════════
       // System.out.println("\n[DB] ====== cercaRistoranti ======");
        //System.out.println("[DB] citta        = '" + citta + "'");
        //System.out.println("[DB] tipoCucina   = '" + tipoCucina + "'");
        //System.out.println("[DB] prezzoMin    = " + prezzoMin);
        //System.out.println("[DB] prezzoMax    = " + prezzoMax);
        //System.out.println("[DB] delivery     = " + delivery);
        //System.out.println("[DB] prenotazione = " + prenotazione);
        //System.out.println("[DB] stelleMin    = " + stelleMin);
        // ══ END DEBUG ═══════════════════════════════════════════════════════════

        StringBuilder sql = new StringBuilder("""
                SELECT r.*,
                       COALESCE(AVG(rec.stelle), 0)   AS media_stelle,
                       COUNT(rec.stelle)               AS num_recensioni
                FROM ristoranti r
                LEFT JOIN recensioni rec ON rec.nome_ristorante = r.nome
                WHERE LOWER(r.citta) = LOWER(?)
                """);

        if (tipoCucina != null && !tipoCucina.isBlank()) {
            sql.append(" AND LOWER(r.tipo_cucina) = LOWER(?)");
        }
        if (prezzoMin > 0) sql.append(" AND r.fascia_prezzo >= ?");
        if (prezzoMax > 0) sql.append(" AND r.fascia_prezzo <= ?");
        if (delivery != null)     sql.append(" AND r.delivery = ?");
        if (prenotazione != null) sql.append(" AND r.prenotazione = ?");
        sql.append(" GROUP BY r.nome");
        if (stelleMin > 0) sql.append(" HAVING COALESCE(AVG(rec.stelle), 0) >= ?");
        sql.append(" ORDER BY r.nome");

        // ══ DEBUG ═══════════════════════════════════════════════════════════════
        //System.out.println("[DB] SQL = " + sql);
        // ══ END DEBUG ═══════════════════════════════════════════════════════════

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int i = 1;
            ps.setString(i++, citta);
            if (tipoCucina != null && !tipoCucina.isBlank()) ps.setString(i++, tipoCucina);
            if (prezzoMin > 0)        ps.setDouble(i++, prezzoMin);
            if (prezzoMax > 0)        ps.setDouble(i++, prezzoMax);
            if (delivery != null)     ps.setBoolean(i++, delivery);
            if (prenotazione != null) ps.setBoolean(i++, prenotazione);
            if (stelleMin > 0)        ps.setDouble(i, stelleMin);

            List<Ristorante> risultati = eseguiQueryRistoranti(ps);

            // ══ DEBUG ═══════════════════════════════════════════════════════════
            //System.out.println("[DB] risultati trovati = " + risultati.size());
            //for (Ristorante r : risultati) {
            //    System.out.println("[DB]   - " + r.getNome() + " (" + r.getCitta() + ")");
            //}
            //System.out.println("[DB] ================================");
            // ══ END DEBUG ═══════════════════════════════════════════════════════

            return risultati;
        }
    }

    /**
     * Recupera i dettagli completi di un singolo ristorante.
     *
     * @param nomeRistorante nome del ristorante
     * @return oggetto {@link Ristorante} con media stelle e numero recensioni,
     *         oppure {@code null} se non trovato
     * @throws SQLException in caso di errore JDBC
     */
    public Ristorante visualizzaRistorante(String nomeRistorante) throws SQLException {
        String sql = """
                SELECT r.*,
                       COALESCE(AVG(rec.stelle), 0) AS media_stelle,
                       COUNT(rec.stelle)             AS num_recensioni
                FROM ristoranti r
                LEFT JOIN recensioni rec ON rec.nome_ristorante = r.nome
                WHERE r.nome = ?
                GROUP BY r.nome
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeRistorante);
            List<Ristorante> lista = eseguiQueryRistoranti(ps);
            return lista.isEmpty() ? null : lista.get(0);
        }
    }

    // =========================================================================
    // RECENSIONI – lettura (disponibile anche per guest)
    // =========================================================================

    /**
     * Recupera tutte le recensioni di un ristorante, ordinate per stelle
     * in ordine decrescente.
     *
     * @param nomeRistorante nome del ristorante
     * @return lista di {@link Recensione}, vuota se nessuna recensione presente
     * @throws SQLException in caso di errore JDBC
     */
    public List<Recensione> visualizzaRecensioni(String nomeRistorante) throws SQLException {
        String sql = """
                SELECT nome_ristorante, username_cliente, stelle, testo, risposta
                FROM recensioni
                WHERE nome_ristorante = ?
                ORDER BY stelle DESC
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeRistorante);
            return eseguiQueryRecensioni(ps);
        }
    }

    // =========================================================================
    // PREFERITI – operazioni cliente
    // =========================================================================

    /**
     * Aggiunge un ristorante alla lista dei preferiti di un cliente.
     *
     * @param username       username del cliente
     * @param nomeRistorante nome del ristorante da aggiungere
     * @throws SQLException in caso di errore JDBC o se il preferito esiste già
     */
    public void aggiungiPreferito(String username, String nomeRistorante) throws SQLException {
        String sql = "INSERT INTO preferiti (username, nome_ristorante) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, nomeRistorante);
            ps.executeUpdate();
        }
    }

    /**
     * Rimuove un ristorante dalla lista dei preferiti di un cliente.
     *
     * @param username       username del cliente
     * @param nomeRistorante nome del ristorante da rimuovere
     * @throws SQLException in caso di errore JDBC
     */
    public void rimuoviPreferito(String username, String nomeRistorante) throws SQLException {
        String sql = "DELETE FROM preferiti WHERE username = ? AND nome_ristorante = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, nomeRistorante);
            ps.executeUpdate();
        }
    }

    /**
     * Recupera la lista dei ristoranti preferiti di un cliente, con i
     * relativi dettagli (media stelle inclusa).
     *
     * @param username username del cliente
     * @return lista di {@link Ristorante} nella lista dei preferiti
     * @throws SQLException in caso di errore JDBC
     */
    public List<Ristorante> visualizzaPreferiti(String username) throws SQLException {
        String sql = """
                SELECT r.*,
                       COALESCE(AVG(rec.stelle), 0) AS media_stelle,
                       COUNT(rec.stelle)             AS num_recensioni
                FROM ristoranti r
                JOIN preferiti p ON p.nome_ristorante = r.nome
                LEFT JOIN recensioni rec ON rec.nome_ristorante = r.nome
                WHERE p.username = ?
                GROUP BY r.nome
                ORDER BY r.nome
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return eseguiQueryRistoranti(ps);
        }
    }

    // =========================================================================
    // RECENSIONI – operazioni cliente
    // =========================================================================

    /**
     * Aggiunge una nuova recensione.
     * Un cliente può inserire al massimo una recensione per ristorante
     * (vincolo garantito dalla PK composta nel database).
     *
     * @param username       username del cliente
     * @param nomeRistorante nome del ristorante
     * @param stelle         valore da 1 a 5
     * @param testo          testo della recensione
     * @throws SQLException in caso di errore JDBC o di recensione duplicata
     */
    public void aggiungiRecensione(String username, String nomeRistorante,
                                   int stelle, String testo) throws SQLException {
        String sql = """
                INSERT INTO recensioni (nome_ristorante, username_cliente, stelle, testo)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeRistorante);
            ps.setString(2, username);
            ps.setInt(3, stelle);
            ps.setString(4, testo);
            ps.executeUpdate();
        }
    }

    /**
     * Modifica una recensione esistente.
     * È possibile modificare solo le proprie recensioni.
     *
     * @param username       username del cliente autore
     * @param nomeRistorante nome del ristorante
     * @param nuoveStelle    nuovo valore stelle (1–5)
     * @param nuovoTesto     nuovo testo della recensione
     * @return {@code true} se la recensione è stata trovata e aggiornata
     * @throws SQLException in caso di errore JDBC
     */
    public boolean modificaRecensione(String username, String nomeRistorante,
                                      int nuoveStelle, String nuovoTesto) throws SQLException {
        String sql = """
                UPDATE recensioni
                SET stelle = ?, testo = ?
                WHERE nome_ristorante = ? AND username_cliente = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuoveStelle);
            ps.setString(2, nuovoTesto);
            ps.setString(3, nomeRistorante);
            ps.setString(4, username);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una recensione.
     * È possibile eliminare solo le proprie recensioni.
     *
     * @param username       username del cliente autore
     * @param nomeRistorante nome del ristorante
     * @return {@code true} se la recensione è stata trovata ed eliminata
     * @throws SQLException in caso di errore JDBC
     */
    public boolean eliminaRecensione(String username, String nomeRistorante) throws SQLException {
        String sql = """
                DELETE FROM recensioni
                WHERE nome_ristorante = ? AND username_cliente = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeRistorante);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Recupera tutte le recensioni scritte da un cliente.
     *
     * @param username username del cliente
     * @return lista di {@link Recensione} dell'utente, vuota se nessuna presente
     * @throws SQLException in caso di errore JDBC
     */
    public List<Recensione> visualizzaMieRecensioni(String username) throws SQLException {
        String sql = """
                SELECT nome_ristorante, username_cliente, stelle, testo, risposta
                FROM recensioni
                WHERE username_cliente = ?
                ORDER BY nome_ristorante
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return eseguiQueryRecensioni(ps);
        }
    }

    // =========================================================================
    // RISTORANTI – operazioni ristoratore
    // =========================================================================

    /**
     * Aggiunge un nuovo ristorante di proprietà del ristoratore loggato.
     *
     * @param r           oggetto {@link Ristorante} da inserire
     * @param proprietario username del ristoratore (dalla sessione, non dal client)
     * @throws SQLException             in caso di errore JDBC
     * @throws IllegalArgumentException se il nome del ristorante esiste già
     */
    public void aggiungiRistorante(Ristorante r, String proprietario) throws SQLException {
        String sql = """
                INSERT INTO ristoranti (nome, nazione, citta, indirizzo, latitudine,
                                        longitudine, fascia_prezzo, delivery,
                                        prenotazione, tipo_cucina, proprietario)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getNazione());
            ps.setString(3, r.getCitta());
            ps.setString(4, r.getIndirizzo());
            ps.setDouble(5, r.getLatitudine());
            ps.setDouble(6, r.getLongitudine());
            ps.setDouble(7, r.getFasciaPrezzo());
            ps.setBoolean(8, r.isDelivery());
            ps.setBoolean(9, r.isPrenotazione());
            ps.setString(10, r.getTipoCucina());
            ps.setString(11, proprietario); // sempre dal server, mai dal client
            ps.executeUpdate();
        }
    }

    /**
     * Recupera il riepilogo dei ristoranti di proprietà di un ristoratore,
     * con media stelle e numero recensioni per ciascuno.
     *
     * @param proprietario username del ristoratore
     * @return lista di {@link Ristorante} con statistiche aggregate
     * @throws SQLException in caso di errore JDBC
     */
    public List<Ristorante> visualizzaRiepilogo(String proprietario) throws SQLException {
        String sql = """
                SELECT r.*,
                       COALESCE(AVG(rec.stelle), 0) AS media_stelle,
                       COUNT(rec.stelle)             AS num_recensioni
                FROM ristoranti r
                LEFT JOIN recensioni rec ON rec.nome_ristorante = r.nome
                WHERE r.proprietario = ?
                GROUP BY r.nome
                ORDER BY r.nome
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, proprietario);
            return eseguiQueryRistoranti(ps);
        }
    }

    /**
     * Recupera tutte le recensioni dei ristoranti di un ristoratore.
     *
     * @param proprietario username del ristoratore
     * @return lista di {@link Recensione} relative ai suoi ristoranti
     * @throws SQLException in caso di errore JDBC
     */
    public List<Recensione> visualizzaRecensioniRistoratore(String proprietario) throws SQLException {
        String sql = """
                SELECT rec.nome_ristorante, rec.username_cliente,
                       rec.stelle, rec.testo, rec.risposta
                FROM recensioni rec
                JOIN ristoranti r ON r.nome = rec.nome_ristorante
                WHERE r.proprietario = ?
                ORDER BY rec.nome_ristorante, rec.stelle DESC
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, proprietario);
            return eseguiQueryRecensioni(ps);
        }
    }

    /**
     * Aggiunge o aggiorna la risposta del ristoratore a una recensione.
     * <p>
     * Il vincolo di "al massimo una risposta per recensione" è garantito
     * dall'istruzione UPDATE: se la risposta esiste già viene sovrascritta,
     * ma solo se il ristorante appartiene al ristoratore che ha fatto
     * la richiesta (join con tabella ristoranti).
     * </p>
     *
     * @param proprietario    username del ristoratore (dalla sessione)
     * @param nomeRistorante  nome del ristorante
     * @param usernameCliente username del cliente autore della recensione
     * @param risposta        testo della risposta
     * @return {@code true} se la recensione è stata trovata e aggiornata
     * @throws SQLException in caso di errore JDBC
     */
    public boolean rispondiRecensione(String proprietario, String nomeRistorante,
                                       String usernameCliente, String risposta) throws SQLException {
        // La JOIN con ristoranti garantisce che il ristoratore possa rispondere
        // solo alle recensioni dei propri ristoranti.
        String sql = """
                UPDATE recensioni rec
                SET risposta = ?
                FROM ristoranti r
                WHERE rec.nome_ristorante = r.nome
                  AND r.proprietario     = ?
                  AND rec.nome_ristorante = ?
                  AND rec.username_cliente = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, risposta);
            ps.setString(2, proprietario);
            ps.setString(3, nomeRistorante);
            ps.setString(4, usernameCliente);
            return ps.executeUpdate() > 0;
        }
    }

    // =========================================================================
    // Metodi privati di supporto (mapping ResultSet → oggetti)
    // =========================================================================

    /**
     * Esegue un {@link PreparedStatement} già configurato e mappa i risultati
     * in una lista di {@link Ristorante}.
     *
     * @param ps statement da eseguire (già con parametri impostati)
     * @return lista di ristoranti risultanti
     * @throws SQLException in caso di errore JDBC
     */
    private List<Ristorante> eseguiQueryRistoranti(PreparedStatement ps) throws SQLException {
        List<Ristorante> lista = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRistorante(rs));
            }
        }
        return lista;
    }

    /**
     * Esegue un {@link PreparedStatement} già configurato e mappa i risultati
     * in una lista di {@link Recensione}.
     *
     * @param ps statement da eseguire
     * @return lista di recensioni risultanti
     * @throws SQLException in caso di errore JDBC
     */
    private List<Recensione> eseguiQueryRecensioni(PreparedStatement ps) throws SQLException {
        List<Recensione> lista = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRecensione(rs));
            }
        }
        return lista;
    }

    /**
     * Costruisce un oggetto {@link Ristorante} a partire dalla riga corrente
     * del {@link ResultSet}.
     * Presuppone che il ResultSet contenga le colonne di {@code ristoranti}
     * più {@code media_stelle} e {@code num_recensioni}.
     */
    private Ristorante mapRistorante(ResultSet rs) throws SQLException {
        Ristorante r = new Ristorante(
            rs.getString("nome"),
            rs.getString("nazione"),
            rs.getString("citta"),
            rs.getString("indirizzo"),
            rs.getDouble("latitudine"),
            rs.getDouble("longitudine"),
            rs.getDouble("fascia_prezzo"),
            rs.getBoolean("delivery"),
            rs.getBoolean("prenotazione"),
            rs.getString("tipo_cucina"),
            rs.getString("proprietario")
        );
        r.setMediaStelle(rs.getDouble("media_stelle"));
        r.setNumeroRecensioni(rs.getInt("num_recensioni"));
        return r;
    }

    /**
     * Costruisce un oggetto {@link Recensione} a partire dalla riga corrente
     * del {@link ResultSet}.
     */
    private Recensione mapRecensione(ResultSet rs) throws SQLException {
        return new Recensione(
            rs.getString("nome_ristorante"),
            rs.getString("username_cliente"),
            rs.getInt("stelle"),
            rs.getString("testo"),
            rs.getString("risposta") // può essere null
        );
    }

    /**
     * Costruisce un oggetto {@link Utente} (senza password) a partire dalla
     * riga corrente del {@link ResultSet}.
     * Usato solo internamente dopo una query che esclude già il campo
     * {@code password_hash} dalla SELECT.
     */
    private Utente mapUtente(ResultSet rs) throws SQLException {
        return new Utente(
            rs.getString("nome"),
            rs.getString("cognome"),
            rs.getString("username"),
            null, // la password non viene mai inviata al client
            rs.getString("data_nascita"),
            rs.getString("domicilio"),
            rs.getString("ruolo")
        );
    }
}