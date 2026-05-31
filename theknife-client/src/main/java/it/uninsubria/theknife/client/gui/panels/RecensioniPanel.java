/**
 * TheKnife – Pannello recensioni.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.client.gui.FancyFrame;
import it.uninsubria.theknife.client.gui.GradientPanel;
import it.uninsubria.theknife.client.gui.UITheme;
import it.uninsubria.theknife.common.CommandType;
import it.uninsubria.theknife.common.Request;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Recensione;
import it.uninsubria.theknife.common.model.Ristorante;

/**
 * Pannello di gestione recensioni per Clienti e Ristoratori.
 * Layout moderno, dialog strutturati con selezione basata su città e rendering Java2D nativo per evitare bug di font.
 */
public class RecensioniPanel extends GradientPanel {

    private final FancyFrame parent;

    private List<Recensione>   cache        = List.of();
    private int                filtroStelle = 0;

    private final UITheme.StarChip[] starChips = new UITheme.StarChip[6];
    private final JPanel     cardArea   = new JPanel();
    private final JLabel     lblCount   = new JLabel("");
    private final JLabel     lblTitolo  = new JLabel("Recensioni");

    private final UITheme.TKButton btnNuova    = UITheme.btnPrimary("+ Nuova recensione");
    private final UITheme.TKButton btnModifica = UITheme.btnGhost("Modifica");
    private final UITheme.TKButton btnElimina  = UITheme.btnDanger("Elimina");
    private final UITheme.TKButton btnRispondi = UITheme.btnPrimary("Rispondi");

    public RecensioniPanel(FancyFrame parent) {
        super(new BorderLayout());
        this.parent = parent;
        setBackground(UITheme.BG);
        initUI();
    }

    // =========================================================================
    // COSTRUZIONE INTERFACCIA GRAFICA (UI)
    // =========================================================================

