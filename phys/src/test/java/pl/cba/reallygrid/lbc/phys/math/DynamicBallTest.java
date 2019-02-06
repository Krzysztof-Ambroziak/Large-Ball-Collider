package pl.cba.reallygrid.lbc.phys.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.Calendar;
import java.util.Objects;

public class DynamicBallTest {
    @BeforeClass
    public static void beforeAllTests() {
        long timestamp = Calendar.getInstance().getTimeInMillis();
        double posX = 100 * Math.sin(timestamp);
        double posY = 100 * Math.sin(timestamp + 29);
        double velX = 100 * Math.sin(timestamp + 50);
        double velY = 100 * Math.sin(timestamp + 101);
        double m = 100 * Math.sin(timestamp + 3210); // mass
        double r = 100 * Math.sin(timestamp + 7391); // radius
        position = new Point2D.Float((float)posX, (float)posY);
        velocity = new Vector2D.Float((float)velX, (float)velY);
        mass = m;
        radius = r;
    }
    
    @Before
    public void setUp() throws Exception {
        ball = DynamicBall.Builder.floatBuilder()
                .setPosition(position)
                .setVelocity(velocity)
                .setMass(mass)
                .setRadius(radius)
                .build();
    }
    
    @Test
    public void setPosition() {
        Point2D newPosition = new Point2D.Float(
                (float)(position.getX() + 873.40101),
                (float)(position.getY() + 71.00914));
        ball.setPosition(newPosition);
        Assert.assertEquals(newPosition, ball.getPosition());
    }
    
    @Test
    public void setVelocity() {
        Vector2D newVelocity = new Vector2D.Float(
                (float)(velocity.getX() + 111.40689),
                (float)(velocity.getY() + 52.02214));
        ball.setVelocity(newVelocity);
        Assert.assertEquals(newVelocity, ball.getVelocity());
    }
    
    @Test
    public void setMass() {
        double newMass = ball.getMass() + 1832.037184;
        ball.setMass(newMass);
        Assert.assertEquals(newMass, ball.getMass(), 0.0);
    }
    
    @Test
    public void setRadius() {
        double rad = ball.getRadius() + 6912.02381;
        ball.setRadius(rad);
        Assert.assertEquals(rad, ball.getRadius(), 0.0);
    }
    
    @Test
    public void floatBuilder() {
        DynamicBall expectedBall = DynamicBall.Builder.floatBuilder().build();
        Point2D.Float positionEx = new Point2D.Float(77.939f, 11.098f);
        Vector2D.Float velocityEx = new Vector2D.Float(73.4433f, -22.9876f);
        double massEx = 321.4325;
        double radiusEx = 725.046719;
        expectedBall.setPosition(positionEx);
        expectedBall.setVelocity(velocityEx);
        expectedBall.setMass(massEx);
        expectedBall.setRadius(radiusEx);
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(positionEx)
                .setVelocity(velocityEx)
                .setMass(massEx)
                .setRadius(radiusEx)
                .build();
        Assert.assertEquals(expectedBall, ball2);
    }
    
    @Test
    public void doubleBuilder() {
        DynamicBall expectedBall = DynamicBall.Builder.doubleBuilder().build();
        Point2D.Double positionEx = new Point2D.Double(77.939, 11.098);
        Vector2D.Double velocityEx = new Vector2D.Double(73.4433, -22.9876);
        double massEx = 321.4325;
        double radiusEx = 725.046719;
        expectedBall.setPosition(positionEx);
        expectedBall.setVelocity(velocityEx);
        expectedBall.setMass(massEx);
        expectedBall.setRadius(radiusEx);
        DynamicBall ball2 = DynamicBall.Builder.doubleBuilder()
                .setPosition(positionEx)
                .setVelocity(velocityEx)
                .setMass(massEx)
                .setRadius(radiusEx)
                .build();
        Assert.assertEquals(expectedBall, ball2);
    }
    
    @Test
    public void equals() {
        Assert.assertEquals(ball, ball);
    }
    
    @Test
    public void equals1() {
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(position)
                .setVelocity(velocity)
                .setMass(mass)
                .setRadius(radius)
                .build();
        Assert.assertEquals(ball, ball2);
        Assert.assertEquals(ball2, ball);
    }
    
    @Test
    public void equals2() {
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(ball.getPosition())
                .setVelocity(ball.getVelocity())
                .setMass(ball.getMass())
                .setRadius(ball.getRadius())
                .build();
        DynamicBall ball3 = DynamicBall.Builder.floatBuilder()
                .setPosition(ball2.getPosition())
                .setVelocity(ball2.getVelocity())
                .setMass(ball2.getMass())
                .setRadius(ball2.getRadius())
                .build();
        Assert.assertEquals(ball, ball2);
        Assert.assertEquals(ball2, ball3);
        Assert.assertEquals(ball, ball3);
    }
    
    @Test
    public void equals3() {
        Assert.assertNotEquals(null, ball);
    }
    
    @Test
    public void equals4() {
        Point2D p = new Point2D.Float((float)position.getX(), (float)position.getY());
        Assert.assertNotEquals(ball, p);
    }
    
    @Test
    public void equals5() {
        Point2D newPosition = new Point2D.Float((float)position.getX(), (float)(position.getY() + 111.12691));
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(newPosition)
                .setVelocity(velocity)
                .setMass(mass)
                .setRadius(radius)
                .build();
        Assert.assertNotEquals(ball, ball2);
    }
    
    @Test
    public void equals6() {
        Vector2D newVelocity = new Vector2D.Float((float)velocity.getX(), (float)(velocity.getY() + 111.12691));
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(position)
                .setVelocity(newVelocity)
                .setMass(mass)
                .setRadius(radius)
                .build();
        Assert.assertNotEquals(ball, ball2);
    }
    
    @Test
    public void equals7() {
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(position)
                .setVelocity(velocity)
                .setMass(mass + 0.812731)
                .setRadius(radius)
                .build();
        Assert.assertNotEquals(ball, ball2);
    }
    
    @Test
    public void equals8() {
        DynamicBall ball2 = DynamicBall.Builder.floatBuilder()
                .setPosition(position)
                .setVelocity(velocity)
                .setMass(mass)
                .setRadius(radius + 893.715142)
                .build();
        Assert.assertNotEquals(ball, ball2);
    }
    
    @Test
    public void hashCode1() {
        int hash = Objects.hash(ball.getPosition(), ball.getVelocity(), ball.getMass(), ball.getRadius());
        
        Assert.assertEquals(hash, ball.hashCode());
    }
    
    private DynamicBall ball;
    
    private static Point2D position;
    
    private static Vector2D velocity;
    
    private static double mass;
    
    private static double radius;
}
