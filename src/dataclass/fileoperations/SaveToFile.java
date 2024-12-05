package dataclass.fileoperations;

import java.util.Map;

public interface SaveToFile<K,V> {
     void saveToFile(Map<K,V> data);
}
