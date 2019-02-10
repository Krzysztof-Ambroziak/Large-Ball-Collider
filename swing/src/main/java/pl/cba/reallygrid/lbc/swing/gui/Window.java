package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.HeadlessException;

class Window extends JFrame {
    Window(Canvas canvas, SidePanel sidePanel) throws HeadlessException {
        add(canvas, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
    }
    
    void init() {
        setDefaultPreferences();
        setCustomPreferences();
    }
    
    private void setDefaultPreferences() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    private void setCustomPreferences() {
        PreferencesKey keyPositionX = PreferencesKey.POSITION_X;
        PreferencesKey keyPositionY = PreferencesKey.POSITION_Y;
        setBounds(Preferences.getInteger(keyPositionX), Preferences.getInteger(keyPositionY), 0, 0);
        pack();
    }
}
