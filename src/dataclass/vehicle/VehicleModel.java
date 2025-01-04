package dataclass.vehicle;

import java.io.Serial;
import java.io.Serializable;

public record VehicleModel(String model) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Opcjonalne, ale zalecane

    @Override
    public String toString() {
        return "Model pojazdu "+model;
    }
}
