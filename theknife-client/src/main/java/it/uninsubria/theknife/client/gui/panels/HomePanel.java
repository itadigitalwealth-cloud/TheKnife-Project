/**
 * TheKnife – Modulo Client
 * Schermata principale con ristoranti per luogo/domicilio.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client.gui.panels;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.client.gui.FancyFrame;
import it.uninsubria.theknife.client.gui.GradientPanel;
import it.uninsubria.theknife.client.gui.UITheme;
import it.uninsubria.theknife.common.CommandType;
import it.uninsubria.theknife.common.Request;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Ristorante;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Pannello Home: hero section + ricerca rapida per luogo + lista ristoranti.
 *
 * <p>
 * Implementa il requisito delle specifiche (pag. 14):
 * <ul>
 *   <li><b>Utente guest</b>: mostra un campo di testo per inserire una città
 *       e carica i ristoranti in quella città al click su "Cerca".</li>
 *   <li><b>Utente loggato</b>: carica automaticamente i ristoranti nella città
 *       di domicilio dell'utente (campo {@code domicilio} del profilo).</li>
 * </ul>
 * </p>
 *
 * <p>Il metodo {@link #refresh()} viene richiamato da {@link FancyFrame}
 * ogni volta che questa card viene visualizzata, aggiornando la lista
 * in base allo stato di login corrente.</p>
 */
public class HomePanel extends GradientPanel {

    private final FancyFrame parent;

    /* ---- Componenti di ricerca ---------------------------------------- */
    private final JTextField  txtLuogo       = UITheme.textField(18);
    private final UITheme.StyledButton btnCercaLuogo = UITheme.btnPrimary("Cerca");

    /* ---- Area risultati ristoranti vicini ----------------------------- */
    private final JPanel     ristorantiPanel = new JPanel();
    private final JLabel     lblAreaTitolo   = new JLabel();
    private final JLabel     lblAreaSub      = new JLabel();

    /**
     * Costruisce la HomePanel.
     *
     * @param parent frame principale
     */
    public HomePanel(FancyFrame parent) {
        super(new BorderLayout());
        this.parent = parent;
        setBackground(UITheme.BG);
        initUI();
    }

    // =========================================================================
    // COSTRUZIONE UI
    // =========================================================================

