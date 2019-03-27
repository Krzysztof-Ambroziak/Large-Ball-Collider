package pl.cba.reallygrid.lbc.swing.service;

import pl.cba.reallygrid.lbc.phys.Engine;
import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.swing.gui.GuiProvider;
import pl.cba.reallygrid.lbc.swing.gui.Renderer;
import pl.cba.reallygrid.lbc.swing.model.Model;

import java.awt.Point;
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
        return engine.getBalls(point.x, point.y);
    }
    
    void addBall(int x, int y) {
        DynamicBall ball;
        int id;
        if(b) {
            ball = DynamicBall.Builder.floatBuilder()
                    .setPosition(x, y)
                    .setVelocity(100.0f, 0.0f) // todo może random...
                    .build();
            id = engine.addBall(ball);
            b = false;
        }
        else {
            ball = DynamicBall.Builder.floatBuilder()
                    .setPosition(x, y)
                    .setVelocity(-100.0f, 0.0f) // todo może random...
                    .build();
            id = engine.addBall(ball);
            b = true;
        }
        model.setActiveBall(ball, id);
        guiProvider.setActiveBall(ball, id);
    }
    
    void start() {
        engine.setDimension(guiProvider.getCanvasDimension());
        engine.start();
        timer = new Timer("Gui refresh Timer: #" + timerCounter++);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                guiProvider.refresh();
                guiProvider.setActiveBall(model.getActiveBall(), model.getActiveBallId());
                guiProvider.repaintSidePanel();
            }
        }, 0L, 1000 / 60);
    }
    
    void stop() {
        engine.cancel();
        timer.cancel();
        timer.purge();
    }
    
    private boolean b = true;
    
    private static int timerCounter = 0;
    
    private GuiProvider guiProvider = new GuiProvider();
    
    private Model model = new Model();
    
    private ActionProvider actions = new ActionProvider(this, model);
    
    private Engine engine = new Engine();
    
    private Renderer renderer = new Renderer(engine);
    
    private Timer timer;
    
    void refreshAll() {
        guiProvider.refresh();
        guiProvider.repaintSidePanel();
    }
}
