package dataclass.vehicle;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class  ScooterBrand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Opcjonalne, ale zalecane
    private final String name;
    private final List<ScooterModel> models = new ArrayList<>();

    public ScooterBrand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ScooterModel> getModels() {
        return models;
    }

    public void addModel(ScooterModel model) {
        models.add(model);
    }

    @Override
    public String toString() {
        return "Brand: " + name + ", Models: " + models;
    }
}
