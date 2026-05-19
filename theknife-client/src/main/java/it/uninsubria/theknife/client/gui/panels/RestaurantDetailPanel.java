/**
 * TheKnife – Pannello dettaglio ristorante.
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
import it.uninsubria.theknife.common.model.Recensione;
import it.uninsubria.theknife.common.model.Ristorante;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello di dettaglio del ristorante ottimizzato.
 * Layout fluido, supporto cross-platform per i caratteri Unicode delle stelle
 * e dialoghi di inserimento recensioni integrati e spaziosi.
 */
public class RestaurantDetailPanel extends GradientPanel {

    private final FancyFrame parent;
    private Ristorante ristoranteCorrente;

    // Elementi dell'Header Informativo
    private final JLabel lblCucina    = new JLabel();
    private final JLabel lblNome      = new JLabel();
    private final JLabel lblStelle    = new JLabel();
    private final JLabel lblMeta      = new JLabel();
    private final JLabel lblDelivery  = new JLabel();
    private final JLabel lblPrenot    = new JLabel();

    // Pulsanti di Azione della Navbar
    private final UITheme.TKButton btnBack   = UITheme.btnGhost("← Torna ai risultati");
    private final UITheme.TKButton btnPref   = UITheme.btnGhost("Aggiungi ai preferiti");
    private final UITheme.TKButton btnReview = UITheme.btnPrimary("Scrivi una recensione");

    // Area Recensioni
    private final JPanel recArea   = new JPanel();
    private final JLabel lblRecHdr = new JLabel("Recensioni");

    public RestaurantDetailPanel(FancyFrame parent) {
        super(new BorderLayout());
        this.parent = parent;
        setBackground(UITheme.BG);
        initUI();
    }

    private void initUI() {
        add(buildNavBar(), BorderLayout.NORTH);
        add(buildScrollArea(), BorderLayout.CENTER);
    }

