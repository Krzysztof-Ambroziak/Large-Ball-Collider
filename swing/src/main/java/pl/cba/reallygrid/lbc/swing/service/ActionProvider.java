package pl.cba.reallygrid.lbc.swing.service;

import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ActionProvider {
    public ComponentListener windowListener = new WindowPositionListener() {
        @Override
        public void componentMoved(ComponentEvent e) {
            Rectangle bounds = e.getComponent().getBounds();
            saveNewPlace(bounds);
        }
    };
    
    public MouseAdapter mouseListeners = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            List<DynamicBall> balls = controller.getBalls(point);
            if(balls.isEmpty()) {
                controller.addBall(point.x, point.y);
                e.getComponent().repaint();
            }
            else {
                // todo pokazac okienko z możliwością zmiany prędkości, promienia i masy
            }
        }
    };
    
    public ActionListener startSimulate = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.start();
        }
    };
    
    public ActionListener stopSimulate = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.stop();
        }
    };
    
    ActionProvider(Controller controller) {
        this.controller = controller;
    }
    
    private final Controller controller;
    
    private static class WindowPositionListener extends ComponentAdapter {
        void saveNewPlace(Rectangle bounds) {
            int x = bounds.x;
            int y = bounds.y;
            
            if(x != oldX) {
                Preferences.putInteger(PreferencesKey.POSITION_X, x);
                oldX = x;
            }
            if(y != oldY) {
                Preferences.putInteger(PreferencesKey.POSITION_Y, y);
                oldY = y;
            }
        }
        
        private int oldX = Preferences.getInteger(PreferencesKey.POSITION_X);
        private int oldY = Preferences.getInteger(PreferencesKey.POSITION_Y);
    }
}
