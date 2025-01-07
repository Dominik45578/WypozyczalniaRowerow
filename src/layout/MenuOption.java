package layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuOption extends JPanel implements Screen {
    private final String title;
    private final ImageIcon optionIcon;
    private JLabel iconLabel;
    private JLabel textLabel;
    private boolean isActive;


    public MenuOption(String title, ImageIcon optionIcon) {
        this.title = title;
        this.optionIcon = optionIcon;

        // Konfiguracja głównego panelu
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setOpaque(false); // Panel będzie zaokrąglony z własnym rysowaniem

        // Tworzenie ikonki i etykiety
        createComponents();

        // Dodanie efektów myszki
        addMouseEffect();
    }

    private void createComponents() {
        // Ikona po lewej stronie
        if (optionIcon != null) {
            iconLabel = new JLabel(optionIcon);
            iconLabel.setHorizontalAlignment(SwingConstants.LEFT);
            add(iconLabel, BorderLayout.WEST);
        }

        // Tekst wyśrodkowany
        textLabel = new JLabel(title);
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(textLabel, BorderLayout.CENTER);
    }

    private void addMouseEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(Colors.DARK_BLUE.getColor());
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Colors.DARK_BLUE_HOVER.getColor());
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(isActive) {
                    setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
                    return;
                }
                setBackground(Colors.DARK_BLUE.getColor());
                repaint();
            }
        });
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

    public boolean isActive() {
        return isActive;
    }
    public void setIsActive(boolean active){
        isActive = active;
    }
}
