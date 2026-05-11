/**
 * TheKnife – Modulo Client
 * Pannello gestione recensioni.
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
import it.uninsubria.theknife.common.model.Recensione;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello per la gestione completa delle recensioni.
 * <p>
 * <b>Clienti:</b> visualizzano le proprie recensioni e possono
 * inserire, modificare o eliminare.
 * </p>
 * <p>
 * <b>Ristoratori:</b> visualizzano le recensioni dei propri ristoranti
 * e possono rispondere (al massimo una volta per recensione).
 * </p>
 * <p>
 * Tutte le operazioni vengono delegate al server tramite il protocollo
 * {@link it.uninsubria.theknife.common.CommandType}.
 * </p>
 */
public class RecensioniPanel extends GradientPanel {

    private final FancyFrame parent;
    private final JTextArea  textArea = new JTextArea();

    private final JButton btnNuova    = new JButton("Nuova Recensione");
    private final JButton btnModifica = new JButton("Modifica");
    private final JButton btnElimina  = new JButton("Elimina");
    private final JButton btnRispondi = new JButton("Rispondi (Ristoratore)");

    /** Cache delle recensioni mostrate, usata dai dialog di modifica/elimina/risposta. */
    private List<Recensione> recensioniCorrente = List.of();

    /**
     * @param parent frame principale
     */
    public RecensioniPanel(FancyFrame parent) {
        super(new Color(220, 220, 220), new Color(200, 200, 200));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Recensioni", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(btnNuova);
        bottom.add(btnModifica);
        bottom.add(btnElimina);
        bottom.add(btnRispondi);
        add(bottom, BorderLayout.SOUTH);

        btnNuova.addActionListener(e    -> nuovaRecensione());
        btnModifica.addActionListener(e -> modificaRecensione());
        btnElimina.addActionListener(e  -> eliminaRecensione());
        btnRispondi.addActionListener(e -> rispondiRecensione());
    }

    /**
     * Ricarica le recensioni dal server.
     * <ul>
     *   <li>Cliente loggato: carica le proprie recensioni</li>
     *   <li>Ristoratore loggato: carica le recensioni dei suoi ristoranti</li>
     *   <li>Guest: mostra messaggio di accesso richiesto</li>
     * </ul>
     */
    public void refreshData() {
        textArea.setText("");
        recensioniCorrente = List.of();

        if (!ClientTK.isLoggato()) {
            textArea.setText("Effettua il login per gestire le recensioni.");
            aggiornaVisibilitaPulsanti();
            return;
        }

        boolean isCliente = ClientTK.getUtenteLoggato().isCliente();
        CommandType cmd = isCliente
                ? CommandType.CLIENTE_VISUALIZZA_MIE_RECENSIONI
                : CommandType.RISTORATORE_VISUALIZZA_RECENSIONI;

        try {
            Request req = new Request(cmd, ClientTK.getUtenteLoggato().getUsername());
            Response resp = ClientTK.getConnessione().invia(req);

            if (resp.isSuccesso()) {
                recensioniCorrente = resp.getDatoTipizzato();
                stampaRecensioni(recensioniCorrente, isCliente);
            } else {
                textArea.setText("Errore: " + resp.getMessaggio());
            }
        } catch (Exception ex) {
            textArea.setText("Errore di connessione: " + ex.getMessage());
        }

        aggiornaVisibilitaPulsanti();
    }

