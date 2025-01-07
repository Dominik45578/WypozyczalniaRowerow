package layout;//package layout;
//
//import javax.swing.*;
//import javax.swing.border.Border;
//import javax.swing.plaf.BorderUIResource;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.lang.reflect.Method;
//
//public class EditableTogglePanel extends EditableTogglePanelUtil implements Screen {
//    private JPanel mainPanel;
//    private JLabel titleLabel;
//    private JLabel closeLabel;
//    private JPanel contentPanel;
//    private JPanel submitPanel;
//    private JTextField textField;
//    private JLabel sizeErrorLabel;
//    private JLabel emailErrorLabel;
//    private JLabel passwordErrorLabel;
//    private JLabel postalCodeErrorLabel;
//    private String type;
//
//    public EditableTogglePanel(String title, String type, String predecessor) {
//        // Set semi-transparent background
//        this.type = type;
//        setBackground(new Color(0, 0, 0, 100));
//        setLayout(new GridBagLayout());
//
//        // Create the main panel
//        mainPanel = createRoundedPanel(Colors.BACKGROUND.getColor()); // Example color
//        mainPanel.setPreferredSize(new Dimension(550, 6500));
//        mainPanel.setLayout(new BorderLayout());
//        add(mainPanel);
//
//        // Create the title panel
//        JPanel titlePanel = new JPanel(new BorderLayout());
//        titlePanel.setOpaque(false);
//        Border innerBorder = BorderFactory.createLineBorder(Colors.BACKGROUND.getColor(), 10 , true);
//        Border outerBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE);
//
//        titlePanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
//        titlePanel.setPreferredSize(new Dimension(400, 50));
//
//        // Create the labels
//        closeLabel = createLabel("Zamknij", new Font("SansSerif", Font.BOLD, 24), Color.WHITE);
//        titleLabel = createLabel(title, new Font("SansSerif", Font.BOLD, 20), Color.WHITE);
//
//        // Add labels to the title panel
//        titlePanel.add(titleLabel, SwingConstants.CENTER);
//        titlePanel.add(closeLabel, BorderLayout.EAST);
//
//
//        // Add the title panel to the main panel
//        mainPanel.add(titlePanel, BorderLayout.NORTH);
//
//        contentPanel = new JPanel(new GridLayout(8,1,10,10));
//        contentPanel.setOpaque(false);
//        contentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//        contentPanel.add(createLabel("Twój obecny "+ type +" : \n"+predecessor,20));
//        textField= new JTextField(30);
//        textField.setBackground(Colors.DARK_BLUE_HOVER.getColor());
//        textField.setFont(new Font("SansSerif", Font.BOLD, 20));
//        textField.setForeground(Color.WHITE);
//        textField.setCaretColor(Color.WHITE);
//        textField.setBorder(BorderFactory.createLineBorder(Colors.DARK_BLUE_HOVER.getColor(),2,true));
//        textField.setMargin(new Insets(2,10,2,10));
//        textField.setToolTipText("Email");
//        contentPanel.add(textField);
//        mainPanel.add(contentPanel, BorderLayout.CENTER);
//        submitPanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
//        submitPanel.add(createLabel("Zapisz zmiany", 20), BorderLayout.CENTER);
//        submitPanel.setAlignmentX(SwingConstants.CENTER);
//        submitPanel.setAlignmentY(SwingConstants.CENTER);
//        contentPanel.add(submitPanel);
//
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//            }
//        });
//
//         emailErrorLabel = createLabel("E-mail nie zawiera znaku @ lub . lub ma mniej niż 8 znaków ", 20);
//         emailErrorLabel.setForeground(Color.RED);
//         passwordErrorLabel= createLabel("Hasło musi zawierać przynajmniej 8 znaków", 20);
//         passwordErrorLabel.setForeground(Color.RED);
//         sizeErrorLabel = createLabel("Zbyt dużo znaków",20);
//         postalCodeErrorLabel = createLabel("Niepoprawny kod pocztowy" , 20);
//         sizeErrorLabel.setForeground(Color.RED);
//         emailErrorLabel.setVisible(false);
//         passwordErrorLabel.setVisible(false);
//         sizeErrorLabel.setVisible(false);
//         postalCodeErrorLabel.setVisible(false);
//         contentPanel.add(emailErrorLabel);
//         contentPanel.add(passwordErrorLabel);
//         contentPanel.add(sizeErrorLabel);
//         contentPanel.add(postalCodeErrorLabel);
//         contentPanel.add(postalCodeErrorLabel);
//    }
//
//   @Override
//        protected void paintComponent(Graphics g) {
//            Graphics2D g2d = (Graphics2D) g.create();
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//            // Narysowanie zaokrąglonego tła
//            g2d.setColor(new Color(0,0,0,100));
//            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Zaokrąglone rogi
//
//            // Wywołanie domyślnego renderowania treści (tekst, ikony)
//            super.paintComponent(g);
//
//            g2d.dispose();
//        }
//
//    public JLabel getCloseLabel(){
//        return closeLabel;
//    }
//     public void addMenuListener(Runnable action) {
//        submitPanel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                submitPanel.setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
//                action.run();
//                revalidate();
//                repaint();
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                submitPanel.setBackground(Colors.DARK_BLUE_HOVER.getColor());
//                submitPanel.repaint();
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                submitPanel.setBackground(Colors.DARK_BLUE.getColor());
//                  submitPanel.repaint();
//            }
//        });
//
//    }
//    public String getEnteredText(){
//        return textField.getText();
//    }
//    public JPanel getContentPanel(){
//        return contentPanel;
//    }
//  public boolean validateData() {
//    String news = getEnteredText();
//    boolean haveError = false;
//
//    // Niedozwolone znaki
//    String disallowedChars = "{}[]:;\"'<>?/\\|`~!#$%^&*()=+";
//
//    // Sprawdzenie e-maila
//    if (type.contains("mail")) {
//        boolean invalidEmail = !news.contains("@") || !news.contains(".") || news.length() < 8;
//        boolean containsDisallowedChars = news.chars().anyMatch(ch -> disallowedChars.indexOf(ch) != -1);
//
//        if (invalidEmail || containsDisallowedChars) {
//            emailErrorLabel.setVisible(true);
//            haveError = true;
//        } else {
//            emailErrorLabel.setVisible(false);
//        }
//    }
//
//    // Sprawdzenie długości tekstu
//    if (news.length() > 25) {
//        sizeErrorLabel.setVisible(true);
//        haveError = true;
//    } else {
//        sizeErrorLabel.setVisible(false);
//    }
//
//    // Sprawdzenie hasła
//    if (type.contains("pass") && news.length() < 8) {
//        passwordErrorLabel.setVisible(true);
//        haveError = true;
//    } else if (type.contains("pass")) {
//        passwordErrorLabel.setVisible(false);
//    }
//
//    // Sprawdzenie kodu pocztowego
//    if (type.contains("Kod")) {
//        boolean invalidPostalCode = !news.matches("\\d{2}-\\d{3}"); // Format np. "12-345"
//
//        if (invalidPostalCode) {
//            postalCodeErrorLabel.setVisible(true);
//            haveError = true;
//        } else {
//            postalCodeErrorLabel.setVisible(false);
//        }
//    }
//
//    // Ukrycie błędów, jeśli brak problemów
//    if (!haveError) {
//        emailErrorLabel.setVisible(false);
//        passwordErrorLabel.setVisible(false);
//        sizeErrorLabel.setVisible(false);
//        postalCodeErrorLabel.setVisible(false);
//    }
//
//    contentPanel.repaint();
//    return haveError;
//}
//
//}

