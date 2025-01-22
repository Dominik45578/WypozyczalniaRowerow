
// Updated SingleTrackVehicle class
package dataclass.vehicle;

import dataclass.user.User;

import java.io.Serializable;

/**
 * Class representing a single-track vehicle.
 */
public class SingleTrackVehicle implements Vehicle , Serializable {
    public static String STV_BIKE_PREFIX = "STVB";
    public static String STV_E_BIKE_PREFIX = "STVEB";
     public static String STV_SCOOTER_PREFIX= "STVST";
    public static final String BROKEN ="broken";
    public static final String OCCUPIED ="occupied";
    public static final String FREE ="free";
    public static final String BIKE = "Rower";
    public static final String EBIKE = "Rower Elektryczny";
    public static final String ESCOOTER = "Hulajnoga Elektryczna";
    public static final String MTB = "Górski";
    public static final String SZOSA = "Szosowy";
    public static final String MIASTO = "Miejski";





    protected boolean rented;
    protected User renter;
    protected String vehicleType;
    protected final VehicleBrand vehicleBrand;
    protected final VehicleModel vehicleModel;
    protected String vehicleId;
    protected String status;
    protected float price;
    protected String type;



    public SingleTrackVehicle(String  type ,VehicleBrand vehicleBrand, VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
        this.vehicleBrand = vehicleBrand;
        this.type = type;
        this.rented = false;
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
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
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
        return vehicleModel.model();
    }

    // Set renter and update rented status
    @Override
    public void rentVehicle(User user) {
        this.renter = user;
        this.rented = true;
        this.status = OCCUPIED;
    }

    @Override
    public String getVehicleBrandToString() {
        return vehicleBrand.getName();
    }

    @Override
    public VehicleBrand getVehicleBrand() {
        return vehicleBrand;
    }

    @Override
    public String getType() {
        return type;
    }

    // Clear renter and update rented status
    public void returnVehicle() {
        this.renter = null;
        this.rented = false;
        this.status = FREE;
    }

}
