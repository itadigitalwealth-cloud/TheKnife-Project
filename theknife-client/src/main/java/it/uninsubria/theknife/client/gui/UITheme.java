/**
 * TheKnife – Modulo Client
 * Sistema di design centralizzato – stile Luxury Editorial.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client.gui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Centralizza colori, font, spaziature e factory di componenti stilizzati.
 * <p>
 * Palette ispirata al luxury dining: midnight navy per la sidebar,
 * warm ivory per il contenuto, gold per gli accenti.
 * </p>
 */
public final class UITheme {

    // =========================================================================
    // PALETTE
    // =========================================================================

    /** Sfondo sidebar – midnight navy profondo. */
    public static final Color SIDEBAR_BG      = new Color(13, 17, 31);
    /** Hover sulla sidebar. */
    public static final Color SIDEBAR_HOVER   = new Color(26, 32, 54);
    /** Voce sidebar selezionata. */
    public static final Color SIDEBAR_ACTIVE  = new Color(38, 47, 77);
    /** Testo sidebar principale. */
    public static final Color SIDEBAR_TEXT    = new Color(226, 232, 240);
    /** Testo sidebar secondario / label sezione. */
    public static final Color SIDEBAR_MUTED   = new Color(100, 116, 139);

    /** Sfondo top bar – bianco puro. */
    public static final Color TOPBAR_BG       = Color.WHITE;
    /** Bordo sottile della top bar. */
    public static final Color TOPBAR_BORDER   = new Color(241, 245, 249);

    /** Sfondo principale del contenuto – warm ivory. */
    public static final Color BG              = new Color(250, 249, 247);
    /** Sfondo card – bianco. */
    public static final Color CARD            = Color.WHITE;
    /** Bordo card. */
    public static final Color CARD_BORDER     = new Color(234, 234, 230);

    /** Accento oro – primario per azioni importanti. */
    public static final Color GOLD            = new Color(196, 160, 72);
    /** Oro più scuro (hover / pressed). */
    public static final Color GOLD_DARK       = new Color(168, 136, 56);
    /** Oro chiaro (sfondo badge). */
    public static final Color GOLD_LIGHT      = new Color(255, 248, 230);

    /** Testo principale – quasi nero caldo. */
    public static final Color TEXT            = new Color(18, 18, 24);
    /** Testo secondario. */
    public static final Color TEXT_MUTED      = new Color(107, 114, 128);
    /** Placeholder nei campi. */
    public static final Color TEXT_PLACEHOLDER = new Color(180, 180, 180);

    /** Successo. */
    public static final Color SUCCESS         = new Color(22, 163, 74);
    /** Errore. */
    public static final Color DANGER          = new Color(220, 38, 38);
    /** Stelle rating. */
    public static final Color STAR            = new Color(245, 158, 11);

    // =========================================================================
    // TIPOGRAFIA
    // =========================================================================

    private static final String F = "Segoe UI";

    public static final Font FONT_DISPLAY  = new Font(F, Font.BOLD,  32);
    public static final Font FONT_H1       = new Font(F, Font.BOLD,  22);
    public static final Font FONT_H2       = new Font(F, Font.BOLD,  16);
    public static final Font FONT_H3       = new Font(F, Font.BOLD,  13);
    public static final Font FONT_BODY     = new Font(F, Font.PLAIN, 13);
    public static final Font FONT_SMALL    = new Font(F, Font.PLAIN, 11);
    public static final Font FONT_LABEL    = new Font(F, Font.BOLD,  11);
    public static final Font FONT_MONO     = new Font("Consolas", Font.PLAIN, 13);

    // =========================================================================
    // DIMENSIONI
    // =========================================================================

    public static final int SIDEBAR_W  = 220;
    public static final int TOPBAR_H   = 56;
    public static final int RADIUS     = 10;
    public static final int RADIUS_SM  = 6;

    // =========================================================================
    // FACTORY COMPONENTI
    // =========================================================================

    /**
     * Pulsante primario oro, angoli arrotondati.
     */
    public static StyledButton btnPrimary(String txt) {
        return new StyledButton(txt, GOLD, GOLD_DARK, Color.WHITE);
    }

    /**
     * Pulsante ghost (bordo, sfondo trasparente).
     */
    public static StyledButton btnGhost(String txt) {
        return new StyledButton(txt, new Color(0,0,0,0), CARD_BORDER, TEXT);
    }

    /**
     * Pulsante pericolo.
     */
    public static StyledButton btnDanger(String txt) {
        return new StyledButton(txt, DANGER, new Color(185,28,28), Color.WHITE);
    }

    /**
     * Campo di testo con stile elegante.
     */
    public static JTextField textField(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setFont(FONT_BODY);
        tf.setForeground(TEXT);
        tf.setBackground(Color.WHITE);
        tf.setBorder(new ElegantBorder(CARD_BORDER, GOLD, RADIUS_SM));
        tf.setPreferredSize(new Dimension(tf.getPreferredSize().width, 38));
        return tf;
    }

