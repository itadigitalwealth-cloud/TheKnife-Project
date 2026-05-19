/**
 * TheKnife – Modulo Server
 * Thread dedicato alla gestione di un singolo client connesso.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */

package it.uninsubria.theknife.server;

import it.uninsubria.theknife.common.CommandType;
import it.uninsubria.theknife.common.Request;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Recensione;
import it.uninsubria.theknife.common.model.Ristorante;
import it.uninsubria.theknife.common.model.Utente;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

/**
 * Gestisce la comunicazione con un singolo client su un thread dedicato.
 * <p>
 * Per ogni client che si connette al server, {@link ServerTK} crea una
 * nuova istanza di {@code ClientHandler} e la avvia come thread separato.
 * Questo permette a più client di interagire con la piattaforma in parallelo.
 * </p>
 *
 * <p>
 * Il ciclo di vita del thread è:
 * <ol>
 *   <li>Apertura degli stream {@link ObjectInputStream}/{@link ObjectOutputStream}</li>
 *   <li>Apertura della connessione JDBC tramite {@link DatabaseManager}</li>
 *   <li>Loop: ricezione {@link Request} → elaborazione → invio {@link Response}</li>
 *   <li>Chiusura di stream, socket e connessione DB alla disconnessione</li>
 * </ol>
 * </p>
 *
 * <p>
 * La sessione utente (chi è loggato) è mantenuta nel campo {@code utenteLoggato}:
 * è {@code null} per gli utenti guest, valorizzato dopo un {@code LOGIN} andato
 * a buon fine. Il server verifica i permessi prima di eseguire ogni operazione
 * riservata, indipendentemente da quanto dichiarato nella {@link Request}.
 * </p>
 */
public class ClientHandler implements Runnable {

    /** Socket della connessione con il client. */
    private final Socket socket;

    /** Parametri di connessione al database, condivisi tra tutti i thread. */
    private final String dbHost;
    private final int    dbPorta;
    private final String dbNome;
    private final String dbUtente;
    private final String dbPassword;

    /**
     * Utente attualmente autenticato su questa connessione.
     * {@code null} se il client non ha ancora effettuato il login.
     */
    private Utente utenteLoggato = null;

    /**
     * Crea un nuovo handler per il client connesso sul socket fornito.
     *
     * @param socket     socket della connessione accettata dal server
     * @param dbHost     indirizzo del server PostgreSQL
     * @param dbPorta    porta PostgreSQL
     * @param dbNome     nome del database
     * @param dbUtente   username PostgreSQL
     * @param dbPassword password PostgreSQL
     */
    public ClientHandler(Socket socket, String dbHost, int dbPorta,
                         String dbNome, String dbUtente, String dbPassword) {
        this.socket     = socket;
        this.dbHost     = dbHost;
        this.dbPorta    = dbPorta;
        this.dbNome     = dbNome;
        this.dbUtente   = dbUtente;
        this.dbPassword = dbPassword;
    }

    /**
     * Corpo principale del thread.
     * Apre gli stream e il {@link DatabaseManager}, poi entra nel loop
     * di ricezione comandi fino alla disconnessione del client.
     */
    @Override
    public void run() {
        String clientAddr = socket.getRemoteSocketAddress().toString();
        System.out.println("[Server] Client connesso: " + clientAddr);

        try (
            // try-with-resources: chiude automaticamente stream, socket e DB
            socket;
            ObjectOutputStream out = new ObjectOutputStream(
                                         new BufferedOutputStream(socket.getOutputStream()));
            ObjectInputStream  in  = new ObjectInputStream(
                                         new BufferedInputStream(socket.getInputStream()));
            DatabaseManager db = new DatabaseManager(
                                         dbHost, dbPorta, dbNome, dbUtente, dbPassword)
        ) {
            // Necessario flushare prima di aprire ObjectInputStream sull'altro capo
            out.flush();

            // Loop principale: legge richieste finché il client non chiude la connessione
            while (true) {
                Request req;
                try {
                    req = (Request) in.readObject();
                } catch (EOFException | ClassNotFoundException e) {
                    // Il client ha chiuso la connessione normalmente
                    break;
                }

                Response resp = elabora(req, db);
                out.writeObject(resp);
                out.flush();
                // Reset necessario per evitare che ObjectOutputStream usi la cache
                // e invii riferimenti a oggetti precedenti invece dei nuovi dati
                out.reset();
            }

        } catch (IOException e) {
            System.err.println("[Server] Errore I/O con client " + clientAddr
                               + ": " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[Server] Errore connessione DB per client " + clientAddr
                               + ": " + e.getMessage());
        } finally {
            System.out.println("[Server] Client disconnesso: " + clientAddr);
        }
    }

