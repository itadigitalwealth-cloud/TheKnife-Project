/**
 * TheKnife – Modulo Client - Pannello Preferiti
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
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
 * Pannello per la visualizzazione e gestione dei ristoranti preferiti del cliente.
 */
public class PreferitiPanel extends GradientPanel {

    private final FancyFrame parent;
    private List<Ristorante> cache = new ArrayList<>();
    private boolean ordinaPerSt = false;

    // Rimosso WrapLayout temporaneamente per usare un GridLayout dinamico controllato che garantisce la visibilità
    private final JPanel gridPanel = new JPanel();
    private final JLabel lblCount = new JLabel("");
    private final UITheme.TKButton btnSort = UITheme.btnGhost("Ordina per stelle");
    private JScrollPane scroll;

    /**
     * Costruisce il pannello dei preferiti.
     *
     * @param parent la finestra principale {@link FancyFrame} usata per la navigazione
     */
    public PreferitiPanel(FancyFrame parent) {
        super(new BorderLayout());
        this.parent = parent;
        setBackground(UITheme.BG);
        initUI();
    }

    private void initUI() {
        add(buildTopBar(), BorderLayout.NORTH);

        // Pannello principale del contenuto con BoxLayout verticale
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BG);
        content.setBorder(new EmptyBorder(15, 20, 20, 20));

