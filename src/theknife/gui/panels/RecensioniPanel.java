/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello recensionipanel.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import theknife.GestoreFile;
import theknife.Recensione;
import theknife.Ristorante;
import theknife.Utente;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

/**
 * <h2>RecensioniPanel</h2>
 *
 * <p>Permette la <strong>gestione completa</strong> delle recensioni:</p>
 * <ul>
 *   <li>Il <em>cliente</em> può inserire / modificare / eliminare le proprie recensioni.</li>
 *   <li>Il <em>ristoratore</em> può rispondere alle recensioni dei ristoranti di cui è proprietario.</li>
 *   <li>Tutte le operazioni eseguono la persistenza su <code>data/recensioni.csv</code>.</li>
 * </ul>
 *
 * <p>
 * Eredita da {@link GradientPanel} per avere lo sfondo sfumato e si integra
 * come una “card” del {@link FancyFrame} principale.
 * </p>
 *
 * @author TheKnife Team
 */
public class RecensioniPanel extends GradientPanel {

    /* ------------------------------------------------------------------ */
    /* Attributi GUI                                                      */
    /* ------------------------------------------------------------------ */

    private final FancyFrame parent;
    private final JTextArea textArea;

    private final JButton btnNuova;
    private final JButton btnModifica;
    private final JButton btnElimina;
    private final JButton btnRispondi;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Costruisce il pannello e ne inizializza la grafica.
     *
     * @param parent frame principale che contiene la card
     */
    public RecensioniPanel(FancyFrame parent) {
        super(new Color(220, 220, 220), new Color(200, 200, 200));
        this.parent = parent;
        this.textArea = new JTextArea();
        this.btnNuova    = new JButton("Nuova Recensione (Cliente)");
        this.btnModifica = new JButton("Modifica Recensione (Cliente)");
        this.btnElimina  = new JButton("Elimina Recensione (Cliente)");
        this.btnRispondi = new JButton("Rispondi (Ristoratore)");

        initUI();
    }

    /* ------------------------------------------------------------------ */
    /* Inizializzazione interfaccia                                       */
    /* ------------------------------------------------------------------ */

