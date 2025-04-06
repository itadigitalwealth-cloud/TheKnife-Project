package theknife.gui.panels;

import theknife.Ristorante;
import theknife.GestoreFile;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchPanel extends GradientPanel {
    private FancyFrame parent;

    private JTextField txtNome;
    private JTextField txtCitta;
    private JTextField txtCucina;
    private JTextField txtPrezzo;
    private JCheckBox chkDelivery;
    private JCheckBox chkPrenotazione;

    private JPanel resultsPanel;

    public SearchPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Nome (parziale):"));
        txtNome = new JTextField(10);
        topPanel.add(txtNome);

        topPanel.add(new JLabel("Città:"));
        txtCitta = new JTextField(10);
        topPanel.add(txtCitta);

        topPanel.add(new JLabel("Cucina:"));
        txtCucina = new JTextField(10);
        topPanel.add(txtCucina);

        topPanel.add(new JLabel("Max Prezzo:"));
        txtPrezzo = new JTextField(5);
        topPanel.add(txtPrezzo);

        chkDelivery = new JCheckBox("Delivery?");
        topPanel.add(chkDelivery);

        chkPrenotazione = new JCheckBox("Prenotazione?");
        topPanel.add(chkPrenotazione);

        JButton btnCerca = new JButton("Cerca");
        topPanel.add(btnCerca);

        add(topPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(resultsPanel);
        add(scroll, BorderLayout.CENTER);

        btnCerca.addActionListener(e -> eseguiRicerca());
    }

    private void eseguiRicerca() {
        resultsPanel.removeAll();

        String nomePart = txtNome.getText().trim();
        String citta = txtCitta.getText().trim();
        String cucina = txtCucina.getText().trim();
        Double maxPrezzo = null;
        if (!txtPrezzo.getText().trim().isEmpty()) {
            try {
                maxPrezzo = Double.parseDouble(txtPrezzo.getText().trim());
            } catch (NumberFormatException ex) {
                maxPrezzo = null;
            }
        }
        Boolean del = chkDelivery.isSelected();
        Boolean pren = chkPrenotazione.isSelected();

        List<Ristorante> risultati = GestoreFile.cercaRistoranti(
                "data/ristoranti.csv",
                nomePart,
                citta,
                cucina,
                maxPrezzo,
                del,
                pren);

        if (risultati.isEmpty()) {
            JLabel lblNo = new JLabel("Nessun ristorante trovato con i filtri selezionati.");
            resultsPanel.add(lblNo);
        } else {
            for (Ristorante r : risultati) {
                JButton btn = new JButton(r.getNome());
                btn.setAlignmentX(Component.LEFT_ALIGNMENT);
                btn.addActionListener(ev -> {
                    parent.getDetailPanel().setRistorante(r);
                    parent.showCard(FancyFrame.CARD_DETAIL);
                });
                resultsPanel.add(btn);
            }
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    public void refresh() {
        // se vuoi svuotare i filtri ogni volta, falli qui
    }
}
