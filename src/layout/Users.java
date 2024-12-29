package layout;

public enum Users {
    ROOT(10),
    PRIVATE_CUSTOMER(1),
    BUSINESS_CUSTOMER(5),
    EMPLOYEE(4);

    private final int weight;

    Users(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
