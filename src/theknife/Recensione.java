/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello recensione.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife;

/**
 * Rappresenta una recensione associata a un ristorante.
 * Contiene l'autore, il punteggio in stelle, il testo della recensione
 * e una possibile risposta del ristoratore.
 */
public class Recensione {
    /** Nome identificativo del ristorante oggetto della recensione */
    private String idRistorante;

    /** Username dell’utente autore della recensione */
    private String username;

    /** Voto espresso in stelle (valori ammessi da 1 a 5) */
    private int stelle;

    /** Testo libero inserito dal cliente */
    private String testo;

    /** Eventuale replica del ristoratore alla recensione */
    private String risposta;

    /**
     * Costruttore vuoto, richiesto per operazioni di deserializzazione o istanziazione dinamica.
     */
    public Recensione() {
    }

    /**
     * Costruttore completo con inizializzazione di tutti i campi.
     *
     * @param idRistorante Nome del ristorante recensito
     * @param username     Username dell’utente autore
     * @param stelle       Valutazione in stelle (da 1 a 5)
     * @param testo        Testo della recensione
     * @param risposta     Risposta del ristoratore (può essere null o vuota)
     */
    public Recensione(String idRistorante, String username,
                      int stelle, String testo, String risposta) {
        this.idRistorante = idRistorante;
        this.username = username;
        setStelle(stelle); // include validazione
        this.testo = testo;
        this.risposta = risposta;
    }

    /** @return il nome del ristorante oggetto della recensione */
    public String getIdRistorante() {
        return idRistorante;
    }

    /** @param idRistorante il nome del ristorante da impostare */
    public void setIdRistorante(String idRistorante) {
        this.idRistorante = idRistorante;
    }

    /** @return username dell’autore della recensione */
    public String getUsername() {
        return username;
    }

    /** @param username username dell’utente da impostare */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return il numero di stelle assegnato (tra 1 e 5)
     */
    public int getStelle() {
        return stelle;
    }

    /**
     * Imposta il numero di stelle assegnate alla recensione.
     *
     * @param stelle valore tra 1 e 5
     * @throws IllegalArgumentException se il valore non è compreso tra 1 e 5
     */
    public void setStelle(int stelle) {
        if (stelle < 1 || stelle > 5)
            throw new IllegalArgumentException("Il numero di stelle deve essere tra 1 e 5.");
        this.stelle = stelle;
    }

    /** @return il testo della recensione */
    public String getTesto() {
        return testo;
    }

    /** @param testo il contenuto testuale da impostare */
    public void setTesto(String testo) {
        this.testo = testo;
    }

    /** @return la risposta del ristoratore (può essere null o vuota) */
    public String getRisposta() {
        return risposta;
    }

    /** @param risposta la risposta da impostare alla recensione */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    /**
     * Verifica se è presente una risposta associata alla recensione.
     *
     * @return true se esiste una risposta non vuota
     */
    public boolean hasRisposta() {
        return risposta != null && !risposta.trim().isEmpty();
    }

    /**
     * Ritorna una rappresentazione testuale completa della recensione.
     *
     * @return stringa descrittiva della recensione
     */
    @Override
    public String toString() {
        return "Recensione{" +
                "idRistorante='" + idRistorante + '\'' +
                ", username='" + username + '\'' +
                ", stelle=" + stelle +
                ", testo='" + testo + '\'' +
                ", risposta='" + risposta + '\'' +
                '}';
    }
}
