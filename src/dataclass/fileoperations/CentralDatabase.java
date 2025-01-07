package dataclass.fileoperations;

import dataclass.user.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CentralDatabase {
    private static CentralDatabase instance;
    private final Map<Class<?>, FileDataManager<?>> managers;
    private final Map<Class<?>, Map<String, ?>> cachedData;
    private User currentUser;
    private FileDataManager<User> userFileDataManager; // FileDataManager dla użytkowników

    private CentralDatabase() {
        managers = new HashMap<>();
        cachedData = new HashMap<>();
        currentUser = null;
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
                    userFileDataManager.save(currentUser.getId(), currentUser);
                } else {
                    throw new IllegalStateException("User FileDataManager is not set");
                }
            } catch (IOException e) {
                System.err.println("Error saving user data: " + e.getMessage());
            }
        }
        currentUser = null; // Usuń użytkownika
    }
}
