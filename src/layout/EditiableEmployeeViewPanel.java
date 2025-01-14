package layout;

import dataclass.user.Employee;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.Map;

public class EditiableEmployeeViewPanel extends AbstractEditablePanel {
    private Map<String, Employee> employeeMap;
    private JPanel employeePanel;

    public EditiableEmployeeViewPanel(String title, String type, Map<String, Employee> employeeMap) {
        super(new Dimension(600, 600), title, type);
        this.employeeMap = employeeMap;

        // Sprawdzamy, czy mapa pracowników jest pusta
        if (employeeMap.isEmpty()) {
            contentPanel.add(createLabel("Brak pracowników do wyświetlenia", new Font("SanSerif", Font.BOLD, 20), Color.ORANGE));
            return;
        }

        // Ustawienia dla contentPanel
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tworzymy panel dla pracowników
        employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));
        employeePanel.setBackground(Colors.BACKGROUND.getColor());

        // Dodajemy panele dla każdego pracownika
        for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            Employee employee = entry.getValue();
            JPanel roundedPanel = createEmployeePanel(employee);
            employeePanel.add(roundedPanel);
            employeePanel.add(Box.createVerticalStrut(10)); // Dodajemy odstęp między panelami
        }

        // Tworzymy JScrollPane i konfigurujemy
        JScrollPane scrollPane = new JScrollPane(employeePanel);
        setScrollPane(scrollPane);

        // Dodajemy scrollPane do contentPanel
        contentPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // Tworzymy panel z danymi pracownika, używając createRoundedPanel
    private JPanel createEmployeePanel(Employee employee) {
        JPanel employeePanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
        employeePanel.setLayout(new BorderLayout(10, 10));
        employeePanel.setPreferredSize(new Dimension(500, 80));

        // Dodajemy dane pracownika
        employeePanel.add(new JLabel("ID: " + employee.getID()), BorderLayout.WEST);
        employeePanel.add(new JLabel("Imię: " + employee.getFirstName()), BorderLayout.EAST);

        return employeePanel;
    }

    @Override
    void addMenuListener(Runnable action) {
        // Implementacja nasłuchiwania menu (jeśli jest potrzebna)
    }

    @Override
    String getEnteredText() {
        return ""; // Zwracamy pusty tekst, jeśli nie ma potrzeby wprowadzać danych
    }

    @Override
    boolean validateData() {
        return false; // Walidacja danych, jeśli jest potrzebna
    }

    // Konfiguracja JScrollPane
    void setScrollPane(JScrollPane scrollPane) {
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBackground(Colors.DARK_BLUE.getColor());
    scrollPane.setBorder(null);

    JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
    verticalScrollBar.setBackground(Colors.BACKGROUND.getColor());
    verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(1,4,1,1));
    verticalScrollBar.setUnitIncrement(20);
    verticalScrollBar.setPreferredSize(new Dimension(5, 0)); // Ustawienie szerokości przewijaka
    verticalScrollBar.setUI(new BasicScrollBarUI() {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = Colors.DARK_BLUE.getColor(); // Kolor suwaka
            this.trackColor = Colors.BACKGROUND.getColor(); // Kolor tła przewijaka
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return invisibleScrollBarButton(); // Ukrycie przycisków góra/dół
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return invisibleScrollBarButton();
        }

        private JButton invisibleScrollBarButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setVisible(false);
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(thumbColor);
            g2.fillRoundRect(
                thumbBounds.x, thumbBounds.y,
                thumbBounds.width, thumbBounds.height,
                10, 10 // Promień zaokrąglenia
            );
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(trackColor);
            g2.fillRoundRect(
                trackBounds.x, trackBounds.y,
                trackBounds.width, trackBounds.height,
                10, 10 // Promień zaokrąglenia
            );
        }
    });
}

}
