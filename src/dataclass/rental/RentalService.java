package dataclass.rental;

import dataclass.vehicle.Vehicle;
import dataclass.user.User;
import dataclass.vehicle.VehicleBrand;
import dataclass.vehicle.VehicleModel;

import java.util.*;

/**
 * Interface representing core operations of a rental system.
 */
public interface RentalService {

    void addUser(User user);
    void addVehicle(Vehicle vehicle);
    void addBrand(VehicleBrand brand);
    void addModel(VehicleBrand brand,VehicleModel model);
    boolean removeVehicle(String vehicleId);
    boolean rentVehicle(String vehicleId, User user);
    boolean returnVehicle(String vehicleId);
    List<Vehicle> getAllVehicles();
    List<Vehicle> getRentedVehicles();
    List<Vehicle> getBrokenVehicle();
    boolean emailExistForPrivate(String email);
    boolean emailExistForBusiness(String email);
    boolean peselExist(String pesel);
    boolean nipExist(String nip);
    boolean vehicleExistByID(String id);
    boolean brandExistByID(String id);
    User filterUserByEmail(String email);


}