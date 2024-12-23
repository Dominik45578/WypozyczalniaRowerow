package dataclass.fileoperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

/**
 * Interface for managing data operations with generic database structures.
 * Provides methods for data import, export, and manipulation.
 */
public interface DataLoader<T> {

    <String,User> Map<String,User> LoadtoMap();

    /**
     * Loads data into a map structure.
     *
     * @return a map containing the loaded data.
     */
    <K, V> Map<K, V> loadToMap();

    /**
     * Checks if the file is readable.
     *
     * @return true if readable, false otherwise.
     * @throws FileNotFoundException if the file cannot be found.
     */
    boolean isReadable() throws FileNotFoundException;

    /**
     * Sets the file path for data operations.
     *
     * @param path the path to set.
     * @return true if the path is valid and set successfully.
     */
    boolean setPath(Path path);

    boolean setPath(String path);

    /**
     * Gets the current file path.
     *
     * @return the file path as a string.
     */
    String getPath();

    /**
     * Imports data from the specified file into a map.
     *
     * @return a map containing the imported data.
     */
    Map<Integer, T> importDataFromFile() throws IOException;

    /**
     * Exports current data to the specified file.
     */
    void exportDataToFile() throws IOException;

    /**
     * Adds an item to the database.
     *
     * @param item the item to add.
     */
    void addItemToDatabase(T item);

    /**
     * Adds an item to the database with a specific ID.
     *
     * @param id the ID of the item.
     * @param item the item to add.
     */
    void addItemToDatabase(int id, T item);

    /**
     * Removes an item from the database by its ID.
     *
     * @param id the ID of the item to remove.
     */
    void removeItemFromDatabase(int id);

    /**
     * Retrieves an item from the database by its ID.
     *
     * @param id the ID of the item to retrieve.
     * @return the item, or null if not found.
     */
    T getItemFromDatabase(int id);

    /**
     * Updates an item in the database by its ID.
     *
     * @param id the ID of the item to update.
     * @param item the updated item.
     */
    void updateItemInDatabase(int id, T item);

    /**
     * Clears all items from the database.
     */
    void clearDatabase();

    /**
     * Retrieves all items in the database as a map.
     *
     * @return a map of all items.
     */
    Map<Integer, T> getAll();

    /**
     * Retrieves all items in a sorted order based on a comparator.
     *
     * @param comparator the comparator to use for sorting.
     * @return a sorted map of items.
     */
    LinkedHashMap<Integer, T> getSorted(Comparator<T> comparator);

    /**
     * Retrieves items from the database that match a filter predicate.
     *
     * @param filter the predicate for filtering items.
     * @return a map of filtered items.
     */
    Map<Integer, T> getFiltered(Predicate<T> filter);

    /**
     * Checks for duplicate entries in the database by ID.
     *
     * @param id the ID to check.
     * @return true if a duplicate exists, false otherwise.
     */
    boolean checkDuplicate(int id);

    /**
     * Synchronizes the database with the file source.
     *
     * @throws IOException if an I/O error occurs.
     */
    void synchronize() throws IOException;
}
