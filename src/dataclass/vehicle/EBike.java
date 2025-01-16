// Updated EBike class
package dataclass.vehicle;

import java.io.Serializable;

/**
 * Class representing an Electric Bike, extending SingleTrackVehicle.
 */
public class EBike extends SingleTrackVehicle implements Vehicle, Serializable {

    private int batteryLevel;

    public EBike(String vehicleId, String vehicleName, String vehicleModel, int batteryLevel) {
        super(vehicleId, vehicleName, vehicleModel);
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
