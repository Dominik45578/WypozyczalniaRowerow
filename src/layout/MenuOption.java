package layout;

import javax.swing.*;
import java.awt.*;

public class MenuOption implements Screen {
    private final String title;
    private final ImageIcon optionIcon;
    private JPanel optionPanel;

    public MenuOption(String title, ImageIcon optionIcon) {
        this.title = title;
        this.optionIcon = optionIcon;
    }

    public JPanel getPanel() {
        if (optionPanel == null) {
            createPanel();
        }
        return optionPanel;
    }

    private void createPanel() {
        optionPanel = createRoundedPanel(new Color(33, 42, 49));
        optionPanel.setLayout(new BorderLayout());
        optionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel optionLabel = createOptionLabel();
        optionPanel.add(optionLabel, BorderLayout.CENTER);
    }

    private JLabel createOptionLabel() {
        JLabel optionLabel = new JLabel(title);
        optionLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        optionLabel.setForeground(Color.WHITE);
        optionLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        optionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (optionIcon != null) {
            optionPanel.add(new JLabel(optionIcon) , BorderLayout.WEST);
        }

        return optionLabel;
    }
}
