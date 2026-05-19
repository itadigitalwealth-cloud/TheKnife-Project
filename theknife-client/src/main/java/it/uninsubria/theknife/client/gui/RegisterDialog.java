/**
 * TheKnife – Finestra di registrazione.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.common.Response;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Dialog di registrazione con header navy affidabile,
 * form a due colonne e toggle visivo Cliente/Ristoratore.
 */
public class RegisterDialog extends JDialog {

    private final JTextField     tfNome      = UITheme.textField(13);
    private final JTextField     tfCognome   = UITheme.textField(13);
    private final JTextField     tfUsername  = UITheme.textField(13);
    private final JPasswordField tfPassword  = UITheme.passwordField(13);
    private final JTextField     tfNascita   = UITheme.textField(13);
    private final JTextField     tfDomicilio = UITheme.textField(13);

    private String ruoloSelezionato = "cliente";
    private JPanel btnCliente, btnRistoratore;

    public RegisterDialog(Dialog owner) {
        super(owner, "Registrati – TheKnife", true);
        setSize(500, 570);
        setLocationRelativeTo(owner);
        setResizable(false);
        getContentPane().setBackground(UITheme.CARD);
        build();
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.CARD);
        root.add(UITheme.dialogHeader("Crea il tuo account",
                "Unisciti a TheKnife e scopri i migliori ristoranti"), BorderLayout.NORTH);
        root.add(buildForm(),   BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);
        setContentPane(root);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.CARD);
        form.setBorder(new EmptyBorder(18,26,8,26));
        GridBagConstraints g = new GridBagConstraints();
        g.fill=GridBagConstraints.HORIZONTAL; g.weightx=0.5;

        // Nome + Cognome
        g.gridx=0; g.gridy=0; g.insets=new Insets(0,0,3,8);  form.add(UITheme.fieldLabel("NOME"),g);
        g.gridx=1; g.insets=new Insets(0,0,3,0);              form.add(UITheme.fieldLabel("COGNOME"),g);
        g.gridx=0; g.gridy=1; g.insets=new Insets(0,0,12,8); form.add(tfNome,g);
        g.gridx=1; g.insets=new Insets(0,0,12,0);             form.add(tfCognome,g);

        // Username (full width)
        g.gridx=0; g.gridy=2; g.gridwidth=2; g.insets=new Insets(0,0,3,0);
        form.add(UITheme.fieldLabel("USERNAME"),g);
        g.gridy=3; g.insets=new Insets(0,0,12,0); form.add(tfUsername,g);

        // Password (full width)
        g.gridy=4; g.insets=new Insets(0,0,3,0); form.add(UITheme.fieldLabel("PASSWORD"),g);
        g.gridy=5; g.insets=new Insets(0,0,12,0); form.add(tfPassword,g);

        // Data nascita + Domicilio
        g.gridwidth=1; g.weightx=0.5;
        g.gridx=0; g.gridy=6; g.insets=new Insets(0,0,3,8);  form.add(UITheme.fieldLabel("DATA DI NASCITA (opz.)"),g);
        g.gridx=1; g.insets=new Insets(0,0,3,0);              form.add(UITheme.fieldLabel("DOMICILIO (CITTÀ)"),g);

        // Placeholder data nascita
        tfNascita.setForeground(UITheme.TEXT_MUTED); tfNascita.setText("AAAA-MM-GG");
        tfNascita.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (tfNascita.getText().equals("AAAA-MM-GG")) {
                    tfNascita.setText(""); tfNascita.setForeground(UITheme.TEXT);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (tfNascita.getText().isBlank()) {
                    tfNascita.setText("AAAA-MM-GG"); tfNascita.setForeground(UITheme.TEXT_MUTED);
                }
            }
        });

        g.gridx=0; g.gridy=7; g.insets=new Insets(0,0,12,8); form.add(tfNascita,g);
        g.gridx=1; g.insets=new Insets(0,0,12,0);             form.add(tfDomicilio,g);

        // Toggle ruolo
        g.gridx=0; g.gridy=8; g.gridwidth=2; g.insets=new Insets(0,0,4,0);
        form.add(UITheme.fieldLabel("TIPO DI ACCOUNT"),g);
        g.gridy=9; form.add(buildRuoloToggle(),g);
        return form;
    }

    private JPanel buildRuoloToggle() {
        JPanel toggle = new JPanel(new GridLayout(1,2,8,0));
        toggle.setOpaque(false);
        btnCliente     = ruoloCard("cliente",     "Cliente",     "Cerca ristoranti\ne scrivi recensioni");
        btnRistoratore = ruoloCard("ristoratore", "Ristoratore", "Gestisci il tuo\nlocale");
        toggle.add(btnCliente); toggle.add(btnRistoratore);
        return toggle;
    }

    private JPanel ruoloCard(String ruolo, String titolo, String desc) {
        JPanel card = new JPanel(new BorderLayout(0,3)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = UITheme.rh(g);
                boolean sel = ruoloSelezionato.equals(ruolo);
                g2.setColor(sel ? UITheme.GOLD_LIGHT : UITheme.CARD);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                g2.setColor(sel ? UITheme.GOLD : UITheme.CARD_BORDER);
                g2.setStroke(new BasicStroke(sel ? 1.5f : 0.8f));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,10,10);
                g2.dispose();
            }
        };
        card.setOpaque(false); card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setBorder(new EmptyBorder(10,14,10,14)); card.setPreferredSize(new Dimension(0,58));

        JLabel t = new JLabel(titolo); t.setFont(UITheme.FONT_H3); t.setForeground(UITheme.TEXT);
        JLabel d = new JLabel("<html>"+desc.replace("\n","<br>")+"</html>");
        d.setFont(UITheme.FONT_SMALL); d.setForeground(UITheme.TEXT_MUTED);
        card.add(t, BorderLayout.NORTH); card.add(d, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                ruoloSelezionato = ruolo;
                if (btnCliente != null)     btnCliente.repaint();
                if (btnRistoratore != null) btnRistoratore.repaint();
            }
        });
        return card;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout(0,10));
        footer.setBackground(UITheme.CARD);
        footer.setBorder(new EmptyBorder(8,26,20,26));

        UITheme.TKButton btnOk = UITheme.btnPrimary("Crea account");
        btnOk.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        btnOk.addActionListener(e -> doRegistra());
        footer.add(btnOk, BorderLayout.NORTH);

        JLabel lnk = new JLabel("<html><center>Hai già un account?  <u>Accedi</u></center></html>");
        lnk.setFont(UITheme.FONT_SMALL); lnk.setForeground(UITheme.GOLD);
        lnk.setHorizontalAlignment(SwingConstants.CENTER);
        lnk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lnk.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { dispose(); }
        });
        footer.add(lnk, BorderLayout.CENTER);
        return footer;
    }

    private void doRegistra() {
        String nome      = tfNome.getText().trim();
        String cognome   = tfCognome.getText().trim();
        String username  = tfUsername.getText().trim();
        String password  = new String(tfPassword.getPassword());
        String nascita   = tfNascita.getText().trim();
        String domicilio = tfDomicilio.getText().trim();

        if (nome.isEmpty())     { UITheme.flashRed(tfNome);     return; }
        if (cognome.isEmpty())  { UITheme.flashRed(tfCognome);  return; }
        if (username.isEmpty()) { UITheme.flashRed(tfUsername); return; }
        if (password.isEmpty()) { UITheme.flashRed(tfPassword); return; }

        String dataNascita = (nascita.equals("AAAA-MM-GG") || nascita.isEmpty()) ? "" : nascita;

        try {
            Response resp = ClientTK.getConnessione().registrazione(
                    nome, cognome, username, password, dataNascita, domicilio, ruoloSelezionato);
            if (resp.isSuccesso()) {
                JOptionPane.showMessageDialog(this,
                        "Registrazione completata!\nOra puoi accedere con le tue credenziali.",
                        "Benvenuto su TheKnife", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, resp.getMessaggio(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore di connessione: "+ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}