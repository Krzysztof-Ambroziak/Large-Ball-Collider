package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.swing.service.ActionProvider;
import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

class SidePanel extends JPanel {
    SidePanel() {
        super(new GridBagLayout());
//        setPreferredSize(new Dimension(Preferences.getInteger(PreferencesKey.SIDE_PANEL_WIDTH),
//                                       Preferences.getInteger(PreferencesKey.SIDE_PANEL_HEIGHT)));
        JPanel startStopInnerPanel = new JPanel();
        startStopInnerPanel.add(start);
        startStopInnerPanel.add(stop);
        add(startStopInnerPanel,
            new GBC(0, 0)
                    .insets(4, 8, 2, 8));
        add(ballInformation,
            new GBC(0, 1)
                    .weight(0.0, 1.0)
                    .insets(2, 8, 4, 8)
                    .anchor(GBC.NORTH));
        
    }
    
    void addAction(ActionProvider actions) {
        start.addActionListener(actions.startSimulate);
        stop.addActionListener(actions.stopSimulate);
    }
    
    void setActiveBall(DynamicBall activeBall, int id) {
        ballInformation.setActiveBall(activeBall, id);
    }
    
    private BallInformation ballInformation = new BallInformation();
    
    private AbstractButton start = new JButton("Start");
    
    private AbstractButton stop = new JButton("Stop");
}
