package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.fileoperations.CheckData;
import dataclass.user.Customer;
import dataclass.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class LoginScreen extends ScreenUtil {
    JPanel formPanel ;
    public LoginScreen() {
        super("Guckor Bike Rental - Logowanie", 1000, 650);
    }

    @Override
    protected void createScreenContent(User user) {
        // Ustawienie układu centralnego panelu
         centralPanel.setLayout(new BorderLayout(10, 10)); // 2 kolumny: lewa i prawa
        formPanel = new JPanel(new GridLayout(1,2,10,10));
        formPanel.setOpaque(false);
        // Lewy panel - obraz
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(10,10));
        leftPanel.setOpaque(false);
        JPanel iconPanel = createRoundedPanel(new Color(68, 68, 68));
        iconPanel.add(new JLabel(new ImageIcon("src/rentallogo1.png")), BorderLayout.CENTER);
        JPanel registerPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        registerPanel.add(createRoundedButton("Zarejestruj się",20));

        registerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        leftPanel.add(iconPanel, BorderLayout.CENTER);
        leftPanel.add(registerPanel, BorderLayout.SOUTH);
        // Prawy panel - formularz logowania
        JPanel rightPanel = createRoundedPanel(new Color(68, 68, 68));
        rightPanel.setLayout(new GridLayout(5, 1, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setOpaque(false);

        JLabel loginTitle = createLabel("Zaloguj się" ,24);
        loginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(loginTitle);

        FormWrapper emailField = new FormWrapper("E-mail", "Wpisz e-mail", CheckData::isValidExistingEmail);
        FormWrapper passwordField = new FormWrapper("Hasło", "Wpisz hasło", CheckData::isValidPassword);
        rightPanel.add(emailField);
        rightPanel.add(passwordField);

        JButton loginButton = createRoundedButton("Zaloguj się",20);
        loginButton.setBackground(Colors.DARK_BLUE.getColor());
        loginButton.addActionListener(e -> {
            boolean allValid = true;
            allValid &=emailField.validateInput();
            allValid &=passwordField.validateInput();
            if(allValid){
                User searchUser = CentralDatabase.getInstance().FilterUser(emailField.getValue());
                if(searchUser.getPassword().equals(passwordField.getValue())){
                    JOptionPane.showMessageDialog(rightPanel, "Logowanie...");
                    CentralDatabase.getInstance().setCurrentUser(CentralDatabase.getInstance().FilterUser(emailField.getValue()));
                    new MainScreen().showScreen();
                }
                else{
                    passwordField.getTextField().setForeground(Color.RED);
                }
            }else{
                 emailField.getTextField().setForeground(Color.RED);
                 passwordField.getTextField().setForeground(Color.RED);
            }

        });
        rightPanel.add(loginButton);

        // Dodanie paneli do centralnego panelu
        formPanel.add(leftPanel);
        formPanel.add(rightPanel);
        createUpperPanel();
        centralPanel.add(upperContentPanel, BorderLayout.NORTH);
        centralPanel.add(formPanel, BorderLayout.CENTER);
    }
     private JPanel createUpperPanel() {
        upperContentPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        upperContentPanel.setLayout(new BorderLayout());
        upperContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        upperContentPanel.setPreferredSize(new Dimension(0, 80));

        JLabel titleLabel = createLabel("Logowanie użytkownika", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton backButton = createRoundedButton("< Wróć" , 20);
        addListener(backButton, () -> SwingUtilities.invokeLater(() -> new WelcomeScreen().showScreen()));
        upperContentPanel.add(backButton, BorderLayout.WEST);
        upperContentPanel.add(titleLabel, BorderLayout.CENTER);

        return upperContentPanel;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.showScreen();
        });
    }
}