    private void initUI() {
        add(buildTopBar(), BorderLayout.NORTH);

        cardArea.setLayout(new BoxLayout(cardArea, BoxLayout.Y_AXIS));
        cardArea.setBackground(UITheme.BG);
        cardArea.setBorder(new EmptyBorder(16, 24, 24, 24));

        JScrollPane scroll = new JScrollPane(cardArea);
        scroll.setBorder(null); 
        scroll.setBackground(UITheme.BG);
        scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        add(scroll, BorderLayout.CENTER);
        
        add(buildBottomBar(), BorderLayout.SOUTH);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout(0, 14));
        bar.setBackground(Color.WHITE);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.CARD_BORDER),
                new EmptyBorder(18, 24, 16, 24)));

        JPanel titleRow = new JPanel(new BorderLayout()); 
        titleRow.setOpaque(false);
        lblTitolo.setFont(UITheme.FONT_H1); 
        lblTitolo.setForeground(UITheme.TEXT);
        lblCount.setFont(UITheme.FONT_SMALL); 
        lblCount.setForeground(UITheme.TEXT_MUTED);
        titleRow.add(lblTitolo, BorderLayout.WEST);
        titleRow.add(lblCount,  BorderLayout.EAST);
        bar.add(titleRow, BorderLayout.NORTH);

        JPanel starRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        starRow.setOpaque(false);
        JLabel lbl = new JLabel("Filtra per stelle:");
        lbl.setFont(UITheme.FONT_LABEL); 
        lbl.setForeground(UITheme.TEXT_MUTED);
        starRow.add(lbl);

        starChips[0] = UITheme.starChip("Tutte", 0);
        starChips[0].addActionListener(e -> filtra(0));
        starRow.add(starChips[0]);

        for (int i = 1; i <= 5; i++) {
            final int val = i;
            starChips[i] = UITheme.starChip("", i);
            starChips[i].addActionListener(e -> filtra(val));
            starRow.add(starChips[i]);
        }
        starChips[0].setSelected(true);
        bar.add(starRow, BorderLayout.SOUTH);
        return bar;
    }

    private JPanel buildBottomBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 14));
        bar.setBackground(Color.WHITE);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.CARD_BORDER));
        
        btnElimina.addActionListener(e  -> eliminaRecensione());
        btnModifica.addActionListener(e -> modificaRecensione());
        btnRispondi.addActionListener(e -> rispondiRecensione());
        btnNuova.addActionListener(e    -> nuovaRecensione());
        
        bar.add(btnElimina);
        bar.add(btnModifica);
        bar.add(btnRispondi);
        bar.add(btnNuova);
        // Stato iniziale: Rispondi nascosto finché non viene chiamato aggiornaVisibilitaPulsanti()
        // Viene mostrato solo se loggato come ristoratore
        btnRispondi.setVisible(false);
        btnElimina.setVisible(false);
        btnModifica.setVisible(false);
        btnNuova.setVisible(false);
        return bar;
    }

    // =========================================================================
    // LOGICA DI CARICAMENTO E FILTRAGGIO
    // =========================================================================

    public void refreshData() {
        cardArea.removeAll(); 
        cache = List.of();
        aggiornaVisibilitaPulsanti();
        
        if (!ClientTK.isLoggato()) {
            lblTitolo.setText("Recensioni");
            addLabel("Accedi al sistema per visualizzare o gestire le tue recensioni."); 
            return;
        }
        
        boolean isCliente = ClientTK.getUtenteLoggato().isCliente();
        lblTitolo.setText(isCliente ? "Le mie recensioni" : "Recensioni ricevute");
        
        CommandType cmd = isCliente ? CommandType.CLIENTE_VISUALIZZA_MIE_RECENSIONI
                                    : CommandType.RISTORATORE_VISUALIZZA_RECENSIONI;
        addLabel("Sincronizzazione archivio recensioni...");
        
        new SwingWorker<List<Recensione>, Void>() {
            @Override 
            protected List<Recensione> doInBackground() throws Exception {
                Response r = ClientTK.getConnessione().invia(
                        new Request(cmd, ClientTK.getUtenteLoggato().getUsername()));
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }
            @Override 
            protected void done() {
                try { 
                    cache = get(); 
                    filtra(0); 
                } catch (Exception ex) { 
                    cardArea.removeAll(); 
                    addLabel("Impossibile caricare i dati: " + ex.getMessage()); 
                }
            }
        }.execute();
    }

    private void filtra(int stelle) {
        filtroStelle = stelle;
        for (int i = 0; i <= 5; i++) {
            if (starChips[i] != null) starChips[i].setSelected(i == stelle);
        }

        List<Recensione> filt = stelle == 0 ? cache
                : cache.stream().filter(r -> r.getStelle() == stelle).collect(Collectors.toList());

        cardArea.removeAll();
        boolean isC = ClientTK.isLoggato() && ClientTK.getUtenteLoggato().isCliente();

        if (filt.isEmpty()) {
            addLabel(stelle == 0 ? "Nessuna recensione registrata nel sistema."
                    : "Non sono presenti recensioni con una valutazione di " + stelle + " stelle.");
        } else {
            filt.forEach(r -> {
                cardArea.add(buildRecCard(r, isC));
                cardArea.add(Box.createVerticalStrut(14)); // Spazio uniforme tra le card
            });
        }
        int tot = filt.size();
        lblCount.setText(tot + (tot == 1 ? " recensione trovata" : " recensioni trovate"));
        cardArea.revalidate(); 
        cardArea.repaint();
    }

    // =========================================================================
    // RENDERING CARD COMPATTE (CON RIGIDO FIX SUI MARGINI E PADDING)
    // =========================================================================

    private JPanel buildRecCard(Recensione rec, boolean showRistorante) {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout(0, 10));

        JPanel inner = new JPanel(new BorderLayout(0, 12));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(18, 20, 18, 20)); // Padding generoso interno alla card

        // Intestazione Card
        JPanel head = new JPanel(new BorderLayout(0, 4)); 
        head.setOpaque(false);

        JPanel leftHead = new JPanel(new BorderLayout(0, 2)); 
        leftHead.setOpaque(false);
        String mainTxt = showRistorante ? rec.getNomeRistorante() : rec.getUsernameCliente();
        JLabel mainLbl = new JLabel(mainTxt); 
        mainLbl.setFont(UITheme.FONT_H3); 
        mainLbl.setForeground(UITheme.TEXT);
        leftHead.add(mainLbl, BorderLayout.NORTH);

        if (!showRistorante) {
            JLabel subLbl = new JLabel(rec.getNomeRistorante());
            subLbl.setFont(UITheme.FONT_SMALL); 
            subLbl.setForeground(UITheme.TEXT_MUTED);
            leftHead.add(subLbl, BorderLayout.CENTER);
        }
        head.add(leftHead, BorderLayout.WEST);

        JPanel starsPanel = buildStarsPanel(rec.getStelle());
        head.add(starsPanel, BorderLayout.EAST);
        inner.add(head, BorderLayout.NORTH);

        // Corpo del Testo con font standard sicuro e pulito
        JLabel testo = new JLabel("<html><body style='width:580px; margin:0; font-family:Segoe UI; line-height:14px;'>" + rec.getTesto() + "</body></html>");
        testo.setFont(UITheme.FONT_BODY); 
        testo.setForeground(UITheme.TEXT);
        testo.setBorder(new EmptyBorder(4, 2, 4, 2));
        inner.add(testo, BorderLayout.CENTER);

        // Sezione Risposta
        if (rec.hasRisposta()) {
            JPanel reply = new JPanel(new BorderLayout(0, 6));
            reply.setBackground(UITheme.INFO_BG);
            reply.setOpaque(true);
            reply.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 0, UITheme.INFO_FG),
                    new EmptyBorder(12, 14, 12, 14)));

            JLabel rl = new JLabel("Risposta del ristoratore");
            rl.setFont(UITheme.FONT_LABEL); 
            rl.setForeground(UITheme.INFO_FG);
            
            JLabel rt = new JLabel("<html><body style='width:550px; margin:0; font-family:Segoe UI;'>" + rec.getRisposta() + "</body></html>");
            rt.setFont(UITheme.FONT_BODY); 
            rt.setForeground(UITheme.TEXT);
            
            reply.add(rl, BorderLayout.NORTH);
            reply.add(rt, BorderLayout.CENTER);
            inner.add(reply, BorderLayout.SOUTH);
        }

        card.add(inner, BorderLayout.CENTER);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        return card;
    }

    private static JPanel buildStarsPanel(int stelle) {
        JPanel p = new JPanel() {
            @Override 
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = UITheme.rh(g);
                g2.setColor(UITheme.STAR);
                float sz = 9f, gap = 3f, startX = 2f, cy = getHeight() / 2f;
                for (int i = 0; i < 5; i++) {
                    float cx = startX + i * (sz + gap) + sz / 2;
                    UITheme.drawStar(g2, cx, cy, sz / 2, sz / 2 * 0.42f, i < stelle);
                }
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(70, 20); }
            @Override public Dimension getMinimumSize()   { return new Dimension(70, 20); }
            @Override public boolean   isOpaque()         { return false; }
        };
        p.setOpaque(false);
        return p;
    }

    // =========================================================================
    // AZIONI DI INTERAZIONE E MODIFICA DATI
    // =========================================================================

    private void nuovaRecensione() {
        if (!checkCliente()) return;

        // FIX LOGICO: Chiediamo prima la città per inviare una richiesta valida al server
        String cittaInput = JOptionPane.showInputDialog(this, "In quale città si trova il ristorante?", "Nuova Recensione", JOptionPane.QUESTION_MESSAGE);
        if (cittaInput == null || cittaInput.trim().isBlank()) {
            return;
        }
        
        final String cittaCercata = cittaInput.trim();
        addLabel("Ricerca dei locali attivi a " + cittaCercata + "...");

        new SwingWorker<List<Ristorante>, Void>() {
            @Override
            protected List<Ristorante> doInBackground() throws Exception {
                // Inviamo la città inserita per popolare correttamente la lista
                Response r = ClientTK.getConnessione().invia(
                        new Request(CommandType.CERCA_RISTORANTI, null).aggiungiParametro("citta", cittaCercata));
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }

            @Override
            protected void done() {
                try {
                    List<Ristorante> disponibili = get();
                    if (disponibili.isEmpty()) {
                        toast("Nessun ristorante trovato nella città di '" + cittaCercata + "'.", false);
                        filtra(filtroStelle);
                        return;
                    }

                    Window w = SwingUtilities.getWindowAncestor(RecensioniPanel.this);
                    FancyReviewSelectionDialog selectionDialog = new FancyReviewSelectionDialog(w, disponibili, cittaCercata);
                    selectionDialog.setVisible(true);

                    if (!selectionDialog.isSelezionato()) {
                        filtra(filtroStelle);
                        return;
                    }

                    String ristoranteScelto = selectionDialog.getNomeSelezionato();
                    RecensioneDialog dlg = new RecensioneDialog(w, ristoranteScelto, 3, "");
                    dlg.setVisible(true);
                    
                    if (!dlg.isConfermato()) {
                        filtra(filtroStelle);
                        return;
                    }

                    Response r = ClientTK.getConnessione().invia(
                            new Request(CommandType.CLIENTE_AGGIUNGI_RECENSIONE, ClientTK.getUtenteLoggato().getUsername())
                                    .aggiungiParametro("nomeRistorante", ristoranteScelto)
                                    .aggiungiParametro("stelle", dlg.getStelle())
                                    .aggiungiParametro("testo", dlg.getTesto()));
                    
                    toast((r.getMessaggio()!=null&&!r.getMessaggio().isBlank())?r.getMessaggio():(r.isSuccesso()?"Operazione completata.":"Operazione fallita."), r.isSuccesso());
                    refreshData();

                } catch (Exception ex) {
                    toast("Errore durante il recupero: " + ex.getMessage(), false);
                    filtra(filtroStelle);
                }
            }
        }.execute();
    }

    private void modificaRecensione() {
        if (!checkCliente() || cache.isEmpty()) return;
        Recensione sel = scegliRecensione("Seleziona la recensione da modificare");
        if (sel == null) return;
        
        RecensioneDialog dlg = new RecensioneDialog(
                SwingUtilities.getWindowAncestor(this), sel.getNomeRistorante(), sel.getStelle(), sel.getTesto());
        dlg.setVisible(true);
        if (!dlg.isConfermato()) return;
        
        try {
            Response r = ClientTK.getConnessione().invia(
                    new Request(CommandType.CLIENTE_MODIFICA_RECENSIONE, ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("nomeRistorante", sel.getNomeRistorante())
                            .aggiungiParametro("stelle", dlg.getStelle())
                            .aggiungiParametro("testo", dlg.getTesto()));
            toast((r.getMessaggio()!=null&&!r.getMessaggio().isBlank())?r.getMessaggio():(r.isSuccesso()?"Operazione completata.":"Operazione fallita."), r.isSuccesso());
            if (r.isSuccesso()) refreshData();
        } catch (Exception ex) { toast("Errore: " + ex.getMessage(), false); }
    }

    private void eliminaRecensione() {
        if (!checkCliente() || cache.isEmpty()) return;
        Recensione sel = scegliRecensione("Seleziona la recensione da eliminare");
        if (sel == null) return;
        
        int c = JOptionPane.showConfirmDialog(this, "Eliminare definitivamente la recensione per \"" + sel.getNomeRistorante() + "\"?",
                "Conferma Rimozione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (c != JOptionPane.YES_OPTION) return;
        
        try {
            Response r = ClientTK.getConnessione().invia(
                    new Request(CommandType.CLIENTE_ELIMINA_RECENSIONE, ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("nomeRistorante", sel.getNomeRistorante()));
            toast((r.getMessaggio()!=null&&!r.getMessaggio().isBlank())?r.getMessaggio():(r.isSuccesso()?"Operazione completata.":"Operazione fallita."), r.isSuccesso());
            if (r.isSuccesso()) refreshData();
        } catch (Exception ex) { toast("Errore: " + ex.getMessage(), false); }
    }

    private void rispondiRecensione() {
        if (!checkRistoratore() || cache.isEmpty()) return;
        Recensione sel = scegliRecensione("A quale recensione vuoi rispondere?");
        if (sel == null) return;
        
        RispostaDialog dlg = new RispostaDialog(SwingUtilities.getWindowAncestor(this),
                sel.getNomeRistorante(), sel.getUsernameCliente(),
                sel.hasRisposta() ? sel.getRisposta() : "");
        dlg.setVisible(true);
        if (!dlg.isConfermato()) return;
        
        try {
            Response r = ClientTK.getConnessione().invia(
                    new Request(CommandType.RISTORATORE_RISPONDI_RECENSIONE, ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("nomeRistorante", sel.getNomeRistorante())
                            .aggiungiParametro("usernameCliente", sel.getUsernameCliente())
                            .aggiungiParametro("risposta", dlg.getRisposta()));
            toast((r.getMessaggio()!=null&&!r.getMessaggio().isBlank())?r.getMessaggio():(r.isSuccesso()?"Operazione completata.":"Operazione fallita."), r.isSuccesso());
            if (r.isSuccesso()) refreshData();
        } catch (Exception ex) { toast("Errore: " + ex.getMessage(), false); }
    }

    // =========================================================================
    // POPUP DI SELEZIONE E METODI INTERNI DI APPOGGIO
    // =========================================================================

    private Recensione scegliRecensione(String titolo) {
        List<Recensione> lista = filtroStelle == 0 ? cache
                : cache.stream().filter(r -> r.getStelle() == filtroStelle).collect(Collectors.toList());
        if (lista.isEmpty()) { toast("Nessuna recensione disponibile con questo filtro.", false); return null; }
        
        String[] labels = lista.stream()
                .map(r -> r.getNomeRistorante() + " (" + r.getStelle() + " stelle)")
                .toArray(String[]::new);
                
        JComboBox<String> combo = new JComboBox<>(labels); 
        combo.setFont(UITheme.FONT_BODY);
        combo.setBackground(Color.WHITE);
        
        int res = JOptionPane.showConfirmDialog(this, combo, titolo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        return res == JOptionPane.OK_OPTION ? lista.get(combo.getSelectedIndex()) : null;
    }

    private void aggiornaVisibilitaPulsanti() {
        boolean loggato = ClientTK.isLoggato();
        boolean isC = loggato && ClientTK.getUtenteLoggato().isCliente();
        boolean isR = loggato && ClientTK.getUtenteLoggato().isRistoratore();
        // Bottoni cliente: solo per clienti loggati
        btnNuova.setVisible(isC);
        btnModifica.setVisible(isC);
        btnElimina.setVisible(isC);
        // Rispondi: SOLO per ristoratori loggati
        btnRispondi.setVisible(isR);
    }

    private boolean checkCliente() {
        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isCliente()) { toast("Azione riservata agli account cliente.", false); return false; } return true;
    }
    private boolean checkRistoratore() {
        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isRistoratore()) { toast("Azione riservata ai gestori delle attività.", false); return false; } return true;
    }
    private void toast(String msg, boolean ok) {
        // Fallback se il server non fornisce un messaggio
        String testo = (msg != null && !msg.isBlank()) ? msg
                : (ok ? "Operazione completata con successo." : "Si è verificato un errore.");
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background",      Color.WHITE);
        JOptionPane.showMessageDialog(this,
                "<html><body style='width:300px; font-family:Segoe UI; color:#0F172A; font-size:13px;'>"
                + testo + "</body></html>",
                ok ? "Esito" : "Attenzione",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }
    private void addLabel(String txt) {
        cardArea.removeAll();
        JLabel l = new JLabel(txt); l.setFont(UITheme.FONT_BODY); l.setForeground(UITheme.TEXT_MUTED);
        l.setBorder(new EmptyBorder(30, 12, 0, 0)); l.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardArea.add(l); cardArea.revalidate(); cardArea.repaint();
    }

    // =========================================================================
    // DIALOG DI SELEZIONE RISTORANTE SU BASE CITTÀ
    // =========================================================================

    private static class FancyReviewSelectionDialog extends JDialog {
        private boolean selezionato = false;
        private final JComboBox<String> cmbLista;

        public FancyReviewSelectionDialog(Window owner, List<Ristorante> lista, String citta) {
            super(owner, "Nuova recensione", ModalityType.APPLICATION_MODAL);
            setSize(540, 260);
            setLocationRelativeTo(owner);
            setResizable(false);

            JPanel root = new JPanel(new BorderLayout(16, 16));
            root.setBackground(Color.WHITE);
            root.setBorder(new EmptyBorder(24, 24, 24, 24));

            JLabel title = new JLabel("Locali disponibili a " + citta);
            title.setFont(UITheme.FONT_H2);
            title.setForeground(UITheme.TEXT);
            root.add(title, BorderLayout.NORTH);

            String[] elementi = lista.stream().map(Ristorante::getNome).toArray(String[]::new);
            cmbLista = new JComboBox<>(elementi);
            cmbLista.setFont(UITheme.FONT_BODY);
            cmbLista.setBackground(Color.WHITE);

            JPanel mid = new JPanel(new BorderLayout(0, 10));
            mid.setOpaque(false);
            mid.add(new JLabel("Seleziona quale ristorante vuoi recensire di questa città:"), BorderLayout.NORTH);
            mid.add(cmbLista, BorderLayout.CENTER);
            root.add(mid, BorderLayout.CENTER);

            JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
            footer.setOpaque(false);
            UITheme.TKButton btnC = UITheme.btnGhost("Annulla");
            UITheme.TKButton btnO = UITheme.btnPrimary("Procedi");

            btnC.addActionListener(e -> dispose());
            btnO.addActionListener(e -> {
                if (cmbLista.getSelectedIndex() != -1) {
                    selezionato = true;
                    dispose();
                }
            });
            footer.add(btnC); footer.add(btnO);
            root.add(footer, BorderLayout.SOUTH);
            setContentPane(root);
        }

        public boolean isSelezionato() { return selezionato; }
        public String getNomeSelezionato() {
            return (String) cmbLista.getSelectedItem();
        }
    }

    // =========================================================================
    // DIALOG COMPILAZIONE RECENSIONE
    // =========================================================================

    public static class RecensioneDialog extends JDialog {
        private boolean conf = false;
        private final JSlider   slStelle = new JSlider(1, 5, 3);
        private final JPanel    starsPreview;
        private final JTextArea txtTesto;

        public RecensioneDialog(Window owner, String nome, int stelle, String testo) {
            super(owner, "Compila Recensione – " + nome, ModalityType.APPLICATION_MODAL);
            setSize(560, 490);
            setLocationRelativeTo(owner); 
            setResizable(false);
            
            txtTesto = new JTextArea(10, 36);
            txtTesto.setFont(UITheme.FONT_BODY); 
            txtTesto.setLineWrap(true);
            txtTesto.setWrapStyleWord(true); 
            txtTesto.setBorder(new EmptyBorder(12, 14, 12, 14));
            txtTesto.setText(testo); 
            
            slStelle.setValue(stelle);
            starsPreview = buildPreviewPanel();
            slStelle.addChangeListener(e -> starsPreview.repaint());
            build(nome);
        }

        private JPanel buildPreviewPanel() {
            return new JPanel() {
                @Override 
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = UITheme.rh(g);
                    g2.setColor(UITheme.STAR);
                    int v = slStelle.getValue();
                    float sz = 14f, gap = 5f;
                    float total = 5 * sz + 4 * gap;
                    float sx = (getWidth() - total) / 2f + sz / 2, cy = getHeight() / 2f;
                    for (int i = 0; i < 5; i++) {
                        UITheme.drawStar(g2, sx + i * (sz + gap), cy, sz / 2, sz / 2 * 0.42f, i < v);
                    }
                    g2.dispose();
                }
                @Override public Dimension getPreferredSize() { return new Dimension(180, 32); }
                @Override public boolean   isOpaque()          { return false; }
            };
        }

        private void build(String nome) {
            JPanel root = new JPanel(new BorderLayout()); 
            root.setBackground(UITheme.CARD);
            root.add(UITheme.dialogHeader("Lascia una recensione", nome), BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout()); 
            form.setBackground(UITheme.CARD);
            form.setBorder(new EmptyBorder(22, 26, 16, 26));
            GridBagConstraints g = new GridBagConstraints(); 
            g.fill = GridBagConstraints.HORIZONTAL; 
            g.weightx = 1; 
            g.gridx = 0;

            g.gridy = 0; g.insets = new Insets(0, 0, 6, 0); form.add(UITheme.fieldLabel("VALUTAZIONE"), g);
            g.gridy = 1; slStelle.setOpaque(false); slStelle.setMajorTickSpacing(1);
            slStelle.setPaintTicks(true); slStelle.setSnapToTicks(true); form.add(slStelle, g);
            g.gridy = 2; g.insets = new Insets(4, 0, 18, 0); form.add(starsPreview, g);
            g.gridy = 3; g.insets = new Insets(0, 0, 8, 0); form.add(UITheme.fieldLabel("TESTO RECENSIONE"), g);
            
            g.gridy = 4; g.weighty = 1.0; g.fill = GridBagConstraints.BOTH;
            JScrollPane sc = new JScrollPane(txtTesto); 
            sc.setBorder(BorderFactory.createLineBorder(UITheme.CARD_BORDER, 1, true));
            form.add(sc, g); 
            root.add(form, BorderLayout.CENTER);

            JPanel footer = new JPanel(new GridLayout(1, 2, 14, 0)); 
            footer.setBackground(UITheme.CARD);
            footer.setBorder(new EmptyBorder(4, 26, 22, 26));
            UITheme.TKButton ba = UITheme.btnGhost("Annulla"), bi = UITheme.btnPrimary("Pubblica");
            ba.addActionListener(e -> dispose());
            bi.addActionListener(e -> { 
                if (txtTesto.getText().trim().isEmpty()) { UITheme.flashRed(txtTesto); return; } 
                conf = true; dispose(); 
            });
            footer.add(ba); footer.add(bi); 
            root.add(footer, BorderLayout.SOUTH);
            setContentPane(root);
        }

        public boolean isConfermato() { return conf; }
        public int     getStelle()     { return slStelle.getValue(); }
        public String  getTesto()      { return txtTesto.getText().trim(); }
    }

    // =========================================================================
    // DIALOG RISPOSTA DEL RISTORATORE
    // =========================================================================

    public static class RispostaDialog extends JDialog {
        private boolean conf = false;
        private final JTextArea txtRisposta;

        public RispostaDialog(Window owner, String ristorante, String cliente, String prev) {
            super(owner, "Risposta a @" + cliente, ModalityType.APPLICATION_MODAL);
            setSize(560, 410);
            setLocationRelativeTo(owner); 
            setResizable(false);
            
            txtRisposta = new JTextArea(8, 36);
            txtRisposta.setFont(UITheme.FONT_BODY); 
            txtRisposta.setLineWrap(true);
            txtRisposta.setWrapStyleWord(true); 
            txtRisposta.setBorder(new EmptyBorder(12, 14, 12, 14));
            txtRisposta.setText(prev);
            build(ristorante, cliente);
        }

        private void build(String ristorante, String cliente) {
            JPanel root = new JPanel(new BorderLayout()); 
            root.setBackground(UITheme.CARD);
            root.add(UITheme.dialogHeader("Rispondi alla recensione", ristorante + " · @" + cliente), BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout()); 
            form.setBackground(UITheme.CARD);
            form.setBorder(new EmptyBorder(22, 26, 16, 26));
            GridBagConstraints g = new GridBagConstraints(); 
            g.fill = GridBagConstraints.HORIZONTAL; 
            g.weightx = 1; 
            g.gridx = 0;
            
            g.gridy = 0; g.insets = new Insets(0, 0, 8, 0); form.add(UITheme.fieldLabel("TESTO DELLA RISPOSTA"), g);
            g.gridy = 1; g.weighty = 1.0; g.fill = GridBagConstraints.BOTH;
            JScrollPane sc = new JScrollPane(txtRisposta); 
            sc.setBorder(BorderFactory.createLineBorder(UITheme.CARD_BORDER, 1, true));
            form.add(sc, g); 
            root.add(form, BorderLayout.CENTER);

            JPanel footer = new JPanel(new GridLayout(1, 2, 14, 0)); 
            footer.setBackground(UITheme.CARD);
            footer.setBorder(new EmptyBorder(4, 26, 22, 26));
            UITheme.TKButton ba = UITheme.btnGhost("Annulla"), bi = UITheme.btnPrimary("Invia Risposta");
            ba.addActionListener(e -> dispose());
            bi.addActionListener(e -> { 
                if (txtRisposta.getText().trim().isEmpty()) { UITheme.flashRed(txtRisposta); return; } 
                conf = true; dispose(); 
            });
            footer.add(ba); footer.add(bi); 
            root.add(footer, BorderLayout.SOUTH);
            setContentPane(root);
        }

        public boolean isConfermato() { return conf; }
        public String  getRisposta()   { return txtRisposta.getText().trim(); }
    }
}