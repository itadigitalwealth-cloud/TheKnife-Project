/**
 * TheKnife – Pannello Esplora (ricerca avanzata ristoranti).
 *
 * Fix v4.0 – risolve il problema delle card invisibili:
 * il gridPanel ora implementa Scrollable con getScrollableTracksViewportWidth()=true,
 * in modo che il JScrollPane assegni sempre la larghezza del viewport al pannello
 * e WrapLayout possa calcolare la dimensione corretta.
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
import java.util.List;

public class SearchPanel extends GradientPanel {

    private static final String PH = "Cerca una città... (es. Como, Milano)";

    private final FancyFrame parent;

    private final JTextField       tfCitta   = UITheme.textField(22);
    private final UITheme.TKButton btnFiltri = UITheme.btnGhost("Filtri avanzati");
    private final UITheme.TKButton btnCerca  = UITheme.btnPrimary("Cerca");

    private boolean placeholderAttivo = true;

    private String  fCucina=""; private double fPrzMin=0,fPrzMax=0,fStelle=0;
    private boolean fDelivery=false,fPrenot=false;

    private final JPanel chipsRow = new JPanel(new FlowLayout(FlowLayout.LEFT,6,0));
    private final JLabel lblCount = new JLabel("");

    /**
     * gridPanel implementa Scrollable con getScrollableTracksViewportWidth()=true.
     * Questo dice al JScrollPane di impostare sempre la larghezza del pannello
     * uguale alla larghezza del viewport, permettendo a WrapLayout di calcolare
     * la dimensione preferita corretta e mostrare le card.
     */
    private final JPanel gridPanel = new JPanel(new WrapLayout(FlowLayout.LEFT,14,14)) {
        @Override public boolean isOpaque() { return false; }
        @Override public Color getBackground() { return UITheme.BG; }
    };

    private final FocusListener focusListener = new FocusAdapter() {
        @Override public void focusGained(FocusEvent e) {
            if (placeholderAttivo) {
                tfCitta.setText(""); tfCitta.setForeground(UITheme.TEXT); placeholderAttivo = false;
            }
        }
        @Override public void focusLost(FocusEvent e) {
            if (tfCitta.getText().isEmpty()) {
                tfCitta.setText(PH); tfCitta.setForeground(Color.LIGHT_GRAY); placeholderAttivo = true;
            }
        }
    };

    public SearchPanel(FancyFrame parent) {
        super(new BorderLayout()); this.parent = parent; setBackground(UITheme.BG); initUI();
    }

    private void initUI() {
        add(buildTopBar(), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0,12));
        content.setBackground(UITheme.BG);
        content.setBorder(new EmptyBorder(16,36,24,36));

        JPanel infoRow = new JPanel(new BorderLayout()); infoRow.setOpaque(false);
        lblCount.setFont(UITheme.FONT_BODY); lblCount.setForeground(UITheme.TEXT);
        infoRow.add(lblCount, BorderLayout.WEST);
        content.add(infoRow, BorderLayout.NORTH);

        gridPanel.setBackground(UITheme.BG);
        content.add(gridPanel, BorderLayout.CENTER);

        // ── FIX CHIAVE ──────────────────────────────────────────────────────────
        // Avvolgiamo gridPanel in un ScrollablePanel che forza il JScrollPane
        // ad assegnare la larghezza del viewport al pannello.
        // Senza questo, WrapLayout usa Integer.MAX_VALUE come larghezza e le
        // card vengono disposte fuori dallo schermo.
        ScrollablePanel scrollable = new ScrollablePanel(content);
        JScrollPane scroll = new JScrollPane(scrollable);
        // ── END FIX ─────────────────────────────────────────────────────────────

        scroll.setBorder(null); scroll.setBackground(UITheme.BG);
        scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(25);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        tfCitta.setText(PH); tfCitta.setForeground(Color.LIGHT_GRAY);
        tfCitta.addFocusListener(focusListener);
        tfCitta.addActionListener(e -> eseguiRicerca());
    }

    private JPanel buildTopBar() {
        JPanel ext = new JPanel(new BorderLayout()); ext.setBackground(UITheme.BG);
        ext.setBorder(new EmptyBorder(20,36,0,36));
        JPanel bar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2=UITheme.rh(g); g2.setColor(Color.WHITE);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                g2.setColor(UITheme.CARD_BORDER);
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,12,12); g2.dispose();
            }
        };
        bar.setLayout(new BoxLayout(bar,BoxLayout.Y_AXIS)); bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(16,20,16,20));

        JLabel lbl = new JLabel("LOCALITÀ DI RICERCA");
        lbl.setFont(UITheme.FONT_LABEL); lbl.setForeground(UITheme.TEXT_MUTED);
        lbl.setBorder(new EmptyBorder(0,2,6,0)); bar.add(lbl);

        JPanel row1 = new JPanel(new BorderLayout(12,0)); row1.setOpaque(false);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE,38));
        tfCitta.setPreferredSize(new Dimension(340,38));
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0)); btns.setOpaque(false);
        btnFiltri.setPreferredSize(new Dimension(150,38)); btnCerca.setPreferredSize(new Dimension(100,38));
        btnFiltri.addActionListener(e -> apriDialogFiltri());
        btnCerca.addActionListener(e -> eseguiRicerca());
        btns.add(btnFiltri); btns.add(btnCerca);
        row1.add(tfCitta, BorderLayout.WEST); row1.add(btns, BorderLayout.CENTER);
        bar.add(row1);

        bar.add(Box.createVerticalStrut(12));
        JPanel row2 = new JPanel(new BorderLayout()); row2.setOpaque(false);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        JLabel filtLbl = new JLabel("Filtri applicati:");
        filtLbl.setFont(UITheme.FONT_LABEL); filtLbl.setForeground(UITheme.TEXT_MUTED);
        filtLbl.setBorder(new EmptyBorder(2,2,0,10));
        row2.add(filtLbl, BorderLayout.WEST);
        chipsRow.setOpaque(false); row2.add(chipsRow, BorderLayout.CENTER);
        bar.add(row2);

        ext.add(bar, BorderLayout.CENTER); return ext;
    }

    // =========================================================================
    // RICERCA
    // =========================================================================

    private void eseguiRicerca() {
        if (placeholderAttivo || tfCitta.getText().trim().isEmpty()) {
            flashField(tfCitta); return;
        }
        final String citta = tfCitta.getText().trim();

        gridPanel.removeAll();
        lblCount.setText("Ricerca in corso...");
        rigeneraChips(citta);
        gridPanel.revalidate(); gridPanel.repaint();

        Request req = new Request(CommandType.CERCA_RISTORANTI,
                ClientTK.isLoggato() ? ClientTK.getUtenteLoggato().getUsername() : null)
                .aggiungiParametro("citta",      citta)
                .aggiungiParametro("tipoCucina", fCucina);
        if (fPrzMin>0) req.aggiungiParametro("prezzoMin",    fPrzMin);
        if (fPrzMax>0) req.aggiungiParametro("prezzoMax",    fPrzMax);
        if (fStelle>0) req.aggiungiParametro("stelleMin",    fStelle);
        if (fDelivery) req.aggiungiParametro("delivery",     true);
        if (fPrenot)   req.aggiungiParametro("prenotazione", true);

        new SwingWorker<List<Ristorante>,Void>() {
            @Override protected List<Ristorante> doInBackground() throws Exception {
                Response r = ClientTK.getConnessione().invia(req);
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }
            @Override protected void done() {
                gridPanel.removeAll();
                try {
                    List<Ristorante> lista = get();
                    if (lista.isEmpty()) {
                        lblCount.setText("Nessun risultato per \""+citta+"\" con i filtri impostati.");
                        JLabel l = new JLabel("Nessun ristorante trovato. Prova a modificare i filtri.");
                        l.setFont(UITheme.FONT_BODY); l.setForeground(UITheme.TEXT_MUTED);
                        l.setBorder(new EmptyBorder(30,4,0,0)); gridPanel.add(l);
                    } else {
                        lblCount.setText(lista.size()+(lista.size()==1?" ristorante trovato":" ristoranti trovati"));
                        List<Ristorante> sorted = lista.stream()
                                .sorted((a,b) -> Double.compare(b.getMediaStelle(), a.getMediaStelle()))
                                .toList();
                        boolean first = true;
                        for (Ristorante r : sorted) {
                            addCard(r, first && r.getMediaStelle() >= 4.5);
                            first = false;
                        }
                    }
                } catch (Exception ex) {
                    lblCount.setText("Errore: " + ex.getMessage());
                    JLabel l = new JLabel("Errore connessione server.");
                    l.setFont(UITheme.FONT_BODY); l.setForeground(Color.RED);
                    l.setBorder(new EmptyBorder(30,4,0,0)); gridPanel.add(l);
                }
                gridPanel.revalidate();
                gridPanel.repaint();
            }
        }.execute();
    }

    private void apriDialogFiltri() {
        FiltriDialog dlg = new FiltriDialog(SwingUtilities.getWindowAncestor(this),
                fCucina,fPrzMin,fPrzMax,fStelle,fDelivery,fPrenot);
        dlg.setVisible(true);
        if (dlg.isConfermato()) {
            fCucina=dlg.getCucina(); fPrzMin=dlg.getPrzMin(); fPrzMax=dlg.getPrzMax();
            fStelle=dlg.getStelle(); fDelivery=dlg.isDel(); fPrenot=dlg.isPren();
            if (!placeholderAttivo && !tfCitta.getText().trim().isEmpty()) eseguiRicerca();
            else flashField(tfCitta);
        }
    }

    // =========================================================================
    // CHIP
    // =========================================================================

    private void rigeneraChips(String citta) {
        chipsRow.removeAll();
        addChip("\uD83D\uDCCD "+citta, null);
        if (!fCucina.isEmpty()) addChip(fCucina,              ()->{fCucina="";       eseguiRicerca();});
        if (fPrzMin>0) addChip("Min "+(int)fPrzMin+"\u20AC",  ()->{fPrzMin=0;        eseguiRicerca();});
        if (fPrzMax>0) addChip("Max "+(int)fPrzMax+"\u20AC",  ()->{fPrzMax=0;        eseguiRicerca();});
        if (fStelle>0) addChip(stelleStr((int)fStelle)+"+",   ()->{fStelle=0;        eseguiRicerca();});
        if (fDelivery) addChip("Delivery",                    ()->{fDelivery=false;  eseguiRicerca();});
        if (fPrenot)   addChip("Prenotazione",                ()->{fPrenot=false;    eseguiRicerca();});
        chipsRow.revalidate(); chipsRow.repaint();
    }

    private void addChip(String txt, Runnable onRemove) {
        JPanel chip = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2=UITheme.rh(g); g2.setColor(UITheme.GOLD_LIGHT);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14); g2.dispose();
            }
        };
        chip.setOpaque(false); chip.setBorder(new EmptyBorder(4,10,4,onRemove!=null?4:10));
        JLabel l = new JLabel(txt); l.setFont(UITheme.FONT_LABEL); l.setForeground(UITheme.GOLD_DARK);
        chip.add(l);
        if (onRemove != null) {
            JLabel x = new JLabel(" \u00D7"); x.setFont(new Font("Segoe UI",Font.BOLD,13));
            x.setForeground(UITheme.GOLD); x.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            x.addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) { onRemove.run(); }
            });
            chip.add(x);
        }
        chipsRow.add(chip);
    }

    // =========================================================================
    // CARD
    // =========================================================================

    private void addCard(Ristorante r, boolean topRated) {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(215, topRated?146:126));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel(); inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD); inner.setBorder(new EmptyBorder(12,14,12,14));

        JPanel bRow = new JPanel(new FlowLayout(FlowLayout.LEFT,4,0));
        bRow.setOpaque(false); bRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (topRated) bRow.add(solidBadge("Top rated", UITheme.GOLD, Color.WHITE));
        bRow.add(UITheme.badgeCucina(r.getTipoCucina()));

        JLabel nome = new JLabel(r.getNome()); nome.setFont(UITheme.FONT_H3); nome.setForeground(UITheme.TEXT);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT); nome.setBorder(new EmptyBorder(6,0,2,0));

        JLabel loc = new JLabel(r.getCitta()+"  \u00B7  "+String.format("%.0f\u20AC",r.getFasciaPrezzo()));
        loc.setFont(UITheme.FONT_SMALL); loc.setForeground(UITheme.TEXT_MUTED); loc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
        bot.setOpaque(false); bot.setAlignmentX(Component.LEFT_ALIGNMENT);
        bot.add(UITheme.starLabel(r.getMediaStelle(), r.getNumeroRecensioni()));
        if (r.isDelivery())     bot.add(UITheme.pillDelivery());
        if (r.isPrenotazione()) bot.add(UITheme.pillPrenotazione());

        inner.add(bRow); inner.add(nome); inner.add(loc);
        inner.add(Box.createVerticalStrut(5)); inner.add(bot);
        card.add(inner, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                parent.getDetailPanel().setRistorante(r); parent.showCard(FancyFrame.CARD_DETAIL);
            }
            @Override public void mouseEntered(MouseEvent e) { card.setHovered(true);  inner.setBackground(UITheme.CARD_HOV_BG); }
            @Override public void mouseExited (MouseEvent e) { card.setHovered(false); inner.setBackground(UITheme.CARD); }
        });
        gridPanel.add(card);
    }

    // =========================================================================
    // UTILS
    // =========================================================================

    public void refresh() {
        tfCitta.setText(PH); tfCitta.setForeground(Color.LIGHT_GRAY); placeholderAttivo=true;
        fCucina=""; fPrzMin=0; fPrzMax=0; fStelle=0; fDelivery=false; fPrenot=false;
        chipsRow.removeAll(); chipsRow.revalidate(); chipsRow.repaint();
        lblCount.setText(""); gridPanel.removeAll();
        JLabel l = new JLabel("Inserisci una città per visualizzare i ristoranti disponibili.");
        l.setFont(UITheme.FONT_BODY); l.setForeground(UITheme.TEXT_MUTED);
        l.setBorder(new EmptyBorder(45,10,0,0));
        gridPanel.add(l); gridPanel.revalidate(); gridPanel.repaint();
    }

    static JLabel solidBadge(String txt, Color bg, Color fg) {
        JLabel l = new JLabel(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2=UITheme.rh(g); g2.setColor(bg);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),5,5); g2.dispose(); super.paintComponent(g);
            }
        };
        l.setFont(UITheme.FONT_LABEL); l.setForeground(fg); l.setOpaque(false);
        l.setBorder(new EmptyBorder(2,7,2,7)); return l;
    }

    private static String stelleStr(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i=1;i<=5;i++) sb.append(i<=n?"\u2605":"\u2606");
        return sb.toString();
    }

    private static void flashField(JTextField tf) {
        Color orig = tf.getBackground(); tf.setBackground(new Color(255,220,220));
        new Timer(600, e -> { tf.setBackground(orig); ((Timer)e.getSource()).stop(); }).start();
    }

    // =========================================================================
    // SCROLLABLE PANEL – FIX CHIAVE
    // Avvolge il contenuto e dice al JScrollPane di usare la larghezza del viewport,
    // in modo che WrapLayout possa calcolare la larghezza corretta per le card.
    // =========================================================================
    private static class ScrollablePanel extends JPanel implements Scrollable {
        ScrollablePanel(JPanel content) {
            super(new BorderLayout());
            setBackground(UITheme.BG);
            add(content, BorderLayout.CENTER);
        }
        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d)  { return 25; }
        @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 100; }
        /**
         * TRUE = il JScrollPane imposta la larghezza di questo pannello uguale
         * alla larghezza del viewport. WrapLayout riceve così la larghezza
         * corretta e dispone le card con wrap preciso invece di usare
         * Integer.MAX_VALUE che le rendeva invisibili.
         */
        @Override public boolean getScrollableTracksViewportWidth()  { return true;  }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }
    }

    // =========================================================================
    // DIALOG FILTRI
    // =========================================================================
    public static class FiltriDialog extends JDialog {
        private boolean ok = false;
        private final JTextField tfC=UITheme.textField(14), tfMn=UITheme.textField(7), tfMx=UITheme.textField(7);
        private final JSlider slSt = new JSlider(0,5,0);
        private final JLabel lblSt = new JLabel("Qualsiasi");
        private final JCheckBox chkD=chkBox("Delivery disponibile"), chkP=chkBox("Prenotazione online");

        public FiltriDialog(Window owner, String cucina, double min, double max, double st, boolean d, boolean p) {
            super(owner,"Filtri avanzati",ModalityType.APPLICATION_MODAL);
            setSize(400,490); setLocationRelativeTo(owner); setResizable(false);
            tfC.setText(cucina);
            if (min>0) tfMn.setText(String.valueOf((int)min));
            if (max>0) tfMx.setText(String.valueOf((int)max));
            slSt.setValue((int)st); chkD.setSelected(d); chkP.setSelected(p);
            updStars(); build();
        }

        private void build() {
            JPanel root = new JPanel(new BorderLayout()); root.setBackground(UITheme.CARD);
            JPanel hdr = new JPanel(new BorderLayout()) {
                @Override protected void paintComponent(Graphics g) {
                    g.setColor(UITheme.SIDEBAR_BG); g.fillRect(0,0,getWidth(),getHeight());
                    g.setColor(UITheme.GOLD);       g.fillRect(0,getHeight()-2,getWidth(),2);
                }
            };
            hdr.setOpaque(false); hdr.setPreferredSize(new Dimension(0,58));
            hdr.setBorder(new EmptyBorder(0,22,2,22));
            JPanel hc = new JPanel(new BorderLayout()); hc.setOpaque(false);
            JLabel t = new JLabel("Filtri avanzati"); t.setFont(UITheme.FONT_H2); t.setForeground(Color.WHITE);
            JLabel s = new JLabel("Personalizza la tua ricerca"); s.setFont(UITheme.FONT_SMALL); s.setForeground(UITheme.SIDEBAR_MUTED);
            hc.add(t,BorderLayout.NORTH); hc.add(s,BorderLayout.SOUTH); hdr.add(hc,BorderLayout.CENTER);
            root.add(hdr,BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout()); form.setBackground(UITheme.CARD);
            form.setBorder(new EmptyBorder(18,22,10,22));
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill=GridBagConstraints.HORIZONTAL; gc.weightx=1; gc.gridx=0;
            gc.gridy=0; gc.insets=new Insets(0,0,3,0);  form.add(fldLbl("TIPO DI CUCINA"),gc);
            gc.gridy=1; gc.insets=new Insets(0,0,14,0); form.add(tfC,gc);
            gc.gridy=2; gc.insets=new Insets(0,0,3,0);  form.add(fldLbl("FASCIA DI PREZZO (\u20AC)"),gc);
            gc.gridy=3; gc.insets=new Insets(0,0,14,0);
            JPanel pr = new JPanel(new GridLayout(1,3,8,0)); pr.setOpaque(false);
            JLabel dash = new JLabel("\u2014",SwingConstants.CENTER); dash.setFont(UITheme.FONT_BODY); dash.setForeground(UITheme.TEXT_MUTED);
            pr.add(tfMn); pr.add(dash); pr.add(tfMx); form.add(pr,gc);
            gc.gridy=4; gc.insets=new Insets(0,0,6,0);  form.add(fldLbl("STELLE MINIME"),gc);
            gc.gridy=5; gc.insets=new Insets(0,0,2,0);
            slSt.setOpaque(false); slSt.setMajorTickSpacing(1); slSt.setPaintTicks(true); slSt.setSnapToTicks(true);
            slSt.addChangeListener(e->updStars()); form.add(slSt,gc);
            gc.gridy=6; gc.insets=new Insets(0,0,14,0);
            lblSt.setFont(new Font("Segoe UI",Font.PLAIN,14)); lblSt.setForeground(UITheme.STAR);
            form.add(lblSt,gc);
            gc.gridy=7; gc.insets=new Insets(0,0,3,0); form.add(fldLbl("SERVIZI"),gc);
            gc.gridy=8; gc.insets=new Insets(0,0,6,0); form.add(chkD,gc);
            gc.gridy=9; gc.insets=new Insets(0,0,0,0); form.add(chkP,gc);
            root.add(form,BorderLayout.CENTER);

            JPanel foot = new JPanel(new GridLayout(1,2,10,0)); foot.setBackground(UITheme.CARD);
            foot.setBorder(new EmptyBorder(0,22,18,22));
            UITheme.TKButton bR=UITheme.btnGhost("Azzera tutti"), bA=UITheme.btnPrimary("Applica filtri");
            bR.addActionListener(e->{ tfC.setText(""); tfMn.setText(""); tfMx.setText("");
                slSt.setValue(0); chkD.setSelected(false); chkP.setSelected(false); updStars(); });
            bA.addActionListener(e->{ ok=true; dispose(); });
            foot.add(bR); foot.add(bA); root.add(foot,BorderLayout.SOUTH);
            setContentPane(root);
        }

        private void updStars() {
            int v=slSt.getValue();
            if (v==0) { lblSt.setText("Qualsiasi"); lblSt.setForeground(UITheme.TEXT_MUTED); return; }
            StringBuilder sb=new StringBuilder(); for (int i=1;i<=5;i++) sb.append(i<=v?"\u2605":"\u2606");
            lblSt.setText(sb+" e oltre"); lblSt.setForeground(UITheme.STAR);
        }
        private static JLabel fldLbl(String t) { JLabel l=new JLabel(t); l.setFont(UITheme.FONT_LABEL); l.setForeground(UITheme.TEXT_MUTED); return l; }
        private static JCheckBox chkBox(String t) { JCheckBox c=new JCheckBox(t); c.setFont(UITheme.FONT_BODY); c.setForeground(UITheme.TEXT); c.setBackground(UITheme.CARD); c.setFocusPainted(false); return c; }

        public boolean isConfermato() { return ok; }
        public String  getCucina()   { return tfC.getText().trim(); }
        public double  getPrzMin()   { return parse(tfMn.getText()); }
        public double  getPrzMax()   { return parse(tfMx.getText()); }
        public double  getStelle()   { return slSt.getValue(); }
        public boolean isDel()       { return chkD.isSelected(); }
        public boolean isPren()      { return chkP.isSelected(); }
        private static double parse(String s) { try { return Double.parseDouble(s.trim()); } catch(Exception e) { return 0; } }
    }

    // =========================================================================
    // WRAP LAYOUT
    // =========================================================================
    private static class WrapLayout extends FlowLayout {
        WrapLayout(int a, int h, int v) { super(a,h,v); }
        @Override public Dimension preferredLayoutSize(Container t) { return layout(t,true); }
        @Override public Dimension minimumLayoutSize(Container t)   { return layout(t,false); }
        private Dimension layout(Container t, boolean preferred) {
            synchronized(t.getTreeLock()) {
                // Usa la larghezza del PARENT (viewport) se disponibile,
                // altrimenti la larghezza attuale del container.
                // Con ScrollablePanel(getScrollableTracksViewportWidth=true)
                // t.getSize().width sarà già la larghezza del viewport.
                int tw = t.getSize().width;
                if (tw == 0 && t.getParent() != null) tw = t.getParent().getSize().width;
                if (tw == 0) tw = 800; // fallback ragionevole invece di Integer.MAX_VALUE
                Insets ins = t.getInsets();
                int maxW = tw - ins.left - ins.right - getHgap()*2;
                int rowW=0, rowH=0, totH=ins.top+ins.bottom+getVgap()*2;
                for (int i=0; i<t.getComponentCount(); i++) {
                    Component c = t.getComponent(i); if (!c.isVisible()) continue;
                    Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                    if (rowW+d.width > maxW && rowW > 0) { totH+=rowH+getVgap(); rowW=0; rowH=0; }
                    rowW += d.width+getHgap(); rowH = Math.max(rowH,d.height);
                }
                return new Dimension(tw, totH+rowH);
            }
        }
    }
}