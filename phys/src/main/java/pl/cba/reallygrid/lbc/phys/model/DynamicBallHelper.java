package pl.cba.reallygrid.lbc.phys.model;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class DynamicBallHelper {
    DynamicBallHelper(Point2D position) {
        id = counter++;
        this.position = position;
    }
    
    public int getId() {
        return id;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public Point2D getPosition() {
        return position;
    }
    
    public void setPosition(Point2D position) {
        this.position.setLocation(position.getX(), position.getY());
    }
    
    public AffineTransform getTransform() {
        return transform;
    }
    
    public void setTranslate(Point2D position, double radius) {
        transform.setToTranslation(position.getX() - radius, position.getY() - radius);
    }
    
    private static int counter = 0;
    
    private final int id;
    
    private final AffineTransform transform = new AffineTransform();
    
    private long startTime;
    
    private Point2D position;
}
