package dataclass.vehicle;

import dataclass.user.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Interface representing a generic Vehicle.
 */
public interface Vehicle extends Serializable {
    String getStatus();

    void setStatus(String status);

    boolean isRented();

    float getPrice();
    void setPrice(float price);

    User getRenter();

    void rentVehicle(User user);

    void returnVehicle();

    String getVehicleId();

    String getVehicleType();

    String getVehicleModel();

    default boolean isElectric() {
        return false;
    }

    default int getBatteryLevel() {
        return -1;
    }
    default void setBatteryLevel(int level){return;}
    String getVehicleBrandToString();
    VehicleBrand getVehicleBrand();
    String getType();
}
