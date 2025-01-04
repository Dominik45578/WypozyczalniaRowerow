package layout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainScreen extends ScreenUtil {
    protected JPanel mainContentPanel;
    protected JPanel customerInfoPanel;
    protected JPanel notificationsPanel;
    protected JPanel upperContentPanel;
    protected JPanel formPanel;
    protected JPanel leftPanel;
    protected JLayeredPane layers;

    MainScreen() {
        super("Guckor Bike Rental - Login", 1700, 900);
    }

    @Override
    protected void createScreenContent() {
        centralPanel.setLayout(new BorderLayout(10, 10));

        // Tworzenie i dodawanie paneli
        upperContentPanel = createUpperPanel();
        createFormPanel();

        centralPanel.add(upperContentPanel, BorderLayout.NORTH);  // Górny panel
        centralPanel.add(formPanel, BorderLayout.CENTER);  // Centralny panel
    }

    private JPanel createUpperPanel() {
        JPanel upperPanel = createRoundedPanel(new Color(68, 68, 68, 0));
        upperPanel.setLayout(new BorderLayout(10, 10));

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

    private void createFormPanel() {
        formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setOpaque(false);

        // Lewy panel menu
        createLeftPanel();
        mainContentPanel = createRoundedPanel(new Color(68, 68, 68));
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        layers = new JLayeredPane();
        layers.setLayout(null);
        layers.setPreferredSize(new Dimension((int) (centralPanelDimension.getWidth() - leftPanel.getPreferredSize().getWidth() - 10),
                (int) (centralPanelDimension.height - upperContentPanel.getPreferredSize().height) - 10));

        // Ustawienie bounds dla mainContentPanel
        mainContentPanel.setBounds(0, 0, (int) (centralPanelDimension.getWidth() - leftPanel.getPreferredSize().getWidth() - 10),
                (int) (centralPanelDimension.height - upperContentPanel.getPreferredSize().height) - 10);
        layers.add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);

        formPanel.add(leftPanel, BorderLayout.WEST);
        formPanel.add(layers, BorderLayout.CENTER);
    }

    private void createLeftPanel() {
        leftPanel = createRoundedPanel(new Color(68, 68, 68, 0));
        leftPanel.setLayout(new GridLayout(7, 1, 20, 10));
        leftPanel.setPreferredSize(new Dimension(centralPanelDimension.width / 4 - 50, 100));

        MenuOption settingsOption = new MenuOption("Ustawienia", new ImageIcon("src/settingsicon.jpg"));
        MenuOption searchOption = new MenuOption("Wyszukiwarka", new ImageIcon("src/searchicon.jpg"));
        MenuOption homeOption = new MenuOption("Strona Główna", new ImageIcon("src/homepage.jpg"));
        MenuOption statsOption = new MenuOption("Statystyki", new ImageIcon("src/staticon.jpg"));
        MenuOption historyOption = new MenuOption("Historia", new ImageIcon("src/historyicon.png"));

        addMenuListener(settingsOption, this::createSettingsPanel);
        addMenuListener(searchOption, this::createSearchPanel);
        addMenuListener(homeOption, this::createHomePage);
        addMenuListener(statsOption, this::createStatsPanel);
        addMenuListener(historyOption, this::createHistoryPanel);

        leftPanel.add(settingsOption.getPanel());
        leftPanel.add(searchOption.getPanel());
        leftPanel.add(homeOption.getPanel());
        leftPanel.add(statsOption.getPanel());
        leftPanel.add(historyOption.getPanel());

        leftPanel.setOpaque(false);
    }

    private void addMenuListener(MenuOption option, Runnable action) {
        option.getPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
        option.getPanel().setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
    }

//    private void updateLayerContent(JPanel newContent) {
//        layers.removeAll();
//        newContent.setBounds(0, 0, layers.getPreferredSize().width, layers.getPreferredSize().height);
//        layers.add(newContent, JLayeredPane.DEFAULT_LAYER);
//        layers.revalidate();
//        layers.repaint();
//    }
    private void updateLayerContent(JPanel newContent) {
    // Usunięcie obecnej zawartości warstwy
    layers.removeAll();

    // Tworzenie panelu tła dla loadera
    JPanel loaderBackground = new JPanel();
    loaderBackground.setBackground(Colors.BACKGROUND.getColor());
    loaderBackground.setBounds(0, 0, layers.getPreferredSize().width, layers.getPreferredSize().height);
    loaderBackground.setLayout(new GridBagLayout()); // Wyśrodkowanie etykiety

    // Tworzenie etykiety loadera
    JLabel loadingLabel = new JLabel("Ładowanie...", SwingConstants.CENTER);
    loadingLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    loadingLabel.setForeground(Color.WHITE); // Biała czcionka

    // Dodanie etykiety do panelu tła
    loaderBackground.add(loadingLabel);

    // Dodanie loadera do warstwy
    layers.add(loaderBackground, JLayeredPane.DEFAULT_LAYER);
    layers.revalidate();
    layers.repaint();

    // Użycie SwingWorker do załadowania panelu w tle
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() {
            try {
                // Symulacja opóźnienia (np. ładowania danych)
                Thread.sleep(0); // Możesz to usunąć lub dostosować
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return null;
        }

        @Override
        protected void done() {
            // Usunięcie loadera i dodanie nowej zawartości
            layers.removeAll();
            newContent.setBounds(0, 0, layers.getPreferredSize().width, layers.getPreferredSize().height);
            layers.add(newContent, JLayeredPane.DEFAULT_LAYER);
            layers.revalidate();
            layers.repaint();
        }
    };

    // Uruchomienie SwingWorker
    worker.execute();
}

   public void createHomePage() {
    // Centralny panel treści
    mainContentPanel.removeAll();

    // Panel z GridLayout do przechowywania ContentPanel
    JPanel vehicleContent = new JPanel(new GridLayout(0, 1, 10, 10));
    vehicleContent.setBackground(Colors.BACKGROUND.getColor());

    // Dynamiczna liczba wierszy
    for (int i = 0; i < 200; i++) { // Dodaj przykładowe ContentPanel
        vehicleContent.add(new ContentPanel(2 * i));
    }

    // Ustawienie JScrollPane
    JScrollPane vehicleScrollPanel = new JScrollPane(vehicleContent) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Rysowanie zaokrąglonego prostokąta
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        }
    };
    vehicleScrollPanel.setOpaque(false);
    vehicleScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    vehicleScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    vehicleScrollPanel.setBackground(Colors.DARK_BLUE.getColor());
    vehicleScrollPanel.setBorder(null);

    JScrollBar verticalScrollBar = vehicleScrollPanel.getVerticalScrollBar();
    verticalScrollBar.setBackground(Colors.BACKGROUND.getColor());
    verticalScrollBar.setForeground(Colors.DARK_BLUE.getColor());
    verticalScrollBar.setUnitIncrement(20);
    verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    verticalScrollBar.setUI(new BasicScrollBarUI() {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = Colors.DARK_BLUE.getColor(); // Kolor suwaka
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

    // Dodanie JScrollPane do mainContentPanel
    mainContentPanel.add(vehicleScrollPanel, BorderLayout.CENTER);

    // Aktualizacja zawartości warstwy
    updateLayerContent(mainContentPanel);
}

    public void createHistoryPanel() {
        JPanel historyPanel = new JPanel();

        historyPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(historyPanel);
    }

    public void createSettingsPanel() {
    // Tworzenie panelu z ustawieniami
    JPanel settingsPanel = mainContentPanel;
    settingsPanel.setPreferredSize(layers.getPreferredSize());

    // Dodanie tła z półprzezroczystością
    JPanel backgroundPanel = createRoundedPanel(null);
    backgroundPanel.setBackground(new Color(0, 0, 0, 150)); // Czarne tło z przezroczystością
    backgroundPanel.setBounds(0, 0, layers.getWidth(), layers.getHeight());

    // Tworzymy GridBagLayout z 2 kolumnami
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    // Ustawienie rozkładu kolumn w GridBagLayout
    gridBagLayout.columnWidths = new int[] {0, layers.getPreferredSize().width / 2 + 100}; // Pierwsza kolumna ma szerokość 0, druga kolumna z ustawieniami
    gridBagLayout.rowHeights = new int[] {layers.getHeight()};
    backgroundPanel.setLayout(gridBagLayout);

    // Tworzenie okienka ustawień
    JPanel settingsWindow = createRoundedPanel(Colors.DARK_BLUE.getColor());
    settingsWindow.setPreferredSize(new Dimension(layers.getPreferredSize().width / 2 + 100, layers.getPreferredSize().height - 60)); // Wymiary okienka ustawień
    settingsWindow.setLayout(new BorderLayout());

    // Możesz dodać komponenty do okienka ustawień, np. etykiety, pola tekstowe itp.
    JLabel label = createLabel("Ustawienia", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);
    settingsWindow.add(label, BorderLayout.CENTER);

    // Dodanie okienka ustawień do tła w prawej kolumnie
    gbc.gridx = 1;  // Ustawienie w drugiej kolumnie
    gbc.gridy = 0;  // Pierwszy wiersz
    gbc.anchor = GridBagConstraints.EAST;  // Wyrównanie do prawej strony
    gbc.insets = new Insets(0, 0, 0, 10);  // Odstęp od prawej krawędzi

    backgroundPanel.add(settingsWindow, gbc);

    // Dodanie tła z okienkiem do warstwy
    layers.add(backgroundPanel, JLayeredPane.PALETTE_LAYER);
    layers.revalidate();
    layers.repaint();
}

    public void createSearchPanel() {
        JPanel searchPanel =  createRoundedPanel(Colors.BACKGROUND.getColor());
        searchPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(searchPanel);
    }

    public void createStatsPanel() {
        JPanel statsPanel =  createRoundedPanel(Colors.BACKGROUND.getColor());
        statsPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(statsPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.showScreen();
        });
    }
}
