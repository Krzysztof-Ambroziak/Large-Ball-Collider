package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.phys.Engine;

import java.awt.Graphics;

public class Renderer {
    public Renderer(Engine engine) {
        this.engine = engine;
    }
    
    void drawCanvas(Graphics g) {
        engine.draw(g);
    }
    
    private Engine engine;
}
