package pl.cba.reallygrid.lbc.phys.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.Objects;

public class DynamicBallTest {
    @Before
    public void setUp() throws Exception {
        ball = DynamicBall.Builder.floatBuilder()
                .setPosition(position)
                .setVelocity(velocity)
                .setMass(mass)
                .build();
    }
    
    @Test
    public void getPosition() {
        Assert.assertEquals(position, ball.getPosition());
    }
    
    @Test
    public void setPosition() {
        Point2D newPosition = new Point2D.Float(873.401f, 71.0091f);
        ball.setPosition(newPosition);
        Assert.assertEquals(newPosition, ball.getPosition());
    }
    
    @Test
    public void getVelocity() {
        Assert.assertEquals(velocity, ball.getVelocity());
    }
    
    @Test
    public void setVelocity() {
        Vector2D newVelocity = new Vector2D.Float(113.201f, 35.0956f);
        ball.setVelocity(newVelocity);
        Assert.assertEquals(newVelocity, ball.getVelocity());
    }
    
    @Test
    public void getMass() {
        Assert.assertEquals(mass, ball.getMass(), 0.0);
    }
    
    @Test
    public void setMass() {
        double newMass = 91.7913;
        ball.setMass(newMass);
        Assert.assertEquals(newMass, ball.getMass(), 0.0);
    }
    
    @Test
    public void floatBuilder() {
        DynamicBall expectedBall = DynamicBall.Builder.floatBuilder().build();
        Point2D.Float positionEx = new Point2D.Float(77.939f, 11.098f);
        Vector2D.Float velocityEx = new Vector2D.Float(73.4433f, -22.9876f);
        double massEx = 321.4325;
        expectedBall.setPosition(positionEx);
        expectedBall.setVelocity(velocityEx);
        expectedBall.setMass(massEx);
        Assert.assertEquals(expectedBall, DynamicBall.Builder.floatBuilder().setPosition(positionEx).setVelocity(velocityEx).setMass(massEx).build());
    }
    
    @Test
    public void doubleBuilder() {
        DynamicBall expectedBall = DynamicBall.Builder.doubleBuilder().build();
        Point2D.Double positionEx = new Point2D.Double(77.939, 11.098);
        Vector2D.Double velocityEx = new Vector2D.Double(73.4433, -22.9876);
        double massEx = 321.4325;
        expectedBall.setPosition(positionEx);
        expectedBall.setVelocity(velocityEx);
        expectedBall.setMass(massEx);
        Assert.assertEquals(expectedBall, DynamicBall.Builder.floatBuilder().setPosition(positionEx).setVelocity(velocityEx).setMass(massEx).build());
    }
    
    @Test
    public void equals() {
        Assert.assertEquals(ball, ball);
    }
    
    @Test
    public void equals1() {
        Assert.assertNotEquals(ball, new Point2D.Float());
    }
    
    @Test
    public void equals2() {
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition((Point2D)position.clone())
                .setVelocity((Vector2D)velocity.clone())
                .setMass(mass)
                .build();
        Assert.assertEquals(ball, ball2);
    }
    
    @Test
    public void equals3() {
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(new Point2D.Float(0.33f, 44.4f))
                .setVelocity(new Vector2D.Float(-22.9f, 0.003f))
                .setMass(9393.0001)
                .build();
        Assert.assertNotEquals(ball, ball2);
    }
    
    @Test
    public void hashCode1() {
        int hash = Objects.hash(ball.getPosition(), ball.getVelocity(), ball.getMass());
        
        Assert.assertEquals(hash, ball.hashCode());
    }
    
    private DynamicBall ball;
    private Point2D position = new Point2D.Float(12.031f, 62.217f);
    private Vector2D velocity = new Vector2D.Float(100.8301f, -37.8422f);
    private double mass = 349.721054;
}
