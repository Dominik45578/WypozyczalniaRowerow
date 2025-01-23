package dataclass.user;

import dataclass.fileoperations.CentralDatabase;
import dataclass.rental.RentalServices;
import dataclass.rental.RentalTransaction;
import dataclass.vehicle.Vehicle;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Customer implements User, Serializable {
    protected String customerId;
    protected String pesel;
    protected String address;
    protected String postalCode;
    protected String city;
    protected String firstName;
    protected String secondName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected float saldo;

    protected Map<String, Vehicle> rentedItems = new HashMap<>();

    public Customer() {
        this("Dominik", "Michał", "Koralik", "0429265555",
                "31-866", "Kraków", "Skarżyńskiego 9", "dkkd3046@gmail.com", "Dominik456");
    }

    public Customer(String firstName, String secondName,
                    String lastName, String pesel, String postalCode, String city,
                    String address, String email, String password) {
        this.secondName = secondName;
        this.pesel = pesel;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = "Brak";
        this.saldo = 0;
    }

    public float getSaldo() {
        return saldo;
    }

    public String getSaldoString() {
        return String.valueOf(saldo);
    }

    public void addToSaldo(float saldo) {
        this.saldo += saldo;
    }
    public void setSaldo(String saldo){
        this.saldo += Integer.parseInt(saldo);
    }

    public void addToSaldoString(String saldo) {
        this.saldo += Integer.parseInt(saldo);
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
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
        return rentedItems.keySet().size();
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
    public Map<String, Vehicle> getRentedItems() {
        return rentedItems;
    }

    @Override
    public void setRentedItems(Map<String, Vehicle> rentedItems) {
        this.rentedItems = rentedItems;
    }

    @Override
    public Map<String, RentalTransaction> getRentedHistory() {
        // Pobranie wszystkich transakcji z pamięci podręcznej
        Map<String, RentalTransaction> allTransactions = (Map<String, RentalTransaction>) CentralDatabase.getInstance()
                .getCachedData()
                .get(RentalTransaction.class);

        // Filtrowanie transakcji po ID użytkownika
        return allTransactions.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getUser().getID().equals(this.customerId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean rentItem(String itemId, Vehicle vehicle) {
        if(2*vehicle.getPrice()>saldo){
            return false;
        }
        rentedItems.put(itemId, vehicle);
        saldo-=vehicle.getPrice();
        return true;
    }

    @Override
    public boolean returnItem(String itemId) {
        RentalTransaction t = (RentalTransaction) CentralDatabase.getInstance().getCachedData().get(RentalTransaction.class).get(itemId);
        rentedItems.remove(t.getVehicle().getVehicleId());
        return true;
    }

    @Override
    public String toString() {
        return email + " " + firstName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String getID() {
        return customerId;
    }

    @Override
    public void removeRentedItem(String itemId) {
        rentedItems.remove(itemId);
    }
}