import layout.AbstractEditablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Concrete class extending the abstract panel
public class EditableTogglePanel extends AbstractEditablePanel {
    private JPanel submitPanel;
    private JTextField textField;
    private JLabel sizeErrorLabel;
    private JLabel emailErrorLabel;
    private JLabel passwordErrorLabel;
    private JLabel postalCodeErrorLabel;


    public EditableTogglePanel(String title, String type, String predecessor) {
        super(new Dimension(550, 450), title, type);

        // Configure content panel
        contentPanel.setLayout(new GridLayout(8, 1, 10, 10));
        contentPanel.add(createLabel("Twój obecny " + type + " : \n" + predecessor, new Font("SansSerif", Font.BOLD, 20), Color.WHITE));

        textField = new JTextField(30);
        textField.setBackground(Color.DARK_GRAY);
        textField.setFont(new Font("SansSerif", Font.BOLD, 20));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        textField.setMargin(new Insets(2, 10, 2, 10));
        contentPanel.add(textField);

        submitPanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
        submitPanel.add(createLabel("Zapisz zmiany", new Font("SansSerif", Font.BOLD, 20), Color.WHITE));
        contentPanel.add(submitPanel);

        // Add error labels
        sizeErrorLabel = createErrorLabel("Niepoprawna ilość znaków (5 do 25)");
        emailErrorLabel = createErrorLabel("E-mail nie zawiera znaku @ lub . ");
        passwordErrorLabel = createErrorLabel("Hasło musi zawierać przynajmniej 8 znaków");
        postalCodeErrorLabel = createErrorLabel("Niepoprawny kod pocztowy");

        contentPanel.add(sizeErrorLabel);
        contentPanel.add(emailErrorLabel);
        contentPanel.add(passwordErrorLabel);
        contentPanel.add(postalCodeErrorLabel);
    }

