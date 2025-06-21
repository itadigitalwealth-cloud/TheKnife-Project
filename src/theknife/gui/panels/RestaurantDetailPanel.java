/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello restaurantdetailpanel.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import theknife.GestoreFile;
import theknife.Recensione;
import theknife.Ristorante;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

/**
 * Pannello che visualizza i dettagli di un singolo ristorante
 * insieme a tutte le sue recensioni.
 *
 * <p>Flusso di utilizzo:</p>
 * <ol>
 *   <li>Il frame principale richiama {@link #setRistorante(Ristorante)} quando
 *       l’utente clicca su un risultato di ricerca (o su un ristorante di
 *       proprietà).</li>
 *   <li>Il metodo {@code setRistorante} salva il riferimento e
 *       richiama {@link #refreshDetails()} per popolare l’area testuale.</li>
 *   <li>L’utente può tornare alla card di ricerca tramite il pulsante
 *       «Torna Indietro».</li>
 * </ol>
 *
 * <p>Il contenuto è renderizzato in una {@link JTextArea} con font
 * Monospaced per assicurare un allineamento pulito del testo.</p>
 *
 * @author TheKnife Team
 */
public class RestaurantDetailPanel extends GradientPanel {

    /* ------------------------------------------------------------------ */
    /* Attributi                                                          */
    /* ------------------------------------------------------------------ */

    /** Riferimento al frame principale per la gestione delle card. */
    private final FancyFrame parent;

    /** Ristorante attualmente selezionato (può essere {@code null}). */
    private Ristorante ristoranteCorrente;

    /* Componenti GUI --------------------------------------------------- */
    private final JTextArea textArea;
    private final JButton   btnIndietro;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Crea il pannello con i colori di sfondo predefiniti.
     *
     * @param parent frame principale che ospita il pannello
     */
    public RestaurantDetailPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        this.textArea   = new JTextArea();
        this.btnIndietro = new JButton("Torna Indietro");

        initUI();
    }

    /* ------------------------------------------------------------------ */
    /* Inizializzazione GUI                                               */
    /* ------------------------------------------------------------------ */

    /** Configura layout, componenti e listener. */
    private void initUI() {
        setLayout(new BorderLayout());

        /* Titolo -------------------------------------------------------- */
        JLabel lbl = new JLabel("Dettagli Ristorante", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        /* Area testo ---------------------------------------------------- */
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        /* Pulsante indietro -------------------------------------------- */
        JPanel bot = new JPanel();
        bot.add(btnIndietro);
        add(bot, BorderLayout.SOUTH);

        btnIndietro.addActionListener(e ->
                parent.showCard(FancyFrame.CARD_SEARCH));
    }

    /* ------------------------------------------------------------------ */
    /* API pubblica                                                       */
    /* ------------------------------------------------------------------ */

    /**
     * Imposta il ristorante da visualizzare e aggiorna il contenuto
     * del pannello.
     *
     * @param r ristorante selezionato; se {@code null} viene mostrato
     *          un messaggio di placeholder
     */
    public void setRistorante(Ristorante r) {
        this.ristoranteCorrente = r;
        refreshDetails();
    }

    /* ------------------------------------------------------------------ */
    /* Metodi di supporto                                                 */
    /* ------------------------------------------------------------------ */

    /** Ricalcola e mostra tutti i dettagli (info + recensioni). */
    private void refreshDetails() {
        if (ristoranteCorrente == null) {
            textArea.setText("Nessun ristorante selezionato.");
            return;
        }

        StringBuilder sb = new StringBuilder(256);

        /* Info generali ------------------------------------------------- */
        sb.append("Nome: ").append(ristoranteCorrente.getNome()).append('\n')
          .append("Città: ").append(ristoranteCorrente.getCitta()).append('\n')
          .append("Nazione: ").append(ristoranteCorrente.getNazione()).append('\n')
          .append("Indirizzo: ").append(ristoranteCorrente.getIndirizzo()).append('\n')
          .append("Fascia Prezzo: ").append(ristoranteCorrente.getFasciaPrezzo())
          .append(" €\n")
          .append("Delivery: ")
          .append(ristoranteCorrente.isDelivery() ? "sì" : "no").append('\n')
          .append("Prenotazione: ")
          .append(ristoranteCorrente.isPrenotazione() ? "sì" : "no").append('\n')
          .append("Tipo Cucina: ").append(ristoranteCorrente.getTipoCucina())
          .append('\n')
          .append("Proprietario: ").append(ristoranteCorrente.getProprietario())
          .append('\n');

        /* Media recensioni --------------------------------------------- */
        double media = GestoreFile.calcolaMediaStelle(
                "data/recensioni.csv", ristoranteCorrente.getNome());

        sb.append("\nMedia Stelle: ")
          .append(String.format("%.2f", media)).append('\n');

        /* Sezione Recensioni ------------------------------------------- */
        sb.append("\n=== Recensioni ===\n");

        List<Recensione> recs =
                GestoreFile.caricaRecensioni("data/recensioni.csv");

        recs.stream()
            .filter(rec -> rec.getIdRistorante()
                              .equalsIgnoreCase(ristoranteCorrente.getNome()))
            .forEach(rec -> {
                sb.append(" - ")
                  .append(rec.getUsername())
                  .append(" [").append(rec.getStelle()).append(" stelle]\n")
                  .append("   Testo: ").append(rec.getTesto()).append('\n');

                if (rec.hasRisposta()) {
                    sb.append("   Risposta: ")
                      .append(rec.getRisposta()).append('\n');
                }
                sb.append('\n');
            });

        textArea.setText(sb.toString());
        textArea.setCaretPosition(0); // scroll all’inizio
    }
}
