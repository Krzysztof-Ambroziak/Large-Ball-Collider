package pl.cba.reallygrid.lbc.phys;

import pl.cba.reallygrid.lbc.phys.exceptions.NoObjectException;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.phys.model.Model;

import java.awt.Dimension;

public class Engine {
    public void addBall(DynamicBall ball) {
        model.add(ball);
    }
    
    public void start(Dimension dimension) throws NoObjectException {
        prepare(dimension);
        run();
    }
    
    private void prepare(Dimension dimension) throws NoObjectException {
        model.setCanvasSize(dimension.width, dimension.height);
        model.makeGrid();
        model.addBallsToGrid();
    }
    
    private void run() {
    }
    
    public void setDimmension(int width, int height) {
        model.setCanvasSize(width, height);
    }
    
    private Model model = new Model();
}
