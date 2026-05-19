/**
 * TheKnife – Sistema di design centralizzato (versione definitiva 3.0).
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Design system TheKnife: colori, font, factory componenti.
 * Tutti i componenti custom usano Java2D puro — zero dipendenza da font Unicode.
 */
public final class UITheme {

    // =========================================================================
    // COLORI
    // =========================================================================
    public static final Color SIDEBAR_BG       = new Color(13,  17,  31);
    public static final Color SIDEBAR_ITEM_ACT = new Color(196,160, 72, 26);
    public static final Color SIDEBAR_ACTIVE_FG  = new Color(196,160, 72);
    public static final Color SIDEBAR_ACTIVE_BG  = new Color(196,160, 72, 26);
    public static final Color SIDEBAR_GOLD     = new Color(196,160, 72);
    public static final Color SIDEBAR_TEXT     = new Color(148,163,184);
    public static final Color SIDEBAR_MUTED    = new Color( 71, 85,105);
    public static final Color SIDEBAR_SECTION  = new Color( 51, 65, 85);

    public static final Color TOPBAR_BG        = Color.WHITE;
    public static final Color BG               = new Color(248,250,252);
    public static final Color CARD             = Color.WHITE;
    public static final Color CARD_BORDER      = new Color(226,232,240);
    public static final Color CARD_HOV_BORDER  = new Color(148,163,184);
    public static final Color CARD_HOV_BG      = new Color(239,246,255);

    public static final Color GOLD             = new Color(196,160, 72);
    public static final Color GOLD_HOVER       = new Color(168,136, 48);
    public static final Color GOLD_DARK        = new Color(133, 79, 11);
    public static final Color GOLD_LIGHT       = new Color(250,238,218);

    public static final Color TEXT             = new Color( 15, 23, 42);
    public static final Color TEXT_SECONDARY   = new Color( 71, 85,105);
    public static final Color TEXT_MUTED       = new Color(148,163,184);

    public static final Color SUCCESS_BG       = new Color(220,252,231);
    public static final Color SUCCESS_FG       = new Color( 22,101, 52);
    public static final Color INFO_BG          = new Color(219,234,254);
    public static final Color INFO_FG          = new Color( 29, 78,216);
    public static final Color DANGER           = new Color(220, 38, 38);
    public static final Color DANGER_BG        = new Color(254,226,226);
    public static final Color STAR             = new Color(245,158, 11);
    public static final Color PRIMARY_LIGHT    = new Color(239,246,255);
    public static final Color PRIMARY          = new Color( 37, 99,235);

