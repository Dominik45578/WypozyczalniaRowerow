package layout;

import javax.swing.*;
import java.awt.*;

public class ErrorField extends ContentField {
    private JLabel titleLabel;  // Tytuł błędu
    private JLabel messageLabel; // Wyjaśnienie błędu

    public ErrorField(String title , String message){
        this(title,message,new Dimension(100,90));
    }
    public ErrorField(String title, String message, Dimension preferredSize) {
        super(title,new Color(218, 55, 55),preferredSize);

        setPreferredSize(preferredSize);
        titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Wyjaśnienie błędu (normalna czcionka)
        messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel, BorderLayout.CENTER);
    }

    // Metody do aktualizacji treści błędu
    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setMessage(String message) {
        messageLabel.setText("<html><center>" + message + "</center></html>");
    }
}
