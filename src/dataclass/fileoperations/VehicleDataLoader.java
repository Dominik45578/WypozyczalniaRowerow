package dataclass.fileoperations;

import dataclass.user.PrivateCustomer;
import dataclass.user.User;
import dataclass.vehicle.Vehicle;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    public boolean isReadable() throws FileNotFoundException {
         if(file.canRead()){
            dataInput = new Scanner(file);
            return true;
        }

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
}
