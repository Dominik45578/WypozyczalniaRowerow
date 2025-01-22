
// Updated Scooter class
package dataclass.vehicle;

import dataclass.fileoperations.CentralDatabase;

import java.io.Serializable;

/**
 * Class representing a Scooter, extending SingleTrackVehicle.
 */
public class Scooter extends SingleTrackVehicle implements Vehicle, Serializable {

    private int batteryLevel;

    public Scooter(String type,VehicleBrand brand ,VehicleModel model, int batteryLevel) {
        super(type,brand, model);
        this.vehicleId = CentralDatabase.getInstance().getNextID(Vehicle.class,STV_SCOOTER_PREFIX);
        this.batteryLevel = batteryLevel;
    }


    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public boolean needsCharging() {
        return batteryLevel < 20;
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
