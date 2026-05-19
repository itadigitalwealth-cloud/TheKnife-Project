/**
 * TheKnife – Modulo Common
 * Classe Request del protocollo Client/Server.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */

package it.uninsubria.theknife.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Rappresenta una richiesta inviata dal client al server.
 * <p>
 * Ogni richiesta contiene:
 * <ul>
 *   <li>il tipo di comando ({@link CommandType})</li>
 *   <li>lo username dell'utente che effettua la richiesta
 *       ({@code null} per utenti guest)</li>
 *   <li>una mappa di parametri chiave-valore con i dati necessari
 *       all'esecuzione del comando</li>
 * </ul>
 * </p>
 *
 * <p>
 * La classe implementa {@link Serializable} per permettere la
 * trasmissione via {@link java.io.ObjectOutputStream} sul socket TCP.
 * Tutti i valori inseriti nella mappa parametri devono anch'essi
 * essere {@link Serializable}.
 * </p>
 *
 * <p>Esempio di utilizzo lato client:</p>
 * <pre>{@code
 * Request req = new Request(CommandType.LOGIN, null);
 * req.aggiungiParametro("username", "mario");
 * req.aggiungiParametro("passwordHash", "abc123...");
 * }</pre>
 */
public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Tipo di operazione richiesta al server. */
    private final CommandType comando;

    /**
     * Username dell'utente autenticato che effettua la richiesta.
     * {@code null} se la richiesta proviene da un utente guest.
     */
    private final String username;

    /**
     * Parametri specifici del comando.
     * Le chiavi sono stringhe; i valori devono essere Serializable.
     */
    private final Map<String, Object> parametri;

    /**
     * Costruisce una nuova richiesta.
     *
     * @param comando  tipo di comando da eseguire (non deve essere {@code null})
     * @param username username dell'utente loggato, oppure {@code null} per guest
     * @throws IllegalArgumentException se {@code comando} è {@code null}
     */
    public Request(CommandType comando, String username) {
        if (comando == null) {
            throw new IllegalArgumentException("Il tipo di comando non può essere null.");
        }
        this.comando   = comando;
        this.username  = username;
        this.parametri = new HashMap<>();
    }

    /**
     * Aggiunge un parametro alla richiesta.
     *
     * @param chiave chiave del parametro (non deve essere {@code null})
     * @param valore valore del parametro (deve implementare {@link Serializable})
     * @return questa stessa istanza, per permettere chiamate a catena (fluent API)
     * @throws IllegalArgumentException se {@code chiave} è {@code null}
     */
    public Request aggiungiParametro(String chiave, Object valore) {
        if (chiave == null) {
            throw new IllegalArgumentException("La chiave del parametro non può essere null.");
        }
        parametri.put(chiave, valore);
        return this;
    }

    /**
     * Restituisce il valore di un parametro.
     *
     * @param chiave chiave del parametro
     * @return il valore associato, oppure {@code null} se non presente
     */
    public Object getParametro(String chiave) {
        return parametri.get(chiave);
    }

    /**
     * Restituisce il valore di un parametro come {@link String}.
     *
     * @param chiave chiave del parametro
     * @return il valore come stringa, oppure {@code null} se non presente
     */
    public String getParametroStringa(String chiave) {
        Object val = parametri.get(chiave);
        return val != null ? val.toString() : null;
    }

    /**
     * Restituisce il valore di un parametro come {@code int}.
     *
     * @param chiave chiave del parametro
     * @return il valore come intero
     * @throws ClassCastException       se il valore non è un {@link Number}
     * @throws NullPointerException     se il parametro non è presente
     */
    public int getParametroInt(String chiave) {
        return ((Number) parametri.get(chiave)).intValue();
    }

    /**
     * Restituisce il valore di un parametro come {@code double}.
     *
     * @param chiave chiave del parametro
     * @return il valore come double
     * @throws ClassCastException   se il valore non è un {@link Number}
     * @throws NullPointerException se il parametro non è presente
     */
    public double getParametroDouble(String chiave) {
        return ((Number) parametri.get(chiave)).doubleValue();
    }

    /**
     * Restituisce il valore di un parametro come {@code boolean}.
     *
     * @param chiave chiave del parametro
     * @return il valore come boolean, {@code false} se non presente
     */
    public boolean getParametroBoolean(String chiave) {
        Object val = parametri.get(chiave);
        if (val instanceof Boolean b) return b;
        return false;
    }

    /**
     * Verifica se un determinato parametro è presente nella richiesta.
     *
     * @param chiave chiave da cercare
     * @return {@code true} se il parametro esiste
     */
    public boolean hasParametro(String chiave) {
        return parametri.containsKey(chiave);
    }

    /** @return il tipo di comando della richiesta */
    public CommandType getComando() {
        return comando;
    }

    /**
     * @return lo username dell'utente loggato,
     *         oppure {@code null} se la richiesta è guest
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return vista non modificabile della mappa dei parametri
     */
    public Map<String, Object> getParametri() {
        return Collections.unmodifiableMap(parametri);
    }

    @Override
    public String toString() {
        return "Request{comando=" + comando
                + ", username=" + username
                + ", parametri=" + parametri + '}';
    }
}