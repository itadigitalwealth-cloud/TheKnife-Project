/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello registerdialog.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui;

import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import theknife.GestoreFile;
import theknife.Utente;

/**
 * Finestra di dialogo modale per la registrazione di un nuovo utente.
 * <p>Permette l’inserimento di nome, cognome, username, password
 * e ruolo (cliente o ristoratore). I dati vengono validati,
 * la password cifrata con SHA-256 e infine salvati nel file
 * <code>data/utenti.csv</code> tramite {@link GestoreFile}.</p>
 */
public class RegisterDialog extends JDialog {

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Crea e visualizza la finestra di registrazione in modalità modale.
     *
     * @param owner finestra genitore (serve per la modalità modale)
     */
    public RegisterDialog(Dialog owner) {
        super(owner, "Registrazione Nuovo Utente", true);
        setSize(400, 320);
        setLocationRelativeTo(owner);
        initUI();
    }

    /* ------------------------------------------------------------------ */
    /* Interfaccia grafica                                                */
    /* ------------------------------------------------------------------ */

    /**
     * Inizializza tutti i componenti Swing e imposta i listener.
     */
    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /* ---------- Label e campi di input ---------- */
        JLabel lblNome    = new JLabel("Nome:");
        JLabel lblCognome = new JLabel("Cognome:");
        JLabel lblUser    = new JLabel("Username:");
        JLabel lblPass    = new JLabel("Password:");
        JLabel lblRuolo   = new JLabel("Ruolo:");

        JTextField      txtNome    = new JTextField(15);
        JTextField      txtCognome = new JTextField(15);
        JTextField      txtUser    = new JTextField(15);
        JPasswordField  txtPass    = new JPasswordField(15);

        JComboBox<String> comboRuolo =
                new JComboBox<>(new String[] { "cliente", "ristoratore" });

        /* ---------- Pulsanti ---------- */
        JButton btnOk     = new JButton("OK");
        JButton btnCancel = new JButton("Annulla");

        /* ---------- Layout ---------- */
        gbc.gridx = 0; gbc.gridy = 0; add(lblNome,    gbc);
        gbc.gridx = 1;               add(txtNome,     gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(lblCognome, gbc);
        gbc.gridx = 1;               add(txtCognome,  gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(lblUser,    gbc);
        gbc.gridx = 1;               add(txtUser,     gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(lblPass,    gbc);
        gbc.gridx = 1;               add(txtPass,     gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(lblRuolo,   gbc);
        gbc.gridx = 1;               add(comboRuolo,  gbc);

        JPanel panButtons = new JPanel();
        panButtons.add(btnOk);
        panButtons.add(btnCancel);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 5;
        add(panButtons, gbc);

        /* ------------------------------------------------------------------
           LISTENER: Conferma registrazione
           ---------------------------------------------------------------- */
        btnOk.addActionListener(e -> {
            String nome     = txtNome.getText().trim();
            String cognome  = txtCognome.getText().trim();
            String username = txtUser.getText().trim();
            String password = new String(txtPass.getPassword()).trim();
            String ruolo    = (String) comboRuolo.getSelectedItem();

            // Validazione campi obbligatori
            if (nome.isEmpty() || cognome.isEmpty() ||
                username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tutti i campi obbligatori devono essere compilati.",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica unicità username
            if (GestoreFile.usernameEsistente(username, "data/utenti.csv")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Username già esistente! Scegli un altro username.",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            /* Cifratura password con SHA-256 */
            String passwordCifrata = cifraPassword(password);

            // Creazione e salvataggio dell'utente
            Utente nuovo = new Utente(
                    nome, cognome, username,
                    passwordCifrata,
                    "",         // data di nascita non fornita
                    "",         // domicilio non fornito
                    ruolo);

            GestoreFile.aggiungiUtente(nuovo, "data/utenti.csv");

            JOptionPane.showMessageDialog(
                    this,
                    "Utente registrato con successo!",
                    "Registrazione OK",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        /* ------------------------------------------------------------------
           LISTENER: Annulla
           ---------------------------------------------------------------- */
        btnCancel.addActionListener(e -> dispose());
    }

    /* ------------------------------------------------------------------ */
    /* Utility: cifratura password                                         */
    /* ------------------------------------------------------------------ */

    /**
     * Cifra una password in SHA-256 restituendo la stringa esadecimale.
     *
     * @param password password in chiaro
     * @return password cifrata in esadecimale; in caso di errore
     *         viene restituita la password originale
     */
    private String cifraPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password; // fallback (non dovrebbe mai accadere)
        }
    }
}
