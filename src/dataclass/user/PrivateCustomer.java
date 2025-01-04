// PrivateCustomer Class
package dataclass.user;

/**
 * Class representing a private customer.
 */
public class PrivateCustomer extends Customer implements User {

    public PrivateCustomer(String customerId, String firstName, String secondName, String lastName, String pesel, String postalCode, String city, String address) {
        super(customerId, firstName, secondName, lastName, pesel, postalCode, city, address);
    }

    @Override
    public String getId() {
        return customerId;
    }

    @Override
    public Users getUserType() {
        return Users.PRIVATE_CUSTOMER;
    }
}
