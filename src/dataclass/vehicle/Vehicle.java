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

    void rentVehicle(User user);
    void returnVehicle();
    String getVehicleId();


    /**
     * Gets the name of the vehicle.
     *
     * @return the vehicle name.
     */
    String getVehicleType();

    /**
     * Gets the model of the vehicle.
     *
     * @return the vehicle model.
     */

    /**
     *
     * @return
     */
    String getVehicleModel();
    default boolean isElectric(){
        return false;
    }

    /**
     * Gets the battery level of vehicle.If battery do not exist return -1
     *
     * @return the vehicle battery level (0,100) or -1 if battery do not exist
     */
    default int getBatteryLevel(){
        return -1;
    }
}
