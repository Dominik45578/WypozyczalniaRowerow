
// Updated Bike class
package dataclass.vehicle;

/**
 * Class representing a Bike, extending SingleTrackVehicle.
 */
public class Bike extends SingleTrackVehicle {

    private boolean hasBasket;

    public Bike(String vehicleId, String vehicleName, String vehicleModel, boolean hasBasket) {
        super(vehicleId, vehicleName, vehicleModel);
        this.hasBasket = hasBasket;
    }

    public boolean hasBasket() {
        return hasBasket;
    }

    public void setHasBasket(boolean hasBasket) {
        this.hasBasket = hasBasket;
    }
}
