/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello gradientpanel.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui;

import java.awt.*;
import javax.swing.*;

/**
 * Pannello Swing con sfondo a gradiente verticale.
 *
 * <p>L’estensione di {@link JPanel} consente di definire due colori
 * estremi (<em>startColor</em> in alto e <em>endColor</em> in basso) e
 * di ottenere una transizione fluida fra essi mediante
 * {@link java.awt.GradientPaint}.</p>
 *
 * <p>Il componente è dichiarato <code>opaque = false</code> per
 * lasciare alla logica di {@link #paintComponent(Graphics)} la completa
 * gestione del background.</p>
 */
public class GradientPanel extends JPanel {

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
     * Costruisce un pannello con sfondo sfumato.
     *
     * @param startColor colore di partenza (in alto)
     * @param endColor   colore di arrivo (in basso)
     */
    public GradientPanel(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor   = endColor;
        setOpaque(false);            // disabilita background di default
    }

    /* ------------------------------------------------------------------ */
    /* Logica di pittura                                                  */
    /* ------------------------------------------------------------------ */

    /**
     * Disegna il gradiente su tutta l’area del pannello.
     *
     * @param g contesto grafico fornito da Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Copia il contesto per non contaminare il Graphics originale
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            int w = getWidth();
            int h = getHeight();

            /* Definizione del gradiente verticale */
            GradientPaint gp = new GradientPaint(
                    0, 0,        startColor,  // punto (x=0, y=0)
                    0, h,        endColor);   // punto (x=0, y=altezza)

            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);          // riempie l’intero pannello
        } finally {
            g2.dispose();                     // libera le risorse GDI
        }

        /* Permette ai componenti figli di disegnarsi sopra il gradiente */
        super.paintComponent(g);
    }
}
