/**
 * TheKnife – Pannello miei locali.
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
 * Pannello ristoratore – griglia locali con form di inserimento su dialog
 * stilizzato con header affidabile.
 */
public class RistorantiPanel extends GradientPanel {

    private final FancyFrame parent;
    private final JPanel gridPanel = new JPanel(new WrapLayout(FlowLayout.LEFT,12,12));
    private final JLabel lblCount  = new JLabel("");

    public RistorantiPanel(FancyFrame parent) {
        super(new BorderLayout()); this.parent=parent; setBackground(UITheme.BG); initUI();
    }

    private void initUI() {
        add(buildTopBar(), BorderLayout.NORTH);
        JPanel content=new JPanel(new BorderLayout(0,10)); content.setBackground(UITheme.BG);
        content.setBorder(new EmptyBorder(6,0,20,0));
        lblCount.setFont(UITheme.FONT_SMALL); lblCount.setForeground(UITheme.TEXT_MUTED);
        content.add(lblCount, BorderLayout.NORTH);
        gridPanel.setBackground(UITheme.BG); content.add(gridPanel, BorderLayout.CENTER);
        JScrollPane scroll=new JScrollPane(content); scroll.setBorder(null);
        scroll.setBackground(UITheme.BG); scroll.getViewport().setBackground(UITheme.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER); add(buildBottomBar(), BorderLayout.SOUTH);
    }

