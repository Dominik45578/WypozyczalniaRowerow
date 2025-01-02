package layout;

import dataclass.vehicle.Scooter;
import dataclass.vehicle.SingleTrackVehicle;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel implements Screen {
    JLabel typeLabel;
    JLabel brandLabel;
    JLabel isRentedLabel;
    JPanel batteryLevelPanel;
    SingleTrackVehicle vehicle;
    JButton rentButon;

    public ContentPanel(int value) {
        vehicle = new Scooter(value);
        this.setOpaque(false); // Ustawienie przezroczystości, aby zaokrąglone rogi były widoczne
        this.setBackground(Colors.DARK_BLUE.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(1, 5, 10, 10));

        if (!vehicle.isElectric()) {
            batteryLevelPanel = null;
        } else {
            batteryLevelPanel = createRoundedPanel(Colors.DARK_BLUE.getColor());
            batteryLevelPanel.setLayout(new BorderLayout(10, 10));
            createBarPanel();
        }

        isRentedLabel = createLabel(vehicle.isRented() ? "Zajęty" : "Wolny", 20);
        brandLabel = createLabel(vehicle.getVehicleModel(), 20);
        typeLabel = createLabel(vehicle.getClass().getSimpleName(), 20); // Użycie getSimpleName dla czytelności

        this.add(typeLabel);
        this.add(brandLabel);
        this.add(isRentedLabel);
        if (batteryLevelPanel != null) {
            this.add(batteryLevelPanel);
        }
        rentButon = createButton("Wypożycz", Color.BLACK, Color.WHITE, new Font("San-Sferis", Font.BOLD, 20));
        this.add(rentButon);
        if(vehicle.isRented()){
            rentButon.setVisible(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rysowanie zaokrąglonego prostokąta
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    }

    @Override
    public boolean isOpaque() {
        return false; // Panel nie jest całkowicie kryjący, aby widoczne były zaokrąglone rogi
    }

    private void setBatteryBar(JProgressBar batteryLevelBar) {
        int batteryLevel = vehicle.getBatteryLevel();
        if (batteryLevel < 0) {
            return;
        }
        if (batteryLevel > 50) {
            batteryLevelBar.setForeground(Color.GREEN);
        } else if (batteryLevel > 25) {
            batteryLevelBar.setForeground(Color.ORANGE);
        } else {
            batteryLevelBar.setForeground(Color.RED);
        }
        batteryLevelBar.setValue(batteryLevel);
    }

    private void createBarPanel() {
        JProgressBar batteryLevelBar = new JProgressBar(0, 100);
        batteryLevelBar.setPreferredSize(new Dimension(100, 10));
        batteryLevelBar.setBorderPainted(false);
       // batteryLevelBar.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        batteryLevelBar.setBackground(Colors.BACKGROUND.getColor());
        setBatteryBar(batteryLevelBar);

        JLabel batteryLevelLabel = createLabel(batteryLevelBar.getString(), 18);
        batteryLevelPanel.add(batteryLevelBar, BorderLayout.CENTER);
        batteryLevelPanel.add(batteryLevelLabel, BorderLayout.WEST);
    }


}
