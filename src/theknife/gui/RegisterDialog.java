package theknife.gui;

import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import theknife.GestoreFile;
import theknife.Utente;

public class RegisterDialog extends JDialog {
    public RegisterDialog(Dialog owner) {
        super(owner, "Registrazione Nuovo Utente", true);
        setSize(400, 320);
        setLocationRelativeTo(owner);
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCognome = new JLabel("Cognome:");
        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        JLabel lblRuolo = new JLabel("Ruolo:");

        JTextField txtNome = new JTextField(15);
        JTextField txtCognome = new JTextField(15);
        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);

        JComboBox<String> comboRuolo = new JComboBox<>(new String[]{"cliente", "ristoratore"});

        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Annulla");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblNome, gbc);
        gbc.gridx = 1;
        add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblCognome, gbc);
        gbc.gridx = 1;
        add(txtCognome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblUser, gbc);
        gbc.gridx = 1;
        add(txtUser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblPass, gbc);
        gbc.gridx = 1;
        add(txtPass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblRuolo, gbc);
        gbc.gridx = 1;
        add(comboRuolo, gbc);

        JPanel panButtons = new JPanel();
        panButtons.add(btnOk);
        panButtons.add(btnCancel);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(panButtons, gbc);

        btnOk.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String cognome = txtCognome.getText().trim();
            String username = txtUser.getText().trim();
            String password = new String(txtPass.getPassword()).trim();
            String ruolo = (String) comboRuolo.getSelectedItem();

            if (nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tutti i campi obbligatori devono essere compilati.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (GestoreFile.usernameEsistente(username, "data/utenti.csv")) {
                JOptionPane.showMessageDialog(this, "Username già esistente! Scegli un altro username.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cifra la password
            String passwordCifrata = cifraPassword(password);

            Utente nuovo = new Utente(nome, cognome, username, passwordCifrata, "", "", ruolo);
            GestoreFile.aggiungiUtente(nuovo, "data/utenti.csv");

            JOptionPane.showMessageDialog(this, "Utente registrato con successo!", "OK", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());
    }

    private String cifraPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }
}
