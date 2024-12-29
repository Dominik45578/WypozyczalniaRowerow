package dataclass.fileoperations;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CentralDatabase {
    private static CentralDatabase instance;
    private final Map<Class<?>, FileDataManager<?>> managers;
    private final Map<Class<?>, Map<String, ?>> cachedData;

    private CentralDatabase() {
        managers = new HashMap<>();
        cachedData = new HashMap<>();
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

    public <T> void addObject(Class<T> type, String id, T object) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        data.put(id, object);
    }

    // Pobranie obiektu z bazy
    public <T> T getObject(Class<T> type, String id) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return data.get(id);
    }

    // Pobranie wszystkich obiektów danego typu
    public <T> Map<String, T> getAllObjects(Class<T> type) {
        @SuppressWarnings("unchecked")
        Map<String, T> data = (Map<String, T>) cachedData.get(type);
        if (data == null) {
            throw new IllegalArgumentException("No manager registered for type: " + type.getName());
        }
        return new HashMap<>(data); // Zwracamy kopię danych
    }

    // Usunięcie obiektu z bazy
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

            // Pobieramy dane z cache
            @SuppressWarnings("unchecked")
            Map<String, ?> data = cachedData.get(type);

            if (data != null) {
                // Bezpieczne rzutowanie managera i danych do odpowiednich typów
                saveAllWithType(manager, data);
            }
        }
    }

    // Pomocnicza metoda generyczna do zapisu danych
    private <T extends Serializable> void saveAllWithType(FileDataManager<T> manager, Map<String, ?> data) throws IOException {
    //private <T> void saveAllWithType(FileDataManager<T> manager, Map<String, ?> data) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, T> typedData = (Map<String, T>) data;
        manager.saveAll(typedData);
    }
}

