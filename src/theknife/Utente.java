/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello utente.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife;

/**
 * Rappresenta un utente del sistema, che può essere un cliente o un ristoratore.
 * Gli utenti hanno attributi personali, credenziali e un ruolo specifico.
 * <p>
 * Ruoli possibili:
 * <ul>
 *   <li>cliente</li>
 *   <li>ristoratore</li>
 * </ul>
 */
public class Utente {
    private String nome;
    private String cognome;
    private String username;
    private String passwordCifrata;
    private String dataNascita;
    private String domicilio;
    private String ruolo;

    /**
     * Costruttore vuoto richiesto per operazioni di serializzazione o riflessione.
     */
    public Utente() {
    }

    /**
     * Costruttore completo dell'utente.
     *
     * @param nome            Nome dell'utente
     * @param cognome         Cognome dell'utente
     * @param username        Username univoco dell'utente
     * @param passwordCifrata Password cifrata in SHA-256
     * @param dataNascita     Data di nascita in formato stringa
     * @param domicilio       Indirizzo di residenza
     * @param ruolo           Ruolo dell'utente ("cliente" o "ristoratore")
     */
    public Utente(String nome, String cognome, String username,
                  String passwordCifrata, String dataNascita,
                  String domicilio, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.passwordCifrata = passwordCifrata;
        this.dataNascita = dataNascita;
        this.domicilio = domicilio;
        this.ruolo = ruolo;
    }

    /** @return il nome dell'utente */
    public String getNome() {
        return nome;
    }

    /** @param nome il nome da impostare */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return il cognome dell'utente */
    public String getCognome() {
        return cognome;
    }

    /** @param cognome il cognome da impostare */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /** @return l'username dell'utente */
    public String getUsername() {
        return username;
    }

    /** @param username lo username da impostare */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return la password cifrata dell'utente */
    public String getPasswordCifrata() {
        return passwordCifrata;
    }

    /** @param passwordCifrata la password cifrata da impostare */
    public void setPasswordCifrata(String passwordCifrata) {
        this.passwordCifrata = passwordCifrata;
    }

    /** @return la data di nascita dell'utente */
    public String getDataNascita() {
        return dataNascita;
    }

    /** @param dataNascita la data di nascita da impostare */
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    /** @return il domicilio dell'utente */
    public String getDomicilio() {
        return domicilio;
    }

    /** @param domicilio il domicilio da impostare */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /** @return il ruolo dell'utente ("cliente" o "ristoratore") */
    public String getRuolo() {
        return ruolo;
    }

    /** @param ruolo il ruolo da impostare */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * Verifica se l'utente è un cliente.
     *
     * @return true se il ruolo è "cliente", false altrimenti
     */
    public boolean isCliente() {
        return "cliente".equalsIgnoreCase(ruolo);
    }

    /**
     * Verifica se l'utente è un ristoratore.
     *
     * @return true se il ruolo è "ristoratore", false altrimenti
     */
    public boolean isRistoratore() {
        return "ristoratore".equalsIgnoreCase(ruolo);
    }

    /**
     * Rappresentazione testuale dell'oggetto Utente per scopi di debug o logging.
     *
     * @return stringa che rappresenta l'utente
     */
    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", dataNascita='" + dataNascita + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}
