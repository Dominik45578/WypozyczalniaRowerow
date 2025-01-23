package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.fileoperations.CheckData;
import dataclass.rental.ChartGenerator;
import dataclass.rental.RentalServices;
import dataclass.rental.RentalTransaction;
import dataclass.user.*;
import dataclass.vehicle.*;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    public static MainScreen instance;

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
        super("Guckor Bike Rental - Login", 1500, 850);
        instance = this;
        lastElement = -1;
        customer = CentralDatabase.getInstance().getCurrentUser();
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
        customerInfoPanel = createRoundedPanel(Colors.DARK_BLUE.getColor());
        customerInfoPanel.setAlignmentX(SwingUtilities.CENTER);
        customerInfoPanel.setAlignmentY(SwingUtilities.CENTER);
        customerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        customerInfoPanel.setLayout(new BorderLayout());
        customerInfoPanel.setPreferredSize(new Dimension(400, 100));
        JLabel customerLabel = createLabel("Cześć " + CentralDatabase.getInstance().getCurrentUser().getFirstName(), new Font("SansSerif", Font.PLAIN, 18), Color.WHITE);
        customerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        customerInfoPanel.add(createLabel(CentralDatabase.getInstance().getCurrentUser().getUserType().getType(), 20), BorderLayout.NORTH);
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
        addMenuListener(homeOption,
                CentralDatabase.getInstance().getCurrentUser().getUserType().equals(Users.ROOT) ? this::createAdminPanel : this::createHomePage);
        addMenuListener(statsOption, this::createStatsPanel);
        addMenuListener(historyOption,
                CentralDatabase.getInstance().getCurrentUser().getUserType().equals(Users.ROOT) ? this::createHistoryRootPanel : this::createHistoryPanel);
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

    private void createHomePage() {
        if (customer instanceof BusinessCustomer) {
            createHomePageForBusiness();
        }
    }

    public void createHomePageForBusiness() {
        // Główny panel z BorderLayout
        JPanel statsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        changeLastElement(MenuOptionPosition.STATS.getIndex());
        statsPanel.setPreferredSize(layers.getPreferredSize());
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statsPanel.setLayout(new BorderLayout(10, 10));

        // Panel na dynamiczną treść
        JPanel contentPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(
                layers.getPreferredSize().width - 20,
                layers.getPreferredSize().height - 150
        ));

        // Panel przycisków na górze
        JPanel buttonPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        // Przycisk "Wyświetl pracowników"
        JButton showEmployeesButton = createRoundedButton("Wyświetl pracowników", 20);
        showEmployeesButton.addActionListener(e -> {
            // Wyświetlanie listy pracowników
            createEmployeeListPanel(contentPanel);
        });
        buttonPanel.add(showEmployeesButton);

        // Przycisk "Dodaj pracownika"
        JButton addEmployeeButton = createRoundedButton("Dodaj pracownika", 20);
        addEmployeeButton.addActionListener(e -> {
            // Wyświetlanie formularza dodawania pracownika
            contentPanel.removeAll();
            contentPanel.add(createEmployeeInputPanel(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        buttonPanel.add(addEmployeeButton);

        statsPanel.add(buttonPanel, BorderLayout.NORTH);

        // Dodanie panelu centralnego na dynamiczną treść
        statsPanel.add(contentPanel, BorderLayout.CENTER);

        // Panel nawigacyjny na dole (opcjonalny)
        JPanel navigationPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        statsPanel.add(navigationPanel, BorderLayout.SOUTH);

        // Aktualizacja głównej warstwy
        updateLayerContent(statsPanel);
    }


    private void createEmployeeListPanel(JPanel contentPanel) {
        // Czyszczenie panelu centralnego
        contentPanel.removeAll();

        // Pobieranie pracowników z bazy danych
        BusinessCustomer customer = (BusinessCustomer) CentralDatabase.getInstance().getCurrentUser();
        Map<String, Employee> employeeMap = customer.getEmployees();


        // Panel dla listy pracowników
        JPanel employeesPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        employeesPanel.setLayout(new GridLayout(7, 1, 10, 10));
        employeesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        employeeMap.values().forEach(employee -> {
            EditiableEmployeeViewPanel employeePanel = new EditiableEmployeeViewPanel(employee);
            employeePanel.setMaximumSize(new Dimension(layers.getPreferredSize().width - 50, 100));
            employeesPanel.add(employeePanel);
            employeesPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Odstęp między panelami
        });

        // Dodanie panelu przewijanego do centralnego
        contentPanel.add(employeesPanel, BorderLayout.CENTER);

        // Odświeżanie panelu
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private JPanel createEmployeeInputPanel() {
        JPanel panel = createRoundedPanel(Colors.BACKGROUND.getColor());
        panel.setLayout(new GridLayout(5, 3, 10, 10));

        FormWrapper firstNameField = new FormWrapper("Pierwsze imię", "Wpisz imię", CheckData::isValidName);
        FormWrapper secondNameField = new FormWrapper("Drugie imię", "Wpisz drugie imię", CheckData::isValidSecondName);
        FormWrapper lastNameField = new FormWrapper("Nazwisko", "Wpisz nazwisko", CheckData::isValidName);
        FormWrapper birthDateField = new FormWrapper("Data urodzenia", "DD-MM-YYYY", CheckData::isValidDate);
        FormWrapper peselField = new FormWrapper(true, "PESEL", "Wpisz PESEL", CheckData::isValidPesel);
        FormWrapper streetField = new FormWrapper("Ulica", "Wpisz ulicę", CheckData::isValidAdres);
        FormWrapper postalCodeField = new FormWrapper("Kod pocztowy", "Wpisz kod pocztowy", CheckData::isValidPostalCode);
        FormWrapper houseNumberField = new FormWrapper("Numer domu", "Wpisz numer domu", CheckData::isValidHouseNumber);
        FormWrapper cityField = new FormWrapper("Miejscowość", "Wpisz miejscowość", CheckData::isValidName);
        FormWrapper emailField = new FormWrapper("Email", "Wpisz email", CheckData::isValidEmail);
        FormWrapper passwordField = new FormWrapper(true, "Hasło", "Wpisz hasło", CheckData::isValidPassword);
        FormWrapper repeatPasswordField = new FormWrapper(true, "Powtórz Hasło", "Powtórz hasło", CheckData::arePasswordsMatching, passwordField);

        passwordField.setSingleValidationMethod(null);
        passwordField.setDoubleValidationMethod(CheckData::arePasswordsMatching, repeatPasswordField);

        panel.add(firstNameField);
        panel.add(secondNameField);
        panel.add(lastNameField);
        panel.add(birthDateField);
        panel.add(peselField);
        panel.add(cityField);
        panel.add(streetField);
        panel.add(houseNumberField);
        panel.add(postalCodeField);
        panel.add(emailField);
        panel.add(passwordField);
        panel.add(repeatPasswordField);
        JButton registerButton;
        registerButton = createRoundedButton("Dalej ->", Colors.DARK_BLUE_ACTIVE.getColor(), Color.WHITE, new Font("SansSerif", Font.PLAIN, 20));
        panel.add(new JLabel());
        panel.add(registerButton);
        registerButton.addActionListener(e -> {
            boolean allValid = true;
            allValid &= firstNameField.validateInput();
            allValid &= secondNameField.validateInput();
            allValid &= lastNameField.validateInput();
            allValid &= birthDateField.validateInput();
            allValid &= peselField.validateInput();
            allValid &= streetField.validateInput();
            allValid &= postalCodeField.validateInput();
            allValid &= houseNumberField.validateInput();
            allValid &= cityField.validateInput();
            allValid &= emailField.validateInput();
            allValid &= passwordField.validateInputWithRelatedField();
            allValid &= repeatPasswordField.validateInputWithRelatedField();


            if (allValid) {
                Employee user = new Employee(
                        firstNameField.getValue(), secondNameField.getValue(), lastNameField.getValue(),
                        peselField.getValue(), postalCodeField.getValue(), cityField.getValue(), streetField.getValue() + " " + houseNumberField.getValue(),
                        emailField.getValue(), passwordField.getValue());
                BusinessCustomer customer = (BusinessCustomer) CentralDatabase.getInstance().getCurrentUser();
                user.setEmployer(customer);
                customer.addEmployee(user.getID(), user);

                CentralDatabase.getInstance().addObject(User.class, user.getID(), user);
                CentralDatabase.getInstance().addObject(User.class, customer.getID(), customer);
                try {
                    CentralDatabase.getInstance().saveAll();
                    CentralDatabase.getInstance().loadAll();
                    JOptionPane.showMessageDialog(rightBackPanel, "Wszystkie dane są poprawne!");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                SwingUtilities.invokeLater(() -> {
                    updateLayerContent(createEmployeeInputPanel());
                });
            } else {
                JOptionPane.showMessageDialog(panel, "Nie wszystkie dane są poprawne !");
            }

        });
        return panel;
    }


    protected JPanel adminInfoPanel;

    private void createAdminPanel() {
        createRoundedPanel(Colors.BACKGROUND.getColor());
        if (adminInfoPanel == null) {
            adminInfoPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
            adminInfoPanel.setLayout(new BorderLayout(10, 10));
        }
        adminInfoPanel.removeAll();

        // Panel z przyciskami u góry
        JPanel buttonPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addBikeButton = createRoundedButton("Dodaj Rower", 20);
        JButton addElectricBikeButton = createRoundedButton("Dodaj Rower Elektryczny", 20);
        JButton addScooterButton = createRoundedButton("Dodaj Hulajnogę", 20);
        JButton removeVehicleButton = createRoundedButton("Usuń pojazd", 20);
        JButton addBrandButton = createRoundedButton("Dodaj Markę", 20);
        JButton addModelButton = createRoundedButton("Dodaj Model", 20);

        buttonPanel.add(addBikeButton);
        buttonPanel.add(addElectricBikeButton);
        buttonPanel.add(addScooterButton);
        buttonPanel.add(removeVehicleButton);
        buttonPanel.add(addBrandButton);
        buttonPanel.add(addModelButton);

        adminInfoPanel.add(buttonPanel, BorderLayout.NORTH);

        // Panel środkowy
        JPanel centerPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        adminInfoPanel.add(centerPanel, BorderLayout.CENTER);

        // Obsługa akcji dla przycisków
        addBikeButton.addActionListener(e -> updateCenterPanel(centerPanel, 1));
        addElectricBikeButton.addActionListener(e -> updateCenterPanel(centerPanel, 2));
        addScooterButton.addActionListener(e -> updateCenterPanel(centerPanel, 3));

        removeVehicleButton.addActionListener(e -> {
            JComboBox<String> vehicleComboBox = new JComboBox<>();
            Map<String, Vehicle> vehicles = (Map<String, Vehicle>) CentralDatabase.getInstance().getCachedData().get(Vehicle.class);
            for (String vehicleId : vehicles.keySet()) {
                Vehicle vehicle = vehicles.get(vehicleId);
                if (!vehicle.isRented()) {
                    vehicleComboBox.addItem(vehicleId);
                }
            }

            JPanel inputPanel = new JPanel(new GridLayout(1, 2));
            inputPanel.add(new JLabel("Wybierz pojazd do usunięcia:"));
            inputPanel.add(vehicleComboBox);

            int result = JOptionPane.showConfirmDialog(adminInfoPanel, inputPanel, "Usuń pojazd", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedVehicleId = (String) vehicleComboBox.getSelectedItem();
                if (selectedVehicleId != null && vehicles.containsKey(selectedVehicleId)) {
                    RentalServices.getInstance().returnVehicle(selectedVehicleId);
                    JOptionPane.showMessageDialog(adminInfoPanel, "Pojazd o ID " + selectedVehicleId + " został usunięty.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(adminInfoPanel, "Nie wybrano prawidłowego pojazdu.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addBrandButton.addActionListener(e -> {
            JTextField brandField = new JTextField(10);
            JTextField modelField = new JTextField(10);
            JPanel inputPanel = new JPanel(new GridLayout(2, 2));
            inputPanel.add(new JLabel("Nazwa Marki:"));
            inputPanel.add(brandField);
            inputPanel.add(new JLabel("Pierwszy Model:"));
            inputPanel.add(modelField);

            int result = JOptionPane.showConfirmDialog(adminInfoPanel, inputPanel, "Dodaj Nową Markę", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String brandName = brandField.getText().trim();
                String modelName = modelField.getText().trim();

                if (!brandName.isEmpty() && !modelName.isEmpty()) {
                    VehicleBrand newBrand = new VehicleBrand(brandName);
                    newBrand.addModel(new VehicleModel(modelName));
                    CentralDatabase.getInstance().addObject(VehicleBrand.class, brandName, newBrand);
                    JOptionPane.showMessageDialog(adminInfoPanel, "Dodano nową markę: " + brandName, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(adminInfoPanel, "Proszę uzupełnić wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addModelButton.addActionListener(e -> {
            JComboBox<String> brandComboBox = new JComboBox<>();
            JTextField modelField = new JTextField(10);

            Map<String, VehicleBrand> brands = (Map<String, VehicleBrand>) CentralDatabase.getInstance().getCachedData().get(VehicleBrand.class);
            for (String brandName : brands.keySet()) {
                brandComboBox.addItem(brandName);
            }

            JPanel inputPanel = new JPanel(new GridLayout(2, 2));
            inputPanel.add(new JLabel("Wybierz Markę:"));
            inputPanel.add(brandComboBox);
            inputPanel.add(new JLabel("Nowy Model:"));
            inputPanel.add(modelField);

            int result = JOptionPane.showConfirmDialog(adminInfoPanel, inputPanel, "Dodaj Nowy Model", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedBrand = (String) brandComboBox.getSelectedItem();
                String modelName = modelField.getText().trim();

                if (selectedBrand != null && !modelName.isEmpty()) {
                    VehicleBrand brand = brands.get(selectedBrand);
                    if (brand != null) {
                        brand.addModel(new VehicleModel(modelName));
                        CentralDatabase.getInstance().addObject(VehicleBrand.class, brand.getName(), brand);
                        JOptionPane.showMessageDialog(adminInfoPanel, "Dodano nowy model: " + modelName + " do marki: " + selectedBrand, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(adminInfoPanel, "Proszę uzupełnić wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Aktualizacja zawartości panelu bazowego
        updateLayerContent(adminInfoPanel);
    }

    private void updateCenterPanel(JPanel centerPanel, int option) {
        centerPanel.removeAll();

        // Wspólne komponenty
        JLabel titleLabel = new JLabel();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel typeLabel = new JLabel("Typ:");
        typeLabel.setForeground(Color.WHITE);
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{
                SingleTrackVehicle.MTB,
                SingleTrackVehicle.SZOSA,
                SingleTrackVehicle.MIASTO
        });

        JLabel brandLabel = new JLabel("Marka:");
        brandLabel.setForeground(Color.WHITE);
        JComboBox<String> brandComboBox = new JComboBox<>();
        Map<String, VehicleBrand> brands = (Map<String, VehicleBrand>) CentralDatabase.getInstance().getCachedData().get(VehicleBrand.class);
        for (String brandName : brands.keySet()) {
            brandComboBox.addItem(brandName);
        }

        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setForeground(Color.WHITE);
        JComboBox<String> modelComboBox = new JComboBox<>();
        brandComboBox.addActionListener(e -> {
            String selectedBrand = (String) brandComboBox.getSelectedItem();
            modelComboBox.removeAllItems();
            if (selectedBrand != null) {
                VehicleBrand brand = brands.get(selectedBrand);
                for (String modelName : brand.getModels().keySet()) {
                    modelComboBox.addItem(modelName);
                }
            }
        });

        JLabel priceLabel = new JLabel("Cena:");
        priceLabel.setForeground(Color.WHITE);
        JTextField priceField = new JTextField(10);

        JLabel batteryLevelLabel = new JLabel("Poziom baterii:");
        batteryLevelLabel.setForeground(Color.WHITE);
        JTextField batteryLevelField = new JTextField(10);
        batteryLevelLabel.setVisible(false);
        batteryLevelField.setVisible(false);

        JCheckBox basketCheckBox = new JCheckBox("Koszyk");
        basketCheckBox.setForeground(Color.WHITE);
        basketCheckBox.setBackground(Colors.DARK_BLUE.getColor());
        basketCheckBox.setVisible(false);

        JCheckBox kidsFriendlyCheckBox = new JCheckBox("Dla dzieci");
        kidsFriendlyCheckBox.setForeground(Color.WHITE);
        kidsFriendlyCheckBox.setBackground(Colors.DARK_BLUE.getColor());
        kidsFriendlyCheckBox.setVisible(false);

        JButton addButton = createRoundedButton("Dodaj", 20);
        addButton.setVisible(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        inputPanel.setBackground(Colors.DARK_BLUE.getColor());
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        inputPanel.add(typeLabel);
        inputPanel.add(typeComboBox);
        inputPanel.add(brandLabel);
        inputPanel.add(brandComboBox);
        inputPanel.add(modelLabel);
        inputPanel.add(modelComboBox);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(batteryLevelLabel);
        inputPanel.add(batteryLevelField);
        inputPanel.add(basketCheckBox);
        inputPanel.add(kidsFriendlyCheckBox);
        inputPanel.add(addButton);

        switch (option) {
            case 1: // Menu dodawania roweru
                titleLabel.setText("Dodawanie Roweru");
                basketCheckBox.setVisible(true);
                kidsFriendlyCheckBox.setVisible(true);
                addButton.setVisible(true);

                addButton.addActionListener(e -> {
                    String selectedType = (String) typeComboBox.getSelectedItem();
                    String selectedBrand = (String) brandComboBox.getSelectedItem();
                    String selectedModel = (String) modelComboBox.getSelectedItem();
                    boolean hasBasket = basketCheckBox.isSelected();
                    boolean isKidsFriendly = kidsFriendlyCheckBox.isSelected();
                    String price = priceField.getText();

                    if (selectedType != null && selectedBrand != null && selectedModel != null && !price.isEmpty()) {
                        Bike bike = new Bike(selectedType, brands.get(selectedBrand), brands.get(selectedBrand).getModels().get(selectedModel), hasBasket, isKidsFriendly);
                        bike.setPrice(Integer.parseInt(price));
                        CentralDatabase.getInstance().addObject(Vehicle.class, bike.getVehicleId(), bike);

                        updateCenterPanel(centerPanel, 0); // Powrót do menu głównego po dodaniu
                    } else {
                        JOptionPane.showMessageDialog(centerPanel, "Proszę uzupełnić wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;

            case 2: // Menu dodawania eBike
                titleLabel.setText("Dodawanie eBike");
                batteryLevelLabel.setVisible(true);
                batteryLevelField.setVisible(true);
                addButton.setVisible(true);

                addButton.addActionListener(e -> {
                    String selectedType = (String) typeComboBox.getSelectedItem();
                    String selectedBrand = (String) brandComboBox.getSelectedItem();
                    String selectedModel = (String) modelComboBox.getSelectedItem();
                    String price = priceField.getText();
                    String batteryLevel = batteryLevelField.getText();

                    if (selectedType != null && selectedBrand != null && selectedModel != null && !price.isEmpty() && !batteryLevel.isEmpty()) {
                        EBike ebike = new EBike(selectedType, brands.get(selectedBrand), brands.get(selectedBrand).getModels().get(selectedModel), Integer.parseInt(batteryLevel));
                        System.out.printf("Dodano eBike: Typ=%s, Marka=%s, Model=%s, Poziom baterii=%s, Cena=%s\n",
                                selectedType, selectedBrand, selectedModel,
                                batteryLevel, price);
                        ebike.setPrice(Integer.parseInt(price));

                        CentralDatabase.getInstance().addObject(Vehicle.class, ebike.getVehicleId(), ebike);

                        updateCenterPanel(centerPanel, 0); // Powrót do menu głównego po dodaniu
                    } else {
                        JOptionPane.showMessageDialog(centerPanel, "Proszę uzupełnić wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                });
                break;
            case 3:
                titleLabel.setText("Dodawanie Hulajnogi");
                batteryLevelLabel.setVisible(true);
                batteryLevelField.setVisible(true);
                addButton.setVisible(true);

                addButton.addActionListener(e -> {
                    String selectedType = (String) typeComboBox.getSelectedItem();
                    String selectedBrand = (String) brandComboBox.getSelectedItem();
                    String selectedModel = (String) modelComboBox.getSelectedItem();
                    String price = priceField.getText();
                    String batteryLevel = batteryLevelField.getText();

                    if (selectedType != null && selectedBrand != null && selectedModel != null && !price.isEmpty() && !batteryLevel.isEmpty()) {
                        Vehicle scooter = new Scooter(selectedType, brands.get(selectedBrand), brands.get(selectedBrand).getModels().get(selectedModel), Integer.parseInt(batteryLevel));
                        scooter.setPrice(Integer.parseInt(price));
                        CentralDatabase.getInstance().addObject(Vehicle.class,scooter.getVehicleId(),scooter);

                        updateCenterPanel(centerPanel, 0); // Powrót do menu głównego po dodaniu
                    } else {
                        JOptionPane.showMessageDialog(centerPanel, "Proszę uzupełnić wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                });


            default:
                titleLabel.setText("Wybierz opcję z menu powyżej.");
                break;
        }

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(inputPanel);

        centerPanel.revalidate();
        centerPanel.repaint();

        // Aktualizacja adminInfoPanel po zmianach w centerPanel
        updateLayerContent(adminInfoPanel);
    }


    private int currentPage = 0; // Numer aktualnej strony
    private static final int TRANSACTIONS_PER_PAGE = 10; // Liczba transakcji na stronę

    public void createHistoryPanel() {
        // Główny panel z BorderLayout
        JPanel historyPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        historyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        historyPanel.setLayout(new BorderLayout(10, 10));

        // Pobieramy historię wypożyczeń użytkownika i sortujemy transakcje
        Map<String, RentalTransaction> transactions = CentralDatabase.getInstance().getCurrentUser().getRentedHistory();
        List<RentalTransaction> transactionList = new ArrayList<>(transactions.values());

        // Sortowanie: aktywne na początku
        transactionList.sort((t1, t2) -> Boolean.compare(!t1.isActive(), !t2.isActive()));

        int totalTransactions = transactionList.size();
        int totalPages = (int) Math.ceil((double) totalTransactions / TRANSACTIONS_PER_PAGE); // Całkowita liczba stron
        int startIndex = currentPageSearch * TRANSACTIONS_PER_PAGE; // Indeks początkowy dla bieżącej strony
        int endIndex = Math.min(startIndex + TRANSACTIONS_PER_PAGE, totalTransactions); // Indeks końcowy

        // Panel transakcji w centrum
        JPanel transactionsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        transactionsPanel.setLayout(new GridLayout(5, 2, 10, 10)); // 4 rzędy, 2 kolumny

        // Dodawanie transakcji do panelu
        for (int i = startIndex; i < endIndex; i++) {
            RentalTransaction transaction = transactionList.get(i);
            JPanel transactionPanel = new TransactionField(transaction);
            transactionsPanel.add(transactionPanel);
        }
        historyPanel.add(transactionsPanel, BorderLayout.CENTER);

        // Panel nawigacyjny na dole
        JPanel navigationPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        navigationPanel.setLayout(new BorderLayout());

        // Panel przycisków nawigacyjnych (po lewej stronie)
        JPanel buttonPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setOpaque(false);
        JButton previousButton = createRoundedButton("<- Nowsze", 20);
        previousButton.setVisible(false);
        if (currentPageSearch > 0) {
            previousButton.setVisible(true);

            previousButton.addActionListener(e -> {
                currentPageSearch--;
                createHistoryPanel(); // Odtwarzanie panelu
            });

        }
        buttonPanel.add(previousButton);
        JButton nextButton = createRoundedButton("Poprzednie ->", 20);
        nextButton.setVisible(false);
        if (endIndex < totalTransactions) {
            nextButton.setVisible(true);
            nextButton.addActionListener(e -> {
                currentPageSearch++;
                createHistoryPanel(); // Odtwarzanie panelu
            });

        }
        buttonPanel.add(nextButton);

        navigationPanel.add(buttonPanel, BorderLayout.WEST);

        // Wyświetlanie numeru strony po prawej stronie
        JLabel pageLabel = new JLabel(String.format("Strona %d z %d", currentPageSearch + 1, totalPages));
        pageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        pageLabel.setForeground(Color.WHITE); // Ustaw kolor tekstu
        navigationPanel.add(pageLabel, BorderLayout.EAST);

        // Dodanie panelu nawigacyjnego do dolnej części głównego panelu
        historyPanel.add(navigationPanel, BorderLayout.SOUTH);

        // Aktualizacja warstwy
        updateLayerContent(historyPanel);
    }

    private static final int TRANSACTIONS_PER_PAGE_ROOT = 8;

    public void createHistoryRootPanel() {
        // Główny panel z BorderLayout
        JPanel historyPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        historyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        historyPanel.setLayout(new BorderLayout(10, 10));

        // Pobieramy historię wypożyczeń użytkownika i sortujemy transakcje
        Map<String, RentalTransaction> transactions = (Map<String, RentalTransaction>) CentralDatabase.getInstance().getCachedData().get(RentalTransaction.class);
        List<RentalTransaction> transactionList = new ArrayList<>(transactions.values());

        // Sortowanie: aktywne na początku
        transactionList.sort((t1, t2) -> Boolean.compare(!t1.isActive(), !t2.isActive()));

        int totalTransactions = transactionList.size();
        int totalPages = (int) Math.ceil((double) totalTransactions / TRANSACTIONS_PER_PAGE_ROOT); // Całkowita liczba stron
        int startIndex = currentPageSearch * TRANSACTIONS_PER_PAGE_ROOT; // Indeks początkowy dla bieżącej strony
        int endIndex = Math.min(startIndex + TRANSACTIONS_PER_PAGE_ROOT, totalTransactions); // Indeks końcowy

        // Panel transakcji w centrum
        JPanel transactionsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        transactionsPanel.setLayout(new GridLayout(TRANSACTIONS_PER_PAGE_ROOT / 2, 2, 10, 10)); // 4 rzędy, 2 kolumny

        // Dodawanie transakcji do panelu
        for (int i = startIndex; i < endIndex; i++) {
            RentalTransaction transaction = transactionList.get(i);
            JPanel transactionPanel = new TransactionField(transaction);
            transactionsPanel.add(transactionPanel);
        }
        historyPanel.add(transactionsPanel, BorderLayout.CENTER);

        // Panel nawigacyjny na dole
        JPanel navigationPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        navigationPanel.setLayout(new BorderLayout());

        // Panel przycisków nawigacyjnych (po lewej stronie)
        JPanel buttonPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setOpaque(false);
        JButton previousButton = createRoundedButton("<- Nowsze", 20);
        previousButton.setVisible(false);
        if (currentPageSearch > 0) {
            previousButton.setVisible(true);

            previousButton.addActionListener(e -> {
                currentPageSearch--;
                createHistoryPanel(); // Odtwarzanie panelu
            });

        }
        buttonPanel.add(previousButton);
        JButton nextButton = createRoundedButton("Poprzednie ->", 20);
        nextButton.setVisible(false);
        if (endIndex < totalTransactions) {
            nextButton.setVisible(true);
            nextButton.addActionListener(e -> {
                currentPageSearch++;
                createHistoryPanel(); // Odtwarzanie panelu
            });

        }
        buttonPanel.add(nextButton);

        navigationPanel.add(buttonPanel, BorderLayout.WEST);

        // Wyświetlanie numeru strony po prawej stronie
        JLabel pageLabel = new JLabel(String.format("Strona %d z %d", currentPageSearch + 1, totalPages));
        pageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        pageLabel.setForeground(Color.WHITE); // Ustaw kolor tekstu
        navigationPanel.add(pageLabel, BorderLayout.EAST);

        // Dodanie panelu nawigacyjnego do dolnej części głównego panelu
        historyPanel.add(navigationPanel, BorderLayout.SOUTH);

        // Aktualizacja warstwy
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
            createSettingsPanelForBuissnes(settingsPanel);
        } else if (customer instanceof RootUser) {
            createSettingsPanelForRoot(settingsPanel);
        } else if (customer instanceof Employee) {
            createSettingsPanelForEmployee(settingsPanel);
        }

    }

    public void createSettingsPanelForRoot(JPanel settingsPanel) {
        RootUser customer = (RootUser) CentralDatabase.getInstance().getCurrentUser();

        EditableField idField = new EditableField("ID", customer.getID());
        EditableField firstNameField = new EditableField("Imię", customer.getFirstName());
        EditableField secondNameField = new EditableField("Drugie imię", customer.getSecondName());
        EditableField lastNameField = new EditableField("Nazwisko", customer.getLastName());
        EditableField passwordField = new EditableField("Hasło", ".".repeat(customer.getPassword().length()));
        EditableField rentedItemsField = new EditableField("Ilość wypożyczonych pojazdów (obecnie)", String.valueOf(RentalServices.getInstance().getNumberOfActiveTransaction()));
        EditableField transactionHistoryField = new EditableField("łączna ilość wypożyczonych pojazdów", String.valueOf(RentalServices.getInstance().getAllNumberOfTransactions()));
        EditableField userField = new EditableField("łączna ilość użytkowników", String.valueOf(RentalServices.getInstance().getNumberOfUsers()));
        EditableField vehicleField = new EditableField("łączna ilość pojazdów", String.valueOf(RentalServices.getInstance().getNumberOfVehicle()));
        EditableField brandField = new EditableField("łączna ilość Marek", String.valueOf(RentalServices.getInstance().getNumberOfVehicleBrand()));
        idField.getButton().setVisible(false);
        rentedItemsField.getButton().setVisible(false);
        transactionHistoryField.getButton().setVisible(false);


        addFieldListener(firstNameField, "Imię", customer::getFirstName, customer::setFirstName);
        addFieldListener(secondNameField, "Drugie imię", customer::getSecondName, customer::setSecondName);
        addFieldListener(lastNameField, "Nazwisko", customer::getLastName, customer::setLastName);
        addFieldListener(passwordField, "Hasło", customer::getPassword, customer::setPassword);
        //addFieldListener(rentedItemsField, "Wynajęte przedmioty", () -> String.valueOf(customer.getNumberOfRentedItems()), value -> customer.setNumberOfRentedItems(Integer.parseInt(value)));
        CentralDatabase.getInstance().setCurrentUser(customer);


        // Add EditableFields to the settings panel`
        JLabel ownerInfoPanel = createLabel("Informacje o Administratorze", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        settingsPanel.add(ownerInfoPanel);
        settingsPanel.add(new JLabel());
        settingsPanel.add(idField);
        settingsPanel.add(firstNameField);
        settingsPanel.add(secondNameField);
        settingsPanel.add(lastNameField);
        settingsPanel.add(passwordField);
        settingsPanel.add(new JLabel());
        JLabel vehicleInfoPanel = createLabel("Informacje o wypożyczalni", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        settingsPanel.add(vehicleInfoPanel);
        settingsPanel.add(new JLabel());
        settingsPanel.add(userField);
        settingsPanel.add(rentedItemsField);
        settingsPanel.add(transactionHistoryField);
        settingsPanel.add(vehicleField);
        settingsPanel.add(brandField);


        updateLayerContent(settingsPanel);
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
        EditableField saldoField = new EditableField("Saldo", String.valueOf(customer.getSaldo()));
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
        addFieldListener(saldoField, "Saldo", customer::getSaldoString, customer::setSaldo);
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
        settingsPanel.add(saldoField);
          settingsPanel.add(new JLabel());
        JLabel vehicleInfoPanel = createLabel("Informacje o wypożyczeniach", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        settingsPanel.add(vehicleInfoPanel);
        settingsPanel.add(new JLabel());

        settingsPanel.add(rentedItemsField);
        settingsPanel.add(transactionHistoryField);

        updateLayerContent(settingsPanel);
    }

    public void createSettingsPanelForBuissnes(JPanel settingsPanel) {
        BusinessCustomer company = (BusinessCustomer) CentralDatabase.getInstance().getCurrentUser();
        CentralDatabase.getInstance().setCurrentUser(company);
        CentralDatabase.getInstance().updateUser(User.class, company);
        settingsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JLabel infoLabel = createLabel("Informacje o firmie", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        EditableField companyNameField = new EditableField("Nazwa Firmy", company.getCompanyName());
        EditableField companyIdField = new EditableField("ID Firmy", company.getID());
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
        //settingsPanel.add(new JLabel());


        Customer customer = (Customer) company;
        EditableField ownerCompanyNameField = new EditableField("Imię", customer.getFirstName() + customer.getSecondName());
        EditableField ownerCompanyLastNameField = new EditableField("Nazwisko ", customer.getLastName());
        EditableField ownerCompanyIdField = new EditableField("ID użytkownika", customer.getID());
        EditableField ownerCompanyPhoneField = new EditableField("Numer telefonu ", customer.getID());
        EditableField ownerCompanyEmailField = new EditableField("Adress e-mail", customer.getEmail());
        EditableField ownerCompanyAddressField = new EditableField("Adres ", customer.getAddress());
        EditableField ownerCompanyCityField = new EditableField("Miasto", customer.getCity());
        EditableField ownerCompanyPostalCodeField = new EditableField("Kod pocztowy", customer.getPostalCode());
        EditableField saldoField = new EditableField("Saldo", String.valueOf(customer.getSaldo()));

        addFieldListener(ownerCompanyNameField, "Imię", customer::getFirstName, customer::setFirstName);
        addFieldListener(ownerCompanyLastNameField, "Nazwisko ", customer::getLastName, customer::setLastName);
        addFieldListener(ownerCompanyAddressField, "Adres ", customer::getAddress, customer::setAddress);
        addFieldListener(ownerCompanyCityField, "Miasto", customer::getCity, customer::setCity);
        addFieldListener(ownerCompanyPostalCodeField, "Kod pocztowy", customer::getPostalCode, customer::setPostalCode);
        addFieldListener(ownerCompanyEmailField, "E-mail", customer::getEmail, customer::setEmail);
        addFieldListener(ownerCompanyPhoneField, "Numer Telefonu", customer::getPhoneNumber, customer::setPhoneNumber);
        addFieldListener(saldoField, "Saldo", customer::getSaldoString, customer::setSaldo);

        settingsPanel.add(ownerCompanyIdField);
        settingsPanel.add(ownerCompanyNameField);
        settingsPanel.add(ownerCompanyLastNameField);
        settingsPanel.add(phoneNumberField);
        settingsPanel.add(ownerCompanyEmailField);


        settingsPanel.add(ownerCompanyAddressField);
        settingsPanel.add(ownerCompanyCityField);
        settingsPanel.add(ownerCompanyPostalCodeField);
        settingsPanel.add(saldoField);
//         JScrollPane settingsScroll = new JScrollPane(settingsPanel);
//         setScrollPane(settingsScroll);
//        //settingsScroll.setOpaque(false);

        updateLayerContent(settingsPanel);

    }

    public void createSettingsPanelForEmployee(JPanel settingsPanel) {
        Employee employee = (Employee) CentralDatabase.getInstance().getCurrentUser();
        BusinessCustomer company = employee.getEmployer();

        settingsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JLabel infoLabel = createLabel("Informacje o firmie", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        EditableField companyNameField = new EditableField("Nazwa Firmy", company.getCompanyName());
        EditableField companyIdField = new EditableField("ID Firmy", company.getID());
        EditableField addressField = new EditableField("Adres Firmy", company.getCompanyAddress());
        EditableField postalCodeField = new EditableField("Kod Pocztowy", company.getCompanyPostalCode());
        EditableField cityField = new EditableField("Miasto", company.getCompanyCity());
        EditableField phoneNumberField = new EditableField("Number Telefonu", company.getCompanyPhoneNumber());
        EditableField companyEmail = new EditableField("Adres e-mail", company.getCompanyEmail());
        EditableField employeesField = new EditableField("Pracownicy", String.valueOf(company.getEmployees().size()));


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

        JLabel ownerInfoPanel = createLabel("Informacje o Pracowniku ", new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
        settingsPanel.add(new JLabel());
        settingsPanel.add(ownerInfoPanel);
        settingsPanel.add(new JLabel());

        EditableField ownerCompanyNameField = new EditableField("Imię", employee.getFirstName() + employee.getSecondName());
        EditableField ownerCompanyLastNameField = new EditableField("Nazwisko ", employee.getLastName());
        EditableField ownerCompanyIdField = new EditableField("ID użytkownika", employee.getID());
        EditableField ownerCompanyPhoneField = new EditableField("Numer telefonu ", employee.getPhoneNumber());
        EditableField ownerCompanyEmailField = new EditableField("Adress e-mail", employee.getEmail());
        EditableField ownerCompanyAddressField = new EditableField("Adres ", employee.getAddress());
        EditableField ownerCompanyCityField = new EditableField("Miasto", employee.getCity());
        EditableField ownerCompanyPostalCodeField = new EditableField("Kod pocztowy", employee.getPostalCode());
        EditableField saldoField = new EditableField("Saldo", String.valueOf(employee.getSaldo()));

        addFieldListener(ownerCompanyNameField, "Imię", employee::getFirstName, employee::setFirstName);
        addFieldListener(ownerCompanyLastNameField, "Nazwisko ", employee::getLastName, employee::setLastName);
        addFieldListener(ownerCompanyAddressField, "Adres ", employee::getAddress, employee::setAddress);
        addFieldListener(ownerCompanyCityField, "Miasto", employee::getCity, employee::setCity);
        addFieldListener(ownerCompanyPostalCodeField, "Kod pocztowy", employee::getPostalCode, employee::setPostalCode);
        addFieldListener(ownerCompanyEmailField, "E-mail", employee::getEmail, employee::setEmail);
        addFieldListener(ownerCompanyPhoneField, "Numer Telefonu", employee::getPhoneNumber, employee::setPhoneNumber);
        addFieldListener(saldoField, "Saldo", employee::getSaldoString, employee::setSaldo);

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

    private static final int VEHICLES_PER_PAGE = 8; // Liczba pojazdów na stronę
    private int currentPageSearch = 0; // Bieżąca strona

    public void createSearchPanel() {
        // Główny panel z BorderLayout
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout(10, 10));

        // Pasek wyszukiwania
        JPanel filterPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JComboBox<String> brandComboBox = new JComboBox<>(getAvailableBrands());
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Wszystko", SingleTrackVehicle.MTB, SingleTrackVehicle.SZOSA, SingleTrackVehicle.MIASTO});
        JComboBox<String> electricComboBox = new JComboBox<>(new String[]{"Wszystko", "Elektryczny", "Nie elektryczny"});
        JTextField minPriceField = new JTextField(5);
        JTextField maxPriceField = new JTextField(5);
        JButton searchButton = createRoundedButton("Szukaj", 20);

        filterPanel.add(createLabel("Marka :", 20));
        filterPanel.add(brandComboBox);
        filterPanel.add(createLabel("Typ :", 20));
        filterPanel.add(typeComboBox);
        filterPanel.add(createLabel("Elektryczny :", 20));
        filterPanel.add(electricComboBox);
        filterPanel.add(createLabel("Cena od :", 20));
        filterPanel.add(minPriceField);
        filterPanel.add(createLabel("Cena do :", 20));
        filterPanel.add(maxPriceField);
        filterPanel.add(searchButton);

        mainContentPanel.add(filterPanel, BorderLayout.NORTH);

        // Panel na wyniki
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(VEHICLES_PER_PAGE, 1, 10, 10));
        resultsPanel.setBackground(Colors.BACKGROUND.getColor());
        mainContentPanel.add(resultsPanel, BorderLayout.CENTER);

        // Panel nawigacyjny na dole
        JPanel navigationPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        navigationPanel.setLayout(new BorderLayout(10, 0));

        JPanel buttonPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JLabel pageLabel = new JLabel(); // Numer strony
        navigationPanel.add(buttonPanel, BorderLayout.WEST);
        navigationPanel.add(pageLabel, BorderLayout.EAST);

        mainContentPanel.add(navigationPanel, BorderLayout.SOUTH);

        // Domyślne ładowanie wszystkich dostępnych pojazdów
        searchButton.addActionListener(e -> performSearchAndUpdate(
                (String) brandComboBox.getSelectedItem(),
                (String) typeComboBox.getSelectedItem(),
                (String) electricComboBox.getSelectedItem(),
                minPriceField.getText(),
                maxPriceField.getText()
        ));

        // Wyświetlenie domyślnych danych (wszystkie dostępne pojazdy)
        performSearchAndUpdate("Wszystko", "Wszystko", "Wszystko", "", "");

        updateLayerContent(mainContentPanel);
    }

    private String[] getAvailableBrands() {
        @SuppressWarnings("unchecked")
        Map<String, VehicleBrand> brandMap = (Map<String, VehicleBrand>) CentralDatabase.getInstance().getCachedData().get(VehicleBrand.class);
        return brandMap.values().stream().map(VehicleBrand::getName).toArray(String[]::new);
    }

    private void performSearchAndUpdate(String brand, String type, String electric, String minPrice, String maxPrice) {
        @SuppressWarnings("unchecked")
        Map<String, Vehicle> vehicleMap = (Map<String, Vehicle>) CentralDatabase.getInstance().getCachedData().get(Vehicle.class);

        double minPriceValue = minPrice.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(minPrice);
        double maxPriceValue = maxPrice.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPrice);

        List<Vehicle> filteredVehicles = vehicleMap.values().stream()
                .filter(vehicle -> vehicle != null && !vehicle.isRented())
                .filter(vehicle -> brand.equals("Wszystko") || vehicle.getVehicleBrand().getName().equalsIgnoreCase(brand))
                .filter(vehicle -> type.equals("Wszystko") || vehicle.getVehicleType().equalsIgnoreCase(type))
                .filter(vehicle -> electric.equals("Wszystko") ||
                        (electric.equals("Elektryczny") && vehicle.isElectric()) ||
                        (electric.equals("Nie elektryczny") && !vehicle.isElectric()))
                .filter(vehicle -> vehicle.getPrice() >= minPriceValue && vehicle.getPrice() <= maxPriceValue)
                .collect(Collectors.toList());

        updateSearchPanel(filteredVehicles);
    }

    private void updateSearchPanel(List<Vehicle> filteredVehicles) {
        JPanel resultsPanel = (JPanel) ((BorderLayout) mainContentPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        JPanel navigationPanel = (JPanel) ((BorderLayout) mainContentPanel.getLayout()).getLayoutComponent(BorderLayout.SOUTH);

        resultsPanel.removeAll();
        // Wyświetlanie wyników dla bieżącej strony
        int totalVehicles = filteredVehicles.size();
        int totalPages = (int) Math.ceil((double) totalVehicles / VEHICLES_PER_PAGE);
        int startIndex = currentPageSearch * VEHICLES_PER_PAGE;
        int endIndex = Math.min(startIndex + VEHICLES_PER_PAGE, totalVehicles);
        for (int i = startIndex; i < endIndex; i++) {
            Vehicle vehicle = filteredVehicles.get(i);
            ContentPanel contentPanel = new ContentPanel(vehicle);
            if (CentralDatabase.getInstance().getCurrentUser() instanceof Customer) {
                Customer c = (Customer) CentralDatabase.getInstance().getCurrentUser();
                if (c.getSaldo() < vehicle.getPrice()) {
                    contentPanel.getRentButton().setText("Zbyt niskie saldo");
                    contentPanel.getRentButton().setEnabled(false);
                }
            }
            resultsPanel.add(contentPanel);
        }

        // Aktualizacja przycisków nawigacyjnych
        JPanel buttonPanel = (JPanel) ((BorderLayout) navigationPanel.getLayout()).getLayoutComponent(BorderLayout.WEST);
        JLabel pageLabel = (JLabel) ((BorderLayout) navigationPanel.getLayout()).getLayoutComponent(BorderLayout.EAST);

        buttonPanel.removeAll();

        if (currentPageSearch > 0) {
            JButton previousButton = createRoundedButton("< - Poprzednie", 20);
            previousButton.addActionListener(e -> {
                currentPageSearch--;
                updateSearchPanel(filteredVehicles);
            });
            buttonPanel.add(previousButton);
        }

        if (endIndex < totalVehicles) {
            JButton nextButton = createRoundedButton("Następne -> ", 20);
            nextButton.addActionListener(e -> {
                currentPageSearch++;
                updateSearchPanel(filteredVehicles);
            });
            buttonPanel.add(nextButton);
        }

        // Aktualizacja numeru strony
        pageLabel.setText(String.format("Strona %d z %d", currentPageSearch + 1, totalPages));

        resultsPanel.revalidate();
        resultsPanel.repaint();
        navigationPanel.revalidate();
        navigationPanel.repaint();
    }


    public void createStatsPanel() {
        JPanel statsPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statsPanel.setLayout(new BorderLayout(10, 10));

        // CardLayout dla wykresów
        JPanel chartPanel = new JPanel(new CardLayout());
        ChartPanel vehicleChartPanel = new ChartPanel(ChartGenerator.createVehicleChart());
        ChartPanel transactionChartPanel = new ChartPanel(ChartGenerator.createTransactionChart());
        chartPanel.add(vehicleChartPanel, "VehicleChart");
        chartPanel.add(transactionChartPanel, "TransactionChart");

        JPanel buttonPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton previousButton = createRoundedButton("<- Poprzedni", 20);
        JButton nextButton = createRoundedButton("Następny ->", 20);

        CardLayout cl = (CardLayout) chartPanel.getLayout();
        previousButton.setEnabled(false); // Na początku pokazujemy tylko wykres kołowy

        previousButton.addActionListener(e -> {
            cl.show(chartPanel, "VehicleChart");
            previousButton.setEnabled(false);
            nextButton.setEnabled(true);
        });

        nextButton.addActionListener(e -> {
            cl.show(chartPanel, "TransactionChart");
            nextButton.setEnabled(false);
            previousButton.setEnabled(true);
        });

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        statsPanel.add(chartPanel, BorderLayout.CENTER);
        statsPanel.add(buttonPanel, BorderLayout.SOUTH);

        changeLastElement(MenuOptionPosition.STATS.getIndex());
        statsPanel.setPreferredSize(layers.getPreferredSize());
        updateLayerContent(statsPanel);
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

    public static void main(String[] args) {
        CentralDatabase cd = CentralDatabase.getInstance();
        VehicleBrand Brand = new VehicleBrand("Trek");
        Brand.addModel(new VehicleModel("Marlin"));
        cd.addObject(
                VehicleBrand.class, Brand.getName(), Brand
        );

        VehicleBrand Brand1 = new VehicleBrand("Specialized");
        Brand1.addModel(new VehicleModel("Rockhopper Elite"));
        cd.addObject(
                VehicleBrand.class, Brand1.getName(), Brand1
        );

        VehicleBrand Brand2 = new VehicleBrand("Giant");
        Brand2.addModel(new VehicleModel("Talon 1"));
        cd.addObject(
                VehicleBrand.class, Brand2.getName(), Brand2
        );
        Vehicle v = new Bike(SingleTrackVehicle.MTB, Brand, Brand.getModels().get("Marlin"), false, false);
        cd.addObject(
                Vehicle.class, v.getVehicleId(), v
        );
        Vehicle v1 = new Bike(SingleTrackVehicle.MTB, Brand1, Brand1.getModels().get("Rockhopper Elite"), false, false);
        cd.addObject(
                Vehicle.class, v1.getVehicleId(), v1
        );
        Vehicle v2 = new Bike(SingleTrackVehicle.MTB, Brand2, Brand2.getModels().get("Talon 1"), false, false);
        Vehicle v3 = new Scooter(SingleTrackVehicle.MTB, Brand1, Brand1.getModels().get("Rockhopper Elite"), 67);

        cd.addObject(
                Vehicle.class, v3.getVehicleId(), v3
        );

//        SwingUtilities.invokeLater(() -> {
//            MainScreen mainScreen = new MainScreen();
//            mainScreen.showScreen(CentralDatabase.getInstance().getCurrentUser() == null ? null : CentralDatabase.getInstance().getCurrentUser().getUserType());
//        });
    }

}
