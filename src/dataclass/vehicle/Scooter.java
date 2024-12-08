
// Updated Scooter class
package dataclass.vehicle;

/**
 * Class representing a Scooter, extending SingleTrackVehicle.
 */
public class Scooter extends SingleTrackVehicle {

    private int batteryLevel;

    public Scooter(String vehicleId, String vehicleName, String vehicleModel, int batteryLevel) {
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
