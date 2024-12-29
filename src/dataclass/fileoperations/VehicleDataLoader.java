package dataclass.fileoperations;

import dataclass.user.PrivateCustomer;
import dataclass.user.User;
import dataclass.vehicle.Vehicle;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
@Deprecated
public class VehicleDataLoader implements DataLoader<Vehicle> {
    private String filePath;
    private File file;
    private Scanner dataInput;
    private Map<String , User> userMap;

    VehicleDataLoader(String path) throws FileNotFoundException {
        setPath(path);
        if(!isReadable()){
             throw new RuntimeException();
        }
    }

    User loadUser(String line){
       User user ;
        try{ObjectInputStream userLoad = new ObjectInputStream(new FileInputStream(filePath));
            user = (PrivateCustomer) userLoad.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public <String,User> Map<String,User> LoadtoMap() {
        Map<String,User> map = new HashMap<>();
        return map;

    }

    @Override
    public <K, V> Map<K, V> loadToMap() {
        return Map.of();
    }

    @Override
    public boolean isReadable() throws FileNotFoundException {
         if(file.canRead()){
            dataInput = new Scanner(file);
            return true;
        }

        return false;
    }

    @Override
    public boolean setPath(Path path) {
        return false;
    }


    @Override
    public boolean setPath(String path) {
        if(path.contains(".txt")){
             filePath = path;
        }
        else{
            filePath = path+".txt";
        }
        file = new File(filePath);
        try {
            isReadable();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public String getPath() {
        return  filePath;
    }

    @Override
    public Map<Integer, Vehicle> importDataFromFile() throws IOException {
        return Map.of();
    }

    @Override
    public void exportDataToFile() throws IOException {

    }

    @Override
    public void addItemToDatabase(Vehicle item) {

    }

    @Override
    public void addItemToDatabase(int id, Vehicle item) {

    }

    @Override
    public void removeItemFromDatabase(int id) {

    }

    @Override
    public Vehicle getItemFromDatabase(int id) {
        return null;
    }

    @Override
    public void updateItemInDatabase(int id, Vehicle item) {

    }

    @Override
    public void clearDatabase() {

    }

    @Override
    public Map<Integer, Vehicle> getAll() {
        return Map.of();
    }

    @Override
    public LinkedHashMap<Integer, Vehicle> getSorted(Comparator<Vehicle> comparator) {
        return null;
    }

    @Override
    public Map<Integer, Vehicle> getFiltered(Predicate<Vehicle> filter) {
        return Map.of();
    }

    @Override
    public boolean checkDuplicate(int id) {
        return false;
    }

    @Override
    public void synchronize() throws IOException {

    }
}
