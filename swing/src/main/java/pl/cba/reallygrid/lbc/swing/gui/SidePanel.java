package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.swing.service.ActionProvider;
import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SidePanel extends JPanel {
    SidePanel() {
        setPreferredSize(new Dimension(Preferences.getInteger(PreferencesKey.SIDE_PANEL_WIDTH),
                                       Preferences.getInteger(PreferencesKey.SIDE_PANEL_HEIGHT)));
        add(start);
        add(stop);
    }
    
    void addAction(ActionProvider actions) {
        start.addActionListener(actions.startSimulate);
        stop.addActionListener(actions.stopSimulate);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        ((Graphics2D)g).setBackground(Color.BLACK);
        g.clearRect(0, 0, getWidth(), getHeight());
    }
    
    private AbstractButton start = new JButton("Start");
    
    private AbstractButton stop = new JButton("Stop");
}
