package dataclass.rental;

import dataclass.rental.RentalService;
import dataclass.user.User;
import dataclass.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalServices implements RentalService {

    private final Map<String, Vehicle> inventory = new HashMap<>();
    private final Map<String, Vehicle> rentedVehicles = new HashMap<>();

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
            if (vehicle instanceof dataclass.vehicle.SingleTrackVehicle) {
                ((dataclass.vehicle.SingleTrackVehicle) vehicle).rentVehicle(user);
            }
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
            if (vehicle instanceof dataclass.vehicle.SingleTrackVehicle) {
                ((dataclass.vehicle.SingleTrackVehicle) vehicle).returnVehicle();
            }
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