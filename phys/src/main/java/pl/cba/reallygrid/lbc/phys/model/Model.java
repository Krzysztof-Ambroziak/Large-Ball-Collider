package pl.cba.reallygrid.lbc.phys.model;

import pl.cba.reallygrid.lbc.phys.exceptions.NoObjectException;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Model {
    public void add(DynamicBall ball) {
        balls.add(ball);
    }
    
    public void setCanvasSize(int width, int height) {
        dimension.width = width;
        dimension.height = height;
    }
    
    public void makeGrid() throws NoObjectException {
        int cellSize = (int)Math.ceil(2 * largestBall().getRadius());
        arrayMap = new ArrayMap<>(dimension, cellSize);
    }
    
    private DynamicBall largestBall() throws NoObjectException {
        return balls.stream()
                .reduce(DynamicBall::largest)
                .orElseThrow(NoObjectException::new);
    }
    
    private DynamicBall fastestBall() throws NoObjectException {
        return balls.stream()
                .reduce(DynamicBall::fastest)
                .orElseThrow(NoObjectException::new);
    }
    
    public void addBallsToGrid() {
        balls.forEach(ball -> arrayMap.put(ball));
    }
    
    private Dimension dimension = new Dimension();
    
    private List<DynamicBall> balls = new ArrayList<>();
    
    private ArrayMap<DynamicBall> arrayMap;
}
