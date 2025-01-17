package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.fileoperations.FileDataManager;
import dataclass.rental.RentalServices;
import dataclass.rental.RentalTransaction;
import dataclass.user.*;
import dataclass.vehicle.Bike;
import dataclass.vehicle.Vehicle;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MainScreen extends ScreenUtil {
    protected JPanel mainContentPanel;
    protected JPanel customerInfoPanel;
    protected JPanel notificationsPanel;
    protected JPanel upperContentPanel;
    protected JPanel formPanel;
    protected JPanel leftPanel;
    protected JPanel toggle;
    EditableTogglePanel element;
    protected JLayeredPane layers;
    protected int lastElement;
    protected User customer;

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
        super("Guckor Bike Rental - Login", 1400, 800);
        lastElement = -1;
//        Customer c = new BusinessCustomer("BC001", "Dominik", "Michał", "Koralik", "0429265555",
//                "31-866", "Kraków", "Skarżyńskiego 9", "dkkd3046@gmail.com", "Guckor", "BC001",
//                "Warszawska 24", "31-234", "Kraków");
//        CentralDatabase.getInstance().setCurrentUser(c);
        customer = CentralDatabase.getInstance().getCurrentUser();
//         FileDataManager<Customer> customerManager = new FileDataManager<>("customer" , User.BUSINESS_C_PREFIX);
//        try {
//            CentralDatabase.getInstance().registerManager(Customer.class, customerManager);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        CentralDatabase.getInstance().addObject(Customer.class, c.getID(), c);
//        RentalTransaction t = new RentalTransaction(new Bike("B001", "Default" , "Crtoss Hexagon 2" , false),c);
//        c.getRentedHistory().put(t.getTransactionID(),t);
    }

    @Override
    protected void createScreenContent(Users user) {
        centralPanel.setLayout(new BorderLayout(10, 10));
        //CentralDatabase.getInstance().setCurrentUser(CentralDatabase.getInstance().FilterUser("dkkd3046@gmail.com"));
        if (CentralDatabase.getInstance().getCurrentUser() == null) {
            new WelcomeScreen().showScreen(null);
            return;
        } else {
            // Tworzenie i dodawanie paneli
            upperContentPanel = createUpperPanel();
            createFormPanel();

            centralPanel.add(upperContentPanel, BorderLayout.NORTH);  // Górny panel
            centralPanel.add(formPanel, BorderLayout.CENTER);  // Centralny panel

        }


    }

    @Override
    protected void addListener(Component component, Runnable action) {

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
        JLabel customerLabel = createLabel(CentralDatabase.getInstance().getCurrentUser().getFirstName(), new Font("SansSerif", Font.PLAIN, 18), Color.WHITE);
        customerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customerInfoPanel.add(createLabel(CentralDatabase.getInstance().getCurrentUser().getID(), 20), BorderLayout.NORTH);
        customerInfoPanel.add(customerLabel, BorderLayout.CENTER);
        upperPanel.add(customerInfoPanel, BorderLayout.EAST);
        JButton logOut = createRoundedButton("<- Wyloguj się ", 20);
        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        CentralDatabase.getInstance().save(User.class, customer.getID());
                        CentralDatabase.getInstance().load(User.class, customer.getID());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    ScreenUtil screen = new WelcomeScreen();
                    screen.showScreen(null);

                    CentralDatabase.getInstance().setCurrentUser(null);

                });
            }
        });
        customerInfoPanel.add(logOut, BorderLayout.SOUTH);

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
        MenuOption historyOption = new MenuOption("Transakcje", new ImageIcon("src/historyicon.png"));

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
    private void updateLayerContent(Component newContent) {
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
        JPanel statsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        changeLastElement(MenuOptionPosition.STATS.getIndex());
        statsPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(statsPanel);
    }

    public void createHistoryPanel() {
        JPanel historyPanel = new JPanel();
        historyPanel.setPreferredSize(layers.getPreferredSize());
        historyPanel.setLayout(new GridLayout(30, 1, 10, 10));

        changeLastElement(MenuOptionPosition.HISTORY.getIndex());

        // Pobieramy historię wypożyczeń użytkownika
        Map<String , RentalTransaction>transactions = CentralDatabase.getInstance().getCurrentUser().getRentedHistory();

        // Iterujemy przez transakcje i wyświetlamy je w panelu
        for (Map.Entry<String , RentalTransaction> entry: transactions.entrySet()){
            RentalTransaction transaction = entry.getValue();
            JPanel transactionPanel = new JPanel();
            transactionPanel.setLayout(new GridLayout(1, 4));

            // Wyświetlanie danych w zależności od roli użytkownika
            if (customer instanceof BusinessCustomer) {
                JLabel usernameLabel = new JLabel(transaction.getUser().getFirstName());
                transactionPanel.add(usernameLabel);
            }

            JLabel bikeBrandLabel = new JLabel(transaction.getVehicle().getVehicleModel());
            transactionPanel.add(bikeBrandLabel);

            JLabel startDateLabel = new JLabel(transaction.getRentalStart().toString());
            transactionPanel.add(startDateLabel);

            JLabel endDateLabel = new JLabel(transaction.getRentalEnd() != null ? transaction.getRentalEnd().toString() : "N/A");
            transactionPanel.add(endDateLabel);

            historyPanel.add(transactionPanel);
        }

        updateLayerContent(historyPanel);
    }


    public void createSettingsPanel() {
        toggle = createRoundedPanel(new Color(0, 0, 0, 0));
        toggle.setBounds(0, 0, layers.getPreferredSize().width, layers.getPreferredSize().height);
        toggle.setPreferredSize(layers.getPreferredSize());
        JPanel settingsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        changeLastElement(MenuOptionPosition.SETTINGS.getIndex());
        settingsPanel.setPreferredSize(layers.getPreferredSize());
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        settingsPanel.setLayout(new GridLayout(0, 2, 10, 10));


        if (customer instanceof PrivateCustomer) {
            // PrivateCustomer customer = (PrivateCustomer) CentralDatabase.getInstance().getUser();
            createSettingsPanelForPrivate(settingsPanel);
        } else if (customer instanceof BusinessCustomer) {
            // BusinessCustomer customer = (BusinessCustomer) CentralDatabase.getInstance().getUser();
            createSettingsPanelForBuissnes(settingsPanel, (BusinessCustomer) customer);
        }

    }

    public void createSettingsPanelForPrivate(JPanel settingsPanel) {
        PrivateCustomer customer = (PrivateCustomer) CentralDatabase.getInstance().getCurrentUser();

        EditableField idField = new EditableField("ID", customer.getID());
        EditableField firstNameField = new EditableField("Imię", customer.getFirstName());
        EditableField secondNameField = new EditableField("Drugie imię", customer.getSecondName());
        EditableField lastNameField = new EditableField("Nazwisko", customer.getLastName());
        EditableField addressField = new EditableField("Adres", customer.getAddress());
        EditableField cityField = new EditableField("Miejscowość", customer.getCity());
        EditableField postalCodeField = new EditableField("Kod Pocztowy", customer.getPostalCode());
        EditableField emailField = new EditableField("Email", customer.getEmail());
        EditableField phoneNumberField = new EditableField("Numer telefonu ", customer.getPhoneNumber());
        EditableField passwordField = new EditableField("Hasło", ".".repeat(customer.getPassword().length()));
        EditableField rentedItemsField = new EditableField("Ilość wypożyczonych pojazdów (obecnie)", String.valueOf(customer.getNumberOfRentedItems()));
        EditableField transactionHistoryField = new EditableField("łączna ilość wypożyczonych pojazdów", String.valueOf(customer.getRentedHistory().size()));
        idField.getButton().setVisible(false);
        rentedItemsField.getButton().setVisible(false);
        transactionHistoryField.getButton().setVisible(false);


        addFieldListener(firstNameField, "Imię", customer::getFirstName, customer::setFirstName);
        addFieldListener(secondNameField, "Drugie imię", customer::getSecondName, customer::setSecondName);
        addFieldListener(lastNameField, "Nazwisko", customer::getLastName, customer::setLastName);
        addFieldListener(addressField, "Adres", customer::getAddress, customer::setAddress);
        addFieldListener(emailField, "E-mail", customer::getEmail, customer::setEmail);
        addFieldListener(phoneNumberField, "Numer Telefonu", customer::getPhoneNumber, customer::setPhoneNumber);
        addFieldListener(cityField, "Miasto", customer::getCity, customer::setCity);
        addFieldListener(postalCodeField, "Kod pocztowy", customer::getPostalCode, customer::setPostalCode);
        addFieldListener(passwordField, "Hasło", customer::getPassword, customer::setPassword);
        //addFieldListener(rentedItemsField, "Wynajęte przedmioty", () -> String.valueOf(customer.getNumberOfRentedItems()), value -> customer.setNumberOfRentedItems(Integer.parseInt(value)));
        CentralDatabase.getInstance().setCurrentUser(customer);


        // Add EditableFields to the settings panel`
        JLabel ownerInfoPanel = createLabel("Informacje o właścicielu", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        settingsPanel.add(ownerInfoPanel);
        settingsPanel.add(new JLabel());
        settingsPanel.add(idField);
        settingsPanel.add(firstNameField);
        settingsPanel.add(secondNameField);
        settingsPanel.add(lastNameField);
        settingsPanel.add(cityField);
        settingsPanel.add(addressField);
        settingsPanel.add(postalCodeField);
        settingsPanel.add(phoneNumberField);
        settingsPanel.add(emailField);
        settingsPanel.add(passwordField);
        JLabel vehicleInfoPanel = createLabel("Informacje o wypożyczeniach", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        settingsPanel.add(vehicleInfoPanel);
        settingsPanel.add(new JLabel());

        settingsPanel.add(rentedItemsField);
        settingsPanel.add(transactionHistoryField);

        updateLayerContent(settingsPanel);
    }

    public void createSettingsPanelForBuissnes(JPanel settingsPanel, BusinessCustomer company) {

        CentralDatabase.getInstance().setCurrentUser(company);
        CentralDatabase.getInstance().updateUser(User.class, company);
        company.addEmployee("E001", Employee.createNewEmployee());
        company.addEmployee("E002", Employee.createNewEmployee());
        company.addEmployee("E003", Employee.createNewEmployee());
        company.addEmployee("E004", Employee.createNewEmployee());
        company.addEmployee("E005", Employee.createNewEmployee());
        company.addEmployee("E006", Employee.createNewEmployee());
        company.addEmployee("E007", Employee.createNewEmployee());
        settingsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JLabel infoLabel = createLabel("Informacje o firmie", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        EditableField companyNameField = new EditableField("Nazwa Firmy", company.getCompanyName());
        EditableField companyIdField = new EditableField("ID Firmy", company.getCompanyId());
        EditableField addressField = new EditableField("Adres Firmy", company.getCompanyAddress());
        EditableField postalCodeField = new EditableField("Kod Pocztowy", company.getCompanyPostalCode());
        EditableField cityField = new EditableField("Miasto", company.getCompanyCity());
        EditableField phoneNumberField = new EditableField("Number Telefonu", company.getCompanyPhoneNumber());
        EditableField companyEmail = new EditableField("Adres e-mail", company.getCompanyEmail());
        EditableField employeesField = new EditableField("Pracownicy", String.valueOf(company.getEmployees().size()));

        addFieldListener(companyNameField, "Nazwa Firmy", company::getCompanyName, company::setCompanyName);
        addFieldListener(phoneNumberField, "Numer Telefonu", company::getCompanyName, company::setCompanyName);
        addFieldListener(companyEmail, "Adres email", company::getCompanyEmail, company::setCompanyEmail);
        //addFieldListener(companyIdField, "ID Firmy", company::getCompanyId, company::setCompanyId);
        addFieldListener(addressField, "Adres", company::getCompanyAddress, company::setCompanyAddress);
        addFieldListener(postalCodeField, "Kod Pocztowy", company::getCompanyPostalCode, company::setCompanyPostalCode);
        addFieldListener(cityField, "Miasto", company::getCompanyCity, company::setCompanyCity);
        addFieldMapListener(employeesField, "Pracownicy", company::getEmployees,
                company::addEmployee, true);


        settingsPanel.add(infoLabel);
        settingsPanel.add(new JLabel()); // Spacer
        settingsPanel.add(companyIdField);
        settingsPanel.add(companyNameField);
        settingsPanel.add(phoneNumberField);
        settingsPanel.add(companyEmail);
        settingsPanel.add(addressField);
        settingsPanel.add(postalCodeField);
        settingsPanel.add(cityField);
        settingsPanel.add(employeesField);

        JLabel ownerInfoPanel = createLabel("Informacje o właścicielu", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        settingsPanel.add(new JLabel());
        settingsPanel.add(ownerInfoPanel);
        settingsPanel.add(new JLabel());


        Customer customer = (Customer) company;
        EditableField ownerCompanyNameField = new EditableField("Imię", customer.getFirstName() + customer.getSecondName());
        EditableField ownerCompanyLastNameField = new EditableField("Nazwisko ", customer.getLastName());
        EditableField ownerCompanyIdField = new EditableField("ID użytkownika", customer.getID());
        EditableField ownerCompanyPhoneField = new EditableField("Numer telefonu ", customer.getID());
        EditableField ownerCompanyEmailField = new EditableField("Adress e-mail", customer.getEmail());
        EditableField ownerCompanyAddressField = new EditableField("Adres ", customer.getAddress());
        EditableField ownerCompanyCityField = new EditableField("Miasto", customer.getCity());
        EditableField ownerCompanyPostalCodeField = new EditableField("Kod pocztowy", customer.getPostalCode());

        addFieldListener(ownerCompanyNameField, "Imię", customer::getFirstName, customer::setFirstName);
        addFieldListener(ownerCompanyLastNameField, "Nazwisko ", customer::getLastName, customer::setLastName);
        addFieldListener(ownerCompanyAddressField, "Adres ", customer::getAddress, customer::setAddress);
        addFieldListener(ownerCompanyCityField, "Miasto", customer::getCity, customer::setCity);
        addFieldListener(ownerCompanyPostalCodeField, "Kod pocztowy", customer::getPostalCode, customer::setPostalCode);
        addFieldListener(ownerCompanyEmailField, "E-mail", customer::getEmail, customer::setEmail);
        addFieldListener(ownerCompanyPhoneField, "Numer Telefonu", customer::getPhoneNumber, customer::setPhoneNumber);

        settingsPanel.add(ownerCompanyIdField);
        settingsPanel.add(ownerCompanyNameField);
        settingsPanel.add(ownerCompanyLastNameField);
        settingsPanel.add(phoneNumberField);
        settingsPanel.add(ownerCompanyEmailField);


        settingsPanel.add(ownerCompanyAddressField);
        settingsPanel.add(ownerCompanyCityField);
        settingsPanel.add(ownerCompanyPostalCodeField);
//         JScrollPane settingsScroll = new JScrollPane(settingsPanel);
//         setScrollPane(settingsScroll);
//        //settingsScroll.setOpaque(false);

        updateLayerContent(settingsPanel);

    }
    private JPanel performSearch(String brand, String type, String electric, String status) {
    // Dynamiczna liczba wierszy
    @SuppressWarnings("unchecked")
    Map<String, Vehicle> vehicleMap = (Map<String, Vehicle>) CentralDatabase.getInstance().getCachedData().get(Vehicle.class);

    List<Vehicle> filteredVehicles = new ArrayList<>();

    for (Map.Entry<String, Vehicle> entry : vehicleMap.entrySet()) {
        Vehicle vehicle = entry.getValue();
        if (vehicle == null) continue;

        boolean matches = true;

        // Filtruj po marce
        if (!brand.isEmpty() && !vehicle.getVehicleModel().toLowerCase().contains(brand.toLowerCase())) {
            matches = false;
        }

        // Filtruj po typie pojazdu
        if (!type.equals("Wszystko") && !vehicle.getVehicleType().contains(type)) {
            matches = false;
        }

        // Filtruj po elektryczności
        if (electric.equals("Elektryczny") && !vehicle.isElectric()) {
            matches = false;
        } else if (electric.equals("Nie elektryczny") && vehicle.isElectric()) {
            matches = false;
        }

        // Filtruj po statusie (wolny/zajęty)
        if (status.equals("Zajęty") && !vehicle.isRented()) {
            matches = false;
        } else if (status.equals("Wolny") && vehicle.isRented()) {
            matches = false;
        }

        if (matches) {
            filteredVehicles.add(vehicle);
        }
    }

    // Przeładuj wyniki wyszukiwania
      return updateVehicleContentPanel(filteredVehicles);

}

    private JPanel updateVehicleContentPanel(List<Vehicle> filteredVehicles) {
        JPanel vehicleContent = new JPanel();
        vehicleContent.setLayout(new BoxLayout(vehicleContent, BoxLayout.Y_AXIS));
        vehicleContent.setBackground(Colors.BACKGROUND.getColor());

        for (Vehicle vehicle : filteredVehicles) {
            ContentPanel contentPanel = new ContentPanel(vehicle);

            // Ustawianie preferowanego rozmiaru na podstawie długości obiektu
            int preferredWidth = layers.getPreferredSize().width - 20;
            contentPanel.setPreferredSize(new Dimension(preferredWidth, 90));

            // Dodaj ContentPanel do vehicleContent
            vehicleContent.add(contentPanel);

            // Dodaj przerwę między panelami
            vehicleContent.add(Box.createVerticalStrut(10));
        }

       return vehicleContent;
    }

    public void createSearchPanel() {
        // Panel z główną zawartością
        mainContentPanel.removeAll();

        // Panel na belkę wyszukiwania
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Colors.BACKGROUND.getColor());
        searchPanel.setForeground(Color.WHITE);

        // Pole tekstowe do wyszukiwania po marce
        JTextField brandField = new JTextField(10);
        searchPanel.add(new JLabel("Marka:"));
        searchPanel.add(brandField);

        // Lista rozwijana do wyboru typu pojazdu (Bike, Ebike, Scooter)
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Wszystko", "Rower", "elek", "Hulajnoga"});
        searchPanel.add(new JLabel("Typ:"));
        searchPanel.add(typeComboBox);

        // Lista rozwijana do wyboru statusu (Elektryczny)
        JComboBox<String> electricComboBox = new JComboBox<>(new String[]{"Wszystko", "Elektryczny", "Nie elektryczny"});
        searchPanel.add(new JLabel("Elektryczny:"));
        searchPanel.add(electricComboBox);

        // Lista rozwijana do wyboru statusu (Zajęty/Wolny)
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Wszystko", "Zajęty", "Wolny"});
        searchPanel.add(new JLabel("Status:"));
        searchPanel.add(statusComboBox);

        // Przycisk do uruchamiania wyszukiwania
        JPanel vehicleContent = new JPanel();
        vehicleContent.setLayout(new BoxLayout(vehicleContent, BoxLayout.Y_AXIS));
        vehicleContent.setBackground(Colors.BACKGROUND.getColor());
        JButton searchButton = new JButton("Szukaj");
        searchButton.addActionListener(e -> performSearch(brandField.getText(),
                (String) typeComboBox.getSelectedItem(),
                (String) electricComboBox.getSelectedItem(),
                (String) statusComboBox.getSelectedItem()));
        searchPanel.add(searchButton);

        // Dodaj belkę wyszukiwania do głównego panelu
        mainContentPanel.add(searchPanel, BorderLayout.NORTH);

        // Panel na wyniki wyszukiwania


        // Dynamiczna liczba wierszy
        @SuppressWarnings("unchecked")
        Map<String, Vehicle> vehicleMap = (Map<String, Vehicle>) CentralDatabase.getInstance().getCachedData().get(Vehicle.class);

        // Dodaj panel z wynikami
        for (Map.Entry<String, Vehicle> entry : vehicleMap.entrySet()) {
            if (entry.getValue() != null) {
                ContentPanel contentPanel = new ContentPanel(entry.getValue());

                // Ustawianie preferowanego rozmiaru na podstawie długości obiektu
                int preferredWidth = layers.getPreferredSize().width - 20;
                contentPanel.setPreferredSize(new Dimension(preferredWidth, contentPanel.getPreferredSize().height));

                // Dodaj ContentPanel do vehicleContent
                vehicleContent.add(contentPanel);

                // Dodaj przerwę między panelami
                vehicleContent.add(Box.createVerticalStrut(10));
            }
        }

        // Dodaj vehicleContent do mainContentPanel
        mainContentPanel.add(vehicleContent);

        // Uaktualnij widok po dodaniu nowych elementów
        mainContentPanel.revalidate();
        mainContentPanel.repaint();

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
        JPanel statsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        changeLastElement(MenuOptionPosition.STATS.getIndex());
        statsPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(statsPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.showScreen(CentralDatabase.getInstance().getCurrentUser()==null? null : CentralDatabase.getInstance().getCurrentUser().getUserType());
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

    private void addFieldListener(EditableField field, String label, Supplier<String> getter, Consumer<String> setter) {
        addFieldListener(field, label, getter, setter, true);
    }

    private void addFieldListener(EditableField field, String label, Supplier<String> getter, Consumer<String> setter, boolean isSwitchable) {
        if (isSwitchable) {
            field.getButton().setVisible(true);
        } else {
            return;
        }
        field.getButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                element = new EditableTogglePanel(
                        "Zmień " + label,
                        label,
                        getter.get()
                );
                element.setPreferredSize(layers.getPreferredSize());

                // Obsługa przycisku zamknięcia
                element.getCloseLabel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        toggle.remove(element);
                        layers.remove(toggle);
                        layers.revalidate();
                        layers.repaint();
                    }
                });

                // Dodanie panelu elementu do panelu toggle
                toggle.add(element);
                layers.add(toggle, JLayeredPane.PALETTE_LAYER);
                layers.revalidate();
                layers.repaint();

                // Dodanie obsługi akcji dla przycisku "Zapisz zmiany"
                element.addMenuListener(() -> {
                    if (!element.validateData()) {
                        String newValue = element.getEnteredText();
                        setter.accept(newValue);

                        createSettingsPanel();
                    }
                });
            }
        });
    }

    private void addFieldMapListener(
            EditableField field,
            String label,
            Supplier<Map<String, Employee>> getter, // Getter zwracający mapę
            BiConsumer<String, Employee> setter,   // Setter aktualizujący mapę
            boolean isSwitchable
    ) {
        if (isSwitchable) {
            field.getButton().setVisible(true);
        } else {
            return;
        }

        field.getButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tworzenie panelu edycyjnego
                AbstractEditablePanel element = new EditiableEmployeeViewPanel(
                        "Zmień " + label,
                        label,// Początkowy tekst, jeśli wymagany
                        getter.get() // Wartość domyślna
                );
                element.setPreferredSize(layers.getPreferredSize());

                // Obsługa przycisku zamknięcia
                element.getCloseLabel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        toggle.remove(element);
                        layers.remove(toggle);
                        layers.revalidate();
                        layers.repaint();
                    }
                });

                // Dodanie panelu elementu do panelu toggle
                toggle.add(element);
                layers.add(toggle, JLayeredPane.PALETTE_LAYER);
                layers.revalidate();
                layers.repaint();

                // Obsługa akcji dla przycisku "Zapisz zmiany"
                element.addMenuListener(() -> {
                    if (element.validateData()) {
                        String newEmployeeId = element.getEnteredText(); // Pobranie klucza (ID pracownika)
                        Map<String, Employee> employeeMap = getter.get(); // Pobranie mapy
                        if (!employeeMap.containsKey(newEmployeeId)) {
                            String employeeName = JOptionPane.showInputDialog("Podaj imię pracownika:");
                            if (employeeName != null && !employeeName.trim().isEmpty()) {
                                Employee newEmployee = Employee.createNewEmployee(); // Tworzenie nowego pracownika
                                setter.accept(newEmployeeId, newEmployee); // Aktualizacja mapy
                                createSettingsPanel(); // Odświeżenie panelu
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Pracownik o podanym ID już istnieje.");
                        }
                    }
                });
            }
        });
    }

    void setScrollPane(JScrollPane scrollPane) {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Colors.DARK_BLUE.getColor());
        scrollPane.setBorder(null);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
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
    }


}
