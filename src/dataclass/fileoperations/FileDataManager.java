package dataclass.fileoperations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileDataManager<T extends Serializable> implements DataManager<T> {
    private final String folderPath;
    private final String filePrefix;

    public FileDataManager(String folderPath, String filePrefix) {
        this.folderPath = folderPath.endsWith("/") ? folderPath : folderPath + "/";
        this.filePrefix = filePrefix;

        // Tworzymy folder, jeśli nie istnieje
        File folder = new File(this.folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    @Override
    public Map<String, T> loadAll() throws IOException {
        Map<String, T> data = new HashMap<>();
        File folder = new File(folderPath);

        for (File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.startsWith(filePrefix) && name.endsWith(".dat")))) {
            String id = extractIdFromFileName(file.getName());
            data.put(id, load(id));
        }

        return data;
    }

    @Override
    public void saveAll(Map<String, T> data) throws IOException {
        for (Map.Entry<String, T> entry : data.entrySet()) {
            save(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public T load(String id) throws IOException {
        String fileName = getFileName(id);
        Path filePath = Paths.get(folderPath, fileName);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error deserializing object", e);
        }
    }

    @Override
    public void save(String id, T item) throws IOException {
        String fileName = getFileName(id);
        Path filePath = Paths.get(folderPath, fileName);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(item);
        }
    }

    @Override
    public boolean delete(String id) {
        String fileName = getFileName(id);
        File file = new File(folderPath, fileName);
        return file.delete();
    }

    @Override
    public boolean exists(String id) {
        String fileName = getFileName(id);
        File file = new File(folderPath, fileName);
        return file.exists();
    }

    @Override
    public void clearAll() throws IOException {
        File folder = new File(folderPath);
        for (File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.startsWith(filePrefix) && name.endsWith(".dat")))) {
            if (!file.delete()) {
                throw new IOException("Failed to delete file: " + file.getName());
            }
        }
    }

    @Override
    public long count() {
        File folder = new File(folderPath);
        return Objects.requireNonNull(folder.listFiles((dir, name) -> name.startsWith(filePrefix) && name.endsWith(".dat"))).length;
    }

    private String getFileName(String id) {
        return id + ".dat";
    }

    private String extractIdFromFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf(".dat"));
    }
}
