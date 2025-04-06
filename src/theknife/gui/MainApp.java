package theknife.gui;

import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        FancyFrame.setCustomNimbusLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            FancyFrame frame = new FancyFrame();
            frame.setVisible(true);
        });
    }
}
