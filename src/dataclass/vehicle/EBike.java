// Updated EBike class
package dataclass.vehicle;

import dataclass.fileoperations.CentralDatabase;

import java.io.Serializable;

/**
 * Class representing an Electric Bike, extending SingleTrackVehicle.
 */
public class EBike extends SingleTrackVehicle implements Vehicle, Serializable {

    private int batteryLevel;

    public EBike(String type,VehicleBrand brand ,VehicleModel model, int batteryLevel) {
        super(type,brand, model);
        this.vehicleId = CentralDatabase.getInstance().getNextID(Vehicle.class,STV_E_BIKE_PREFIX);
        this.batteryLevel = batteryLevel;
    }


    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public boolean needsCharging() {
        return batteryLevel < 15;
    }
    @Override
    public boolean isElectric() {
        return true;
    }
    @Override
    public int getBatteryLevel() {
        return batteryLevel;
    }
}
