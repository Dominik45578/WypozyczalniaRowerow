package dataclass.user;


import dataclass.fileoperations.CentralDatabase;

import java.util.Map;

public class Employee extends Customer implements User{
    private String position; // Rola pracownika
    private BusinessCustomer employer; // Powiązana firma




    public Employee(String firstName, String secondName, String lastName, String pesel,
                    String postalCode, String city, String address, String email, String password) {
        super(firstName, secondName, lastName, pesel, postalCode, city, address, email, password);
        this.customerId = CentralDatabase.getInstance().getNextID(User.class,User.EMPLOYEE_C_PREFIX);
        this.phoneNumber ="Brak";
    }

    public String getPosition() {
        return position;
    }

    public BusinessCustomer getEmployer() {
        return employer;
    }
    public void setEmployer(BusinessCustomer bc){
        this.employer = bc;
    }
    @Override
    public int getNumberOfRentedItems() {
        return rentedItems.size();
    }


    @Override
    public Users getUserType() {
        return Users.EMPLOYEE;
    }
}
