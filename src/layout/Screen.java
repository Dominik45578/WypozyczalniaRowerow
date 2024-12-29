
package layout;

import javax.swing.*;
import java.awt.*;

public interface Screen {
    default JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 173, 181),
                        getWidth(), getHeight(), new Color(255, 77, 77));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    default JPanel createRoundedPanel(Color color) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
    }

    default JButton createRoundedButton(String text, Color background, Color foreground, Font font) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(background);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Zaokrąglenie rogów
            super.paintComponent(g2d);
            g2d.dispose();
        }

        @Override
        public void setContentAreaFilled(boolean b) {
            // Ignorujemy ustawienia wypełnienia
        }
    };

    button.setForeground(foreground);
    button.setFont(font);
    button.setFocusPainted(false);
    button.setOpaque(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Opcjonalne odstępy wewnętrzne
    return button;
}
 default JButton createButton(String text, Color background, Color foreground, Font font) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(font);
        button.setFocusPainted(false);
        return button;
    }

    default JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    default JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        return textField;
    }

    default JCheckBox createCheckBox(String text, Color background, Color Foreground,Font font) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setBackground(background);
        checkBox.setFont(font);
        checkBox.setForeground(Foreground);
        return checkBox;
    }

    default JPanel createFormWrapper(String title) {
        JPanel formWrapper = createRoundedPanel(new Color(85, 84, 84));//new Color(218, 237, 248));
        formWrapper.setLayout(new BorderLayout(10,20));
        formWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formWrapper.setOpaque(false);
        JLabel formTitle = new JLabel(title + " : ");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        formTitle.setForeground(Color.WHITE);
        JTextField formTextField = new JTextField();
        formTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        formTextField.setBackground(new Color(97, 97, 97));
        formTextField.setMargin(new Insets(0,20,0,20));
        formTextField.setBorder(null);
        formTextField.setCaretColor(Color.WHITE);
        formTextField.setForeground(Color.WHITE);
        formWrapper.add(formTitle, BorderLayout.NORTH);
        formWrapper.add(formTextField, BorderLayout.CENTER);
        return formWrapper;
    }
}
