package dataclass.user;

import java.util.Map;

public class PrivateCustomer extends Customer implements User {
    @Override
    public String getID() {
        return customerID;
    }

    @Override
    public int getNumberOfRented() {
        return numberOfRented;
    }

    @Override
    public <K, V> Map<K, V> getRented() {
        return Map.of();
    }

    @Override
    public User getUserType() {
        return null;
    }
}
