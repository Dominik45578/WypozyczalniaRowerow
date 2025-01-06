package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.user.Customer;
import dataclass.user.PrivateCustomer;
import dataclass.user.User;

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
    protected JPanel toogle;
    protected JLayeredPane layers;
    protected int lastElement;
    protected Customer customer;
    protected enum MenuOptionPosition {
    HOMEPAGE(0),
        SEARCH(1),
    HISTORY(2),
            STATS(3),
        SETTINGS(4);

    private final int index;

    MenuOptionPosition(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}



    MainScreen() {
        super("Guckor Bike Rental - Login", 1700, 900);
        lastElement = -1;
          CentralDatabase.getInstance().setUser(new PrivateCustomer("001","Dominik"," ","Koralik","0429265555",
               "31-866","Kraków","Skarżyńskiego 9","dkkd3046@gmail.com"));
         customer = (Customer) CentralDatabase.getInstance().getUser();
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


        leftPanel.add(homeOption);
        leftPanel.add(searchOption);
        leftPanel.add(historyOption);
        leftPanel.add(statsOption);
        leftPanel.add(settingsOption);

        leftPanel.setOpaque(false);
    }

    private void addMenuListener(JPanel option, Runnable action) {
        option.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
        option.setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
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
        JPanel statsPanel =  createRoundedPanel(Colors.BACKGROUND.getColor());
        changeLastElement(MenuOptionPosition.STATS.getIndex());
        statsPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(statsPanel);
}

    public void createHistoryPanel() {
        JPanel historyPanel = new JPanel();
        changeLastElement(MenuOptionPosition.HISTORY.getIndex());

        historyPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(historyPanel);
    }


public void createSettingsPanel() {
    // Create the main settings panel
    JPanel settingsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
    changeLastElement(MenuOptionPosition.SETTINGS.getIndex());
    settingsPanel.setPreferredSize(layers.getPreferredSize());
    settingsPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    settingsPanel.setLayout(new GridLayout(6, 2, 10, 10));

    // Get the current customer;

    // Create EditableFields for customer information
    EditableField firstNameField = new EditableField("First Name", customer.getFirstName());
    EditableField lastNameField = new EditableField("Last Name", customer.getLastName());
    EditableField addressField = new EditableField("Address", customer.getAddress());
    EditableField emailField = new EditableField("Email", customer.getEmail());
    EditableField cityField = new EditableField("City", customer.getCity());
    EditableField postalCodeField = new EditableField("Postal Code", customer.getPostalCode());
    EditableField rentedItemsField = new EditableField("Rented Items", String.valueOf(customer.getNumberOfRentedItems()));

    firstNameField.getButton().addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Tworzenie głównego panelu toggle
        toogle = null;
        toogle = createRoundedPanel(new Color(0, 0, 0, 0));
        toogle.setBounds(0, 0, layers.getPreferredSize().width, layers.getPreferredSize().height);
        toogle.setPreferredSize(layers.getPreferredSize());

        // Tworzenie panelu EditableTogglePanel
        EditableTogglePanel element = new EditableTogglePanel(
            "Zmień email",
            "E-mail",
            customer.getEmail()
        );
        element.setPreferredSize(layers.getPreferredSize());

        // Obsługa przycisku zamknięcia
        element.getCloseLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Usuwanie panelu z warstwy
                toogle.remove(element);
                layers.remove(toogle);
                layers.revalidate();
                layers.repaint();
            }
        });

        // Dodanie panelu elementu do panelu toggle
        toogle.add(element);
        layers.add(toogle, JLayeredPane.PALETTE_LAYER);
        layers.revalidate();
        layers.repaint();

        // Dodanie obsługi akcji dla przycisku "Zapisz zmiany"
        element.addMenuListener(() -> {
            String news = element.getEnteredText(); // Pobieranie nowego e-maila
            JPanel error = element.getContentPanel(); // Panel błędów
            boolean haveError = false;

            // Walidacja e-maila
            if (!news.contains("@")) {
                error.add(createLabel("E-mail nie zawiera znaku @", 20));
                haveError = true;
            }
            if (!news.contains(".")) {
                error.add(createLabel("E-mail nie zawiera znaku .", 20));
                haveError = true;
            }
            if (news.length() < 8) {
                error.add(createLabel("E-mail musi mieć przynajmniej 8 znaków", 20));
                haveError = true;
            }

            // Jeśli brak błędów, zaktualizuj e-mail i odśwież ustawienia
            if (!haveError) {
                customer.setEmail(news);
                toogle.remove(element);
                layers.remove(toogle);
                createSettingsPanel();
            }

            // Odśwież panel błędów
            error.revalidate();
            error.repaint();
        });
    }
});


    // Add EditableFields to the settings panel
    settingsPanel.add(firstNameField);
    settingsPanel.add(lastNameField);
    settingsPanel.add(addressField);
    settingsPanel.add(emailField);
    settingsPanel.add(cityField);
    settingsPanel.add(postalCodeField);
    settingsPanel.add(rentedItemsField);

    // Update the layer content with the settings panel
    updateLayerContent(settingsPanel);
}

    public void createSearchPanel() {
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

    public void createStatsPanel() {
        JPanel statsPanel =  createRoundedPanel(Colors.BACKGROUND.getColor());
        changeLastElement(MenuOptionPosition.STATS.getIndex());
        statsPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(statsPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.showScreen();
        });
    }
     public void changeLastElement(int newElement) {
        if (lastElement == -1 || lastElement == newElement) {
            return;
        }
        // Dezaktywuj poprzedni element
        MenuOption oldOption = (MenuOption) leftPanel.getComponent(lastElement);
        oldOption.setIsActive(false);
        oldOption.setBackground(Colors.DARK_BLUE.getColor());

        // Aktywuj nowy element
        MenuOption newOption = (MenuOption) leftPanel.getComponent(newElement);
        newOption.setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
        newOption.setIsActive(true);

        lastElement = newElement;
    }

}