    // =========================================================================
    // Dispatcher: instrada la richiesta al metodo corretto
    // =========================================================================

    /**
     * Elabora una {@link Request} e restituisce la {@link Response} appropriata.
     * Prima di eseguire qualsiasi operazione riservata, verifica che
     * {@code utenteLoggato} abbia il ruolo necessario.
     *
     * @param req richiesta ricevuta dal client
     * @param db  gestore del database per questa connessione
     * @return risposta da inviare al client
     */
    private Response elabora(Request req, DatabaseManager db) {
        try {
            return switch (req.getComando()) {

                // --- Comandi pubblici (anche guest) ---
                case CERCA_RISTORANTI       -> cercaRistoranti(req, db);
                case VISUALIZZA_RISTORANTE  -> visualizzaRistorante(req, db);
                case VISUALIZZA_RECENSIONI  -> visualizzaRecensioni(req, db);
                case REGISTRAZIONE          -> registrazione(req, db);
                case LOGIN                  -> login(req, db);

                // --- Comandi cliente ---
                case CLIENTE_AGGIUNGI_PREFERITO      -> aggiungiPreferito(req, db);
                case CLIENTE_RIMUOVI_PREFERITO       -> rimuoviPreferito(req, db);
                case CLIENTE_VISUALIZZA_PREFERITI    -> visualizzaPreferiti(req, db);
                case CLIENTE_AGGIUNGI_RECENSIONE     -> aggiungiRecensione(req, db);
                case CLIENTE_MODIFICA_RECENSIONE     -> modificaRecensione(req, db);
                case CLIENTE_ELIMINA_RECENSIONE      -> eliminaRecensione(req, db);
                case CLIENTE_VISUALIZZA_MIE_RECENSIONI -> visualizzaMieRecensioni(req, db);

                // --- Comandi ristoratore ---
                case RISTORATORE_AGGIUNGI_RISTORANTE -> aggiungiRistorante(req, db);
                case RISTORATORE_VISUALIZZA_RIEPILOGO -> visualizzaRiepilogo(req, db);
                case RISTORATORE_VISUALIZZA_RECENSIONI -> visualizzaRecensioniRistoratore(db);
                case RISTORATORE_RISPONDI_RECENSIONE  -> rispondiRecensione(req, db);
            };
        } catch (Exception e) {
            // Eccezione non gestita: log lato server, errore generico al client
            System.err.println("[Server] Errore elaborando "
                               + req.getComando() + ": " + e.getMessage());
            return Response.errore("Errore interno del server. Riprovare.");
        }
    }

    // =========================================================================
    // Comandi pubblici
    // =========================================================================

    private Response cercaRistoranti(Request req, DatabaseManager db) throws SQLException {
        String  citta        = req.getParametroStringa("citta");
        String  tipoCucina   = req.getParametroStringa("tipoCucina");
        double  prezzoMin    = req.hasParametro("prezzoMin")    ? req.getParametroDouble("prezzoMin")    : 0;
        double  prezzoMax    = req.hasParametro("prezzoMax")    ? req.getParametroDouble("prezzoMax")    : 0;
        Boolean delivery     = req.hasParametro("delivery")     ? req.getParametroBoolean("delivery")    : null;
        Boolean prenotazione = req.hasParametro("prenotazione") ? req.getParametroBoolean("prenotazione"): null;
        double  stelleMin    = req.hasParametro("stelleMin")    ? req.getParametroDouble("stelleMin")    : 0;

        if (citta == null || citta.isBlank()) {
            return Response.errore("La città è obbligatoria per la ricerca.");
        }

        List<Ristorante> lista = db.cercaRistoranti(
                citta, tipoCucina, prezzoMin, prezzoMax, delivery, prenotazione, stelleMin);
        return Response.ok(lista);
    }

