package pl.cba.reallygrid.lbc.phys.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.phys.math.Vector2D;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayMapTest {
    @BeforeClass
    public static void beforeAllTests() {
        cellSize = 30;
        dimension = new Dimension(16 * cellSize + (int)(0.23 * cellSize), 9 * cellSize + (int)(0.75 * cellSize));
    }
    
    @Before
    public void setUp() throws Exception {
        arrayMap = new ArrayMap<>(dimension, cellSize);
    }
    
    @Test
    public void get() {
        Point2D.Double p = new Point2D.Double(16 * cellSize, 9 * cellSize);
        DynamicBall ball = DynamicBall.Builder.doubleBuilder()
                .setPosition(p)
                .setVelocity(new Vector2D.Double(Math.sin(3), Math.sin(4)))
                .setMass(Math.sin(5))
                .setRadius(Math.sin(6))
                .build();
        arrayMap.put(ball);
        
        List<DynamicBall> dynamicBalls = arrayMap.get(p);
        Assert.assertTrue(dynamicBalls.contains(ball));
    }
    
    @Test
    public void size() {
        Assert.assertEquals(0, arrayMap.size());
    }
    
    @Test
    public void size1() {
        DynamicBall ball = DynamicBall.Builder.doubleBuilder()
                .setPosition(new Point2D.Double(Math.sin(1), Math.sin(2)))
                .setVelocity(new Vector2D.Double(Math.sin(3), Math.sin(4)))
                .setMass(Math.sin(5))
                .setRadius(Math.sin(6))
                .build();
        arrayMap.put(ball);
        
        Assert.assertEquals(1, arrayMap.size());
    }
    
    @Test
    public void size2() {
        DynamicBall ball1 = DynamicBall.Builder.doubleBuilder()
                .setPosition(new Point2D.Double(Math.sin(1), Math.sin(2)))
                .setVelocity(new Vector2D.Double(Math.sin(3), Math.sin(4)))
                .setMass(Math.sin(5))
                .setRadius(Math.sin(6))
                .build();
        DynamicBall ball2 = DynamicBall.Builder.doubleBuilder()
                .setPosition(new Point2D.Double(Math.sin(11), Math.sin(12)))
                .setVelocity(new Vector2D.Double(Math.sin(13), Math.sin(14)))
                .setMass(Math.sin(15))
                .setRadius(Math.sin(16))
                .build();
        
        arrayMap.put(ball1);
        arrayMap.put(ball2);
        
        Assert.assertEquals(2, arrayMap.size());
    }
    
    private static int cellSize;
    private static Dimension dimension;
    private ArrayMap<DynamicBall> arrayMap;
}