    /** Scrive le recensioni nell'area di testo. */
    private void stampaRecensioni(List<Recensione> lista, boolean isCliente) {
        if (lista.isEmpty()) {
            textArea.setText(isCliente
                    ? "Non hai ancora scritto recensioni."
                    : "Nessuna recensione per i tuoi ristoranti.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Recensione r : lista) {
            sb.append("Ristorante: ").append(r.getNomeRistorante()).append('\n');
            if (!isCliente) sb.append("Autore: ").append(r.getUsernameCliente()).append('\n');
            sb.append(r.getStelle()).append("★  ").append(r.getTesto()).append('\n');
            if (r.hasRisposta()) sb.append("↳ Risposta: ").append(r.getRisposta()).append('\n');
            sb.append("──────────────\n");
        }
        textArea.setText(sb.toString());
        textArea.setCaretPosition(0);
    }

    /** Mostra/nasconde i pulsanti in base al ruolo dell'utente. */
    private void aggiornaVisibilitaPulsanti() {
        boolean loggedIn   = ClientTK.isLoggato();
        boolean isCliente  = loggedIn && ClientTK.getUtenteLoggato().isCliente();
        boolean isRist     = loggedIn && ClientTK.getUtenteLoggato().isRistoratore();

        btnNuova.setVisible(isCliente);
        btnModifica.setVisible(isCliente);
        btnElimina.setVisible(isCliente);
        btnRispondi.setVisible(isRist);
    }

    /* ---- Azioni cliente ---------------------------------------------- */

    private void nuovaRecensione() {
        if (!checkCliente()) return;

        String nomeRist = JOptionPane.showInputDialog(this,
                "Nome del ristorante:", "Nuova Recensione", JOptionPane.PLAIN_MESSAGE);
        if (nomeRist == null || nomeRist.isBlank()) return;

        JSpinner spinStelle = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        JTextArea txtTesto  = new JTextArea(3, 20);
        txtTesto.setLineWrap(true);

        Object[] msg = {"Stelle (1–5):", spinStelle,
                        "Testo:", new JScrollPane(txtTesto)};

        if (JOptionPane.showConfirmDialog(this, msg, "Nuova Recensione",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        try {
            Request req = new Request(CommandType.CLIENTE_AGGIUNGI_RECENSIONE,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante", nomeRist.trim())
                    .aggiungiParametro("stelle", (Integer) spinStelle.getValue())
                    .aggiungiParametro("testo",  txtTesto.getText().trim());
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio(),
                    resp.isSuccesso() ? "OK" : "Errore",
                    resp.isSuccesso()
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE);
            if (resp.isSuccesso()) refreshData();
        } catch (Exception ex) {
            mostraErroreRete(ex);
        }
    }

    private void modificaRecensione() {
        if (!checkCliente()) return;
        if (recensioniCorrente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da modificare.");
            return;
        }

        JComboBox<String> combo = buildComboRecensioni(recensioniCorrente);
        JSpinner spinStelle     = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        JTextArea txtTesto      = new JTextArea(3, 20);
        txtTesto.setLineWrap(true);

        Object[] msg = {"Recensione:", combo, "Nuove stelle:", spinStelle,
                        "Nuovo testo:", new JScrollPane(txtTesto)};

        if (JOptionPane.showConfirmDialog(this, msg, "Modifica Recensione",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        Recensione sel = recensioniCorrente.get(combo.getSelectedIndex());
        try {
            Request req = new Request(CommandType.CLIENTE_MODIFICA_RECENSIONE,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante", sel.getNomeRistorante())
                    .aggiungiParametro("stelle", (Integer) spinStelle.getValue())
                    .aggiungiParametro("testo",  txtTesto.getText().trim());
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio());
            if (resp.isSuccesso()) refreshData();
        } catch (Exception ex) {
            mostraErroreRete(ex);
        }
    }

    private void eliminaRecensione() {
        if (!checkCliente()) return;
        if (recensioniCorrente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai recensioni da eliminare.");
            return;
        }

        JComboBox<String> combo = buildComboRecensioni(recensioniCorrente);
        if (JOptionPane.showConfirmDialog(this, combo, "Elimina Recensione",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        Recensione sel = recensioniCorrente.get(combo.getSelectedIndex());
        try {
            Request req = new Request(CommandType.CLIENTE_ELIMINA_RECENSIONE,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante", sel.getNomeRistorante());
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio());
            if (resp.isSuccesso()) refreshData();
        } catch (Exception ex) {
            mostraErroreRete(ex);
        }
    }

    /* ---- Azioni ristoratore ------------------------------------------ */

    private void rispondiRecensione() {
        if (!checkRistoratore()) return;
        if (recensioniCorrente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna recensione a cui rispondere.");
            return;
        }

        JComboBox<String> combo = buildComboRecensioni(recensioniCorrente);
        JTextArea txtRisposta   = new JTextArea(3, 20);
        txtRisposta.setLineWrap(true);

        Object[] msg = {"Recensione:", combo,
                        "Risposta:", new JScrollPane(txtRisposta)};

        if (JOptionPane.showConfirmDialog(this, msg, "Rispondi",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        Recensione sel = recensioniCorrente.get(combo.getSelectedIndex());
        try {
            Request req = new Request(CommandType.RISTORATORE_RISPONDI_RECENSIONE,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante",  sel.getNomeRistorante())
                    .aggiungiParametro("usernameCliente", sel.getUsernameCliente())
                    .aggiungiParametro("risposta",        txtRisposta.getText().trim());
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio());
            if (resp.isSuccesso()) refreshData();
        } catch (Exception ex) {
            mostraErroreRete(ex);
        }
    }

    /* ---- Helpers -------------------------------------------------------- */

    private JComboBox<String> buildComboRecensioni(List<Recensione> lista) {
        return new JComboBox<>(lista.stream()
                .map(r -> r.getNomeRistorante() + " – " + r.getStelle() + "★")
                .toArray(String[]::new));
    }

    private boolean checkCliente() {
        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isCliente()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato come cliente.",
                    "Accesso negato", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkRistoratore() {
        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isRistoratore()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato come ristoratore.",
                    "Accesso negato", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void mostraErroreRete(Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Errore di connessione: " + ex.getMessage(),
                "Errore", JOptionPane.ERROR_MESSAGE);
    }
}