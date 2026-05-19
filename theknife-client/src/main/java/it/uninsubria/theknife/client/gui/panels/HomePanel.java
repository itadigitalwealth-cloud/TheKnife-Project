/**
 * TheKnife – Schermata Home Premium Edition.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui.panels;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.client.gui.*;
import it.uninsubria.theknife.common.CommandType;
import it.uninsubria.theknife.common.Request;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Ristorante;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;

/**
 * Home panel: hero + ristoranti per città/domicilio + feature card + CTA Premium.
 */
public class HomePanel extends GradientPanel {

    private final FancyFrame parent;

    private final JTextField      tfCitta  = UITheme.textField(24);
    private final UITheme.TKButton btnCerca = UITheme.btnPrimary("Cerca");

    private final JLabel lblTitolo = new JLabel("Ristoranti disponibili");
    private final JLabel lblSub    = new JLabel("Inserisci una città per iniziare.");
    private final JPanel gridPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 12, 12));

    public HomePanel(FancyFrame parent) {
        super(new BorderLayout(0, 0));
        this.parent = parent;
        setBackground(UITheme.BG);
        initUI();
    }

    // =========================================================================
    // INIT
    // =========================================================================

    private void initUI() {
        // Hero fisso in alto
        add(buildHero(), BorderLayout.NORTH);

        // Corpo scrollabile
        JPanel body = new JPanel() {
            @Override public Dimension getMaximumSize() {
                return new Dimension(Short.MAX_VALUE, getPreferredSize().height);
            }
        };
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UITheme.BG);
        body.setBorder(new EmptyBorder(24, 0, 32, 0));

        body.add(buildRistorantiSection());
        body.add(Box.createVerticalStrut(40));
        body.add(buildFeatureRow());
        body.add(Box.createVerticalStrut(44));
        body.add(buildCTAPremium()); // La nuova CTA raffinata e riprogettata
        body.add(Box.createVerticalStrut(24));

        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.setBackground(UITheme.BG);
        scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }

    // =========================================================================
    // HERO
    // =========================================================================

    private JPanel buildHero() {
        JPanel hero = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                GradientPaint gp = new GradientPaint(0, 0, UITheme.SIDEBAR_BG,
                        getWidth(), 0, new Color(20, 31, 58));
                g2.setPaint(gp); g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(196, 160, 72, 14));
                g2.fillOval(getWidth() - 180, -80, 320, 320);
                g2.setColor(new Color(196, 160, 72, 6));
                g2.fillOval(-80, getHeight() - 100, 200, 200);
                g2.setColor(UITheme.GOLD);
                g2.fillRect(0, getHeight() - 2, getWidth(), 2);
                g2.dispose();
            }
        };
        hero.setOpaque(true);
        hero.setPreferredSize(new Dimension(0, 220));
        hero.setBorder(new EmptyBorder(0, 36, 0, 36));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        JLabel tag = new JLabel("La tua guida ai migliori ristoranti");
        tag.setFont(UITheme.FONT_LABEL); tag.setForeground(UITheme.GOLD);
        gc.gridy = 0; gc.insets = new Insets(0, 0, 8, 0); hero.add(tag, gc);

        JLabel title = new JLabel("<html><b>Scopri dove</b><br><b>mangiare bene.</b></html>");
        title.setFont(UITheme.FONT_DISPLAY); title.setForeground(Color.WHITE);
        gc.gridy = 1; gc.insets = new Insets(0, 0, 8, 0); hero.add(title, gc);

        JLabel desc = new JLabel("Cerca per città · filtra per cucina e budget · leggi recensioni autentiche.");
        desc.setFont(UITheme.FONT_SMALL); desc.setForeground(new Color(148, 163, 184));
        gc.gridy = 2; gc.insets = new Insets(0, 0, 16, 0); hero.add(desc, gc);

        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        bar.setOpaque(false);
        tfCitta.setPreferredSize(new Dimension(280, 36));
        tfCitta.addActionListener(e -> cercaPerCitta());
        btnCerca.addActionListener(e -> cercaPerCitta());
        bar.add(tfCitta); bar.add(btnCerca);
        gc.gridy = 3; gc.insets = new Insets(0, 0, 0, 0);
        gc.fill = GridBagConstraints.NONE;
        hero.add(bar, gc);
        return hero;
    }

    // =========================================================================
    // SEZIONE RISTORANTI
    // =========================================================================

    private JPanel buildRistorantiSection() {
        JPanel section = new JPanel(new BorderLayout(0, 12));
        section.setOpaque(false);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setBorder(new EmptyBorder(0, 36, 0, 36));

        JPanel hdr = new JPanel(new BorderLayout(0, 2)); hdr.setOpaque(false);
        lblTitolo.setFont(UITheme.FONT_H1); lblTitolo.setForeground(UITheme.TEXT);
        lblSub.setFont(UITheme.FONT_SMALL);  lblSub.setForeground(UITheme.TEXT_MUTED);
        hdr.add(lblTitolo, BorderLayout.NORTH);
        hdr.add(lblSub,    BorderLayout.CENTER);
        section.add(hdr, BorderLayout.NORTH);

        gridPanel.setBackground(UITheme.BG);
        section.add(gridPanel, BorderLayout.CENTER);
        return section;
    }

    // =========================================================================
    // FEATURE CARDS
    // =========================================================================

    private JPanel buildFeatureRow() {
        JPanel container = new JPanel(new BorderLayout()) {
            @Override public Dimension getMaximumSize() {
                return new Dimension(Short.MAX_VALUE, getPreferredSize().height);
            }
        };
        container.setOpaque(false);
        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.setBorder(new EmptyBorder(0, 36, 0, 36));

        JLabel secLabel = new JLabel("FUNZIONALITÀ");
        secLabel.setFont(UITheme.FONT_LABEL);
        secLabel.setForeground(UITheme.TEXT_MUTED);
        secLabel.setBorder(new EmptyBorder(0, 2, 12, 0));
        container.add(secLabel, BorderLayout.NORTH);

        JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        cardsRow.setOpaque(false);
        cardsRow.setBorder(new EmptyBorder(0, -20, 0, 0)); 

        cardsRow.add(buildFeatureCard(
                "cerca", "Cerca",
                "Filtra per città, cucina e fascia di prezzo.\nTrova il locale perfetto.",
                () -> { parent.getSearchPanel().refresh(); parent.showCard(FancyFrame.CARD_SEARCH); }
        ));

        cardsRow.add(buildFeatureCard(
                "preferiti", "Preferiti",
                "Salva i locali che ami.\nRitrovali con un click.",
                () -> { parent.showCard(FancyFrame.CARD_PREFERITI); }
        ));

        cardsRow.add(buildFeatureCard(
                "stelle", "Recensioni",
                "Condividi la tua esperienza.\nLeggi le opinioni di chi c'è stato.",
                () -> { parent.showCard(FancyFrame.CARD_RECENSIONI); }
        ));

        container.add(cardsRow, BorderLayout.CENTER);
        return container;
    }

    private JPanel buildFeatureCard(String iconKey, String title, String desc, Runnable onClick) {
        UITheme.CardPanel card = new UITheme.CardPanel(new BorderLayout()) {
            private boolean isHoveredState = false;
            @Override public void setHovered(boolean h) {
                super.setHovered(h);
                this.isHoveredState = h;
                repaint();
            }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(UITheme.GOLD);
                if (isHoveredState) {
                    g2.fillRect(0, 0, getWidth(), 4);
                } else {
                    g2.fillRect(0, 0, 36, 4);
                }
                g2.dispose();
            }
        };
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        Dimension cardSize = new Dimension(260, 170);
        card.setPreferredSize(cardSize);
        card.setMinimumSize(cardSize);
        card.setMaximumSize(cardSize);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(18, 16, 14, 16));

        JPanel iconWrapper = buildIconPanel(iconKey);
        iconWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(iconWrapper);
        inner.add(Box.createVerticalStrut(10));

        JLabel t = new JLabel(title);
        t.setFont(UITheme.FONT_H2); t.setForeground(UITheme.TEXT);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(t);
        inner.add(Box.createVerticalStrut(4));

        JLabel d = new JLabel("<html><body style='width: 220px; color:#64748B;'>"
                + desc.replace("\n", "<br>") + "</body></html>");
        d.setFont(UITheme.FONT_SMALL);
        d.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(d);
        inner.add(Box.createVerticalStrut(10));

        JLabel link = new JLabel("Vai alla sezione →");
        link.setFont(UITheme.FONT_LABEL); link.setForeground(UITheme.GOLD);
        link.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(link);

        card.add(inner, BorderLayout.CENTER);

        MouseAdapter ma = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { onClick.run(); }
            @Override public void mouseEntered(MouseEvent e) {
                card.setHovered(true); 
                inner.setBackground(UITheme.CARD_HOV_BG);
                link.setForeground(Color.WHITE);
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setHovered(false); 
                inner.setBackground(UITheme.CARD);
                link.setForeground(UITheme.GOLD);
            }
        };
        card.addMouseListener(ma);
        inner.addMouseListener(ma);
        return card;
    }

    // =========================================================================
    // NUOVA CTA PREMIUM ("SEI UN RISTORATORE?")
    // =========================================================================

    private JPanel buildCTAPremium() {
        // Wrapper esterno per forzare i 36px di margine laterale coerenti con la pagina
        JPanel marginWrapper = new JPanel(new BorderLayout());
        marginWrapper.setOpaque(false);
        marginWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        marginWrapper.setBorder(new EmptyBorder(0, 36, 0, 36));

        // Il pannello interno con gradiente scuro/bronzo lucido come nel mockup
        JPanel cta = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                // Gradiente metallico sfumato premium
                GradientPaint gp = new GradientPaint(0, 0, new Color(42, 35, 27),
                        getWidth(), 0, new Color(22, 19, 15));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Bordo rifinito color oro lucido
                g2.setColor(new Color(196, 160, 72, 180));
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                
                // Disegno decorativo artistico in Java2D sul lato destro (Simbolo Chef minimale)
                int cx = getWidth() - 75;
                int cy = getHeight() / 2;
                g2.setColor(new Color(196, 160, 72, 30));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(cx - 24, cy - 24, 48, 48);
                g2.drawOval(cx - 20, cy - 20, 40, 40);
                
                // Piccola icona stilizzata del cappello all'interno
                g2.setColor(new Color(196, 160, 72, 75));
                g2.fillRoundRect(cx - 10, cy + 2, 20, 6, 2, 2);
                g2.fillOval(cx - 12, cy - 10, 14, 14);
                g2.fillOval(cx - 2, cy - 12, 14, 14);
                g2.fillOval(cx - 7, cy - 6, 14, 14);
                
                g2.dispose();
            }
            @Override public Dimension getMaximumSize() { return new Dimension(Short.MAX_VALUE, 84); }
            @Override public Dimension getPreferredSize() { return new Dimension(0, 84); }
            @Override public Dimension getMinimumSize() { return new Dimension(0, 84); }
        };
        cta.setOpaque(false);
        cta.setBorder(new EmptyBorder(0, 28, 0, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        
        // Testi (Incolonnati a sinistra)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel t = new JLabel("SEI UN RISTORATORE?");
        t.setFont(UITheme.FONT_H2); 
        t.setForeground(new Color(230, 210, 180)); // Oro chiaro delicato
        
        JLabel s = new JLabel("Eleva il tuo locale. Raggiungi migliaia di nuovi clienti su TheKnife.");
        s.setFont(UITheme.FONT_BODY); 
        s.setForeground(new Color(164, 154, 142)); // Testo secondario desaturato caldo

        textPanel.add(t);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(s);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
        cta.add(textPanel, gbc);

        // Bottone d'azione posizionato perfettamente a destra
        UITheme.TKButton btn = UITheme.btnPrimary("Inizia ora →");
        btn.setPreferredSize(new Dimension(135, 36));
        btn.addActionListener(e -> parent.showCard(FancyFrame.CARD_RISTORANTI));
        
        gbc.gridx = 1; gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        // Padding extra per non sovrapporsi alle linee decorative di sfondo
        gbc.insets = new Insets(0, 0, 0, 70); 
        cta.add(btn, gbc);

        marginWrapper.add(cta, BorderLayout.CENTER);
        return marginWrapper;
    }

    // =========================================================================
    // ICONE VETTORIALI JAVA2D
    // =========================================================================

    private static JPanel buildIconPanel(String iconKey) {
        return new JPanel() {
            private static final int S = 40;
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(UITheme.GOLD_LIGHT); g2.fillOval(0, 0, S, S);
                g2.setColor(new Color(196, 160, 72, 70));
                g2.setStroke(new BasicStroke(0.8f)); g2.drawOval(0, 0, S-1, S-1);
                g2.setColor(UITheme.GOLD);
                g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                switch (iconKey) {
                    case "cerca" -> {
                        g2.setColor(Color.WHITE); g2.fillOval(10, 10, 14, 14);
                        g2.setColor(UITheme.GOLD); g2.drawOval(10, 10, 14, 14);
                        g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(22, 22, 29, 29);
                    }
                    case "preferiti" -> {
                        g2.setStroke(new BasicStroke(1f));
                        float cx=S/2f, top=S/2f-4;
                        Path2D h = new Path2D.Float();
                        h.moveTo(cx,top+12); h.curveTo(cx-1,top+7,cx-10,top,cx-9,top-5);
                        h.curveTo(cx-7,top-10,cx,top-3,cx,top+1);
                        h.curveTo(cx,top-3,cx+7,top-10,cx+9,top-5);
                        h.curveTo(cx+10,top,cx+1,top+7,cx,top+12);
                        h.closePath(); g2.fill(h);
                    }
                    case "stelle" -> {
                        UITheme.drawStar(g2, S/2f, S/2f+1, 10f, 4.3f, true);
                    }
                }
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(S, S); }
            @Override public Dimension getMaximumSize()   { return new Dimension(S, S); }
            @Override public Dimension getMinimumSize()   { return new Dimension(S, S); }
            @Override public boolean   isOpaque()         { return false; }
        };
    }

    // =========================================================================
    // LOGICA DI BUSINESS & SWINGWORKER
    // =========================================================================

    public void refresh() {
        if (ClientTK.isLoggato()) {
            String dom = ClientTK.getUtenteLoggato().getDomicilio();
            if (dom != null && !dom.isBlank()) {
                tfCitta.setVisible(false); btnCerca.setVisible(false);
                lblTitolo.setText("Ristoranti a " + dom);
                lblSub.setText("Basato sul tuo domicilio");
                caricaRistoranti(dom); return;
            }
        }
        tfCitta.setVisible(true); btnCerca.setVisible(true);
        lblTitolo.setText("Ristoranti disponibili");
        lblSub.setText("Inserisci una città nella barra di ricerca qui sopra.");
        gridPanel.removeAll(); gridPanel.revalidate(); gridPanel.repaint();
    }

    private void cercaPerCitta() {
        String c = tfCitta.getText().trim();
        if (c.isEmpty()) { UITheme.flashRed(tfCitta); return; }
        lblTitolo.setText("Ristoranti a " + c);
        lblSub.setText("Risultati per la città cercata");
        caricaRistoranti(c);
    }

    private void caricaRistoranti(String citta) {
        gridPanel.removeAll();
        JLabel loading = new JLabel("Ricerca in corso...");
        loading.setFont(UITheme.FONT_BODY); loading.setForeground(UITheme.TEXT_MUTED);
        gridPanel.add(loading); gridPanel.revalidate(); gridPanel.repaint();

        new SwingWorker<List<Ristorante>, Void>() {
            @Override protected List<Ristorante> doInBackground() throws Exception {
                Request req = new Request(CommandType.CERCA_RISTORANTI,
                        ClientTK.isLoggato() ? ClientTK.getUtenteLoggato().getUsername() : null)
                        .aggiungiParametro("citta", citta);
                Response r = ClientTK.getConnessione().invia(req);
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }
            @Override protected void done() {
                gridPanel.removeAll();
                try {
                    List<Ristorante> lista = get();
                    if (lista.isEmpty()) {
                        JLabel e = new JLabel("Nessun ristorante trovato a \"" + citta + "\".");
                        e.setFont(UITheme.FONT_BODY); e.setForeground(UITheme.TEXT_MUTED);
                        gridPanel.add(e);
                    } else {
                        List<Ristorante> sorted = lista.stream()
                                .sorted((a, b) -> Double.compare(b.getMediaStelle(), a.getMediaStelle()))
                                .toList();
                        boolean first = true;
                        for (Ristorante r : sorted) {
                            addCard(r, first && r.getMediaStelle() >= 4.5);
                            first = false;
                        }
                    }
                } catch (Exception ex) {
                    JLabel e = new JLabel("Errore: " + ex.getMessage());
                    e.setFont(UITheme.FONT_BODY); e.setForeground(UITheme.DANGER);
                    gridPanel.add(e);
                }
                gridPanel.revalidate(); gridPanel.repaint();
            }
        }.execute();
    }

    private void addCard(Ristorante r, boolean topRated) {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(210, topRated ? 142 : 124));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel br = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        br.setOpaque(false); br.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (topRated) br.add(solidBadge("Top rated", UITheme.GOLD, Color.WHITE));
        br.add(UITheme.badgeCucina(r.getTipoCucina()));

        JLabel nome = new JLabel(r.getNome());
        nome.setFont(UITheme.FONT_H3); nome.setForeground(UITheme.TEXT);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT);
        nome.setBorder(new EmptyBorder(7, 0, 1, 0));

        JLabel loc = new JLabel(r.getCitta() + "  ·  " + String.format("%.0f€", r.getFasciaPrezzo()));
        loc.setFont(UITheme.FONT_SMALL); loc.setForeground(UITheme.TEXT_MUTED);
        loc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bot.setOpaque(false); bot.setAlignmentX(Component.LEFT_ALIGNMENT);
        bot.add(UITheme.starLabel(r.getMediaStelle(), r.getNumeroRecensioni()));
        if (r.isDelivery())     bot.add(UITheme.pillDelivery());
        if (r.isPrenotazione()) bot.add(UITheme.pillPrenotazione());

        inner.add(br); inner.add(nome); inner.add(loc);
        inner.add(Box.createVerticalStrut(5)); inner.add(bot);
        card.add(inner, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                parent.getDetailPanel().setRistorante(r);
                parent.showCard(FancyFrame.CARD_DETAIL);
            }
            @Override public void mouseEntered(MouseEvent e) {
                card.setHovered(true); inner.setBackground(UITheme.CARD_HOV_BG);
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setHovered(false); inner.setBackground(UITheme.CARD);
            }
        });
        gridPanel.add(card);
    }

    private static JLabel solidBadge(String txt, Color bg, Color fg) {
        JLabel l = new JLabel(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(bg); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                g2.dispose(); super.paintComponent(g);
            }
        };
        l.setFont(UITheme.FONT_LABEL); l.setForeground(fg);
        l.setOpaque(false); l.setBorder(new EmptyBorder(2, 7, 2, 7));
        return l;
    }

    // =========================================================================
    // WRAP LAYOUT (Ottimizzazione Griglia Dinamica)
    // =========================================================================
    private static class WrapLayout extends FlowLayout {
        public WrapLayout(int a, int h, int v) { super(a, h, v); }
        @Override public Dimension preferredLayoutSize(Container t) { return ls(t, true); }
        @Override public Dimension minimumLayoutSize(Container t)   { return ls(t, false); }
        private Dimension ls(Container t, boolean p) {
            synchronized (t.getTreeLock()) {
                int tw = t.getSize().width; if (tw == 0) tw = Integer.MAX_VALUE;
                Insets ins = t.getInsets();
                int mw = tw - ins.left - ins.right - getHgap() * 2;
                int rw = 0, rh = 0, th = ins.top + ins.bottom + getVgap() * 2;
                for (int i = 0; i < t.getComponentCount(); i++) {
                    Component m = t.getComponent(i); if (!m.isVisible()) continue;
                    Dimension d = p ? m.getPreferredSize() : m.getMinimumSize();
                    if (rw + d.width > mw && rw > 0) { th += rh + getVgap(); rw = 0; rh = 0; }
                    rw += d.width + getHgap(); rh = Math.max(rh, d.height);
                }
                return new Dimension(tw, th + rh);
            }
        }
    }
}