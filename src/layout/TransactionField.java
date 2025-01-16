package layout;

import dataclass.vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;

public class TransactionField extends ContentField{
    JLabel dataLabel;
    JLabel isActive;
    JLabel modelLabel;

    public TransactionField(String labelText, Color backgroundColor, Dimension preferredSize) {
        super(labelText, backgroundColor, preferredSize);
    }
//    public TransactionField(Vehicle vehicle, Dimension preferredSize) {
//        this.label = vehicle.getVehicleType();
//
//    }
}
