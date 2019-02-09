package pl.cba.reallygrid.lbc.phys;

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
    }
    
    void stop() {
        executorService.shutdown();
    }
    
    private ExecutorService executorService;
    
    private final EngineTimerTask task;
}
