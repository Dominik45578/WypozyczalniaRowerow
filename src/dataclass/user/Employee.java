package dataclass.user;

import layout.Users;

import java.util.Map;

public class Employee extends Customer implements User{
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position; // Rola pracownika
    private BusinessCustomer employer; // Powiązana firma



    public Employee(String customerId, String firstName, String secondName, String lastName, String pesel, String postalCode, String city, String address) {
        super(customerId, firstName, secondName, lastName, pesel, postalCode, city, address);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public BusinessCustomer getEmployer() {
        return employer;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public int getNumberOfRentedItems() {
        return 0;
    }

    @Override
    public Map<String, Object> getRentedItems() {
        return Map.of();
    }

    @Override
    public Users getUserType() {
        return Users.EMPLOYEE;
    }
}
