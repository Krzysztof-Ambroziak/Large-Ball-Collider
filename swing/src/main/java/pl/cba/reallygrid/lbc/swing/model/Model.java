package pl.cba.reallygrid.lbc.swing.model;

import pl.cba.reallygrid.lbc.phys.model.DynamicBall;

public class Model {
    public DynamicBall getActiveBall() {
        return activeBall;
    }
    
    public int getActiveBallId() {
        return activeBallId;
    }
    
    public void setActiveBall(DynamicBall activeBall, int id) {
        this.activeBall = activeBall;
        activeBallId = id;
    }
    
    private DynamicBall activeBall;
    
    private int activeBallId;
}
