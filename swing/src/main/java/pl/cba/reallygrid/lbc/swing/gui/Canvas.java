package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import javax.swing.JPanel;
import java.awt.Dimension;

public class Canvas extends JPanel {
    Canvas() {
        super(null);
        PreferencesKey widthKey = PreferencesKey.CANVAS_WIDTH;
        PreferencesKey heightKey = PreferencesKey.CANVAS_HEIGHT;
        setPreferredSize(new Dimension(Preferences.getInteger(widthKey),
                                       Preferences.getInteger(heightKey)));
    }
}