    // =========================================================================
    // FONT
    // =========================================================================
    private static final String F = "Segoe UI";
    public static final Font FONT_DISPLAY = new Font(F, Font.BOLD,  28);
    public static final Font FONT_H1      = new Font(F, Font.BOLD,  20);
    public static final Font FONT_H2      = new Font(F, Font.BOLD,  15);
    public static final Font FONT_H3      = new Font(F, Font.BOLD,  13);
    public static final Font FONT_BODY    = new Font(F, Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font(F, Font.PLAIN, 11);
    public static final Font FONT_LABEL   = new Font(F, Font.BOLD,  10);

    // =========================================================================
    // DIMENSIONI
    // =========================================================================
    public static final int SIDEBAR_W   = 210;
    public static final int TOPBAR_H    =  52;
    public static final int CARD_RADIUS =  10;
    public static final int BTN_RADIUS  =   7;

    // =========================================================================
    // FACTORY – pulsanti
    // =========================================================================
    public static TKButton btnPrimary(String txt) {
        return new TKButton(txt, GOLD, GOLD_HOVER, Color.WHITE, false);
    }
    public static TKButton btnGhost(String txt) {
        return new TKButton(txt, new Color(0,0,0,0), new Color(0,0,0,20), TEXT_SECONDARY, true);
    }
    public static TKButton btnDanger(String txt) {
        return new TKButton(txt, DANGER, new Color(185,28,28), Color.WHITE, false);
    }

    // =========================================================================
    // FACTORY – campi testo
    // =========================================================================
    public static JTextField textField(int cols) {
        JTextField tf = new JTextField(cols) {
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = rh(g);
                g2.setColor(hasFocus() ? GOLD : CARD_BORDER);
                g2.setStroke(new BasicStroke(hasFocus() ? 1.5f : 0.8f));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,BTN_RADIUS*2,BTN_RADIUS*2);
                g2.dispose();
            }
        };
        tf.setFont(FONT_BODY); tf.setForeground(TEXT); tf.setBackground(Color.WHITE);
        tf.setOpaque(true); tf.setBorder(new EmptyBorder(8,12,8,12));
        tf.setPreferredSize(new Dimension(tf.getPreferredSize().width, 36));
        return tf;
    }

    public static JPasswordField passwordField(int cols) {
        JPasswordField pf = new JPasswordField(cols) {
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = rh(g);
                g2.setColor(hasFocus() ? GOLD : CARD_BORDER);
                g2.setStroke(new BasicStroke(hasFocus() ? 1.5f : 0.8f));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,BTN_RADIUS*2,BTN_RADIUS*2);
                g2.dispose();
            }
        };
        pf.setFont(FONT_BODY); pf.setForeground(TEXT); pf.setBackground(Color.WHITE);
        pf.setOpaque(true); pf.setBorder(new EmptyBorder(8,12,8,12));
        pf.setPreferredSize(new Dimension(pf.getPreferredSize().width, 36));
        return pf;
    }

    // =========================================================================
    // FACTORY – badge e labels
    // =========================================================================
    public static JLabel badgeCucina(String txt) { return badge(txt, GOLD_LIGHT, GOLD_DARK); }
    public static JLabel pillDelivery()          { return badge("Delivery",    SUCCESS_BG, SUCCESS_FG); }
    public static JLabel pillPrenotazione()      { return badge("Prenotazione",INFO_BG,    INFO_FG);    }

    public static JLabel badge(String txt, Color bg, Color fg) {
        JLabel l = new JLabel(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = rh(g);
                g2.setColor(bg); g2.fillRoundRect(0,0,getWidth(),getHeight(),5,5);
                g2.dispose(); super.paintComponent(g);
            }
        };
        l.setFont(FONT_LABEL); l.setForeground(fg);
        l.setOpaque(false); l.setBorder(new EmptyBorder(2,7,2,7));
        return l;
    }

    /**
     * Stelle disegnate con Java2D – non dipende da font Unicode.
     * Mostra stelle piene e vuote, con valore numerico e conteggio.
     */
    public static JLabel starLabel(double media, int count) {
        String txt;
        if (media == 0 && count == 0) {
            txt = "Nessuna recensione";
        } else {
            txt = count > 0
                ? String.format("%.1f", media) + "  (" + count + " rec.)"
                : String.format("%.1f", media);
        }
        // Usiamo un pannello custom che dipinge le stelle + testo
        JLabel l = new JLabel(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = rh(g);
                if (media > 0) {
                    int s = (int) Math.round(Math.min(media, 5));
                    float sz = 9f, gap = 2f;
                    float totalW = 5 * sz + 4 * gap;
                    float x = 0, cy = getHeight() / 2f;
                    g2.setColor(STAR);
                    for (int i = 0; i < 5; i++) {
                        float cx = x + i * (sz + gap) + sz / 2;
                        drawStar(g2, cx, cy, sz/2, sz/2*0.42f, i < s);
                    }
                    // Sposta il testo a destra delle stelle
                    g2.translate(totalW + 5, 0);
                }
                g2.setColor(media == 0 ? TEXT_MUTED : TEXT_SECONDARY);
                super.paintComponent(g2);
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                if (media > 0) d.width += 55; // spazio stelle
                return d;
            }
        };
        l.setFont(FONT_SMALL);
        l.setForeground(media == 0 ? TEXT_MUTED : TEXT_SECONDARY);
        l.setOpaque(false);
        return l;
    }

    public static CardPanel cardPanel(LayoutManager layout) { return new CardPanel(layout); }
    public static JLabel    fieldLabel(String txt)          {
        JLabel l = new JLabel(txt); l.setFont(FONT_LABEL); l.setForeground(TEXT_MUTED); return l;
    }
    public static StarChip starChip(String label, int value) { return new StarChip(label, value); }

    /**
     * Header navy standard per tutti i dialog — usa setBackground + setOpaque
     * invece di custom painting, garantendo rendering corretto su tutti i L&F.
     *
     * @param title    titolo bianco grande
     * @param subtitle sottotitolo muted (null per omettere)
     */
    public static JPanel dialogHeader(String title, String subtitle) {
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(SIDEBAR_BG);
        hdr.setOpaque(true);
        int h = subtitle != null ? 64 : 50;
        hdr.setPreferredSize(new Dimension(0, h));
        hdr.setBorder(new EmptyBorder(0, 22, 0, 22));

        JPanel content = new JPanel(subtitle != null ? new BorderLayout(0,3) : new BorderLayout());
        content.setOpaque(false);
        JLabel t = new JLabel(title); t.setFont(FONT_H2); t.setForeground(Color.WHITE);
        content.add(t, subtitle != null ? BorderLayout.NORTH : BorderLayout.CENTER);
        if (subtitle != null) {
            JLabel s = new JLabel(subtitle); s.setFont(FONT_SMALL); s.setForeground(SIDEBAR_MUTED);
            content.add(s, BorderLayout.SOUTH);
        }
        hdr.add(content, BorderLayout.CENTER);

        // Linea oro in basso
        JPanel gold = new JPanel(); gold.setBackground(GOLD); gold.setOpaque(true);
        gold.setPreferredSize(new Dimension(0, 2)); gold.setMinimumSize(new Dimension(0, 2));
        hdr.add(gold, BorderLayout.SOUTH);
        return hdr;
    }

    public static void apply() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        UIManager.put("Panel.background",              BG);
        UIManager.put("OptionPane.background",         CARD);
        UIManager.put("ScrollBar.width",               8);
        UIManager.put("ScrollBar.thumb",               CARD_BORDER);
        UIManager.put("ScrollBar.track",               BG);
        UIManager.put("ComboBox.background",           Color.WHITE);
        UIManager.put("ComboBox.foreground",           TEXT);
        UIManager.put("ComboBox.font",                 FONT_BODY);
        UIManager.put("TextField.caretForeground",     GOLD);
        UIManager.put("PasswordField.caretForeground", GOLD);
        UIManager.put("CheckBox.background",           CARD);
        UIManager.put("CheckBox.foreground",           TEXT);
        UIManager.put("CheckBox.font",                 FONT_BODY);
        UIManager.put("Label.font",                    FONT_BODY);
        UIManager.put("Label.foreground",              TEXT);
        UIManager.put("ToolTip.background",            SIDEBAR_BG);
        UIManager.put("ToolTip.foreground",            Color.WHITE);
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    /** Graphics2D con antialiasing attivo. Pubblico – usato dai pannelli. */
    public static Graphics2D rh(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,     RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,        RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    /**
     * Disegna una stella a 5 punte con Java2D – non usa font Unicode.
     * @param filled true = piena, false = solo contorno
     */
    public static void drawStar(Graphics2D g2, float cx, float cy, float outer, float inner, boolean filled) {
        Path2D star = new Path2D.Float();
        for (int i = 0; i < 10; i++) {
            double angle = -Math.PI / 2 + i * Math.PI / 5;
            float r = (i % 2 == 0) ? outer : inner;
            float x = cx + r * (float) Math.cos(angle);
            float y = cy + r * (float) Math.sin(angle);
            if (i == 0) star.moveTo(x, y); else star.lineTo(x, y);
        }
        star.closePath();
        if (filled) {
            g2.fill(star);
        } else {
            Color prev = g2.getColor();
            g2.setColor(new Color(prev.getRed(), prev.getGreen(), prev.getBlue(), 90));
            g2.setStroke(new BasicStroke(0.8f));
            g2.draw(star);
            g2.setColor(prev);
        }
    }

    public static void flashRed(JComponent c) {
        Color orig = c.getBackground();
        c.setBackground(new Color(255,240,240));
        new Timer(700, e -> { c.setBackground(orig); ((Timer)e.getSource()).stop(); }).start();
        c.requestFocus();
    }

    public static JScrollPane scrollPane(Component view) {
        JScrollPane s = new JScrollPane(view);
        s.setBorder(null); s.setBackground(BG);
        s.getViewport().setBackground(BG);
        s.getVerticalScrollBar().setUnitIncrement(18);
        return s;
    }

    private UITheme() {}

    // =========================================================================
    // TKButton
    // =========================================================================
    public static class TKButton extends JButton {
        private final Color bg, bgHov, fg;
        private final boolean ghost;
        private float hov = 0f;
        private Timer hovTimer;

        public TKButton(String txt, Color bg, Color bgHov, Color fg, boolean ghost) {
            super(txt); this.bg=bg; this.bgHov=bgHov; this.fg=fg; this.ghost=ghost;
            setFont(FONT_H3); setForeground(fg);
            setFocusPainted(false); setBorderPainted(false); setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMargin(new Insets(8,18,8,18));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { animTo(1f); }
                @Override public void mouseExited (MouseEvent e) { animTo(0f); }
            });
        }

        private void animTo(float t) {
            if (hovTimer!=null) hovTimer.stop();
            hovTimer = new Timer(10, null);
            hovTimer.addActionListener(e -> {
                hov += (t-hov)*0.3f;
                if (Math.abs(hov-t)<0.02f){hov=t;hovTimer.stop();}
                repaint();
            });
            hovTimer.start();
        }

        private Color lerp(Color a, Color b, float t) {
            return new Color(
                clamp((int)(a.getRed()  +(b.getRed()  -a.getRed())  *t)),
                clamp((int)(a.getGreen()+(b.getGreen()-a.getGreen())*t)),
                clamp((int)(a.getBlue() +(b.getBlue() -a.getBlue()) *t)),
                clamp((int)(a.getAlpha()+(b.getAlpha()-a.getAlpha())*t)));
        }
        private int clamp(int v){return Math.max(0,Math.min(255,v));}

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = rh(g);
            int w=getWidth(), h=getHeight(), r=BTN_RADIUS*2;
            Color c = getModel().isPressed() ? bgHov : lerp(bg, bgHov, hov);
            if (!ghost) { g2.setColor(new Color(0,0,0,12)); g2.fillRoundRect(1,2,w-2,h-1,r,r); }
            g2.setColor(c); g2.fillRoundRect(0,0,w,h-1,r,r);
            if (ghost) {
                g2.setColor(lerp(CARD_BORDER, TEXT_MUTED, hov));
                g2.setStroke(new BasicStroke(0.8f)); g2.drawRoundRect(0,0,w-1,h-2,r,r);
            }
            g2.dispose(); super.paintComponent(g);
        }
    }

    // =========================================================================
    // CardPanel
    // =========================================================================
    public static class CardPanel extends JPanel {
        private static final int PAD = 8;
        private boolean hovered = false;

        public CardPanel(LayoutManager layout) {
            super(layout); setOpaque(false);
            setBorder(new EmptyBorder(PAD, PAD, PAD+2, PAD+2));
        }

        public void setHovered(boolean h) { hovered=h; repaint(); }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = rh(g);
            int x=PAD/2, y=PAD/2, w=getWidth()-PAD, h=getHeight()-PAD-2, r=CARD_RADIUS*2;
            g2.setColor(new Color(0,0,0,7));  g2.fillRoundRect(x+1,y+2,w,h+1,r,r);
            g2.setColor(new Color(0,0,0,4));  g2.fillRoundRect(x+2,y+4,w,h+2,r,r);
            g2.setColor(CARD);                g2.fillRoundRect(x,y,w,h,r,r);
            g2.setColor(hovered ? CARD_HOV_BORDER : CARD_BORDER);
            g2.setStroke(new BasicStroke(hovered ? 1f : 0.8f));
            g2.drawRoundRect(x,y,w-1,h-1,r,r);
            g2.dispose();
        }
    }

    // =========================================================================
    // StarChip – stelle disegnate con Java2D, zero Unicode
    // =========================================================================
    /**
     * Chip filtro stelle. Per value=0 mostra testo "Tutte", per 1-5 mostra
     * le stelle disegnate con Java2D puro — funziona su tutti i sistemi.
     */
    public static class StarChip extends JButton {
        private final int value;
        private boolean selected = false;
        private float hov = 0f;
        private Timer hovTimer;

        public StarChip(String label, int value) {
            super(value == 0 ? label : ""); // Solo "Tutte" ha testo; le stelle si disegnano
            this.value = value;
            setFont(FONT_LABEL); setForeground(TEXT_SECONDARY);
            setFocusPainted(false); setBorderPainted(false); setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMargin(new Insets(5, value == 0 ? 12 : 8, 5, value == 0 ? 12 : 8));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { if (!selected) animTo(1f); }
                @Override public void mouseExited (MouseEvent e) { if (!selected) animTo(0f); }
            });
        }

        public void setSelected(boolean sel) {
            selected = sel; hov = sel ? 1f : 0f;
            setForeground(sel ? Color.WHITE : TEXT_SECONDARY);
            repaint();
        }
        public boolean isSelected() { return selected; }
        public int     getValue()   { return value; }

        private void animTo(float t) {
            if (hovTimer!=null) hovTimer.stop();
            hovTimer = new Timer(10, null);
            hovTimer.addActionListener(e -> {
                hov+=(t-hov)*0.3f;
                if(Math.abs(hov-t)<0.02f){hov=t;hovTimer.stop();}
                repaint();
            });
            hovTimer.start();
        }

        @Override public Dimension getPreferredSize() {
            if (value > 0) return new Dimension(78, 28);
            Dimension d = super.getPreferredSize();
            d.height = 28; return d;
        }
        @Override public Dimension getMinimumSize()  { return getPreferredSize(); }
        @Override public Dimension getMaximumSize()  { return getPreferredSize(); }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = rh(g);
            int w=getWidth(), h=getHeight(), r=16;

            // Sfondo pillola
            if (selected) {
                g2.setColor(GOLD); g2.fillRoundRect(0,0,w,h,r,r);
            } else {
                g2.setColor(BG); g2.fillRoundRect(0,0,w,h,r,r);
                if (hov > 0.01f) {
                    g2.setColor(new Color(0,0,0,(int)(hov*18)));
                    g2.fillRoundRect(0,0,w,h,r,r);
                }
                g2.setColor(CARD_BORDER);
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawRoundRect(0,0,w-1,h-1,r,r);
            }

            if (value > 0) {
                // Disegna stelle con Java2D — nessun Unicode
                float outer = 5.5f, inner = 2.3f;
                float totalW = 5 * outer * 2 + 4 * 2.5f;
                float startX = (w - totalW) / 2f + outer;
                float cy = h / 2f;

                g2.setColor(selected ? Color.WHITE : STAR);
                for (int i = 0; i < 5; i++) {
                    float cx = startX + i * (outer * 2 + 2.5f);
                    drawStar(g2, cx, cy, outer, inner, i < value);
                }
            }
            g2.dispose();

            // Per "Tutte" (value==0), lascia che super disegni il testo
            if (value == 0) super.paintComponent(g);
        }
    }

    // =========================================================================
    // SidebarItem
    // =========================================================================
    public static class SidebarItem extends JButton {
        private boolean active = false;
        private float hov = 0f;
        private Timer hovTimer;

        public SidebarItem(String label) {
            super(label);
            setFont(FONT_BODY); setForeground(SIDEBAR_TEXT);
            setHorizontalAlignment(SwingConstants.LEFT);
            setBorderPainted(false); setFocusPainted(false); setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMaximumSize(new Dimension(SIDEBAR_W, 40));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setBorder(new EmptyBorder(9,18,9,18));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { if (!active) animTo(1f); }
                @Override public void mouseExited (MouseEvent e) { if (!active) animTo(0f); }
            });
        }

        public void setActive(boolean a) {
            active=a; hov=a?1f:0f; setForeground(a?SIDEBAR_GOLD:SIDEBAR_TEXT); repaint();
        }

        private void animTo(float t) {
            if (hovTimer!=null) hovTimer.stop();
            hovTimer=new Timer(10,null);
            hovTimer.addActionListener(e->{hov+=(t-hov)*0.3f;if(Math.abs(hov-t)<0.02f){hov=t;hovTimer.stop();}repaint();});
            hovTimer.start();
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = rh(g);
            int w=getWidth(), h=getHeight();
            if (active) {
                g2.setColor(SIDEBAR_ITEM_ACT); g2.fillRect(0,0,w,h);
                g2.setColor(SIDEBAR_GOLD); g2.fillRoundRect(0,5,3,h-10,3,3);
            } else if (hov>0.01f) {
                g2.setColor(new Color(255,255,255,(int)(hov*14))); g2.fillRect(0,0,w,h);
            }
            g2.dispose(); super.paintComponent(g);
        }
    }
}