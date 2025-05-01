package theknife.gui.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import theknife.*;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

public class RecensioniPanel extends GradientPanel {

    private FancyFrame parent;
    private JTextArea textArea;

    private JButton btnNuova, btnModifica, btnElimina, btnRispondi;

    public RecensioniPanel(FancyFrame parent) {
        super(new Color(220, 220, 220), new Color(200, 200, 200));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Gestione Recensioni", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        btnNuova = new JButton("Nuova Recensione (Cliente)");
        btnModifica = new JButton("Modifica Recensione (Cliente)");
        btnElimina = new JButton("Elimina Recensione (Cliente)");
        btnRispondi = new JButton("Rispondi (Ristoratore)");

        bottom.add(btnNuova);
        bottom.add(btnModifica);
        bottom.add(btnElimina);
        bottom.add(btnRispondi);

        add(bottom, BorderLayout.SOUTH);

        btnNuova.addActionListener(e -> nuovaRecensione());
        btnModifica.addActionListener(e -> modificaRecensione());
        btnElimina.addActionListener(e -> eliminaRecensione());
        btnRispondi.addActionListener(e -> rispondiRecensione());
    }

    public void refreshData() {
        List<Recensione> recs = GestoreFile.caricaRecensioni("data/recensioni.csv");
        textArea.setText("");
        boolean logged = parent.isLoggedIn();

        for (Recensione r : recs) {
            String userDisplay = logged ? r.getUsername().trim() : "Anonimo";
            textArea.append("Ristorante: " + r.getIdRistorante()
                    + ", Utente: " + userDisplay
                    + ", " + r.getStelle() + " stelle\n"
                    + "Testo: " + r.getTesto() + "\n");
            if (r.getRisposta() != null && !r.getRisposta().isEmpty()) {
                textArea.append("Risposta Ristoratore: " + r.getRisposta() + "\n");
            }
            textArea.append("--------------\n");
        }
    }

    private void nuovaRecensione() {
        if (!parent.isLoggedIn() || !parent.getUtenteCorrente().isCliente()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato come cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Ristorante> ristos = GestoreFile.caricaRistoranti("data/ristoranti.csv");
        if (ristos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun ristorante disponibile", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] ristoNomi = ristos.stream().map(Ristorante::getNome).toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(ristoNomi);
        SpinnerNumberModel stelleModel = new SpinnerNumberModel(3, 1, 5, 1);
        JSpinner spinStelle = new JSpinner(stelleModel);
        JTextField txtTesto = new JTextField();

        Object[] msg = { "Ristorante:", combo, "Stelle (1-5):", spinStelle, "Testo:", txtTesto };
        int opt = JOptionPane.showConfirmDialog(this, msg, "Nuova Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            Recensione rec = new Recensione(
                    ((String) combo.getSelectedItem()).trim(),
                    parent.getUtenteCorrente().getUsername().trim(),
                    (Integer) spinStelle.getValue(),
                    txtTesto.getText().trim(), "");
            GestoreFile.aggiungiRecensione(rec, "data/recensioni.csv");
            refreshData();
        }
    }

    private void modificaRecensione() {
        Utente u = parent.getUtenteCorrente();
        if (!parent.isLoggedIn() || !u.isCliente()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato come cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Recensione> tutte = GestoreFile.caricaRecensioni("data/recensioni.csv");
        System.out.println("USERNAME attivo: [" + u.getUsername() + "]");
        for (Recensione r : tutte) {
            System.out.println("Trovata rec: user=[" + r.getUsername() + "] - risto=[" + r.getIdRistorante() + "]");
        }

        List<Recensione> mie = tutte.stream()
                .filter(r -> r.getUsername().trim().equalsIgnoreCase(u.getUsername().trim()))
                .toList();

        if (mie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da modificare!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(mie.stream()
                .map(r -> r.getIdRistorante() + " (" + r.getStelle() + " stelle)")
                .toArray(String[]::new));
        SpinnerNumberModel sm = new SpinnerNumberModel(3, 1, 5, 1);
        JSpinner spin = new JSpinner(sm);
        JTextField txtTesto = new JTextField();

        Object[] msg = { "Quale recensione?", combo, "Nuove Stelle:", spin, "Nuovo testo:", txtTesto };
        int opt = JOptionPane.showConfirmDialog(this, msg, "Modifica Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            Recensione sel = mie.get(combo.getSelectedIndex());
            GestoreFile.modificaRecensione("data/recensioni.csv", u.getUsername(), sel.getIdRistorante(),
                    txtTesto.getText().trim(), (Integer) spin.getValue());
            refreshData();
        }
    }

    private void eliminaRecensione() {
        Utente u = parent.getUtenteCorrente();
        if (!parent.isLoggedIn() || !u.isCliente()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato come cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Recensione> mie = GestoreFile.caricaRecensioni("data/recensioni.csv").stream()
                .filter(r -> r.getUsername().trim().equalsIgnoreCase(u.getUsername().trim())).toList();
        if (mie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da eliminare.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(mie.stream()
                .map(r -> r.getIdRistorante() + " (" + r.getStelle() + " stelle)")
                .toArray(String[]::new));
        int opt = JOptionPane.showConfirmDialog(this, combo, "Elimina Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            Recensione sel = mie.get(combo.getSelectedIndex());
            GestoreFile.eliminaRecensione("data/recensioni.csv", u.getUsername(), sel.getIdRistorante());
            refreshData();
        }
    }

    private void rispondiRecensione() {
        Utente u = parent.getUtenteCorrente();
        if (!parent.isLoggedIn() || !u.isRistoratore()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato come ristoratore!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Ristorante> miei = GestoreFile.caricaRistoranti("data/ristoranti.csv").stream()
                .filter(r -> u.getUsername().trim().equalsIgnoreCase(r.getProprietario().trim())).toList();
        List<Recensione> recs = GestoreFile.caricaRecensioni("data/recensioni.csv");

        List<Recensione> mieRec = recs.stream()
                .filter(r -> miei.stream().anyMatch(x -> x.getNome().equalsIgnoreCase(r.getIdRistorante())))
                .toList();

        if (mieRec.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna recensione dei tuoi ristoranti da rispondere.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(mieRec.stream()
                .map(r -> r.getIdRistorante() + " - " + r.getUsername())
                .toArray(String[]::new));
        JTextField txtResp = new JTextField();

        Object[] msg = { "Recensione:", combo, "Risposta:", txtResp };
        int opt = JOptionPane.showConfirmDialog(this, msg, "Rispondi Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            Recensione sel = mieRec.get(combo.getSelectedIndex());
            sel.setRisposta(txtResp.getText().trim());

            for (int i = 0; i < recs.size(); i++) {
                Recensione rr = recs.get(i);
                if (rr.getUsername().trim().equalsIgnoreCase(sel.getUsername().trim())
                        && rr.getIdRistorante().trim().equalsIgnoreCase(sel.getIdRistorante().trim())) {
                    rr.setRisposta(sel.getRisposta());
                }
            }
            GestoreFile.salvaRecensioni(recs, "data/recensioni.csv");
            refreshData();
        }
    }
}

