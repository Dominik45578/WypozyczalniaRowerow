package dataclass.vehicle;

import dataclass.user.User;

/**
 * Interface representing a generic Vehicle.
 */
public interface Vehicle {

    /**
     * Checks if the vehicle is currently rented.
     *
     * @return true if the vehicle is rented, false otherwise.
     */
    boolean isRented();

    /**
     * Gets the user who has rented the vehicle.
     *
     * @return the user renting the vehicle, or null if not rented.
     */
    User getRenter();

    /**
     * Gets the unique identifier of the vehicle.
     *
     * @return the vehicle ID.
     */
    String getVehicleId();

    /**
     * Gets the name of the vehicle.
     *
     * @return the vehicle name.
     */
    String getVehicleName();

    /**
     * Gets the model of the vehicle.
     *
     * @return the vehicle model.
     */
    String getVehicleModel();
}
