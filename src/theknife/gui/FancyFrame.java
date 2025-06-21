/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello fancyframe.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import theknife.Utente;
import theknife.gui.panels.*;

/**
 * Finestra principale dell’applicazione <em>TheKnife</em>.
 *
 * <p>Include:</p>
 * <ul>
 *   <li>Barra superiore (logo + titolo)</li>
 *   <li>{@link JMenuBar} con navigazione a tendina</li>
 *   <li>Pannello centrale con <strong>CardLayout</strong> che ospita
 *       tutti i pannelli funzionali
 *       ({@link HomePanel}, {@link SearchPanel}, …).</li>
 * </ul>
 *
 * <p>Gestisce inoltre lo stato di autenticazione dell’utente e abilita
 * / disabilita le funzionalità in base al ruolo.</p>
 */
public class FancyFrame extends JFrame {

    /* ------------------------------------------------------------------ */
    /* Costanti card layout                                               */
    /* ------------------------------------------------------------------ */

    /** Identificatore card: Home. */
    public static final String CARD_HOME        = "HomePanel";
    /** Identificatore card: Ricerca ristoranti. */
    public static final String CARD_SEARCH      = "SearchPanel";
    /** Identificatore card: Ristoranti del ristoratore. */
    public static final String CARD_RISTORANTI  = "RistorantiPanel";
    /** Identificatore card: Recensioni. */
    public static final String CARD_RECENSIONI  = "RecensioniPanel";
    /** Identificatore card: Preferiti del cliente. */
    public static final String CARD_PREFERITI   = "PreferitiPanel";
    /** Identificatore card: Dettaglio ristorante. */
    public static final String CARD_DETAIL      = "RestaurantDetailPanel";

    /* ------------------------------------------------------------------ */
    /* Attributi di stato                                                 */
    /* ------------------------------------------------------------------ */

    private final CardLayout cardLayout  = new CardLayout();
    private final JPanel     centerPanel = new JPanel(cardLayout);

    /** Utente attualmente loggato; <code>null</code> se non autenticato. */
    private Utente utenteCorrente;

    /* ------------------------------------------------------------------ */
    /* Pannelli                                                           */
    /* ------------------------------------------------------------------ */

    private final HomePanel             homePanel;
    private final SearchPanel           searchPanel;
    private final RistorantiPanel       ristorantiPanel;
    private final RecensioniPanel       recensioniPanel;
    private final PreferitiPanel        preferitiPanel;
    private final RestaurantDetailPanel detailPanel;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /** Costruisce e inizializza la finestra principale. */
    public FancyFrame() {
        super("TheKnife - Progetto Completo");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Inizializzazione interfaccia e pannelli */
        initGUI();

        homePanel        = new HomePanel(this);
        searchPanel      = new SearchPanel(this);
        ristorantiPanel  = new RistorantiPanel(this);
        recensioniPanel  = new RecensioniPanel(this);
        preferitiPanel   = new PreferitiPanel(this);
        detailPanel      = new RestaurantDetailPanel(this);

        centerPanel.add(homePanel,        CARD_HOME);
        centerPanel.add(searchPanel,      CARD_SEARCH);
        centerPanel.add(ristorantiPanel,  CARD_RISTORANTI);
        centerPanel.add(recensioniPanel,  CARD_RECENSIONI);
        centerPanel.add(preferitiPanel,   CARD_PREFERITI);
        centerPanel.add(detailPanel,      CARD_DETAIL);

        add(centerPanel, BorderLayout.CENTER);
    }

    /* ------------------------------------------------------------------ */
    /* Inizializzazione GUI                                               */
    /* ------------------------------------------------------------------ */

