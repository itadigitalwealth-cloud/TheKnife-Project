/**
 * TheKnife – Pannello miei locali (ristoratore).
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.client.gui.FancyFrame;
import it.uninsubria.theknife.client.gui.GradientPanel;
import it.uninsubria.theknife.client.gui.UITheme;
import it.uninsubria.theknife.common.CommandType;
import it.uninsubria.theknife.common.Request;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Ristorante;

/**
 * Pannello "I miei locali" riservato ai ristoratori.
 * Mostra la griglia dei ristoranti registrati con valutazione media
 * e conteggio recensioni, e permette di aggiungere nuovi locali tramite
 * {@link NuovoRistoranteDialog}.
 */
public class RistorantiPanel extends GradientPanel {

    private final FancyFrame parent;
    private final JPanel gridPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 16, 16));
    private final JLabel lblCount  = new JLabel("");

    /**
     * Costruisce il pannello "I miei locali".
     *
     * @param parent la finestra principale {@link FancyFrame} usata per la navigazione
     */
    public RistorantiPanel(FancyFrame parent) {
        super(new BorderLayout()); this.parent = parent;
        setBackground(UITheme.BG); initUI();
    }

    private void initUI() {
        // ─── TOPBAR ─────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 64));
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.CARD_BORDER),
                new EmptyBorder(0, 28, 0, 28)));
        JLabel title = new JLabel("I miei locali");
        title.setFont(UITheme.FONT_H1); title.setForeground(UITheme.TEXT);
        topBar.add(title, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        // ─── CONTENT ────────────────────────────────────────────────────────
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(UITheme.BG);
        content.setBorder(new EmptyBorder(18, 28, 24, 28));

        JPanel infoRow = new JPanel(new BorderLayout()); infoRow.setOpaque(false);
        lblCount.setFont(UITheme.FONT_BODY); lblCount.setForeground(UITheme.TEXT_MUTED);
        infoRow.add(lblCount, BorderLayout.WEST);
        content.add(infoRow, BorderLayout.NORTH);

        gridPanel.setBackground(UITheme.BG);
        content.add(gridPanel, BorderLayout.CENTER);

        // ScrollablePanel fix: imposta la larghezza del viewport per WrapLayout
        ScrollablePanel scrollable = new ScrollablePanel(content);
        JScrollPane scroll = new JScrollPane(scrollable);
        scroll.setBorder(null); scroll.setBackground(UITheme.BG);
        scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        // ─── BOTTOM BAR ─────────────────────────────────────────────────────
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 14));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.CARD_BORDER));
        UITheme.TKButton btnAdd = UITheme.btnPrimary("+ Aggiungi ristorante");
        btnAdd.addActionListener(e -> apriDialogNuovoRistorante());
        bottom.add(btnAdd);
        add(bottom, BorderLayout.SOUTH);
    }

    // =========================================================================
    // CARICAMENTO
    // =========================================================================

    /**
     * Ricarica dal server il riepilogo dei ristoranti del ristoratore corrente
     * ({@code RISTORATORE_VISUALIZZA_RIEPILOGO}) e aggiorna la griglia.
     * Se l'utente non è un ristoratore loggato, il metodo non esegue alcuna operazione.
     */
    public void refreshData() {
        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isRistoratore()) return;
        gridPanel.removeAll();
        lblCount.setText("Caricamento...");
        gridPanel.revalidate(); gridPanel.repaint();

        Request req = new Request(CommandType.RISTORATORE_VISUALIZZA_RIEPILOGO,
                ClientTK.getUtenteLoggato().getUsername());

        new SwingWorker<List<Ristorante>, Void>() {
            @Override protected List<Ristorante> doInBackground() throws Exception {
                Response r = ClientTK.getConnessione().invia(req);
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }
            @Override protected void done() {
                gridPanel.removeAll();
                try {
                    List<Ristorante> lista = get();
                    if (lista.isEmpty()) {
                        lblCount.setText("");
                        addLabel("Non hai ancora aggiunto locali.");
                    } else {
                        lblCount.setText(lista.size() + " " + (lista.size() == 1 ? "locale" : "locali"));
                        lista.forEach(RistorantiPanel.this::addCard);
                    }
                } catch (Exception ex) {
                    lblCount.setText("Errore di connessione.");
                    addLabel("Impossibile caricare i locali.");
                }
                gridPanel.revalidate(); gridPanel.repaint();
            }
        }.execute();
    }

    // =========================================================================
    // CARD RISTORANTE
    // =========================================================================

    private void addCard(Ristorante r) {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(270, 155));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel(); inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD); inner.setBorder(new EmptyBorder(14, 16, 14, 16));

        // Badge cucina
        JPanel br = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        br.setOpaque(false); br.setAlignmentX(Component.LEFT_ALIGNMENT);
        br.add(UITheme.badgeCucina(r.getTipoCucina()));

        // Nome (tronca se troppo lungo)
        String nomeStr = r.getNome().length() > 24 ? r.getNome().substring(0, 22) + "..." : r.getNome();
        JLabel nome = new JLabel(nomeStr);
        nome.setFont(UITheme.FONT_H2); nome.setForeground(UITheme.TEXT);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT); nome.setBorder(new EmptyBorder(7, 0, 2, 0));

        JLabel loc = new JLabel(r.getCitta() + "  ·  " + String.format("%.0f€", r.getFasciaPrezzo()));
        loc.setFont(UITheme.FONT_SMALL); loc.setForeground(UITheme.TEXT_MUTED);
        loc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        stats.setOpaque(false); stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        stats.add(UITheme.starLabel(r.getMediaStelle(), r.getNumeroRecensioni()));
        if (r.isDelivery())     stats.add(UITheme.pillDelivery());
        if (r.isPrenotazione()) stats.add(UITheme.pillPrenotazione());

        inner.add(br); inner.add(nome); inner.add(loc);
        inner.add(Box.createVerticalStrut(6)); inner.add(stats);
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

    private void addLabel(String txt) {
        JLabel l = new JLabel(txt); l.setFont(UITheme.FONT_BODY); l.setForeground(UITheme.TEXT_MUTED);
        l.setBorder(new EmptyBorder(20, 4, 0, 0)); gridPanel.add(l);
        gridPanel.revalidate(); gridPanel.repaint();
    }

    // =========================================================================
    // DIALOG NUOVO RISTORANTE
    // =========================================================================

    private void apriDialogNuovoRistorante() {
        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isRistoratore()) {
            JOptionPane.showMessageDialog(this, "Devi essere loggato come ristoratore.",
                    "Accesso negato", JOptionPane.WARNING_MESSAGE); return;
        }
        NuovoRistoranteDialog dlg = new NuovoRistoranteDialog(SwingUtilities.getWindowAncestor(this));
        dlg.setVisible(true);
        if (!dlg.isConfermato()) return;
        try {
            Response r = ClientTK.getConnessione().invia(
                    new Request(CommandType.RISTORATORE_AGGIUNGI_RISTORANTE,
                            ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("nome",         dlg.getNome())
                            .aggiungiParametro("nazione",      dlg.getNazione())
                            .aggiungiParametro("citta",        dlg.getCitta())
                            .aggiungiParametro("indirizzo",    dlg.getIndirizzo())
                            .aggiungiParametro("latitudine",   dlg.getLat())
                            .aggiungiParametro("longitudine",  dlg.getLon())
                            .aggiungiParametro("fasciaPrezzo", dlg.getPrezzo())
                            .aggiungiParametro("delivery",     dlg.isDel())
                            .aggiungiParametro("prenotazione", dlg.isPren())
                            .aggiungiParametro("tipoCucina",   dlg.getCucina()));
            // Messaggio fallback se il server non ne fornisce uno
            String msg = (r.getMessaggio() != null && !r.getMessaggio().isBlank())
                    ? r.getMessaggio()
                    : (r.isSuccesso() ? "Ristorante aggiunto con successo!" : "Operazione fallita.");
            JOptionPane.showMessageDialog(this, msg,
                    r.isSuccesso() ? "Ristorante aggiunto" : "Errore",
                    r.isSuccesso() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (r.isSuccesso()) refreshData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore di connessione: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // SCROLLABLE PANEL – fix WrapLayout
    // =========================================================================
    private static class ScrollablePanel extends JPanel implements Scrollable {
        ScrollablePanel(JPanel content) {
            super(new BorderLayout()); setBackground(UITheme.BG); add(content, BorderLayout.CENTER);
        }
        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d)  { return 20; }
        @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 80; }
        @Override public boolean getScrollableTracksViewportWidth()  { return true; }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }
    }

    // =========================================================================
    // DIALOG NUOVO RISTORANTE – form completo con header navy
    // =========================================================================
    public static class NuovoRistoranteDialog extends JDialog {
        private boolean conf = false;
        private final JTextField tfNome = UITheme.textField(18), tfNaz  = UITheme.textField(12);
        private final JTextField tfCitta= UITheme.textField(12), tfInd  = UITheme.textField(24);
        private final JTextField tfLat  = UITheme.textField(10), tfLon  = UITheme.textField(10);
        private final JTextField tfPrz  = UITheme.textField(8),  tfCuc  = UITheme.textField(14);
        private final JCheckBox  chkDel = new JCheckBox("Delivery");
        private final JCheckBox  chkPren= new JCheckBox("Prenotazione online");

        /**
         * Crea il dialog di inserimento di un nuovo ristorante con valori predefiniti.
         *
         * @param owner la finestra padre
         */
        public NuovoRistoranteDialog(Window owner) {
            super(owner, "Aggiungi ristorante", ModalityType.APPLICATION_MODAL);
            setSize(520, 560); setLocationRelativeTo(owner); setResizable(false);
            tfNaz.setText("Italia"); tfLat.setText("0.0"); tfLon.setText("0.0"); tfPrz.setText("30");
            for (JCheckBox cb : new JCheckBox[]{chkDel, chkPren}) {
                cb.setBackground(UITheme.CARD); cb.setFont(UITheme.FONT_BODY);
                cb.setForeground(UITheme.TEXT); cb.setFocusPainted(false);
            }
            build();
        }

        private void build() {
            JPanel root = new JPanel(new BorderLayout()); root.setBackground(UITheme.CARD);

            // Header navy – costruito direttamente (non dipende da UITheme.dialogHeader)
            JPanel hdr = new JPanel(new BorderLayout()) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(UITheme.SIDEBAR_BG); g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(UITheme.GOLD);       g2.fillRect(0, getHeight()-3, getWidth(), 3);
                    g2.dispose();
                }
            };
            hdr.setOpaque(false); hdr.setPreferredSize(new Dimension(0, 68));
            hdr.setBorder(new EmptyBorder(0, 22, 3, 22));
            JPanel hc = new JPanel(new BorderLayout(0, 3)); hc.setOpaque(false);
            JLabel htitle = new JLabel("Aggiungi un nuovo ristorante");
            htitle.setFont(UITheme.FONT_H2); htitle.setForeground(Color.WHITE);
            JLabel hsub = new JLabel("I dati saranno visibili a tutti gli utenti della piattaforma");
            hsub.setFont(UITheme.FONT_SMALL); hsub.setForeground(new Color(148, 163, 184));
            hc.add(htitle, BorderLayout.NORTH); hc.add(hsub, BorderLayout.SOUTH);
            hdr.add(hc, BorderLayout.CENTER);
            root.add(hdr, BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout()); form.setBackground(UITheme.CARD);
            form.setBorder(new EmptyBorder(16, 22, 10, 22));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(3, 4, 3, 4); g.fill = GridBagConstraints.HORIZONTAL;

            addRow(form, g, 0, 0, "NOME RISTORANTE",   tfNome, 4);
            addRow(form, g, 1, 0, "NAZIONE",            tfNaz,  2);
            addRow(form, g, 1, 2, "CITTÀ",              tfCitta,2);
            addRow(form, g, 2, 0, "INDIRIZZO",          tfInd,  4);
            addRow(form, g, 3, 0, "LATITUDINE",         tfLat,  2);
            addRow(form, g, 3, 2, "LONGITUDINE",        tfLon,  2);
            addRow(form, g, 4, 0, "PREZZO MEDIO (€)",   tfPrz,  2);
            addRow(form, g, 4, 2, "TIPO CUCINA",        tfCuc,  2);

            g.gridy=10; g.gridx=0; g.gridwidth=2; g.insets=new Insets(10,4,3,4); form.add(chkDel,g);
            g.gridx=2; form.add(chkPren, g);
            root.add(form, BorderLayout.CENTER);

            JPanel footer = new JPanel(new GridLayout(1, 2, 10, 0));
            footer.setBackground(UITheme.CARD); footer.setBorder(new EmptyBorder(0, 22, 18, 22));
            UITheme.TKButton ba = UITheme.btnGhost("Annulla"), bi = UITheme.btnPrimary("Salva ristorante");
            ba.addActionListener(e -> dispose());
            bi.addActionListener(e -> {
                if (tfNome.getText().trim().isEmpty() || tfCitta.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nome e città sono obbligatori.",
                            "Attenzione", JOptionPane.WARNING_MESSAGE); return;
                }
                try {
                    Double.parseDouble(tfLat.getText().trim());
                    Double.parseDouble(tfLon.getText().trim());
                    Double.parseDouble(tfPrz.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Latitudine, longitudine e prezzo devono essere numeri.",
                            "Errore formato", JOptionPane.ERROR_MESSAGE); return;
                }
                if (tfCuc.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Inserisci il tipo di cucina.",
                            "Attenzione", JOptionPane.WARNING_MESSAGE); return;
                }
                conf = true; dispose();
            });
            footer.add(ba); footer.add(bi); root.add(footer, BorderLayout.SOUTH);
            setContentPane(root);
        }

        private void addRow(JPanel f, GridBagConstraints g, int row, int col, String lbl, JTextField tf, int span) {
            g.gridx = col; g.gridy = row * 2; g.gridwidth = span; g.weightx = 1;
            g.insets = new Insets(6, 4, 0, 4);
            JLabel l = new JLabel(lbl); l.setFont(UITheme.FONT_LABEL); l.setForeground(UITheme.TEXT_MUTED);
            f.add(l, g);
            g.gridy = row * 2 + 1; g.insets = new Insets(2, 4, 4, 4); f.add(tf, g);
        }

        /** @return {@code true} se il ristoratore ha premuto "Salva ristorante" */
        public boolean isConfermato() { return conf; }
        /** @return il nome del ristorante inserito */
        public String  getNome()      { return tfNome.getText().trim(); }
        /** @return la nazione del ristorante */
        public String  getNazione()   { return tfNaz.getText().trim(); }
        /** @return la città del ristorante */
        public String  getCitta()     { return tfCitta.getText().trim(); }
        /** @return l'indirizzo del ristorante (può essere vuoto) */
        public String  getIndirizzo() { return tfInd.getText().trim(); }
        /** @return la latitudine del ristorante
         * @throws NumberFormatException se il valore inserito non è un numero valido */
        public double  getLat()       { return Double.parseDouble(tfLat.getText().trim()); }
        /** @return la longitudine del ristorante
         * @throws NumberFormatException se il valore inserito non è un numero valido */
        public double  getLon()       { return Double.parseDouble(tfLon.getText().trim()); }
        /** @return il prezzo medio per persona in euro
         * @throws NumberFormatException se il valore inserito non è un numero valido */
        public double  getPrezzo()    { return Double.parseDouble(tfPrz.getText().trim()); }
        /** @return il tipo di cucina del ristorante */
        public String  getCucina()    { return tfCuc.getText().trim(); }
        /** @return {@code true} se il servizio di delivery è disponibile */
        public boolean isDel()        { return chkDel.isSelected(); }
        /** @return {@code true} se il servizio di prenotazione online è disponibile */
        public boolean isPren()       { return chkPren.isSelected(); }
    }

    // =========================================================================
    // WRAP LAYOUT
    // =========================================================================
    private static class WrapLayout extends FlowLayout {
        WrapLayout(int a, int h, int v) { super(a, h, v); }
        @Override public Dimension preferredLayoutSize(Container t) { return ls(t, true); }
        @Override public Dimension minimumLayoutSize(Container t)   { return ls(t, false); }
        private Dimension ls(Container t, boolean p) {
            synchronized (t.getTreeLock()) {
                int tw = t.getSize().width;
                if (tw == 0 && t.getParent() != null) tw = t.getParent().getSize().width;
                if (tw == 0) tw = 900;
                Insets ins = t.getInsets(); int mw = tw - ins.left - ins.right - getHgap() * 2;
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