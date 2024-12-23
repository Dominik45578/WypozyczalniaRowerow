package dataclass.rental;

import dataclass.vehicle.Vehicle;
import dataclass.user.User;

import java.time.LocalDateTime;

/**
 * Class to handle rental transactions.
 */
public class RentalTransaction {

    private final Vehicle vehicle;
    private final User user;
    private final LocalDateTime rentalStart;
    private LocalDateTime rentalEnd;

    public RentalTransaction(Vehicle vehicle, User user) {
        this.vehicle = vehicle;
        this.user = user;
        this.rentalStart = LocalDateTime.now();
    }

    public void endRental() {
        this.rentalEnd = LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getRentalStart() {
        return rentalStart;
    }

    public LocalDateTime getRentalEnd() {

        return rentalEnd;
    }

    public boolean isActive() {
        return rentalEnd == null;
    }
}