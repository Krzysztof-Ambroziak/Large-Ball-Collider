package pl.cba.reallygrid.lbc.phys;

import pl.cba.reallygrid.lbc.phys.exceptions.NoObjectException;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.phys.model.Model;

public class Engine {
    public void addBall(DynamicBall ball) {
        model.add(ball);
    }
    
    public void start(int width, int height) throws NoObjectException {
        prepare(width, height);
        run();
    }
    
    private void prepare(int width, int height) throws NoObjectException {
        model.setCanvasSize(width, height);
        model.makeGrid();
        model.addBallsToGrid();
        EngineTimerTask task = new EngineTimerTask(model);
        engineTimer = new EngineTimer(task);
    }
    
    private void run() {
        engineTimer.start();
    }
    
    private void cancel() {
        engineTimer.stop();
    }
    
    private Model model = new Model();
    
    private EngineTimer engineTimer;
}
