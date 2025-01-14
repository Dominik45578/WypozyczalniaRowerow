package dataclass.user;

import dataclass.user.Users;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a business customer.
 */
public class BusinessCustomer extends Customer implements User {

    private String companyName;
    private String companyId;
    private String companyAddress;
    private String companyPostalCode;
    private String companyCity;
    private String companyPhoneNumber;
    private String companyEmail;
    private Map<String, Employee> employees = new HashMap<>();

    public BusinessCustomer(String customerId, String firstName, String secondName, String lastName,
                            String pesel, String postalCode, String city, String address,String email,
                            String companyName, String companyId, String companyAddress, String companyPostalCode, String companyCity) {
        super(customerId, firstName, secondName, lastName, pesel, postalCode, city, address, email);
        this.companyName = companyName;
        this.companyId = companyId;
        this.companyAddress = companyAddress;
        this.companyCity = companyCity;
        this.companyPostalCode= companyPostalCode;
        this.companyPhoneNumber = "Brak";
        this.companyEmail = "Brak";
    }

    public void addEmployee(String employeeId, Employee employee) {
        employees.put(employeeId, employee);
    }

    public void removeEmployee(String employeeId) {
        employees.remove(employeeId);
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public int getNumberOfEmployees(){
        return employees.size();
    }

    @Override
    public String getID() {
        return companyId;
    }

    @Override
    public void setID(String id) {
    }

     @Override
    public Users getUserType() {
        return Users.BUSINESS_CUSTOMER;
    }
}
