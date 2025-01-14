package dataclass.user;


import java.util.Map;

public class Employee extends Customer implements User{
    private String employeeId;
    private String position; // Rola pracownika
    private BusinessCustomer employer; // Powiązana firma
    public static Employee createNewEmployee(){
        return new Employee("null","null","null","null",
                  "null","null","null","null","null");
    }



    public Employee(String customerId, String firstName, String secondName, String lastName, String pesel,
                    String postalCode, String city, String address, String email) {
        super(customerId, firstName, secondName, lastName, pesel, postalCode, city, address, email);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPosition() {
        return position;
    }

    public BusinessCustomer getEmployer() {
        return employer;
    }

    @Override
    public int getNumberOfRentedItems() {
        return rentedItems.size();
    }

    @Override
    public String getID() {
        return employeeId;
    }

    @Override
    public void setID(String id) {
        employeeId = id;

    }


    @Override
    public Users getUserType() {
        return Users.EMPLOYEE;
    }
}
