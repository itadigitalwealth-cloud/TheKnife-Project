package theknife;

/**
 * Modello utente
 * Ruoli: "cliente" o "ristoratore"
 */
public class Utente {
    private String nome;
    private String cognome;
    private String username;
    private String passwordCifrata;
    private String dataNascita;
    private String domicilio;
    private String ruolo;

    public Utente() {
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordCifrata() {
        return passwordCifrata;
    }

    public void setPasswordCifrata(String passwordCifrata) {
        this.passwordCifrata = passwordCifrata;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public boolean isCliente() {
        return "cliente".equalsIgnoreCase(ruolo);
    }

    public boolean isRistoratore() {
        return "ristoratore".equalsIgnoreCase(ruolo);
    }

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
