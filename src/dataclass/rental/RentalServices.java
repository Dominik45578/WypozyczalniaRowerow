package dataclass.rental;

import dataclass.fileoperations.CentralDatabase;
import dataclass.rental.RentalService;
import dataclass.user.Customer;
import dataclass.user.User;
import dataclass.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class RentalServices implements RentalService {

    private static RentalServices instance;

    private final Map<String, Vehicle> inventory = new HashMap<>();
    private final Map<String, Vehicle> rentedVehicles = new HashMap<>();

    private RentalServices() {
        // Prywatny konstruktor zapobiega tworzeniu instancji spoza klasy
    }

    public static synchronized RentalServices getInstance() {
        if (instance == null) {
            instance = new RentalServices();
        }
        return instance;
    }
  public List<RentalTransaction> getTransactionHistory() {
    List<RentalTransaction> transactions = new ArrayList<>();
    Map<String, Customer> customers = CentralDatabase.getInstance().getAllObjects(Customer.class);

    for (Customer customer : customers.values()) {
        transactions.addAll(customer.getRentedHistory().values());
    }

    return transactions;
}

    @Override
    public void addVehicle(Vehicle vehicle) {
        inventory.put(vehicle.getVehicleId(), vehicle);
    }

    @Override
    public boolean removeVehicle(String vehicleId) {
        if (inventory.containsKey(vehicleId)) {
            inventory.remove(vehicleId);
            return true;
        }
        return false;
    }

    @Override
    public boolean rentVehicle(String vehicleId, User user) {
        Vehicle vehicle = inventory.get(vehicleId);
        if (vehicle != null && !vehicle.isRented()) {
            inventory.remove(vehicleId);
            rentedVehicles.put(vehicleId, vehicle);
            vehicle.rentVehicle(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnVehicle(String vehicleId) {
        Vehicle vehicle = rentedVehicles.get(vehicleId);
        if (vehicle != null) {
            rentedVehicles.remove(vehicleId);
            inventory.put(vehicleId, vehicle);
            User user = vehicle.getRenter();
            user.returnItem(vehicleId);
            vehicle.returnVehicle(); // Zakładamy, że Vehicle ma metodę setRented
            return true;
        }
        return false;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(inventory.values());
    }

    @Override
    public List<Vehicle> getRentedVehicles() {
        return new ArrayList<>(rentedVehicles.values());
    }
}
