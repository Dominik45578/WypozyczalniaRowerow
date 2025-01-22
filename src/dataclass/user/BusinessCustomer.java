package dataclass.user;

import dataclass.fileoperations.CentralDatabase;
import dataclass.user.Users;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a business customer.
 */
public class BusinessCustomer extends Customer implements User {

    private String companyName;
    private String companyAddress;
    private String companyPostalCode;
    private String companyCity;
    private String companyPhoneNumber;
    private String companyEmail;
    private String nipNumber;
    private Map<String, Employee> employees = new HashMap<>();

    public BusinessCustomer(Customer customer, String companyName, String companyId, String companyAddress, String companyPostalCode,
                            String companyCity,String companyPhoneNumber, String companyEmail, String nipnumber) {
        this.customerId = customer.customerId;
        this.firstName = customer.firstName;
        this.secondName = customer.secondName;
        this.lastName = customer.lastName;
        this.pesel = customer.pesel;
        this.address = customer.address;
        this.postalCode = customer.postalCode;
        this.city = customer.city;
        this.email = customer.email;
        this.password = customer.password;
        this.phoneNumber = "Brak";
        this.customerId = CentralDatabase.getInstance().getNextID(User.class, User.BUSINESS_C_PREFIX);

        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyCity = companyCity;
        this.companyPostalCode = companyPostalCode;
        this.nipNumber = nipnumber;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyEmail = companyEmail;
    }

    public BusinessCustomer(String firstName, String secondName, String lastName,
                            String pesel, String postalCode, String city, String address, String email,
                            String companyName, String companyAddress, String companyPostalCode, String companyCity,
                            String companyPhoneNumber,String companyEmail, String password, String nipNumber) {

        super(firstName, secondName, lastName, pesel, postalCode, city, address, email, password);
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyCity = companyCity;
        this.companyPostalCode = companyPostalCode;
        this.nipNumber = nipNumber;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyEmail = companyEmail;
    }

    public String getNipNumber() {
        return nipNumber;
    }

    public void addEmployee(String employeeId, Employee employee) {
        employees.put(employeeId, employee);
    }

    public boolean removeEmployee(String employeeId) {
        if(!employees.get(employeeId).rentedItems.entrySet().isEmpty()){
            return false;
        }
        employees.remove(employeeId);
        return true;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public Map<String, Employee> getEmployees() {
        return employees;
    }

    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPostalCode() {
        return companyPostalCode;
    }

    public void setCompanyPostalCode(String companyPostalCode) {
        this.companyPostalCode = companyPostalCode;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public int getNumberOfEmployees() {
        return employees.size();
    }

    @Override
    public Users getUserType() {
        return Users.BUSINESS_CUSTOMER;
    }

}
