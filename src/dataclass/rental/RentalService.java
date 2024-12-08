package dataclass.rental;

import dataclass.vehicle.Vehicle;
import dataclass.user.User;

import java.util.*;

/**
 * Interface representing core operations of a rental system.
 */
public interface RentalService {

    /**
     * Adds a vehicle to the rental inventory.
     *
     * @param vehicle the vehicle to add.
     */
    void addVehicle(Vehicle vehicle);

    /**
     * Removes a vehicle from the rental inventory.
     *
     * @param vehicleId the ID of the vehicle to remove.
     * @return true if the vehicle was removed, false otherwise.
     */
    boolean removeVehicle(String vehicleId);

    /**
     * Rents a vehicle to a user.
     *
     * @param vehicleId the ID of the vehicle to rent.
     * @param user the user renting the vehicle.
     * @return true if the rental was successful, false otherwise.
     */
    boolean rentVehicle(String vehicleId, User user);

    /**
     * Returns a rented vehicle to the inventory.
     *
     * @param vehicleId the ID of the vehicle to return.
     * @return true if the return was successful, false otherwise.
     */
    boolean returnVehicle(String vehicleId);

    /**
     * Gets a list of all vehicles in the rental inventory.
     *
     * @return a list of all vehicles.
     */
    List<Vehicle> getAllVehicles();

    /**
     * Gets a list of all rented vehicles.
     *
     * @return a list of all rented vehicles.
     */
    List<Vehicle> getRentedVehicles();
}