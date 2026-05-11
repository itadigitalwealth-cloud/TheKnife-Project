/**
 * TheKnife – Modulo Client
 * Pannello di ricerca ristoranti.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client.gui.panels;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.client.gui.FancyFrame;
import it.uninsubria.theknife.client.gui.GradientPanel;
import it.uninsubria.theknife.common.CommandType;
import it.uninsubria.theknife.common.Request;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello per la ricerca avanzata dei ristoranti.
 * <p>
 * Criteri di ricerca supportati (corrispondono alla funzione
 * {@code cercaRistorante()} delle specifiche):
 * <ul>
 *   <li>Città (obbligatoria)</li>
 *   <li>Tipo cucina</li>
 *   <li>Prezzo minimo e massimo</li>
 *   <li>Disponibilità delivery</li>
 *   <li>Disponibilità prenotazione</li>
 *   <li>Media stelle minima</li>
 * </ul>
 * </p>
 *
 * <p>
 * A differenza della parte A, la ricerca senza città non è supportata
 * (il server richiede almeno la città come filtro obbligatorio).
 * I risultati vengono recuperati dal server tramite
 * {@link CommandType#CERCA_RISTORANTI}.
 * </p>
 */
public class SearchPanel extends GradientPanel {

    private final FancyFrame parent;

    /* Campi filtro -------------------------------------------------------- */
    private final JTextField  txtCitta       = new JTextField(10);
    private final JTextField  txtCucina      = new JTextField(10);
    private final JTextField  txtPrezzoMin   = new JTextField(5);
    private final JTextField  txtPrezzoMax   = new JTextField(5);
    private final JTextField  txtStelleMin   = new JTextField(5);
    private final JCheckBox   chkDelivery    = new JCheckBox("Delivery");
    private final JCheckBox   chkPrenotazione = new JCheckBox("Prenotazione");

    /** Pannello scrollabile dei risultati. */
    private final JPanel resultsPanel = new JPanel();

    /**
     * Crea il pannello di ricerca.
     *
     * @param parent frame principale
     */
    public SearchPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        initUI();
    }

    /** Costruisce i componenti e i listener. */
    private void initUI() {
        setLayout(new BorderLayout());

        /* ---- Barra dei filtri ----------------------------------------- */
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));

        topPanel.add(new JLabel("Città*:"));
        topPanel.add(txtCitta);

        topPanel.add(new JLabel("Cucina:"));
        topPanel.add(txtCucina);

        topPanel.add(new JLabel("Prezzo min:"));
        topPanel.add(txtPrezzoMin);

        topPanel.add(new JLabel("Prezzo max:"));
        topPanel.add(txtPrezzoMax);

        topPanel.add(new JLabel("Stelle min:"));
        topPanel.add(txtStelleMin);

        topPanel.add(chkDelivery);
        topPanel.add(chkPrenotazione);

        JButton btnCerca = new JButton("Cerca");
        btnCerca.addActionListener(e -> eseguiRicerca());
        topPanel.add(btnCerca);

        add(topPanel, BorderLayout.NORTH);

        /* ---- Area risultati ------------------------------------------ */
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);

        // Messaggio iniziale
        mostraMessaggioIniziale();
    }

    /** Mostra un messaggio che invita ad inserire la città. */
    private void mostraMessaggioIniziale() {
        resultsPanel.removeAll();
        JLabel lbl = new JLabel("Inserisci una città e premi Cerca per trovare ristoranti.");
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultsPanel.add(Box.createVerticalStrut(20));
        resultsPanel.add(lbl);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    /** Invia la richiesta di ricerca al server e visualizza i risultati. */
    private void eseguiRicerca() {
        String citta = txtCitta.getText().trim();
        if (citta.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La città è obbligatoria per la ricerca.",
                    "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lettura e validazione campi numerici opzionali
        double prezzoMin  = parseDouble(txtPrezzoMin.getText().trim(),  "Prezzo minimo non valido.");
        double prezzoMax  = parseDouble(txtPrezzoMax.getText().trim(),  "Prezzo massimo non valido.");
        double stelleMin  = parseDouble(txtStelleMin.getText().trim(),  "Stelle minime non valide.");
        if (prezzoMin < 0 || prezzoMax < 0 || stelleMin < 0) return; // errore già mostrato

        // Costruzione richiesta
        Request req = new Request(CommandType.CERCA_RISTORANTI,
                                  ClientTK.isLoggato()
                                    ? ClientTK.getUtenteLoggato().getUsername()
                                    : null)
                .aggiungiParametro("citta",      citta)
                .aggiungiParametro("tipoCucina", txtCucina.getText().trim());

        if (prezzoMin > 0) req.aggiungiParametro("prezzoMin", prezzoMin);
        if (prezzoMax > 0) req.aggiungiParametro("prezzoMax", prezzoMax);
        if (stelleMin > 0) req.aggiungiParametro("stelleMin", stelleMin);
        if (chkDelivery.isSelected())     req.aggiungiParametro("delivery",     true);
        if (chkPrenotazione.isSelected()) req.aggiungiParametro("prenotazione", true);

        try {
            Response resp = ClientTK.getConnessione().invia(req);
            resultsPanel.removeAll();

            if (!resp.isSuccesso()) {
                JOptionPane.showMessageDialog(this, resp.getMessaggio(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                List<Ristorante> lista = resp.getDatoTipizzato();
                if (lista.isEmpty()) {
                    JLabel lbl = new JLabel("Nessun ristorante trovato con i criteri indicati.");
                    lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
                    resultsPanel.add(lbl);
                } else {
                    lista.forEach(this::creaBottoneRistorante);
                }
            }

            resultsPanel.revalidate();
            resultsPanel.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore di comunicazione col server: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aggiunge un pulsante cliccabile per ogni ristorante trovato.
     * Al click carica i dettagli dal server e apre la card di dettaglio.
     *
     * @param r ristorante da mostrare
     */
    private void creaBottoneRistorante(Ristorante r) {
        String etichetta = "%s – %s – %s  ⭐ %.1f (%d rec.)"
                .formatted(r.getNome(), r.getCitta(), r.getTipoCucina(),
                           r.getMediaStelle(), r.getNumeroRecensioni());
        JButton btn = new JButton(etichetta);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(ev -> {
            parent.getDetailPanel().setRistorante(r);
            parent.showCard(FancyFrame.CARD_DETAIL);
        });
        resultsPanel.add(btn);
    }

    /** Azzera i filtri e mostra il messaggio iniziale. */
    public void refresh() {
        txtCitta.setText("");
        txtCucina.setText("");
        txtPrezzoMin.setText("");
        txtPrezzoMax.setText("");
        txtStelleMin.setText("");
        chkDelivery.setSelected(false);
        chkPrenotazione.setSelected(false);
        mostraMessaggioIniziale();
    }

    /**
     * Converte una stringa in double.
     *
     * @param txt    testo da convertire
     * @param errMsg messaggio da mostrare in caso di errore
     * @return il valore convertito, {@code 0} se la stringa è vuota,
     *         {@code -1} in caso di errore di formato
     */
    private double parseDouble(String txt, String errMsg) {
        if (txt.isEmpty()) return 0;
        try {
            return Double.parseDouble(txt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, errMsg,
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
}