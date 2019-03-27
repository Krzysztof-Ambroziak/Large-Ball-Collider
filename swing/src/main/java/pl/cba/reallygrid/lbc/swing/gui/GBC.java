package pl.cba.reallygrid.lbc.swing.gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

class GBC extends GridBagConstraints {
    GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }
    
    GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }
    
    GBC fill(int fill) {
        this.fill = fill;
        return this;
    }
    
    GBC anchor(int anchor) {
        this.anchor = anchor;
        return this;
    }
    
    GBC insets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }
    
    GBC weight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }
}
