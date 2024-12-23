package layout;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends ScreenUtil {
    protected JPanel mainContentPanel;
    protected JPanel customerInfoPanel;
    protected JPanel notificationsPanel;

    MainScreen() {
        super("Guckor Bike Rental - Login", 1400, 800);
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
        JPanel upperPanel = createRoundedPanel(new Color(68, 68, 68));
        upperPanel.setLayout(new BorderLayout(20,10));
        upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon("C:/Users/Domin/IdeaProjects/Rowerowo/src/rentallogo150.png"));
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
        JPanel formPanel = new JPanel(new BorderLayout(10, 0));
        formPanel.setOpaque(false);

        // Lewy panel menu
        JPanel leftPanel = createLeftPanel();

        // Centralny panel treści
        mainContentPanel = createRoundedPanel(new Color(68, 68, 68));
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.add(new JLabel("Treść centralna", SwingConstants.CENTER), BorderLayout.CENTER);

        formPanel.add(leftPanel, BorderLayout.WEST);   // Lewy panel
        formPanel.add(mainContentPanel, BorderLayout.CENTER); // Centralny panel

        return formPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = createRoundedPanel(new Color(68, 68, 68));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setLayout(new GridLayout(7, 1, 20, 10));
        leftPanel.setPreferredSize(new Dimension(centralPanelDimension.width / 4, 100));

        leftPanel.add(new MenuOption("Ustawienia", new ImageIcon("C:/Users/Domin/IdeaProjects/Rowerowo/src/settingsicon.jpg")).getPanel());
        leftPanel.add(new MenuOption("Wyszukiwarka", new ImageIcon("C:/Users/Domin/IdeaProjects/Rowerowo/src/searchicon.jpg")).getPanel());
        leftPanel.add(new MenuOption("Strona Główna", new ImageIcon("C:/Users/Domin/IdeaProjects/Rowerowo/src/homepage.jpg")).getPanel());
        leftPanel.add(new MenuOption("Statystyki", new ImageIcon("C:/Users/Domin/IdeaProjects/Rowerowo/src/staticon.jpg")).getPanel());

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
