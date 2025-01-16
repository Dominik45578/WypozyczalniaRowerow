package dataclass.rental;

import dataclass.vehicle.Vehicle;
import dataclass.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class to handle rental transactions.
 */
public class RentalTransaction implements Serializable{
    private static int lastNumber;
    private final Vehicle vehicle;
    private final User user;
    private final LocalDateTime rentalStart;
    private LocalDateTime rentalEnd;
    private final String transactionID;

    public RentalTransaction(Vehicle vehicle, User user) {
        this.vehicle = vehicle;
        this.user = user;
        this.rentalStart = LocalDateTime.now();
        this.transactionID = user.getID()+vehicle.getVehicleId()+rentalStart+lastNumber;
        lastNumber++;
        user.rentItem(vehicle.getVehicleId(), vehicle);
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
        //rentalEnd = LocalDateTime.now().minusDays(10);
        return rentalEnd;
    }
    public String getTransactionID(){
        return transactionID;
    }

    public boolean isActive() {
        return rentalEnd == null;
    }
}