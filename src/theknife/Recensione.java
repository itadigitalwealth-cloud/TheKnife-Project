package theknife;

/**
 * Modello recensione
 */
public class Recensione {
    private String idRistorante; // nome del ristorante
    private String username; // autore (cliente)
    private int stelle; // 1..5
    private String testo;
    private String risposta; // ristoratore risponde

    public Recensione() {
    }

    public Recensione(String idRistorante, String username,
                      int stelle, String testo, String risposta) {
        this.idRistorante = idRistorante;
        this.username = username;
        setStelle(stelle); // validazione applicata
        this.testo = testo;
        this.risposta = risposta;
    }

    public String getIdRistorante() {
        return idRistorante;
    }

    public void setIdRistorante(String idRistorante) {
        this.idRistorante = idRistorante;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStelle() {
        return stelle;
    }

    public void setStelle(int stelle) {
        if (stelle < 1 || stelle > 5)
            throw new IllegalArgumentException("Il numero di stelle deve essere tra 1 e 5.");
        this.stelle = stelle;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public boolean hasRisposta() {
        return risposta != null && !risposta.trim().isEmpty();
    }

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
