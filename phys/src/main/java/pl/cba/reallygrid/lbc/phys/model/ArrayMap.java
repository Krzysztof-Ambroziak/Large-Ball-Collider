package pl.cba.reallygrid.lbc.phys.model;

import pl.cba.reallygrid.lbc.phys.math.PositionBall;
import pl.cba.reallygrid.lbc.phys.util.Math;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ArrayMap<T extends PositionBall> {
    public ArrayMap(Dimension dimension, int cellSize) {
        this.dimension = dimension;
        this.cellSize = cellSize;
        collumns = Math.divCell(dimension.width, cellSize);
        rows = Math.divCell(dimension.height, cellSize);
        
        array = new List[collumns * rows];
        
        for(int i = 0; i < array.length; i++) {
            array[i] = new ArrayList<>();
        }
    }
    
    public void put(T ball) {
        int x = (int)ball.getPosition().getX() / cellSize;
        int y = (int)ball.getPosition().getY() / cellSize;
        int index = y * collumns + x;
        
        List<T> list = array[index];
        list.add(ball);
        ++size;
    }
    
    public List<T> get(Point2D point) {
        int x = (int)point.getX() / cellSize;
        int y = (int)point.getY() / cellSize;
        int index = y * collumns + x;
        
        return array[index];
    }
    
    public int size() {
        return size;
    }
    
    private Dimension dimension;
    
    private int cellSize;
    
    private int collumns;
    
    private int rows;
    
    private int size;
    
    private List<T>[] array;
}
