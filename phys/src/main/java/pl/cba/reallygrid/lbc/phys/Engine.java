package pl.cba.reallygrid.lbc.phys;

import pl.cba.reallygrid.lbc.phys.exceptions.NoObjectException;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.phys.model.Model;
import pl.cba.reallygrid.lbc.phys.model.Pair;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public void cancel() {
        engineTimer.stop();
    }
    
    public void draw(Graphics g) {
        for(Pair<Model.StartTimePosition, DynamicBall> pair : model.getPairs()) {
            DynamicBall ball = pair.getSecond();
            Point2D position = ball.getPosition();
            double radius = ball.getRadius();
            int size = (int)Math.round(2 * radius);
            ((Graphics2D)g).setTransform(AffineTransform.getTranslateInstance(position.getX() - radius, position.getY() - radius));
            g.drawOval(0, 0, size, size);
        }
    }
    
    public List<DynamicBall> getBalls(Point point) {
        return model.getPairs().stream()
                .map(Pair::getSecond)
                .filter(ball -> DynamicBall.isInside(ball, point))
                .collect(Collectors.toList());
    }
    
    private Model model = new Model();
    
    private EngineTimer engineTimer;
}
