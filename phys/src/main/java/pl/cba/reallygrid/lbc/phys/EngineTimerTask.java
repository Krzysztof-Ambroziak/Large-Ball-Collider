package pl.cba.reallygrid.lbc.phys;

import pl.cba.reallygrid.lbc.phys.math.DynamicBall;
import pl.cba.reallygrid.lbc.phys.math.Vector2D;
import pl.cba.reallygrid.lbc.phys.model.Model;
import pl.cba.reallygrid.lbc.phys.model.Pair;

import java.awt.geom.Point2D;

public class EngineTimerTask implements Runnable {
    EngineTimerTask(Model model) {
        this.model = model;
    }
    
    @Override
    public void run() {
        running = true;
        init();
        loop();
    }
    
    void stop() {
        running = false;
    }
    
    void init() {
        long currentTime = System.currentTimeMillis();
        model.getPairs().forEach(pair -> pair.getFirst().setTime(currentTime));
    }
    
    private void loop() {
        while(running) {
            updateTimestamp();
            move();
            checkCollision();
            sleep();
        }
    }
    
    private void updateTimestamp() {
        currentTimestamp = System.currentTimeMillis();
    }
    
    private void move() {
        for(Pair<Model.StartTimePosition, DynamicBall> pair : model.getPairs()) {
            Point2D startPosition = pair.getFirst().getPosition();
            Vector2D velocity = pair.getSecond().getVelocity();
            DynamicBall ball = pair.getSecond();
            Point2D currentPosition = ball.getPosition();
            long time = pair.getFirst().getTime();
            
            long deltaTime = currentTimestamp - time;
            // s(t) = s_0 + t*v
            double newX = startPosition.getX() + (deltaTime * velocity.getX()) * SLOWING_FACTOR;
            double newY = startPosition.getY() + (deltaTime * velocity.getY()) * SLOWING_FACTOR;
            // todo tutaj sprawdzić czy zachodzi potrzeba aktualizacji ArrayMap!
            currentPosition.setLocation(newX, newY);
        }
    }
    
    private void checkCollision() {
    }
    
    private void sleep() {
        try {
            Thread.sleep(1);
        }
        catch(InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private static final double SLOWING_FACTOR = 0.001;
    
    private final Model model;
    
    private boolean running = false;
    
    private long currentTimestamp;
}
