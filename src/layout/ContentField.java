package layout;

import javax.swing.*;
import java.awt.*;

public class ContentField extends JPanel {
    protected JLabel label;

    public ContentField(String labelText, Color backgroundColor, Dimension preferredSize) {
        // Ustawienia panelu
        setOpaque(false);
        setBackground(backgroundColor);
        setPreferredSize(preferredSize);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout(10, 10));

        // Tworzenie wspólnego JLabel
        label = new JLabel(labelText);
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

    public JLabel getLabel() {
        return label;
    }
}