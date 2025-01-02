
// Updated SingleTrackVehicle class
package dataclass.vehicle;

import dataclass.user.User;

/**
 * Class representing a single-track vehicle.
 */
public class SingleTrackVehicle implements Vehicle {

    private boolean rented;
    private String vehicleType;
    private String vehicleModel;
    private String vehicleId;
    private User renter;

    // Constructor
    public SingleTrackVehicle(){
        this("D001","Rower","Glowrer");
    }
    public SingleTrackVehicle(String vehicleId, String vehicleName, String vehicleModel) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleName;
        this.vehicleModel = vehicleModel;
        this.rented = true;
        this.renter = null;
    }

    @Override
    public boolean isRented() {
        return rented;
    }

    @Override
    public User getRenter() {
        return renter;
    }

    @Override
    public String getVehicleId() {
        return vehicleId;
    }

    @Override
    public String getVehicleType() {
        return vehicleType;
    }

    @Override
    public String getVehicleModel() {
        return vehicleModel;
    }

    // Set renter and update rented status
    @Override
    public void rentVehicle(User user) {
        this.renter = user;
        this.rented = true;
    }

    // Clear renter and update rented status
    public void returnVehicle() {
        this.renter = null;
        this.rented = false;
    }
}