    /**
     * Campo password con stile elegante.
     */
    public static JPasswordField passwordField(int cols) {
        JPasswordField pf = new JPasswordField(cols);
        pf.setFont(FONT_BODY);
        pf.setForeground(TEXT);
        pf.setBackground(Color.WHITE);
        pf.setBorder(new ElegantBorder(CARD_BORDER, GOLD, RADIUS_SM));
        pf.setPreferredSize(new Dimension(pf.getPreferredSize().width, 38));
        return pf;
    }

    /**
     * Panel card con ombra sottile e angoli arrotondati.
     */
    public static ShadowPanel cardPanel(LayoutManager layout) {
        return new ShadowPanel(layout);
    }

    /**
     * Label piccola uppercase come intestazione di sezione.
     */
    public static JLabel sectionLabel(String txt) {
        JLabel l = new JLabel(txt.toUpperCase());
        l.setFont(FONT_LABEL);
        l.setForeground(SIDEBAR_MUTED);
        l.setBorder(new EmptyBorder(0, 14, 4, 0));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    /**
     * Applica le impostazioni UIManager globali.
     */
    public static void apply() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        UIManager.put("OptionPane.background",        CARD);
        UIManager.put("Panel.background",             BG);
        UIManager.put("ScrollBar.width",              8);
        UIManager.put("ScrollBar.thumbDarkShadow",    BG);
        UIManager.put("ScrollBar.thumb",              CARD_BORDER);
        UIManager.put("ScrollBar.track",              BG);
        UIManager.put("ScrollBar.background",         BG);
        UIManager.put("ComboBox.background",          Color.WHITE);
        UIManager.put("ComboBox.foreground",          TEXT);
        UIManager.put("TextField.caretForeground",    GOLD);
    }

    private UITheme() {}

    // =========================================================================
    // COMPONENTI CUSTOM
    // =========================================================================

    /**
     * Pulsante stilizzato con angoli arrotondati, hover e pressione animati.
     */
    public static class StyledButton extends JButton {
        private final Color bg, bgHover, fg;
        private boolean hovered = false, pressed = false;

        public StyledButton(String txt, Color bg, Color bgHover, Color fg) {
            super(txt);
            this.bg = bg; this.bgHover = bgHover; this.fg = fg;
            setFont(FONT_H3);
            setForeground(fg);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMargin(new Insets(8, 20, 8, 20));
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { hovered=true;  repaint(); }
                public void mouseExited (java.awt.event.MouseEvent e) { hovered=false; repaint(); }
                public void mousePressed(java.awt.event.MouseEvent e) { pressed=true;  repaint(); }
                public void mouseReleased(java.awt.event.MouseEvent e){ pressed=false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color c = pressed ? bgHover : (hovered ? bgHover : bg);
            // Ombra leggera
            if (!c.equals(new Color(0,0,0,0))) {
                g2.setColor(new Color(0,0,0,12));
                g2.fillRoundRect(1, 2, getWidth()-2, getHeight()-1, RADIUS*2, RADIUS*2);
            }
            g2.setColor(c);
            g2.fillRoundRect(0, 0, getWidth(), getHeight()-1, RADIUS*2, RADIUS*2);
            // Bordo ghost
            if (c.equals(new Color(0,0,0,0)) || c.getAlpha() == 0) {
                g2.setColor(CARD_BORDER);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-2, RADIUS*2, RADIUS*2);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Panel con ombra diffusa e angoli arrotondati – simula una card.
     */
    public static class ShadowPanel extends JPanel {
        private static final int SHADOW = 6;

        public ShadowPanel(LayoutManager layout) {
            super(layout);
            setOpaque(false);
            setBorder(new EmptyBorder(SHADOW, SHADOW, SHADOW+2, SHADOW+2));
        }

        public ShadowPanel() { this(new BorderLayout()); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = SHADOW, y = SHADOW, w = getWidth()-SHADOW*2, h = getHeight()-SHADOW*2;
            // Strati ombra
            for (int i = SHADOW; i > 0; i--) {
                g2.setColor(new Color(0, 0, 0, 4));
                g2.fillRoundRect(x-i+2, y-i+3, w+i*2-2, h+i*2-2, RADIUS*2+i, RADIUS*2+i);
            }
            g2.setColor(CARD);
            g2.fillRoundRect(x, y, w, h, RADIUS*2, RADIUS*2);
            g2.setColor(CARD_BORDER);
            g2.setStroke(new BasicStroke(0.8f));
            g2.drawRoundRect(x, y, w-1, h-1, RADIUS*2, RADIUS*2);
            g2.dispose();
        }
    }

    /**
     * Bordo elegante con animazione di focus (cambia colore al focus).
     */
    public static class ElegantBorder extends AbstractBorder {
        private final Color normal, focus;
        private final int radius;

        public ElegantBorder(Color normal, Color focus, int radius) {
            this.normal = normal; this.focus = focus; this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            boolean focused = c.hasFocus();
            g2.setColor(focused ? focus : normal);
            g2.setStroke(new BasicStroke(focused ? 1.6f : 1.0f));
            g2.drawRoundRect(x, y, w-1, h-1, radius*2, radius*2);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) { return new Insets(8, 12, 8, 12); }
        @Override
        public Insets getBorderInsets(Component c, Insets i) {
            i.set(8, 12, 8, 12); return i;
        }
    }
}