    private JLabel createErrorLabel(String text) {
        JLabel label = createLabel(text, new Font("SansSerif", Font.BOLD, 18), Color.RED);
        label.setVisible(false);
        return label;
    }

    @Override
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
    @Override
    public String getEnteredText() {
        return textField.getText();
    }
    @Override
    public boolean validateData() {
        String news = getEnteredText();
        boolean haveError = false;

        // Niedozwolone znaki
        String disallowedChars = "{}[]:;\"'<>?/\\|~!#$%^&*()=+";
        if (news.length() > 25 || news.length() < 4) {
            sizeErrorLabel.setVisible(true);
            haveError = true;
        } else {
            sizeErrorLabel.setVisible(false);
        }

        // Sprawdzenie e-maila
        if (type.contains("mail")) {
            boolean invalidEmail = !news.contains("@") || !news.contains(".") || news.length() < 8;
            boolean containsDisallowedChars = news.chars().anyMatch(ch -> disallowedChars.indexOf(ch) != -1);

            if (invalidEmail || containsDisallowedChars) {
                emailErrorLabel.setVisible(true);

                haveError = true;
            } else {
                emailErrorLabel.setVisible(false);
            }
        }

        // Sprawdzenie długości tekstu
        if (news.length() > 25) {
            sizeErrorLabel.setVisible(true);
            haveError = true;
        } else {
            sizeErrorLabel.setVisible(false);
        }

        // Sprawdzenie hasła
        if (type.contains("pass") && news.length() < 8) {
            passwordErrorLabel.setVisible(true);
            haveError = true;
        } else if (type.contains("pass")) {
            passwordErrorLabel.setVisible(false);
        }

        // Sprawdzenie kodu pocztowego
        if (type.contains("Kod")) {
            boolean invalidPostalCode = !news.matches("\\d{2}-\\d{3}"); // Format np. "12-345"

            if (invalidPostalCode) {
                postalCodeErrorLabel.setVisible(true);
                haveError = true;
            } else {
                postalCodeErrorLabel.setVisible(false);
            }
        }

        // Ukrycie błędów, jeśli brak problemów
        if (!haveError) {
            emailErrorLabel.setVisible(false);
            passwordErrorLabel.setVisible(false);
            sizeErrorLabel.setVisible(false);
            postalCodeErrorLabel.setVisible(false);
        }

        contentPanel.repaint();
        return haveError;
    }
}
