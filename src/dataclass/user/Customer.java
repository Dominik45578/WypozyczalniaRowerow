// Abstract Customer Class
package dataclass.user;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing a Customer.
 */
public abstract class Customer implements Serializable {

    protected String customerId;
    protected String pesel;
    protected String address;
    protected String postalCode;
    protected String city;
    protected String firstName;
    protected String secondName;
    protected String lastName;
    protected int numberOfRentedItems;
    protected Map<String, Object> rentedItems = new HashMap<>();

    public Customer(String customerId, String firstName, String secondName,
                    String lastName, String pesel,String postalCode, String city,
                    String address) {
        this.secondName = secondName;
        this.customerId = customerId;
        this.pesel = pesel;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        numberOfRentedItems = 0;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumberOfRentedItems() {
        return numberOfRentedItems;
    }

    public void setNumberOfRentedItems(int numberOfRentedItems) {
        this.numberOfRentedItems = numberOfRentedItems;
    }

    public Map<String, Object> getRentedItems() {
        return rentedItems;
    }

    public void addRentedItem(String itemId, Object itemDetails) {
        rentedItems.put(itemId, itemDetails);
        numberOfRentedItems++;
    }

    public void removeRentedItem(String itemId) {
        rentedItems.remove(itemId);
        numberOfRentedItems--;
    }
}