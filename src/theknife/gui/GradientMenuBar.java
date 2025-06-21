/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello gradientmenubar.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui;

import java.awt.*;
import javax.swing.*;

/**
 * Barra dei menu con sfondo a gradiente verticale.
 *
 * <p>Estende {@link JMenuBar} e sostituisce il background standard con
 * un gradiente definito dai colori {@code startColor} (in alto) e
 * {@code endColor} (in basso).</p>
 *
 * <p>Il componente imposta <code>opaque = false</code> affinché
 * l’intera pittura del background sia gestita dal metodo
 * {@link #paintComponent(Graphics)}.</p>
 */
public class GradientMenuBar extends JMenuBar {

    /* ------------------------------------------------------------------ */
    /* Attributi                                                           */
    /* ------------------------------------------------------------------ */

    /** Colore iniziale (parte superiore) del gradiente. */
    private final Color startColor;

    /** Colore finale (parte inferiore) del gradiente. */
    private final Color endColor;

    /* ------------------------------------------------------------------ */
    /* Costruttore                                                         */
    /* ------------------------------------------------------------------ */

    /**
     * Crea una barra dei menu con gradiente personalizzato.
     *
     * @param startColor colore di partenza (in alto)
     * @param endColor   colore di arrivo   (in basso)
     */
    public GradientMenuBar(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor   = endColor;

        setOpaque(false);                       // disabilita background swing
        setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    /* ------------------------------------------------------------------ */
    /* Logica di pittura                                                  */
    /* ------------------------------------------------------------------ */

    /**
     * Disegna il gradiente su tutta l’area della JMenuBar.
     *
     * @param g contesto grafico fornito da Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            int width  = getWidth();
            int height = getHeight();

            GradientPaint gp = new GradientPaint(
                    0, 0,       startColor,
                    0, height,  endColor);

            g2.setPaint(gp);
            g2.fillRect(0, 0, width, height);
        } finally {
            g2.dispose();                       // libera risorse grafiche
        }
    }
}
