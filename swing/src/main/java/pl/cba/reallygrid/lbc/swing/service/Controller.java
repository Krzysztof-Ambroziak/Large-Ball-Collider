package pl.cba.reallygrid.lbc.swing.service;

import pl.cba.reallygrid.lbc.phys.Engine;
import pl.cba.reallygrid.lbc.phys.exceptions.NoObjectException;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.phys.math.Vector2D;
import pl.cba.reallygrid.lbc.swing.gui.GuiProvider;
import pl.cba.reallygrid.lbc.swing.gui.Renderer;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public void init() {
        guiProvider.init(renderer);
        guiProvider.addAction(actions);
        guiProvider.show();
    }
    
    List<DynamicBall> getBalls(Point point) {
        return engine.getBalls(point);
    }
    
    void addBall(int x, int y) {
        DynamicBall ball = DynamicBall.Builder.floatBuilder()
                .setPosition(new Point2D.Float(x, y))
                .setVelocity(new Vector2D.Float(100.0f, 0.0f))
                .build();
        engine.addBall(ball);
    }
    
    void start() {
        try {
            Dimension canvasDimension = guiProvider.getCanvasDimension();
            engine.start(canvasDimension.width, canvasDimension.height);
            timer = new Timer("Timer: #" + timerCounter++);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    guiProvider.refresh();
                }
            }, 0L, 1000 / 60);
        }
        catch(NoObjectException e) {
            guiProvider.showWarningPane("No objects to simulation!");
        }
    }
    
    void stop() {
        engine.cancel();
        timer.cancel();
        timer.purge();
    }
    
    private static int timerCounter = 0;
    
    private GuiProvider guiProvider = new GuiProvider();
    
    private ActionProvider actions = new ActionProvider(this);
    
    private Engine engine = new Engine();
    
    private Renderer renderer = new Renderer(engine);
    
    private Timer timer;
}
