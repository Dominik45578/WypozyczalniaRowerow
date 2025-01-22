package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.fileoperations.CheckData;
import dataclass.user.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class RegisterScreen extends ScreenUtil {
    ErrorsTogglePanel element;
    JPanel errorTogglePanel;
    JLayeredPane rightLayers;
    JPanel buissnesTogglePanel;
    Users userType;
    Customer tempUser;

    public RegisterScreen() {
        super("Guckor Bike Rental - Rejestracja", 1100, 800);
    }

    private void createErrorLayer() {
        errorTogglePanel = createRoundedPanel(new Color(0, 0, 0, 1));
        errorTogglePanel.setOpaque(false);
        errorTogglePanel.setPreferredSize(rightLayers.getPreferredSize());
        errorTogglePanel.setBounds(0, 0, rightLayers.getPreferredSize().width, rightLayers.getPreferredSize().height); // Ustawienie wymiarów zgodnych z rodzicem
        errorTogglePanel.setLayout(new GridBagLayout());
        element = new ErrorsTogglePanel(rightLayers.getPreferredSize(), "Błędy");
        // element.setPreferredSize(new Dimension(rightLayers.getWidth() / 2, rightLayers.getHeight() - 100));
        errorTogglePanel.add(element);
        element.setVisible(false);
        element.setElementPreferredSize(new Dimension(rightLayers.getPreferredSize().width / 2 + 150, rightLayers.getPreferredSize().height / 2 + 150));
        rightLayers.revalidate();
        rightLayers.repaint();
        errorTogglePanel.setVisible(true); // Ukrycie panelu
        rightLayers.add(errorTogglePanel, JLayeredPane.PALETTE_LAYER);
    }


    @Override
    protected void createScreenContent(Users user) {
        // Ustawienie układu centralnego panelu
        this.userType = user;
        centralPanel.setLayout(new BorderLayout(10, 10));

        // Dodanie górnego panelu
        centralPanel.add(createUpperPanel(), BorderLayout.NORTH);

        // Dodanie panelu głównego
        centralPanel.add(createMainFormPanel(), BorderLayout.CENTER);
        createErrorLayer();

    }

    @Override
    protected void addListener(Component component, Runnable action) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
    }

    private JPanel createBuissnesTogglePanel() {
        JPanel buissnesTogglePanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        buissnesTogglePanel.setPreferredSize(rightLayers.getPreferredSize());
        buissnesTogglePanel.setBounds(0, 0, rightLayers.getPreferredSize().width, rightLayers.getPreferredSize().height - 50);
        buissnesTogglePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buissnesTogglePanel.setLayout(new GridLayout(7, 2, 10, 10));
        FormWrapper companyNameField = new FormWrapper("Nazwa firmy", "Wpisz nazwę firmy", CheckData::isValidName);
        FormWrapper companyAddressField = new FormWrapper("Adres firmy", "Wpisz ulicę oraz numer budynku", CheckData::isValidAStreetAndNumber);
        FormWrapper companyPostalCodeField = new FormWrapper("Kod pocztowy firmy", "Wpisz kod pocztowy", CheckData::isValidPostalCode);
        FormWrapper companyCityField = new FormWrapper("Miejscowość firmy", "Wpisz miejscowość", CheckData::isValidName);
        FormWrapper companyPhoneNumberField = new FormWrapper("Numer telefonu firmy", "Wpisz numer telefonu", CheckData::isValidPhoneNumber);
        FormWrapper companyEmailField = new FormWrapper("Email firmy", "Wpisz email firmy", CheckData::isValidEmail);
        FormWrapper companyNIPField = new FormWrapper(true, "Numer NIP firmy", "Wpisz NIP", CheckData::isValidNIP);
        buissnesTogglePanel.add(companyNameField);
        buissnesTogglePanel.add(companyAddressField);
        buissnesTogglePanel.add(companyPostalCodeField);
        buissnesTogglePanel.add(companyCityField);
        buissnesTogglePanel.add(companyPhoneNumberField);
        buissnesTogglePanel.add(companyEmailField);
        buissnesTogglePanel.add(companyNIPField);
        buissnesTogglePanel.add(new JLabel());
        buissnesTogglePanel.setVisible(false);
        JButton backButton = createRoundedButton("< Wróć", 20);
        addListener(backButton, () -> buissnesTogglePanel.setVisible(false));
        buissnesTogglePanel.add(backButton);
        JButton registerButton = createRoundedButton("Utwórz konto", Color.WHITE, Colors.DARK_BLUE.getColor(), new Font("Sanserif", Font.BOLD, 20));
        registerButton.addActionListener(g -> {
            boolean allValid = true;
            allValid &= companyNameField.validateInput();
            allValid &= companyAddressField.validateInput();
            allValid &= companyPostalCodeField.validateInput();
            allValid &= companyCityField.validateInput();
            allValid &= companyEmailField.validateInput();
            allValid &= companyPhoneNumberField.validateInput();
            allValid &= companyNIPField.validateInput();
            if (allValid && tempUser != null) {
                User businessUser = new BusinessCustomer(tempUser, companyNameField.getValue(),
                        CentralDatabase.getInstance().getNextID(User.class, User.BUSINESS_C_PREFIX), companyAddressField.getValue(),
                        companyPostalCodeField.getValue(), companyCityField.getValue(), companyPhoneNumberField.getValue(),
                        companyEmailField.getValue(), companyNIPField.getValue());
                CentralDatabase.getInstance().addObject(User.class, businessUser.getID(), businessUser);
                try {
                    CentralDatabase.getInstance().saveAll();
                    CentralDatabase.getInstance().loadAll();
                    JOptionPane.showMessageDialog(rightBackPanel, "Wszystkie dane są poprawne!");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                CentralDatabase.getInstance().setCurrentUser(CentralDatabase.getInstance().FilterUser(businessUser.getEmail()));
                SwingUtilities.invokeLater(() -> {
                    ScreenUtil mainScreen = new MainScreen();
                    mainScreen.showScreen(CentralDatabase.getInstance().getCurrentUser() == null ? null : CentralDatabase.getInstance().getCurrentUser().getUserType());
                });
            }
        });
        buissnesTogglePanel.add(registerButton);
        return buissnesTogglePanel;
    }

    private JPanel createRootPanel() {
        JPanel rootPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        rootPanel.setPreferredSize(rightLayers.getPreferredSize());
        rootPanel.setBounds(0, 0, rightLayers.getPreferredSize().width, rightLayers.getPreferredSize().height - 50);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rootPanel.setLayout(new GridLayout(7, 2, 10, 10));
        FormWrapper firstNameField = new FormWrapper("Pierwsze imię", "Wpisz imię", CheckData::isValidName);
        FormWrapper secondNameField = new FormWrapper("Drugie imię", "Wpisz drugie imię", CheckData::isValidName);
        FormWrapper lastNameField = new FormWrapper("Nazwisko", "Wpisz nazwisko", CheckData::isValidName);
        FormWrapper emailField = new FormWrapper("Email", "Wpisz email", CheckData::isValidEmail);
        FormWrapper passwordField = new FormWrapper(true, "Hasło", "Wpisz hasło", CheckData::isValidPassword);
        FormWrapper repeatPasswordField = new FormWrapper(true, "Powtórz Hasło", "Powtórz hasło", CheckData::arePasswordsMatching, passwordField);

        passwordField.setSingleValidationMethod(null);
        passwordField.setDoubleValidationMethod(CheckData::arePasswordsMatching, repeatPasswordField);

        rootPanel.add(firstNameField);
        rootPanel.add(secondNameField);
        rootPanel.add(lastNameField);
        rootPanel.add(emailField);
        rootPanel.add(passwordField);
        rootPanel.add(repeatPasswordField);
        JButton registerButton;
        registerButton = createRoundedButton("Utwórz konto", Colors.DARK_BLUE_ACTIVE.getColor(), Color.WHITE, new Font("SansSerif", Font.PLAIN, 20));

        registerButton.addActionListener(e -> {
            boolean allValid = true;
            allValid &= firstNameField.validateInput();
            allValid &= secondNameField.validateInput();
            allValid &= lastNameField.validateInput();
            allValid &= emailField.validateInput();
            allValid &= passwordField.validateInputWithRelatedField();
            allValid &= repeatPasswordField.validateInputWithRelatedField();


            if (allValid) {
                User user = new RootUser(firstNameField.getValue(), secondNameField.getValue(), lastNameField.getValue(),
                        emailField.getValue(), passwordField.getValue());
                CentralDatabase.getInstance().addObject(User.class, user.getID(), user);
                try {
                    CentralDatabase.getInstance().saveAll();
                    CentralDatabase.getInstance().loadAll();
                    JOptionPane.showMessageDialog(rightBackPanel, "Wszystkie dane są poprawne!");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                CentralDatabase.getInstance().setCurrentUser(CentralDatabase.getInstance().FilterUser(emailField.getValue()));

                SwingUtilities.invokeLater(() -> {
                    ScreenUtil mainScreen = new MainScreen();
                    mainScreen.showScreen(CentralDatabase.getInstance().getCurrentUser() == null ? null : CentralDatabase.getInstance().getCurrentUser().getUserType());
                });
            } else {
                element.setVisible(true);
                rightLayers.revalidate();
                rightLayers.repaint();
            }

        });

        rootPanel.add(registerButton);

        return rootPanel;
    }

    private JPanel createUpperPanel() {
        upperContentPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        upperContentPanel.setLayout(new BorderLayout());
        upperContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        upperContentPanel.setPreferredSize(new Dimension(0, 80));
        JLabel titleLabel;
        if (userType == Users.BUSINESS_CUSTOMER) {
            titleLabel = createLabel("Rejestracja Klienta Biznesowego", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);
        } else if (userType == Users.ROOT) {
            titleLabel = createLabel("Rejestracja Administratora", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);

        } else {
            titleLabel = createLabel("Rejestracja Użytkownika", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);

        }
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton backButton = createRoundedButton("< Wróć", 20);
        addListener(backButton, () -> SwingUtilities.invokeLater(() -> new WelcomeScreen().showScreen(null)));
        upperContentPanel.add(backButton, BorderLayout.WEST);
        upperContentPanel.add(titleLabel, BorderLayout.CENTER);

        return upperContentPanel;
    }

    private JPanel createMainFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        formPanel.setOpaque(false);

        // Dodanie lewego i prawego panelu
        formPanel.add(createLeftPanel());
        formPanel.add(createRightPanel());

        return formPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);

        JPanel leftCentralPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        leftCentralPanel.setLayout(new BorderLayout(10, 10));
        leftCentralPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        leftCentralPanel.add(new JLabel(new ImageIcon("src/rentallogo1.png")), BorderLayout.CENTER);

        JButton loginButton = createRoundedButton("Zaloguj się", Colors.DARK_BLUE.getColor(), Color.WHITE, new Font("SansSerif", Font.BOLD, 24));
        JPanel loginButtonPanel = createRoundedPanel(new Color(68, 68, 68));
        loginButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        loginButtonPanel.add(loginButton);
        loginButton.addActionListener(e -> SwingUtilities.invokeLater(() -> new LoginScreen().showScreen(null)));

        leftPanel.add(leftCentralPanel, BorderLayout.CENTER);
        leftPanel.add(loginButtonPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = createRoundedPanel(Colors.BACKGROUND.getColor());

        rightLayers = new JLayeredPane();
        rightLayers.setPreferredSize(new Dimension((int) (centralPanelDimension.width - 10) / 2,
                (int) centralPanelDimension.height - createUpperPanel().getPreferredSize().height + 10));

        JPanel rightBackPanel = createRightBackPanel();
        if (userType != Users.ROOT) {
            rightLayers.add(rightBackPanel, JLayeredPane.DEFAULT_LAYER);
        }

        if (userType == Users.BUSINESS_CUSTOMER) {
            buissnesTogglePanel = createBuissnesTogglePanel();
            rightLayers.add(buissnesTogglePanel, JLayeredPane.DRAG_LAYER);
        }

        if (userType == Users.ROOT) {
            rightLayers.add(createRootPanel(), 1000);
        }
        rightPanel.add(rightLayers);

        return rightPanel;
    }

    private JPanel createRightBackPanel() {
        JPanel rightBackPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        rightBackPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightBackPanel.setLayout(new GridLayout(7, 2, 10, 10));
        rightBackPanel.setBounds(0, 0, (centralPanelDimension.width - 10) / 2, centralPanelDimension.height - 110);


        FormWrapper firstNameField = new FormWrapper("Pierwsze imię", "Wpisz imię", CheckData::isValidName);
        FormWrapper secondNameField = new FormWrapper("Drugie imię", "Wpisz drugie imię", CheckData::isValidName);
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


        // Dodawanie wszystkich pól do panelu
        rightBackPanel.add(firstNameField);
        rightBackPanel.add(secondNameField);
        rightBackPanel.add(lastNameField);
        rightBackPanel.add(birthDateField);
        rightBackPanel.add(peselField);
        rightBackPanel.add(cityField);
        rightBackPanel.add(streetField);

        rightBackPanel.add(houseNumberField);
        rightBackPanel.add(postalCodeField);
        rightBackPanel.add(emailField);
        rightBackPanel.add(passwordField);
        rightBackPanel.add(repeatPasswordField);

        JButton button = createRoundedButton("Błędy", Colors.DARK_BLUE_HOVER.getColor(), Color.WHITE,
                new Font("SansSerif", Font.PLAIN, 20));
        createToggleMenu(button);
        rightBackPanel.add(button);
        JButton registerButton;
        if (userType == Users.BUSINESS_CUSTOMER) {
            registerButton = createRoundedButton("Dalej ->", Colors.DARK_BLUE_ACTIVE.getColor(), Color.WHITE, new Font("SansSerif", Font.PLAIN, 20));
        } else {
            registerButton = createRoundedButton("Utwórz konto", Colors.DARK_BLUE_ACTIVE.getColor(), Color.WHITE, new Font("SansSerif", Font.PLAIN, 20));
        }

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
                Customer user = new PrivateCustomer(
                        firstNameField.getValue(), secondNameField.getValue(), lastNameField.getValue(),
                        peselField.getValue(), postalCodeField.getValue(), cityField.getValue(), streetField.getValue() + " " + houseNumberField.getValue(),
                        emailField.getValue(), passwordField.getValue());
                if (userType == Users.BUSINESS_CUSTOMER) {
                    tempUser = user;
                    buissnesTogglePanel.setVisible(true);
                } else {
                    CentralDatabase.getInstance().addObject(User.class, user.getID(), user);
                    try {
                        CentralDatabase.getInstance().saveAll();
                        CentralDatabase.getInstance().loadAll();
                        JOptionPane.showMessageDialog(rightBackPanel, "Wszystkie dane są poprawne!");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    SwingUtilities.invokeLater(() -> {
                        ScreenUtil mainScreen = new MainScreen();
                        mainScreen.showScreen(CentralDatabase.getInstance().getCurrentUser() == null ? null : CentralDatabase.getInstance().getCurrentUser().getUserType());
                    });
                    CentralDatabase.getInstance().setCurrentUser(CentralDatabase.getInstance().FilterUser(emailField.getValue()));
                }

            } else {
                element.setVisible(true);
                rightLayers.revalidate();
                rightLayers.repaint();
                //  JOptionPane.showMessageDialog(rightBackPanel, "Wystąpiły błędy w formularzu.");
            }

        });
        rightBackPanel.add(registerButton);

        return rightBackPanel;
    }

    private void createToggleMenu(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                element.setVisible(true); // Pokazanie panelu

                rightLayers.revalidate();
                rightLayers.repaint();
            }
        });
    }


    @Override
    public JPanel createFormWrapper(String title, String label) {
        JPanel formWrapper = createRoundedPanel(new Color(85, 84, 84));//new Color(218, 237, 248));
        formWrapper.setLayout(new BorderLayout(10, 10));
        formWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formWrapper.setOpaque(false);
        JLabel formTitle = new JLabel(title + " : ");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        formTitle.setForeground(Color.WHITE);
        JTextField formTextField = new JTextField();
        formTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        formTextField.setBackground(new Color(97, 97, 97));
        formTextField.setMargin(new Insets(0, 20, 0, 20));
        formTextField.setBorder(null);
        formTextField.setCaretColor(Color.WHITE);
        formTextField.setForeground(Color.WHITE);
        formWrapper.add(formTitle, BorderLayout.NORTH);
        formWrapper.add(formTextField, BorderLayout.CENTER);
        return formWrapper;
    }
}
