package pl.cba.reallygrid.lbc.phys.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.Objects;

public class Vector2DTest {
    @Before
    public void setUp() throws Exception {
        vectorFloat = new Vector2D.Float();
        vectorDouble = new Vector2D.Double();
    }
    
    @Test
    public void setPositionFloatTest() {
        float[] expected = new float[] {xf, yf};
        vectorFloat.setPosition(xf, yf);
        float[] actual = new float[] {
                (float)vectorFloat.getX(),
                (float)vectorFloat.getY()
        };
        Assert.assertArrayEquals(expected, actual, 0f);
    }
    
    @Test
    public void setPositionDoubleTest() {
        double[] expected = new double[] {xd, yd};
        vectorDouble.setPosition(xd, yd);
        double[] actual = new double[] {
                vectorDouble.getX(),
                vectorDouble.getY()
        };
        Assert.assertArrayEquals(expected, actual, 0f);
    }
    
    @Test
    public void getXFloatTest() {
        vectorFloat.setPosition(xf, yf);
        Assert.assertEquals(xf, (float)vectorFloat.getX(), 0f);
    }
    
    @Test
    public void getYFloatTest() {
        vectorFloat.setPosition(xf, yf);
        Assert.assertEquals(yf, (float)vectorFloat.getY(), 0f);
    }
    
    @Test
    public void getXDoubleTest() {
        vectorDouble.setPosition(xd, yd);
        Assert.assertEquals(xd, vectorDouble.getX(), 0.0);
    }
    
    @Test
    public void getYDoubleTest() {
        vectorDouble.setPosition(xd, yd);
        Assert.assertEquals(yd, vectorDouble.getY(), 0.0);
    }
    
    @Test
    public void normalizeFloatTest() {
        double len = Math.sqrt(xf * xf + yf * yf);
        Vector2D normalizedVector = new Vector2D.Float((float)(xf / len), (float)(yf / len));
        vectorFloat.setPosition(xf, yf);
        
        Assert.assertEquals(normalizedVector, vectorFloat.normalize());
    }
    
    @Test
    public void lengthTest() {
        double length = Math.sqrt((double)xf * (double)xf + (double)yf * (double)yf);
        vectorFloat.setPosition(xf, yf);
    
        Assert.assertEquals(length, vectorFloat.length(), 0.0);
    }
    
    @Test
    public void lengthSqTest() {
        double lengthSq = (double)xf * (double)xf + (double)yf * (double)yf;
        vectorFloat.setPosition(xf, yf);
        
        Assert.assertEquals(lengthSq, vectorFloat.lengthSq(), 0.0);
    }
    
    @Test
    public void dotProductTest() {
        float x2 = 2323.1740021f;
        float y2 = -23.1200129f;
        double dot = (double)xf * x2 + (double)yf * y2;
        vectorFloat.setPosition(xf, yf);
        Vector2D v2 = new Vector2D.Float(x2, y2);
        
        Assert.assertEquals(dot, vectorFloat.dotProduct(v2), 0.0);
    }
    
    @Test
    public void normalizeDoubleTest() {
        double len = Math.sqrt(xd * xd + yd * yd);
        Vector2D normalizedVector = new Vector2D.Double(xd / len, yd / len);
        vectorDouble.setPosition(xd, yd);
        
        Assert.assertEquals(normalizedVector, vectorDouble.normalize());
    }
    
    @Test
    public void equalsReflexiveTest() {
        Assert.assertEquals(vectorFloat, vectorFloat);
    }
    
    @Test
    public void equalsSymmetricTest() {
        Vector2D vector2 = new Vector2D.Float();
        vector2.setPosition(vectorFloat.getX(), vectorFloat.getY());
        Assert.assertEquals(vectorFloat, vector2);
        Assert.assertEquals(vector2, vectorFloat);
    }
    
    @Test
    public void equalsTransitive() {
        Vector2D vector2 = new Vector2D.Float();
        Vector2D vector3 = new Vector2D.Float();
        vector2.setPosition(vectorFloat.getX(), vectorFloat.getY());
        vector3.setPosition(vector2.getX(), vector2.getY());
        Assert.assertEquals(vectorFloat, vector2);
        Assert.assertEquals(vector2, vector3);
        Assert.assertEquals(vectorFloat, vector3);
    }
    
    @Test
    public void equalsNullTest() {
        Assert.assertNotEquals(null, vectorFloat);
    }
    
    @Test
    public void equalsOtherTypeTest() {
        float x = (float)vectorFloat.getX();
        float y = (float)vectorFloat.getY();
        Point2D p = new Point2D.Float(x, y);
        Assert.assertFalse(vectorFloat.equals(p));
    }
    
    @Test
    public void hashCodeTest() {
        int expected = Objects.hash(vectorFloat.getX(), vectorFloat.getY());
        Assert.assertEquals(expected, vectorFloat.hashCode());
    }
    
    private static final float xf = 1.001f;
    
    private static final float yf = 2.003f;
    
    private static final double xd = 1.0001;
    
    private static final double yd = 2.0003;
    
    private Vector2D vectorFloat;
    
    private Vector2D vectorDouble;
}
