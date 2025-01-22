package layout;

import dataclass.user.RootUser;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomPinDialog extends JDialog implements Screen {
    private boolean isPinValid = false;
    private JLabel errorMessageLabel;

    public CustomPinDialog(JFrame parent, RootUser rootUser) {
        super(parent, "Weryfikacja PIN", true); // Modalny dialog
        setUndecorated(true); // Usuń dekoracje okna
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0)); // Przezroczyste tło, umożliwiające zaokrąglenie
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30)); // Zaokrąglone rogi

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Colors.BACKGROUND.getColor()); // Tło okna
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rysowanie zaokrąglonego tła

                // Dodanie czerwonego borderu
                g2d.setColor(Color.RED); // Czerwony kolor ramki
                g2d.setStroke(new BasicStroke(2)); // Ustawienie grubości ramki na 2 px
                g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rysowanie ramki
            }
        };
        contentPanel.setLayout(new GridLayout(4, 1, 5, 5)); // Zmieniono układ, by dodać przestrzeń na komunikat
        contentPanel.setOpaque(false); // Bez domyślnego tła
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPanel);

        // Etykieta
        JLabel messageLabel = new JLabel("Podaj PIN dostępu:");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Etykieta komunikatu o błędzie (początkowo pusta)
        errorMessageLabel = new JLabel("");
        errorMessageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Pole tekstowe (PIN)
        JPasswordField pinField = new JPasswordField(10);
        pinField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        pinField.setHorizontalAlignment(JTextField.CENTER);
        pinField.setBackground(Colors.WRAPPER_GRAY.getColor());
        pinField.setForeground(Color.WHITE);
        pinField.setBorder(BorderFactory.createLineBorder(Colors.DARK_BLUE_ACTIVE.getColor(), 2));
        pinField.setCaretColor(Color.WHITE);

        // Panel z przyciskami
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false); // Bez tła
        JButton confirmButton = createRoundedButton("Potwierdź", 20);
        confirmButton.addActionListener(e -> {
            String inputPin = new String(pinField.getPassword());
            if (inputPin.equals(rootUser.getAccessPin())) {
                isPinValid = true;
                dispose(); // Zamknij okno
            } else {
                // Zaktualizowanie komunikatu o błędzie
                errorMessageLabel.setText("Nieprawidłowy PIN! Spróbuj ponownie.");
            }
        });

        JButton cancelButton = createRoundedButton("Anuluj", 20);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // Dodanie komponentów do dialogu
        contentPanel.add(messageLabel);
        contentPanel.add(pinField);
        contentPanel.add(errorMessageLabel); // Dodanie etykiety błędu
        contentPanel.add(buttonPanel);
    }

    public boolean isPinValid() {
        return isPinValid;
    }
}
