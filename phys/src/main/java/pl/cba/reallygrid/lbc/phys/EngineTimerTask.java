package pl.cba.reallygrid.lbc.phys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.cba.reallygrid.lbc.phys.math.Vector2D;
import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.phys.model.DynamicBallHelper;
import pl.cba.reallygrid.lbc.phys.model.Model;
import pl.cba.reallygrid.util.Pair;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.TimerTask;

public class EngineTimerTask extends TimerTask {
    EngineTimerTask(Model model) {
        this.model = model;
    }
    
    void init() {
        long timestamp = System.nanoTime();
        model.setCurrentTimestamp(timestamp);
        model.getPairs().forEach(tuple -> tuple.getSecond().setStartTime(timestamp));
    }
    
    @Override
    public void run() {
        prepare();
        moveBalls();
        checkCollision();
    }
    
    private void prepare() {
        initImpactQueue();
        updateTimestamp();
    }
    
    private void initImpactQueue() {
        model.getPairs().forEach(model::addToImpactQueue);
    }
    
    private void updateTimestamp() {
        model.setPreviousTimestamp(model.getCurrentTimestamp());
        model.setCurrentTimestamp(System.nanoTime());
    }
    
    private void moveBalls() {
        final long currentTimestamp = model.getCurrentTimestamp();
        model.getPairs().forEach(tuple -> {
            DynamicBall ball = tuple.getFirst();
            DynamicBallHelper ballHelper = tuple.getSecond();
            double oldX = ball.getPosition().getX();
            double oldY = ball.getPosition().getY();
            
            long deltaTime = currentTimestamp - ballHelper.getStartTime();
            move(tuple, deltaTime);
            ballHelper.setTranslate(ball.getPosition(), ball.getRadius());
            model.realloc(tuple, oldX, oldY);
        });
    }
    
    private void move(Pair<DynamicBall, DynamicBallHelper> tuple, long deltaTime) {
        Point2D position = tuple.getSecond().getStartPosition();
        Vector2D velocity = tuple.getSecond().getStartVelocity();
        tuple.getFirst().setPosition(position.getX() + (deltaTime * velocity.getX()) * SLOWING_FACTOR,
                                     position.getY() + (deltaTime * velocity.getY()) * SLOWING_FACTOR);
    }
    
    private void checkCollision() {
        Pair<DynamicBall, DynamicBallHelper> tuple;
        while((tuple = model.pool()) != null) {
            Iterator<Pair<DynamicBall, ?>> neighbours = model.neighbours(tuple.getFirst().getPosition());
            while(neighbours.hasNext()) {
                Pair<DynamicBall, DynamicBallHelper> neighbourTuple = (Pair<DynamicBall, DynamicBallHelper>)neighbours.next();
                if(assertCollision(tuple, neighbourTuple)) {
                    impact(tuple, neighbourTuple);
                    setLastImpactBall(tuple, neighbourTuple);
                    model.addToImpactQueue(tuple);
                    model.addToImpactQueue(neighbourTuple);
                }
            }
            if(assertWallCollision(tuple.getFirst())) {
                impactWall(tuple);
            }
        }
    }
    
    private boolean assertWallCollision(DynamicBall ball) {
        Point2D pos = ball.getPosition();
        double rad = ball.getRadius();
        return (pos.getX() <= rad)
                || (pos.getY() <= rad)
                || (pos.getX() + rad >= model.getWidth())
                || (pos.getY() + rad >= model.getHeight());
    }
    
    private boolean assertCollision(Pair<DynamicBall, DynamicBallHelper> tuple1,
                                    Pair<DynamicBall, DynamicBallHelper> tuple2) {
        return assertSameBall(tuple1.getFirst(), tuple2.getFirst())
                && (assertLastImpact(tuple1.getFirst(), tuple2.getSecond().getLastImpactBall())
                || assertLastImpact(tuple2.getFirst(), tuple1.getSecond().getLastImpactBall()))
                && assertImpact(tuple1.getFirst(), tuple2.getFirst());
    }
    
