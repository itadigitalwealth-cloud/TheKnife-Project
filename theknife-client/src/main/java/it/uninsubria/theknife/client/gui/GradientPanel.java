/**
 * TheKnife – Pannello base.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 * @author De Zuane Samuele   – 763267 – sede CO
 */
package it.uninsubria.theknife.client.gui;

import javax.swing.JPanel;
import java.awt.*;

/**
 * Pannello base con sfondo UITheme.BG.
 * Usato come parent per tutti i pannelli funzionali.
 */
public class GradientPanel extends JPanel {

    /**
     * Crea un pannello con sfondo {@link UITheme#BG} e layout di default ({@code FlowLayout}).
     */
    public GradientPanel() { setBackground(UITheme.BG); }

    /**
     * Crea un pannello con sfondo {@link UITheme#BG} e il layout specificato.
     *
     * @param l il {@link LayoutManager} da applicare al pannello
     */
    public GradientPanel(LayoutManager l) { super(l); setBackground(UITheme.BG); }

    /**
     * Costruttore di compatibilità: i parametri colore vengono ignorati,
     * il pannello usa sempre {@link UITheme#BG}.
     *
     * @param c1 colore iniziale (ignorato)
     * @param c2 colore finale (ignorato)
     */
    public GradientPanel(Color c1, Color c2) { setBackground(UITheme.BG); }
}