package dataclass.user;

import dataclass.rental.RentalSystemManager;
import dataclass.user.User;
import dataclass.vehicle.Vehicle;
import dataclass.vehicle.ScooterBrand;
import dataclass.vehicle.ScooterModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca administratora
 */
public class RootUser implements User {

    private final String id;
    private final RentalSystemManager systemManager;

    public RootUser(String id, RentalSystemManager systemManager) {
        this.id = id;
        this.systemManager = systemManager;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getNumberOfRentedItems() {
        return 0; // Root doesn't rent items
    }

    @Override
    public Map<String, Object> getRentedItems() {
        return new HashMap<>(); // Root has no rented items
    }

    @Override
    public String getUserType() {
        return "Root";
    }

    /**
     * Metoda dodaje nową markę pojazdu
     *
     * @param brandName the name of the brand to add.
     */
    public void addBrand(String brandName) {
        systemManager.addBrand(brandName);
    }

    /**
     * Metoda dodająca nowy model określonej marki
     *
     * @param brand     the brand to which the model will be added.
     * @param modelName the name of the model to add.
     */
    public void addModel(ScooterBrand brand, String modelName) {
        systemManager.addModel(brand, modelName);
    }

    /**
     * Pobiera wszystkie dostępne marki
     *
     * @return a map of brand names to `ScooterBrand` objects.
     */
    public Map<String, ScooterBrand> getAllBrands() {
        return systemManager.getAllBrands();
    }

    /**
     * Pobiera wszystkie dostępne modele
     *
     * @return a map of brands to their respective models.
     */
    public Map<ScooterBrand, Map<String, ScooterModel>> getAllModels() {
        return systemManager.getAllModels();
    }

    /**
     * Dodanie nowego pojazdu do systemu
     *
     * @param vehicle the vehicle to add.
     */
    public void addVehicle(Vehicle vehicle) {
        systemManager.addVehicle(vehicle);
    }

    /**
     * Displays all data in the system (brands, models, and vehicles).
     */
    public void displayAllData() {
        systemManager.displayAllData();
    }
}
