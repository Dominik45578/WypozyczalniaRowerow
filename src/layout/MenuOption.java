package layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuOption implements Screen {
    private final String title;
    private final ImageIcon optionIcon;
    private JPanel optionPanel;
    private JLabel optionLabel;

    public MenuOption(String title, ImageIcon optionIcon) {
        //this.setOpaque(false); // Panel MenuOption jest przezroczysty
        this.title = title;
        this.optionIcon = optionIcon;
    }

    // Getter dla optionPanel (leniwe tworzenie)
    public JPanel getPanel() {
        if (optionPanel == null) {
            createPanel();
        }
        return optionPanel;
    }

    // Tworzenie głównego panelu opcji
    private void createPanel() {
        optionPanel = createRoundedPanel(new Color(33, 42, 49)); // Kolor tła
        optionPanel.setLayout(new BorderLayout());
        optionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        optionPanel.setOpaque(true);
        createOptionLabel();
        optionPanel.add(optionLabel, BorderLayout.CENTER);

        // Dodanie efektów myszki
        addMouseEffect();
    }

    // Tworzenie etykiety opcji z tytułem i ikoną
    private void createOptionLabel() {
        optionLabel = new JLabel(title);
        optionLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        optionLabel.setForeground(Color.WHITE);
        optionLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        optionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (optionIcon != null) {
            optionPanel.add(new JLabel(optionIcon) , BorderLayout.WEST);
        }

    }


    // Dodanie efektów myszki do panelu
    private void addMouseEffect() {
        optionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                optionPanel.setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
                optionPanel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                optionPanel.setBackground(Colors.DARK_BLUE_ACTIVE.getColor());
                optionPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                optionPanel.setBackground(Colors.DARK_BLUE.getColor());
                optionPanel.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                optionPanel.setOpaque(true);
                optionPanel.setBackground(Colors.DARK_BLUE_HOVER.getColor());
                optionPanel.setOpaque(false);
                optionPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                optionPanel.setOpaque(true);
                optionPanel.setBackground(Colors.DARK_BLUE.getColor());
                optionPanel.setOpaque(true);
                optionPanel.repaint();
            }
        });
    }

    // Ustawienie koloru tła dla opcji
    public void setOptionBackground(Color color) {
        if (optionPanel != null) {
            optionPanel.setBackground(color);
            optionPanel.repaint();
        }
    }
}
