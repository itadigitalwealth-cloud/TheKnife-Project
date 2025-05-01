package theknife;

/**
 * Modello ristorante
 * 11 campi, l'ultimo "proprietario" = username del ristoratore
 */
public class Ristorante {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private double latitudine;
    private double longitudine;
    private double fasciaPrezzo;
    private boolean delivery;
    private boolean prenotazione;
    private String tipoCucina;
    private String proprietario;

    public Ristorante() {
    }

    public Ristorante(String nome, String nazione, String citta,
                      String indirizzo, double lat, double lon,
                      double fasciaPrezzo, boolean delivery,
                      boolean prenotazione, String tipoCucina,
                      String proprietario) {
        this.nome = nome;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.latitudine = lat;
        this.longitudine = lon;
        this.fasciaPrezzo = fasciaPrezzo;
        this.delivery = delivery;
        this.prenotazione = prenotazione;
        this.tipoCucina = tipoCucina;
        this.proprietario = proprietario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public double getFasciaPrezzo() {
        return fasciaPrezzo;
    }

    public void setFasciaPrezzo(double fasciaPrezzo) {
        this.fasciaPrezzo = fasciaPrezzo;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(boolean prenotazione) {
        this.prenotazione = prenotazione;
    }

    public String getTipoCucina() {
        return tipoCucina;
    }

    public void setTipoCucina(String tipoCucina) {
        this.tipoCucina = tipoCucina;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return "Ristorante{" +
                "nome='" + nome + '\'' +
                ", nazione='" + nazione + '\'' +
                ", citta='" + citta + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", fasciaPrezzo=" + fasciaPrezzo +
                ", delivery=" + delivery +
                ", prenotazione=" + prenotazione +
                ", tipoCucina='" + tipoCucina + '\'' +
                ", proprietario='" + proprietario + '\'' +
                '}';
    }
} 