        lblCount.setFont(UITheme.FONT_SMALL);
        lblCount.setForeground(UITheme.TEXT_MUTED);
        lblCount.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblCount);
        content.add(Box.createVerticalStrut(10));

        // Configurazione iniziale della griglia
        gridPanel.setBackground(UITheme.BG);
        gridPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(gridPanel);

        scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(UITheme.BG);
        scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll, BorderLayout.CENTER);
        add(buildBottomBar(), BorderLayout.SOUTH);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Color.WHITE);
        bar.setPreferredSize(new Dimension(0, 65));
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.CARD_BORDER),
                new EmptyBorder(0, 20, 0, 20)));

        JPanel left = new JPanel(new GridBagLayout());
        left.setOpaque(false);
        JLabel title = new JLabel("I miei preferiti");
        title.setFont(UITheme.FONT_H1);
        title.setForeground(UITheme.TEXT);
        left.add(title);

        btnSort.addActionListener(e -> {
            ordinaPerSt = !ordinaPerSt;
            btnSort.setText(ordinaPerSt ? "Ordine alfabetico" : "Ordina per stelle");
            popolaGriglia();
        });

        bar.add(left, BorderLayout.WEST);
        bar.add(btnSort, BorderLayout.EAST);
        return bar;
    }

    private JPanel buildBottomBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 12));
        bar.setBackground(Color.WHITE);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.CARD_BORDER));

        UITheme.TKButton btnAgg = UITheme.btnPrimary("+ Aggiungi Ristorante");
        btnAgg.addActionListener(e -> aggiungiManualeFlusso());
        bar.add(btnAgg);
        return bar;
    }

    /**
     * Ricarica dal server la lista dei ristoranti preferiti dell'utente corrente
     * e aggiorna la griglia. Mostra un messaggio appropriato se l'utente non è
     * loggato o non ha il ruolo di cliente.
     */
    public void refreshData() {
        gridPanel.removeAll();

        if (!ClientTK.isLoggato()) {
            aggiungiLabel("Effettua il login per vedere i tuoi ristoranti preferiti.");
            lblCount.setText(""); return;
        }
        if (!ClientTK.getUtenteLoggato().isCliente()) {
            aggiungiLabel("I preferiti sono una funzionalità riservata ai clienti.");
            lblCount.setText(""); return;
        }

        aggiungiLabel("Caricamento in corso...");

        new SwingWorker<List<Ristorante>, Void>() {
            @Override
            protected List<Ristorante> doInBackground() throws Exception {
                Response r = ClientTK.getConnessione().invia(
                        new Request(CommandType.CLIENTE_VISUALIZZA_PREFERITI,
                                ClientTK.getUtenteLoggato().getUsername()));
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }

            @Override
            protected void done() {
                try {
                    cache = get();
                   // System.out.println("[DEBUG] Elementi arrivati a destinazione: " + (cache != null ? cache.size() : "null"));
                    popolaGriglia();
                } catch (Exception ex) {
                    gridPanel.removeAll();
                    aggiungiLabel("Errore durante il caricamento: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void popolaGriglia() {
        gridPanel.removeAll();
        if (cache == null || cache.isEmpty()) {
            lblCount.setText("0 ristoranti salvati");
            aggiungiLabel("Non hai ancora ristoranti preferiti. Cercane uno per città per aggiunculi.");
            revalidate();
            repaint();
            return;
        }

        lblCount.setText(cache.size() + (cache.size() == 1 ? " ristorante salvato" : " ristoranti salvati"));

        List<Ristorante> lista = ordinaPerSt
                ? cache.stream().sorted(Comparator.comparingDouble(Ristorante::getMediaStelle).reversed()).toList()
                : cache.stream().sorted(Comparator.comparing(Ristorante::getNome)).toList();

        // FIX LAYOUT: Configura una griglia fissa adattiva basata sul numero di elementi reali arrivati
        // Questo impedisce a WrapLayout di collassare l'altezza a zero pixel
        int colonne = 3; // Mostra 3 card per riga
        int righe = (int) Math.ceil((double) lista.size() / colonne);
        gridPanel.setLayout(new GridLayout(righe, colonne, 16, 16));

        // Popola la griglia con le schede dei ristoranti
        lista.forEach(this::addCard);

        // Se l'ultima riga non è piena, aggiungiamo spazi vuoti invisibili per non spaginare i componenti di GridLayout
        int celleVuote = (righe * colonne) - lista.size();
        for (int i = 0; i < celleVuote; i++) {
            JPanel dummy = new JPanel();
            dummy.setOpaque(false);
            gridPanel.add(dummy);
        }

        // Forza il calcolo strutturale delle altezze di Swing in modo sincrono
        gridPanel.revalidate();
        gridPanel.repaint();
        scroll.revalidate();
        scroll.repaint();
        this.revalidate();
        this.repaint();
    }

    private void addCard(Ristorante r) {
        UITheme.CardPanel card = UITheme.cardPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(280, 170));
        card.setMinimumSize(new Dimension(280, 170));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD);
        inner.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel badgeWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        badgeWrap.setOpaque(false);
        badgeWrap.add(UITheme.badgeCucina(r.getTipoCucina()));
        topRow.add(badgeWrap, BorderLayout.WEST);

        JButton btnX = buildRemoveBtn(r);
        // Wrapper a dimensione fissa: impedisce che BorderLayout.EAST
        // allunghi il bottone a tutta l'altezza della card (creava il grande ovale rosa)
        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnWrap.setOpaque(false);
        btnWrap.add(btnX);
        topRow.add(btnWrap, BorderLayout.EAST);

        // Tronca il nome se troppo lungo per la card
        String nomeStr = r.getNome().length() > 22 ? r.getNome().substring(0,20)+"..." : r.getNome();
        JLabel nome = new JLabel(nomeStr);
        nome.setFont(UITheme.FONT_H2);
        nome.setForeground(UITheme.TEXT);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT);
        nome.setBorder(new EmptyBorder(8, 0, 3, 0));

        JLabel loc = new JLabel(r.getCitta() + "  ·  " + String.format("%.0f€", r.getFasciaPrezzo()));
        loc.setFont(UITheme.FONT_SMALL);
        loc.setForeground(UITheme.TEXT_MUTED);
        loc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        bottom.setOpaque(false);
        bottom.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(UITheme.starLabel(r.getMediaStelle(), r.getNumeroRecensioni()));
        if (r.isDelivery()) bottom.add(UITheme.pillDelivery());
        if (r.isPrenotazione()) bottom.add(UITheme.pillPrenotazione());

        inner.add(topRow);
        inner.add(nome);
        inner.add(loc);
        inner.add(Box.createVerticalStrut(8));
        inner.add(bottom);
        card.add(inner, BorderLayout.CENTER);

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == btnX) return;
                parent.getDetailPanel().setRistorante(r);
                parent.showCard(FancyFrame.CARD_DETAIL);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setHovered(true);
                inner.setBackground(UITheme.PRIMARY_LIGHT);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setHovered(false);
                inner.setBackground(UITheme.CARD);
            }
        };
        card.addMouseListener(ma);
        inner.addMouseListener(ma);

        gridPanel.add(card);
    }

    private JButton buildRemoveBtn(Ristorante r) {
        JButton btn = new JButton("×");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(UITheme.DANGER);
        btn.setBackground(new Color(254, 226, 226));
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(26, 26));
        btn.setMaximumSize(new Dimension(26, 26));
        btn.setMinimumSize(new Dimension(26, 26));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Rimuovi dai preferiti");
        btn.addActionListener(e -> rimuovi(r.getNome()));
        return btn;
    }

    private void aggiungiManualeFlusso() {
        if (!ClientTK.isLoggato() || !ClientTK.getUtenteLoggato().isCliente()) {
            JOptionPane.showMessageDialog(this,
                    "I preferiti sono riservati agli account cliente. Accedi come cliente per usare questa funzione.",
                    "Sezione riservata", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(parent, "Aggiungi un ristorante ai preferiti", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(420, 340);
        dialog.setLocationRelativeTo(this);

        JPanel mainBody = new JPanel(new BorderLayout(10, 10));
        mainBody.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainBody.setBackground(Color.WHITE);

        JPanel step1Panel = new JPanel(new BorderLayout(5, 5));
        step1Panel.setOpaque(false);
        JLabel lblCitta = new JLabel("1. Digita la città del ristorante:");
        lblCitta.setFont(UITheme.FONT_BODY);
        JTextField txtCitta = new JTextField();
        JButton btnCerca = new JButton("Cerca Ristoranti");

        step1Panel.add(lblCitta, BorderLayout.NORTH);
        step1Panel.add(txtCitta, BorderLayout.CENTER);
        step1Panel.add(btnCerca, BorderLayout.EAST);
        mainBody.add(step1Panel, BorderLayout.NORTH);

        JPanel step2Panel = new JPanel(new BorderLayout(5, 5));
        step2Panel.setOpaque(false);
        JLabel lblList = new JLabel("2. Seleziona il locale dalla lista:");
        lblList.setFont(UITheme.FONT_BODY);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> ristoList = new JList<>(listModel);
        ristoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ristoList.setFont(UITheme.FONT_BODY);
        JScrollPane ristoScroll = new JScrollPane(ristoList);

        step2Panel.add(lblList, BorderLayout.NORTH);
        step2Panel.add(ristoScroll, BorderLayout.CENTER);
        mainBody.add(step2Panel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        footer.setBackground(UITheme.BG);
        JButton btnAnnulla = new JButton("Annulla");
        JButton btnConferma = new JButton("Aggiungi");
        btnConferma.setEnabled(false);
        footer.add(btnAnnulla);
        footer.add(btnConferma);

        btnCerca.addActionListener(e -> {
            String cittaInput = txtCitta.getText().trim();
            if (cittaInput.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Inserisci una città valida.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            listModel.clear();
            listModel.addElement("Ricerca in corso...");

            new SwingWorker<List<Ristorante>, Void>() {
                @Override
                protected List<Ristorante> doInBackground() throws Exception {
                    Request req = new Request(CommandType.CERCA_RISTORANTI, ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("citta", cittaInput);
                    Response res = ClientTK.getConnessione().invia(req);
                    return res.isSuccesso() ? res.getDatoTipizzato() : List.of();
                }

                @Override
                protected void done() {
                    try {
                        List<Ristorante> trovati = get();
                        listModel.clear();
                        if (trovati == null || trovati.isEmpty()) {
                            listModel.addElement("Nessun ristorante trovato in questa città.");
                            btnConferma.setEnabled(false);
                        } else {
                            for (Ristorante r : trovati) {
                                listModel.addElement(r.getNome());
                            }
                        }
                    } catch (Exception ex) {
                        listModel.clear();
                        listModel.addElement("Errore di connessione.");
                    }
                }
            }.execute();
        });

        ristoList.addListSelectionListener(e -> {
            String sel = ristoList.getSelectedValue();
            btnConferma.setEnabled(sel != null && !sel.equals("Ricerca in corso...") &&
                    !sel.equals("Nessun ristorante trovato in questa città.") &&
                    !sel.equals("Errore di connessione."));
        });

        btnConferma.addActionListener(e -> {
            String ristoranteSelezionato = ristoList.getSelectedValue();
            if (ristoranteSelezionato != null) {
                try {
                    Response r = ClientTK.getConnessione().invia(
                            new Request(CommandType.CLIENTE_AGGIUNGI_PREFERITO, ClientTK.getUtenteLoggato().getUsername())
                                    .aggiungiParametro("nomeRistorante", ristoranteSelezionato));
                    if (r.isSuccesso()) {
                        dialog.dispose();
                        refreshData();
                    } else {
                        JOptionPane.showMessageDialog(dialog, (r.getMessaggio()!=null&&!r.getMessaggio().isBlank())?r.getMessaggio():(r.isSuccesso()?"Operazione completata.":"Operazione fallita."), "Attenzione", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAnnulla.addActionListener(e -> dialog.dispose());

        dialog.add(mainBody, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void rimuovi(String nome) {
        try {
            Response r = ClientTK.getConnessione().invia(
                    new Request(CommandType.CLIENTE_RIMUOVI_PREFERITO,
                            ClientTK.getUtenteLoggato().getUsername())
                            .aggiungiParametro("nomeRistorante", nome));
            if (r.isSuccesso()) refreshData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Impossibile rimuovere: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aggiungiLabel(String txt) {
        gridPanel.removeAll();
        gridPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l = new JLabel(txt);
        l.setFont(UITheme.FONT_BODY);
        l.setForeground(UITheme.TEXT_MUTED);
        l.setBorder(new EmptyBorder(25, 10, 0, 0));
        gridPanel.add(l);
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}