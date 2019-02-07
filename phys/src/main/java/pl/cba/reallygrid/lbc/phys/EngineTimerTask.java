package pl.cba.reallygrid.lbc.phys;

import pl.cba.reallygrid.lbc.phys.model.Model;

import java.util.TimerTask;

public class EngineTimerTask extends TimerTask {
    EngineTimerTask(Model model) {
        this.model = model;
    }
    
    @Override
    public void run() {
        move();
        checkCollision();
    }
    
    private void move() {
    }
    
    private void checkCollision() {
    }
    
    private final Model model;
}
