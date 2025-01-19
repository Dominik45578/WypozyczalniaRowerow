package dataclass.user;

import dataclass.rental.RentalServices;
import dataclass.rental.RentalTransaction;
import dataclass.vehicle.Vehicle;
import dataclass.vehicle.VehicleBrand;
import dataclass.vehicle.VehicleModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa reprezentująca administratora
 */
public class RootUser  implements User {
    private String firstName;
    private String secondName;
    private String surName;
    private String email;
    private String password;
    private  String id;

    public RootUser(String id) {
        super();
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
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
        return surName;
    }

    @Override
    public void setLastName(String lastName) {
        this.surName= lastName;
    }

    @Override
    public int getNumberOfRentedItems() {
        return RentalServices.getInstance().getRentedVehicles().size();
    }

    @Override
    public void setNumberOfRentedItems(int numberOfRentedItems) {

    }

    @Override
    public String getEmail() {
        return  email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;

    }

    @Override
    public Map<String, Vehicle> getRentedItems() {
        List<Vehicle> vehicles= RentalServices.getInstance().getRentedVehicles();
        Map<String,Vehicle> vehicleMap = new HashMap<>();
        for(Vehicle v : vehicles){
            vehicleMap.put(v.getVehicleId(), v);
        }
        return vehicleMap;
    }

    @Override
    public void setRentedItems(Map<String, Vehicle> rentedItems) {

    }

    @Override
    public Map<String, RentalTransaction> getRentedHistory() {
        return Map.of();
    }

    @Override
    public void setRentedHistory(Map<String, RentalTransaction> rentedHistory) {

    }

    @Override
    public void rentItem(String itemId, Vehicle vehicle) {

    }

    @Override
    public void returnItem(String itemId) {

    }

    @Override
    public void removeRentedItem(String itemId) {

    }

    @Override
    public Users getUserType() {
        return Users.ROOT;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
            this.password = password;
    }

}
