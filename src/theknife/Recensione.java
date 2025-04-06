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
        this.stelle = stelle;
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
}
