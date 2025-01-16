package layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class FormWrapper extends JPanel {
    private JTextField textField;
    private JLabel titleLabel;
    private String placeholder;
    private Predicate<String> singleValidationMethod;
    private BiPredicate<String, String> doubleValidationMethod;
    private FormWrapper relatedFormWrapper;
    private boolean visible;

    // Konstruktor dla walidacji z 1 argumentem i field typu uklrytego
    public FormWrapper(boolean visible, String title, String placeholder, Predicate<String> validationMethod) {
        this.placeholder = placeholder;
        this.singleValidationMethod = validationMethod;
        this.visible = true;

        setupUI(title);

    }

    public FormWrapper(boolean visible, String title, String placeholder, BiPredicate<String, String> validationMethod, FormWrapper relatedFormWrapper) {
        this.placeholder = placeholder;
        this.doubleValidationMethod = validationMethod;
        this.relatedFormWrapper = relatedFormWrapper;
        this.visible = true;
        setupUI(title);
    }

    public FormWrapper(String title, String placeholder, BiPredicate<String, String> validationMethod, FormWrapper relatedFormWrapper) {
        this.placeholder = placeholder;
        this.doubleValidationMethod = validationMethod;
        this.relatedFormWrapper = relatedFormWrapper;
        this.visible = false;

        setupUI(title);
    }

    // Konstruktor dla walidacji z jednym argumentem
    public FormWrapper(String title, String placeholder, Predicate<String> validationMethod) {
        this.placeholder = placeholder;
        this.singleValidationMethod = validationMethod;
        this.visible = false;

        setupUI(title);
    }

    private void setupUI(String title) {
        // Ustawienia panelu
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(false);

        // Tworzenie tytułu
        titleLabel = new JLabel(title + " : ");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        // Tworzenie pola tekstowego
        if (visible) {
            textField = new JPasswordField(placeholder);
        } else {
            textField = new JTextField(placeholder);
        }

        textField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        textField.setBackground(new Color(97, 97, 97));
        textField.setMargin(new Insets(0, 20, 0, 20));
        textField.setBorder(null);
        textField.setCaretColor(Color.WHITE);
        textField.setForeground(Color.LIGHT_GRAY);

        // Placeholder logika
        addListener();

        // Dodanie komponentów do panelu
        add(titleLabel, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
    }

    // Pobieranie wartości z pola tekstowego
    public String getValue() {
        String value = textField.getText();
        return value.equals(placeholder) ? "" : value;
    }

    // Walidacja danych z jednym argumentem
    public boolean validateInput() {
        if (singleValidationMethod != null) {
            return singleValidationMethod.test(getValue());
        }
        return false;
    }

    // Walidacja danych z dwoma argumentami
    public boolean validateInputWithRelatedField() {
        if (doubleValidationMethod != null && relatedFormWrapper != null) {
            return doubleValidationMethod.test(getValue(), relatedFormWrapper.getValue());
        }
        return true;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setSingleValidationMethod(Predicate<String> singleValidationMethod) {
        this.singleValidationMethod = singleValidationMethod;
    }

    public BiPredicate<String, String> getDoubleValidationMethod() {
        return doubleValidationMethod;
    }

    public void setDoubleValidationMethod(BiPredicate<String, String> doubleValidationMethod, FormWrapper relatedFormWrapper) {
        this.doubleValidationMethod = doubleValidationMethod;
        this.relatedFormWrapper = relatedFormWrapper;
    }

    public FormWrapper getRelatedFormWrapper() {
        return relatedFormWrapper;
    }

    public void setRelatedFormWrapper(FormWrapper relatedFormWrapper) {
        this.relatedFormWrapper = relatedFormWrapper;
    }

    public void addListener() {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setForeground(Color.WHITE);
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (singleValidationMethod != null) {
                    if (textField.getText().isEmpty()) {
                        textField.setText(placeholder);
                        textField.setForeground(Color.LIGHT_GRAY);
                    } else if (!validateInput()) {
                        textField.setForeground(Color.RED);
                    } else {
                        textField.setForeground(Color.GREEN);
                    }
                } else if (doubleValidationMethod != null) {
                    if (textField.getText().isEmpty()) {
                        textField.setText(placeholder);
                        textField.setForeground(Color.LIGHT_GRAY);
                    } else if (!validateInputWithRelatedField()) {
                        textField.setForeground(Color.RED);
                    } else {
                        textField.setForeground(Color.GREEN);
                        if (Objects.equals(relatedFormWrapper.getTextField().getText(), getTextField().getText())) {
                            relatedFormWrapper.getTextField().setForeground(Color.GREEN);
                        }
                    }
                }

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(85, 84, 84));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
