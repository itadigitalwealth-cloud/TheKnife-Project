/**
 * TheKnife – Modulo Client
 * Finestra di registrazione.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client.gui;

import it.uninsubria.theknife.client.ClientTK;
import it.uninsubria.theknife.common.Response;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra modale di registrazione di un nuovo utente.
 * <p>
 * Raccoglie nome, cognome, username, password, data di nascita (opzionale),
 * domicilio e ruolo. Invia la richiesta al server tramite
 * {@link it.uninsubria.theknife.client.ServerConnection#registrazione}.
 * La password è hashata SHA-256 lato client prima della trasmissione.
 * </p>
 */
public class RegisterDialog extends JDialog {

    /**
     * Crea il dialog in modalità modale.
     *
     * @param owner finestra genitore
     */
    public RegisterDialog(Dialog owner) {
        super(owner, "Registrazione – TheKnife", true);
        setSize(420, 370);
        setLocationRelativeTo(owner);
        initUI();
    }

    /** Costruisce i componenti e i listener. */
    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        JTextField     txtNome      = new JTextField(15);
        JTextField     txtCognome   = new JTextField(15);
        JTextField     txtUsername  = new JTextField(15);
        JPasswordField txtPassword  = new JPasswordField(15);
        JTextField     txtNascita   = new JTextField("AAAA-MM-GG", 15); // opzionale
        JTextField     txtDomicilio = new JTextField(15);
        JComboBox<String> comboRuolo = new JComboBox<>(
                new String[]{"cliente", "ristoratore"});

        int r = 0;
        gbc.gridx = 0; gbc.gridy = r;   add(new JLabel("Nome:"),          gbc);
        gbc.gridx = 1;                   add(txtNome,                       gbc); r++;

        gbc.gridx = 0; gbc.gridy = r;   add(new JLabel("Cognome:"),        gbc);
        gbc.gridx = 1;                   add(txtCognome,                    gbc); r++;

        gbc.gridx = 0; gbc.gridy = r;   add(new JLabel("Username:"),       gbc);
        gbc.gridx = 1;                   add(txtUsername,                   gbc); r++;

        gbc.gridx = 0; gbc.gridy = r;   add(new JLabel("Password:"),       gbc);
        gbc.gridx = 1;                   add(txtPassword,                   gbc); r++;

        gbc.gridx = 0; gbc.gridy = r;   add(new JLabel("Data nascita:"),   gbc);
        gbc.gridx = 1;                   add(txtNascita,                    gbc); r++;

        gbc.gridx = 0; gbc.gridy = r;   add(new JLabel("Domicilio:"),      gbc);
        gbc.gridx = 1;                   add(txtDomicilio,                  gbc); r++;

        gbc.gridx = 0; gbc.gridy = r;   add(new JLabel("Ruolo:"),          gbc);
        gbc.gridx = 1;                   add(comboRuolo,                    gbc); r++;

        JButton btnOk     = new JButton("Registrati");
        JButton btnAnnulla = new JButton("Annulla");
        JPanel  pnlBtn    = new JPanel();
        pnlBtn.add(btnOk);
        pnlBtn.add(btnAnnulla);

        gbc.gridx = 0; gbc.gridy = r;
        gbc.gridwidth = 2;
        add(pnlBtn, gbc);

        /* ---- Listener Registrazione ---- */
        btnOk.addActionListener(e -> {
            String nome      = txtNome.getText().trim();
            String cognome   = txtCognome.getText().trim();
            String username  = txtUsername.getText().trim();
            String password  = new String(txtPassword.getPassword());
            String nascita   = txtNascita.getText().trim();
            String domicilio = txtDomicilio.getText().trim();
            String ruolo     = (String) comboRuolo.getSelectedItem();

            // Validazione campi obbligatori
            if (nome.isEmpty() || cognome.isEmpty() ||
                    username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nome, cognome, username e password sono obbligatori.",
                        "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // La data di nascita è opzionale: se l'utente ha lasciato il placeholder la ignoriamo
            String dataNascita = nascita.equals("AAAA-MM-GG") ? "" : nascita;

            try {
                Response resp = ClientTK.getConnessione().registrazione(
                        nome, cognome, username, password,
                        dataNascita, domicilio, ruolo);

                if (resp.isSuccesso()) {
                    JOptionPane.showMessageDialog(this,
                            "Registrazione completata! Ora puoi fare il login.",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            resp.getMessaggio(),
                            "Errore Registrazione", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore di connessione al server: " + ex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAnnulla.addActionListener(e -> dispose());
    }
}