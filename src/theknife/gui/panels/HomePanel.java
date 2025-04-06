package theknife.gui.panels;

import theknife.gui.FancyFrame;
import theknife.gui.GradientPanel;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends GradientPanel {
    private FancyFrame parent;

    public HomePanel(FancyFrame parent) {
        super(new Color(240, 240, 240), new Color(220, 220, 220));
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("<html><h1>Benvenuto su TheKnife</h1><p>Scegli Login o Registrati.</p></html>",
                SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 20));
        add(lbl, BorderLayout.CENTER);
    }
}
