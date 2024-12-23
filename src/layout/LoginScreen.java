package layout;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends ScreenUtil {

    public LoginScreen() {
        super("Guckor Bike Rental - Login", 1000, 500);
    }

    @Override
    protected void createScreenContent() {
        // Ustawienie układu centralnego panelu
        centralPanel.setLayout(new GridLayout(1, 2, 20, 0)); // 2 kolumny: lewa i prawa

        // Lewy panel - obraz
        JPanel leftPanel = createRoundedPanel(new Color(68, 68, 68));
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(new JLabel(new ImageIcon("C:/Users/Domin/IdeaProjects/laby4/src/rentallogo1.png")), BorderLayout.CENTER);

        // Prawy panel - formularz logowania
        JPanel rightPanel = createRoundedPanel(new Color(68, 68, 68));
        rightPanel.setLayout(new GridLayout(4, 1, 40, 10));
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
