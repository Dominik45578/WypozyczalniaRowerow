package dataclass.fileoperations;

import java.util.Map;

@Deprecated
public interface SaveToFile<K,V> {
     void saveToFile(Map<K,V> data);
}
