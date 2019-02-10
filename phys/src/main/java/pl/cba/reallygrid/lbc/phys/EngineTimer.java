package pl.cba.reallygrid.lbc.phys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class EngineTimer {
    EngineTimer(EngineTimerTask task) {
        executorService = Executors.newSingleThreadExecutor();
        this.task = task;
    }
    
    void start() {
        task.init();
        executorService.execute(task);
        LOGGER.info("Timer started");
    }
    
    void stop() {
        task.stop();
        executorService.shutdown();
        LOGGER.info("Timer stopped.");
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EngineTimer.class);
    
    private ExecutorService executorService;
    
    private final EngineTimerTask task;
}
