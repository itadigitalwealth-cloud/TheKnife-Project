/**
 * TheKnife – Finestra principale.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */
package it.uninsubria.theknife.client.gui;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.client.gui.panels.*;
import it.uninsubria.theknife.common.model.Utente;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Finestra principale con:
 * sidebar navy + top bar bianca + area contenuto ivory.
 */
public class FancyFrame extends JFrame {

    public static final String CARD_HOME       = "Home";
    public static final String CARD_SEARCH     = "Search";
    public static final String CARD_RISTORANTI = "Ristoranti";
    public static final String CARD_RECENSIONI = "Recensioni";
    public static final String CARD_PREFERITI  = "Preferiti";
    public static final String CARD_DETAIL     = "Detail";

    private final CardLayout cardLayout  = new CardLayout();
    private final JPanel     centerPanel = new JPanel(cardLayout);

    private HomePanel             homePanel;
    private SearchPanel           searchPanel;
    private RistorantiPanel       ristorantiPanel;
    private RecensioniPanel       recensioniPanel;
    private PreferitiPanel        preferitiPanel;
    private RestaurantDetailPanel detailPanel;

    // Top bar
    private final JLabel  lblName   = new JLabel("Ospite");
    private final JLabel  lblRole   = new JLabel("");
    private final UITheme.TKButton btnAccedi = UITheme.btnPrimary("Accedi");
    private final UITheme.TKButton btnEsci   = UITheme.btnGhost("Esci");

    // Sidebar – pulsante attivo corrente
    private SidebarItem sidebarAttivo = null;

