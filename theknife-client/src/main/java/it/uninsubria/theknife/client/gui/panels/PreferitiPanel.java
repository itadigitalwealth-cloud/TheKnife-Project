/**
 * TheKnife – Modulo Client
 * Pannello preferiti cliente.
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
 * Pannello per la gestione dei ristoranti preferiti del cliente.
 * <p>
 * Implementa le funzioni {@code aggiungiPreferito()},
 * {@code rimuoviPreferito()} e {@code visualizzaPreferiti()}
 * delle specifiche, delegando ogni operazione al server.
 * </p>
 */
public class PreferitiPanel extends GradientPanel {

    private final FancyFrame parent;
    private final JTextArea  textArea    = new JTextArea();
    private final JButton    btnAggiungi = new JButton("+ Aggiungi Preferito");
    private final JButton    btnRimuovi  = new JButton("− Rimuovi Preferito");

    /** Lista corrente dei ristoranti preferiti (per il dialog di rimozione). */
    private List<Ristorante> preferitiCorrente = List.of();

    /**
     * @param parent frame principale
     */
    public PreferitiPanel(FancyFrame parent) {
        super(new Color(220, 220, 220), new Color(200, 200, 200));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("I Miei Preferiti", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(btnAggiungi);
        bottom.add(btnRimuovi);
        add(bottom, BorderLayout.SOUTH);

        btnAggiungi.addActionListener(e -> aggiungiPreferito());
        btnRimuovi .addActionListener(e -> rimuoviPreferito());
    }

    /**
     * Ricarica la lista dei preferiti dal server.
     */
    public void refreshData() {
        textArea.setText("");
        preferitiCorrente = List.of();

        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isCliente()) {
            textArea.setText("Devi essere loggato come cliente per vedere i preferiti.");
            return;
        }

        try {
            Request req = new Request(CommandType.CLIENTE_VISUALIZZA_PREFERITI,
                                      ClientTK.getUtenteLoggato().getUsername());
            Response resp = ClientTK.getConnessione().invia(req);

            if (resp.isSuccesso()) {
                preferitiCorrente = resp.getDatoTipizzato();
                stampaPreferiti(preferitiCorrente);
            } else {
                textArea.setText("Errore: " + resp.getMessaggio());
            }
        } catch (Exception ex) {
            textArea.setText("Errore di connessione: " + ex.getMessage());
        }
    }

    private void stampaPreferiti(List<Ristorante> lista) {
        if (lista.isEmpty()) {
            textArea.setText("Non hai ancora ristoranti preferiti.\nUsil pulsante «+ Aggiungi» per aggiungerne uno.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== I Miei Preferiti ===\n\n");
        for (Ristorante r : lista) {
            sb.append(String.format("%-25s  %s  –  %s  (%.0f€)  ⭐ %.1f%n",
                    r.getNome(), r.getCitta(), r.getTipoCucina(),
                    r.getFasciaPrezzo(), r.getMediaStelle()));
        }
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0);
    }

    /** Dialog per aggiungere un ristorante ai preferiti tramite il nome. */
    private void aggiungiPreferito() {
        String nome = JOptionPane.showInputDialog(this,
                "Nome del ristorante da aggiungere ai preferiti:",
                "Aggiungi Preferito", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.isBlank()) return;

        try {
            Request req = new Request(CommandType.CLIENTE_AGGIUNGI_PREFERITO,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante", nome.trim());
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio(),
                    resp.isSuccesso() ? "OK" : "Attenzione",
                    resp.isSuccesso()
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.WARNING_MESSAGE);
            if (resp.isSuccesso()) refreshData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Dialog per rimuovere un ristorante dalla lista tramite selezione. */
    private void rimuoviPreferito() {
        if (preferitiCorrente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai ristoranti preferiti da rimuovere.");
            return;
        }

        JComboBox<String> combo = new JComboBox<>(
                preferitiCorrente.stream()
                                 .map(Ristorante::getNome)
                                 .toArray(String[]::new));

        if (JOptionPane.showConfirmDialog(this, combo,
                "Rimuovi Preferito", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        String scelto = (String) combo.getSelectedItem();
        try {
            Request req = new Request(CommandType.CLIENTE_RIMUOVI_PREFERITO,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante", scelto);
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio());
            if (resp.isSuccesso()) refreshData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}