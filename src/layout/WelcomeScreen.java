package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.user.RootUser;
import dataclass.user.User;
import dataclass.user.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

public class WelcomeScreen extends ScreenUtil implements Screen {
    JPanel leftPanel;
    JPanel choosePanel;
    JButton loginButton;
    JButton registerPrivateButton;
    JButton registerBusinessButton;
    JButton adminButton;

    public WelcomeScreen() {
        super("Guckor Bike Rental - Welcome", 1000, 600);
        centralPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 2 kolumny: lewa i prawa
    }

    @Override
    protected void createScreenContent(Users user) {
        // Lewy panel - obraz
        leftPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        leftPanel.setLayout(new BorderLayout(10, 10));
        JPanel iconPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        iconPanel.add(new JLabel(new ImageIcon("src/rentallogo1.png")), BorderLayout.CENTER);
        leftPanel.add(iconPanel, BorderLayout.CENTER);

        //adminButton.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width/2+50,50));

        //  Prawy panel - wybór
        choosePanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        choosePanel.setLayout(new GridLayout(0, 1, 10, 10));
        choosePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel labelPanel = createRoundedPanel(new Color(0, 0, 0, 0));
        labelPanel.add(createLabel(" Witamy ! Miło Cię widzieć", 20), BorderLayout.CENTER);
        labelPanel.add(createLabel("Wybierz interesującą Cię opcję", 20), BorderLayout.SOUTH);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        choosePanel.add(labelPanel);

        loginButton = createButtonPanel("Zaloguj się");
        registerPrivateButton = createButtonPanel("Zarejestruj się");
        registerBusinessButton = createButtonPanel("Dla firm");
        adminButton = createButtonPanel("Administracja");
        adminButton.addActionListener(e -> {
            RootUser rootUser = (RootUser) CentralDatabase.getInstance().getCurrentUser();
            CustomPinDialog pinDialog = new CustomPinDialog((JFrame) SwingUtilities.getWindowAncestor(adminButton), rootUser);
            pinDialog.setVisible(true); // Wyświetl dialog

            if (pinDialog.isPinValid()) {
                new RegisterScreen().showScreen(Users.ROOT); // Jeśli PIN jest poprawny, otwórz ekran rejestracji
            }
        });

        JPanel sloganPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        sloganPanel.setLayout(new GridBagLayout());
        sloganPanel.add(createLabel("Spiesz się, ktoś może Cię ubiec", 20));
        sloganPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addListener(loginButton, () -> new LoginScreen().showScreen(null));
        addListener(registerPrivateButton, () -> new RegisterScreen().showScreen(Users.PRIVATE_CUSTOMER));
        addListener(registerBusinessButton, () -> new RegisterScreen().showScreen(Users.BUSINESS_CUSTOMER));
        addListener(adminButton, () -> new RegisterScreen().showScreen(Users.ROOT));
        choosePanel.add(loginButton);
        choosePanel.add(registerPrivateButton);
        choosePanel.add(registerBusinessButton);
        choosePanel.add(adminButton);
        choosePanel.add(sloganPanel);


        centralPanel.add(leftPanel);
        centralPanel.add(choosePanel);
    }


    private JButton createButtonPanel(String text) {
        JButton buttonPanel = createRoundedButton(text, Colors.DARK_BLUE_ACTIVE.getColor(), Color.WHITE, new Font("Sanserif", Font.BOLD, 20));
        buttonPanel.setLayout(new GridBagLayout()); // Wyrównanie etykiety na środku
        return buttonPanel;
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
        RootUser.setAccessPin("1234");
        SwingUtilities.invokeLater(() -> {
            ScreenUtil ws = new WelcomeScreen();
            ws.showScreen(null);
        });
    }
}
