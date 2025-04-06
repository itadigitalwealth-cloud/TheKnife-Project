package theknife.gui;

import theknife.Utente;
import theknife.GestoreFile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Finestra di login:
 * - Username, Password
 * - Ruolo (cliente/ristoratore)
 */
public class LoginDialog extends JDialog {
    private Utente utenteLoggato;

    public LoginDialog(Frame owner) {
        super(owner, "Login", true);
        setSize(400, 320);
        setLocationRelativeTo(owner);
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        JLabel lblRole = new JLabel("Ruolo:");
        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);
        JComboBox<String> comboRole = new JComboBox<>(new String[] { "cliente", "ristoratore" });

        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");
        JButton btnRegister = new JButton("Registrati");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblUser, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(txtUser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblPass, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtPass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblRole, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(comboRole, gbc);

        JPanel panButtons = new JPanel();
        panButtons.add(btnLogin);
        panButtons.add(btnCancel);
        panButtons.add(btnRegister);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(panButtons, gbc);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();
            String role = (String) comboRole.getSelectedItem();
            Utente u = effettuaLogin(user, pass, role);
            if (u != null) {
                utenteLoggato = u;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenziali non valide o ruolo errato",
                        "Errore Login",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dispose());

        btnRegister.addActionListener(e -> {
            RegisterDialog regDlg = new RegisterDialog(this);
            regDlg.setVisible(true);
        });
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    private Utente effettuaLogin(String username, String password, String ruoloRichiesto) {
        List<Utente> utenti = GestoreFile.caricaUtenti("data/utenti.csv");
        for (Utente u : utenti) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPasswordCifrata().equals(password)
                    && u.getRuolo().equalsIgnoreCase(ruoloRichiesto)) {
                return u;
            }
        }
        return null; // se non trovato
    }
}
