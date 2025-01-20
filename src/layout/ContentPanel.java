package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.rental.RentalServices;
import dataclass.vehicle.SingleTrackVehicle;
import dataclass.vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContentPanel extends JPanel implements Screen {
    JLabel typeLabel;
    JLabel idLabel;
    JLabel brandLabel;
    JLabel isRentedLabel;
    JPanel batteryLevelPanel;
    SingleTrackVehicle vehicle;
    JButton rentButton;

    public ContentPanel(Vehicle vehicle) {
        this.vehicle = (SingleTrackVehicle) vehicle;
        this.setOpaque(false);
        this.setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(1, 6, 10, 10));
        this.setPreferredSize(new Dimension(0, 80));

        isRentedLabel = createLabel("", 18);
        brandLabel = createLabel("", 18);
        typeLabel = createLabel("", 18);
        idLabel = createLabel("", 18);

        if (vehicle.isElectric()) {
            batteryLevelPanel = createRoundedPanel(Colors.DARK_BLUE.getColor());
            batteryLevelPanel.setLayout(new BorderLayout(10, 10));
        } else {
            batteryLevelPanel = null;
        }

        rentButton = createRoundedButton("Wypożycz", Colors.DARK_BLUE_HOVER.getColor(), Color.WHITE, new Font("San-Sferis", Font.BOLD, 20));
        this.add(idLabel);
        this.add(typeLabel);
        this.add(brandLabel);
        this.add(isRentedLabel);
        if (batteryLevelPanel != null) {
            this.add(batteryLevelPanel);
        }
        this.add(rentButton);

        setupButtonActions();
        updateContent();
    }

    private void setupButtonActions() {
        rentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!vehicle.isRented()) {
                    RentalServices.getInstance().rentVehicle(vehicle.getVehicleId(), CentralDatabase.getInstance().getCurrentUser());
                    rentButton.setVisible(false);
                    updateContent();
                     MainScreen.instance.createSearchPanel();
                }
            }
        });
    }

    public void updateContent() {
        // Aktualizacja etykiet
        idLabel.setText(vehicle.getVehicleId());
        isRentedLabel.setText(vehicle.isRented() ? "Zajęty" : "Wolny");
        brandLabel.setText(vehicle.getVehicleModel());
        typeLabel.setText(vehicle.getClass().getSimpleName());

        // Aktualizacja przycisku
        if (vehicle.isRented()) {
            rentButton.setVisible(false);
        } else {
            rentButton.setText("Wypożycz");
            rentButton.setVisible(true);
        }

        // Aktualizacja panelu baterii
        if (batteryLevelPanel != null) {
            batteryLevelPanel.removeAll();
            createBarPanel();
        }

        // Przeładowanie widoku
        revalidate();
        repaint();
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
        batteryLevelBar.setBackground(Colors.BACKGROUND.getColor());
        setBatteryBar(batteryLevelBar);

        JLabel batteryLevelLabel = createLabel(batteryLevelBar.getString(), 18);
        batteryLevelPanel.add(batteryLevelBar, BorderLayout.CENTER);
        batteryLevelPanel.add(batteryLevelLabel, BorderLayout.WEST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Colors.DARK_BLUE_ACTIVE.getColor());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g);
        g2d.dispose();
    }

}

