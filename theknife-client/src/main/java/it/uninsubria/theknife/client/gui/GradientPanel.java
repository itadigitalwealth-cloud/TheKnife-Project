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

    public GradientPanel() { setBackground(UITheme.BG); }
    public GradientPanel(LayoutManager l) { super(l); setBackground(UITheme.BG); }
    /** Compatibilità parte A – colori ignorati. */
    public GradientPanel(Color c1, Color c2) { setBackground(UITheme.BG); }
}