    /** Costruisce top-bar, menu e associazione listener di navigazione. */
    private void initGUI() {
        setLayout(new BorderLayout());

        /* ---------- Top bar titolo ---------- */
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0, 102, 204));
        topBar.setPreferredSize(new Dimension(0, 80));

       

        JLabel title = new JLabel(" TheKnife ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        topBar.add(title, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        /* ---------- Barra dei menu ---------- */
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 102, 204));

        JMenu menuNaviga = new JMenu("Menu");
        menuNaviga.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuNaviga.setForeground(Color.BLACK);

        JMenuItem miHome        = new JMenuItem("Home");
        JMenuItem miSearch      = new JMenuItem("Ricerca");
        JMenuItem miRistoranti  = new JMenuItem("Miei Ristoranti");
        JMenuItem miRecensioni  = new JMenuItem("Recensioni");
        JMenuItem miPreferiti   = new JMenuItem("Preferiti");
        JMenuItem miLoginLogout = new JMenuItem("Login");

        for (JMenuItem item : new JMenuItem[] {
                miHome, miSearch, miRistoranti,
                miRecensioni, miPreferiti, miLoginLogout}) {
            item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        menuNaviga.add(miHome);
        menuNaviga.add(miSearch);
        menuNaviga.add(miRistoranti);
        menuNaviga.add(miRecensioni);
        menuNaviga.add(miPreferiti);
        menuNaviga.addSeparator();
        menuNaviga.add(miLoginLogout);
        menuBar.add(menuNaviga);
        setJMenuBar(menuBar);

        /* ---------- Listener menu ---------- */
        miHome.addActionListener(e -> showCard(CARD_HOME));

        miSearch.addActionListener(e -> {
            showCard(CARD_SEARCH);
            searchPanel.refresh();
        });

        miRistoranti.addActionListener(e -> {
            if (!isLoggedIn() || !utenteCorrente.isRistoratore()) {
                JOptionPane.showMessageDialog(this,
                        "Devi essere loggato come ristoratore!",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showCard(CARD_RISTORANTI);
            ristorantiPanel.refreshData();
        });

        miRecensioni.addActionListener(e -> {
            showCard(CARD_RECENSIONI);
            recensioniPanel.refreshData();
        });

        miPreferiti.addActionListener(e -> {
            if (!isLoggedIn() || !utenteCorrente.isCliente()) {
                JOptionPane.showMessageDialog(this,
                        "Devi essere loggato come cliente!",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showCard(CARD_PREFERITI);
            preferitiPanel.refreshData();
        });

        /* ---------- Login / Logout ---------- */
        miLoginLogout.addActionListener(e -> {
            if (isLoggedIn()) {                     // Logout
                utenteCorrente = null;
                miLoginLogout.setText("Login");
                JOptionPane.showMessageDialog(this,
                        "Logout effettuato!",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                showCard(CARD_HOME);
            } else {                               // Login
                LoginDialog dlg = new LoginDialog(this);
                dlg.setVisible(true);

                Utente u = dlg.getUtenteLoggato();
                if (u != null) {
                    utenteCorrente = u;
                    miLoginLogout.setText("Logout");
                    JOptionPane.showMessageDialog(this,
                            "Benvenuto, " + u.getNome() +
                            "!\nRuolo: " + u.getRuolo(),
                            "Login OK", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    /* ------------------------------------------------------------------ */
    /* Metodi statici di utilità                                          */
    /* ------------------------------------------------------------------ */

    /**
     * Imposta il Look & Feel Nimbus personalizzato.
     * Da invocare prima della creazione di qualsiasi componente Swing.
     */
    public static void setCustomNimbusLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.err.println("Impossibile impostare Nimbus LAF");
        }
    }

    /* ------------------------------------------------------------------ */
    /* Getter / utilità stato                                             */
    /* ------------------------------------------------------------------ */

    /**
     * @return <code>true</code> se un utente è attualmente autenticato
     */
    public boolean isLoggedIn() {
        return utenteCorrente != null;
    }

    /** @return utente corrente (o <code>null</code> se non loggato) */
    public Utente getUtenteCorrente() {
        return utenteCorrente;
    }

    /* ------------------------------------------------------------------ */
    /* Navigazione card layout                                            */
    /* ------------------------------------------------------------------ */

    /**
     * Mostra il pannello identificato dal nome card nel {@link CardLayout}.
     *
     * @param name costante <code>CARD_*</code> del pannello da visualizzare
     */
    public void showCard(String name) {
        cardLayout.show(centerPanel, name);
    }

    /* ------------------------------------------------------------------ */
    /* Accesso al pannello di dettaglio                                   */
    /* ------------------------------------------------------------------ */

    /**
     * @return pannello dei dettagli ristorante, utile per aggiornamenti
     *         dinamici al di fuori di questa classe
     */
    public RestaurantDetailPanel getDetailPanel() {
        return detailPanel;
    }
}
