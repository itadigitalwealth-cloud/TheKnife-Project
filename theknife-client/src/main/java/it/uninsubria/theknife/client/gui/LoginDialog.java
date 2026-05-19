/**
 * TheKnife – Dialog di login.
 *
 * @author Matteo Vigano      – 760537 – sede CO
 * @author Fabio Vecaj        – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Utente;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Dialog di login stilizzato con header navy affidabile ({@link UITheme#dialogHeader}).
 */
public class LoginDialog extends JDialog {

    private Utente utenteLoggato;

    public LoginDialog(Frame owner) {
        super(owner, "Accedi – TheKnife", true);
        setSize(400, 410);
        setLocationRelativeTo(owner);
        setResizable(false);
        getContentPane().setBackground(UITheme.CARD);
        build();
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.CARD);

        // Header – usa dialogHeader() che è affidabile su tutti i L&F
        root.add(UITheme.dialogHeader("Bentornato su TheKnife",
                "Accedi al tuo account per continuare"), BorderLayout.NORTH);

        // Form
        JTextField     tfUser = UITheme.textField(20);
        JPasswordField tfPass = UITheme.passwordField(20);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.CARD);
        form.setBorder(new EmptyBorder(22,28,14,28));
        GridBagConstraints g = new GridBagConstraints();
        g.fill=GridBagConstraints.HORIZONTAL; g.weightx=1; g.gridx=0;

        g.gridy=0; g.insets=new Insets(0,0,3,0);  form.add(UITheme.fieldLabel("USERNAME"),g);
        g.gridy=1; g.insets=new Insets(0,0,14,0); form.add(tfUser,g);
        g.gridy=2; g.insets=new Insets(0,0,3,0);  form.add(UITheme.fieldLabel("PASSWORD"),g);
        g.gridy=3; g.insets=new Insets(0,0,18,0); form.add(tfPass,g);

        // Pulsanti
        JPanel btnRow = new JPanel(new GridLayout(1,2,10,0)); btnRow.setOpaque(false);
        UITheme.TKButton btnAnn    = UITheme.btnGhost("Annulla");
        UITheme.TKButton btnAccedi = UITheme.btnPrimary("Accedi");
        btnRow.add(btnAnn); btnRow.add(btnAccedi);
        g.gridy=4; g.insets=new Insets(0,0,12,0); form.add(btnRow,g);

        // Link registrazione
        JLabel lnkReg = new JLabel(
                "<html><center>Non hai un account?  <u>Registrati</u></center></html>");
        lnkReg.setFont(UITheme.FONT_SMALL); lnkReg.setForeground(UITheme.GOLD);
        lnkReg.setHorizontalAlignment(SwingConstants.CENTER);
        lnkReg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.gridy=5; g.insets=new Insets(0,0,0,0); form.add(lnkReg,g);
        root.add(form, BorderLayout.CENTER);
        setContentPane(root);

        // Listeners
        btnAccedi.addActionListener(e -> doLogin(tfUser, tfPass));
        tfPass.addActionListener(e    -> doLogin(tfUser, tfPass));
        btnAnn.addActionListener(e    -> dispose());
        lnkReg.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new RegisterDialog(LoginDialog.this).setVisible(true);
            }
        });
    }

    private void doLogin(JTextField tfUser, JPasswordField tfPass) {
        String user = tfUser.getText().trim();
        String pass = new String(tfPass.getPassword());
        if (user.isEmpty()) { UITheme.flashRed(tfUser); return; }
        if (pass.isEmpty()) { UITheme.flashRed(tfPass); return; }
        try {
            Response resp = ClientTK.getConnessione().login(user, pass);
            if (resp.isSuccesso()) {
                utenteLoggato = resp.getDatoTipizzato();
                ClientTK.setUtenteLoggato(utenteLoggato);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, resp.getMessaggio(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore di connessione: "+ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Utente getUtenteLoggato() { return utenteLoggato; }
}