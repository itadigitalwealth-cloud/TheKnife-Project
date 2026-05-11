/**
 * TheKnife – Modulo Common
 * Classe Response del protocollo Client/Server.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.common;

import java.io.Serial;
import java.io.Serializable;

/**
 * Rappresenta la risposta che il server invia al client a seguito
 * dell'elaborazione di una {@link Request}.
 * <p>
 * Ogni risposta contiene:
 * <ul>
 *   <li>un flag {@code successo} che indica se l'operazione è andata a buon fine</li>
 *   <li>un messaggio leggibile dall'utente (utile in caso di errore)</li>
 *   <li>un oggetto {@code dato} opzionale con il risultato della query
 *       (es. {@code List<Ristorante>}, {@code Utente}, ecc.)</li>
 * </ul>
 * </p>
 *
 * <p>
 * La classe implementa {@link Serializable} per la trasmissione via socket.
 * Il campo {@code dato} deve anch'esso essere {@link Serializable}.
 * </p>
 *
 * <p>Esempi di utilizzo lato server:</p>
 * <pre>{@code
 * // Risposta di successo con dati
 * Response.ok(listaRistoranti)
 *
 * // Risposta di successo senza dati
 * Response.ok()
 *
 * // Risposta di errore
 * Response.errore("Username già esistente.")
 * }</pre>
 */
public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** {@code true} se l'operazione è avvenuta con successo. */
    private final boolean successo;

    /**
     * Messaggio descrittivo del risultato.
     * In caso di errore contiene la descrizione del problema.
     * In caso di successo può essere una conferma testuale o una stringa vuota.
     */
    private final String messaggio;

    /**
     * Oggetto con il risultato dell'operazione.
     * Può essere {@code null} per operazioni che non restituiscono dati
     * (es. aggiunta preferito, eliminazione recensione, ecc.).
     * Esempi tipici:
     * <ul>
     *   <li>{@code List<Ristorante>} per CERCA_RISTORANTI</li>
     *   <li>{@code Utente} per LOGIN</li>
     *   <li>{@code List<Recensione>} per VISUALIZZA_RECENSIONI</li>
     *   <li>{@code Double} per la media stelle</li>
     * </ul>
     */
    private final Object dato;

    // -------------------------------------------------------------------------
    // Costruttore privato – si usa tramite i factory method statici
    // -------------------------------------------------------------------------

    /**
     * Costruttore privato. Usare i metodi statici {@link #ok()},
     * {@link #ok(Object)}, {@link #ok(String, Object)} e {@link #errore(String)}.
     *
     * @param successo  esito dell'operazione
     * @param messaggio descrizione testuale
     * @param dato      oggetto risultato (può essere {@code null})
     */
    private Response(boolean successo, String messaggio, Object dato) {
        this.successo  = successo;
        this.messaggio = messaggio != null ? messaggio : "";
        this.dato      = dato;
    }

    // -------------------------------------------------------------------------
    // Factory method – API pubblica
    // -------------------------------------------------------------------------

    /**
     * Crea una risposta di successo senza dati.
     *
     * @return risposta positiva con messaggio vuoto e dato {@code null}
     */
    public static Response ok() {
        return new Response(true, "", null);
    }

    /**
     * Crea una risposta di successo con dato risultante.
     *
     * @param dato oggetto risultato (deve essere {@link Serializable})
     * @return risposta positiva con il dato fornito
     */
    public static Response ok(Object dato) {
        return new Response(true, "", dato);
    }

    /**
     * Crea una risposta di successo con messaggio e dato.
     *
     * @param messaggio messaggio di conferma
     * @param dato      oggetto risultato
     * @return risposta positiva con messaggio e dato
     */
    public static Response ok(String messaggio, Object dato) {
        return new Response(true, messaggio, dato);
    }

    /**
     * Crea una risposta di errore.
     *
     * @param messaggio descrizione dell'errore da mostrare all'utente
     * @return risposta negativa con il messaggio fornito
     */
    public static Response errore(String messaggio) {
        return new Response(false, messaggio, null);
    }

    // -------------------------------------------------------------------------
    // Getter
    // -------------------------------------------------------------------------

    /**
     * @return {@code true} se l'operazione è avvenuta con successo
     */
    public boolean isSuccesso() {
        return successo;
    }

    /**
     * @return il messaggio descrittivo del risultato (mai {@code null})
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * @return il dato risultante, oppure {@code null} se non presente
     */
    public Object getDato() {
        return dato;
    }

    /**
     * Metodo di comodo per ottenere il dato con cast automatico.
     * Evita al chiamante il cast esplicito nel caso più comune.
     *
     * @param <T> tipo atteso
     * @return il dato risultante con cast al tipo richiesto
     * @throws ClassCastException se il dato non è del tipo richiesto
     */
    @SuppressWarnings("unchecked")
    public <T> T getDatoTipizzato() {
        return (T) dato;
    }

    @Override
    public String toString() {
        return "Response{successo=" + successo
                + ", messaggio='" + messaggio + '\''
                + ", dato=" + dato + '}';
    }
}