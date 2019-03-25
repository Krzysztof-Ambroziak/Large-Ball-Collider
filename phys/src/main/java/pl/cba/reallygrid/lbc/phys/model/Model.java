package pl.cba.reallygrid.lbc.phys.model;

import pl.cba.reallygrid.util.GeomMap;
import pl.cba.reallygrid.util.Pair;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Model {
    public void add(DynamicBall ball) {
        DynamicBallHelper helper = new DynamicBallHelper(ball);
        Pair<DynamicBall, DynamicBallHelper> tuple = new Pair<>(ball, helper);
        pairs.add(tuple);
        arrayMap.add(tuple);
    }
    
    public void addToImpactQueue(Pair<DynamicBall, DynamicBallHelper> pair) {
        impactQueue.add(pair);
    }
    
    public Pair<DynamicBall, DynamicBallHelper> pool() {
        return impactQueue.poll();
    }
    
    public void setDimension(Dimension dimension) {
        width = dimension.width;
        height = dimension.height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void realloc(Pair<DynamicBall, DynamicBallHelper> pair, double oldX, double oldY) {
        arrayMap.realloc(pair, oldX, oldY);
    }
    
    public Iterator<Pair<DynamicBall, ?>> neighbours(Point2D position) {
        return arrayMap.neighbours(position.getX(), position.getY());
    }
    
    public List<Pair<DynamicBall, DynamicBallHelper>> getPairs() {
        return pairs;
    }
    
    public long getPreviousTimestamp() {
        return previousTimestamp;
    }
    
    public void setPreviousTimestamp(long previousTimestamp) {
        this.previousTimestamp = previousTimestamp;
    }
    
    public long getCurrentTimestamp() {
        return currentTimestamp;
    }
    
    public void setCurrentTimestamp(long currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }
    
    private List<Pair<DynamicBall, DynamicBallHelper>> pairs = new ArrayList<>();
    
    private GeomMap<DynamicBall> arrayMap = new GeomMap<>();
    
    private Queue<Pair<DynamicBall, DynamicBallHelper>> impactQueue = new LinkedList<>();
    
    private int width;
    
    private int height;
    
    private long previousTimestamp;
    
    private long currentTimestamp;
}
