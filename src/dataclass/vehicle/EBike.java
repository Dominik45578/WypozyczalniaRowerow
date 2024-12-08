// Updated EBike class
package dataclass.vehicle;

/**
 * Class representing an Electric Bike, extending SingleTrackVehicle.
 */
public class EBike extends SingleTrackVehicle {

    private int batteryLevel;

    public EBike(String vehicleId, String vehicleName, String vehicleModel, int batteryLevel) {
        super(vehicleId, vehicleName, vehicleModel);
        this.batteryLevel = batteryLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public boolean needsCharging() {
        return batteryLevel < 20;
    }
}
