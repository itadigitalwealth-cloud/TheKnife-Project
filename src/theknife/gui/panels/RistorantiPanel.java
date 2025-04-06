package theknife.gui.panels;

import theknife.*;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class RistorantiPanel extends GradientPanel {

    private FancyFrame parent;
    private JPanel resultsPanel;
    private JButton btnAggiungi;

    public RistorantiPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Miei Ristoranti (Ristoratore)", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(resultsPanel);
        add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        btnAggiungi = new JButton("Aggiungi Ristorante");
        bottomPanel.add(btnAggiungi);

        add(bottomPanel, BorderLayout.SOUTH);

        btnAggiungi.addActionListener(e -> mostraDialogNuovoRistorante());
    }

    public void refreshData() {
        resultsPanel.removeAll();

        Utente u = parent.getUtenteCorrente();
        if (u == null || !"ristoratore".equalsIgnoreCase(u.getRuolo())) {
            JLabel lbl = new JLabel("Devi essere loggato come ristoratore.");
            resultsPanel.add(lbl);
            resultsPanel.revalidate();
            resultsPanel.repaint();
            return;
        }

        List<Ristorante> tutti = GestoreFile.caricaRistoranti("data/ristoranti.csv");
        List<Ristorante> miei = tutti.stream()
                .filter(r -> r.getProprietario() != null && r.getProprietario().equalsIgnoreCase(u.getUsername()))
                .collect(Collectors.toList());

        if (miei.isEmpty()) {
            JLabel lblNo = new JLabel("Non hai creato ristoranti ancora.");
            resultsPanel.add(lblNo);
        } else {
            for (Ristorante r : miei) {
                JButton btn = new JButton(r.getNome());
                btn.setAlignmentX(Component.LEFT_ALIGNMENT);

                btn.addActionListener(ev -> {
                    // Al click, apri detail
                    parent.getDetailPanel().setRistorante(r);
                    parent.showCard(FancyFrame.CARD_DETAIL);
                });

                resultsPanel.add(btn);
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void mostraDialogNuovoRistorante() {
        Utente u = parent.getUtenteCorrente();
        if (u == null || !"ristoratore".equalsIgnoreCase(u.getRuolo())) {
            JOptionPane.showMessageDialog(this,
                    "Devi essere ristoratore loggato!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtNome = new JTextField();
        JTextField txtNazione = new JTextField("Italia");
        JTextField txtCitta = new JTextField();
        JTextField txtIndirizzo = new JTextField();
        JTextField txtLat = new JTextField("0.0");
        JTextField txtLon = new JTextField("0.0");
        JTextField txtPrezzo = new JTextField("20.0");
        JCheckBox chkDel = new JCheckBox("Delivery?");
        JCheckBox chkPren = new JCheckBox("Prenotazione?");
        JTextField txtCucina = new JTextField("Italiana");

        Object[] msg = {
                "Nome:", txtNome,
                "Nazione:", txtNazione,
                "Città:", txtCitta,
                "Indirizzo:", txtIndirizzo,
                "Latitudine:", txtLat,
                "Longitudine:", txtLon,
                "Fascia Prezzo:", txtPrezzo,
                chkDel,
                chkPren,
                "Tipo Cucina:", txtCucina
        };
        int opt = JOptionPane.showConfirmDialog(this, msg,
                "Nuovo Ristorante", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                double latD = Double.parseDouble(txtLat.getText().trim());
                double lonD = Double.parseDouble(txtLon.getText().trim());
                double prezzo = Double.parseDouble(txtPrezzo.getText().trim());
                boolean deliv = chkDel.isSelected();
                boolean preno = chkPren.isSelected();

                Ristorante r = new Ristorante(
                        txtNome.getText().trim(),
                        txtNazione.getText().trim(),
                        txtCitta.getText().trim(),
                        txtIndirizzo.getText().trim(),
                        latD, lonD,
                        prezzo,
                        deliv, preno,
                        txtCucina.getText().trim(),
                        u.getUsername() // proprietario
                );
                GestoreFile.aggiungiRistorante(r, "data/ristoranti.csv");
                refreshData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore nei campi numerici!",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
