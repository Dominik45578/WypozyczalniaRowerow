
// Updated Bike class
package dataclass.vehicle;

import dataclass.fileoperations.CentralDatabase;

import java.io.Serializable;

/**
 * Class representing a Bike, extending SingleTrackVehicle.
 */
public class Bike extends SingleTrackVehicle implements Vehicle,Serializable {

    protected boolean hasBasket;
    protected boolean forKids;

     public Bike(String type,VehicleBrand brand ,VehicleModel model, boolean hasBasket, boolean forKids) {
        super(type,brand, model);
        this.vehicleId = CentralDatabase.getInstance().getNextID(Vehicle.class,STV_BIKE_PREFIX);
        this.hasBasket = hasBasket;
        this.forKids = forKids;
    }

    public boolean hasBasket() {
        return hasBasket;
    }

    public void setHasBasket(boolean hasBasket) {
        this.hasBasket = hasBasket;
    }

    public boolean isHasBasket() {
        return hasBasket;
    }

    public boolean isForKids() {
        return forKids;
    }

    public void setForKids(boolean forKids) {
        this.forKids = forKids;
    }
}
