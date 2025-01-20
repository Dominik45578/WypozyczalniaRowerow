package layout;

import layout.ContentField;

import javax.swing.*;
import java.awt.*;

public class EditableField extends ContentField {
    protected JTextField textField;
    protected JButton editButton;

    public EditableField(String fieldName, String initialValue) {
        super(fieldName , Colors.DARK_BLUE_ACTIVE.getColor(), new Dimension(300, 150));

        // Text field to show the current value
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(label, BorderLayout.WEST);

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
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        add(editButton, BorderLayout.EAST);
        editButton.setVisible(false);

        // Add a simple action listener (no functionality for now)
        editButton.addActionListener(e -> {
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

    public JButton getButton() {
        return editButton;
    }
}
