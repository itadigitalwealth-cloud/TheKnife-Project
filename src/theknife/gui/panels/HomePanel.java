/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello homepanel.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

/**
 * <h2>HomePanel</h2>
 * <p>
 * Pannello di benvenuto mostrato all’avvio dell’applicazione
 * o quando l’utente effettua il logout.<br>
 * Fornisce soltanto un messaggio d’introduzione invitando
 * l’utente a <strong>“Login”</strong> o <strong>“Registrati”</strong>.
 * </p>
 *
 * <p>
 * Graficamente eredita da {@link GradientPanel} per avere uno
 * sfondo sfumato grigio chiaro.
 * </p>
 *
 * @author TheKnife Team
 */
public class HomePanel extends GradientPanel {

    /* ------------------------------------------------------------------ */
    /* Attributi                                                          */
    /* ------------------------------------------------------------------ */

    /** Riferimento al frame principale per eventuali interazioni future. */
    private final FancyFrame parent;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Crea il pannello di home con gradiente very-light gray
     * e ne inizializza la GUI.
     *
     * @param parent frame che contiene il pannello
     */
    public HomePanel(FancyFrame parent) {
        super(new Color(240, 240, 240), new Color(220, 220, 220));
        this.parent = parent;
        initUI();
    }

    /* ------------------------------------------------------------------ */
    /* Inizializzazione GUI                                               */
    /* ------------------------------------------------------------------ */

    /** Configura layout e messaggio di benvenuto. */
    private void initUI() {
        setLayout(new BorderLayout());

        /* Messaggio centrato in HTML per consentire formattazione rapida. */
        JLabel lbl = new JLabel(
            """
            <html>
               <h1>Benvenuto su TheKnife</h1>
               <p>Scegli <b>Login</b> o <b>Registrati</b>.</p>
            </html>
            """,
            SwingConstants.CENTER);

        lbl.setFont(new Font("SansSerif", Font.PLAIN, 20));
        add(lbl, BorderLayout.CENTER);
    }
}
