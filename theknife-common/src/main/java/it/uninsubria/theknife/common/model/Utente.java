/**
 * TheKnife – Modulo Common
 * Classe di modello utente.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.common.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Rappresenta un utente registrato nel sistema TheKnife.
 * <p>
 * Può essere un <b>cliente</b> (inserisce recensioni, gestisce preferiti)
 * o un <b>ristoratore</b> (crea ristoranti, risponde alle recensioni).
 * </p>
 * <p>
 * Implementa {@link Serializable} per la trasmissione via socket.
 * La password hashata <b>non</b> viene mai inviata dal server al client:
 * il campo è presente solo per la fase di login/registrazione
 * (client → server).
 * </p>
 */
public class Utente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cognome;
    private String username;

    /**
     * Hash SHA-256 della password in formato esadecimale (64 caratteri).
     * Questo campo viene popolato solo nelle richieste di login e registrazione
     * (direzione client → server). Il server non lo include nelle risposte.
     */
    private String passwordHash;

    private String dataNascita;
    private String domicilio;
    private String ruolo;

    /** Costruttore vuoto richiesto per deserializzazione. */
    public Utente() {}

    /**
     * Costruttore completo.
     *
     * @param nome         Nome
     * @param cognome      Cognome
     * @param username     Username univoco
     * @param passwordHash Hash SHA-256 della password (64 char hex)
     * @param dataNascita  Data di nascita (opzionale, può essere {@code null})
     * @param domicilio    Luogo di domicilio
     * @param ruolo        Ruolo: {@code "cliente"} oppure {@code "ristoratore"}
     */
    public Utente(String nome, String cognome, String username,
                  String passwordHash, String dataNascita,
                  String domicilio, String ruolo) {
        this.nome         = nome;
        this.cognome      = cognome;
        this.username     = username;
        this.passwordHash = passwordHash;
        this.dataNascita  = dataNascita;
        this.domicilio    = domicilio;
        this.ruolo        = ruolo;
    }

    // -------------------------------------------------------------------------
    // Getter e Setter
    // -------------------------------------------------------------------------

    /** @return nome dell'utente */
    public String getNome() { return nome; }

    /** @param nome nome da impostare */
    public void setNome(String nome) { this.nome = nome; }

    /** @return cognome dell'utente */
    public String getCognome() { return cognome; }

    /** @param cognome cognome da impostare */
    public void setCognome(String cognome) { this.cognome = cognome; }

    /** @return username univoco */
    public String getUsername() { return username; }

    /** @param username username da impostare */
    public void setUsername(String username) { this.username = username; }

    /** @return hash SHA-256 della password */
    public String getPasswordHash() { return passwordHash; }

    /** @param passwordHash hash da impostare */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    /** @return data di nascita (può essere {@code null}) */
    public String getDataNascita() { return dataNascita; }

    /** @param dataNascita data di nascita da impostare */
    public void setDataNascita(String dataNascita) { this.dataNascita = dataNascita; }

    /** @return domicilio dell'utente */
    public String getDomicilio() { return domicilio; }

    /** @param domicilio domicilio da impostare */
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    /** @return ruolo: {@code "cliente"} o {@code "ristoratore"} */
    public String getRuolo() { return ruolo; }

    /** @param ruolo ruolo da impostare */
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    /**
     * @return {@code true} se il ruolo è {@code "cliente"}
     */
    public boolean isCliente() {
        return "cliente".equalsIgnoreCase(ruolo);
    }

    /**
     * @return {@code true} se il ruolo è {@code "ristoratore"}
     */
    public boolean isRistoratore() {
        return "ristoratore".equalsIgnoreCase(ruolo);
    }

    /**
     * Azzera il campo password prima di inviare l'oggetto al client.
     * Chiamato dal server prima di costruire la {@link it.uninsubria.theknife.common.Response}.
     */
    public void rimuoviPassword() {
        this.passwordHash = null;
    }

    @Override
    public String toString() {
        return "Utente{username='" + username + "', nome='" + nome +
               "', cognome='" + cognome + "', ruolo='" + ruolo +
               "', domicilio='" + domicilio + "'}";
    }
}