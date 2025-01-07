package dataclass.vehicle;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VehicleBrand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final List<VehicleModel> models = new ArrayList<>();

    public VehicleBrand(String name, List<VehicleModel> models){
        this.name = name;
        this.models.addAll(models);
    }

    public VehicleBrand(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public List<VehicleModel> getModels() {
        return models;
    }

    public void addModel(VehicleModel model) {
        models.add(model);
    }
    public void addModelFromList(List<VehicleModel> model) {
        if(!models.isEmpty()) {
            models.addAll(model);
        }
    }

    @Override
    public String toString() {
        return "Brand: " + name + ", Models: " + models;
    }
}
