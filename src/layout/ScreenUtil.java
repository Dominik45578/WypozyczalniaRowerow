package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.user.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public abstract class ScreenUtil implements Screen {
    protected static JFrame rightBackPanel; // Jeden wspólny JFrame
    protected JPanel mainPanel;    // Gradientowe tło
    protected JPanel wrapperPanel; // Wrapper dla centralPanel
    protected JPanel centralPanel; // Panel środkowy
    protected JPanel upperContentPanel;
    protected Dimension centralPanelDimension;

    public ScreenUtil() {
        this("DEFAULT_FRAME_NAME");
    }

    public ScreenUtil(String title) {
        this(title, 1000, 500);
    }

    public ScreenUtil(String title, int width, int height) {
        if (rightBackPanel == null) { // Tworzymy JFrame tylko raz
            rightBackPanel = new JFrame(title);
            rightBackPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            rightBackPanel.setExtendedState(JFrame.MAXIMIZED_BOTH);
            rightBackPanel.setIconImage(new ImageIcon("src/rentallogo1.png").getImage());
            rightBackPanel.setUndecorated(true);

        }

        this.centralPanelDimension = new Dimension(width, height);
        // Gradientowe tło
        mainPanel = createGradientPanel();
        mainPanel.setLayout(new BorderLayout());

        // Wrapper panel
        wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.setPreferredSize(centralPanelDimension);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Centralny panel (zmienna zawartość)
        centralPanel = new JPanel();
        centralPanel.setPreferredSize(centralPanelDimension);
        centralPanel.setMaximumSize(centralPanelDimension);
        centralPanel.setMinimumSize(centralPanelDimension);
        centralPanel.setOpaque(false); // Transparentny
        wrapperPanel.add(centralPanel, gbc);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
        rightBackPanel.setContentPane(mainPanel);
        try {
            CentralDatabase.getInstance().loadAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Załadowano obiekty");
    }

    protected abstract void createScreenContent(User user);
    protected abstract void addListener(Component component, Runnable action);

    public void showScreen() {
        centralPanel.removeAll(); // Usuwamy starą zawartość
        createScreenContent(CentralDatabase.getInstance().getCurrentUser());    // Tworzymy nową zawartość
        rightBackPanel.revalidate();
        rightBackPanel.repaint();
        rightBackPanel.setVisible(true);
    }
}