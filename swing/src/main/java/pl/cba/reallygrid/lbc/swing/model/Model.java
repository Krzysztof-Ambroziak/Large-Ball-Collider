package pl.cba.reallygrid.lbc.swing.model;

import pl.cba.reallygrid.lbc.phys.model.DynamicBall;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Model {
    public DynamicBall getActiveBall() {
        return activeBall;
    }
    
    public int getActiveBallId() {
        return activeBallId;
    }
    
    public void setActiveBall(DynamicBall activeBall, int id) {
        this.activeBall = activeBall;
        activeBallId = id;
    }
    
    public Point getRoundedPosition() {
        return activeBallPosition;
    }
    
    public void setRoundedPosition(Point2D position) {
        activeBallPosition.setLocation(position.getX(), position.getY());
    }
    
    public Point getVelocity() {
        return newVelocity;
    }
    
    public void setVelocity(Point point) {
        newVelocity = point;
    }
    
    private DynamicBall activeBall;
    
    private int activeBallId;
    
    private Point activeBallPosition = new Point();
    
    private Point newVelocity;
}
