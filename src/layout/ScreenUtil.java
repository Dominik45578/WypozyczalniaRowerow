package layout;

import javax.swing.*;
import java.awt.*;

public abstract class ScreenUtil implements Screen {
    protected static JFrame frame; // Jeden wspólny JFrame
    protected JPanel mainPanel;    // Gradientowe tło
    protected JPanel wrapperPanel; // Wrapper dla centralPanel
    protected JPanel centralPanel; // Panel środkowy
    protected Dimension centralPanelDimension;

    public ScreenUtil() {
        this("DEFAULT_FRAME_NAME");
    }

    public ScreenUtil(String title) {
        this(title, 1000, 500);
    }

    public ScreenUtil(String title, int width, int height) {
        if (frame == null) { // Tworzymy JFrame tylko raz
            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setIconImage(new ImageIcon("C:/Users/Domin/IdeaProjects/laby4/src/rentallogo1.png").getImage());
            frame.setUndecorated(true);
            this.centralPanelDimension = new Dimension(width, height);
        }

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
        frame.setContentPane(mainPanel);
    }

    protected abstract void createScreenContent();

    public void showScreen() {
        centralPanel.removeAll(); // Usuwamy starą zawartość
        createScreenContent();    // Tworzymy nową zawartość
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
}