    private JPanel buildNavBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Color.WHITE);
        bar.setPreferredSize(new Dimension(0, 55));
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.CARD_BORDER),
                new EmptyBorder(0, 16, 0, 16)));

        btnBack.addActionListener(e -> parent.showCard(FancyFrame.CARD_SEARCH));
        bar.add(btnBack, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        actions.setOpaque(false);
        btnPref.addActionListener(e -> aggiungiPreferito());
        btnReview.addActionListener(e -> apriDialogRecensione());
        actions.add(btnPref);
        actions.add(btnReview);
        bar.add(actions, BorderLayout.EAST);
        return bar;
    }

    private JScrollPane buildScrollArea() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UITheme.BG);
        body.setBorder(new EmptyBorder(20, 24, 20, 24));

        body.add(buildInfoCard());
        body.add(Box.createVerticalStrut(24));
        body.add(buildRecensioniSection());

        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        return scroll;
    }

    private UITheme.CardPanel buildInfoCard() {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout());
        
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Riga 1: Tipo Cucina (Badge)
        JPanel cuisineWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cuisineWrapper.setOpaque(false);
        lblCucina.setFont(UITheme.FONT_LABEL);
        lblCucina.setForeground(UITheme.GOLD_DARK);
        lblCucina.setBackground(UITheme.GOLD_LIGHT);
        lblCucina.setOpaque(true);
        lblCucina.setBorder(new EmptyBorder(4, 10, 4, 10));
        cuisineWrapper.add(lblCucina);
        inner.add(cuisineWrapper);
        inner.add(Box.createVerticalStrut(12));

        // Riga 2: Nome Ristorante
        lblNome.setFont(UITheme.FONT_H1);
        lblNome.setForeground(UITheme.TEXT);
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(lblNome);
        inner.add(Box.createVerticalStrut(12));

        // Riga 3: Valutazioni e Posizione
        JPanel metaRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        metaRow.setOpaque(false);
        lblStelle.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16)); // Risolve bug stelle invisibili nell'header
        lblStelle.setForeground(UITheme.STAR);
        lblMeta.setFont(UITheme.FONT_BODY);
        lblMeta.setForeground(UITheme.TEXT_MUTED);
        metaRow.add(lblStelle);
        metaRow.add(Box.createHorizontalStrut(10));
        metaRow.add(lblMeta);
        metaRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(metaRow);
        inner.add(Box.createVerticalStrut(16));

        // Riga 4: Servizi Disponibili (Badges)
        JPanel badgeServizi = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        badgeServizi.setOpaque(false);
        badgeServizi.setAlignmentX(Component.LEFT_ALIGNMENT);
        badgeServizi.add(lblDelivery);
        badgeServizi.add(lblPrenot);
        inner.add(badgeServizi);

        card.add(inner, BorderLayout.CENTER);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        return card;
    }

    private JPanel buildRecensioniSection() {
        JPanel section = new JPanel(new BorderLayout(0, 16));
        section.setBackground(UITheme.BG);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblRecHdr.setFont(UITheme.FONT_H2);
        lblRecHdr.setForeground(UITheme.TEXT);
        section.add(lblRecHdr, BorderLayout.NORTH);

        recArea.setLayout(new BoxLayout(recArea, BoxLayout.Y_AXIS));
        recArea.setBackground(UITheme.BG);
        section.add(recArea, BorderLayout.CENTER);
        return section;
    }

    public void setRistorante(Ristorante r) {
        this.ristoranteCorrente = r;
        popolaInfoCard(r);
        aggiornaPulsanti();
        caricaRecensioni(r.getNome());
    }

    private void popolaInfoCard(Ristorante r) {
        lblCucina.setText(r.getTipoCucina().toUpperCase());
        lblNome.setText(r.getNome());

        int s = (int) Math.round(Math.min(r.getMediaStelle(), 5));
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) sb.append(i <= s ? "★" : "☆");
        lblStelle.setText(sb.toString());

        String recStr = r.getNumeroRecensioni() > 0
                ? String.format("%.1f (%d recensioni)", r.getMediaStelle(), r.getNumeroRecensioni())
                : "Nessuna recensione";

        lblMeta.setText("<html>· &nbsp; <b>" + recStr + "</b> &nbsp; · &nbsp; " 
                + r.getCitta() + ", " + r.getNazione() + " &nbsp; · &nbsp; " 
                + String.format("Fascia %s", String.format("%.0f€", r.getFasciaPrezzo())) + "</html>");

        configuraBadge(lblDelivery, r.isDelivery(), "DELIVERY", new Color(220, 252, 231), new Color(22, 101, 52));
        configuraBadge(lblPrenot, r.isPrenotazione(), "PRENOTAZIONE", new Color(219, 234, 254), new Color(29, 78, 216));

        lblRecHdr.setText("Recensioni dell'attività" + (r.getNumeroRecensioni() > 0 ? " (" + r.getNumeroRecensioni() + ")" : ""));
        revalidate(); repaint();
    }

    private void configuraBadge(JLabel lbl, boolean visible, String txt, Color bg, Color fg) {
        lbl.setVisible(visible);
        if (visible) {
            lbl.setText(txt);
            lbl.setFont(UITheme.FONT_LABEL);
            lbl.setForeground(fg);
            lbl.setOpaque(true);
            lbl.setBackground(bg);
            lbl.setBorder(new EmptyBorder(4, 12, 4, 12));
        }
    }

    private void aggiornaPulsanti() {
        boolean isCliente = ClientTK.isLoggato() && ClientTK.getUtenteLoggato().isCliente();
        btnPref.setVisible(isCliente);
        btnReview.setVisible(isCliente);
    }

    private void caricaRecensioni(String nome) {
        recArea.removeAll();
        JLabel loading = new JLabel("Caricamento recensioni in corso...");
        loading.setFont(UITheme.FONT_BODY);
        loading.setForeground(UITheme.TEXT_MUTED);
        recArea.add(loading);
        recArea.revalidate(); recArea.repaint();

        new SwingWorker<List<Recensione>, Void>() {
            @Override
            protected List<Recensione> doInBackground() throws Exception {
                Response r = ClientTK.getConnessione().invia(
                        new Request(CommandType.VISUALIZZA_RECENSIONI, null)
                                .aggiungiParametro("nomeRistorante", nome));
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }

            @Override
            protected void done() {
                recArea.removeAll();
                try {
                    List<Recensione> lista = get();
                    if (lista.isEmpty()) {
                        JLabel empty = new JLabel("Non ci sono ancora recensioni per questo locale. Condividi per primo la tua esperienza!");
                        empty.setFont(UITheme.FONT_BODY);
                        empty.setForeground(UITheme.TEXT_MUTED);
                        recArea.add(empty);
                    } else {
                        for (Recensione rec : lista) {
                            recArea.add(buildRecCard(rec));
                            recArea.add(Box.createVerticalStrut(12));
                        }
                    }
                } catch (Exception ex) {
                    JLabel err = new JLabel("Errore durante il recupero dei dati: " + ex.getMessage());
                    err.setFont(UITheme.FONT_BODY);
                    err.setForeground(UITheme.DANGER);
                    recArea.add(err);
                }
                recArea.revalidate(); recArea.repaint();
            }
        }.execute();
    }

    private JPanel buildRecCard(Recensione rec) {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout());
        
        JPanel inner = new JPanel(new BorderLayout(0, 12));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Intestazione: Nome utente e Stelle date
        JPanel head = new JPanel(new BorderLayout());
        head.setOpaque(false);

        String uname = ClientTK.isLoggato() ? rec.getUsernameCliente() : "Utente verificato";
        JLabel user = new JLabel(uname);
        user.setFont(UITheme.FONT_H3);
        user.setForeground(UITheme.TEXT);
        head.add(user, BorderLayout.WEST);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) sb.append(i <= rec.getStelle() ? "★" : "☆");
        JLabel starsLbl = new JLabel(sb.toString());
        starsLbl.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15)); // Risolve bug delle stelle invisibili nel feed
        starsLbl.setForeground(UITheme.STAR);
        head.add(starsLbl, BorderLayout.EAST);
        inner.add(head, BorderLayout.NORTH);

        // Testo della recensione
        JTextArea txtTesto = new JTextArea(rec.getTesto());
        txtTesto.setFont(UITheme.FONT_BODY);
        txtTesto.setForeground(UITheme.TEXT);
        txtTesto.setLineWrap(true);
        txtTesto.setWrapStyleWord(true);
        txtTesto.setEditable(false);
        txtTesto.setOpaque(false);
        inner.add(txtTesto, BorderLayout.CENTER);

        // Risposta del Ristoratore (Se presente)
        if (rec.hasRisposta()) {
            JPanel reply = new JPanel(new BorderLayout(0, 6)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = UITheme.rh(g);
                    g2.setColor(UITheme.INFO_BG);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.setColor(UITheme.INFO_FG);
                    g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(2, 6, 2, getHeight() - 6);
                    g2.dispose();
                }
            };
            reply.setOpaque(false);
            reply.setBorder(new EmptyBorder(12, 16, 12, 16));

            JLabel rl = new JLabel("RISPOSTA DEL RISTORATORE");
            rl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            rl.setForeground(UITheme.INFO_FG);

            JTextArea rt = new JTextArea(rec.getRisposta());
            rt.setFont(UITheme.FONT_BODY);
            rt.setForeground(UITheme.TEXT);
            rt.setLineWrap(true);
            rt.setWrapStyleWord(true);
            rt.setEditable(false);
            rt.setOpaque(false);

            reply.add(rl, BorderLayout.NORTH);
            reply.add(rt, BorderLayout.CENTER);
            inner.add(reply, BorderLayout.SOUTH);
        }

        card.add(inner, BorderLayout.CENTER);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        return card;
    }

    // =========================================================================
    // AZIONI DI SISTEMA
    // =========================================================================

    private void aggiungiPreferito() {
        if (ristoranteCorrente == null) return;
        try {
            Response r = ClientTK.getConnessione().invia(
                    new Request(CommandType.CLIENTE_AGGIUNGI_PREFERITO, ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("nomeRistorante", ristoranteCorrente.getNome()));
            
            showFancyMessage(r.getMessaggio(), r.isSuccesso() ? "Operazione Completata" : "Attenzione",
                    r.isSuccesso() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            showFancyMessage("Impossibile salvare nei preferiti: " + ex.getMessage(), "Errore di rete", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void apriDialogRecensione() {
        if (ristoranteCorrente == null) return;
        
        Window ancestor = SwingUtilities.getWindowAncestor(this);
        FancyReviewDialog dlg = new FancyReviewDialog(ancestor, ristoranteCorrente.getNome());
        dlg.setVisible(true);
        
        if (!dlg.isConfermato()) return;

        try {
            Response r = ClientTK.getConnessione().invia(
                    new Request(CommandType.CLIENTE_AGGIUNGI_RECENSIONE, ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("nomeRistorante", ristoranteCorrente.getNome())
                            .aggiungiParametro("stelle", dlg.getStelle())
                            .aggiungiParametro("testo", dlg.getTesto()));
            
            showFancyMessage(r.getMessaggio(), r.isSuccesso() ? "Recensione Pubblicata" : "Attenzione",
                    r.isSuccesso() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            
            if (r.isSuccesso()) caricaRecensioni(ristoranteCorrente.getNome());
        } catch (Exception ex) {
            showFancyMessage("Errore durante l'invio della recensione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showFancyMessage(String msg, String title, int type) {
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        JOptionPane.showMessageDialog(this, "<html><body style='width: 300px; font-family: Segoe UI;'>" + msg + "</body></html>", title, type);
    }

    // =========================================================================
    // FINESTRA MODALE DI RECENSIONE PERSONALIZZATA
    // =========================================================================

    private static class FancyReviewDialog extends JDialog {
        private boolean confermato = false;
        private final JComboBox<String> cmbStelle;
        private final JTextArea txtAreaRecensione;

        public FancyReviewDialog(Window owner, String nomeRistorante) {
            super(owner, "Lascia una recensione per " + nomeRistorante, ModalityType.APPLICATION_MODAL);
            setSize(520, 380);
            setLocationRelativeTo(owner);
            setResizable(false);
            
            JPanel mainPanel = new JPanel(new BorderLayout(16, 16));
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            // Pannello Superiore: Titolo & Punteggio
            JPanel topPanel = new JPanel(new GridLayout(2, 1, 6, 6));
            topPanel.setOpaque(false);
            
            JLabel lblInfo = new JLabel("Raccontaci la tua esperienza culinaria");
            lblInfo.setFont(UITheme.FONT_H2);
            lblInfo.setForeground(UITheme.TEXT);
            topPanel.add(lblInfo);

            JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
            scorePanel.setOpaque(false);
            scorePanel.add(new JLabel("Valutazione del locale:  "));
            
            String[] opzioniStelle = {"5 Stelle ★★★★★", "4 Stelle ★★★★", "3 Stelle ★★★", "2 Stelle ★★", "1 Stella ★"};
            cmbStelle = new JComboBox<>(opzioniStelle);
            cmbStelle.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14)); // Risolve bug stelle nel menu a tendina
            cmbStelle.setBackground(Color.WHITE);
            scorePanel.add(cmbStelle);
            topPanel.add(scorePanel);
            mainPanel.add(topPanel, BorderLayout.NORTH);

            // Centro: Area di Testo
            txtAreaRecensione = new JTextArea();
            txtAreaRecensione.setFont(UITheme.FONT_BODY);
            txtAreaRecensione.setLineWrap(true);
            txtAreaRecensione.setWrapStyleWord(true);
            txtAreaRecensione.setBorder(new EmptyBorder(8, 8, 8, 8));
            
            JScrollPane txtScroll = new JScrollPane(txtAreaRecensione);
            txtScroll.setBorder(BorderFactory.createLineBorder(UITheme.CARD_BORDER, 1, true));
            mainPanel.add(txtScroll, BorderLayout.CENTER);

            // Sud: Azioni
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
            bottomPanel.setOpaque(false);
            
            UITheme.TKButton btnAnnulla = UITheme.btnGhost("Annulla");
            UITheme.TKButton btnInvia = UITheme.btnPrimary("Invia Recensione");

            btnAnnulla.addActionListener(e -> dispose());
            btnInvia.addActionListener(e -> {
                if (txtAreaRecensione.getText().strip().isEmpty()) {
                    UIManager.put("OptionPane.background", Color.WHITE);
                    UIManager.put("Panel.background", Color.WHITE);
                    JOptionPane.showMessageDialog(this, "Il testo della recensione non può essere vuoto.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                confermato = true;
                dispose();
            });

            bottomPanel.add(btnAnnulla);
            bottomPanel.add(btnInvia);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);

            setContentPane(mainPanel);
        }

        public boolean isConfermato() { return confermato; }
        public String getTesto() { return txtAreaRecensione.getText().strip(); }
        public int getStelle() {
            return 5 - cmbStelle.getSelectedIndex();
        }
    }
}