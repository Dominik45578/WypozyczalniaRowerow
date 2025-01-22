package dataclass.user;

import dataclass.fileoperations.CentralDatabase;

/**
 * Class representing a private customer.
 */
public class PrivateCustomer extends Customer {

    public PrivateCustomer(String firstName, String secondName, String lastName,
                           String pesel, String postalCode, String city, String address, String email, String password) {
        super(firstName, secondName, lastName, pesel, postalCode, city, address, email, password);
        this.customerId = CentralDatabase.getInstance().getNextID(User.class,User.PRIVATE_C_PREFIX);
    }


    @Override
    public String getID() {
        return customerId;
    }


    @Override
    public Users getUserType() {
        return Users.PRIVATE_CUSTOMER;
    }
}
