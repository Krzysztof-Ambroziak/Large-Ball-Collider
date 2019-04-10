package pl.cba.reallygrid.lbc.swing.gui;

import pl.cba.reallygrid.lbc.phys.math.Vector2D;
import pl.cba.reallygrid.lbc.phys.model.DynamicBall;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class BallInformation extends JPanel {
    BallInformation() {
        super(new GridBagLayout());
        massFld.setColumns(11);
        radiusFld.setColumns(11);
        
        add(new JLabel("Ball id:"), new GBC(0, 0).anchor(GBC.WEST).insets(0, 0, 0, 6));
        add(idLbl, new GBC(1, 0).anchor(GBC.WEST));
        
        add(new JLabel("Mass:"), new GBC(0, 1).anchor(GBC.WEST).insets(2, 0, 2, 6));
        add(massFld, new GBC(1, 1).anchor(GBC.WEST).insets(2, 0, 2, 0));
        
        add(new JLabel("Radius:"), new GBC(0, 2).anchor(GBC.WEST).insets(2, 0, 2, 6));
        add(radiusFld, new GBC(1, 2).anchor(GBC.WEST).insets(2, 0, 2, 0));
        
        add(new JLabel("Position:"), new GBC(0, 3).anchor(GBC.WEST).insets(2, 0, 2, 6));
        add(positionLbl, new GBC(1, 3).anchor(GBC.WEST).insets(2, 0, 2, 0));
        
        add(new JLabel("Velocity:"), new GBC(0, 4).anchor(GBC.WEST).insets(2, 0, 2, 6));
        add(velocityLbl, new GBC(1, 4).anchor(GBC.WEST).insets(2, 0, 2, 0));
        
        add(saveBtn, new GBC(0, 5, 2, 1).insets(2, 0, 0, 0));
    }
    
    void setActiveBall(DynamicBall activeBall, int id) {
        idLbl.setText(Integer.toString(id));
        massFld.setText(Double.toString(activeBall.getMass()));
        radiusFld.setText(Double.toString(activeBall.getRadius()));
        Point2D pos = activeBall.getPosition();
        String posX = Double.toString(Math.round(pos.getX() * 100) / 100.0);
        String posY = Double.toString(Math.round(pos.getY() * 100) / 100.0);
        positionLbl.setText('(' + posX + ", " + posY + ')');
        Vector2D vel = activeBall.getVelocity();
        String velX = Double.toString(Math.round(vel.getX() * 100) / 100.0);
        String velY = Double.toString(Math.round(vel.getY() * 100) / 100.0);
        velocityLbl.setText('[' + velX + ", " + velY + ']');
    }
    
    void addAction(ActionListener saveAction) {
        saveBtn.addActionListener(saveAction);
    }
    
    public String getMass() {
        return massFld.getText();
    }
    
    public String getRadius() {
        return radiusFld.getText();
    }
    
    private JLabel idLbl = new JLabel();
    
    private JTextField massFld = new JTextField();
    
    private JTextField radiusFld = new JTextField();
    
    private JLabel positionLbl = new JLabel();
    
    private JLabel velocityLbl = new JLabel();
    
    private AbstractButton saveBtn = new JButton("Save");
}
