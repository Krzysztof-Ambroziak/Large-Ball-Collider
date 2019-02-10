package pl.cba.reallygrid.lbc.swing.util;

public enum PreferencesKey {
    POSITION_X(Preferences.POSITION_X_DEFAULT_VALUE),
    POSITION_Y(Preferences.POSITION_Y_DEFAULT_VALUE),
    CANVAS_WIDTH(Preferences.CANVAS_WIDTH_DEFAULT_VALUE),
    CANVAS_HEIGHT(Preferences.CANVAS_HEIGHT_DEFAULT_VALUE),
    SIDE_PANEL_WIDTH(Preferences.SIDE_PANEL_WIDTH_DEFAULT_VALUE),
    SIDE_PANEL_HEIGHT(Preferences.SIDE_PANEL_HEIGHT_DEFAULT_VALUE);
    
    PreferencesKey(int DEFAULT_VALUE) {
        this.DEFAULT_VALUE = DEFAULT_VALUE;
    }
    
    public int getValue() {
        return DEFAULT_VALUE;
    }
    
    private final int DEFAULT_VALUE;
}
