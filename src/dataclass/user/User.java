
package dataclass.user;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface representing a generic User.
 */
public interface User extends Serializable {

    /**
     * Gets the unique identifier of the user.
     *
     * @return the user ID.
     */
    String getId();

    /**
     * Gets the number of items rented by the user.
     *
     * @return the number of rented items.
     */
    int getNumberOfRentedItems();

    /**
     * Gets the rented items as a map.
     *
     * @return a map containing rented items.
     */
    Map<String, Object> getRentedItems();

    /**
     * Adds a rented item to the user's list by ID.
     *
     * @param itemId the ID of the item to rent.
     * @param itemDetails the details of the item to rent.
     */
    void rentItem(String itemId, Object itemDetails);

    /**
     * Removes a rented item from the user's list by ID.
     *
     * @param itemId the ID of the item to return.
     */
    void returnItem(String itemId);

    /**
     * Removes a rented item by its ID.
     *
     * @param itemId the ID of the item to remove.
     */
    void removeRentedItem(String itemId);

    /**
     * Gets the type of the user.
     *
     * @return the user type.
     */
    Users getUserType();
}
