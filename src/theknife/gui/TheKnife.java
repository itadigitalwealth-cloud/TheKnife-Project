/**
 * PROGETTO UNIVERSITÀ – TheKnife
 * Classe di modello theknife.
 *
 * @author Matteo Vigano – 760537 – sede CO
 * @author FABIO  Vecaj  – 761232 – sede CO
 */

package theknife.gui;

import javax.swing.SwingUtilities;

/**
 * Punto d’ingresso dell’applicazione TheKnife.
 *
 * <p>Questa classe:</p>
 * <ol>
 *   <li>Imposta il Look & Feel Nimbus personalizzato tramite
 *       {@link FancyFrame#setCustomNimbusLookAndFeel()}.</li>
 *   <li>Avvia la GUI sul thread EDT (Event Dispatch Thread) usando
 *       {@link SwingUtilities#invokeLater(Runnable)}.</li>
 *   <li>Crea un’istanza di {@link FancyFrame} e la rende visibile.</li>
 * </ol>
 *
 * <p>Non contiene logica di business: il suo unico compito è bootstrap
 * dell’interfaccia grafica.</p>
 */
public final class TheKnife {

    /**
     * Metodo <code>main</code> eseguito dalla JVM all’avvio.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {

        /* Imposta il tema Nimbus prima di creare qualunque componente Swing */
        FancyFrame.setCustomNimbusLookAndFeel();

        /* Avvia l’interfaccia sul thread dedicato agli eventi Swing (EDT) */
        SwingUtilities.invokeLater(() -> {
            FancyFrame frame = new FancyFrame();
            frame.setVisible(true);
        });
    }

    /** Costruttore privato per impedire l'istanziazione. */
    private TheKnife() { }
}