    private Response visualizzaRistorante(Request req, DatabaseManager db) throws SQLException {
        String nome = req.getParametroStringa("nomeRistorante");
        Ristorante r = db.visualizzaRistorante(nome);
        if (r == null) return Response.errore("Ristorante non trovato: " + nome);
        return Response.ok(r);
    }

    private Response visualizzaRecensioni(Request req, DatabaseManager db) throws SQLException {
        String nome = req.getParametroStringa("nomeRistorante");
        List<Recensione> lista = db.visualizzaRecensioni(nome);
        return Response.ok(lista);
    }

    private Response registrazione(Request req, DatabaseManager db) throws SQLException {
        Utente u = new Utente(
            req.getParametroStringa("nome"),
            req.getParametroStringa("cognome"),
            req.getParametroStringa("username"),
            req.getParametroStringa("passwordHash"),
            req.getParametroStringa("dataNascita"),
            req.getParametroStringa("domicilio"),
            req.getParametroStringa("ruolo")
        );

        try {
            db.registrazione(u);
        } catch (SQLException e) {
            // SQLState 23505 = unique_violation: username già esistente
            if ("23505".equals(e.getSQLState())) {
                return Response.errore("Username '" + u.getUsername() + "' già in uso.");
            }
            throw e;
        }
        return Response.ok("Registrazione completata.");
    }

    private Response login(Request req, DatabaseManager db) throws SQLException {
        String username     = req.getParametroStringa("username");
        String passwordHash = req.getParametroStringa("passwordHash");
        Utente u = db.login(username, passwordHash);
        if (u == null) {
            return Response.errore("Username o password non corretti.");
        }
        // Salva la sessione lato server
        this.utenteLoggato = u;
        return Response.ok(u);
    }

    // =========================================================================
    // Comandi cliente – verifica ruolo prima di ogni operazione
    // =========================================================================

