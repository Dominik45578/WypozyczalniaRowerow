package layout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// Abstract class defining common properties and behavior
public abstract class AbstractEditablePanel extends JPanel implements Screen {
    protected JPanel mainPanel;
    protected JLabel titleLabel;
    protected JLabel closeLabel;
    protected JPanel contentPanel;
    protected String type;


    public AbstractEditablePanel(Dimension dimension, String title, String type) {
        // Set semi-transparent background
        this.type = type;
        setBackground(new Color(0, 0, 0, 100));
        setLayout(new GridBagLayout());

        // Create the main panel
        setPreferredSize(dimension);
        mainPanel = createRoundedPanel(Colors.BACKGROUND.getColor());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(dimension.width / 2 + 100, dimension.height / 2 + 100));
        add(mainPanel);

        // Create the title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        Border innerBorder = BorderFactory.createLineBorder(Colors.BACKGROUND.getColor(), 10, true);
        Border outerBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE);
        titlePanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        titlePanel.setPreferredSize(new Dimension(dimension.width, 50));

        // Create the labels
        closeLabel = createLabel("Zamknij", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);
        titleLabel = createLabel(title, new Font("SansSerif", Font.BOLD, 20), Color.WHITE);

        // Add labels to the title panel
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(closeLabel, BorderLayout.EAST);

        // Add the title panel to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Initialize content panel
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    public JLabel getCloseLabel() {
        return closeLabel;
    }

    abstract void addMenuListener(Runnable action);

    abstract String getEnteredText();

    abstract boolean validateData();

    void setElementPreferredSize(Dimension d){
        this.mainPanel.setPreferredSize(d);
    }
    void setElementVisible(boolean b) {
        super.setVisible(false);
    }
}

