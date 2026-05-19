/**
 * TheKnife – Pannello ricerca ristoranti (Premium Edition).
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

/**
 * Pannello di ricerca avanzata ristoranti riprogettato con margini premium a 36px,
 * indicatore di contesto, placeholder e bug-fix sui filtri applicati.
 */
public class SearchPanel extends GradientPanel {

    private final FancyFrame parent;

    // ---- Barra ricerca con segnaposto (Placeholder) ---------------------
    private final JTextField      tfCitta   = UITheme.textField(22);
    private final UITheme.TKButton btnFiltri = UITheme.btnGhost("Filtri avanzati");
    private final UITheme.TKButton btnCerca  = UITheme.btnPrimary("Cerca");

    // ---- Stato filtri ---------------------------------------------------
    private String  fCucina   = "";
    private double  fPrzMin   = 0, fPrzMax = 0, fStelle = 0;
    private boolean fDelivery = false, fPrenot = false;

    // ---- Elementi di testo e layout -------------------------------------
    private final JPanel chipsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    private final JLabel lblCount = new JLabel("");
    private final JPanel gridPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 14, 14));

    public SearchPanel(FancyFrame parent) {
        super(new BorderLayout(0, 0));
        this.parent = parent;
        setBackground(UITheme.BG);
        initUI();
    }

    // =========================================================================
    // UI SETUP
    // =========================================================================

    private void initUI() {
        // Aggiunge la barra di ricerca superiore riprogettata
        add(buildTopBar(), BorderLayout.NORTH);

        // Pannello centrale dei contenuti (Griglia + Contatore)
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(UITheme.BG);
        content.setBorder(new EmptyBorder(16, 36, 24, 36)); // Margine simmetrico a 36px

        JPanel infoRow = new JPanel(new BorderLayout());
        infoRow.setOpaque(false);
        lblCount.setFont(UITheme.FONT_BODY);
        lblCount.setForeground(UITheme.TEXT);
        infoRow.add(lblCount, BorderLayout.WEST);
        content.add(infoRow, BorderLayout.NORTH);

        gridPanel.setBackground(UITheme.BG);
        content.add(gridPanel, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(UITheme.BG);
        scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(25);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        mostraPlaceholder();
    }

    private JPanel buildTopBar() {
        // Pannello contenitore esterno per mantenere lo sfondo della pagina coerente
        JPanel externalPanel = new JPanel(new BorderLayout());
        externalPanel.setBackground(UITheme.BG);
        externalPanel.setBorder(new EmptyBorder(20, 36, 0, 36)); // Allineato a 36px di margine laterale

        // Pannello interno arrotondato in stile scheda per la ricerca
        JPanel bar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(UITheme.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        bar.setLayout(new BoxLayout(bar, BoxLayout.Y_AXIS));
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Label informativa sopra il campo di testo per far capire all'utente cosa inserire
        JLabel lblInputTitle = new JLabel("LOCALITÀ DI RICERCA");
        lblInputTitle.setFont(UITheme.FONT_LABEL);
        lblInputTitle.setForeground(UITheme.TEXT_MUTED);
        lblInputTitle.setBorder(new EmptyBorder(0, 2, 6, 0));
        bar.add(lblInputTitle);

        // Riga 1: Input text + Bottone Filtri + Bottone Cerca
        JPanel row1 = new JPanel(new BorderLayout(12, 0));
        row1.setOpaque(false);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        // Gestione Placeholder visuale dentro il JTextField
        tfCitta.setPreferredSize(new Dimension(340, 38));
        setupPlaceholder(tfCitta, "Cerca una città... (es. Como, Milano)");
        tfCitta.addActionListener(e -> eseguiRicerca());

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btns.setOpaque(false);
        
        btnFiltri.setPreferredSize(new Dimension(150, 38));
        btnFiltri.addActionListener(e -> apriDialogFiltri());
        
        btnCerca.setPreferredSize(new Dimension(100, 38));
        btnCerca.addActionListener(e -> eseguiRicerca());
        
        btns.add(btnFiltri);
        btns.add(btnCerca);

        row1.add(tfCitta, BorderLayout.WEST);
        row1.add(btns, BorderLayout.CENTER);
        bar.add(row1);

        // Riga 2: Chip dei filtri attivi (Inizialmente vuota, si popola alla ricerca)
        bar.add(Box.createVerticalStrut(12));
        JPanel row2 = new JPanel(new BorderLayout());
        row2.setOpaque(false);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

        JLabel filtLabel = new JLabel("Filtri applicati:");
        filtLabel.setFont(UITheme.FONT_LABEL);
        filtLabel.setForeground(UITheme.TEXT_MUTED);
        filtLabel.setBorder(new EmptyBorder(2, 2, 0, 10));
        row2.add(filtLabel, BorderLayout.WEST);

        chipsRow.setOpaque(false);
        row2.add(chipsRow, BorderLayout.CENTER);
        bar.add(row2);

        externalPanel.add(bar, BorderLayout.CENTER);
        return externalPanel;
    }

    // =========================================================================
    // LOGICA DI RICERCA ED ESECUZIONE CORRETTA
    // =========================================================================

    private void eseguiRicerca() {
        String citta = tfCitta.getText().trim();
        // Controllo aggiuntivo per evitare di cercare il testo del placeholder di sistema
        if (citta.isEmpty() || tfCitta.getForeground().equals(Color.LIGHT_GRAY)) { 
            flashField(tfCitta); 
            return; 
        }

        gridPanel.removeAll();
        lblCount.setText("Ricerca e filtraggio in corso...");
        rigeneraChips(citta);
        gridPanel.revalidate(); gridPanel.repaint();

        Request req = new Request(CommandType.CERCA_RISTORANTI,
                ClientTK.isLoggato() ? ClientTK.getUtenteLoggato().getUsername() : null)
                .aggiungiParametro("citta",      citta)
                .aggiungiParametro("tipoCucina", fCucina);
        if (fPrzMin > 0)  req.aggiungiParametro("prezzoMin",   fPrzMin);
        if (fPrzMax > 0)  req.aggiungiParametro("prezzoMax",   fPrzMax);
        if (fStelle > 0)  req.aggiungiParametro("stelleMin",   fStelle);
        if (fDelivery)    req.aggiungiParametro("delivery",     true);
        if (fPrenot)      req.aggiungiParametro("prenotazione", true);

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
                        lblCount.setText("Nessun risultato corrispondente ai filtri impostati a \"" + citta + "\"");
                        mostraPlaceholder();
                    } else {
                        lblCount.setText(lista.size() +
                                (lista.size() == 1 ? " ristorante trovato" : " ristoranti trovati in questa zona"));
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
                    lblCount.setText("Errore di connessione con il server.");
                    mostraPlaceholder();
                }
                gridPanel.revalidate(); gridPanel.repaint();
            }
        }.execute();
    }

    private void apriDialogFiltri() {
        FiltriDialog dlg = new FiltriDialog(
                SwingUtilities.getWindowAncestor(this),
                fCucina, fPrzMin, fPrzMax, fStelle, fDelivery, fPrenot);
        dlg.setVisible(true);
        
        // FIX CRITICO: Se l'utente preme ok, aggiorna lo stato locale E lancia subito la query!
        if (dlg.isConfermato()) {
            fCucina   = dlg.getCucina();
            fPrzMin   = dlg.getPrzMin();
            fPrzMax   = dlg.getPrzMax();
            fStelle   = dlg.getStelle();
            fDelivery = dlg.isDel();
            fPrenot   = dlg.isPren();
            
            // Se la città è compilata esegue subito, altrimenti fa un flash visivo per ricordare l'input
            if (!tfCitta.getText().trim().isEmpty() && !tfCitta.getForeground().equals(Color.LIGHT_GRAY)) {
                eseguiRicerca();
            } else {
                flashField(tfCitta);
            }
        }
    }

    // ---- Rigenerazione Dinamica dei Filtri a Scomparsa ------------------

    private void rigeneraChips(String citta) {
        chipsRow.removeAll();
        addChip("📍 " + citta, null);
        if (!fCucina.isEmpty())  addChip(fCucina,                 () -> { fCucina = "";      eseguiRicerca(); });
        if (fPrzMin > 0)         addChip("Min " + (int)fPrzMin + "€", () -> { fPrzMin = 0;  eseguiRicerca(); });
        if (fPrzMax > 0)         addChip("Max " + (int)fPrzMax + "€", () -> { fPrzMax = 0;  eseguiRicerca(); });
        if (fStelle > 0)         addChip(starsStr((int)fStelle) + "+", () -> { fStelle = 0; eseguiRicerca(); });
        if (fDelivery)           addChip("Delivery",               () -> { fDelivery = false; eseguiRicerca(); });
        if (fPrenot)             addChip("Prenotazione",           () -> { fPrenot  = false;  eseguiRicerca(); });
        chipsRow.revalidate(); chipsRow.repaint();
    }

    private void addChip(String txt, Runnable onRemove) {
        JPanel chip = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(UITheme.GOLD_LIGHT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
            }
        };
        chip.setOpaque(false);
        chip.setBorder(new EmptyBorder(4, 10, 4, onRemove != null ? 4 : 10));

        JLabel lbl = new JLabel(txt);
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.GOLD_DARK);
        chip.add(lbl);

        if (onRemove != null) {
            JLabel x = new JLabel(" ×");
            x.setFont(new Font("Segoe UI", Font.BOLD, 13));
            x.setForeground(UITheme.GOLD);
            x.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            x.addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) { onRemove.run(); }
            });
            chip.add(x);
        }
        chipsRow.add(chip);
    }

    // =========================================================================
    // CREAZIONE DELLE CARD RISTORANTE CON EFFETTO HOVER COMPLETO
    // =========================================================================

    private void addCard(Ristorante r, boolean topRated) {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(215, topRated ? 146 : 126));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        badgeRow.setOpaque(false); badgeRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (topRated) badgeRow.add(solidBadge("Top rated", UITheme.GOLD, Color.WHITE));
        badgeRow.add(UITheme.badgeCucina(r.getTipoCucina()));

        JLabel nome = new JLabel(r.getNome());
        nome.setFont(UITheme.FONT_H3); nome.setForeground(UITheme.TEXT);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT);
        nome.setBorder(new EmptyBorder(6, 0, 2, 0));

        JLabel loc = new JLabel(r.getCitta() + "  ·  " + String.format("%.0f€", r.getFasciaPrezzo()));
        loc.setFont(UITheme.FONT_SMALL); loc.setForeground(UITheme.TEXT_MUTED);
        loc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottom.setOpaque(false); bottom.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(UITheme.starLabel(r.getMediaStelle(), r.getNumeroRecensioni()));
        if (r.isDelivery())     bottom.add(UITheme.pillDelivery());
        if (r.isPrenotazione()) bottom.add(UITheme.pillPrenotazione());

        inner.add(badgeRow); inner.add(nome); inner.add(loc);
        inner.add(Box.createVerticalStrut(5)); inner.add(bottom);
        card.add(inner, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                parent.getDetailPanel().setRistorante(r);
                parent.showCard(FancyFrame.CARD_DETAIL);
            }
            @Override public void mouseEntered(MouseEvent e) {
                card.setHovered(true); 
                inner.setBackground(UITheme.CARD_HOV_BG);
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setHovered(false); 
                inner.setBackground(UITheme.CARD);
            }
        });
        gridPanel.add(card);
    }

    // =========================================================================
    // UTILITY DI SUPPORTO & INTERFACCIA
    // =========================================================================

    private void setupPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.LIGHT_GRAY);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(UITheme.TEXT);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.LIGHT_GRAY);
                }
            }
        });
    }

    private void mostraPlaceholder() {
        gridPanel.removeAll();
        JLabel l = new JLabel("Inserisci la tua città di destinazione per visualizzare l'elenco dei locali.");
        l.setFont(UITheme.FONT_BODY); l.setForeground(UITheme.TEXT_MUTED);
        l.setBorder(new EmptyBorder(45, 10, 0, 0));
        gridPanel.add(l);
        gridPanel.revalidate(); gridPanel.repaint();
    }

    static JLabel solidBadge(String txt, Color bg, Color fg) {
        JLabel l = new JLabel(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(bg); g2.fillRoundRect(0,0,getWidth(),getHeight(),5,5);
                g2.dispose(); super.paintComponent(g);
            }
        };
        l.setFont(UITheme.FONT_LABEL); l.setForeground(fg);
        l.setOpaque(false); l.setBorder(new EmptyBorder(2,7,2,7));
        return l;
    }

    private static String starsStr(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i=1;i<=5;i++) sb.append(i<=n?"\u2605":"\u2606");
        return sb.toString();
    }

    private static void flashField(JTextField tf) {
        Color orig = tf.getBackground();
        tf.setBackground(new Color(255,235,235));
        new Timer(500, e->{ tf.setBackground(orig);((Timer)e.getSource()).stop();}).start();
    }

    public void refresh() {
        // Ripristina il placeholder iniziale anziché lasciare il campo vuoto
        setupPlaceholder(tfCitta, "Cerca una città... (es. Como, Milano)");
        fCucina=""; fPrzMin=0; fPrzMax=0; fStelle=0; fDelivery=false; fPrenot=false;
        chipsRow.removeAll(); chipsRow.revalidate(); chipsRow.repaint();
        lblCount.setText("");
        mostraPlaceholder();
    }

    // =========================================================================
    // MODULO DIALOG FILTRI INTERNI (Mantenuto Strutturato)
    // =========================================================================
    public static class FiltriDialog extends JDialog {
        private boolean confermato = false;
        private final JTextField tfCucina = UITheme.textField(14);
        private final JTextField tfPrzMin = UITheme.textField(7);
        private final JTextField tfPrzMax = UITheme.textField(7);
        private final JSlider     slStelle = new JSlider(0, 5, 0);
        private final JLabel      lblSt    = new JLabel("Qualsiasi");
        private final JCheckBox   chkDel   = styledCheck("Delivery disponibile");
        private final JCheckBox   chkPren  = styledCheck("Prenotazione online");

        public FiltriDialog(Window owner, String cucina, double przMin,
                            double przMax, double stelle, boolean del, boolean pren) {
            super(owner, "Filtri avanzati", ModalityType.APPLICATION_MODAL);
            setSize(400, 480);
            setLocationRelativeTo(owner);
            setResizable(false);
            getContentPane().setBackground(UITheme.CARD);
            tfCucina.setText(cucina);
            if (przMin > 0) tfPrzMin.setText(String.valueOf((int) przMin));
            if (przMax > 0) tfPrzMax.setText(String.valueOf((int) przMax));
            slStelle.setValue((int) stelle);
            chkDel.setSelected(del); chkPren.setSelected(pren);
            updateStars();
            build();
        }

        private void build() {
            JPanel root = new JPanel(new BorderLayout());
            root.setBackground(UITheme.CARD);

            JPanel hdr = new JPanel(new BorderLayout()) {
                @Override protected void paintComponent(Graphics g) {
                    g.setColor(UITheme.SIDEBAR_BG);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(UITheme.GOLD);
                    g.fillRect(0, getHeight()-2, getWidth(), 2);
                }
            };
            hdr.setOpaque(false);
            hdr.setPreferredSize(new Dimension(0, 58));
            hdr.setBorder(new EmptyBorder(0, 22, 2, 22));

            JPanel hdrContent = new JPanel(new BorderLayout());
            hdrContent.setOpaque(false);
            JLabel title = new JLabel("Filtri avanzati");
            title.setFont(UITheme.FONT_H2); title.setForeground(Color.WHITE);
            JLabel sub = new JLabel("Personalizza la tua ricerca");
            sub.setFont(UITheme.FONT_SMALL); sub.setForeground(UITheme.SIDEBAR_MUTED);
            hdrContent.add(title, BorderLayout.NORTH);
            hdrContent.add(sub,   BorderLayout.SOUTH);
            hdr.add(hdrContent, BorderLayout.CENTER);
            root.add(hdr, BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(UITheme.CARD);
            form.setBorder(new EmptyBorder(18, 22, 10, 22));
            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1; g.gridx = 0;

            g.gridy=0; g.insets=new Insets(0,0,3,0);
            form.add(fieldLabel("TIPO DI CUCINA"), g);
            g.gridy=1; g.insets=new Insets(0,0,14,0);
            form.add(tfCucina, g);

            g.gridy=2; g.insets=new Insets(0,0,3,0);
            form.add(fieldLabel("FASCIA DI PREZZO (€)"), g);
            g.gridy=3; g.insets=new Insets(0,0,14,0);
            JPanel prRow = new JPanel(new GridLayout(1, 3, 8, 0));
            prRow.setOpaque(false);
            JLabel dash = new JLabel("—", SwingConstants.CENTER);
            dash.setFont(UITheme.FONT_BODY); dash.setForeground(UITheme.TEXT_MUTED);
            prRow.add(tfPrzMin); prRow.add(dash); prRow.add(tfPrzMax);
            form.add(prRow, g);

            g.gridy=4; g.insets=new Insets(0,0,6,0);
            form.add(fieldLabel("STELLE MINIME"), g);
            g.gridy=5; g.insets=new Insets(0,0,2,0);
            slStelle.setOpaque(false);
            slStelle.setMajorTickSpacing(1); slStelle.setPaintTicks(true);
            slStelle.setSnapToTicks(true);
            slStelle.addChangeListener(e -> updateStars());
            form.add(slStelle, g);
            g.gridy=6; g.insets=new Insets(0,0,14,0);
            lblSt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblSt.setForeground(UITheme.STAR);
            form.add(lblSt, g);

            g.gridy=7; g.insets=new Insets(0,0,3,0);
            form.add(fieldLabel("SERVIZI"), g);
            g.gridy=8; g.insets=new Insets(0,0,6,0); form.add(chkDel, g);
            g.gridy=9; g.insets=new Insets(0,0,0,0); form.add(chkPren, g);
            root.add(form, BorderLayout.CENTER);

            JPanel footer = new JPanel(new GridLayout(1, 2, 10, 0));
            footer.setBackground(UITheme.CARD);
            footer.setBorder(new EmptyBorder(0, 22, 18, 22));
            UITheme.TKButton btnR = UITheme.btnGhost("Azzera tutti");
            UITheme.TKButton btnA = UITheme.btnPrimary("Applica filtri");
            btnR.addActionListener(e -> {
                tfCucina.setText(""); tfPrzMin.setText(""); tfPrzMax.setText("");
                slStelle.setValue(0); chkDel.setSelected(false);
                chkPren.setSelected(false); updateStars();
            });
            btnA.addActionListener(e -> { confermato = true; dispose(); });
            footer.add(btnR); footer.add(btnA);
            root.add(footer, BorderLayout.SOUTH);
            setContentPane(root);
        }

        private void updateStars() {
            int v = slStelle.getValue();
            if (v == 0) { lblSt.setText("Qualsiasi"); lblSt.setForeground(UITheme.TEXT_MUTED); return; }
            StringBuilder sb = new StringBuilder();
            for (int i=1;i<=5;i++) sb.append(i<=v?"\u2605":"\u2606");
            lblSt.setText(sb + " e oltre");
            lblSt.setForeground(UITheme.STAR);
        }

        private static JLabel fieldLabel(String t) {
            JLabel l = new JLabel(t); l.setFont(UITheme.FONT_LABEL); l.setForeground(UITheme.TEXT_MUTED); return l;
        }
        private static JCheckBox styledCheck(String txt) {
            JCheckBox c = new JCheckBox(txt);
            c.setFont(UITheme.FONT_BODY); c.setForeground(UITheme.TEXT);
            c.setBackground(UITheme.CARD); c.setFocusPainted(false); return c;
        }

        public boolean isConfermato(){ return confermato; }
        public String  getCucina()   { return tfCucina.getText().trim(); }
        public double  getPrzMin()   { return parse(tfPrzMin.getText()); }
        public double  getPrzMax()   { return parse(tfPrzMax.getText()); }
        public double  get欲しいStelle()   { return slStelle.getValue(); }
        public double  getStelle()   { return slStelle.getValue(); }
        public boolean isDel()       { return chkDel.isSelected(); }
        public boolean isPren()      { return chkPren.isSelected(); }
        private double parse(String s){ try{return Double.parseDouble(s.trim());}catch(Exception e){return 0;} }
    }

    // =========================================================================
    // WRAP LAYOUT AUTOMATICO
    // =========================================================================
    private static class WrapLayout extends FlowLayout {
        public WrapLayout(int a,int h,int v){super(a,h,v);}
        @Override public Dimension preferredLayoutSize(Container t){return ls(t,true);}
        @Override public Dimension minimumLayoutSize(Container t){return ls(t,false);}
        private Dimension ls(Container t,boolean p){
            synchronized(t.getTreeLock()){
                int tw=t.getSize().width;if(tw==0)tw=Integer.MAX_VALUE;
                Insets ins=t.getInsets();int mw=tw-ins.left-ins.right-getHgap()*2;
                int rw=0,rh=0,th=ins.top+ins.bottom+getVgap()*2;
                for(int i=0;i<t.getComponentCount();i++){
                    Component m=t.getComponent(i);if(!m.isVisible())continue;
                    Dimension d=p?m.getPreferredSize():m.getMinimumSize();
                    if(rw+d.width>mw&&rw>0){th+=rh+getVgap();rw=0;rh=0;}
                    rw+=d.width+getHgap();rh=Math.max(rh,d.height);}
                return new Dimension(tw,th+rh);}}
    }
}