    private JPanel buildTopBar() {
        JPanel bar=new JPanel(new BorderLayout()); bar.setBackground(Color.WHITE);
        bar.setPreferredSize(new Dimension(0,58));
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,1,0,UITheme.CARD_BORDER),
                new EmptyBorder(0,18,0,18)));
        JPanel left=new JPanel(new BorderLayout(0,3)); left.setOpaque(false);
        JLabel title=new JLabel("I miei locali"); title.setFont(UITheme.FONT_H1); title.setForeground(UITheme.TEXT);
        left.add(title, BorderLayout.NORTH); left.add(lblCount, BorderLayout.SOUTH);
        bar.add(left, BorderLayout.WEST); return bar;
    }

    private JPanel buildBottomBar() {
        JPanel bar=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10)); bar.setBackground(Color.WHITE);
        bar.setBorder(BorderFactory.createMatteBorder(1,0,0,0,UITheme.CARD_BORDER));
        UITheme.TKButton btn=UITheme.btnPrimary("+ Aggiungi ristorante");
        btn.addActionListener(e->apriDialogNuovoRistorante()); bar.add(btn); return bar;
    }

    public void refreshData() {
        gridPanel.removeAll();
        if (!ClientTK.isLoggato()||!ClientTK.getUtenteLoggato().isRistoratore()) {
            addLabel("Accedi come ristoratore per gestire i tuoi locali."); return;
        }
        addLabel("Caricamento...");
        new SwingWorker<List<Ristorante>,Void>() {
            @Override protected List<Ristorante> doInBackground() throws Exception {
                Response r=ClientTK.getConnessione().invia(
                        new Request(CommandType.RISTORATORE_VISUALIZZA_RIEPILOGO, ClientTK.getUtenteLoggato().getUsername()));
                return r.isSuccesso() ? r.getDatoTipizzato() : List.of();
            }
            @Override protected void done() {
                gridPanel.removeAll();
                try {
                    List<Ristorante> lista=get();
                    if (lista.isEmpty()) { lblCount.setText(""); addLabel("Non hai ancora aggiunto locali."); }
                    else { lblCount.setText(lista.size()+" "+(lista.size()==1?"locale":"locali")); lista.forEach(RistorantiPanel.this::addCard); }
                } catch (Exception ex) { addLabel("Errore: "+ex.getMessage()); }
                gridPanel.revalidate(); gridPanel.repaint();
            }
        }.execute();
    }

    private void addCard(Ristorante r) {
        UITheme.CardPanel card=UITheme.cardPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(240,140)); card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel inner=new JPanel(); inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));
        inner.setBackground(UITheme.CARD); inner.setBorder(new EmptyBorder(13,15,13,15));
        JPanel br=new JPanel(new FlowLayout(FlowLayout.LEFT,4,0)); br.setOpaque(false); br.setAlignmentX(Component.LEFT_ALIGNMENT);
        br.add(UITheme.badgeCucina(r.getTipoCucina()));
        JLabel nome=new JLabel(r.getNome()); nome.setFont(UITheme.FONT_H2); nome.setForeground(UITheme.TEXT);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT); nome.setBorder(new EmptyBorder(7,0,2,0));
        JLabel loc=new JLabel(r.getCitta()+"  ·  "+String.format("%.0f€",r.getFasciaPrezzo()));
        loc.setFont(UITheme.FONT_SMALL); loc.setForeground(UITheme.TEXT_MUTED); loc.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel stats=new JPanel(new FlowLayout(FlowLayout.LEFT,8,0)); stats.setOpaque(false); stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        stats.add(UITheme.starLabel(r.getMediaStelle(), r.getNumeroRecensioni()));
        if (r.isDelivery())     stats.add(UITheme.pillDelivery());
        if (r.isPrenotazione()) stats.add(UITheme.pillPrenotazione());
        inner.add(br); inner.add(nome); inner.add(loc); inner.add(Box.createVerticalStrut(6)); inner.add(stats);
        card.add(inner, BorderLayout.CENTER);
        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { parent.getDetailPanel().setRistorante(r); parent.showCard(FancyFrame.CARD_DETAIL); }
            @Override public void mouseEntered(MouseEvent e) { card.setHovered(true); inner.setBackground(UITheme.CARD_HOV_BG); }
            @Override public void mouseExited (MouseEvent e) { card.setHovered(false); inner.setBackground(UITheme.CARD); }
        });
        gridPanel.add(card);
    }

    private void apriDialogNuovoRistorante() {
        if (!ClientTK.isLoggato()||!ClientTK.getUtenteLoggato().isRistoratore()) {
            JOptionPane.showMessageDialog(this,"Devi essere loggato come ristoratore.","Accesso negato",JOptionPane.WARNING_MESSAGE); return;
        }
        NuovoRistoranteDialog dlg=new NuovoRistoranteDialog(SwingUtilities.getWindowAncestor(this));
        dlg.setVisible(true);
        if (!dlg.isConfermato()) return;
        try {
            Response r=ClientTK.getConnessione().invia(
                    new Request(CommandType.RISTORATORE_AGGIUNGI_RISTORANTE, ClientTK.getUtenteLoggato().getUsername())
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
            JOptionPane.showMessageDialog(this, r.getMessaggio(),
                    r.isSuccesso()?"OK":"Errore",
                    r.isSuccesso()?JOptionPane.INFORMATION_MESSAGE:JOptionPane.ERROR_MESSAGE);
            if (r.isSuccesso()) refreshData();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this,"Errore: "+ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE); }
    }

    private void addLabel(String txt) {
        gridPanel.removeAll();
        JLabel l=new JLabel(txt); l.setFont(UITheme.FONT_BODY); l.setForeground(UITheme.TEXT_MUTED);
        l.setBorder(new EmptyBorder(20,8,0,0)); gridPanel.add(l);
        gridPanel.revalidate(); gridPanel.repaint();
    }

    // =========================================================================
    // DIALOG NUOVO RISTORANTE – header affidabile
    // =========================================================================

    public static class NuovoRistoranteDialog extends JDialog {
        private boolean conf=false;
        private final JTextField tfNome=UITheme.textField(18), tfNaz=UITheme.textField(12);
        private final JTextField tfCitta=UITheme.textField(12), tfInd=UITheme.textField(24);
        private final JTextField tfLat=UITheme.textField(10),   tfLon=UITheme.textField(10);
        private final JTextField tfPrz=UITheme.textField(8),    tfCuc=UITheme.textField(14);
        private final JCheckBox  chkDel=new JCheckBox("Delivery");
        private final JCheckBox  chkPren=new JCheckBox("Prenotazione online");

        public NuovoRistoranteDialog(Window owner) {
            super(owner,"Aggiungi ristorante",ModalityType.APPLICATION_MODAL);
            setSize(520,550); setLocationRelativeTo(owner); setResizable(false);
            getContentPane().setBackground(UITheme.CARD);
            tfNaz.setText("Italia"); tfLat.setText("0.0"); tfLon.setText("0.0"); tfPrz.setText("30");
            for (JCheckBox c : new JCheckBox[]{chkDel,chkPren}) {
                c.setBackground(UITheme.CARD); c.setFont(UITheme.FONT_BODY);
                c.setForeground(UITheme.TEXT); c.setFocusPainted(false);
            }
            build();
        }

        private void build() {
            JPanel root=new JPanel(new BorderLayout()); root.setBackground(UITheme.CARD);
            // FIX: header affidabile
            root.add(UITheme.dialogHeader("Aggiungi un nuovo ristorante",
                    "I dati saranno visibili a tutti gli utenti della piattaforma"), BorderLayout.NORTH);

            JPanel form=new JPanel(new GridBagLayout()); form.setBackground(UITheme.CARD);
            form.setBorder(new EmptyBorder(16,22,10,22));
            GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(3,4,3,4); g.fill=GridBagConstraints.HORIZONTAL;

            addRow(form,g,0,0,"NOME RISTORANTE",tfNome,4);
            addRow(form,g,1,0,"NAZIONE",tfNaz,2); addRow(form,g,1,2,"CITTÀ",tfCitta,2);
            addRow(form,g,2,0,"INDIRIZZO",tfInd,4);
            addRow(form,g,3,0,"LATITUDINE",tfLat,2); addRow(form,g,3,2,"LONGITUDINE",tfLon,2);
            addRow(form,g,4,0,"PREZZO MEDIO (€)",tfPrz,2); addRow(form,g,4,2,"TIPO CUCINA",tfCuc,2);

            g.gridy=10; g.gridx=0; g.gridwidth=2; g.insets=new Insets(10,4,3,4); form.add(chkDel,g);
            g.gridx=2; form.add(chkPren,g);
            root.add(form,BorderLayout.CENTER);

            JPanel footer=new JPanel(new GridLayout(1,2,10,0)); footer.setBackground(UITheme.CARD);
            footer.setBorder(new EmptyBorder(0,22,18,22));
            UITheme.TKButton ba=UITheme.btnGhost("Annulla"), bi=UITheme.btnPrimary("Salva ristorante");
            ba.addActionListener(e->dispose());
            bi.addActionListener(e->{
                if(tfNome.getText().trim().isEmpty()||tfCitta.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Nome e città sono obbligatori.","Attenzione",JOptionPane.WARNING_MESSAGE); return;}
                try{Double.parseDouble(tfLat.getText().trim()); Double.parseDouble(tfLon.getText().trim()); Double.parseDouble(tfPrz.getText().trim());}
                catch(NumberFormatException ex){ JOptionPane.showMessageDialog(this,"Lat, lon e prezzo devono essere numeri.","Errore",JOptionPane.ERROR_MESSAGE); return;}
                conf=true; dispose();
            });
            footer.add(ba); footer.add(bi); root.add(footer,BorderLayout.SOUTH);
            setContentPane(root);
        }

        private void addRow(JPanel f, GridBagConstraints g, int row, int col, String lbl, JTextField tf, int span) {
            g.gridx=col; g.gridy=row*2; g.gridwidth=span; g.weightx=1; g.insets=new Insets(6,4,0,4);
            JLabel l=new JLabel(lbl); l.setFont(UITheme.FONT_LABEL); l.setForeground(UITheme.TEXT_MUTED); f.add(l,g);
            g.gridy=row*2+1; g.insets=new Insets(2,4,4,4); f.add(tf,g);
        }

        public boolean isConfermato(){return conf;}
        public String  getNome()    {return tfNome.getText().trim();}
        public String  getNazione() {return tfNaz.getText().trim();}
        public String  getCitta()   {return tfCitta.getText().trim();}
        public String  getIndirizzo(){return tfInd.getText().trim();}
        public double  getLat()     {return Double.parseDouble(tfLat.getText().trim());}
        public double  getLon()     {return Double.parseDouble(tfLon.getText().trim());}
        public double  getPrezzo()  {return Double.parseDouble(tfPrz.getText().trim());}
        public String  getCucina()  {return tfCuc.getText().trim();}
        public boolean isDel()      {return chkDel.isSelected();}
        public boolean isPren()     {return chkPren.isSelected();}
    }

    private static class WrapLayout extends FlowLayout {
        public WrapLayout(int a,int h,int v){super(a,h,v);}
        @Override public Dimension preferredLayoutSize(Container t){return ls(t,true);}
        @Override public Dimension minimumLayoutSize(Container t){return ls(t,false);}
        private Dimension ls(Container t,boolean p){synchronized(t.getTreeLock()){
            int tw=t.getSize().width;if(tw==0)tw=Integer.MAX_VALUE;
            Insets ins=t.getInsets();int mw=tw-ins.left-ins.right-getHgap()*2;
            int rw=0,rh=0,th=ins.top+ins.bottom+getVgap()*2;
            for(int i=0;i<t.getComponentCount();i++){Component m=t.getComponent(i);if(!m.isVisible())continue;
                Dimension d=p?m.getPreferredSize():m.getMinimumSize();
                if(rw+d.width>mw&&rw>0){th+=rh+getVgap();rw=0;rh=0;}
                rw+=d.width+getHgap();rh=Math.max(rh,d.height);}
            return new Dimension(tw,th+rh);}}
    }
}