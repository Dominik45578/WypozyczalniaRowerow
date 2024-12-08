// PrivateCustomer Class
package dataclass.user;

/**
 * Class representing a private customer.
 */
public class PrivateCustomer extends Customer implements User {

    @Override
    public String getId() {
        return customerId;
    }

    @Override
    public String getUserType() {
        return "PrivateCustomer";
    }
}
