package pl.cba.reallygrid.lbc.swing.service;

import pl.cba.reallygrid.lbc.phys.Engine;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.swing.gui.GuiProvider;

import java.awt.geom.Point2D;

public class Controller {
    public void init() {
        initGui();
        initEngine();
    }
    
    private void initGui() {
        guiProvider.init();
        guiProvider.addAction(actions);
        guiProvider.show();
    }
    
    private void initEngine() {
    }
    
    void addBall(int x, int y) {
        DynamicBall ball = DynamicBall.Builder.floatBuilder()
                .setPosition(new Point2D.Float(x, y))
                .build();
        engine.addBall(ball);
    }
    
    private GuiProvider guiProvider = new GuiProvider();
    
    private ActionProvider actions = new ActionProvider(this);
    
    private Engine engine = new Engine();
}
