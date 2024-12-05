package dataclass.fileoperations;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public interface DataLoader<T> {
    <K,V >Map<K,V> LoadtoMap();
    boolean isReadable() throws FileNotFoundException;
    boolean setPath(Path path);
    String getPath();
    /*
    Map<Integer, T> importDataFromFile();

    void exportDataToFile();

    void addItemToDatabase(T item);

    void addItemToDatabase(int id,T item);

    void removeItemFromDatabase(int id);

    T getItemFromDatabase(int id);

    void updateItemInDatabase(int id,T item);

    void clearDatabase();

    Map<Integer, T> getAll();

    LinkedHashMap<Integer, T> getSorted(Comparator<T> comparator);

    Map<Integer, T>  getFiltered(Predicate<T> filter);

    boolean checkDuplicate(int id);
    */
}

