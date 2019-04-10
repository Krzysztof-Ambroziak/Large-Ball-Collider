package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.swing.service.ActionProvider;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;

class SidePanel extends JPanel {
    SidePanel(BallInformation ballInformation) {
        super(new GridBagLayout());
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
    
    private AbstractButton start = new JButton("Start");
    
    private AbstractButton stop = new JButton("Stop");
}
