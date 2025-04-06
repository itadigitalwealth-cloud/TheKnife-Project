package theknife.gui.panels;

import theknife.*;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

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
            String userDisplay = logged ? r.getUsername() : "Anonimo";
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
        if (!parent.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Utente u = parent.getUtenteCorrente();
        if (!"cliente".equalsIgnoreCase(u.getRuolo())) {
            JOptionPane.showMessageDialog(this, "Solo i clienti possono inserire recensioni!", "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Ristorante> ristos = GestoreFile.caricaRistoranti("data/ristoranti.csv");
        if (ristos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun ristorante disponibile", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // seleziona ristorante
        String[] ristoNomi = ristos.stream().map(Ristorante::getNome).toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(ristoNomi);
        SpinnerNumberModel stelleModel = new SpinnerNumberModel(3, 1, 5, 1);
        JSpinner spinStelle = new JSpinner(stelleModel);
        JTextField txtTesto = new JTextField();

        Object[] msg = {
                "Ristorante:", combo,
                "Stelle (1-5):", spinStelle,
                "Testo:", txtTesto
        };
        int opt = JOptionPane.showConfirmDialog(this, msg,
                "Nuova Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            String risto = (String) combo.getSelectedItem();
            int st = (Integer) spinStelle.getValue();
            String testo = txtTesto.getText().trim();
            Recensione rec = new Recensione(risto, u.getUsername(), st, testo, "");
            GestoreFile.aggiungiRecensione(rec, "data/recensioni.csv");
            refreshData();
        }
    }

    private void modificaRecensione() {
        if (!parent.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Utente u = parent.getUtenteCorrente();
        if (!"cliente".equalsIgnoreCase(u.getRuolo())) {
            JOptionPane.showMessageDialog(this, "Solo i clienti possono modificare recensioni!", "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Cerchiamo le recensioni di quell'utente
        List<Recensione> recs = GestoreFile.caricaRecensioni("data/recensioni.csv");
        List<Recensione> mie = new ArrayList<>();
        for (Recensione r : recs) {
            if (r.getUsername().equalsIgnoreCase(u.getUsername())) {
                mie.add(r);
            }
        }
        if (mie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da modificare!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] scelte = mie.stream()
                .map(r -> r.getIdRistorante() + " (" + r.getStelle() + " stelle)")
                .toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(scelte);
        SpinnerNumberModel sm = new SpinnerNumberModel(3, 1, 5, 1);
        JSpinner spin = new JSpinner(sm);
        JTextField txtTesto = new JTextField();

        Object[] msg = {
                "Quale recensione?", combo,
                "Nuove Stelle:", spin,
                "Nuovo testo:", txtTesto
        };
        int opt = JOptionPane.showConfirmDialog(this, msg, "Modifica Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            int idx = combo.getSelectedIndex();
            Recensione sel = mie.get(idx);
            String risto = sel.getIdRistorante();
            int st = (Integer) spin.getValue();
            String testo = txtTesto.getText().trim();
            GestoreFile.modificaRecensione("data/recensioni.csv", u.getUsername(), risto, testo, st);
            refreshData();
        }
    }

    private void eliminaRecensione() {
        if (!parent.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Utente u = parent.getUtenteCorrente();
        if (!"cliente".equalsIgnoreCase(u.getRuolo())) {
            JOptionPane.showMessageDialog(this, "Solo i clienti possono eliminare recensioni!", "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // trovo le sue
        List<Recensione> recs = GestoreFile.caricaRecensioni("data/recensioni.csv");
        List<Recensione> mie = new ArrayList<>();
        for (Recensione r : recs) {
            if (r.getUsername().equalsIgnoreCase(u.getUsername())) {
                mie.add(r);
            }
        }
        if (mie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da eliminare.", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] scelte = mie.stream()
                .map(r -> r.getIdRistorante() + " (" + r.getStelle() + " stelle)")
                .toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(scelte);
        int opt = JOptionPane.showConfirmDialog(this, combo,
                "Elimina Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            int idx = combo.getSelectedIndex();
            Recensione sel = mie.get(idx);
            GestoreFile.eliminaRecensione("data/recensioni.csv", u.getUsername(), sel.getIdRistorante());
            refreshData();
        }
    }

    private void rispondiRecensione() {
        if (!parent.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Utente u = parent.getUtenteCorrente();
        if (!"ristoratore".equalsIgnoreCase(u.getRuolo())) {
            JOptionPane.showMessageDialog(this, "Solo i ristoratori possono rispondere!", "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Carichiamo TUTTE le recensioni, filtriamo solo quelle dei ristoranti che
        // possiede
        List<Recensione> recs = GestoreFile.caricaRecensioni("data/recensioni.csv");
        List<Ristorante> miei = GestoreFile.caricaRistoranti("data/ristoranti.csv")
                .stream()
                .filter(r -> u.getUsername().equalsIgnoreCase(r.getProprietario()))
                .toList();

        // Troviamo SOLO le recensioni relative a tali ristoranti
        List<Recensione> mieRec = new ArrayList<>();
        for (Recensione r : recs) {
            boolean isMio = miei.stream().anyMatch(x -> x.getNome().equalsIgnoreCase(r.getIdRistorante()));
            if (isMio) {
                mieRec.add(r);
            }
        }
        if (mieRec.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nessuna recensione dei tuoi ristoranti da rispondere.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] scelte = mieRec.stream()
                .map(r -> r.getIdRistorante() + " - " + r.getUsername())
                .toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(scelte);
        JTextField txtResp = new JTextField();

        Object[] msg = {
                "Recensione:", combo,
                "Risposta:", txtResp
        };
        int opt = JOptionPane.showConfirmDialog(this, msg,
                "Rispondi Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            int idx = combo.getSelectedIndex();
            Recensione sel = mieRec.get(idx);
            sel.setRisposta(txtResp.getText().trim());
            // Riscriviamo su file TUTTE le rec
            // Sovrascriviamo
            for (int i = 0; i < recs.size(); i++) {
                Recensione rr = recs.get(i);
                if (rr.getUsername().equalsIgnoreCase(sel.getUsername())
                        && rr.getIdRistorante().equalsIgnoreCase(sel.getIdRistorante())) {
                    rr.setRisposta(sel.getRisposta());
                }
            }
            GestoreFile.salvaRecensioni(recs, "data/recensioni.csv");
            refreshData();
        }
    }
}
