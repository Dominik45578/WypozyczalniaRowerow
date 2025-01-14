package dataclass.user;

/**
 * Class representing a private customer.
 */
public class PrivateCustomer extends Customer {

    public PrivateCustomer(String customerId, String firstName, String secondName, String lastName,
                           String pesel, String postalCode, String city, String address, String email) {
        super(customerId, firstName, secondName, lastName, pesel, postalCode, city, address, email);
    }

    @Override
    public String getID() {
        return customerId;
    }

    @Override
    public void setID(String id) {
        customerId = id;
    }

    @Override
    public Users getUserType() {
        return Users.PRIVATE_CUSTOMER;
    }
}
