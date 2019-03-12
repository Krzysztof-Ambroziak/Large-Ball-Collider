package pl.cba.reallygrid.lbc.phys.model;

import pl.cba.reallygrid.util.GeomMap;
import pl.cba.reallygrid.util.Pair;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
    public void add(DynamicBall ball) {
        Point2D startPosition = (Point2D)ball.getPosition().clone();
        DynamicBallHelper helper = new DynamicBallHelper(startPosition);
        helper.setTranslate(startPosition, ball.getRadius());
        Pair<DynamicBall, DynamicBallHelper> pair = new Pair<>(ball, helper);
        pairs.add(pair);
        arrayMap.add(pair);
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
    
    public Iterator<Pair<? extends DynamicBall, ?>> neighbours(Point2D position) {
        return arrayMap.neighbours(position.getX(), position.getY());
    }
    
    public List<Pair<DynamicBall, DynamicBallHelper>> getPairs() {
        return pairs;
    }
    
    private List<Pair<DynamicBall, DynamicBallHelper>> pairs = new ArrayList<>();
    
    private GeomMap<DynamicBall> arrayMap = new GeomMap<>();
    
    private int width;
    
    private int height;
}
