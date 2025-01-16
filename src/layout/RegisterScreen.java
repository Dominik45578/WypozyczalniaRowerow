package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.fileoperations.CheckData;
import dataclass.user.PrivateCustomer;
import dataclass.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class RegisterScreen extends ScreenUtil {
    ErrorsTogglePanel element;
    JPanel panel;
    JLayeredPane rightLayers;

    public RegisterScreen() {
        super("Guckor Bike Rental - Rejestracja", 1100, 800);
    }

    private void createErrorLayer() {
        panel = createRoundedPanel(new Color(0, 0, 0, 1));
        panel.setOpaque(false);
        panel.setPreferredSize(rightLayers.getPreferredSize());
        panel.setBounds(0, 0, rightLayers.getPreferredSize().width, rightLayers.getPreferredSize().height); // Ustawienie wymiarów zgodnych z rodzicem
        panel.setLayout(new GridBagLayout());
        element = new ErrorsTogglePanel(rightLayers.getPreferredSize(), "Błędy");
        // element.setPreferredSize(new Dimension(rightLayers.getWidth() / 2, rightLayers.getHeight() - 100));
        panel.add(element);
        element.setVisible(false);
        element.setElementPreferredSize(new Dimension(rightLayers.getPreferredSize().width/2+150, rightLayers.getPreferredSize().height/2+150));
        rightLayers.revalidate();
        rightLayers.repaint();
        panel.setVisible(true); // Ukrycie panelu
        rightLayers.add(panel, JLayeredPane.PALETTE_LAYER);
    }


    @Override
    protected void createScreenContent(User user) {
        // Ustawienie układu centralnego panelu
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

    private JPanel createUpperPanel() {
        upperContentPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        upperContentPanel.setLayout(new BorderLayout());
        upperContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        upperContentPanel.setPreferredSize(new Dimension(0, 80));

        JLabel titleLabel = createLabel("Rejestracja Użytkownika", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton backButton = createRoundedButton("< Wróć", 20);
        addListener(backButton, () -> SwingUtilities.invokeLater(() -> new WelcomeScreen().showScreen()));
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
        loginButton.addActionListener(e -> SwingUtilities.invokeLater(() -> new LoginScreen().showScreen()));

        leftPanel.add(leftCentralPanel, BorderLayout.CENTER);
        leftPanel.add(loginButtonPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = createRoundedPanel(Colors.BACKGROUND.getColor());

        rightLayers = new JLayeredPane();
        rightLayers.setPreferredSize(new Dimension((int) (centralPanelDimension.width - 10) / 2,
                (int) centralPanelDimension.height - createUpperPanel().getPreferredSize().height));

        JPanel rightBackPanel = createRightBackPanel();
        rightLayers.add(rightBackPanel, JLayeredPane.DEFAULT_LAYER);

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
        JButton registerButton = createRoundedButton("Utwórz konto", Colors.DARK_BLUE_ACTIVE.getColor(), Color.WHITE,
                new Font("SansSerif", Font.PLAIN, 20));
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
                User user = new PrivateCustomer(
                        String.valueOf(CentralDatabase.getInstance().getNextID(User.class, User.PRIVATE_C_PREFIX)),
                        firstNameField.getValue(), secondNameField.getValue(), lastNameField.getValue(),
                        peselField.getValue(), postalCodeField.getValue(), cityField.getValue(), streetField.getValue(), emailField.getValue(), passwordField.getValue()
                );
                CentralDatabase.getInstance().addObject(User.class, user.getID(), user);
                try {
                    CentralDatabase.getInstance().saveAll();
                    CentralDatabase.getInstance().loadAll();
                    JOptionPane.showMessageDialog(rightBackPanel, "Wszystkie dane są poprawne!");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                SwingUtilities.invokeLater(() -> {
                    MainScreen mainScreen = new MainScreen();
                    mainScreen.showScreen();
                });
                CentralDatabase.getInstance().setCurrentUser(CentralDatabase.getInstance().FilterUser(emailField.getValue()));


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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterScreen registerScreen = new RegisterScreen();
            registerScreen.showScreen();
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
