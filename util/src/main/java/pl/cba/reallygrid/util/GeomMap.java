package pl.cba.reallygrid.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.max;
import static pl.cba.reallygrid.util.Math.ceilInt;
import static pl.cba.reallygrid.util.Math.divInc;

public class GeomMap<T extends ShapePosition & ShapeSize> {
    public GeomMap() {
        this(0, 0);
    }
    
    public GeomMap(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        elementsLists = new List[rows * columns];
    }
    
    public GeomMap(Iterable<? extends Pair<? extends T, ?>> c) {
        for(Pair<? extends T, ?> t : c) {
            add(t);
        }
    }
    
    public boolean add(Pair<? extends T, ?> t) {
        if(assertReconfigure(t)) {
            setProperties(t);
            reconfigureElements();
        }
        return addToGrid(t);
    }
    
    private boolean assertReconfigure(Pair<? extends T, ?> t) {
        T shape = t.getFirst();
        return (ceilInt(shape.getWidth()) > width) ||
                (ceilInt(shape.getHeight()) > height) ||
                ((int)shape.getPosition().getX() >= columns * width) ||
                ((int)shape.getPosition().getY() >= rows * height);
    }
    
    private void setProperties(Pair<? extends T, ?> t) {
        T shape = t.getFirst();
        width = max(width, ceilInt(shape.getWidth()));
        height = max(height, ceilInt(shape.getHeight()));
        columns = max(columns, divInc((int)shape.getPosition().getX(), width));
        rows = max(rows, divInc((int)shape.getPosition().getY(), height));
    }
    
    private void reconfigureElements() {
        List<Pair<? extends T, ?>>[] oldCollection = elementsLists;
        makeNewArray();
        for(List<Pair<? extends T, ?>> t : oldCollection) {
            for(Pair<? extends T, ?> pair : t) {
                addToGrid(pair);
            }
        }
    }
    
    private void makeNewArray() {
        elementsLists = new List[columns * rows];
        for(int i = 0; i < elementsLists.length; i++) {
            elementsLists[i] = new ArrayList<>();
        }
    }
    
    private boolean addToGrid(Pair<? extends T, ?> t) {
        int posX = posX(t.getFirst().getPosition());
        int posY = posY(t.getFirst().getPosition());
        
        if(elementsLists[posY * columns + posX].add(t)) {
            sizeIncrement();
            return true;
        }
        return false;
    }
    
    private void sizeIncrement() {
        if(size != Integer.MAX_VALUE) {
            size++;
        }
    }
    
    private void sizeDecrement() {
        if(size > 0) {
            size--;
        }
    }
    
    public List<Pair<? extends T, ?>> get(Pair<? extends ShapePosition, ?> pair) {
        return get(pair.getFirst());
    }
    
    public List<Pair<? extends T, ?>> get(ShapePosition object) {
        return get(object.getPosition());
    }
    
    public List<Pair<? extends T, ?>> get(Point2D position) {
        return get(position.getX(), position.getY());
    }
    
    public List<Pair<? extends T, ?>> get(double x, double y) {
        int posX = posX(x);
        int posY = posY(y);
        
        return elementsLists[posY * columns + posX];
    }
    
    private boolean remove(Pair<? extends T, ?> pair, Point2D position) {
        return remove(pair, position.getX(), position.getY());
    }
    
    private boolean remove(Pair<? extends T, ?> pair, double x, double y) {
        int posX = posX(x);
        int posY = posY(y);
        
        if(elementsLists[posY * columns + posX].remove(pair)) {
            sizeDecrement();
            return true;
        }
        return false;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    private int posX(Point2D point) {
        return posX(point.getX());
    }
    
    private int posY(Point2D point) {
        return posY(point.getY());
    }
    
    private int posX(double x) {
        return (int)x / width;
    }
    
    private int posY(double y) {
        return (int)y / height;
    }
    
    public void realloc(Pair<? extends T, ?> pair, double oldX, double oldY) {
        if(assertRealloc(pair, oldX, oldY)) {
            remove(pair, oldX, oldY);
            add(pair);
        }
    }
    
    private boolean assertRealloc(Pair<? extends T, ?> pair, double oldX, double oldY) {
        Point2D position = pair.getFirst().getPosition();
        return posX(position) != posX(oldX) || posY(position) != posY(oldY);
    }
    
    public Iterator<Pair<? extends T, ?>> neighbours(double x, double y) {
        return new Itr(x, y);
    }
    
    private List<Pair<? extends T, ?>>[] elementsLists;
    
    private int size = 0;
    
    private int columns = 0;
    
    private int rows = 0;
    
    private int width = 0;
    
    private int height = 0;
    
    private class Itr implements Iterator<Pair<? extends T, ?>> {
        public Itr(double x, double y) {
            posX = posX(x);
            posY = posY(y);
            this.x = posX - 1;
            this.y = posY - 1;
        }
        
        @Override
        public boolean hasNext() {
            index++;
            while(y <= posY + 1) {
                while(x <= posX + 1) {
                    if(x >= 0 && x < columns && y >= 0 && y < rows && index < elementsLists[y * columns + x].size()) {
                        return true;
                    }
                    x++;
                    index = 0;
                }
                y++;
                x = posX - 1;
            }
            
            return false;
        }
        
        @Override
        public Pair<? extends T, ?> next() {
            return elementsLists[y * columns + x].get(index);
        }
        
        private final int posX;
        
        private final int posY;
        
        private int x;
        
        private int y;
        
        private int index = -1;
    }
}
