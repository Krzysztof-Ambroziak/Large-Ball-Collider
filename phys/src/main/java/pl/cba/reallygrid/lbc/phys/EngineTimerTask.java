package pl.cba.reallygrid.lbc.phys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.cba.reallygrid.lbc.phys.math.Vector2D;
import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.phys.model.DynamicBallHelper;
import pl.cba.reallygrid.lbc.phys.model.Model;
import pl.cba.reallygrid.lbc.phys.util.Math;
import pl.cba.reallygrid.util.Pair;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.TimerTask;

public class EngineTimerTask extends TimerTask {
    EngineTimerTask(Model model) {
        this.model = model;
    }
    
    void init() {
        width = model.getWidth();
        height = model.getHeight();
        long currentTime = System.currentTimeMillis();
        model.getPairs().forEach(pair -> pair.getSecond().setStartTime(currentTime));
    }
    
    @Override
    public void run() {
        updateTimestamp();
        move();
        checkCollision();
    }
    
    private void updateTimestamp() {
        currentTimestamp = System.currentTimeMillis();
    }
    
    private void move() {
        for(Pair<DynamicBall, DynamicBallHelper> pair : model.getPairs()) {
            Point2D startPosition = pair.getSecond().getPosition();
            long deltaTime = currentTimestamp - pair.getSecond().getStartTime();
            
            DynamicBall ball = pair.getFirst();
            Vector2D velocity = ball.getVelocity();
            Point2D currentPosition = ball.getPosition();
            
            // s(t) = s_0 + t*v
            double oldX = currentPosition.getX();
            double oldY = currentPosition.getY();
            double newX = Math.normalize(startPosition.getX() + (deltaTime * velocity.getX()) * SLOWING_FACTOR, width);
            double newY = Math.normalize(startPosition.getY() + (deltaTime * velocity.getY()) * SLOWING_FACTOR, height);
            currentPosition.setLocation(newX, newY);
            pair.getSecond().setTranslate(currentPosition, ball.getRadius());
            model.realloc(pair, oldX, oldY);
        }
    }
    
    private void checkCollision() {
        for(Pair<DynamicBall, DynamicBallHelper> pair : model.getPairs()) {
            int id = pair.getSecond().getId();
            Iterator<Pair<? extends DynamicBall, ?>> neighbours = model.neighbours(pair.getFirst().getPosition());
            while(neighbours.hasNext()) {
                Pair<DynamicBall, DynamicBallHelper> neighbour = (Pair<DynamicBall, DynamicBallHelper>)neighbours.next();
                if(id < neighbour.getSecond().getId() && assertImpact(pair.getFirst(), neighbour.getFirst())) {
                    impact(pair, neighbour);
                }
            }
        }
    }
    
    private boolean assertImpact(DynamicBall ball1, DynamicBall ball2) {
        Point2D position1 = ball1.getPosition();
        Point2D position2 = ball2.getPosition();
        double rr = ball1.getRadius() + ball2.getRadius();
        double dx = position1.getX() - position2.getX();
        double dy = position1.getY() - position2.getY();
        
        return dx * dx + dy * dy <= rr * rr;
    }
    
    private void impact(Pair<DynamicBall, DynamicBallHelper> pair1, Pair<DynamicBall, DynamicBallHelper> pair2) {
        LOGGER.info("Impact between #" + pair1.getSecond().getId() + " and #" + pair2.getSecond().getId());
        resetPositionAndTime(pair1.getSecond(), pair1.getFirst().getPosition());
        resetPositionAndTime(pair2.getSecond(), pair2.getFirst().getPosition());
        resetVelocity(pair1.getFirst(), pair2.getFirst());
    }
    
    private void resetPositionAndTime(DynamicBallHelper ball, Point2D position) {
        ball.setPosition(position);
        ball.setStartTime(currentTimestamp);
    }
    
    private void resetVelocity(DynamicBall ball1, DynamicBall ball2) {
        double deltaVx = ball1.getVelocity().getX() - ball2.getVelocity().getX();
        double deltaVy = ball1.getVelocity().getY() - ball2.getVelocity().getY();
        double deltaPx = ball1.getPosition().getX() - ball2.getPosition().getX();
        double deltaPy = ball1.getPosition().getY() - ball2.getPosition().getY();
        double dotProduct = Vector2D.dotProduct(deltaVx, deltaVy, deltaPx, deltaPy);
        double lengthSq = Vector2D.lengthSq(deltaPx, deltaPy);
        double c = (2 * dotProduct) / ((ball1.getMass() + ball2.getMass()) * lengthSq);
        
        ball1.setVelocity(ball1.getVelocity().getX() - ball2.getMass() * c * deltaPx, ball1.getVelocity().getY() - ball2.getMass() * c * deltaPy);
        ball2.setVelocity(ball2.getVelocity().getX() + ball1.getMass() * c * deltaPx, ball2.getVelocity().getY() + ball1.getMass() * c * deltaPy);
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EngineTimerTask.class);
    
    /**
     * Korekcja odmierzania czasu przystosowująca czułość w milisekundach na czułość w sekundach.
     */
    private static final double SLOWING_FACTOR = 0.001;
    
    private final Model model;
    
    private long currentTimestamp;
    
    private int width;
    
    private int height;
}
