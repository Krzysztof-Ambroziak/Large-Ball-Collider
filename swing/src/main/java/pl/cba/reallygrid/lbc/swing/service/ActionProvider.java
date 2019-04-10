package pl.cba.reallygrid.lbc.swing.service;

import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.swing.gui.BallInformation;
import pl.cba.reallygrid.lbc.swing.model.Model;
import pl.cba.reallygrid.lbc.swing.util.Preferences;
import pl.cba.reallygrid.lbc.swing.util.PreferencesKey;

import javax.swing.JButton;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class ActionProvider {
    public ComponentListener windowListener = new WindowPositionListener() {
        @Override
        public void componentMoved(ComponentEvent e) {
            Rectangle bounds = e.getComponent().getBounds();
            saveNewPlace(bounds);
        }
    };
    
    public MouseListener mouseListeners = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            List<DynamicBall> balls = controller.getBalls(point);
            if(balls.isEmpty()) {
                controller.addBall(point.x, point.y);
            }
            controller.refreshAll();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            Point point = e.getPoint();
            List<DynamicBall> balls = controller.getBalls(point);
            if(!balls.isEmpty()) {
                int id = controller.getBallId(balls.get(0));
                controller.setActiveBall(balls.get(0), id);
                controller.refreshAll();
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            Point velocity = model.getVelocity();
            if(velocity != null) {
                controller.saveNewVelocity();
                controller.resetVelocity();
                controller.refreshAll();
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            model.setVelocity(null);
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
    
    public MouseMotionListener velocityAction = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            model.setVelocity(e.getPoint());
            controller.setVelocity(e.getPoint());
            controller.refreshAll();
        }
    };
    
    public ActionListener saveAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BallInformation ballInformationPanel = (BallInformation)((JButton)e.getSource()).getParent();
            String mass = ballInformationPanel.getMass();
            String radius = ballInformationPanel.getRadius();
            controller.setMassAndRadius(Double.parseDouble(mass), Double.parseDouble(radius));
            controller.refreshAll();
        }
    };
    
    ActionProvider(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
    }
    
    private final Controller controller;
    
    private final Model model;
    
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
