package layout;

import javax.swing.*;
import java.awt.*;

public class EditableField extends JPanel {
    private JLabel label;
    private JTextField textField;
    private JButton editButton;

    public EditableField(String fieldName, String initialValue) {
        this.setMinimumSize(new Dimension(300,150));
        setLayout(new BorderLayout(10, 10));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setBackground(Colors.DARK_BLUE_ACTIVE.getColor());

        // Label for the field name
        label = new JLabel(fieldName + ":");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(label, BorderLayout.WEST);

        // Text field to show the current value
        textField = new JTextField(initialValue);
        textField.setEditable(false); // Initially not editable
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        add(textField, BorderLayout.CENTER);

        // Button for editing the value
        editButton = new JButton("Edytuj");
        editButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        editButton.setBackground(Colors.DARK_BLUE_HOVER.getColor());
        editButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        add(editButton, BorderLayout.EAST);
        editButton.setVisible(false);

        // Add a simple action listener (no functionality for now)
        editButton.addActionListener(e -> {
            // Placeholder: Just enable editing for now
            textField.setEditable(true);
            textField.requestFocus();
        });
    }

    // Methods to get and set values
    public String getValue() {
        return textField.getText();
    }

    public void setValue(String value) {
        textField.setText(value);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rysowanie zaokrąglonego panelu
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rysowanie tła
        g2d.setColor(getBackground() != null ? getBackground() : new Color(33, 42, 49));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Promień zaokrąglenia: 20

        g2d.dispose();
    }
    public JButton getButton(){
        return editButton;
    }
}
