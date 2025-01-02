package layout;

import java.awt.Color;

public enum Colors {
    BACKGROUND(new Color(68, 68, 68)),
    DARK_BLUE(new Color(33, 42, 49));

    private final Color color;        // Obiekt java.awt.Color

    // Konstruktor
    Colors(Color color) {
        this.color = color;
    }

    // Getter dla obiektu Color
    public Color getColor() {
        return color;
    }
}
