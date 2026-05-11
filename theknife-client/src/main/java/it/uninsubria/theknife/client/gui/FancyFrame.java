/**
 * TheKnife – Modulo Client
 * Finestra principale – stile Luxury Editorial.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client.gui;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.client.gui.panels.*;
import it.uninsubria.theknife.common.model.Utente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Finestra principale di TheKnife con layout professionale a tre zone:
 * <ul>
 *   <li>Top bar bianca: logo + utente corrente + azioni</li>
 *   <li>Sidebar scura (midnight navy): navigazione verticale</li>
 *   <li>Area contenuto: sfondo ivory caldo con CardLayout</li>
 * </ul>
 */
public class FancyFrame extends JFrame {

    public static final String CARD_HOME       = "HomePanel";
    public static final String CARD_SEARCH     = "SearchPanel";
    public static final String CARD_RISTORANTI = "RistorantiPanel";
    public static final String CARD_RECENSIONI = "RecensioniPanel";
    public static final String CARD_PREFERITI  = "PreferitiPanel";
    public static final String CARD_DETAIL     = "RestaurantDetailPanel";

    private final CardLayout cardLayout  = new CardLayout();
    private final JPanel     centerPanel = new JPanel(cardLayout);

    private HomePanel             homePanel;
    private SearchPanel           searchPanel;
    private RistorantiPanel       ristorantiPanel;
    private RecensioniPanel       recensioniPanel;
    private PreferitiPanel        preferitiPanel;
    private RestaurantDetailPanel detailPanel;

    // Top bar
    private final JLabel  lblUserName = new JLabel("Ospite");
    private final JLabel  lblUserRole = new JLabel("");
    private final JLabel  lblDot      = new JLabel("·");
    private final UITheme.StyledButton btnLogin  = UITheme.btnPrimary("Accedi");
    private final UITheme.StyledButton btnLogout = UITheme.btnGhost("Esci");

    // Sidebar
    private JButton btnAttivo = null;

    public FancyFrame() {
        super("TheKnife");
        setSize(1340, 800);
        setMinimumSize(new Dimension(960, 640));
        setLocationRelativeTo(null);
        UITheme.apply();

        buildFrame();
        buildPanels();
        showCard(CARD_HOME);
    }

    // =========================================================================
    // STRUTTURA
    // =========================================================================

    private void buildFrame() {
        setLayout(new BorderLayout(0, 0));
        JPanel topBar = buildTopBar();
        JPanel sidebar = buildSidebar();

        centerPanel.setBackground(UITheme.BG);
        centerPanel.setBorder(new EmptyBorder(28, 28, 28, 28));

        add(topBar,       BorderLayout.NORTH);
        add(sidebar,      BorderLayout.WEST);
        add(centerPanel,  BorderLayout.CENTER);
    }

    private void buildPanels() {
        homePanel       = new HomePanel(this);
        searchPanel     = new SearchPanel(this);
        ristorantiPanel = new RistorantiPanel(this);
        recensioniPanel = new RecensioniPanel(this);
        preferitiPanel  = new PreferitiPanel(this);
        detailPanel     = new RestaurantDetailPanel(this);

        centerPanel.add(homePanel,       CARD_HOME);
        centerPanel.add(searchPanel,     CARD_SEARCH);
        centerPanel.add(ristorantiPanel, CARD_RISTORANTI);
        centerPanel.add(recensioniPanel, CARD_RECENSIONI);
        centerPanel.add(preferitiPanel,  CARD_PREFERITI);
        centerPanel.add(detailPanel,     CARD_DETAIL);
    }

