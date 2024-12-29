// Supplementary classes for rental management
package dataclass.rental;

import java.util.*;
import dataclass.vehicle.Vehicle;

/**
 * Klasa do zarządzania inwentarzem wypożyczalni
 */
public class RentalInventory {

    private final Map<String, Vehicle> inventory = new HashMap<>();

    /**
     * Dodaje pojazd do inwentarza
     *
     * @param vehicle pojazd do dodania
     */
    public void addVehicle(Vehicle vehicle) {
        inventory.put(vehicle.getVehicleId(), vehicle);
    }

    /**
     * Removes a vehicle from the inventory.
     *
     * @param vehicleId the ID of the vehicle to remove.
     * @return true if removed successfully, false otherwise.
     */
    public boolean removeVehicle(String vehicleId) {
        return inventory.remove(vehicleId) != null;
    }

    /**
     * Gets a vehicle by ID.
     *
     * @param vehicleId the ID of the vehicle to retrieve.
     * @return the vehicle, or null if not found.
     */
    public Vehicle getVehicle(String vehicleId) {
        return inventory.get(vehicleId);
    }

    /**
     * Gets all vehicles in the inventory.
     *
     * @return a collection of vehicles.
     */
    public Collection<Vehicle> getAllVehicles() {
        return Collections.unmodifiableCollection(inventory.values());
    }
}
