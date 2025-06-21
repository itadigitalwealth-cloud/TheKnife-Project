/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello preferitipanel.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import theknife.GestoreFile;
import theknife.Ristorante;
import theknife.Utente;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

/**
 * Pannello dedicato alla <b>gestione dei ristoranti preferiti</b> del
 * cliente loggato.
 *
 * <p>Consente di:</p>
 * <ul>
 *     <li>Mostrare l’elenco dei preferiti già salvati.</li>
 *     <li>Aggiungere un nuovo ristorante all’elenco.</li>
 *     <li>Rimuovere un ristorante dall’elenco.</li>
 * </ul>
 *
 * <p>Il pannello è visibile solo a utenti con ruolo <i>cliente</i>;
 * l’accesso è gestito da {@link FancyFrame} prima di visualizzarlo.</p>
 *
 * @author TheKnife Team
 */
public class PreferitiPanel extends GradientPanel {

    /* ------------------------------------------------------------------ */
    /* Attributi                                                          */
    /* ------------------------------------------------------------------ */

    /** Frame principale per verificare login/ruolo e cambiare card. */
    private final FancyFrame parent;

    /* Componenti GUI --------------------------------------------------- */
    private final JTextArea textArea;
    private final JButton   btnAggiungi;
    private final JButton   btnRimuovi;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Costruisce il pannello con uno sfondo in gradiente grigio.
     *
     * @param parent frame che ospita il pannello
     */
    public PreferitiPanel(FancyFrame parent) {
        super(new Color(220, 220, 220), new Color(200, 200, 200));
        this.parent      = parent;
        this.textArea    = new JTextArea();
        this.btnAggiungi = new JButton("Aggiungi Preferito");
        this.btnRimuovi  = new JButton("Rimuovi Preferito");

        initUI();
    }

    /* ------------------------------------------------------------------ */
    /* Inizializzazione GUI                                               */
    /* ------------------------------------------------------------------ */

    /** Configura layout, componenti e listener dei pulsanti. */
    private void initUI() {
        setLayout(new BorderLayout());

        /* ----- Titolo ------------------------------------------------- */
        JLabel lbl = new JLabel("I Miei Preferiti (Cliente)",
                                SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        /* ----- Area testo -------------------------------------------- */
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        /* ----- Pulsanti azione --------------------------------------- */
        JPanel bottom = new JPanel();
        bottom.add(btnAggiungi);
        bottom.add(btnRimuovi);
        add(bottom, BorderLayout.SOUTH);

        /* Listener */
        btnAggiungi.addActionListener(e -> aggiungiPreferito());
        btnRimuovi .addActionListener(e -> rimuoviPreferito());
    }

    /* ------------------------------------------------------------------ */
    /* Aggiornamento contenuto                                            */
    /* ------------------------------------------------------------------ */

    /** Ricarica l’elenco dei preferiti in base all’utente loggato. */
    public void refreshData() {
        if (!parent.isLoggedIn()) {
            textArea.setText("Non sei loggato!");
            return;
        }

        Utente u = parent.getUtenteCorrente();
        if (!u.isCliente()) {
            textArea.setText("Sei ristoratore, niente preferiti!");
            return;
        }

        List<String> lista = GestoreFile.caricaPreferiti(
                "data/preferiti.csv", u.getUsername());

        textArea.setText("=== I Miei Preferiti ===\n");
        lista.forEach(n -> textArea.append(n + "\n"));
        textArea.setCaretPosition(0);
    }

    /* ------------------------------------------------------------------ */
    /* Operazioni CRUD sui preferiti                                      */
    /* ------------------------------------------------------------------ */

    /** Mostra un dialog per aggiungere un ristorante ai preferiti. */
    private void aggiungiPreferito() {
        Utente u = parent.getUtenteCorrente();
        if (u == null || !u.isCliente()) {
            JOptionPane.showMessageDialog(this,
                    "Devi essere un cliente!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Ristorante> ristos =
                GestoreFile.caricaRistoranti("data/ristoranti.csv");

        if (ristos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nessun ristorante disponibile!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(
                ristos.stream()
                      .map(Ristorante::getNome)
                      .toArray(String[]::new));

        if (JOptionPane.showConfirmDialog(this, combo,
                "Aggiungi Preferito", JOptionPane.OK_CANCEL_OPTION)
            != JOptionPane.OK_OPTION) return;

        String scelto = (String) combo.getSelectedItem();
        GestoreFile.aggiungiPreferito("data/preferiti.csv",
                                      u.getUsername(), scelto);
        refreshData();
    }

    /** Dialog di rimozione di un ristorante dai preferiti. */
    private void rimuoviPreferito() {
        Utente u = parent.getUtenteCorrente();
        if (u == null || !u.isCliente()) {
            JOptionPane.showMessageDialog(this,
                    "Devi essere un cliente!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> lista = GestoreFile.caricaPreferiti(
                "data/preferiti.csv", u.getUsername());

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Non hai ristoranti preferiti!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> combo = new JComboBox<>(lista.toArray(String[]::new));

        if (JOptionPane.showConfirmDialog(this, combo,
                "Rimuovi Preferito", JOptionPane.OK_CANCEL_OPTION)
            != JOptionPane.OK_OPTION) return;

        String scelto = (String) combo.getSelectedItem();
        GestoreFile.rimuoviPreferito("data/preferiti.csv",
                                     u.getUsername(), scelto);
        refreshData();
    }
}
