package dataclass.rental;

import dataclass.vehicle.Vehicle;
import dataclass.user.User;

/**
 * Class to handle rental transactions.
 */
public class RentalTransaction {

    private final Vehicle vehicle;
    private final User user;
    private final long rentalStart;
    private long rentalEnd;

    public RentalTransaction(Vehicle vehicle, User user) {
        this.vehicle = vehicle;
        this.user = user;
        this.rentalStart = System.currentTimeMillis();
    }

    public void endRental() {
        this.rentalEnd = System.currentTimeMillis();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public User getUser() {
        return user;
    }

    public long getRentalStart() {
        return rentalStart;
    }

    public long getRentalEnd() {

        return rentalEnd;
    }

    public boolean isActive() {
        return rentalEnd == 0;
    }
}