package pl.cba.reallygrid.lbc.phys.math;

import java.awt.geom.Point2D;
import java.util.Objects;

public class DynamicBall {
    public Point2D getPosition() {
        return position;
    }
    
    public void setPosition(Point2D position) {
        this.position = position;
    }
    
    public Vector2D getVelocity() {
        return velocity;
    }
    
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
    
    public double getMass() {
        return mass;
    }
    
    public void setMass(double mass) {
        this.mass = mass;
    }
    
    private DynamicBall(Builder builder) {
        position = builder.position;
        velocity = builder.velocity;
        mass = builder.mass;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(position, velocity, mass);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj instanceof DynamicBall) {
            DynamicBall ball = (DynamicBall)obj;
            return position.equals(ball.position) && velocity.equals(ball.velocity) && Objects.equals(mass, ball.mass);
        }
        return false;
    }
    
    private Point2D position;
    private Vector2D velocity;
    private double mass;
    
    public static final class Builder {
        public static Builder floatBuilder() {
            Builder builder = new Builder();
            builder.position = new Point2D.Float();
            builder.velocity = new Vector2D.Float();
            return builder;
        }
        
        public static Builder doubleBuilder() {
            Builder builder = new Builder();
            builder.position = new Point2D.Double();
            builder.velocity = new Vector2D.Double();
            return builder;
        }
        
        public Builder setMass(double mass) {
            this.mass = mass;
            return this;
        }
        
        public Builder setPosition(Point2D position) {
            this.position = (Point2D)position.clone();
            return this;
        }
        
        public Builder setVelocity(Vector2D velocity) {
            this.velocity = (Vector2D)velocity.clone();
            return this;
        }
        
        public DynamicBall build() {
            return new DynamicBall(this);
        }
        
        private Builder() {
        }
        
        private Point2D position;
        private Vector2D velocity;
        private double mass = 0.0;
    }
}
