/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello searchpanel.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import theknife.GestoreFile;
import theknife.Ristorante;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

/**
 * <h2>SearchPanel</h2>
 *
 * <p>Pannello grafico che permette la <strong>ricerca avanzata</strong>
 * dei ristoranti secondo molteplici criteri:</p>
 *
 * <ul>
 *   <li>Nome (anche parziale)</li>
 *   <li>Città</li>
 *   <li>Tipologia di cucina</li>
 *   <li>Prezzo massimo</li>
 *   <li>Media minima delle stelle ottenute</li>
 *   <li>Servizio di delivery</li>
 *   <li>Servizio di prenotazione online</li>
 * </ul>
 *
 * <p>All’avvio mostra l’elenco completo dei ristoranti caricati
 * da <em>data/ristoranti.csv</em>; in seguito filtra il risultato in base
 * ai campi impostati dall’utente.</p>
 *
 * @author TheKnife Team
 */
public class SearchPanel extends GradientPanel {

    /* ------------------------------------------------------------------ */
    /* Campi GUI                                                          */
    /* ------------------------------------------------------------------ */

    private final FancyFrame parent;

    private final JTextField txtNome   = new JTextField(10);
    private final JTextField txtCitta  = new JTextField(10);
    private final JTextField txtCucina = new JTextField(10);
    private final JTextField txtPrezzo = new JTextField(5);
    private final JTextField txtMedia  = new JTextField(5);

    private final JCheckBox chkDelivery     = new JCheckBox("Delivery?");
    private final JCheckBox chkPrenotazione = new JCheckBox("Prenotazione?");

    private final JPanel resultsPanel = new JPanel();

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Crea il pannello e visualizza immediatamente l’elenco completo
     * dei ristoranti disponibili.
     *
     * @param parent riferimento al frame principale
     */
    public SearchPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        initUI();
        mostraTuttiRistoranti();
    }

    /* ------------------------------------------------------------------ */
    /* Layout iniziale                                                    */
    /* ------------------------------------------------------------------ */

    /** Inizializza i componenti grafici e i listener. */
    private void initUI() {
        setLayout(new BorderLayout());

        /* ---------- Pannello filtri ------------------------------------ */
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Nome (parziale):"));
        topPanel.add(txtNome);

        topPanel.add(new JLabel("Città:"));
        topPanel.add(txtCitta);

        topPanel.add(new JLabel("Cucina:"));
        topPanel.add(txtCucina);

        topPanel.add(new JLabel("Max Prezzo:"));
        topPanel.add(txtPrezzo);

        topPanel.add(new JLabel("Min Media Stelle:"));
        topPanel.add(txtMedia);

        topPanel.add(chkDelivery);
        topPanel.add(chkPrenotazione);

        JButton btnCerca = new JButton("Cerca");
        btnCerca.addActionListener(e -> eseguiRicerca());
        topPanel.add(btnCerca);

        add(topPanel, BorderLayout.NORTH);

        /* ---------- Area risultati ------------------------------------ */
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);
    }

    /* ------------------------------------------------------------------ */
    /* Visualizzazione lista completa                                     */
    /* ------------------------------------------------------------------ */

    /** Stampa la lista completa dei ristoranti senza applicare filtri. */
    private void mostraTuttiRistoranti() {
        resultsPanel.removeAll();

        List<Ristorante> tutti = GestoreFile.caricaRistoranti("data/ristoranti.csv");

        JLabel lblIntro = new JLabel("Questi sono i ristoranti disponibili:");
        lblIntro.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultsPanel.add(lblIntro);

        tutti.forEach(this::creaBottoneRistorante);

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    /* ------------------------------------------------------------------ */
    /* Ricerca filtrata                                                   */
    /* ------------------------------------------------------------------ */

    /** Esegue la ricerca applicando i criteri impostati nei campi input. */
    private void eseguiRicerca() {
        resultsPanel.removeAll();

        /* -------- Lettura e validazione campi numerici ---------------- */
        Double maxPrezzo = parseDouble(txtPrezzo.getText().trim(), "Prezzo massimo non valido.");
        if (maxPrezzo == null && !txtPrezzo.getText().trim().isEmpty()) return;

        Double mediaMin = parseDouble(txtMedia.getText().trim(), "Media stelle non valida.");
        if (mediaMin == null && !txtMedia.getText().trim().isEmpty()) return;

        /* -------- Ricerca -------------------------------------------- */
        List<Ristorante> risultati = GestoreFile.cercaRistoranti(
                "data/ristoranti.csv",
                txtNome.getText().trim(),
                txtCitta.getText().trim(),
                txtCucina.getText().trim(),
                maxPrezzo,
                chkDelivery.isSelected(),
                chkPrenotazione.isSelected(),
                mediaMin);

        /* -------- Visualizzazione ------------------------------------ */
        if (risultati.isEmpty()) {
            JLabel lblNo = new JLabel("Nessun ristorante trovato con i filtri selezionati.");
            lblNo.setAlignmentX(Component.LEFT_ALIGNMENT);
            resultsPanel.add(lblNo);
        } else {
            risultati.forEach(this::creaBottoneRistorante);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    /* ------------------------------------------------------------------ */
    /* Utils                                                              */
    /* ------------------------------------------------------------------ */

    /**
     * Converte una stringa in {@code Double}. Se la stringa è vuota restituisce
     * {@code null}; se la conversione fallisce mostra un messaggio d’errore e
     * restituisce {@code null}.
     */
    private Double parseDouble(String txt, String errMsg) {
        if (txt.isEmpty()) return null;
        try {
            return Double.parseDouble(txt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, errMsg,
                                          "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Crea e aggiunge un pulsante per il ristorante fornito. Al click apre
     * il {@link theknife.gui.panels.RestaurantDetailPanel}.
     *
     * @param r ristorante da visualizzare
     */
    private void creaBottoneRistorante(Ristorante r) {
        JButton btn = new JButton("%s - %s - %s"
                .formatted(r.getNome(), r.getCitta(), r.getTipoCucina()));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(ev -> {
            parent.getDetailPanel().setRistorante(r);
            parent.showCard(FancyFrame.CARD_DETAIL);
        });
        resultsPanel.add(btn);
    }

    /* ------------------------------------------------------------------ */
    /* Reset pannello                                                     */
    /* ------------------------------------------------------------------ */

    /** Ripristina i filtri e mostra di nuovo l’elenco completo. */
    public void refresh() {
        txtNome.setText("");
        txtCitta.setText("");
        txtCucina.setText("");
        txtPrezzo.setText("");
        txtMedia.setText("");
        chkDelivery.setSelected(false);
        chkPrenotazione.setSelected(false);

        resultsPanel.removeAll();
        mostraTuttiRistoranti();
    }
}
