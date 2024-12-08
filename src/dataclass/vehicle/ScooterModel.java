package dataclass.vehicle;

public class ScooterModel {
    private final ScooterBrand brand;
    private final String name;

    public ScooterModel(ScooterBrand brand, String name) {
        this.brand = brand;
        this.name = name;
    }

    public ScooterBrand getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Model: " + name + " (Brand: " + brand.getName() + ")";
    }
}
