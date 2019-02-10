package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

public class Canvas extends JPanel {
    Canvas() {
        super(null);
        PreferencesKey widthKey = PreferencesKey.CANVAS_WIDTH;
        PreferencesKey heightKey = PreferencesKey.CANVAS_HEIGHT;
        setPreferredSize(new Dimension(Preferences.getInteger(widthKey),
                                       Preferences.getInteger(heightKey)));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        renderer.drawCanvas(g);
    }
    
    void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
    
    private Renderer renderer;
}
