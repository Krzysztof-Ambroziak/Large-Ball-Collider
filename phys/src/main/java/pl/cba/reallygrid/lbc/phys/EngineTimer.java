package pl.cba.reallygrid.lbc.phys;

import java.util.Timer;
import java.util.TimerTask;

class EngineTimer {
    EngineTimer(EngineTimerTask task) {
        timer = new Timer("TimerEngine: #" + count++);
        this.task = task;
    }
    
    void start() {
        timer.scheduleAtFixedRate(task, 0L, );
    }
    
    void stop() {
        timer.cancel();
        timer.purge();
    }
    
    private static int count = 0;
    
    private final Timer timer;
    
    private final TimerTask task;
}
