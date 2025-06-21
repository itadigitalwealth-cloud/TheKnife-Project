/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello logindialog.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui;

import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.swing.*;
import theknife.GestoreFile;
import theknife.Utente;

/**
 * Finestra modale di login.
 *
 * <p>Permette l’autenticazione di un utente tramite:</p>
 * <ul>
 *   <li>Username</li>
 *   <li>Password (cifrata con SHA-256 al confronto)</li>
 *   <li>Ruolo (cliente o ristoratore)</li>
 * </ul>
 *
 * <p>Inoltre offre l’accesso alla registrazione
 * tramite il dialog {@link RegisterDialog}.</p>
 */
public class LoginDialog extends JDialog {

    /** Utente autenticato con successo; <code>null</code> se login fallito. */
    private Utente utenteLoggato;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Crea il dialog di login in modalità modale.
     *
     * @param owner finestra genitore (può essere <code>null</code>)
     */
    public LoginDialog(Frame owner) {
        super(owner, "Login", true);
        setSize(400, 320);
        setLocationRelativeTo(owner);
        initUI();
    }

    /* ------------------------------------------------------------------ */
    /* Inizializzazione interfaccia Swing                                 */
    /* ------------------------------------------------------------------ */

    /** Costruisce i componenti grafici e i relativi listener. */
    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill  = GridBagConstraints.HORIZONTAL;

        /* ----- Label e campi ----- */
        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        JLabel lblRole = new JLabel("Ruolo:");

        JTextField     txtUser  = new JTextField(15);
        JPasswordField txtPass  = new JPasswordField(15);
        JComboBox<String> comboRole =
                new JComboBox<>(new String[] { "cliente", "ristoratore" });

        /* ----- Pulsanti ----- */
        JButton btnLogin    = new JButton("Login");
        JButton btnCancel   = new JButton("Annulla");
        JButton btnRegister = new JButton("Registrati");

        /* ----- Posizionamento ----- */
        gbc.gridx = 0; gbc.gridy = 0; add(lblUser, gbc);
        gbc.gridx = 1;               add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(lblPass, gbc);
        gbc.gridx = 1;               add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(lblRole, gbc);
        gbc.gridx = 1;               add(comboRole, gbc);

        JPanel panButtons = new JPanel();
        panButtons.add(btnLogin);
        panButtons.add(btnCancel);
        panButtons.add(btnRegister);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3;
        add(panButtons, gbc);

        /* --------------------------------------------------------------
           LISTENER: Login
           ------------------------------------------------------------ */
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();
            String role = (String) comboRole.getSelectedItem();

            String passCifrata = cifraPassword(pass);
            Utente u = effettuaLogin(user, passCifrata, role);

            if (u != null) {
                utenteLoggato = u;
                dispose();                       // chiude la finestra
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Credenziali non valide o ruolo errato.",
                        "Errore Login",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        /* LISTENER: Annulla */
        btnCancel.addActionListener(e -> dispose());

        /* LISTENER: Registrati */
        btnRegister.addActionListener(e -> {
            RegisterDialog regDlg = new RegisterDialog(this);
            regDlg.setVisible(true);
        });
    }

    /* ------------------------------------------------------------------ */
    /* Getter                                                              */
    /* ------------------------------------------------------------------ */

    /**
     * Restituisce l’utente autenticato con successo.
     *
     * @return {@link Utente} loggato oppure <code>null</code> se login fallito
     */
    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    /* ------------------------------------------------------------------ */
    /* Logica di autenticazione                                           */
    /* ------------------------------------------------------------------ */

    /**
     * Verifica le credenziali confrontando username, password cifrata
     * e ruolo con i dati presenti nel file <code>utenti.csv</code>.
     *
     * @param username        username inserito
     * @param passwordCifrata password già cifrata in SHA-256
     * @param ruoloRichiesto  ruolo selezionato dall’utente
     * @return oggetto {@link Utente} se le credenziali sono corrette;
     *         <code>null</code> altrimenti
     */
    private Utente effettuaLogin(String username,
                                 String passwordCifrata,
                                 String ruoloRichiesto) {
        List<Utente> utenti = GestoreFile.caricaUtenti("data/utenti.csv");
        return utenti.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username)
                          && u.getPasswordCifrata().equals(passwordCifrata)
                          && u.getRuolo().equalsIgnoreCase(ruoloRichiesto))
                .findFirst()
                .orElse(null);
    }

    /* ------------------------------------------------------------------ */
    /* Utility: cifratura password                                         */
    /* ------------------------------------------------------------------ */

    /**
     * Cifra la password in SHA-256 restituendo la stringa esadecimale.
     *
     * @param password password in chiaro
     * @return digest SHA-256 in formato esadecimale
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
            /* Caso improbabile: in fallback restituiamo la password originale */
            return password;
        }
    }
}
