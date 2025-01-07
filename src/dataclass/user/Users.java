package dataclass.user;

public enum Users {
    ROOT(10, "Administrator"),
    PRIVATE_CUSTOMER(1,"Klient Prywatny"),
    BUSINESS_CUSTOMER(5, "Klient Biznesowy"),
    EMPLOYEE(4, "Pracownik");

    private final int weight;
    private final String type;

    Users(int weight, String type) {
        this.weight = weight;
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }
    public String getType(){return type;}
}
