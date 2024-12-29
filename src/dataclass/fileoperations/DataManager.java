package dataclass.fileoperations;

import java.io.IOException;
import java.util.Map;

public interface DataManager<T> {
    Map<String, T> loadAll() throws IOException;

    void saveAll(Map<String, T> data) throws IOException;

    T load(String id) throws IOException;

    void save(String id, T item) throws IOException;

    boolean delete(String id);

    boolean exists(String id);

    void clearAll() throws IOException;

    long count();
}
