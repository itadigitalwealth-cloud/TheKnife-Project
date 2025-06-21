/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello ristorantipanel.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui.panels;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import theknife.GestoreFile;
import theknife.Ristorante;
import theknife.Utente;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

/**
 * Pannello dedicato ai ristoratori per la gestione dei propri locali.
 *
 * <p>Funzionalità offerte:</p>
 * <ul>
 *   <li>Visualizzare l’elenco dei ristoranti il cui {@code proprietario}
 *       coincide con l’utente loggato.</li>
 *   <li>Aggiungere un nuovo ristorante tramite {@link JOptionPane} con
 *       validazione dei campi numerici.</li>
 *   <li>Accedere al pannello di dettaglio cliccando sul pulsante
 *       corrispondente ad un ristorante.</li>
 * </ul>
 *
 * <p>Il pannello è mostrato solo se l’utente corrente ha il ruolo
 * «ristoratore»; in caso contrario viene segnalato con un messaggio.</p>
 *
 * @author TheKnife Team
 */
public class RistorantiPanel extends GradientPanel {

    /* ------------------------------------------------------------------ */
    /* Attributi gui                                                      */
    /* ------------------------------------------------------------------ */

    /** Riferimento al frame principale per la gestione delle card. */
    private final FancyFrame parent;

    /** Pannello contenitore dei risultati (lista dei ristoranti). */
    private final JPanel resultsPanel;

    /** Pulsante per l’aggiunta di un nuovo ristorante. */
    private final JButton btnAggiungi;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Costruisce il pannello, inizializzando interfaccia e listener.
     *
     * @param parent frame principale dell’applicazione
     */
    public RistorantiPanel(FancyFrame parent) {
        super(new Color(230, 230, 230), new Color(210, 210, 210));
        this.parent = parent;

        /* --- Creazione componenti --- */
        setLayout(new BorderLayout());

        /* Titolo -------------------------------------------------------- */
        JLabel lblTitle = new JLabel("Miei Ristoranti (Ristoratore)",
                                     SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        /* Area risultati ------------------------------------------------ */
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);

        /* Pulsante «Aggiungi» ------------------------------------------ */
        btnAggiungi = new JButton("Aggiungi Ristorante");
        btnAggiungi.addActionListener(e -> mostraDialogNuovoRistorante());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnAggiungi);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /* ------------------------------------------------------------------ */
    /* Aggiornamento lista                                                */
    /* ------------------------------------------------------------------ */

    /**
     * Aggiorna l’elenco mostrato in base all’utente loggato.
     * <p>Se l’utente non è ristoratore, viene mostrato un messaggio
     * esplicativo.</p>
     */
    public void refreshData() {
        resultsPanel.removeAll();

        Utente u = parent.getUtenteCorrente();
        if (u == null || !u.isRistoratore()) {
            resultsPanel.add(new JLabel("Devi essere loggato come ristoratore."));
            resultsPanel.revalidate();
            resultsPanel.repaint();
            return;
        }

        /* Carica tutti i ristoranti e filtra quelli del proprietario ---- */
        List<Ristorante> miei = GestoreFile.caricaRistoranti("data/ristoranti.csv")
                                           .stream()
                                           .filter(r -> u.getUsername().equalsIgnoreCase(r.getProprietario()))
                                           .collect(Collectors.toList());

        if (miei.isEmpty()) {
            resultsPanel.add(new JLabel("Non hai creato ristoranti ancora."));
        } else {
            miei.forEach(this::addRistoranteButton);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    /* ------------------------------------------------------------------ */
    /* Dialog di inserimento                                              */
    /* ------------------------------------------------------------------ */

    /**
     * Mostra un {@link JOptionPane} per l’inserimento di un nuovo
     * ristorante. Valida i campi numerici e salva il record su CSV.
     */
    private void mostraDialogNuovoRistorante() {
        Utente u = parent.getUtenteCorrente();
        if (u == null || !u.isRistoratore()) {
            JOptionPane.showMessageDialog(this,
                    "Devi essere ristoratore loggato!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /* --- Campi di input ------------------------------------------- */
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
            "Nome:",           txtNome,
            "Nazione:",        txtNazione,
            "Città:",          txtCitta,
            "Indirizzo:",      txtIndirizzo,
            "Latitudine:",     txtLat,
            "Longitudine:",    txtLon,
            "Fascia Prezzo:",  txtPrezzo,
            chkDel,
            chkPren,
            "Tipo Cucina:",    txtCucina
        };

        int opt = JOptionPane.showConfirmDialog(this, msg,
                "Nuovo Ristorante", JOptionPane.OK_CANCEL_OPTION);

        if (opt != JOptionPane.OK_OPTION) return;

        try {
            double latD   = Double.parseDouble(txtLat.getText().trim());
            double lonD   = Double.parseDouble(txtLon.getText().trim());
            double prezzo = Double.parseDouble(txtPrezzo.getText().trim());

            Ristorante r = new Ristorante(
                    txtNome.getText().trim(),
                    txtNazione.getText().trim(),
                    txtCitta.getText().trim(),
                    txtIndirizzo.getText().trim(),
                    latD, lonD,
                    prezzo,
                    chkDel.isSelected(),
                    chkPren.isSelected(),
                    txtCucina.getText().trim(),
                    u.getUsername()          // proprietario
            );

            GestoreFile.aggiungiRistorante(r, "data/ristoranti.csv");
            refreshData(); // aggiorna lista

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore nei campi numerici!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* ------------------------------------------------------------------ */
    /* Helpers                                                            */
    /* ------------------------------------------------------------------ */

    /**
     * Crea un pulsante che rappresenta un ristorante e lo aggiunge alla
     * lista. Al click viene aperto il dettaglio del ristorante.
     *
     * @param r ristorante da visualizzare
     */
    private void addRistoranteButton(Ristorante r) {
        JButton btn = new JButton(
                r.getNome() + " - " + r.getCitta() + " - " + r.getTipoCucina());
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(ev -> {
            parent.getDetailPanel().setRistorante(r);
            parent.showCard(FancyFrame.CARD_DETAIL);
        });
        resultsPanel.add(btn);
    }
}
