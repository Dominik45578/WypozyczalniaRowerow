package dataclass.vehicle;

import dataclass.user.User;

public class SingleTrackVehicle implements Vehicle {
    boolean isRented;
    String vehicleName;
    String vehicleModel;
    String vehicleID;


    @Override
    public boolean rented() {
        return false;
    }

    @Override
    public User getUser() {
        return null;
    }
}
