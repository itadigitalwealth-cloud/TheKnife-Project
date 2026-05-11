/**
 * TheKnife – Modulo Client
 * Gestore della connessione socket verso il server TheKnife.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client;

import it.uninsubria.theknife.common.CommandType;
import it.uninsubria.theknife.common.Request;
import it.uninsubria.theknife.common.Response;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Gestisce la connessione TCP con il server TheKnife.
 * <p>
 * Questa classe è il <b>unico punto di contatto</b> tra la GUI e il server:
 * tutti i pannelli Swing ottengono l'istanza tramite {@link ClientTK#getConnessione()}
 * e invocano {@link #invia(Request)} per ogni operazione.
 * </p>
 *
 * <p>
 * La connessione è <b>persistente</b> per tutta la sessione dell'utente:
 * viene aperta all'avvio del client e chiusa alla chiusura della finestra.
 * Questo permette al server di mantenere la sessione (utente loggato)
 * associata al socket senza che il client debba riautenticarsi ad ogni richiesta.
 * </p>
 *
 * <p>La classe offre anche metodi di comodo per le operazioni più frequenti
 * (login, registrazione, ricerca) in modo da non esporre {@link Request}
 * e {@link Response} direttamente ai pannelli GUI.</p>
 *
 * <p>Implementa {@link AutoCloseable} per l'uso in try-with-resources.</p>
 */
public class ServerConnection implements AutoCloseable {

    /** Socket TCP verso il server. */
    private final Socket socket;

    /** Stream di scrittura verso il server. */
    private final ObjectOutputStream out;

    /** Stream di lettura dal server. */
    private final ObjectInputStream in;

    // -------------------------------------------------------------------------
    // Costruttore
    // -------------------------------------------------------------------------

    /**
     * Apre la connessione TCP verso il server TheKnife.
     *
     * @param host  indirizzo del server (es. {@code "localhost"})
     * @param porta porta del server (es. {@code 9090})
     * @throws IOException se la connessione non riesce
     */
    public ServerConnection(String host, int porta) throws IOException {
        socket = new Socket(host, porta);
        // L'OutputStream va aperto e flushato PRIMA dell'InputStream,
        // altrimenti i due lati si bloccano in attesa l'uno dell'altro
        // (deadlock nell'handshake di ObjectStream).
        out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        out.flush();
        in  = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    // -------------------------------------------------------------------------
    // Metodo base di comunicazione
    // -------------------------------------------------------------------------

    /**
     * Invia una {@link Request} al server e attende la {@link Response}.
     * <p>
     * Questo metodo è <b>sincronizzato</b> per garantire che ogni pannello
     * della GUI (che gira sull'EDT) non possa inviare due richieste
     * contemporaneamente sullo stesso socket.
     * </p>
     *
     * @param req richiesta da inviare
     * @return risposta ricevuta dal server
     * @throws IOException            in caso di errore di rete
     * @throws ClassNotFoundException se la risposta non è deserializzabile
     */
    public synchronized Response invia(Request req)
            throws IOException, ClassNotFoundException {
        out.writeObject(req);
        out.flush();
        out.reset(); // evita la cache di ObjectOutputStream
        return (Response) in.readObject();
    }

    // -------------------------------------------------------------------------
    // Metodi di comodo (usati direttamente dai pannelli GUI)
    // -------------------------------------------------------------------------

    /**
     * Esegue il login dell'utente.
     *
     * @param username username inserito
     * @param password password in chiaro (verrà hashata internamente)
     * @return {@link Response} con l'oggetto {@code Utente} se il login ha
     *         successo, risposta di errore altrimenti
     * @throws IOException            in caso di errore di rete
     * @throws ClassNotFoundException se la risposta non è deserializzabile
     */
    public Response login(String username, String password)
            throws IOException, ClassNotFoundException {
        Request req = new Request(CommandType.LOGIN, null)
                .aggiungiParametro("username",     username)
                .aggiungiParametro("passwordHash", sha256(password));
        return invia(req);
    }

    /**
     * Registra un nuovo utente.
     *
     * @param nome         nome
     * @param cognome      cognome
     * @param username     username desiderato
     * @param password     password in chiaro (verrà hashata)
     * @param dataNascita  data di nascita (può essere vuota)
     * @param domicilio    domicilio
     * @param ruolo        {@code "cliente"} o {@code "ristoratore"}
     * @return {@link Response} di esito
     * @throws IOException            in caso di errore di rete
     * @throws ClassNotFoundException se la risposta non è deserializzabile
     */
    public Response registrazione(String nome, String cognome, String username,
                                   String password, String dataNascita,
                                   String domicilio, String ruolo)
            throws IOException, ClassNotFoundException {
        Request req = new Request(CommandType.REGISTRAZIONE, null)
                .aggiungiParametro("nome",         nome)
                .aggiungiParametro("cognome",      cognome)
                .aggiungiParametro("username",     username)
                .aggiungiParametro("passwordHash", sha256(password))
                .aggiungiParametro("dataNascita",  dataNascita)
                .aggiungiParametro("domicilio",    domicilio)
                .aggiungiParametro("ruolo",        ruolo);
        return invia(req);
    }

    /**
     * Calcola l'hash SHA-256 di una stringa e lo restituisce in formato
     * esadecimale minuscolo (64 caratteri).
     * <p>
     * L'hashing avviene <b>lato client</b>: la password in chiaro non
     * transita mai sulla rete.
     * </p>
     *
     * @param input stringa da hashare (tipicamente la password in chiaro)
     * @return stringa esadecimale di 64 caratteri
     * @throws IllegalStateException se SHA-256 non è disponibile nella JVM
     */
    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(64);
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 è garantito da tutte le JVM conformi a Java SE
            throw new IllegalStateException("SHA-256 non disponibile", e);
        }
    }

    // -------------------------------------------------------------------------
    // Chiusura
    // -------------------------------------------------------------------------

    /**
     * Chiude gli stream e il socket verso il server.
     * Chiamato automaticamente alla chiusura della finestra principale.
     */
    @Override
    public void close() {
        try {
            if (out != null)    out.close();
            if (in  != null)    in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("[Client] Errore nella chiusura della connessione: "
                               + e.getMessage());
        }
    }
}