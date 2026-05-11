/**
 * TheKnife – Modulo Client
 * Finestra di login – stile Luxury Editorial.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client.gui;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.common.Response;
import it.uninsubria.theknife.common.model.Utente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dialog di autenticazione con layout centrato ed elegante.
 */
public class LoginDialog extends JDialog {

    private Utente utenteLoggato;

    public LoginDialog(Frame owner) {
        super(owner, "Accedi a TheKnife", true);
        setSize(440, 420);
        setLocationRelativeTo(owner);
        setResizable(false);
        getContentPane().setBackground(UITheme.CARD);
        initUI();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.CARD);
        root.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Header colorato
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(UITheme.SIDEBAR_BG);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(UITheme.GOLD);
                g.fillRect(0, getHeight()-3, getWidth(), 3);
            }
        };
        header.setPreferredSize(new Dimension(0, 90));
        header.setBorder(new EmptyBorder(0, 32, 3, 32));
        header.setOpaque(false);

        JLabel title = new JLabel("Bentornato");
        title.setFont(UITheme.FONT_DISPLAY);
        title.setForeground(Color.WHITE);
        title.setVerticalAlignment(SwingConstants.CENTER);
        header.add(title, BorderLayout.CENTER);

        JLabel sub = new JLabel("Accedi al tuo account TheKnife");
        sub.setFont(UITheme.FONT_SMALL);
        sub.setForeground(UITheme.SIDEBAR_MUTED);
        header.add(sub, BorderLayout.SOUTH);
        root.add(header, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.CARD);
        form.setBorder(new EmptyBorder(28, 36, 20, 36));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        g.insets = new Insets(0, 0, 14, 0);

        JTextField tfUser  = UITheme.textField(20);
        JPasswordField tfPass = UITheme.passwordField(20);

        g.gridx=0; g.gridy=0;
        form.add(fieldLabel("USERNAME"), g);
        g.gridy=1;
        form.add(tfUser, g);
        g.gridy=2; g.insets=new Insets(10, 0, 14, 0);
        form.add(fieldLabel("PASSWORD"), g);
        g.gridy=3; g.insets=new Insets(0, 0, 20, 0);
        form.add(tfPass, g);

        // Pulsanti
        JPanel btnRow = new JPanel(new GridLayout(1, 2, 10, 0));
        btnRow.setOpaque(false);
        UITheme.StyledButton btnAccedi = UITheme.btnPrimary("Accedi");
        UITheme.StyledButton btnAnnulla = UITheme.btnGhost("Annulla");
        btnRow.add(btnAccedi);
        btnRow.add(btnAnnulla);
        g.gridy=4; g.insets=new Insets(0,0,0,0);
        form.add(btnRow, g);

        // Link registrazione
        JLabel lnkReg = new JLabel("<html><u>Non hai un account? Registrati</u></html>");
        lnkReg.setFont(UITheme.FONT_SMALL);
        lnkReg.setForeground(UITheme.GOLD);
        lnkReg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lnkReg.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy=5; g.insets=new Insets(10,0,0,0);
        form.add(lnkReg, g);

        root.add(form, BorderLayout.CENTER);
        setContentPane(root);

        // Listeners
        btnAccedi.addActionListener(e -> doLogin(tfUser, tfPass));
        tfPass.addActionListener(e   -> doLogin(tfUser, tfPass));
        btnAnnulla.addActionListener(e -> dispose());
        lnkReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new RegisterDialog(LoginDialog.this).setVisible(true);
            }
        });
    }

    private void doLogin(JTextField tfUser, JPasswordField tfPass) {
        String user = tfUser.getText().trim();
        String pass = new String(tfPass.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            showError("Inserisci username e password."); return;
        }
        try {
            Response resp = ClientTK.getConnessione().login(user, pass);
            if (resp.isSuccesso()) {
                utenteLoggato = resp.getDatoTipizzato();
                ClientTK.setUtenteLoggato(utenteLoggato);
                dispose();
            } else {
                showError(resp.getMessaggio());
            }
        } catch (Exception ex) {
            showError("Errore di connessione: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    private JLabel fieldLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(UITheme.FONT_LABEL);
        l.setForeground(UITheme.TEXT_MUTED);
        return l;
    }

    public Utente getUtenteLoggato() { return utenteLoggato; }
}