package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Canvas extends JPanel {
    Canvas() {
        super(null);
        PreferencesKey widthKey = PreferencesKey.CANVAS_WIDTH;
        PreferencesKey heightKey = PreferencesKey.CANVAS_HEIGHT;
        setPreferredSize(new Dimension(Preferences.getInteger(widthKey),
                                       Preferences.getInteger(heightKey)));
    }
    
    void setVelocity(Point from, Point to) {
        velocityFrom = from;
        velocityTo = to;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        renderer.drawCanvas(g);
        if(velocityFrom != null && velocityTo != null) {
            ((Graphics2D)g).setTransform(new AffineTransform());
            g.drawLine(velocityFrom.x, velocityFrom.y, velocityTo.x, velocityTo.y);
        }
    }
    
    void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
    
    private Renderer renderer;
    
    private Point velocityFrom;
    
    private Point velocityTo;
}
