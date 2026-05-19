/**
 * TheKnife – Modulo Common
 * Classe di modello recensione.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */

package it.uninsubria.theknife.common.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Rappresenta una recensione associata a un ristorante.
 * <p>
 * Una recensione è identificata dalla coppia
 * ({@code nomeRistorante}, {@code usernameCliente}): ogni cliente può
 * inserire al massimo una recensione per ristorante (vincolo garantito
 * anche dalla chiave primaria composta nel database).
 * </p>
 * <p>
 * Il campo {@code risposta} è {@code null} finché il ristoratore non
 * risponde; una volta inserita, la risposta non può essere rimossa
 * (al massimo una risposta per recensione).
 * </p>
 */
public class Recensione implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Nome del ristorante oggetto della recensione. */
    private String nomeRistorante;

    /** Username del cliente autore della recensione. */
    private String usernameCliente;

    /** Valutazione in stelle: valore intero compreso tra 1 e 5. */
    private int stelle;

    /** Testo libero della recensione. */
    private String testo;

    /**
     * Risposta del ristoratore alla recensione.
     * {@code null} se il ristoratore non ha ancora risposto.
     */
    private String risposta;

    /** Costruttore vuoto richiesto per deserializzazione. */
    public Recensione() {}

    /**
     * Costruttore completo.
     *
     * @param nomeRistorante  nome del ristorante recensito
     * @param usernameCliente username dell'autore
     * @param stelle          valore da 1 a 5
     * @param testo           testo della recensione
     * @param risposta        risposta del ristoratore ({@code null} se assente)
     * @throws IllegalArgumentException se le stelle non sono tra 1 e 5
     */
    public Recensione(String nomeRistorante, String usernameCliente,
                      int stelle, String testo, String risposta) {
        this.nomeRistorante  = nomeRistorante;
        this.usernameCliente = usernameCliente;
        setStelle(stelle);
        this.testo    = testo;
        this.risposta = risposta;
    }

    // -------------------------------------------------------------------------
    // Getter e Setter
    // -------------------------------------------------------------------------

    /** @return nome del ristorante recensito */
    public String getNomeRistorante() { return nomeRistorante; }

    /** @param nomeRistorante nome da impostare */
    public void setNomeRistorante(String nomeRistorante) { this.nomeRistorante = nomeRistorante; }

    /** @return username del cliente autore */
    public String getUsernameCliente() { return usernameCliente; }

    /** @param usernameCliente username da impostare */
    public void setUsernameCliente(String usernameCliente) { this.usernameCliente = usernameCliente; }

    /**
     * @return numero di stelle assegnate (tra 1 e 5)
     */
    public int getStelle() { return stelle; }

    /**
     * Imposta il numero di stelle con validazione.
     *
     * @param stelle valore compreso tra 1 e 5
     * @throws IllegalArgumentException se il valore non è nel range ammesso
     */
    public void setStelle(int stelle) {
        if (stelle < 1 || stelle > 5)
            throw new IllegalArgumentException(
                "Il numero di stelle deve essere compreso tra 1 e 5, ricevuto: " + stelle);
        this.stelle = stelle;
    }

    /** @return testo della recensione */
    public String getTesto() { return testo; }

    /** @param testo testo da impostare */
    public void setTesto(String testo) { this.testo = testo; }

    /**
     * @return risposta del ristoratore, oppure {@code null} se non ancora fornita
     */
    public String getRisposta() { return risposta; }

    /** @param risposta risposta da impostare */
    public void setRisposta(String risposta) { this.risposta = risposta; }

    /**
     * Indica se il ristoratore ha già risposto a questa recensione.
     *
     * @return {@code true} se la risposta è presente e non vuota
     */
    public boolean hasRisposta() {
        return risposta != null && !risposta.isBlank();
    }

    @Override
    public String toString() {
        return "Recensione{nomeRistorante='" + nomeRistorante +
               "', usernameCliente='" + usernameCliente +
               "', stelle=" + stelle +
               ", hasRisposta=" + hasRisposta() + '}';
    }
}