package dataclass.user;

import java.util.Map;

/**
 * Interface representing a generic User.
 */
public interface User {

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
     * Gets the type of the user.
     *
     * @return the user type.
     */
    String getUserType();
}