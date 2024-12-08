package dataclass.user;

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position; // Rola pracownika
    private BusinessCustomer employer; // Powiązana firma

    public Employee(String employeeId, String firstName, String lastName, String position, BusinessCustomer employer) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.employer = employer;
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
}
