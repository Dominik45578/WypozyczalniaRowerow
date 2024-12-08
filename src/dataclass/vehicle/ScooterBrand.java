package dataclass.vehicle;

public class ScooterBrand {
    private final String name;

    public ScooterBrand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Brand: " + name;
    }
}
