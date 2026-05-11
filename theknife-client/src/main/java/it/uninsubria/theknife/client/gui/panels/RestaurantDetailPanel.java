/**
 * TheKnife – Modulo Client
 * Pannello dettaglio ristorante.
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
import it.uninsubria.theknife.common.model.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello che visualizza i dettagli completi di un ristorante e le
 * sue recensioni, recuperati dal server.
 * <p>
 * Implementa le funzioni {@code visualizzaRistorante()} e
 * {@code visualizzaRecensioni()} delle specifiche.
 * I dati (media stelle, numero recensioni) vengono richiesti
 * tramite {@link CommandType#VISUALIZZA_RISTORANTE} e
 * {@link CommandType#VISUALIZZA_RECENSIONI}.
 * </p>
 *
 * <p>
 * Se l'utente loggato è un <b>cliente</b> sono disponibili i pulsanti:
 * <ul>
 *   <li>Aggiungi/Rimuovi dai preferiti</li>
 *   <li>Aggiungi recensione</li>
 * </ul>
 * </p>
 */
public class RestaurantDetailPanel extends GradientPanel {

    private final FancyFrame parent;

    /** Ristorante attualmente visualizzato. */
    private Ristorante ristoranteCorrente;

    private final JTextArea textArea    = new JTextArea();
    private final JButton   btnIndietro = new JButton("← Torna Indietro");
    private final JButton   btnPreferito   = new JButton("★ Aggiungi ai Preferiti");
    private final JButton   btnRecensione  = new JButton("✎ Aggiungi Recensione");

    /**
     * @param parent frame principale
     */
    public RestaurantDetailPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Dettagli Ristorante", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        bot.add(btnIndietro);
        bot.add(btnPreferito);
        bot.add(btnRecensione);
        add(bot, BorderLayout.SOUTH);

        btnIndietro.addActionListener(e -> parent.showCard(FancyFrame.CARD_SEARCH));

        btnPreferito.addActionListener(e -> aggiungiPreferito());
        btnRecensione.addActionListener(e -> aggiungiRecensione());
    }

    /**
     * Imposta il ristorante da visualizzare e ricarica il contenuto
     * dal server.
     *
     * @param r ristorante selezionato
     */
    public void setRistorante(Ristorante r) {
        this.ristoranteCorrente = r;
        aggiornaPulsanti();
        refreshDetails();
    }

    /** Mostra/nasconde i pulsanti cliente in base allo stato di login. */
    private void aggiornaPulsanti() {
        boolean isCliente = ClientTK.isLoggato()
                            && ClientTK.getUtenteLoggato().isCliente();
        btnPreferito.setVisible(isCliente);
        btnRecensione.setVisible(isCliente);
    }

    /**
     * Richiede al server i dettagli aggiornati del ristorante e le recensioni.
     */
    private void refreshDetails() {
        if (ristoranteCorrente == null) {
            textArea.setText("Nessun ristorante selezionato.");
            return;
        }

        StringBuilder sb = new StringBuilder(512);

        try {
            // Dettagli ristorante (con media stelle e conteggio aggiornati)
            Request reqRist = new Request(CommandType.VISUALIZZA_RISTORANTE, null)
                    .aggiungiParametro("nomeRistorante", ristoranteCorrente.getNome());
            Response respRist = ClientTK.getConnessione().invia(reqRist);

            Ristorante r = respRist.isSuccesso()
                    ? (Ristorante) respRist.getDato()
                    : ristoranteCorrente; // fallback sui dati già in memoria

            sb.append("Nome:        ").append(r.getNome()).append('\n')
              .append("Città:       ").append(r.getCitta()).append('\n')
              .append("Nazione:     ").append(r.getNazione()).append('\n')
              .append("Indirizzo:   ").append(r.getIndirizzo()).append('\n')
              .append("Tipo cucina: ").append(r.getTipoCucina()).append('\n')
              .append("Prezzo:      ").append(r.getFasciaPrezzo()).append(" €\n")
              .append("Delivery:    ").append(r.isDelivery()    ? "sì" : "no").append('\n')
              .append("Prenotazione:").append(r.isPrenotazione() ? "sì" : "no").append('\n')
              .append(String.format("Media stelle: %.2f  (%d recensioni)%n",
                      r.getMediaStelle(), r.getNumeroRecensioni()));

            // Recensioni
            sb.append("\n=== Recensioni ===\n");

            Request reqRec = new Request(CommandType.VISUALIZZA_RECENSIONI, null)
                    .aggiungiParametro("nomeRistorante", r.getNome());
            Response respRec = ClientTK.getConnessione().invia(reqRec);

            if (respRec.isSuccesso()) {
                List<Recensione> recs = respRec.getDatoTipizzato();
                if (recs.isEmpty()) {
                    sb.append("Nessuna recensione ancora.\n");
                } else {
                    for (Recensione rec : recs) {
                        // Le recensioni sono mostrate in forma anonima se non loggati
                        String autore = ClientTK.isLoggato()
                                ? rec.getUsernameCliente()
                                : "Anonimo";
                        sb.append(String.format(" %-15s  %d★%n", autore, rec.getStelle()))
                          .append("   ").append(rec.getTesto()).append('\n');
                        if (rec.hasRisposta()) {
                            sb.append("   ↳ Risposta: ").append(rec.getRisposta()).append('\n');
                        }
                        sb.append('\n');
                    }
                }
            }

        } catch (Exception ex) {
            sb.append("\n[Errore nel recupero dati: ").append(ex.getMessage()).append(']');
        }

        textArea.setText(sb.toString());
        textArea.setCaretPosition(0);
    }

    /** Invia al server la richiesta di aggiunta ai preferiti. */
    private void aggiungiPreferito() {
        if (ristoranteCorrente == null) return;
        try {
            Request req = new Request(CommandType.CLIENTE_AGGIUNGI_PREFERITO,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante", ristoranteCorrente.getNome());
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio(),
                    resp.isSuccesso() ? "OK" : "Attenzione",
                    resp.isSuccesso()
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Mostra il dialog per inserire una nuova recensione. */
    private void aggiungiRecensione() {
        if (ristoranteCorrente == null) return;

        JSpinner spinStelle = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        JTextArea txtTesto  = new JTextArea(4, 25);
        txtTesto.setLineWrap(true);
        txtTesto.setWrapStyleWord(true);

        Object[] msg = {
            "Stelle (1–5):", spinStelle,
            "Testo recensione:", new JScrollPane(txtTesto)
        };

        if (JOptionPane.showConfirmDialog(this, msg,
                "Aggiungi Recensione", JOptionPane.OK_CANCEL_OPTION)
                != JOptionPane.OK_OPTION) return;

        String testo = txtTesto.getText().trim();
        if (testo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il testo non può essere vuoto.",
                    "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Request req = new Request(CommandType.CLIENTE_AGGIUNGI_RECENSIONE,
                                      ClientTK.getUtenteLoggato().getUsername())
                    .aggiungiParametro("nomeRistorante", ristoranteCorrente.getNome())
                    .aggiungiParametro("stelle",  (Integer) spinStelle.getValue())
                    .aggiungiParametro("testo",   testo);
            Response resp = ClientTK.getConnessione().invia(req);
            JOptionPane.showMessageDialog(this, resp.getMessaggio(),
                    resp.isSuccesso() ? "OK" : "Errore",
                    resp.isSuccesso()
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE);
            if (resp.isSuccesso()) refreshDetails();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}