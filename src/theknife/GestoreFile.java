/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello gestorefile.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * Classe di servizio (stateless) per la persistenza dei dati in formato CSV.
 * <p>Gestisce cinque aree funzionali:</p>
 * <ul>
 *   <li><b>Utenti</b> – 7 campi</li>
 *   <li><b>Ristoranti</b> – 11 campi (l'ultimo è il proprietario)</li>
 *   <li><b>Recensioni</b> – 5 campi</li>
 *   <li><b>Preferiti</b> – 2 campi</li>
 *   <li><b>Ricerca ristoranti</b> – filtro combinato su più criteri</li>
 * </ul>
 *
 * Tutti i metodi sono statici e non mantengono stato interno.
 */
public final class GestoreFile {

    /** Separatore di campo usato nei file CSV. */
    private static final String SEP = ";";

    /* ========================================================================
       SEZIONE UTENTI
       ====================================================================== */

    /**
     * Legge tutti gli utenti da un file CSV.
     *
     * @param percorso path del file <code>utenti.csv</code>
     * @return lista di {@link Utente}; se il file non esiste viene restituita
     *         una lista vuota
     */
    public static List<Utente> caricaUtenti(String percorso) {
        List<Utente> lista = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();                         // salta intestazione
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP);
                if (c.length < 7) continue;        // riga malformata
                lista.add(new Utente(c[0], c[1], c[2], c[3], c[4], c[5], c[6]));
            }
        } catch (IOException e) {
            System.err.println("Errore lettura utenti: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Sovrascrive (o crea) il file utenti con l’elenco fornito.
     *
     * @param utenti   lista di utenti da salvare
     * @param percorso path del file CSV
     */
    public static void salvaUtenti(List<Utente> utenti, String percorso) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("Nome;Cognome;Username;Password;DataNascita;Domicilio;Ruolo");
            for (Utente u : utenti) {
                pw.println(u.getNome() + SEP + u.getCognome() + SEP + u.getUsername() + SEP +
                           u.getPasswordCifrata() + SEP + u.getDataNascita() + SEP +
                           u.getDomicilio() + SEP + u.getRuolo());
            }
        } catch (IOException e) {
            System.err.println("Errore salvataggio utenti: " + e.getMessage());
        }
    }

    /**
     * Verifica se esiste già un utente con lo username indicato.
     *
     * @param username username da cercare
     * @param percorso file utenti
     * @return {@code true} se presente
     */
    public static boolean usernameEsistente(String username, String percorso) {
        return caricaUtenti(percorso).stream()
               .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    /**
     * Aggiunge un nuovo utente se lo username non è occupato.
     *
     * @param u        oggetto {@link Utente} da aggiungere
     * @param percorso file utenti
     */
    public static void aggiungiUtente(Utente u, String percorso) {
        if (usernameEsistente(u.getUsername(), percorso)) {
            System.out.println("ERRORE: username \"" + u.getUsername() + "\" già esistente!");
            return;
        }
        List<Utente> lista = caricaUtenti(percorso);
        lista.add(u);
        salvaUtenti(lista, percorso);
    }

    /* ========================================================================
       SEZIONE RISTORANTI
       ====================================================================== */

    /**
     * Carica tutti i ristoranti dal file CSV.
     *
     * @param percorso path del file <code>ristoranti.csv</code>
     * @return lista di {@link Ristorante}
     */
    public static List<Ristorante> caricaRistoranti(String percorso) {
        List<Ristorante> lista = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();                 // salta header
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP);
                if (c.length < 11) continue;
                lista.add(new Ristorante(
                        c[0], c[1], c[2], c[3],
                        Double.parseDouble(c[4]),
                        Double.parseDouble(c[5]),
                        Double.parseDouble(c[6]),
                        "si".equalsIgnoreCase(c[7]),
                        "si".equalsIgnoreCase(c[8]),
                        c[9], c[10]
                ));
            }
        } catch (IOException e) {
            System.err.println("Errore lettura ristoranti: " + e.getMessage());
        }
        return lista;
    }

    /** @return <code>true</code> se esiste un ristorante con lo stesso nome. */
    public static boolean ristoranteEsistente(String nome, String percorso) {
        return caricaRistoranti(percorso).stream()
               .anyMatch(r -> r.getNome().equalsIgnoreCase(nome));
    }

    /**
     * Salva l’elenco ristoranti su file, sovrascrivendo il contenuto.
     */
    public static void salvaRistoranti(List<Ristorante> list, String percorso) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("Nome;Nazione;Citta;Indirizzo;Latitudine;Longitudine;FasciaPrezzo;"
                     + "Delivery;Prenotazione;TipoCucina;Proprietario");
            for (Ristorante r : list) {
                pw.println(r.getNome() + SEP + r.getNazione() + SEP + r.getCitta() + SEP
                         + r.getIndirizzo() + SEP + r.getLatitudine() + SEP + r.getLongitudine() + SEP
                         + r.getFasciaPrezzo() + SEP + (r.isDelivery() ? "si" : "no") + SEP
                         + (r.isPrenotazione() ? "si" : "no") + SEP + r.getTipoCucina() + SEP
                         + (r.getProprietario() != null ? r.getProprietario() : ""));
            }
        } catch (IOException e) {
            System.err.println("Errore salvataggio ristoranti: " + e.getMessage());
        }
    }

    /**
     * Aggiunge un ristorante se non esiste già uno con lo stesso nome.
     */
    public static void aggiungiRistorante(Ristorante r, String percorso) {
        if (ristoranteEsistente(r.getNome(), percorso)) {
            System.out.println("ERRORE: ristorante \"" + r.getNome() + "\" già esistente!");
            return;
        }
        List<Ristorante> lista = caricaRistoranti(percorso);
        lista.add(r);
        salvaRistoranti(lista, percorso);
    }

    /* ========================================================================
       SEZIONE RECENSIONI
       ====================================================================== */

    /**
     * Carica tutte le recensioni da file CSV.
     *
     * @param percorso path del file <code>recensioni.csv</code>
     * @return lista di {@link Recensione}
     */
    public static List<Recensione> caricaRecensioni(String percorso) {
        List<Recensione> lista = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();                             // salta header
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP, -1);      // -1 => mantiene campi vuoti
                if (c.length < 4) continue;
                lista.add(new Recensione(
                        c[0].trim(),
                        c[1].trim(),
                        Integer.parseInt(c[2].trim()),
                        c[3].trim(),
                        c.length >= 5 ? c[4].trim() : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Errore lettura recensioni: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Sovrascrive il file recensioni con l’elenco fornito.
     */
    public static void salvaRecensioni(List<Recensione> recs, String percorso) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("IdRistorante;Username;Stelle;Testo;Risposta");
            for (Recensione r : recs) {
                pw.println(r.getIdRistorante() + SEP + r.getUsername() + SEP + r.getStelle() + SEP
                         + r.getTesto() + SEP + (r.getRisposta() != null ? r.getRisposta() : ""));
            }
        } catch (IOException e) {
            System.err.println("Errore salvataggio recensioni: " + e.getMessage());
        }
    }

    /**
     * Aggiunge una nuova recensione dopo aver verificato che l'utente non ne
     * abbia già inserita una per lo stesso ristorante.
     *
     * @param rec       recensione da aggiungere
     * @param percorso  file <code>recensioni.csv</code>
     */
    public static void aggiungiRecensione(Recensione rec, String percorso) {
        File f = new File(percorso);
        boolean exists = f.exists();

        /* Controllo duplicati */
        if (exists) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                br.readLine();               // salta intestazione
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.split(SEP);
                    if (p.length >= 2 &&
                        p[0].trim().equalsIgnoreCase(rec.getIdRistorante().trim()) &&
                        p[1].trim().equalsIgnoreCase(rec.getUsername().trim())) {
                        JOptionPane.showMessageDialog(null,
                                "Recensione già inserita.\n"
                              + "Modifica o elimina quella esistente prima di crearne una nuova.",
                                "Avviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Errore verifica duplicati: " + e.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        /* Append su file */
        try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))) {
            if (!exists) pw.println("IdRistorante;Username;Stelle;Testo;Risposta");
            pw.println(rec.getIdRistorante().trim() + SEP +
                       rec.getUsername().trim()      + SEP +
                       rec.getStelle()               + SEP +
                       rec.getTesto().trim()         + SEP +
                       (rec.getRisposta() != null ? rec.getRisposta().trim() : ""));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Errore aggiunta recensione: " + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica una recensione esistente identificata da utente + ristorante.
     */
    public static void modificaRecensione(String percorso,
                                          String user,
                                          String idRisto,
                                          String nuovoTesto,
                                          int nuoveStelle) {
        List<Recensione> list = caricaRecensioni(percorso);
        list.forEach(r -> {
            if (r.getUsername().equalsIgnoreCase(user) &&
                r.getIdRistorante().equalsIgnoreCase(idRisto)) {
                r.setTesto(nuovoTesto);
                r.setStelle(nuoveStelle);
            }
        });
        salvaRecensioni(list, percorso);
    }

    /**
     * Elimina la recensione di un utente per un ristorante.
     */
    public static void eliminaRecensione(String percorso,
                                         String user,
                                         String idRisto) {
        List<Recensione> list = caricaRecensioni(percorso).stream()
                .filter(r -> !(r.getUsername().equalsIgnoreCase(user) &&
                               r.getIdRistorante().equalsIgnoreCase(idRisto)))
                .collect(Collectors.toList());
        salvaRecensioni(list, percorso);
    }

    /* ========================================================================
       SEZIONE PREFERITI
       ====================================================================== */

    /**
     * Restituisce i ristoranti preferiti di un utente.
     *
     * @param percorso file <code>preferiti.csv</code>
     * @param username utente interessato
     */
    public static List<String> caricaPreferiti(String percorso, String username) {
        List<String> result = new ArrayList<>();
        File f = new File(percorso);
        if (!f.exists()) return result;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();                        // header
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(SEP);
                if (c.length == 2 && c[0].equalsIgnoreCase(username)) {
                    result.add(c[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura preferiti: " + e.getMessage());
        }
        return result;
    }

    /** Scrive l'intera lista di preferiti su file (overwrite). */
    public static void salvaPreferiti(String percorso, List<String[]> righe) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(percorso))) {
            pw.println("Username;NomeRistorante");
            for (String[] r : righe) pw.println(r[0] + SEP + r[1]);
        } catch (IOException e) {
            System.err.println("Errore salvataggio preferiti: " + e.getMessage());
        }
    }

    /**
     * Aggiunge un ristorante ai preferiti dell'utente, evitando duplicati.
     */
    public static void aggiungiPreferito(String percorso,
                                         String username,
                                         String risto) {
        List<String[]> righe = new ArrayList<>();
        File f = new File(percorso);

        /* leggi file esistente (se presente) */
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                br.readLine(); // header
                String line;
                while ((line = br.readLine()) != null) {
                    String[] c = line.split(SEP);
                    if (c.length == 2) righe.add(c);
                }
            } catch (IOException e) {
                System.err.println("Errore lettura preferiti: " + e.getMessage());
            }
        }

        /* evita duplicati */
        boolean presente = righe.stream()
                .anyMatch(r -> r[0].equalsIgnoreCase(username)
                            && r[1].equalsIgnoreCase(risto));

        if (!presente) righe.add(new String[]{username, risto});
        salvaPreferiti(percorso, righe);
    }

    /** Rimuove un ristorante dai preferiti dell'utente. */
    public static void rimuoviPreferito(String percorso,
                                        String username,
                                        String risto) {
        List<String[]> righe = new ArrayList<>();
        File f = new File(percorso);

        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                br.readLine(); // header
                String line;
                while ((line = br.readLine()) != null) {
                    String[] c = line.split(SEP);
                    if (c.length == 2) righe.add(c);
                }
            } catch (IOException e) {
                System.err.println("Errore lettura preferiti: " + e.getMessage());
            }
        }

        righe.removeIf(r -> r[0].equalsIgnoreCase(username)
                          && r[1].equalsIgnoreCase(risto));
        salvaPreferiti(percorso, righe);
    }

    /* ========================================================================
       SEZIONE RICERCA
       ====================================================================== */

    /**
     * Ricerca i ristoranti filtrando una combinazione di criteri.
     *
     * @param percorsoFile file ristoranti
     * @param nomeParziale stringa contenuta nel nome (facoltativo)
     * @param citta        città esatta (facoltativo)
     * @param tipoCucina   tipo cucina (facoltativo, match parziale)
     * @param maxPrezzo    prezzo massimo (facoltativo)
     * @param delivery     {@code true} se si richiede il servizio
     * @param prenotazione {@code true} se si richiede la prenotazione
     * @param mediaMin     media stelle minima (facoltativa)
     * @return lista di ristoranti che soddisfano tutti i criteri impostati
     */
    public static List<Ristorante> cercaRistoranti(
            String percorsoFile,
            String nomeParziale,
            String citta,
            String tipoCucina,
            Double maxPrezzo,
            Boolean delivery,
            Boolean prenotazione,
            Double mediaMin) {

        List<Ristorante> tutti = caricaRistoranti(percorsoFile);
        List<Recensione> recensioni = caricaRecensioni("data/recensioni.csv");

        return tutti.stream()
                .filter(r -> {
                    boolean ok = true;
                    if (nomeParziale != null && !nomeParziale.isBlank())
                        ok &= r.getNome().toLowerCase().contains(nomeParziale.toLowerCase());
                    if (citta != null && !citta.isBlank())
                        ok &= r.getCitta().equalsIgnoreCase(citta.trim());
                    if (tipoCucina != null && !tipoCucina.isBlank())
                        ok &= r.getTipoCucina().toLowerCase().contains(tipoCucina.toLowerCase());
                    if (maxPrezzo != null)
                        ok &= r.getFasciaPrezzo() <= maxPrezzo;
                    if (Boolean.TRUE.equals(delivery))
                        ok &= r.isDelivery();
                    if (Boolean.TRUE.equals(prenotazione))
                        ok &= r.isPrenotazione();
                    if (mediaMin != null) {
                        double media = recensioni.stream()
                                .filter(rec -> rec.getIdRistorante()
                                                  .equalsIgnoreCase(r.getNome()))
                                .mapToInt(Recensione::getStelle)
                                .average()
                                .orElse(0.0);
                        ok &= media >= mediaMin;
                    }
                    return ok;
                })
                .toList();
    }

    /**
     * Calcola la media aritmetica delle stelle di un ristorante.
     *
     * @param percorsoRecensioni file recensioni
     * @param nomeRisto          nome ristorante
     * @return media stelle (0.0 se nessuna recensione)
     */
    public static double calcolaMediaStelle(String percorsoRecensioni,
                                            String nomeRisto) {
        List<Recensione> recs = caricaRecensioni(percorsoRecensioni).stream()
                .filter(r -> r.getIdRistorante().equalsIgnoreCase(nomeRisto))
                .toList();
        if (recs.isEmpty()) return 0.0;
        return recs.stream().mapToInt(Recensione::getStelle).average().orElse(0.0);
    }

    /* ====================================================================== */
    /*                          FINE CLASSE                                   */
    /* ====================================================================== */

    /** Costruttore privato per impedire l'istanziazione. */
    private GestoreFile() { }
}
