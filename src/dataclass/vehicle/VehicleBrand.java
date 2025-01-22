package dataclass.vehicle;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleBrand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final Map<String , VehicleModel> models = new HashMap<>();


    public VehicleBrand(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Map<String,VehicleModel> getModels() {
        return models;
    }

    public void addModel(VehicleModel model) {
        models.put(model.model(), model);
    }
    public void addModelFromList(Map<String,VehicleModel> model) {
        models.putAll(model);

    }

}
