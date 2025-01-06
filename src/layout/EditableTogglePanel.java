package layout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

public class EditableTogglePanel extends JPanel implements Screen {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel closeLabel;
    private JPanel contentPanel;
    private JPanel submitPanel;
    private JTextField textField;

    public EditableTogglePanel(String title, String type, String predecessor) {
        // Set semi-transparent background
        setBackground(new Color(0, 0, 0, 100));
        setLayout(new GridBagLayout());

        // Create the main panel
        mainPanel = createRoundedPanel(Colors.BACKGROUND.getColor()); // Example color
        mainPanel.setPreferredSize(new Dimension(500, 600));
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Create the title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        Border innerBorder = BorderFactory.createLineBorder(Colors.BACKGROUND.getColor(), 10 , true);
        Border outerBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE);

        titlePanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        titlePanel.setPreferredSize(new Dimension(400, 50));

        // Create the labels
        closeLabel = createLabel("Zamknij", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);
        titleLabel = createLabel(title, new Font("SansSerif", Font.BOLD, 20), Color.WHITE);

        // Add labels to the title panel
        titlePanel.add(titleLabel, SwingConstants.CENTER);
        titlePanel.add(closeLabel, BorderLayout.EAST);


        // Add the title panel to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new GridLayout(7,1,10,10));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        contentPanel.add(createLabel("Twój obecny "+ type +" : \n"+predecessor,20));
        textField= new JTextField(30);
        textField.setBackground(Colors.DARK_BLUE_HOVER.getColor());
        textField.setFont(new Font("SansSerif", Font.BOLD, 20));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Colors.DARK_BLUE_HOVER.getColor(),2,true));
        textField.setMargin(new Insets(2,10,2,10));
        textField.setToolTipText("Email");
        contentPanel.add(textField);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        submitPanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
        submitPanel.add(createLabel("Zapisz zmiany", 20), BorderLayout.CENTER);
        submitPanel.setAlignmentX(SwingConstants.CENTER);
        submitPanel.setAlignmentY(SwingConstants.CENTER);
        contentPanel.add(submitPanel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }

   @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Narysowanie zaokrąglonego tła
            g2d.setColor(new Color(0,0,0,100));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Zaokrąglone rogi

            // Wywołanie domyślnego renderowania treści (tekst, ikony)
            super.paintComponent(g);

            g2d.dispose();
        }

    public JLabel getCloseLabel(){
        return closeLabel;
    }
     public void addMenuListener(Runnable action) {
        submitPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submitPanel.setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
                action.run();
                revalidate();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                submitPanel.setBackground(Colors.DARK_BLUE_HOVER.getColor());
                submitPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                submitPanel.setBackground(Colors.DARK_BLUE.getColor());
                  submitPanel.repaint();
            }
        });

    }
    public String getEnteredText(){
        return textField.getText();
    }
    public JPanel getContentPanel(){
        return contentPanel;
    }
}