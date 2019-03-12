package pl.cba.reallygrid.lbc.phys;

import pl.cba.reallygrid.lbc.phys.model.DynamicBall;
import pl.cba.reallygrid.lbc.phys.model.DynamicBallHelper;
import pl.cba.reallygrid.lbc.phys.model.Model;
import pl.cba.reallygrid.util.Pair;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Engine {
    public void addBall(DynamicBall ball) {
        model.add(ball);
    }
    
    public List<DynamicBall> getBalls(int x, int y) {
        return model.getPairs().stream()
                .filter(pair -> (x - pair.getFirst().getPosition().getX()) * (x - pair.getFirst().getPosition().getX()) + (y - pair.getFirst().getPosition().getY()) * (y - pair.getFirst().getPosition().getY()) <= pair.getFirst().getRadius() * pair.getFirst().getRadius())
                .map(Pair::getFirst)
                .collect(Collectors.toList());
    }
    
    public void setDimension(Dimension dimension) {
        model.setDimension(dimension);
    }
    
    public void start() {
        prepare();
        run();
    }
    
    private void prepare() {
        model.getPairs().sort(Comparator.comparingInt(o -> o.getSecond().getId()));
        task = new EngineTimerTask(model);
        timer = new Timer("Animation: #" + animCounter.getAndIncrement());
    }
    
    private void run() {
        task.init();
        timer.scheduleAtFixedRate(task, 0L, 10L);
    }
    
    public void cancel() {
        timer.cancel();
        timer.purge();
    }
    
    public void draw(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(Pair<DynamicBall, DynamicBallHelper> pair : model.getPairs()) {
            int size = (int)Math.round(2 * pair.getFirst().getRadius());
            ((Graphics2D)g).setTransform(pair.getSecond().getTransform());
            g.drawOval(0, 0, size, size);
        }
    }
    
    private static final AtomicInteger animCounter = new AtomicInteger();
    
    private Model model = new Model();
    
    private Timer timer;
    
    private EngineTimerTask task;
}
