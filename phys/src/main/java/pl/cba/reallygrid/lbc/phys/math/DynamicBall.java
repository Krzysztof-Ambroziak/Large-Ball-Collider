package pl.cba.reallygrid.lbc.phys.math;

import java.awt.geom.Point2D;
import java.util.Objects;

public class DynamicBall implements PositionBall {
    public static DynamicBall largest(DynamicBall ball1, DynamicBall ball2) {
        return ball1.getRadius() >= ball2.getRadius() ? ball1 : ball2;
    }
    
    public static DynamicBall fastest(DynamicBall ball1, DynamicBall ball2) {
        return ball1.getVelocity().lengthSq() >= ball2.getVelocity().lengthSq() ? ball1 : ball2;
    }
    
    @Override
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
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(position, velocity, mass, radius);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof DynamicBall)) {
            return false;
        }
        DynamicBall ball = (DynamicBall)obj;
        return position.equals(ball.position)
                && velocity.equals(ball.velocity)
                && Objects.equals(mass, ball.mass)
                && Objects.equals(radius, ball.radius);
    }
    
    @Override
    public String toString() {
        return DynamicBall.class.toString() + '{' +
                "position: (" + position.getX() + ", " + position.getY() + "), " +
                "velocity: [" + velocity.getX() + ", " + velocity.getY() + "], " +
                "mass: " + mass + ", " +
                "radius: " + radius + '}';
    }
    
    private Point2D position;
    
    private Vector2D velocity;
    
    private double mass;
    
    private double radius;
    
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
        
        public Builder setPosition(Point2D position) {
            this.position.setLocation(position);
            return this;
        }
        
        public Builder setVelocity(Vector2D velocity) {
            this.velocity.setPosition(velocity);
            return this;
        }
        
        public Builder setMass(double mass) {
            this.mass = mass;
            return this;
        }
        
        public Builder setRadius(double radius) {
            this.radius = radius;
            return this;
        }
        
        public DynamicBall build() {
            return new DynamicBall(this);
        }
        
        private Builder() {
        }
        
        private static final double DEFAULT_MASS = 10.0;
        
        private static final double DEFAULT_RADIUS = 10.0;
        
        private Point2D position;
        
        private Vector2D velocity;
        
        private double mass = DEFAULT_MASS;
        
        private double radius = DEFAULT_RADIUS;
    }
    
    private DynamicBall(Builder builder) {
        position = builder.position;
        velocity = builder.velocity;
        mass = builder.mass;
        radius = builder.radius;
    }
}
