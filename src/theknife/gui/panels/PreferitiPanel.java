package theknife.gui.panels;

import theknife.*;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PreferitiPanel extends GradientPanel {

    private FancyFrame parent;
    private JTextArea textArea;
    private JButton btnAggiungi, btnRimuovi;

    public PreferitiPanel(FancyFrame parent) {
        super(new Color(220, 220, 220), new Color(200, 200, 200));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("I Miei Preferiti (Cliente)", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        btnAggiungi = new JButton("Aggiungi Preferito");
        btnRimuovi = new JButton("Rimuovi Preferito");
        bottom.add(btnAggiungi);
        bottom.add(btnRimuovi);

        add(bottom, BorderLayout.SOUTH);

        btnAggiungi.addActionListener(e -> aggiungiPreferito());
        btnRimuovi.addActionListener(e -> rimuoviPreferito());
    }

    public void refreshData() {
        if (!parent.isLoggedIn()) {
            textArea.setText("Non sei loggato!");
            return;
        }
        Utente u = parent.getUtenteCorrente();
        if (!"cliente".equalsIgnoreCase(u.getRuolo())) {
            textArea.setText("Sei ristoratore, niente preferiti!");
            return;
        }
        List<String> lista = GestoreFile.caricaPreferiti("data/preferiti.csv", u.getUsername());
        textArea.setText("=== I Miei Preferiti ===\\n");
        for (String nomeRisto : lista) {
            textArea.append(nomeRisto + "\\n");
        }
    }

    private void aggiungiPreferito() {
        Utente u = parent.getUtenteCorrente();
        if (u == null || !"cliente".equalsIgnoreCase(u.getRuolo())) {
            JOptionPane.showMessageDialog(this, "Devi essere un cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Ristorante> ristos = GestoreFile.caricaRistoranti("data/ristoranti.csv");
        if (ristos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun ristorante disponibile!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] nomi = ristos.stream().map(Ristorante::getNome).toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(nomi);

        int opt = JOptionPane.showConfirmDialog(this, combo,
                "Aggiungi Preferito", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            String scelto = (String) combo.getSelectedItem();
            GestoreFile.aggiungiPreferito("data/preferiti.csv", u.getUsername(), scelto);
            refreshData();
        }
    }

    private void rimuoviPreferito() {
        Utente u = parent.getUtenteCorrente();
        if (u == null || !"cliente".equalsIgnoreCase(u.getRuolo())) {
            JOptionPane.showMessageDialog(this, "Devi essere un cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<String> lista = GestoreFile.caricaPreferiti("data/preferiti.csv", u.getUsername());
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai ristoranti preferiti!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] arr = lista.toArray(new String[0]);
        JComboBox<String> combo = new JComboBox<>(arr);

        int opt = JOptionPane.showConfirmDialog(this, combo,
                "Rimuovi Preferito", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            String scelto = (String) combo.getSelectedItem();
            GestoreFile.rimuoviPreferito("data/preferiti.csv", u.getUsername(), scelto);
            refreshData();
        }
    }
}
