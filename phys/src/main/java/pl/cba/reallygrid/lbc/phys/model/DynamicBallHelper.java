package pl.cba.reallygrid.lbc.phys.model;

import pl.cba.reallygrid.lbc.phys.math.Vector2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class DynamicBallHelper {
    DynamicBallHelper(DynamicBall ball) {
        id = counter++;
        startPosition = (Point2D)ball.getPosition().clone();
        startVelocity = ball.getVelocity().clone();
        setTranslate(this.startPosition, ball.getRadius());
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
    
    public Point2D getStartPosition() {
        return startPosition;
    }
    
    public void setStartPosition(Point2D startPosition) {
        this.startPosition.setLocation(startPosition);
    }
    
    public Vector2D getStartVelocity() {
        return startVelocity;
    }
    
    public void setStartVelocity(Vector2D startVelocity) {
        this.startVelocity.setPosition(startVelocity);
    }
    
    public DynamicBall getLastImpactBall() {
        return lastImpactBall;
    }
    
    public void setLastImpactBall(DynamicBall lastImpactBall) {
        this.lastImpactBall = lastImpactBall;
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
    
    private Point2D startPosition;
    
    private Vector2D startVelocity;
    
    private DynamicBall lastImpactBall;
}
