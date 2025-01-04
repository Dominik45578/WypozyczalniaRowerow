import dataclass.fileoperations.CentralDatabase;
import dataclass.vehicle.VehicleBrand;
import dataclass.vehicle.VehicleModel;
import layout.LoginScreen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CentralDatabase database = CentralDatabase.getInstance();
        VehicleBrand brand = new VehicleBrand("Kross");
        List<VehicleModel> models = new ArrayList<>();
        models.add(new VehicleModel("Cross"));

        brand.addModelFromList(models);
        database.addObject(VehicleBrand.class, brand.getName(), brand);

        /* tak wygląda powyższa metoda :
        * public <T extends Serializable> void registerManager(Class<T> type, FileDataManager<T> manager) throws IOException {
        managers.put(type, manager);
        cachedData.put(type, manager.loadAll()); // Wczytujemy dane przy rejestracji
        * */
    }

}


