package pl.cba.reallygrid.lbc.phys;

import pl.cba.reallygrid.lbc.phys.exceptions.NoObjectException;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.phys.model.Model;

public class Engine {
    public void setDimmension(int width, int height) {
        model.setCanvasSize(width, height);
    }
    
    public void addBall(DynamicBall ball) {
        model.add(ball);
    }
    
    public void start() throws NoObjectException {
        prepare();
        run();
    }
    
    private void prepare() throws NoObjectException {
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
