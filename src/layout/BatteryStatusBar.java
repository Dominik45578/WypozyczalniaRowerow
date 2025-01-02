package layout;

import javax.swing.*;
import java.awt.*;

public class BatteryStatusBar {
    private JFrame frame;
    private JProgressBar progressBar;

    public BatteryStatusBar() {
        frame = new JFrame("Battery Status");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        // Inicjalizacja paska postępu
        progressBar = new JProgressBar(0, 100); // Zakres od 0 do 100
        progressBar.setStringPainted(true); // Wyświetlanie wartości procentowej
        progressBar.setValue(75); // Domyślna wartość

        // Dodanie paska do okna
        frame.add(progressBar, BorderLayout.CENTER);

        // Symulacja zmiany poziomu baterii
        simulateBatteryChange();
    }

    // Metoda do ustawiania koloru paska w zależności od poziomu baterii
    private void updateBatteryStatus(int batteryLevel) {
        progressBar.setValue(batteryLevel);

        if (batteryLevel > 50) {
            progressBar.setForeground(Color.GREEN);
        } else if (batteryLevel > 25) {
            progressBar.setForeground(Color.ORANGE);
        } else {
            progressBar.setForeground(Color.RED);
        }
    }

    // Symulacja zmiany poziomu baterii
    private void simulateBatteryChange() {
        new Timer(1000, e -> {
            int currentValue = progressBar.getValue();
            if (currentValue > 0) {
                updateBatteryStatus(currentValue - 5); // Zmniejsz poziom baterii co sekundę
            } else {
                ((Timer) e.getSource()).stop(); // Zatrzymaj timer, gdy bateria osiągnie 0%
            }
        }).start();
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BatteryStatusBar batteryStatusBar = new BatteryStatusBar();
            batteryStatusBar.show();
        });
    }
}
