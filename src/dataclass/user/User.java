package dataclass.user;
import java.util.Map;
public interface User {
    String getID();
    int getNumberOfRented();
    <K,V>Map<K,V> getRented();
    User getUserType();
}
