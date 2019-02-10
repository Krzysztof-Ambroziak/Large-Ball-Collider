package pl.cba.reallygrid.lbc.swing.util;

import pl.cba.reallygrid.lbc.swing.LargeBallColliderApplication;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Preferences {
    public static int getInteger(PreferencesKey key) {
        return PREFERENCES.getInt(key.name(), key.getValue());
    }
    
    public static void putInteger(PreferencesKey key, int value) {
        PREFERENCES.putInt(key.name(), value);
    }
    
    private Preferences() {
    }
    
    private static final java.util.prefs.Preferences PREFERENCES;
    
    static final int POSITION_X_DEFAULT_VALUE;
    
    static final int POSITION_Y_DEFAULT_VALUE;
    
    static final int CANVAS_WIDTH_DEFAULT_VALUE;
    
    static final int CANVAS_HEIGHT_DEFAULT_VALUE;
    
    static final int SIDE_PANEL_WIDTH_DEFAULT_VALUE;
    
    static final int SIDE_PANEL_HEIGHT_DEFAULT_VALUE;
    
    static {
        PREFERENCES = java.util.prefs.Preferences.userNodeForPackage(LargeBallColliderApplication.class);
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        CANVAS_WIDTH_DEFAULT_VALUE = 800;
        CANVAS_HEIGHT_DEFAULT_VALUE = CANVAS_WIDTH_DEFAULT_VALUE;
        
        SIDE_PANEL_WIDTH_DEFAULT_VALUE = 200;
        SIDE_PANEL_HEIGHT_DEFAULT_VALUE = CANVAS_HEIGHT_DEFAULT_VALUE;
        
        POSITION_X_DEFAULT_VALUE = (screenSize.width - (CANVAS_WIDTH_DEFAULT_VALUE + SIDE_PANEL_WIDTH_DEFAULT_VALUE)) / 2;
        POSITION_Y_DEFAULT_VALUE = (screenSize.height - CANVAS_HEIGHT_DEFAULT_VALUE) / 2;
    }
}
