/**
 * TheKnife – Modulo Client
 * Pannello gestione ristoranti del ristoratore.
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
 * Pannello riservato al ristoratore per la gestione dei propri locali.
 * <p>
 * Implementa le funzioni {@code aggiungiRistorante()} e
 * {@code visualizzaRiepilogo()} delle specifiche.
 * Mostra la lista dei propri ristoranti con media stelle e numero
 * recensioni; al click su un ristorante apre il pannello di dettaglio.
 * </p>
 */
public class RistorantiPanel extends GradientPanel {

    private final FancyFrame parent;
    private final JPanel     resultsPanel = new JPanel();
    private final JButton    btnAggiungi  = new JButton("+ Aggiungi Ristorante");

    /**
     * @param parent frame principale
     */
    public RistorantiPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("I Miei Ristoranti", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(btnAggiungi);
        add(bottom, BorderLayout.SOUTH);

        btnAggiungi.addActionListener(e -> mostraDialogNuovoRistorante());
    }

    /**
     * Carica dal server il riepilogo dei ristoranti del ristoratore loggato
     * (implementa {@code visualizzaRiepilogo()}).
     */
    public void refreshData() {
        resultsPanel.removeAll();

        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isRistoratore()) {
            resultsPanel.add(new JLabel("Devi essere loggato come ristoratore."));
            resultsPanel.revalidate();
            resultsPanel.repaint();
            return;
        }

        try {
            Request req = new Request(CommandType.RISTORATORE_VISUALIZZA_RIEPILOGO,
                                      ClientTK.getUtenteLoggato().getUsername());
            Response resp = ClientTK.getConnessione().invia(req);

            if (resp.isSuccesso()) {
                List<Ristorante> lista = resp.getDatoTipizzato();
                if (lista.isEmpty()) {
                    resultsPanel.add(new JLabel("Non hai ancora aggiunto ristoranti."));
                } else {
                    lista.forEach(this::addRistoranteButton);
                }
            } else {
                resultsPanel.add(new JLabel("Errore: " + resp.getMessaggio()));
            }
        } catch (Exception ex) {
            resultsPanel.add(new JLabel("Errore di connessione: " + ex.getMessage()));
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    /**
     * Dialog per l'inserimento di un nuovo ristorante
     * (implementa {@code aggiungiRistorante()}).
     */
    private void mostraDialogNuovoRistorante() {
        JTextField txtNome      = new JTextField();
        JTextField txtNazione   = new JTextField("Italia");
        JTextField txtCitta     = new JTextField();
        JTextField txtIndirizzo = new JTextField();
        JTextField txtLat       = new JTextField("0.0");
        JTextField txtLon       = new JTextField("0.0");
        JTextField txtPrezzo    = new JTextField("20.0");
        JCheckBox  chkDel       = new JCheckBox("Delivery?");
        JCheckBox  chkPren      = new JCheckBox("Prenotazione?");
        JTextField txtCucina    = new JTextField("Italiana");

        Object[] msg = {
            "Nome:",          txtNome,
            "Nazione:",       txtNazione,
            "Città:",         txtCitta,
            "Indirizzo:",     txtIndirizzo,
            "Latitudine:",    txtLat,
            "Longitudine:",   txtLon,
            "Fascia Prezzo:", txtPrezzo,
            chkDel, chkPren,
            "Tipo Cucina:",   txtCucina
        };

        if (JOptionPane.showConfirmDialog(this, msg, "Nuovo Ristorante",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        try {
            double lat    = Double.parseDouble(txtLat.getText().trim());
            double lon    = Double.parseDouble(txtLon.getText().trim());
            double prezzo = Double.parseDouble(txtPrezzo.getText().trim());

            Request req = new Request(CommandType.RISTORATORE_AGGIUNGI_RISTORANTE,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nome",         txtNome.getText().trim())
                    .aggiungiParametro("nazione",      txtNazione.getText().trim())
                    .aggiungiParametro("citta",        txtCitta.getText().trim())
                    .aggiungiParametro("indirizzo",    txtIndirizzo.getText().trim())
                    .aggiungiParametro("latitudine",   lat)
                    .aggiungiParametro("longitudine",  lon)
                    .aggiungiParametro("fasciaPrezzo", prezzo)
                    .aggiungiParametro("delivery",     chkDel.isSelected())
                    .aggiungiParametro("prenotazione", chkPren.isSelected())
                    .aggiungiParametro("tipoCucina",   txtCucina.getText().trim());

            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio(),
                    resp.isSuccesso() ? "OK" : "Errore",
                    resp.isSuccesso()
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE);
            if (resp.isSuccesso()) refreshData();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Latitudine, longitudine e prezzo devono essere numeri validi.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore di connessione: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Crea e aggiunge un pulsante per ogni ristorante del riepilogo. */
    private void addRistoranteButton(Ristorante r) {
        String etichetta = "%s  –  %s  –  %s  ⭐ %.1f (%d rec.)"
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
}