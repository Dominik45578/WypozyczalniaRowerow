
// Updated SingleTrackVehicle class
package dataclass.vehicle;

import dataclass.user.User;

import java.io.Serializable;

/**
 * Class representing a single-track vehicle.
 */
public class SingleTrackVehicle implements Vehicle, Serializable {
    public static String STV_BIKE_PREFIX = "STVB";
    public static String STV_E_BIKE_PREFIX = "STVEB";
    public static String STV_SCOOTER_PREFIX= "STVST";
    public static final String BROKEN ="broken";
    public static final String OCCUPIED ="occupied";
    public static final String FREE ="free";

    private boolean rented;
    private User renter;
    private final String vehicleType;
    private final String vehicleModel;
    private final String vehicleId;
    private String status;


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
    public String getStatus(){
        return status;
    }
    @Override
    public void setStatus(String status){
        this.status = status;
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
        this.status = OCCUPIED;
    }

    // Clear renter and update rented status
    public void returnVehicle() {
        this.renter = null;
        this.rented = false;
        this.status = FREE;
    }

}