    private boolean assertSameBall(DynamicBall ball1, DynamicBall ball2) {
        return ball1 != ball2;
    }
    
    private boolean assertLastImpact(DynamicBall ball1, DynamicBall ball2) {
        return ball1 != ball2;
    }
    
    private boolean assertImpact(DynamicBall ball1, DynamicBall ball2) {
        Point2D position1 = ball1.getPosition();
        Point2D position2 = ball2.getPosition();
        double rr = ball1.getRadius() + ball2.getRadius();
        double dx = position1.getX() - position2.getX();
        double dy = position1.getY() - position2.getY();
        
        return dx * dx + dy * dy <= rr * rr;
    }
    
    private void impact(Pair<DynamicBall, DynamicBallHelper> tuple1, Pair<DynamicBall, DynamicBallHelper> tuple2) {
        DynamicBall ball1 = tuple1.getFirst();
        DynamicBall ball2 = tuple2.getFirst();
        DynamicBallHelper ballHelper1 = tuple1.getSecond();
        DynamicBallHelper ballHelper2 = tuple2.getSecond();
        long timeBefore = model.getPreviousTimestamp();
        long timeAfter = model.getCurrentTimestamp();
        double oldX1 = ball1.getPosition().getX();
        double oldY1 = ball1.getPosition().getY();
        double oldX2 = ball2.getPosition().getX();
        double oldY2 = ball2.getPosition().getY();
        
        while(assertQuantumTime(timeBefore, timeAfter)) {
            long pTime = (timeBefore + timeAfter) / 2;
            long deltaTime1 = pTime - ballHelper1.getStartTime();
            long deltaTime2 = pTime - ballHelper2.getStartTime();
            
            move(tuple1, deltaTime1);
            move(tuple2, deltaTime2);
            if(assertImpact(ball1, ball2)) {
                timeAfter = pTime;
            }
            else {
                timeBefore = pTime;
            }
        }
        LOGGER.info("Impact between #" + ballHelper1.getId() + " and #" + ballHelper2.getId());
        resetVelocity(ball1, ball2);
        resetPositionAndTime(ballHelper1, ball1.getPosition(), ball1.getVelocity(), timeAfter);
        resetPositionAndTime(ballHelper2, ball2.getPosition(), ball2.getVelocity(), timeAfter);
        long deltaTime = model.getCurrentTimestamp() - timeAfter;
        move(tuple1, deltaTime);
        move(tuple2, deltaTime);
        ballHelper1.setTranslate(ball1.getPosition(), ball1.getRadius());
        ballHelper2.setTranslate(ball2.getPosition(), ball2.getRadius());
        model.realloc(tuple1, oldX1, oldY1);
        model.realloc(tuple2, oldX2, oldY2);
    }
    
    private void impactWall(Pair<DynamicBall, DynamicBallHelper> tuple) {
        DynamicBall ball = tuple.getFirst();
        DynamicBallHelper ballHelper = tuple.getSecond();
        Point2D pos = ball.getPosition();
        double oldX = pos.getX();
        double oldY = pos.getY();
        double rad = ball.getRadius();
        if(pos.getX() <= rad) {
            impactNorthWall(ball, ballHelper);
        }
        if(pos.getY() <= rad) {
            impactSouthWall(ball, ballHelper);
        }
        if(pos.getX() + rad >= model.getWidth()) {
            impactEastWall(ball, ballHelper);
        }
        if(pos.getY() + rad >= model.getHeight()) {
            impactWestWall(ball, ballHelper);
        }
        model.realloc(tuple, oldX, oldY);
        model.addToImpactQueue(tuple);
    }
    
    private void impactNorthWall(DynamicBall ball, DynamicBallHelper ballHelper) {
        double rad = ball.getRadius();
        Point2D pos = ball.getPosition();
        Vector2D vel = ball.getVelocity();
        ball.setPosition(2 * rad - pos.getX(), pos.getY());
        ball.setVelocity(-vel.getX(), vel.getY());
        resetPositionAndTime(ballHelper, pos, vel, model.getCurrentTimestamp());
        ballHelper.setTranslate(pos, rad);
    }
    
