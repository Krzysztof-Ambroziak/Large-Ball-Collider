package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.swing.service.ActionProvider;

import javax.swing.SwingUtilities;

public class GuiProvider {
    public void init() {
        SwingUtilities.invokeLater(window::init);
    }
    
    public void addAction(ActionProvider actions) {
        window.addComponentListener(actions.windowListener);
        canvas.addMouseListener(actions.mouseListeners);
    }
    
    public void show() {
        SwingUtilities.invokeLater(() -> window.setVisible(true));
    }
    
    public GuiProvider() {
        window = new Window(canvas);
    }
    
    private final Window window;
    private final Canvas canvas = new Canvas();
}