    // =========================================================================
    // TOP BAR
    // =========================================================================

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Linea di accento oro in basso
                g.setColor(UITheme.GOLD);
                g.fillRect(0, getHeight()-2, getWidth(), 2);
            }
        };
        bar.setBackground(UITheme.TOPBAR_BG);
        bar.setPreferredSize(new Dimension(0, UITheme.TOPBAR_H));
        bar.setBorder(new EmptyBorder(0, 20, 2, 20));

        // SINISTRA: logo
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);

        // Rettangolo logo
        JPanel logoBox = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.SIDEBAR_BG);
                g2.fillRoundRect(0, 6, 38, 34, 8, 8);
                g2.setColor(UITheme.GOLD);
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("🔪", 8, 28);
                g2.dispose();
            }
        };
        logoBox.setOpaque(false);
        logoBox.setPreferredSize(new Dimension(42, UITheme.TOPBAR_H));

        JLabel appName = new JLabel("TheKnife");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appName.setForeground(UITheme.SIDEBAR_BG);
        appName.setBorder(new EmptyBorder(0, 8, 0, 0));

        JLabel tagline = new JLabel("  Guida ai Ristoranti");
        tagline.setFont(UITheme.FONT_SMALL);
        tagline.setForeground(UITheme.TEXT_MUTED);

        left.add(logoBox);
        left.add(appName);
        left.add(tagline);
        bar.add(left, BorderLayout.WEST);

        // DESTRA: utente
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        lblDot.setFont(UITheme.FONT_BODY);
        lblDot.setForeground(UITheme.TEXT_MUTED);
        lblDot.setVisible(false);

        lblUserRole.setFont(UITheme.FONT_SMALL);
        lblUserRole.setForeground(UITheme.TEXT_MUTED);

        lblUserName.setFont(UITheme.FONT_H3);
        lblUserName.setForeground(UITheme.TEXT);

        btnLogout.setVisible(false);
        btnLogin.addActionListener(e  -> doLogin());
        btnLogout.addActionListener(e -> doLogout());

        right.add(lblUserRole);
        right.add(lblDot);
        right.add(lblUserName);
        right.add(Box.createHorizontalStrut(4));
        right.add(btnLogin);
        right.add(btnLogout);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // =========================================================================
    // SIDEBAR
    // =========================================================================

    private JPanel buildSidebar() {
        JPanel sb = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(UITheme.SIDEBAR_BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sb.setLayout(new BoxLayout(sb, BoxLayout.Y_AXIS));
        sb.setPreferredSize(new Dimension(UITheme.SIDEBAR_W, 0));
        sb.setBorder(new EmptyBorder(24, 0, 24, 0));
        sb.setOpaque(true);

        // Sezione principale
        sb.add(UITheme.sectionLabel("  Navigazione"));
        sb.add(Box.createVerticalStrut(6));
        sb.add(navItem("  Home",         CARD_HOME,       null,           "⌂"));
        sb.add(navItem("  Esplora",      CARD_SEARCH,     null,           "◎"));

        // Sezione cliente
        sb.add(Box.createVerticalStrut(20));
        sb.add(UITheme.sectionLabel("  Area Cliente"));
        sb.add(Box.createVerticalStrut(6));
        sb.add(navItem("  Preferiti",    CARD_PREFERITI,  "cliente",      "♥"));
        sb.add(navItem("  Recensioni",   CARD_RECENSIONI, null,           "✦"));

        // Sezione ristoratore
        sb.add(Box.createVerticalStrut(20));
        sb.add(UITheme.sectionLabel("  Area Ristoratore"));
        sb.add(Box.createVerticalStrut(6));
        sb.add(navItem("  Miei Locali",  CARD_RISTORANTI, "ristoratore",  "◈"));

        sb.add(Box.createVerticalGlue());

        // Footer sidebar
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(12, 16, 0, 16));
        footer.setMaximumSize(new Dimension(UITheme.SIDEBAR_W, 40));

        JLabel ver = new JLabel("TheKnife v2.0");
        ver.setFont(UITheme.FONT_SMALL);
        ver.setForeground(new Color(50, 60, 90));
        footer.add(ver, BorderLayout.CENTER);
        sb.add(footer);
        return sb;
    }

    private JButton navItem(String label, String card, String ruolo, String icon) {
        JButton btn = new JButton() {
            private boolean hovered = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent e) { hovered=true;  repaint(); }
                    public void mouseExited (java.awt.event.MouseEvent e) { hovered=false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean active = (this == btnAttivo);
                if (active) {
                    // Indicatore attivo: rettangolo laterale oro + sfondo
                    g2.setColor(UITheme.SIDEBAR_ACTIVE);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(UITheme.GOLD);
                    g2.fillRect(0, 0, 3, getHeight());
                } else if (hovered) {
                    g2.setColor(UITheme.SIDEBAR_HOVER);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setText(icon + "  " + label.trim());
        btn.setFont(UITheme.FONT_BODY);
        btn.setForeground(UITheme.SIDEBAR_TEXT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(UITheme.SIDEBAR_W, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(10, 16, 10, 16));

        btn.addActionListener(e -> {
            if ("cliente".equals(ruolo) &&
                    (!isLoggedIn() || !ClientTK.getUtenteLoggato().isCliente())) {
                showAccessDenied("cliente"); return;
            }
            if ("ristoratore".equals(ruolo) &&
                    (!isLoggedIn() || !ClientTK.getUtenteLoggato().isRistoratore())) {
                showAccessDenied("ristoratore"); return;
            }
            setActive(btn);
            showCard(card);
            if (CARD_HOME.equals(card))      homePanel.refresh();
            if (CARD_SEARCH.equals(card))     searchPanel.refresh();
            if (CARD_RISTORANTI.equals(card)) ristorantiPanel.refreshData();
            if (CARD_RECENSIONI.equals(card)) recensioniPanel.refreshData();
            if (CARD_PREFERITI.equals(card))  preferitiPanel.refreshData();
        });
        return btn;
    }

    private void setActive(JButton btn) {
        if (btnAttivo != null) btnAttivo.repaint();
        btnAttivo = btn;
        btn.repaint();
    }

    private void showAccessDenied(String ruolo) {
        JOptionPane.showMessageDialog(this,
                "Questa sezione richiede l'accesso come " + ruolo + ".",
                "Accesso riservato", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================================
    // LOGIN / LOGOUT
    // =========================================================================

    private void doLogin() {
        LoginDialog dlg = new LoginDialog(this);
        dlg.setVisible(true);
        Utente u = dlg.getUtenteLoggato();
        if (u != null) {
            lblUserName.setText(u.getNome() + " " + u.getCognome());
            lblUserRole.setText(u.getRuolo().substring(0,1).toUpperCase()
                                + u.getRuolo().substring(1));
            lblDot.setVisible(true);
            btnLogin.setVisible(false);
            btnLogout.setVisible(true);
            getContentPane().revalidate();
            getContentPane().repaint();
        }
    }

    private void doLogout() {
        ClientTK.logout();
        lblUserName.setText("Ospite");
        lblUserRole.setText("");
        lblDot.setVisible(false);
        btnLogin.setVisible(true);
        btnLogout.setVisible(false);
        if (btnAttivo != null) { btnAttivo.repaint(); btnAttivo = null; }
        showCard(CARD_HOME);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // =========================================================================
    // API
    // =========================================================================

    public void showCard(String name)   { cardLayout.show(centerPanel, name); }
    public boolean isLoggedIn()         { return ClientTK.isLoggato(); }
    public Utente  getUtenteCorrente()  { return ClientTK.getUtenteLoggato(); }
    public RestaurantDetailPanel getDetailPanel() { return detailPanel; }
    public SearchPanel getSearchPanel() { return searchPanel; }
}

// Metodo aggiunto per HomePanel
// (già presente nella classe, ma esposto come accessor)