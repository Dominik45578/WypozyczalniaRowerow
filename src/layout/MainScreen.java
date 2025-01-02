package layout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class MainScreen extends ScreenUtil {
    protected JPanel mainContentPanel;
    protected JPanel customerInfoPanel;
    protected JPanel notificationsPanel;

    MainScreen() {
        super("Guckor Bike Rental - Login", 1500, 800);
    }

    @Override
    protected void createScreenContent() {
        centralPanel.setLayout(new BorderLayout(10, 10));

        // Tworzenie i dodawanie paneli
        JPanel upperPanel = createUpperPanel();
        JPanel formPanel = createFormPanel();

        centralPanel.add(upperPanel, BorderLayout.NORTH);  // Górny panel
        centralPanel.add(formPanel, BorderLayout.CENTER);  // Centralny panel
    }

    private JPanel createUpperPanel() {
        JPanel upperPanel = createRoundedPanel(new Color(68, 68, 68,0));
        upperPanel.setLayout(new BorderLayout(10,10));
        //upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon("src/rentallogo150.png"));
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBackground(new Color(33, 42, 49));
        JPanel logoPanel = createRoundedPanel(new Color(33, 42, 49));
        logoPanel.add(logoLabel);
        upperPanel.add(logoPanel, BorderLayout.WEST);

        // Powiadomienia
        notificationsPanel = createRoundedPanel(new Color(33, 42, 49));
        notificationsPanel.setLayout(new BorderLayout());
        JLabel notificationsLabel = createLabel("Powiadomienia", new Font("SansSerif", Font.BOLD, 20), new Color(0, 173, 181));
        notificationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notificationsPanel.add(notificationsLabel, BorderLayout.CENTER);
        upperPanel.add(notificationsPanel, BorderLayout.CENTER);

        // Informacje o kliencie
        customerInfoPanel = createRoundedPanel(new Color(33, 42, 49));
        customerInfoPanel.setLayout(new BorderLayout());
        customerInfoPanel.setPreferredSize(new Dimension(400, 100));
        JLabel customerLabel = createLabel("Klient: Jan Kowalski", new Font("SansSerif", Font.PLAIN, 18), Color.WHITE);
        customerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customerInfoPanel.add(customerLabel, BorderLayout.CENTER);
        upperPanel.add(customerInfoPanel, BorderLayout.EAST);

        return upperPanel;
    }

   private JPanel createFormPanel() {
    JPanel formPanel = new JPanel(new BorderLayout(10, 10));
    formPanel.setOpaque(false);

    // Lewy panel menu
    JPanel leftPanel = createLeftPanel();

    // Centralny panel treści
    mainContentPanel = createRoundedPanel(new Color(68, 68, 68));
    mainContentPanel.setLayout(new BorderLayout()); // Użyj BorderLayout dla pełnego wypełnienia
    mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Panel z GridLayout do przechowywania ContentPanel
    JPanel vehicleContent = new JPanel(new GridLayout(0, 1, 10, 10));
    vehicleContent.setBackground(Colors.BACKGROUND.getColor());
    // Dynamiczna liczba wierszy
    for (int i = 0; i < 40; i++) { // Dodaj przykładowe ContentPanel
        vehicleContent.add(new ContentPanel(2*i));
    }

    // Ustawienie JScrollPane
    JScrollPane vehicleScrollPanel = new JScrollPane(vehicleContent){
        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rysowanie zaokrąglonego prostokąta
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    }};
    vehicleScrollPanel.setOpaque(false);
    vehicleScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    vehicleScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    vehicleScrollPanel.setBackground(Colors.DARK_BLUE.getColor());
    vehicleScrollPanel.setBorder(null);
    JScrollBar verticalScrollBar = vehicleScrollPanel.getVerticalScrollBar();

    verticalScrollBar.setBackground(Colors.BACKGROUND.getColor());
    verticalScrollBar.setForeground(Colors.DARK_BLUE.getColor());
    verticalScrollBar.setUnitIncrement(20);
    verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
     verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Colors.DARK_BLUE.getColor(); // Kolor suwaka
                 // Kolor tła paska
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setPreferredSize(new Dimension(0, 0)); // Usuwa przyciski góra/dół
                button.setVisible(false);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setPreferredSize(new Dimension(0, 0)); // Usuwa przyciski góra/dół
                button.setVisible(false);
                return button;
            }

        });
    //JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();

    //vehicleScrollPanel.setBorder(BorderFactory.createEmptyBorder()); // Usuń obramowanie JScrollPane

    // Dodanie JScrollPane do mainContentPanel
    mainContentPanel.add(vehicleScrollPanel, BorderLayout.CENTER);

    // Dodanie paneli do formPanel
    formPanel.add(leftPanel, BorderLayout.WEST);   // Lewy panel
    formPanel.add(mainContentPanel, BorderLayout.CENTER); // Centralny panel

    return formPanel;
}

    private JPanel createLeftPanel() {
        JPanel leftPanel = createRoundedPanel(new Color(68, 68, 68,0));

        //leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setLayout(new GridLayout(7, 1, 20, 10));
        leftPanel.setPreferredSize(new Dimension(centralPanelDimension.width / 4, 100));

        leftPanel.add(new MenuOption("Ustawienia", new ImageIcon("src/settingsicon.jpg")).getPanel());
        leftPanel.add(new MenuOption("Wyszukiwarka", new ImageIcon("src/searchicon.jpg")).getPanel());
        leftPanel.add(new MenuOption("Strona Główna", new ImageIcon("src/homepage.jpg")).getPanel());
        leftPanel.add(new MenuOption("Statystyki", new ImageIcon("src/staticon.jpg")).getPanel());
        leftPanel.setOpaque(false);
        return leftPanel;
    }

    public void updateMainContentPanel(JPanel newContent) {
        mainContentPanel.removeAll();
        mainContentPanel.add(newContent, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    public void updateCustomerInfoPanel(JPanel newCustomerInfo) {
        customerInfoPanel.removeAll();
        customerInfoPanel.add(newCustomerInfo, BorderLayout.CENTER);
        customerInfoPanel.revalidate();
        customerInfoPanel.repaint();
    }

    public void updateNotificationsPanel(JPanel newNotifications) {
        notificationsPanel.removeAll();
        notificationsPanel.add(newNotifications, BorderLayout.CENTER);
        notificationsPanel.revalidate();
        notificationsPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.showScreen();
        });
    }
}
