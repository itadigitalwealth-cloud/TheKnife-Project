/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello ristorante.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife;

/**
 * Rappresenta un ristorante nel sistema.
 * Ogni ristorante è identificato da dati anagrafici, geografici, funzionali e di gestione.
 * È associato a un ristoratore tramite il campo "proprietario".
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

    /**
     * Costruttore vuoto richiesto per operazioni di serializzazione o inizializzazione posticipata.
     */
    public Ristorante() {
    }

    /**
     * Costruttore completo che inizializza tutti gli attributi di un ristorante.
     *
     * @param nome         Nome del ristorante
     * @param nazione      Nazione in cui si trova
     * @param citta        Città di ubicazione
     * @param indirizzo    Indirizzo completo
     * @param lat          Latitudine geografica
     * @param lon          Longitudine geografica
     * @param fasciaPrezzo Fascia di prezzo media (valore numerico)
     * @param delivery     Disponibilità di servizio di consegna a domicilio
     * @param prenotazione Possibilità di prenotare online
     * @param tipoCucina   Tipo di cucina (es. italiana, cinese, ecc.)
     * @param proprietario Username del ristoratore proprietario
     */
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

    /** @return il nome del ristorante */
    public String getNome() {
        return nome;
    }

    /** @param nome il nome da impostare */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return la nazione in cui si trova il ristorante */
    public String getNazione() {
        return nazione;
    }

    /** @param nazione la nazione da impostare */
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    /** @return la città del ristorante */
    public String getCitta() {
        return citta;
    }

    /** @param citta la città da impostare */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /** @return l'indirizzo completo del ristorante */
    public String getIndirizzo() {
        return indirizzo;
    }

    /** @param indirizzo l'indirizzo da impostare */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /** @return la latitudine geografica */
    public double getLatitudine() {
        return latitudine;
    }

    /** @param latitudine la latitudine da impostare */
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    /** @return la longitudine geografica */
    public double getLongitudine() {
        return longitudine;
    }

    /** @param longitudine la longitudine da impostare */
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    /** @return fascia di prezzo media come valore numerico */
    public double getFasciaPrezzo() {
        return fasciaPrezzo;
    }

    /** @param fasciaPrezzo la fascia di prezzo da impostare */
    public void setFasciaPrezzo(double fasciaPrezzo) {
        this.fasciaPrezzo = fasciaPrezzo;
    }

    /** @return true se il ristorante offre delivery, false altrimenti */
    public boolean isDelivery() {
        return delivery;
    }

    /** @param delivery imposta la disponibilità di servizio a domicilio */
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    /** @return true se è possibile prenotare online, false altrimenti */
    public boolean isPrenotazione() {
        return prenotazione;
    }

    /** @param prenotazione imposta la possibilità di prenotazione */
    public void setPrenotazione(boolean prenotazione) {
        this.prenotazione = prenotazione;
    }

    /** @return tipo di cucina del ristorante */
    public String getTipoCucina() {
        return tipoCucina;
    }

    /** @param tipoCucina il tipo di cucina da impostare */
    public void setTipoCucina(String tipoCucina) {
        this.tipoCucina = tipoCucina;
    }

    /** @return username del proprietario del ristorante */
    public String getProprietario() {
        return proprietario;
    }

    /** @param proprietario username del ristoratore da associare */
    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Rappresentazione testuale dell'oggetto Ristorante.
     *
     * @return stringa descrittiva del ristorante
     */
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
