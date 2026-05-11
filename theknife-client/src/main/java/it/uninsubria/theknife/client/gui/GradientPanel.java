/**
 * TheKnife – Modulo Client
 * Pannello base con sfondo elegante.
 *
 * @author Matteo Vigano  – 760537 – sede CO
 * @author Fabio Vecaj    – 761232 – sede CO
 */

package it.uninsubria.theknife.client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello base con sfondo pulito definito dal tema {@link UITheme}.
 * Tutti i pannelli funzionali ereditano da questa classe.
 */
public class GradientPanel extends JPanel {

    public GradientPanel() {
        setBackground(UITheme.BG);
    }

    public GradientPanel(LayoutManager layout) {
        super(layout);
        setBackground(UITheme.BG);
    }

    /** Compatibilità con la parte A – i colori vengono ignorati. */
    public GradientPanel(Color c1, Color c2) {
        setBackground(UITheme.BG);
    }
}