    /** Configura layout, componenti e listener. */
    private void initUI() {
        setLayout(new BorderLayout());

        /* -------- Titolo ------------------------------------------------ */
        JLabel lblTitle = new JLabel("Gestione Recensioni", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        /* -------- Area di testo ---------------------------------------- */
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        /* -------- Pannello pulsanti ------------------------------------ */
        JPanel bottom = new JPanel();
        bottom.add(btnNuova);
        bottom.add(btnModifica);
        bottom.add(btnElimina);
        bottom.add(btnRispondi);
        add(bottom, BorderLayout.SOUTH);

        /* Listener pulsanti */
        btnNuova.addActionListener(e -> nuovaRecensione());
        btnModifica.addActionListener(e -> modificaRecensione());
        btnElimina.addActionListener(e -> eliminaRecensione());
        btnRispondi.addActionListener(e -> rispondiRecensione());
    }

    /* ------------------------------------------------------------------ */
    /* Aggiornamento area testo                                           */
    /* ------------------------------------------------------------------ */

    /**
     * Ricarica e stampa tutte le recensioni presenti nel file CSV.
     * Se l’utente non è loggato il nome autore viene mascherato come *Anonimo*.
     */
    public void refreshData() {
        List<Recensione> recs = GestoreFile.caricaRecensioni("data/recensioni.csv");
        textArea.setText("");
        boolean logged = parent.isLoggedIn();

        for (Recensione r : recs) {
            String autore = logged ? r.getUsername().trim() : "Anonimo";
            textArea.append("""
                    Ristorante: %s, Utente: %s, %d stelle
                    Testo: %s
                    """.formatted(r.getIdRistorante(), autore, r.getStelle(), r.getTesto()));

            if (r.hasRisposta()) {
                textArea.append("Risposta Ristoratore: " + r.getRisposta() + "\n");
            }
            textArea.append("--------------\n");
        }
    }

    /* ------------------------------------------------------------------ */
    /* AZIONE 1 – Nuova recensione                                        */
    /* ------------------------------------------------------------------ */

    /** Permette al cliente loggato di inserire una recensione. */
    private void nuovaRecensione() {
        if (!checkCliente()) return;

        List<Ristorante> elenco = GestoreFile.caricaRistoranti("data/ristoranti.csv");
        if (elenco.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun ristorante disponibile",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> comboRisto = new JComboBox<>(
                elenco.stream().map(Ristorante::getNome).toArray(String[]::new));
        JSpinner spinStelle = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        JTextField txtTesto = new JTextField();

        Object[] msg = { "Ristorante:", comboRisto,
                         "Stelle (1–5):", spinStelle,
                         "Testo:", txtTesto };

        if (JOptionPane.showConfirmDialog(this, msg,
                "Nuova Recensione", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            Recensione rec = new Recensione(
                    comboRisto.getSelectedItem().toString().trim(),
                    parent.getUtenteCorrente().getUsername().trim(),
                    (Integer) spinStelle.getValue(),
                    txtTesto.getText().trim(),
                    "");
            GestoreFile.aggiungiRecensione(rec, "data/recensioni.csv");
            refreshData();
        }
    }

    /* ------------------------------------------------------------------ */
    /* AZIONE 2 – Modifica recensione                                     */
    /* ------------------------------------------------------------------ */

    /** Consente al cliente di modificare una propria recensione. */
    private void modificaRecensione() {
        if (!checkCliente()) return;

        Utente   user   = parent.getUtenteCorrente();
        String   who    = user.getUsername().trim().toLowerCase();
        List<Recensione> tutte = GestoreFile.caricaRecensioni("data/recensioni.csv");

        List<Recensione> mie = tutte.stream()
                                    .filter(r -> r.getUsername().trim().equalsIgnoreCase(who))
                                    .toList();

        if (mie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da modificare.",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(mie.stream()
                .map(r -> r.getIdRistorante() + " (" + r.getStelle() + "★)")
                .toArray(String[]::new));
        JSpinner spin = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        JTextField txtTesto = new JTextField();

        Object[] msg = { "Recensione da modificare:", combo,
                         "Nuove Stelle:", spin,
                         "Nuovo testo:", txtTesto };

        if (JOptionPane.showConfirmDialog(this, msg,
                "Modifica Recensione", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            Recensione sel = mie.get(combo.getSelectedIndex());
            GestoreFile.modificaRecensione("data/recensioni.csv",
                    who,
                    sel.getIdRistorante(),
                    txtTesto.getText().trim(),
                    (Integer) spin.getValue());
            refreshData();
        }
    }

    /* ------------------------------------------------------------------ */
    /* AZIONE 3 – Elimina recensione                                      */
    /* ------------------------------------------------------------------ */

    /** Elimina una recensione del cliente loggato. */
    private void eliminaRecensione() {
        if (!checkCliente()) return;

        Utente u = parent.getUtenteCorrente();
        List<Recensione> mie = GestoreFile.caricaRecensioni("data/recensioni.csv")
                .stream()
                .filter(r -> r.getUsername().trim().equalsIgnoreCase(u.getUsername()))
                .toList();

        if (mie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da eliminare.",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(mie.stream()
                .map(r -> r.getIdRistorante() + " (" + r.getStelle() + "★)")
                .toArray(String[]::new));

        if (JOptionPane.showConfirmDialog(this, combo,
                "Elimina Recensione", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            Recensione sel = mie.get(combo.getSelectedIndex());
            GestoreFile.eliminaRecensione("data/recensioni.csv",
                                          u.getUsername(),
                                          sel.getIdRistorante());
            refreshData();
        }
    }

    /* ------------------------------------------------------------------ */
    /* AZIONE 4 – Risposta ristoratore                                    */
    /* ------------------------------------------------------------------ */

    /** Permette al ristoratore di rispondere a recensioni dei propri locali. */
    private void rispondiRecensione() {
        if (!checkRistoratore()) return;

        Utente rst = parent.getUtenteCorrente();

        /* Ristoranti di cui l’utente è proprietario */
        List<Ristorante> mieiRisto = GestoreFile.caricaRistoranti("data/ristoranti.csv")
                .stream()
                .filter(r -> rst.getUsername().equalsIgnoreCase(r.getProprietario()))
                .toList();

        /* Recensioni riferite ai ristoranti sopra */
        List<Recensione> recTarget = GestoreFile.caricaRecensioni("data/recensioni.csv")
                .stream()
                .filter(rec -> mieiRisto.stream()
                        .anyMatch(r -> r.getNome().equalsIgnoreCase(rec.getIdRistorante())))
                .toList();

        if (recTarget.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nessuna recensione dei tuoi ristoranti da rispondere.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(recTarget.stream()
                .map(r -> r.getIdRistorante() + " - " + r.getUsername())
                .toArray(String[]::new));
        JTextField txtResp = new JTextField();

        Object[] msg = { "Recensione:", combo, "Risposta:", txtResp };

        if (JOptionPane.showConfirmDialog(this, msg,
                "Rispondi Recensione", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

            Recensione sel = recTarget.get(combo.getSelectedIndex());
            sel.setRisposta(txtResp.getText().trim());

            /* Aggiorna lista completa e persiste */
            List<Recensione> all = GestoreFile.caricaRecensioni("data/recensioni.csv")
                                              .stream()
                                              .peek(r -> {
                                                  if (r.getUsername().equalsIgnoreCase(sel.getUsername())
                                                      && r.getIdRistorante().equalsIgnoreCase(sel.getIdRistorante())) {
                                                      r.setRisposta(sel.getRisposta());
                                                  }
                                              })
                                              .toList();

            GestoreFile.salvaRecensioni(all, "data/recensioni.csv");
            refreshData();
        }
    }

    /* ------------------------------------------------------------------ */
    /* Helper di ruolo                                                    */
    /* ------------------------------------------------------------------ */

    /** Controlla che l’utente sia un cliente loggato. */
    private boolean checkCliente() {
        if (!parent.isLoggedIn() || !parent.getUtenteCorrente().isCliente()) {
            JOptionPane.showMessageDialog(this,
                    "Devi essere loggato come cliente!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /** Controlla che l’utente sia un ristoratore loggato. */
    private boolean checkRistoratore() {
        if (!parent.isLoggedIn() || !parent.getUtenteCorrente().isRistoratore()) {
            JOptionPane.showMessageDialog(this,
                    "Devi essere loggato come ristoratore!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
