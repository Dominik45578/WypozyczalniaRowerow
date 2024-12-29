package layout;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends ScreenUtil {

    public LoginScreen() {
        super("Guckor Bike Rental - Login", 1000, 600);
    }

    @Override
    protected void createScreenContent() {
        // Ustawienie układu centralnego panelu
        centralPanel.setLayout(new GridLayout(1, 2, 20, 0)); // 2 kolumny: lewa i prawa

        // Lewy panel - obraz
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(10,10));
        leftPanel.setOpaque(false);
        JPanel iconPanel = createRoundedPanel(new Color(68, 68, 68));
        iconPanel.add(new JLabel(new ImageIcon("C:/Users/Domin/IdeaProjects/laby4/src/rentallogo1.png")), BorderLayout.CENTER);
        JPanel registerPanel = createRoundedPanel(new Color(68, 68, 68));
        registerPanel.add(createRoundedButton("Zarejestruj się", new Color(244, 86, 86), Color.WHITE, new Font("SansSerif", Font.PLAIN, 20)));

        registerPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        leftPanel.add(iconPanel, BorderLayout.CENTER);
        leftPanel.add(registerPanel, BorderLayout.SOUTH);
        // Prawy panel - formularz logowania
        JPanel rightPanel = createRoundedPanel(new Color(68, 68, 68));
        rightPanel.setLayout(new GridLayout(5, 1, 40, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        rightPanel.setOpaque(false);

        JLabel loginTitle = new JLabel("Logowanie do systemu");
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        loginTitle.setForeground(new Color(0, 173, 181));
        loginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(loginTitle);

        rightPanel.add(createFormWrapper("Email"));
        rightPanel.add(createFormWrapper("Hasło"));

        JButton loginButton = createRoundedButton("Login",new Color(0, 173, 181),Color.WHITE,new Font("SansSerif", Font.BOLD, 16));
        loginButton.setFocusPainted(true);
//        loginButton.addActionListener(e -> {
//            // Akcja logowania - przykład
//            JOptionPane.showMessageDialog(frame, "Logowanie...");
//        });
        rightPanel.add(loginButton);

        // Dodanie paneli do centralnego panelu
        centralPanel.add(leftPanel);
        centralPanel.add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.showScreen();
        });
    }
}
