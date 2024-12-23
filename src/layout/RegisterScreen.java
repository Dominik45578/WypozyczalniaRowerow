
package layout;

import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends ScreenUtil {

    public RegisterScreen() {
        super("Guckor Bike Rental - Rejestracja", 1200, 800);
    }

    @Override
    protected void createScreenContent() {
        // Ustawienie układu centralnego panelu
        centralPanel.setLayout(new BorderLayout(0, 20)); // Dodanie 20px przerwy między NORTH a CENTER

        // Górny panel
        JPanel upperContentPanel = createRoundedPanel(new Color(68, 68, 68));
        upperContentPanel.setLayout(new BorderLayout());
        upperContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = createLabel("Rejestracja Użytkownika", new Font("SansSerif", Font.BOLD, 24), Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Wyśrodkowanie tekstu w JLabel
        upperContentPanel.add(titleLabel, BorderLayout.CENTER);


        // Panel główny z GridLayout (formularz)
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        formPanel.setOpaque(false);


        // Lewy panel z logiem
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        JPanel leftCentralPanel = createRoundedPanel(new Color(68, 68, 68));
        leftCentralPanel.setLayout(new BorderLayout());
        leftCentralPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        leftCentralPanel.add(new JLabel(new ImageIcon("C:/Users/Domin/IdeaProjects/laby4/src/rentallogo1.png")),
                BorderLayout.CENTER);

        JButton loginButton = createButton("Zaloguj się", new Color(0, 173, 181), Color.WHITE, new Font("SansSerif", Font.BOLD, 24));
        JPanel loginButtonPanel = createRoundedPanel(new Color(68, 68, 68));
        loginButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        loginButtonPanel.add(loginButton);
        loginButton.addActionListener(e -> SwingUtilities.invokeLater(() -> new LoginScreen().showScreen()));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(leftCentralPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(loginButtonPanel);

        // Prawy panel z formularzem
        JPanel rightPanel = createRoundedPanel(new Color(68, 68, 68));
        rightPanel.setLayout(new GridLayout(6, 2, 20, 20));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        rightPanel.add(createFormWrapper("Imię"));
        rightPanel.add(createFormWrapper("Nazwisko"));
        rightPanel.add(createFormWrapper("Data urodzenia"));
        rightPanel.add(createFormWrapper("PESEL"));
        rightPanel.add(createFormWrapper("Ulica"));
        rightPanel.add(createFormWrapper("Kod pocztowy"));
        rightPanel.add(createFormWrapper("Numer domu"));
        rightPanel.add(createFormWrapper("Miejscowość"));
        rightPanel.add(createFormWrapper("Email"));
        rightPanel.add(createFormWrapper("Hasło"));

        JCheckBox termsCheckBox = createCheckBox("Akceptuję regulamin", Color.WHITE, new Font("SansSerif", Font.PLAIN, 16));
        JButton registerButton = createButton("Zarejestruj się", new Color(244, 86, 86), Color.WHITE, new Font("SansSerif", Font.PLAIN, 20));

        rightPanel.add(termsCheckBox);
        rightPanel.add(registerButton);

        // Dodanie paneli do formPanel
        formPanel.add(leftPanel);
        formPanel.add(rightPanel);

        // Dodanie paneli do centralPanel
        centralPanel.add(upperContentPanel, BorderLayout.NORTH);
        centralPanel.add(formPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterScreen registerScreen = new RegisterScreen();
            registerScreen.showScreen();
        });
    }
}
