package dataclass.rental;

import dataclass.vehicle.*;
import dataclass.user.User;

import java.util.*;

public class RentalSystemManager {
    private final Map<String, ScooterBrand> brands = new HashMap<>();
    private final Map<ScooterBrand, Map<String, ScooterModel>> models = new HashMap<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public void addBrand(String brandName) {
        ScooterBrand brand = new ScooterBrand(brandName);
        brands.put(brandName, brand);
        models.put(brand, new HashMap<>());
    }

    public void addModel(ScooterBrand brand, String modelName) {
        if (!models.containsKey(brand)) {
            throw new IllegalArgumentException("Brand does not exist");
        }
        ScooterModel model = new ScooterModel(brand, modelName);
        models.get(brand).put(modelName, model);
    }

    public Map<String, ScooterBrand> getAllBrands() {
        return Collections.unmodifiableMap(brands);
    }

    public Map<ScooterBrand, Map<String, ScooterModel>> getAllModels() {
        return Collections.unmodifiableMap(models);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Vehicle> getAllVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    public void displayAllData() {
        System.out.println("Brands:");
        brands.values().forEach(System.out::println);

        System.out.println("\nModels:");
        models.forEach((brand, modelMap) -> {
            System.out.println("Brand: " + brand);
            modelMap.values().forEach(System.out::println);
        });

        System.out.println("\nVehicles:");
        vehicles.forEach(System.out::println);

        System.out.println("\nUsers:");
        users.forEach(System.out::println);
    }
}
