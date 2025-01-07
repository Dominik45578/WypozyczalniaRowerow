package dataclass.user;

import dataclass.rental.RentalTransaction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing a Customer.
 */
public abstract class Customer implements User, Serializable {

    protected String customerId;
    protected String pesel;
    protected String address;
    protected String postalCode;
    protected String city;
    protected String firstName;
    protected String secondName;
    protected String lastName;
    protected int numberOfRentedItems;
    protected String email;
    protected String phoneNumber;
    protected Map<String, Object> rentedItems = new HashMap<>();
    protected Map<String , RentalTransaction> rentedHistory = new HashMap<>();

    public Customer(String customerId, String firstName, String secondName,
                    String lastName, String pesel, String postalCode, String city,
                    String address, String email) {
        this.secondName = secondName;
        this.customerId = customerId;
        this.pesel = pesel;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        numberOfRentedItems = 0;
        this.phoneNumber = "Brak";
    }

    // Getters and Setters

    public void setRentedItems(Map<String, Object> rentedItems) {
        this.rentedItems = rentedItems;
    }

    public Map<String, RentalTransaction> getRentedHistory() {
        return rentedHistory;
    }

    public void setRentedHistory(Map<String, RentalTransaction> rentedHistory) {
        this.rentedHistory = rentedHistory;
    }

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

    public void setNumberOfRentedItems(int numberOfRentedItems) {
        this.numberOfRentedItems = numberOfRentedItems;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int getNumberOfRentedItems() {
        return numberOfRentedItems;
    }

    @Override
    public Map<String, Object> getRentedItems() {
        return rentedItems;
    }

    @Override
    public void rentItem(String itemId, Object itemDetails) {
        rentedItems.put(itemId, itemDetails);
        numberOfRentedItems++;
    }

    @Override
    public void returnItem(String itemId) {
        rentedItems.remove(itemId);
        numberOfRentedItems--;
    }

    @Override
    public void removeRentedItem(String itemId) {
        rentedItems.remove(itemId);
        numberOfRentedItems--;
    }

    @Override
    public String getId() {
        return customerId;
    }

    @Override
    public Users getUserType() {
        return null; // To be implemented by subclasses
    }
}