    public FancyFrame() {
        super("TheKnife");
        setSize(1340, 800);
        setMinimumSize(new Dimension(1000, 640));
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
        centerPanel.setBackground(UITheme.BG);
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(buildTopBar(),   BorderLayout.NORTH);
        add(buildSidebar(),  BorderLayout.WEST);
        add(centerPanel,     BorderLayout.CENTER);
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
                // Linea oro 2px in basso
                g.setColor(UITheme.GOLD);
                g.fillRect(0, getHeight() - 2, getWidth(), 2);
            }
        };
        bar.setBackground(UITheme.TOPBAR_BG);
        bar.setPreferredSize(new Dimension(0, UITheme.TOPBAR_H));
        bar.setBorder(new EmptyBorder(0, 18, 2, 18));

        // Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoPanel.setOpaque(false);

        // Box logo
        JPanel logoBox = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(UITheme.SIDEBAR_BG);
                g2.fillRoundRect(0, 6, 34, 30, 8, 8);
                g2.setColor(UITheme.GOLD);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                g2.drawString("TK", 6, 26);
                g2.dispose();
            }
        };
        logoBox.setOpaque(false);
        logoBox.setPreferredSize(new Dimension(38, UITheme.TOPBAR_H));

        JLabel appName = new JLabel("TheKnife");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appName.setForeground(UITheme.SIDEBAR_BG);
        appName.setBorder(new EmptyBorder(0, 8, 0, 0));

        JLabel tagline = new JLabel("  —  Guida ai ristoranti");
        tagline.setFont(UITheme.FONT_SMALL);
        tagline.setForeground(UITheme.TEXT_MUTED);

        logoPanel.add(logoBox);
        logoPanel.add(appName);
        logoPanel.add(tagline);
        bar.add(logoPanel, BorderLayout.WEST);

        // Utente
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);

        // Avatar
        JPanel avatar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(new Color(29, 158, 117));
                g2.fillOval(0, 4, 28, 28);
                g2.setColor(new Color(225, 245, 238));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                FontMetrics fm = g2.getFontMetrics();
                String ini = lblName.getText().length() > 0 ? lblName.getText().substring(0,1).toUpperCase() : "G";
                g2.drawString(ini, (28 - fm.stringWidth(ini))/2, 4 + (28 + fm.getAscent() - fm.getDescent())/2);
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(28, UITheme.TOPBAR_H); }
        };
        avatar.setOpaque(false);

        lblRole.setFont(UITheme.FONT_SMALL);
        lblRole.setForeground(UITheme.TEXT_MUTED);
        lblName.setFont(UITheme.FONT_H3);
        lblName.setForeground(UITheme.TEXT);
        btnEsci.setVisible(false);

        btnAccedi.addActionListener(e -> doLogin());
        btnEsci.addActionListener(e  -> doLogout());

        right.add(avatar);
        right.add(lblRole);
        right.add(lblName);
        right.add(btnAccedi);
        right.add(btnEsci);
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
                // Bordo destro sottile
                g.setColor(new Color(255, 255, 255, 12));
                g.fillRect(getWidth()-1, 0, 1, getHeight());
            }
        };
        sb.setLayout(new BoxLayout(sb, BoxLayout.Y_AXIS));
        sb.setPreferredSize(new Dimension(UITheme.SIDEBAR_W, 0));
        sb.setBorder(new EmptyBorder(20, 0, 20, 0));
        sb.setOpaque(true);

        sb.add(sidebarSection("Principale"));
        sb.add(sidebarItem("Home",         CARD_HOME,       null));
        sb.add(sidebarItem("Esplora",      CARD_SEARCH,     null));

        sb.add(Box.createVerticalStrut(8));
        sb.add(sidebarSection("Cliente"));
        sb.add(sidebarItem("Preferiti",    CARD_PREFERITI,  "cliente"));
        sb.add(sidebarItem("Recensioni",   CARD_RECENSIONI, null));

        sb.add(Box.createVerticalStrut(8));
        sb.add(sidebarSection("Ristoratore"));
        sb.add(sidebarItem("Miei locali",  CARD_RISTORANTI, "ristoratore"));

        sb.add(Box.createVerticalGlue());

        // Footer versione
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(12, 18, 0, 18));
        footer.setMaximumSize(new Dimension(UITheme.SIDEBAR_W, 32));
        JLabel ver = new JLabel("v2.0");
        ver.setFont(UITheme.FONT_SMALL);
        ver.setForeground(new Color(55, 65, 95));
        footer.add(ver, BorderLayout.CENTER);
        sb.add(footer);
        return sb;
    }

    private JLabel sidebarSection(String txt) {
        JLabel l = new JLabel(txt.toUpperCase());
        l.setFont(UITheme.FONT_LABEL);
        l.setForeground(UITheme.SIDEBAR_MUTED);
        l.setBorder(new EmptyBorder(12, 18, 4, 18));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private SidebarItem sidebarItem(String label, String card, String ruolo) {
        SidebarItem item = new SidebarItem(label);
        item.addActionListener(e -> {
            if ("cliente".equals(ruolo) && (!isLoggedIn() || !ClientTK.getUtenteLoggato().isCliente())) {
                showAccessDenied(); return;
            }
            if ("ristoratore".equals(ruolo) && (!isLoggedIn() || !ClientTK.getUtenteLoggato().isRistoratore())) {
                showAccessDenied(); return;
            }
            setActive(item);
            showCard(card);
            if (CARD_SEARCH.equals(card))     searchPanel.refresh();
            if (CARD_HOME.equals(card))        homePanel.refresh();
            if (CARD_RISTORANTI.equals(card)) ristorantiPanel.refreshData();
            if (CARD_RECENSIONI.equals(card)) recensioniPanel.refreshData();
            if (CARD_PREFERITI.equals(card))  preferitiPanel.refreshData();
        });
        return item;
    }

    private void setActive(SidebarItem item) {
        if (sidebarAttivo != null) sidebarAttivo.setActive(false);
        sidebarAttivo = item;
        item.setActive(true);
    }

    private void showAccessDenied() {
        JOptionPane.showMessageDialog(this,
                "Devi essere loggato per accedere a questa sezione.",
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
            lblName.setText(u.getNome() + " " + u.getCognome());
            lblRole.setText(capitalize(u.getRuolo()) + "  •  ");
            btnAccedi.setVisible(false);
            btnEsci.setVisible(true);
            getContentPane().revalidate();
            getContentPane().repaint();
        }
    }

    private void doLogout() {
        ClientTK.logout();
        lblName.setText("Ospite");
        lblRole.setText("");
        btnAccedi.setVisible(true);
        btnEsci.setVisible(false);
        if (sidebarAttivo != null) { sidebarAttivo.setActive(false); sidebarAttivo = null; }
        showCard(CARD_HOME);
        homePanel.refresh();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    // =========================================================================
    // API PUBBLICA
    // =========================================================================

    public void showCard(String name)   { cardLayout.show(centerPanel, name); }
    public boolean isLoggedIn()         { return ClientTK.isLoggato(); }
    public Utente  getUtenteCorrente()  { return ClientTK.getUtenteLoggato(); }
    public RestaurantDetailPanel getDetailPanel() { return detailPanel; }
    public SearchPanel getSearchPanel() { return searchPanel; }

    // =========================================================================
    // SIDEBAR ITEM – componente custom
    // =========================================================================

    /**
     * Voce di navigazione sidebar: sfondo navy, indicatore oro a sinistra,
     * testo che cambia colore in base allo stato active/hover.
     */
    public static class SidebarItem extends JButton {

        private boolean active = false;
        private float   hover  = 0f;
        private Timer   timer;
        private final String label;

        public SidebarItem(String label) {
            super(label);
            this.label = label;
            setFont(UITheme.FONT_BODY);
            setForeground(UITheme.SIDEBAR_TEXT);
            setHorizontalAlignment(SwingConstants.LEFT);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMaximumSize(new Dimension(UITheme.SIDEBAR_W, 40));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setBorder(new EmptyBorder(9, 18, 9, 18));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { if (!active) animateTo(1f); }
                @Override public void mouseExited (MouseEvent e) { if (!active) animateTo(0f); }
            });
        }

        public void setActive(boolean a) {
            this.active = a;
            hover = a ? 1f : 0f;
            setForeground(a ? UITheme.SIDEBAR_ACTIVE_FG : UITheme.SIDEBAR_TEXT);
            repaint();
        }

        private void animateTo(float t) {
            if (timer != null) timer.stop();
            timer = new Timer(10, null);
            timer.addActionListener(e -> {
                hover += (t - hover) * 0.28f;
                if (Math.abs(hover - t) < 0.02f) { hover = t; timer.stop(); }
                repaint();
            });
            timer.start();
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = UITheme.rh(g);
            int w = getWidth(), h = getHeight();

            if (active) {
                // Sfondo oro trasparente
                g2.setColor(UITheme.SIDEBAR_ACTIVE_BG);
                g2.fillRect(0, 0, w, h);
                // Indicatore gold sinistra
                g2.setColor(UITheme.GOLD);
                g2.fillRoundRect(0, 6, 3, h - 12, 3, 3);
            } else if (hover > 0.01f) {
                g2.setColor(new Color(255, 255, 255, (int)(hover * 13)));
                g2.fillRect(0, 0, w, h);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }
}