    private void initUI() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BG);

        content.add(buildHero());
        content.add(Box.createVerticalStrut(28));
        content.add(buildRistorantiVicini());
        content.add(Box.createVerticalStrut(24));
        content.add(buildFeatures());
        content.add(Box.createVerticalStrut(24));
        content.add(buildCTA());
        content.add(Box.createVerticalStrut(24));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(UITheme.BG);
        scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    // ---- Hero con campo ricerca rapida ----------------------------------

    private JPanel buildHero() {
        JPanel hero = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, UITheme.SIDEBAR_BG,
                        getWidth(), getHeight(), new Color(25, 35, 65));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                // Cerchi decorativi oro semitrasparenti
                g2.setColor(new Color(196, 160, 72, 15));
                g2.fillOval(getWidth() - 200, -80, 340, 340);
                g2.fillOval(-100, getHeight() - 130, 220, 220);
                g2.dispose();
            }
        };
        hero.setLayout(new GridBagLayout());
        hero.setOpaque(false);
        hero.setPreferredSize(new Dimension(0, 240));
        hero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        hero.setBorder(new EmptyBorder(0, 44, 0, 44));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(0, 0, 6, 0);

        JLabel tagline = new JLabel("La tua guida ai migliori ristoranti");
        tagline.setFont(UITheme.FONT_SMALL);
        tagline.setForeground(UITheme.GOLD);

        JLabel title = new JLabel("<html>Scopri dove<br>mangiare bene.</html>");
        title.setFont(UITheme.FONT_DISPLAY);
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel(
                "Inserisci una città per scoprire i ristoranti disponibili.");
        sub.setFont(UITheme.FONT_BODY);
        sub.setForeground(new Color(180, 190, 210));

        // Barra di ricerca rapida
        JPanel searchBar = buildSearchBar();

        hero.add(tagline,   gc);
        gc.gridy = 1; gc.insets = new Insets(0, 0, 10, 0);
        hero.add(title,     gc);
        gc.gridy = 2; gc.insets = new Insets(0, 0, 16, 0);
        hero.add(sub,       gc);
        gc.gridy = 3; gc.insets = new Insets(0, 0, 0, 0);
        hero.add(searchBar, gc);

        return hero;
    }

    /**
     * Costruisce la barra di ricerca rapida per luogo (campo + pulsante).
     * Usata dagli utenti guest.
     */
    private JPanel buildSearchBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        bar.setOpaque(false);

        txtLuogo.setPreferredSize(new Dimension(260, 38));
        txtLuogo.setToolTipText("Inserisci una città (es. Milano, Roma...)");

        btnCercaLuogo.addActionListener(e -> cercaPerLuogo());
        // Anche con Invio nel campo testo
        txtLuogo.addActionListener(e -> cercaPerLuogo());

        bar.add(txtLuogo);
        bar.add(btnCercaLuogo);
        return bar;
    }

    // ---- Sezione ristoranti vicini / per domicilio ----------------------

    private JPanel buildRistorantiVicini() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 10));
        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Intestazione sezione
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        lblAreaTitolo.setFont(UITheme.FONT_H2);
        lblAreaTitolo.setForeground(UITheme.TEXT);
        lblAreaTitolo.setText("Ristoranti disponibili");

        lblAreaSub.setFont(UITheme.FONT_SMALL);
        lblAreaSub.setForeground(UITheme.TEXT_MUTED);
        lblAreaSub.setText("Inserisci una città per visualizzare i ristoranti.");

        header.add(lblAreaTitolo, BorderLayout.NORTH);
        header.add(lblAreaSub,   BorderLayout.CENTER);
        wrapper.add(header, BorderLayout.NORTH);

        // Griglia ristoranti
        ristorantiPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 12, 12));
        ristorantiPanel.setOpaque(false);
        wrapper.add(ristorantiPanel, BorderLayout.CENTER);

        return wrapper;
    }

    // ---- Feature cards --------------------------------------------------

    private JPanel buildFeatures() {
        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        row.add(featureCard("◎", "Cerca",
                "Filtra per città, cucina,<br>fascia di prezzo e stelle."));
        row.add(featureCard("♥", "Salva",
                "Crea la tua lista<br>di ristoranti preferiti."));
        row.add(featureCard("✦", "Recensisci",
                "Condividi la tua<br>esperienza con gli altri."));
        return row;
    }

    private JPanel featureCard(String icon, String title, String desc) {
        UITheme.ShadowPanel card = UITheme.cardPanel(new BorderLayout());
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(16, 18, 16, 18));

        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI", Font.BOLD, 20));
        ico.setForeground(UITheme.GOLD);
        ico.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel t = new JLabel(title);
        t.setFont(UITheme.FONT_H2);
        t.setForeground(UITheme.TEXT);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        t.setBorder(new EmptyBorder(6, 0, 4, 0));

        JLabel d = new JLabel("<html>" + desc + "</html>");
        d.setFont(UITheme.FONT_BODY);
        d.setForeground(UITheme.TEXT_MUTED);
        d.setAlignmentX(Component.LEFT_ALIGNMENT);

        inner.add(ico); inner.add(t); inner.add(d);
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    // ---- CTA ristoratori ------------------------------------------------

    private JPanel buildCTA() {
        JPanel cta = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(UITheme.GOLD_LIGHT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(UITheme.GOLD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2.dispose();
            }
        };
        cta.setOpaque(false);
        cta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        cta.setBorder(new EmptyBorder(14, 24, 14, 24));

        JLabel lbl = new JLabel(
                "Sei un ristoratore? Aggiungi il tuo locale e raggiungi nuovi clienti.");
        lbl.setFont(UITheme.FONT_BODY);
        lbl.setForeground(UITheme.TEXT);
        cta.add(lbl, BorderLayout.WEST);

        UITheme.StyledButton btn = UITheme.btnPrimary("Registra il tuo ristorante");
        btn.addActionListener(e -> parent.showCard(FancyFrame.CARD_RISTORANTI));
        cta.add(btn, BorderLayout.EAST);
        return cta;
    }

    // =========================================================================
    // LOGICA DI CARICAMENTO RISTORANTI
    // =========================================================================

    /**
     * Aggiorna il pannello in base allo stato di autenticazione corrente.
     * <p>
     * Chiamato da {@link FancyFrame} ogni volta che la card Home viene
     * visualizzata:
     * <ul>
     *   <li>Se l'utente è <b>loggato</b> e ha un domicilio: carica
     *       automaticamente i ristoranti nella sua città di domicilio
     *       e nasconde il campo di ricerca manuale.</li>
     *   <li>Se <b>guest</b>: mostra il campo di testo per inserire la città.</li>
     * </ul>
     * </p>
     */
    public void refresh() {
        if (ClientTK.isLoggato()) {
            String domicilio = ClientTK.getUtenteLoggato().getDomicilio();
            if (domicilio != null && !domicilio.isBlank()) {
                // Utente loggato con domicilio: carica automaticamente
                txtLuogo.setText(domicilio);
                txtLuogo.setVisible(false);
                btnCercaLuogo.setVisible(false);
                lblAreaTitolo.setText("Ristoranti a " + domicilio);
                lblAreaSub.setText("Basato sul tuo domicilio");
                caricaRistoranti(domicilio);
            } else {
                // Loggato senza domicilio
                txtLuogo.setVisible(true);
                btnCercaLuogo.setVisible(true);
                lblAreaTitolo.setText("Ristoranti disponibili");
                lblAreaSub.setText("Inserisci una città per visualizzare i ristoranti.");
            }
        } else {
            // Guest
            txtLuogo.setVisible(true);
            btnCercaLuogo.setVisible(true);
            lblAreaTitolo.setText("Ristoranti disponibili");
            lblAreaSub.setText("Inserisci una città per visualizzare i ristoranti.");
            ristorantiPanel.removeAll();
            ristorantiPanel.revalidate();
            ristorantiPanel.repaint();
        }
    }

    /**
     * Gestisce il click su "Cerca" nella barra di ricerca rapida.
     * Legge la città inserita dall'utente e chiama {@link #caricaRistoranti(String)}.
     */
    private void cercaPerLuogo() {
        String luogo = txtLuogo.getText().trim();
        if (luogo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Inserisci il nome di una città.",
                    "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        lblAreaTitolo.setText("Ristoranti a " + luogo);
        lblAreaSub.setText("Risultati per la città cercata");
        caricaRistoranti(luogo);
    }

    /**
     * Richiede al server i ristoranti nella città specificata e li mostra
     * come card cliccabili nella sezione "Ristoranti disponibili".
     *
     * @param citta città da ricercare
     */
    private void caricaRistoranti(String citta) {
        ristorantiPanel.removeAll();

        // Indicatore di caricamento
        JLabel loading = new JLabel("Caricamento...");
        loading.setFont(UITheme.FONT_BODY);
        loading.setForeground(UITheme.TEXT_MUTED);
        ristorantiPanel.add(loading);
        ristorantiPanel.revalidate();
        ristorantiPanel.repaint();

        // Eseguiamo la chiamata in un thread separato per non bloccare l'EDT
        SwingWorker<List<Ristorante>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Ristorante> doInBackground() throws Exception {
                Request req = new Request(CommandType.CERCA_RISTORANTI,
                        ClientTK.isLoggato()
                            ? ClientTK.getUtenteLoggato().getUsername()
                            : null)
                        .aggiungiParametro("citta", citta);
                Response resp = ClientTK.getConnessione().invia(req);
                if (resp.isSuccesso()) {
                    return resp.getDatoTipizzato();
                }
                return List.of();
            }

            @Override
            protected void done() {
                ristorantiPanel.removeAll();
                try {
                    List<Ristorante> lista = get();
                    if (lista.isEmpty()) {
                        JLabel nessuno = new JLabel(
                                "Nessun ristorante trovato a \"" + citta + "\".");
                        nessuno.setFont(UITheme.FONT_BODY);
                        nessuno.setForeground(UITheme.TEXT_MUTED);
                        ristorantiPanel.add(nessuno);
                    } else {
                        lista.forEach(HomePanel.this::addRistoranteCard);
                    }
                } catch (Exception ex) {
                    JLabel err = new JLabel("Errore: " + ex.getMessage());
                    err.setFont(UITheme.FONT_BODY);
                    err.setForeground(UITheme.DANGER);
                    ristorantiPanel.add(err);
                }
                ristorantiPanel.revalidate();
                ristorantiPanel.repaint();
            }
        };
        worker.execute();
    }

    /**
     * Crea e aggiunge una mini-card cliccabile per il ristorante.
     * Al click apre il pannello di dettaglio.
     *
     * @param r ristorante da visualizzare
     */
    private void addRistoranteCard(Ristorante r) {
        UITheme.ShadowPanel card = UITheme.cardPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(210, 100));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel nome = new JLabel(r.getNome());
        nome.setFont(UITheme.FONT_H3);
        nome.setForeground(UITheme.TEXT);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel info = new JLabel(r.getTipoCucina() + "  ·  " +
                String.format("%.0f€", r.getFasciaPrezzo()));
        info.setFont(UITheme.FONT_SMALL);
        info.setForeground(UITheme.TEXT_MUTED);
        info.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Stelle
        int stelle = (int) Math.round(r.getMediaStelle());
        StringBuilder starStr = new StringBuilder();
        for (int i = 0; i < 5; i++)
            starStr.append(i < stelle ? "★" : "☆");
        JLabel stars = new JLabel(starStr.toString());
        stars.setFont(UITheme.FONT_SMALL);
        stars.setForeground(UITheme.STAR);
        stars.setAlignmentX(Component.LEFT_ALIGNMENT);

        inner.add(nome);
        inner.add(Box.createVerticalStrut(4));
        inner.add(info);
        inner.add(Box.createVerticalStrut(4));
        inner.add(stars);
        card.add(inner, BorderLayout.CENTER);

        // Click → dettaglio
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                parent.getDetailPanel().setRistorante(r);
                parent.showCard(FancyFrame.CARD_DETAIL);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                inner.setBackground(UITheme.PRIMARY_LIGHT);
                card.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                inner.setBackground(UITheme.CARD);
                card.repaint();
            }
        });

        ristorantiPanel.add(card);
    }

    // =========================================================================
    // WRAP LAYOUT – layout a griglia che va a capo automaticamente
    // =========================================================================

    /**
     * Layout a griglia automatica: aggiunge componenti su più righe
     * se la larghezza del pannello non è sufficiente per una sola riga.
     * Usato per la griglia delle mini-card ristoranti.
     */
    private static class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;
                int hgap = getHgap(), vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - insets.left - insets.right - hgap * 2;
                int rowWidth = 0, rowHeight = 0, totalHeight = insets.top + insets.bottom + vgap * 2;

                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = preferred
                                ? m.getPreferredSize() : m.getMinimumSize();
                        if (rowWidth + d.width > maxWidth && rowWidth > 0) {
                            totalHeight += rowHeight + vgap;
                            rowWidth = 0; rowHeight = 0;
                        }
                        rowWidth += d.width + hgap;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }
                totalHeight += rowHeight;
                return new Dimension(targetWidth, totalHeight);
            }
        }
    }
}