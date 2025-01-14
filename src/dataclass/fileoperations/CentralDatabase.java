package dataclass.fileoperations;

import dataclass.user.*;
import dataclass.vehicle.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CentralDatabase {
    private static CentralDatabase instance;
    private final Map<Class<?>, FileDataManager<?>> managers;
    private final Map<Class<?>, Map<String, ?>> cachedData;
    private User currentUser;
    private FileDataManager<User> userFileDataManager;
    private void createDefaultManagers(){
        try {
            registerManager(PrivateCustomer.class, new FileDataManager<PrivateCustomer>(PrivateCustomer.class.getSimpleName(), User.PRIVATE_C_PREFIX));
            registerManager(BusinessCustomer.class, new FileDataManager<BusinessCustomer>(BusinessCustomer.class.getSimpleName(), User.BUSINESS_C_PREFIX));
            registerManager(Employee.class, new FileDataManager<Employee>(Employee.class.getSimpleName(), User.EMPLOYEE_C_PREFIX));
            registerManager(RootUser.class, new FileDataManager<RootUser>(RootUser.class.getSimpleName(), User.ROOT_PREFIX));

            registerManager(Bike.class, new FileDataManager<Bike>(Bike.class.getSimpleName(), SingleTrackVehicle.STV_BIKE_PREFIX));
            registerManager(EBike.class, new FileDataManager<EBike>(EBike.class.getSimpleName(), SingleTrackVehicle.STV_E_BIKE_PREFIX));
            registerManager(Scooter.class, new FileDataManager<Scooter>(Scooter.class.getSimpleName(), SingleTrackVehicle.STV_SCOOTER_PREFIX));
        } catch (IOException e) {
            System.out.println("Problem z menadżerem klasy!");
        }
    }
    public void loadAll() throws IOException {
        saveAll();
        for (Map.Entry<Class<?>, FileDataManager<?>> entry : managers.entrySet()) {
            Class<?> type = entry.getKey();
            FileDataManager<?> manager = entry.getValue();

            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) manager.loadAll();

            cachedData.put(type, data);
        }
    }

    private CentralDatabase() {
        managers = new HashMap<>();
        cachedData = new HashMap<>();
        currentUser = null;
        createDefaultManagers();
    }

    public static synchronized CentralDatabase getInstance() {
        if (instance == null) {
            instance = new CentralDatabase();
        }
        return instance;
    }

    public <T extends Serializable> void registerManager(Class<T> type, FileDataManager<T> manager) throws IOException {
        managers.put(type, manager);
        cachedData.put(type, manager.loadAll()); // Wczytujemy dane przy rejestracji
    }

    public void setUserFileDataManager(FileDataManager<User> manager) {
        this.userFileDataManager = manager;
    }

    public <T> void addObject(Class<T> type, String id, T object) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        data.put(id, object);
    }

    public <T> T getObject(Class<T> type, String id) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return data.get(id);
    }

    public <T> Map<String, T> getAllObjects(Class<T> type) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return new HashMap<>(data); // Zwracamy kopię danych
    }

    public <T> boolean removeObject(Class<T> type, String id) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return data.remove(id) != null;
    }

    public void saveAll() throws IOException {
        for (Map.Entry<Class<?>, FileDataManager<?>> entry : managers.entrySet()) {
            Class<?> type = entry.getKey();
            FileDataManager<?> manager = entry.getValue();

            @SuppressWarnings("unchecked")
            Map<String, ?> data = cachedData.get(type);

            if (data != null) {
                saveAllWithType(manager, data);
            }
        }
    }

    private <T extends Serializable> void saveAllWithType(FileDataManager<T> manager, Map<String, ?> data) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, T> typedData = (Map<String, T>) data;
        manager.saveAll(typedData);
    }

    public void setUser(User user) {
        currentUser = user;
    }

    public User getUser() {
        return currentUser;
    }

    public void removeUser() {
        if (currentUser != null) {
            try {
                // Zapisz dane bieżącego użytkownika do pliku
                if (userFileDataManager != null) {
                    userFileDataManager.save(currentUser.getID(), currentUser);
                } else {
                    throw new IllegalStateException("User FileDataManager is not set");
                }
            } catch (IOException e) {
                System.err.println("Error saving user data: " + e.getMessage());
            }
        }
        currentUser = null; // Usuń użytkownika
    }
    public <T> int getCount(Class<T> type) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return data.size();
    }
}
