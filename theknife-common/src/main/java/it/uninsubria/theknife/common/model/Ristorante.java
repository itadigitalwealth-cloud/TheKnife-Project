/**
 * TheKnife – Modulo Common
 * Classe di modello ristorante.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */

package it.uninsubria.theknife.common.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Rappresenta un ristorante nel sistema TheKnife.
 * <p>
 * Questa classe è condivisa tra client e server (modulo {@code theknife-common}).
 * Implementa {@link Serializable} per permettere la trasmissione tramite
 * {@link java.io.ObjectOutputStream} sul socket TCP.
 * </p>
 */
public class Ristorante implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

    // Campi calcolati dal server (non persistiti direttamente, popolati nelle query)
    /** Media delle stelle calcolata dal server tramite AVG su PostgreSQL. */
    private double mediaStelle;

    /** Numero totale di recensioni calcolato dal server tramite COUNT. */
    private int numeroRecensioni;

    /** Costruttore vuoto richiesto per deserializzazione. */
    public Ristorante() {}

    /**
     * Costruttore completo.
     *
     * @param nome         Nome del ristorante
     * @param nazione      Nazione
     * @param citta        Città
     * @param indirizzo    Indirizzo completo
     * @param latitudine   Latitudine geografica
     * @param longitudine  Longitudine geografica
     * @param fasciaPrezzo Prezzo medio in euro
     * @param delivery     Disponibilità servizio di consegna
     * @param prenotazione Disponibilità prenotazione online
     * @param tipoCucina   Tipo di cucina
     * @param proprietario Username del ristoratore proprietario
     */
    public Ristorante(String nome, String nazione, String citta,
                      String indirizzo, double latitudine, double longitudine,
                      double fasciaPrezzo, boolean delivery, boolean prenotazione,
                      String tipoCucina, String proprietario) {
        this.nome         = nome;
        this.nazione      = nazione;
        this.citta        = citta;
        this.indirizzo    = indirizzo;
        this.latitudine   = latitudine;
        this.longitudine  = longitudine;
        this.fasciaPrezzo = fasciaPrezzo;
        this.delivery     = delivery;
        this.prenotazione = prenotazione;
        this.tipoCucina   = tipoCucina;
        this.proprietario = proprietario;
    }

    // -------------------------------------------------------------------------
    // Getter e Setter
    // -------------------------------------------------------------------------

    /** @return nome del ristorante */
    public String getNome() { return nome; }

    /** @param nome nome da impostare */
    public void setNome(String nome) { this.nome = nome; }

    /** @return nazione */
    public String getNazione() { return nazione; }

    /** @param nazione nazione da impostare */
    public void setNazione(String nazione) { this.nazione = nazione; }

    /** @return città */
    public String getCitta() { return citta; }

    /** @param citta città da impostare */
    public void setCitta(String citta) { this.citta = citta; }

    /** @return indirizzo completo */
    public String getIndirizzo() { return indirizzo; }

    /** @param indirizzo indirizzo da impostare */
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    /** @return latitudine geografica */
    public double getLatitudine() { return latitudine; }

    /** @param latitudine latitudine da impostare */
    public void setLatitudine(double latitudine) { this.latitudine = latitudine; }

    /** @return longitudine geografica */
    public double getLongitudine() { return longitudine; }

    /** @param longitudine longitudine da impostare */
    public void setLongitudine(double longitudine) { this.longitudine = longitudine; }

    /** @return fascia di prezzo media in euro */
    public double getFasciaPrezzo() { return fasciaPrezzo; }

    /** @param fasciaPrezzo prezzo medio da impostare */
    public void setFasciaPrezzo(double fasciaPrezzo) { this.fasciaPrezzo = fasciaPrezzo; }

    /** @return {@code true} se il ristorante offre delivery */
    public boolean isDelivery() { return delivery; }

    /** @param delivery disponibilità delivery da impostare */
    public void setDelivery(boolean delivery) { this.delivery = delivery; }

    /** @return {@code true} se è possibile prenotare online */
    public boolean isPrenotazione() { return prenotazione; }

    /** @param prenotazione disponibilità prenotazione da impostare */
    public void setPrenotazione(boolean prenotazione) { this.prenotazione = prenotazione; }

    /** @return tipo di cucina */
    public String getTipoCucina() { return tipoCucina; }

    /** @param tipoCucina tipo cucina da impostare */
    public void setTipoCucina(String tipoCucina) { this.tipoCucina = tipoCucina; }

    /** @return username del ristoratore proprietario */
    public String getProprietario() { return proprietario; }

    /** @param proprietario proprietario da impostare */
    public void setProprietario(String proprietario) { this.proprietario = proprietario; }

    /**
     * @return media delle stelle (0.0 se nessuna recensione).
     *         Campo popolato dal server, non presente nel DB come colonna.
     */
    public double getMediaStelle() { return mediaStelle; }

    /** @param mediaStelle media stelle da impostare */
    public void setMediaStelle(double mediaStelle) { this.mediaStelle = mediaStelle; }

    /**
     * @return numero totale di recensioni.
     *         Campo popolato dal server, non presente nel DB come colonna.
     */
    public int getNumeroRecensioni() { return numeroRecensioni; }

    /** @param numeroRecensioni numero recensioni da impostare */
    public void setNumeroRecensioni(int numeroRecensioni) { this.numeroRecensioni = numeroRecensioni; }

    @Override
    public String toString() {
        return "Ristorante{nome='" + nome + "', citta='" + citta +
               "', nazione='" + nazione + "', fasciaPrezzo=" + fasciaPrezzo +
               ", tipoCucina='" + tipoCucina + "', mediaStelle=" + mediaStelle +
               ", numeroRecensioni=" + numeroRecensioni + '}';
    }
}