    private Response aggiungiPreferito(Request req, DatabaseManager db) throws SQLException {
        if (!isCliente()) return Response.errore("Accesso riservato ai clienti registrati.");
        String nome = req.getParametroStringa("nomeRistorante");
        try {
            db.aggiungiPreferito(utenteLoggato.getUsername(), nome);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState()))
                return Response.errore("Ristorante già nella lista dei preferiti.");
            throw e;
        }
        return Response.ok("Ristorante aggiunto ai preferiti.");
    }

    private Response rimuoviPreferito(Request req, DatabaseManager db) throws SQLException {
        if (!isCliente()) return Response.errore("Accesso riservato ai clienti registrati.");
        db.rimuoviPreferito(utenteLoggato.getUsername(),
                            req.getParametroStringa("nomeRistorante"));
        return Response.ok("Ristorante rimosso dai preferiti.");
    }

    private Response visualizzaPreferiti(Request req, DatabaseManager db) throws SQLException {
        if (!isCliente()) return Response.errore("Accesso riservato ai clienti registrati.");
        List<Ristorante> lista = db.visualizzaPreferiti(utenteLoggato.getUsername());
        return Response.ok(lista);
    }

    private Response aggiungiRecensione(Request req, DatabaseManager db) throws SQLException {
        if (!isCliente()) return Response.errore("Accesso riservato ai clienti registrati.");
        String nome   = req.getParametroStringa("nomeRistorante");
        int    stelle = req.getParametroInt("stelle");
        String testo  = req.getParametroStringa("testo");
        try {
            db.aggiungiRecensione(utenteLoggato.getUsername(), nome, stelle, testo);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState()))
                return Response.errore("Hai già inserito una recensione per questo ristorante.");
            throw e;
        }
        return Response.ok("Recensione aggiunta.");
    }

    private Response modificaRecensione(Request req, DatabaseManager db) throws SQLException {
        if (!isCliente()) return Response.errore("Accesso riservato ai clienti registrati.");
        boolean aggiornata = db.modificaRecensione(
            utenteLoggato.getUsername(),
            req.getParametroStringa("nomeRistorante"),
            req.getParametroInt("stelle"),
            req.getParametroStringa("testo")
        );
        return aggiornata
            ? Response.ok("Recensione modificata.")
            : Response.errore("Recensione non trovata.");
    }

    private Response eliminaRecensione(Request req, DatabaseManager db) throws SQLException {
        if (!isCliente()) return Response.errore("Accesso riservato ai clienti registrati.");
        boolean eliminata = db.eliminaRecensione(
            utenteLoggato.getUsername(),
            req.getParametroStringa("nomeRistorante")
        );
        return eliminata
            ? Response.ok("Recensione eliminata.")
            : Response.errore("Recensione non trovata.");
    }

    private Response visualizzaMieRecensioni(Request req, DatabaseManager db) throws SQLException {
        if (!isCliente()) return Response.errore("Accesso riservato ai clienti registrati.");
        List<Recensione> lista = db.visualizzaMieRecensioni(utenteLoggato.getUsername());
        return Response.ok(lista);
    }

    // =========================================================================
    // Comandi ristoratore – verifica ruolo prima di ogni operazione
    // =========================================================================

    private Response aggiungiRistorante(Request req, DatabaseManager db) throws SQLException {
        if (!isRistoratore()) return Response.errore("Accesso riservato ai ristoratori registrati.");
        Ristorante r = new Ristorante(
            req.getParametroStringa("nome"),
            req.getParametroStringa("nazione"),
            req.getParametroStringa("citta"),
            req.getParametroStringa("indirizzo"),
            req.getParametroDouble("latitudine"),
            req.getParametroDouble("longitudine"),
            req.getParametroDouble("fasciaPrezzo"),
            req.getParametroBoolean("delivery"),
            req.getParametroBoolean("prenotazione"),
            req.getParametroStringa("tipoCucina"),
            null // il proprietario viene impostato dal server, non dal client
        );
        try {
            db.aggiungiRistorante(r, utenteLoggato.getUsername());
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState()))
                return Response.errore("Esiste già un ristorante con il nome '"
                                       + r.getNome() + "'.");
            throw e;
        }
        return Response.ok("Ristorante aggiunto.");
    }

    private Response visualizzaRiepilogo(Request req, DatabaseManager db) throws SQLException {
        if (!isRistoratore()) return Response.errore("Accesso riservato ai ristoratori registrati.");
        List<Ristorante> lista = db.visualizzaRiepilogo(utenteLoggato.getUsername());
        return Response.ok(lista);
    }

    private Response visualizzaRecensioniRistoratore(DatabaseManager db) throws SQLException {
        if (!isRistoratore()) return Response.errore("Accesso riservato ai ristoratori registrati.");
        List<Recensione> lista = db.visualizzaRecensioniRistoratore(utenteLoggato.getUsername());
        return Response.ok(lista);
    }

    private Response rispondiRecensione(Request req, DatabaseManager db) throws SQLException {
        if (!isRistoratore()) return Response.errore("Accesso riservato ai ristoratori registrati.");
        boolean aggiornata = db.rispondiRecensione(
            utenteLoggato.getUsername(),
            req.getParametroStringa("nomeRistorante"),
            req.getParametroStringa("usernameCliente"),
            req.getParametroStringa("risposta")
        );
        return aggiornata
            ? Response.ok("Risposta salvata.")
            : Response.errore("Recensione non trovata o non autorizzato.");
    }

    // =========================================================================
    // Metodi di supporto per la verifica dei permessi
    // =========================================================================

    /**
     * Verifica che ci sia un utente loggato con ruolo "cliente".
     *
     * @return {@code true} se l'utente è autenticato come cliente
     */
    private boolean isCliente() {
        return utenteLoggato != null && utenteLoggato.isCliente();
    }

    /**
     * Verifica che ci sia un utente loggato con ruolo "ristoratore".
     *
     * @return {@code true} se l'utente è autenticato come ristoratore
     */
    private boolean isRistoratore() {
        return utenteLoggato != null && utenteLoggato.isRistoratore();
    }
}