package theknife.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import theknife.Utente;
import theknife.gui.panels.*;

/**
 * FancyFrame con top bar a colore fisso (con logo e titolo) e menu a tendina con colori più visibili.
 * La barra dei menu viene posizionata subito sotto il top bar.
 */
public class FancyFrame extends JFrame {

    public static final String CARD_HOME = "HomePanel";
    public static final String CARD_SEARCH = "SearchPanel";
    public static final String CARD_RISTORANTI = "RistorantiPanel";
    public static final String CARD_RECENSIONI = "RecensioniPanel";
    public static final String CARD_PREFERITI = "PreferitiPanel";
    public static final String CARD_DETAIL = "RestaurantDetailPanel";

    private CardLayout cardLayout;
    private JPanel centerPanel;
    private Utente utenteCorrente;

    // Pannelli di contenuto
    private HomePanel homePanel;
    private SearchPanel searchPanel;
    private RistorantiPanel ristorantiPanel;
    private RecensioniPanel recensioniPanel;
    private PreferitiPanel preferitiPanel;
    private RestaurantDetailPanel detailPanel;

    public FancyFrame() {
        super("TheKnife - Progetto Completo");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());

        // 1. Top Bar: Pannello semplice con colore fisso e logo
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0, 102, 204)); // colore blu uniforme
        topBar.setPreferredSize(new Dimension(0, 80));

        // Logo: prova a caricare "logo.png" dal classpath; se non trovato, usa un'etichetta di default
        ImageIcon logoIcon = null;
        try {
            // Assicurati che logo.png sia nel percorso corretto, ad esempio in src/resources/
            logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
        } catch(Exception e) {
            System.out.println("Logo non trovato, uso testo di default.");
        }
        JLabel logoLabel;
        if (logoIcon != null) {
            Image img = logoIcon.getImage();
            Image scaledImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaledImg));
        } else {
            logoLabel = new JLabel("LOGO");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            logoLabel.setForeground(Color.WHITE);
        }
        topBar.add(logoLabel, BorderLayout.WEST);

        // Titolo
        JLabel title = new JLabel(" TheKnife ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        topBar.add(title, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        // 2. Barra dei menu a tendina (JMenuBar)
        JMenuBar menuBar = new JMenuBar();
        // Imposta colori e font per maggiore visibilità
        menuBar.setBackground(new Color(0, 102, 204));
        menuBar.setForeground(Color.WHITE);
        menuBar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenu menuNaviga = new JMenu("Menu");
        menuNaviga.setForeground(Color.WHITE);
        menuNaviga.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Voci di menu
        JMenuItem miHome = new JMenuItem("Home");
        JMenuItem miSearch = new JMenuItem("Ricerca");
        JMenuItem miRistoranti = new JMenuItem("Miei Ristoranti");
        JMenuItem miRecensioni = new JMenuItem("Recensioni");
        JMenuItem miPreferiti = new JMenuItem("Preferiti");
        JMenuItem miLoginLogout = new JMenuItem("Login");

        // Imposta stile per ogni voce
        for (JMenuItem item : new JMenuItem[]{miHome, miSearch, miRistoranti, miRecensioni, miPreferiti, miLoginLogout}) {
            item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            item.setForeground(Color.BLACK);
            item.setBackground(new Color(0, 102, 204));
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

        // 3. Center Panel con CardLayout per i vari pannelli
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        homePanel = new HomePanel(this);
        searchPanel = new SearchPanel(this);
        ristorantiPanel = new RistorantiPanel(this);
        recensioniPanel = new RecensioniPanel(this);
        preferitiPanel = new PreferitiPanel(this);
        detailPanel = new RestaurantDetailPanel(this);

        centerPanel.add(homePanel, CARD_HOME);
        centerPanel.add(searchPanel, CARD_SEARCH);
        centerPanel.add(ristorantiPanel, CARD_RISTORANTI);
        centerPanel.add(recensioniPanel, CARD_RECENSIONI);
        centerPanel.add(preferitiPanel, CARD_PREFERITI);
        centerPanel.add(detailPanel, CARD_DETAIL);

        add(centerPanel, BorderLayout.CENTER);

        // 4. Azioni per i JMenuItem
        miHome.addActionListener(e -> showCard(CARD_HOME));

        miSearch.addActionListener(e -> {
            showCard(CARD_SEARCH);
            searchPanel.refresh();
        });

        miRistoranti.addActionListener(e -> {
            if (!isLoggedIn() || !"ristoratore".equalsIgnoreCase(utenteCorrente.getRuolo())) {
                JOptionPane.showMessageDialog(this,
                        "Devi essere loggato come ristoratore!",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showCard(CARD_RISTORANTI);
            ristorantiPanel.refreshData();
        });

        miRecensioni.addActionListener(e -> {
            if (!isLoggedIn()) {
                JOptionPane.showMessageDialog(this,
                        "Devi essere loggato!",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showCard(CARD_RECENSIONI);
            recensioniPanel.refreshData();
        });

        miPreferiti.addActionListener(e -> {
            if (!isLoggedIn() || !"cliente".equalsIgnoreCase(utenteCorrente.getRuolo())) {
                JOptionPane.showMessageDialog(this,
                        "Devi essere loggato come cliente!",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showCard(CARD_PREFERITI);
            preferitiPanel.refreshData();
        });

        miLoginLogout.addActionListener(e -> {
            if (isLoggedIn()) {
                utenteCorrente = null;
                JOptionPane.showMessageDialog(this,
                        "Logout effettuato!",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                miLoginLogout.setText("Login");
                showCard(CARD_HOME);
            } else {
                LoginDialog dlg = new LoginDialog(this);
                dlg.setVisible(true);
                if (dlg.getUtenteLoggato() != null) {
                    utenteCorrente = dlg.getUtenteLoggato();
                    JOptionPane.showMessageDialog(this,
                            "Benvenuto, " + utenteCorrente.getNome() + "!\nRuolo: " + utenteCorrente.getRuolo(),
                            "Login OK", JOptionPane.INFORMATION_MESSAGE);
                    miLoginLogout.setText("Logout");
                }
            }
        });
    }

    public static void setCustomNimbusLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println("Impossibile impostare Nimbus LAF");
        }
    }

    public boolean isLoggedIn() {
        return utenteCorrente != null;
    }

    public Utente getUtenteCorrente() {
        return utenteCorrente;
    }

    public void showCard(String name) {
        cardLayout.show(centerPanel, name);
    }

    public RestaurantDetailPanel getDetailPanel() {
        return detailPanel;
    }
}