    private void impactSouthWall(DynamicBall ball, DynamicBallHelper ballHelper) {
        double rad = ball.getRadius();
        Point2D pos = ball.getPosition();
        Vector2D vel = ball.getVelocity();
        ball.setPosition(pos.getX(), 2 * rad - pos.getY());
        ball.setVelocity(vel.getX(), -vel.getY());
        resetPositionAndTime(ballHelper, pos, vel, model.getCurrentTimestamp());
        ballHelper.setTranslate(pos, rad);
    }
    
    private void impactEastWall(DynamicBall ball, DynamicBallHelper ballHelper) {
        double rad = ball.getRadius();
        Point2D pos = ball.getPosition();
        Vector2D vel = ball.getVelocity();
        ball.setPosition(2 * (model.getWidth() - rad) - pos.getX(), pos.getY());
        ball.setVelocity(-vel.getX(), vel.getY());
        resetPositionAndTime(ballHelper, pos, vel, model.getCurrentTimestamp());
        ballHelper.setTranslate(pos, rad);
    }
    
    private void impactWestWall(DynamicBall ball, DynamicBallHelper ballHelper) {
        double rad = ball.getRadius();
        Point2D pos = ball.getPosition();
        Vector2D vel = ball.getVelocity();
        ball.setPosition(pos.getX(), 2 * (model.getHeight() - rad) - pos.getY());
        ball.setVelocity(vel.getX(), -vel.getY());
        resetPositionAndTime(ballHelper, pos, vel, model.getCurrentTimestamp());
        ballHelper.setTranslate(pos, rad);
    }
    
    private static boolean assertQuantumTime(long timeBefore, long timeAfter) {
        return timeAfter - timeBefore > 1;
    }
    
    private void resetPositionAndTime(DynamicBallHelper ball, Point2D position, Vector2D velocity, long timeStart) {
        ball.setStartPosition(position);
        ball.setStartVelocity(velocity);
        ball.setStartTime(timeStart);
    }
    
    private void resetVelocity(DynamicBall ball1, DynamicBall ball2) {
        Vector2D velocity1 = ball1.getVelocity();
        Vector2D velocity2 = ball2.getVelocity();
        double mass1 = ball1.getMass();
        double mass2 = ball2.getMass();
        double deltaVx = velocity1.getX() - velocity2.getX();
        double deltaVy = velocity1.getY() - velocity2.getY();
        double deltaPx = ball1.getPosition().getX() - ball2.getPosition().getX();
        double deltaPy = ball1.getPosition().getY() - ball2.getPosition().getY();
        double dotProduct = Vector2D.dotProduct(deltaVx, deltaVy, deltaPx, deltaPy);
        double lengthSq = Vector2D.lengthSq(deltaPx, deltaPy);
        double c = (2 * dotProduct) / ((mass1 + mass2) * lengthSq);
        
        ball1.setVelocity(velocity1.getX() - mass2 * c * deltaPx, velocity1.getY() - mass2 * c * deltaPy);
        ball2.setVelocity(velocity2.getX() + mass1 * c * deltaPx, velocity2.getY() + mass1 * c * deltaPy);
    }
    
    private void setLastImpactBall(Pair<DynamicBall, DynamicBallHelper> pair1, Pair<DynamicBall, DynamicBallHelper> pair2) {
        pair1.getSecond().setLastImpactBall(pair2.getFirst());
        pair2.getSecond().setLastImpactBall(pair1.getFirst());
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EngineTimerTask.class);
    
    /**
     * Korekcja odmierzania czasu przystosowująca czułość w nanosekundach na czułość w sekundach.
     */
    private static final double SLOWING_FACTOR = 0.000_000_001;
    
    private final Model model;
}
