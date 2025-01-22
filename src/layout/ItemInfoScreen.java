package layout;

import dataclass.vehicle.SingleTrackVehicle;
import dataclass.vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;

public class ItemInfoScreen extends JFrame implements Screen{
    private JPanel mainContent;

    ItemInfoScreen(Vehicle vehicle){
        mainContent = new JPanel(new GridLayout(0, 2, 10, 10));
        this.setTitle(vehicle.getVehicleType() + " " + vehicle.getVehicleId() + " " + vehicle.getVehicleModel());
        this.setMinimumSize(new Dimension(400,400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("src/rentallogo1.png").getImage());
        this.add(mainContent);
        this.setContentPane(mainContent);

        // Ustawienie rozmiaru okna JFrame
        this.setVisible(true);
    }
}
