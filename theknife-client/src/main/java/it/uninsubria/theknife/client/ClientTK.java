/**
 * TheKnife – Modulo Client
 * Classe principale del client TheKnife.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client;
import it.uninsubria.theknife.client.gui.FancyFrame;
import it.uninsubria.theknife.common.model.Utente;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Punto di ingresso del modulo client di TheKnife.
 * <p>
 * All'avvio mostra una finestra di dialogo per inserire host e porta
 * del server, apre la {@link ServerConnection} e poi lancia la GUI
 * principale ({@link FancyFrame}).
 * </p>
 *
 * <p>
 * Funge anche da <b>contenitore della sessione</b>: mantiene il riferimento
 * all'utente attualmente loggato ({@code null} per guest) e alla connessione
 * attiva. I pannelli GUI accedono a questi dati tramite i metodi statici
 * {@link #getConnessione()} e {@link #getUtenteLoggato()}.
 * </p>
 *
 * <p>Avvio:</p>
 * <pre>
 *   java -jar bin/clientTK.jar
 * </pre>
 */
public class ClientTK {

    /** Porta di default del server TheKnife. */
    private static final int PORTA_DEFAULT = 9090;

    /** Connessione attiva verso il server. Condivisa da tutti i pannelli. */
    private static ServerConnection connessione;

    /** Utente attualmente loggato. {@code null} se guest. */
    private static Utente utenteLoggato = null;

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    /**
     * Metodo principale del client.
     * Imposta il look-and-feel di sistema, chiede host/porta del server,
     * apre la connessione e avvia la GUI sull'Event Dispatch Thread.
     *
     * @param args argomenti da riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        // Imposta il look-and-feel nativo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Se non disponibile, si usa il look-and-feel di default di Swing
        }

        // Tutti i componenti Swing vanno creati sull'EDT
        SwingUtilities.invokeLater(ClientTK::avvia);
    }

    /**
     * Mostra il dialogo di connessione, apre il socket e lancia la GUI.
     * Eseguito sull'Event Dispatch Thread.
     */
    private static void avvia() {
        // --- Dialogo di connessione al server ---
        String[] dati = chiediParametriConnessione();
        if (dati == null) {
            // L'utente ha annullato: chiusura pulita
            System.exit(0);
        }

        String host  = dati[0];
        int    porta;
        try {
            porta = Integer.parseInt(dati[1]);
        } catch (NumberFormatException e) {
            porta = PORTA_DEFAULT;
        }

        // --- Apertura connessione ---
        try {
            connessione = new ServerConnection(host, porta);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null,
                "Impossibile connettersi al server.\n"
                    + "Host: " + host + "  Porta: " + porta + "\n\n"
                    + "Dettaglio: " + e.getMessage(),
                "Errore di connessione",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }

        // --- Avvio finestra principale ---
        FancyFrame frame = new FancyFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Alla chiusura della finestra: chiude la connessione prima di uscire
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                chiudi();
                frame.dispose();
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    // -------------------------------------------------------------------------
    // Gestione sessione – accesso statico per i pannelli GUI
    // -------------------------------------------------------------------------

    /**
     * @return la connessione attiva verso il server
     */
    public static ServerConnection getConnessione() {
        return connessione;
    }

    /**
     * @return l'utente attualmente loggato, oppure {@code null} se guest
     */
    public static Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    /**
     * Imposta l'utente loggato dopo un login andato a buon fine.
     * Chiamato da {@link LoginDialog} al ricevimento della risposta positiva.
     *
     * @param utente utente autenticato
     */
    public static void setUtenteLoggato(Utente utente) {
        utenteLoggato = utente;
    }

    /**
     * Effettua il logout: azzera l'utente loggato.
     * La connessione socket rimane aperta (il server la considera guest
     * fino al prossimo login).
     */
    public static void logout() {
        utenteLoggato = null;
    }

    /**
     * Indica se c'è un utente autenticato.
     *
     * @return {@code true} se l'utente ha effettuato il login
     */
    public static boolean isLoggato() {
        return utenteLoggato != null;
    }

    // -------------------------------------------------------------------------
    // Chiusura
    // -------------------------------------------------------------------------

    /**
     * Chiude la connessione verso il server in modo pulito.
     */
    private static void chiudi() {
        if (connessione != null) {
            connessione.close();
        }
    }

    // -------------------------------------------------------------------------
    // Dialogo di connessione
    // -------------------------------------------------------------------------

    /**
     * Mostra una finestra di dialogo per raccogliere host e porta del server.
     *
     * @return array {@code [host, porta]} oppure {@code null} se annullato
     */
    private static String[] chiediParametriConnessione() {
        JTextField campoHost  = new JTextField("localhost", 15);
        JTextField campoPorta = new JTextField(String.valueOf(PORTA_DEFAULT), 6);

        JPanel pannello = new JPanel(new GridLayout(2, 2, 8, 8));
        pannello.add(new JLabel("Host server:"));
        pannello.add(campoHost);
        pannello.add(new JLabel("Porta:"));
        pannello.add(campoPorta);

        int scelta = JOptionPane.showConfirmDialog(
            null,
            pannello,
            "Connessione a TheKnife Server",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (scelta != JOptionPane.OK_OPTION) {
            return null;
        }

        return new String[] {
            campoHost.getText().trim(),
            campoPorta.getText().trim()
        };
    }
}