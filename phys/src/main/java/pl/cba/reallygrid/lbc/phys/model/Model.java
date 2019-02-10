package pl.cba.reallygrid.lbc.phys.model;

import pl.cba.reallygrid.lbc.phys.exceptions.NoObjectException;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Model {
    public void add(DynamicBall ball) {
        Point2D startPosition = (Point2D)ball.getPosition().clone();
        StartTimePosition startTimePosition = new StartTimePosition(startPosition);
        Pair<StartTimePosition, DynamicBall> pair = new Pair<>(startTimePosition, ball);
        pairs.add(pair);
    }
    
    public void setCanvasSize(int width, int height) {
        dimension = new Dimension(width, height);
    }
    
    public void makeGrid() throws NoObjectException {
        int cellSize = (int)Math.ceil(2 * largestBall().getRadius());
        arrayMap = new ArrayMap<>(dimension, cellSize);
    }
    
    private DynamicBall largestBall() throws NoObjectException {
        return pairs.stream()
                .map(Pair::getSecond)
                .reduce(DynamicBall::larger)
                .orElseThrow(NoObjectException::new);
    }
    
    public void addBallsToGrid() {
        pairs.forEach(arrayMap::put);
    }
    
    public List<Pair<StartTimePosition, DynamicBall>> getPairs() {
        return pairs;
    }
    
    private Dimension dimension;
    
    private List<Pair<StartTimePosition, DynamicBall>> pairs = new ArrayList<>();
    
    private ArrayMap<StartTimePosition, DynamicBall> arrayMap;
    
    public static class StartTimePosition {
        StartTimePosition(Point2D position) {
            this.position = position;
        }
        
        public long getTime() {
            return time;
        }
        
        public void setTime(long time) {
            this.time = time;
        }
        
        public Point2D getPosition() {
            return position;
        }
        
        private long time;
        
        private final Point2D position;
    }
}
