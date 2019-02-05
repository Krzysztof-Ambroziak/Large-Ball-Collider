package pl.cba.reallygrid.lbc.phys.math;

import java.util.Objects;

public abstract class Vector2D {
    public abstract double getX();
    
    public abstract double getY();
    
    public abstract void setPosition(double x, double y);
    
    public double dotProduct(Vector2D v) {
        return getX() * v.getX() + getY() * v.getY();
    }
    
    public double length() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }
    
    public double lengthSq() {
        return getX() * getX() + getY() * getY();
    }
    
    public abstract Vector2D normalize();
    
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj instanceof Vector2D) {
            Vector2D v = (Vector2D)obj;
            return Objects.equals(getX(), v.getX()) && Objects.equals(getY(), v.getY());
        }
        return false;
    }
    
    public static class Float extends Vector2D {
        public Float() {
        }
        
        public Float(float x, float y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public double getX() {
            return x;
        }
        
        @Override
        public double getY() {
            return y;
        }
        
        @Override
        public void setPosition(double x, double y) {
            this.x = (float)x;
            this.y = (float)y;
        }
        
        @Override
        public Vector2D normalize() {
            double len = length();
            
            return new Vector2D.Float((float)(x / len), (float)(y / len));
        }
        
        private float x = 0.0f;
        
        private float y = 0.0f;
    }
    
    public static class Double extends Vector2D {
        public Double() {
        }
        
        public Double(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public double getX() {
            return x;
        }
        
        @Override
        public double getY() {
            return y;
        }
        
        @Override
        public void setPosition(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public Vector2D normalize() {
            double len = length();
            
            return new Vector2D.Double(x / len, y / len);
        }
        
        private double x = 0.0;
        
        private double y = 0.0;
    }
    
    private Vector2D() {
    }
}
