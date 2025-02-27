
package dataclass.user;

import dataclass.rental.RentalTransaction;
import dataclass.vehicle.Vehicle;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface representing a generic User.
 */
public interface User extends Serializable {
    String PRIVATE_C_PREFIX = "PC";
    String BUSINESS_C_PREFIX = "BC";
    String EMPLOYEE_C_PREFIX = "EC";
    String ROOT_PREFIX = "RC";

    String getFirstName();

    void setFirstName(String firstName);

    String getSecondName();

    void setSecondName(String secondName);

    String getLastName();

    void setLastName(String lastName);

    int getNumberOfRentedItems();

    String getEmail();

    void setEmail(String email);

    Map<String, Vehicle> getRentedItems();

    void setRentedItems(Map<String, Vehicle> rentedItems);

    Map<String, RentalTransaction> getRentedHistory();

    String getID();
    boolean rentItem(String itemId, Vehicle vehicle);

    boolean returnItem(String itemId);

    void removeRentedItem(String itemId);

    Users getUserType();

    String getPassword();
    void setPassword(String password);
}
