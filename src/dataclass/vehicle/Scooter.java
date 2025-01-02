
// Updated Scooter class
package dataclass.vehicle;

/**
 * Class representing a Scooter, extending SingleTrackVehicle.
 */
public class Scooter extends SingleTrackVehicle implements Vehicle{

    private int batteryLevel;
    public Scooter(int value){
        super();
        batteryLevel = value;
    }

    public Scooter(String vehicleId, String vehicleName, String vehicleModel, int batteryLevel) {
        super(vehicleId, vehicleName, vehicleModel);
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
