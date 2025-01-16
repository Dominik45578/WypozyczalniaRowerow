package dataclass.fileoperations;

import dataclass.user.User;
import dataclass.vehicle.SingleTrackVehicle;
import dataclass.vehicle.Vehicle;
import dataclass.vehicle.VehicleBrand;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CentralDatabase {
    private static CentralDatabase instance;
    private final Map<Class<?>, FileDataManager<?>> managers;
    private final Map<Class<?>, Map<String, ?>> cachedData;
    private User currentUser;

    public Map<Class<?>, Map<String, ?>> getCachedData() {
        return cachedData;
    }

    private CentralDatabase() {
        managers = new HashMap<>();
        cachedData = new HashMap<>();
        createDefaultManagers();
        try {
            loadAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        if(currentUser == null){
            this.currentUser = null;
        }
        else{
            try{
                updateUser(User.class, currentUser);
            }catch (Exception e){
                System.out.println("Użytkownicy są różni");
            }
            this.currentUser = currentUser;
        }

    }

    public static synchronized CentralDatabase getInstance() {
        if (instance == null) {
            instance = new CentralDatabase();
        }
        try {
            instance.saveAll();
            instance.loadAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    private void createDefaultManagers() {
        try {
            // User-related data
            Set<String> userPrefixes = Set.of(User.PRIVATE_C_PREFIX, User.BUSINESS_C_PREFIX, User.EMPLOYEE_C_PREFIX, User.ROOT_PREFIX);
            FileDataManager<User> userManager = new FileDataManager<>("User", userPrefixes);
            registerManager(User.class, userManager);

            Set<String> vehiclePrefixes = Set.of(SingleTrackVehicle.STV_BIKE_PREFIX, SingleTrackVehicle.STV_E_BIKE_PREFIX, SingleTrackVehicle.STV_SCOOTER_PREFIX);
            FileDataManager<Vehicle> vehicleManager = new FileDataManager<>("Vehicle", vehiclePrefixes);
            registerManager(Vehicle.class, vehicleManager);

            Set<String> brands= Set.of("VB");
            FileDataManager<VehicleBrand> vehicleBrand = new FileDataManager<>("Brand", brands);
            registerManager(VehicleBrand.class, vehicleBrand);

        } catch (IOException e) {
            System.err.println("Error creating default managers: " + e.getMessage());
        }
    }

    public <T extends Serializable> void registerManager(Class<T> type, FileDataManager<T> manager) throws IOException {
        managers.put(type, manager);
        cachedData.put(type, manager.loadAll());
    }

    public <T extends Serializable> Map<String, T> getAllObjects(Class<T> type) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return new HashMap<>(data);
    }

    public <T extends Serializable> Map<String, T> getFilteredObjects(Class<T> type, String prefix) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }

        return data.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public <T extends Serializable> String getNextID(Class<T> type, String prefix) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }

        // Find the maximum numeric suffix
        int maxIndex = data.keySet().stream()
                .filter(key -> key.startsWith(prefix))
                .map(key -> key.substring(prefix.length()))
                .filter(suffix -> suffix.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return prefix + (maxIndex + 1);
    }

    public <T extends Serializable, K extends T> void addObject(Class<T> type, String id, K object) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        // Ensure that we add the object as the base type (T), even if it's a subclass
        data.put(id, type.cast(object)); // Cast the object to T safely
    }

    public <T extends Serializable> T getObject(Class<T> type, String id) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return data.get(id);
    }

    // Load all objects of a specific type
    public <T extends Serializable> void loadAll(Class<T> type) throws IOException {
        FileDataManager<T> manager = (FileDataManager<T>) managers.get(type);
        if (manager == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        Map<String, T> loadedData = manager.loadAll();
        cachedData.put(type, loadedData);
    }

    public <T extends Serializable> void saveAll(Class<T> type) throws IOException {
        FileDataManager<T> manager = (FileDataManager<T>) managers.get(type);
        if (manager == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        Map<String, T> dataToSave = (Map<String, T>) cachedData.get(type);
        if (dataToSave == null) {
            throw new IllegalArgumentException("No data available for type: " + type.getName());
        }
        manager.saveAll(dataToSave);
    }

    public <T extends Serializable> void save(Class<T> type, String id) throws IOException {
        FileDataManager<T> manager = (FileDataManager<T>) managers.get(type);
        if (manager == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        T objectToSave = getObject(type, id);
        if (objectToSave == null) {
            throw new IllegalArgumentException("No object found with ID: " + id);
        }
        System.out.println(objectToSave.toString());
        manager.save(id, objectToSave);
    }

    public <T extends Serializable> T load(Class<T> type, String id) throws IOException {
        FileDataManager<T> manager = (FileDataManager<T>) managers.get(type);
        if (manager == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return manager.load(id);
    }

    public <T extends Serializable> boolean delete(Class<T> type, String id) {
        FileDataManager<T> manager = (FileDataManager<T>) managers.get(type);
        if (manager == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return manager.delete(id);
    }

    public void loadAll() throws IOException {
        // Iterate through all the registered managers
        for (Map.Entry<Class<?>, FileDataManager<?>> entry : managers.entrySet()) {
            Class<?> type = entry.getKey();
            FileDataManager<?> manager = entry.getValue();

            // Load data for the current type and cache it
            Map<String, ?> loadedData = manager.loadAll();
            cachedData.put(type, loadedData);
        }
    }

    // Save all objects for all registered types
    public void saveAll() throws IOException {
        // Iterate through all the registered data
        for (Map.Entry<Class<?>, Map<String, ?>> entry : cachedData.entrySet()) {
            Class<?> type = entry.getKey();
            Map<String, ?> dataToSave = entry.getValue();

            // Get the manager for the current type
            FileDataManager<?> manager = managers.get(type);
            if (manager != null) {
                // Cast dataToSave to the appropriate type
                if (dataToSave != null) {
                    // Perform safe cast based on the type (ensure it matches the manager's type)
                    if (type.isAssignableFrom(User.class)) {
                        @SuppressWarnings("unchecked")
                        Map<String, User> userData = (Map<String, User>) dataToSave;
                        ((FileDataManager<User>) manager).saveAll(userData);
                    } else if (type.isAssignableFrom(Vehicle.class)) {
                        @SuppressWarnings("unchecked")
                        Map<String, Vehicle> vehicleData = (Map<String, Vehicle>) dataToSave;
                        ((FileDataManager<Vehicle>) manager).saveAll(vehicleData);
                    }
                    // Add more type checks if needed (for other classes)
                }
            }
        }

    }

    public boolean emailExists(String email) {
        for (Map.Entry<Class<?>, Map<String, ?>> entry : cachedData.entrySet()) {
            if (User.class.isAssignableFrom(entry.getKey())) {
                @SuppressWarnings("unchecked")
                Map<String, User> users = (Map<String, User>) entry.getValue();
                if (users.values().stream().anyMatch(user -> email.equals(user.getEmail()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public <T extends Serializable> Map<String, T> FilterObject(Class<T> type, Predicate<T> predicate) {
        @SuppressWarnings("unchecked")
        Map<String, T> map = (Map<String, T>) cachedData.get(type);
        return map.entrySet().stream()
                .filter(entry -> predicate.test(entry.getValue())) // Zastosuj predykat
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public User FilterUser(String email) {
        if (emailExists(email)) {
            Map<String, User> map = FilterObject(User.class,
                    (User user) -> user.getEmail().equals(email));
            return map.values().stream().findFirst().orElse(null);
        }
        return null;
    }

    public <T extends Serializable, K extends T> boolean existsObject(Class<T> type, Predicate<K> predicate) {
        @SuppressWarnings("unchecked")
        Map<String, T> map = (Map<String, T>) cachedData.get(type);

        if (map == null) {
            throw new IllegalArgumentException("No data found for type: " + type.getName());
        }

        return map.values().stream()
                .filter(obj -> type.isInstance(obj))
                .map(obj -> (K) obj)
                .anyMatch(predicate);
    }

  public boolean updateUser(Class<User> type, User updatedObject) {
    // Sprawdzamy, czy mamy dane dla danego typu
    @SuppressWarnings("unchecked")
    Map<String, User> data = (Map<String, User>) cachedData.get(type);

    if (data == null) {
        throw new IllegalArgumentException("No manager registered for type: " + type.getName());
    }

    // Wyszukujemy klucz na podstawie ID obiektu
    String objectKey = data.entrySet().stream()
            .filter(entry -> entry.getValue().getID().equals(updatedObject.getID()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Object not found in the database."));

    // Aktualizujemy obiekt w mapie
    data.put(objectKey, updatedObject);

    // Zapisujemy zmiany do menedżera plików
    try {
        save(type, objectKey);
    } catch (IOException e) {
        throw new RuntimeException("Failed to save updated object: " + e.getMessage(), e);
    }
    return false;
}


/*
public class CentralDatabase {
    private static CentralDatabase instance;
    private final Map<Class<?>, FileDataManager<?>> managers;
    private final Map<Class<?>, Map<String, ?>> cachedData;
    private User currentUser;
    private FileDataManager<User> userFileDataManager;

    private void createDefaultManagers() {
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

    public <T extends Serializable> String getNextID(Class<T> type) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);

        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }

        FileDataManager<T> manager = (FileDataManager<T>) managers.get(type);
        if (manager == null) {
            throw new IllegalArgumentException("No FileDataManager registered for type: " + type.getName());
        }

        String prefix = manager.getFilePrefix();

        // Znajdź najwyższy indeks
        int maxIndex = data.keySet().stream()
                .map(key -> key.replace(prefix, "")) // Usuń prefiks
                .filter(key -> key.matches("\\d+")) // Sprawdź, czy to liczba
                .mapToInt(Integer::parseInt)        // Zamień na liczbę
                .max()                              // Znajdź maksymalną wartość
                .orElse(0);                         // Jeśli brak danych, zwróć 0
        return prefix + (maxIndex + 1);
    }

    public <T extends Serializable> boolean emailExist(String email) {
        for()

    }


}*/


}