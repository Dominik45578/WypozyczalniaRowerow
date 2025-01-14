package dataclass.user;

import dataclass.rental.RentalTransaction;
import dataclass.vehicle.Vehicle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    protected Map<String, Vehicle> rentedItems = new HashMap<>();
    protected Map<String, RentalTransaction> rentedHistory = new HashMap<>();

    public Customer() {
        this("C000", "Dominik", "Michał", "Koralik", "0429265555",
                "31-866", "Kraków", "Skarżyńskiego 9", "dkkd3046@gmail.com");
    }

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

    // Implementacja metod z interfejsu CustomerDetails

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getSecondName() {
        return secondName;
    }

    @Override
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int getNumberOfRentedItems() {
        return numberOfRentedItems;
    }

    @Override
    public void setNumberOfRentedItems(int numberOfRentedItems) {
        this.numberOfRentedItems = numberOfRentedItems;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Map<String, Vehicle> getRentedItems() { return rentedItems; }

    @Override
    public void setRentedItems(Map<String, Vehicle> rentedItems) {
        this.rentedItems = rentedItems;
    }

    @Override
    public Map<String, RentalTransaction> getRentedHistory() {
        return rentedHistory;
    }

    @Override
    public void setRentedHistory(Map<String, RentalTransaction> rentedHistory) {
        this.rentedHistory = rentedHistory;
    }

    @Override
    public void rentItem(String itemId, Vehicle vehicle) {
        rentedItems.put(itemId, vehicle);
    }

    @Override
    public void returnItem(String itemId) {

    }

    @Override
    public void removeRentedItem(String itemId) {

    }
}
