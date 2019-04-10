package pl.cba.reallygrid.lbc.swing.service;

import pl.cba.reallygrid.lbc.phys.Engine;
import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.swing.gui.GuiProvider;
import pl.cba.reallygrid.lbc.swing.gui.Renderer;
import pl.cba.reallygrid.lbc.swing.model.Model;

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
        return engine.getBalls(point.x, point.y);
    }
    
    void addBall(int x, int y) {
        DynamicBall ball = DynamicBall.Builder.floatBuilder()
                .setPosition(x, y)
                .build();
        engine.addBall(ball);
        int id = engine.getBallId(ball);
        setActiveBall(ball, id);
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
        engine.updatePositions();
    }
    
    void refreshAll() {
        guiProvider.refresh();
        guiProvider.repaintSidePanel();
    }
    
    void setActiveBall(DynamicBall ball, int id) {
        model.setActiveBall(ball, id);
        model.setRoundedPosition(ball.getPosition());
        guiProvider.setActiveBall(ball, id);
    }
    
    void setVelocity(Point point) {
        Point from = model.getRoundedPosition();
        guiProvider.setVelocity(from, point);
    }
    
    void saveNewVelocity() {
        Point velocity = model.getVelocity();
        Point2D position = model.getActiveBall().getPosition();
        double x = velocity.x - position.getX();
        double y = velocity.y - position.getY();
        engine.changeVelocity(model.getActiveBall(), x, y);
    }
    
    void resetVelocity() {
        guiProvider.setVelocity(null, null);
    }
    
    int getBallId(DynamicBall ball) {
        return engine.getBallId(ball);
    }
    
    void setMassAndRadius(double mass, double radius) {
        DynamicBall ball = model.getActiveBall();
        engine.setMassAndRadius(ball, mass, radius);
    }
    
    private static int timerCounter = 0;
    
    private GuiProvider guiProvider = new GuiProvider();
    
    private Model model = new Model();
    
    private ActionProvider actions = new ActionProvider(this, model);
    
    private Engine engine = new Engine();
    
    private Renderer renderer = new Renderer(engine);
    
    private Timer timer;
}
