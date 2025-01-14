package layout;

import java.awt.Color;

public enum Colors {
    BACKGROUND(new Color(68, 68, 68)),
    DARK_BLUE(new Color(33, 42, 49)),
    DARK_BLUE_HOVER(new Color(38, 47, 54)),
    DARK_BLUE_ACTIVE(new Color(20, 29, 36)),
    WRAPPER_GRAY(new Color(97, 97, 97));
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
