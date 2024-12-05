package dataclass.user;

import java.util.Map;

public class BuissnesCustomer extends Customer implements User {
    String companyName;
    String companyID;
    String companyAdres;
    String companyPostCode;
    String companyCity;

    public BuissnesCustomer(String companyName){}

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    private void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyAdres() {
        return companyAdres;
    }

    public void setCompanyAdres(String companyAdres) {
        this.companyAdres = companyAdres;
    }

    public String getCompanyPostCode() {
        return companyPostCode;
    }

    public void setCompanyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    @Override
    public String getID() {
        return companyID ;
    }

    @Override
    public int getNumberOfRented() {
        return numberOfRented;
    }

    @Override
    public <K, V> Map<K, V> getRented() {
        return Map.of();
    }

    @Override
    public User getUserType() {
        User user = this;
        return user ;
    }
}
