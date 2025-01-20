package layout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ErrorsTogglePanel extends AbstractEditablePanel {
    JPanel errorsPanel;

    public ErrorsTogglePanel(Dimension dimension, String title) {
        super(dimension, title, title);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inicjalizacja errorsPanel przed dodaniem komponentów
        errorsPanel = new JPanel();
        errorsPanel.setLayout(new BoxLayout(errorsPanel, BoxLayout.Y_AXIS));
        errorsPanel.setBackground(Colors.BACKGROUND.getColor());

        // Dodanie przykładowych błędów
        errorsPanel.add(new ErrorField("Błąd Email", "Email musi zawierać znaki @ i .", new Dimension(100, 100)));
        errorsPanel.add(Box.createVerticalStrut(10));
        errorsPanel.add(new ErrorField("Błąd Email", "Email musi zawierać znaki @ i .", new Dimension(100, 100)));
        errorsPanel.add(Box.createVerticalStrut(10));
        errorsPanel.add(new ErrorField("Błąd Email", "Email musi zawierać znaki @ i .", new Dimension(100, 100)));
        errorsPanel.add(Box.createVerticalStrut(10));
        errorsPanel.add(new ErrorField("Błąd Email", "Email musi zawierać znaki @ i .", new Dimension(100, 100)));
        errorsPanel.add(Box.createVerticalStrut(10));
        errorsPanel.add(new ErrorField("Błąd Email", "Email musi zawierać znaki @ i .", new Dimension(100, 80)));
        errorsPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 5));
        // Tworzenie JScrollPane dla panelu błędów
        JScrollPane scrollPane = new JScrollPane(errorsPanel);
        scrollPane.setBackground(Colors.BACKGROUND.getColor());
        setScrollPane(scrollPane);
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setElementVisible(false);
            }
        });
        contentPanel.add(scrollPane);
    }

    void add(ErrorField error) {
        Component[] components = errorsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof ErrorField) {
                ErrorField f = (ErrorField) component;
                if (f.getLabel().equals(error.getLabel())) {
                    // Jeśli istnieje komponent z takim samym label, kończymy metodę
                    return;
                }
            }
        }
        // Dodajemy nowy ErrorField
        errorsPanel.add(error);
        errorsPanel.add(Box.createVerticalStrut(10)); // Przerwa między elementami
        errorsPanel.revalidate(); // Aktualizacja układu panelu
        errorsPanel.repaint();    // Odświeżenie widoku
    }

    void del(ErrorField error) {
        Component[] components = errorsPanel.getComponents();
        for (Component component : components) {
            if (error.equals(component)) {
                errorsPanel.remove(component);
            }
        }
        errorsPanel.revalidate();
        errorsPanel.repaint();
    }

    void setScrollPane(JScrollPane scrollPane) {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Colors.DARK_BLUE.getColor());
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Colors.BACKGROUND.getColor());
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setPreferredSize(new Dimension(5, 0)); // Ustawienie szerokości przewijaka
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Colors.DARK_BLUE.getColor(); // Kolor suwaka
                this.trackColor = Colors.BACKGROUND.getColor(); // Kolor tła przewijaka
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return invisibleScrollBarButton(); // Ukrycie przycisków góra/dół
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return invisibleScrollBarButton();
            }

            private JButton invisibleScrollBarButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setVisible(false);
                return button;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(thumbColor);
                g2.fillRoundRect(
                        thumbBounds.x, thumbBounds.y,
                        thumbBounds.width, thumbBounds.height,
                        10, 10 // Promień zaokrąglenia
                );
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(trackColor);
                g2.fillRoundRect(
                        trackBounds.x, trackBounds.y,
                        trackBounds.width, trackBounds.height,
                        10, 10 // Promień zaokrąglenia
                );
            }
        });

        contentPanel.add(scrollPane, BorderLayout.CENTER); // Dodanie JScrollPane do contentPanel
    }

    @Override
    void addMenuListener(Runnable action) {

    }

    @Override
    String getEnteredText() {
        return "";
    }

    @Override
    boolean validateData() {
        return false;
    }
}
