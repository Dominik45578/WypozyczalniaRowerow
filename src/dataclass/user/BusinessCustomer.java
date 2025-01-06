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
    private Map<String, Employee> employees = new HashMap<>();

    public BusinessCustomer(String customerId, String firstName, String secondName, String lastName,
                            String pesel, String postalCode, String city, String address,String email) {
        super(customerId, firstName, secondName, lastName, pesel, postalCode, city, address, email);
        this.companyName = companyName;
        this.companyId = companyId;
    }

    public void addEmployee(String employeeId, Employee employee) {
        employees.put(employeeId, employee);
    }

    public void removeEmployee(String employeeId) {
        employees.remove(employeeId);
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

    @Override
    public String getId() {
        return companyId;
    }

  @Override
    public Users getUserType() {
        return Users.BUSINESS_CUSTOMER;
    }
}
