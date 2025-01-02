package dataclass.vehicle;

import java.io.Serial;
import java.io.Serializable;

public record ScooterModel(ScooterBrand brand, String name) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Opcjonalne, ale zalecane

    @Override
    public String toString() {
        return "Marka : "+brand+", Model :"+name;
    }
}
