package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.swing.service.ActionProvider;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Point;

public class GuiProvider {
    public GuiProvider() {
        window = new Window(canvas, sidePanel);
    }
    
    public void init(Renderer renderer) {
        canvas.setRenderer(renderer);
        SwingUtilities.invokeLater(window::init);
    }
    
    public void addAction(ActionProvider actions) {
        window.addComponentListener(actions.windowListener);
        canvas.addMouseListener(actions.mouseListeners);
        canvas.addMouseMotionListener(actions.velocityAction);
        ballInformation.addAction(actions.saveAction);
        sidePanel.addAction(actions);
    }
    
    public void show() {
        SwingUtilities.invokeLater(() -> window.setVisible(true));
    }
    
    public Dimension getCanvasDimension() {
        return canvas.getSize();
    }
    
    public void refresh() {
        canvas.repaint();
    }
    
    public void repaintSidePanel() {
        sidePanel.repaint();
    }
    
    public void setActiveBall(DynamicBall activeBall, int id) {
        ballInformation.setActiveBall(activeBall, id);
    }
    
    public void setVelocity(Point from, Point to) {
        canvas.setVelocity(from, to);
    }
    
    public void showWarningPane(String text) {
        JOptionPane.showMessageDialog(canvas, text, "Warning!", JOptionPane.WARNING_MESSAGE);
    }
    
    private final Window window;
    
    private final Canvas canvas = new Canvas();
    
    private final BallInformation ballInformation = new BallInformation();
    
    private final SidePanel sidePanel = new SidePanel(ballInformation);
}
