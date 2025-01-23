package dataclass.rental;

import dataclass.fileoperations.CentralDatabase;
import dataclass.user.BusinessCustomer;
import dataclass.user.Customer;
import dataclass.user.Employee;
import dataclass.user.User;
import dataclass.vehicle.SingleTrackVehicle;
import dataclass.vehicle.Vehicle;
import dataclass.vehicle.VehicleBrand;
import dataclass.vehicle.VehicleModel;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class RentalServices implements RentalService {
    private Map<String, RentalTransaction> rentalTransactionMap;
    private CentralDatabase database;
    private static RentalServices instance;

    private RentalServices() {
        this.database = CentralDatabase.getInstance();
        this.rentalTransactionMap = new HashMap<>();
    }

    public static RentalServices getInstance() {
        if (instance == null) {
            instance = new RentalServices();
        }
        return instance;
    }

    @Override
    public void addUser(User user) {
        try {
            database.addObject(User.class, user.getID(), user);
            database.save(User.class, user.getID());
        } catch (Exception e) {
            System.out.println("Błąd podczas dodawania użytkownika: " + e.getMessage());
        }
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        try {
            database.addObject(Vehicle.class, vehicle.getVehicleId(), vehicle);
            database.save(Vehicle.class, vehicle.getVehicleId());
        } catch (Exception e) {
            System.out.println("Błąd podczas dodawania pojazdu: " + e.getMessage());
        }
    }

    @Override
    public void addBrand(VehicleBrand brand) {
        try {
            database.addObject(VehicleBrand.class, brand.getName(), brand);
            database.save(VehicleBrand.class, brand.getName());
        } catch (Exception e) {
            System.out.println("Błąd podczas dodawania marki: " + e.getMessage());
        }
    }

    @Override
    public void addModel(VehicleBrand brand, VehicleModel model) {
        try {
            brand.addModel(model);
            database.save(VehicleBrand.class, brand.getName());
        } catch (Exception e) {
            System.out.println("Błąd podczas dodawania modelu: " + e.getMessage());
        }
    }

    @Override
    public boolean removeVehicle(String vehicleId) {
        try {
            return database.delete(Vehicle.class, vehicleId);
        } catch (Exception e) {
            System.out.println("Błąd podczas usuwania pojazdu: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean rentVehicle(String vehicleId, User user) {
        Vehicle vehicle = database.getObject(Vehicle.class, vehicleId);
        if (vehicle != null && !vehicle.isRented()) {
            System.out.println("Wynajem :" + vehicleId);
            if (user.rentItem(vehicle.getVehicleId(), vehicle)) {
                RentalTransaction t = new RentalTransaction(database.getNextID(RentalTransaction.class, "T"), vehicle, user);
                user.getRentedHistory().put(t.getTransactionID(), t);
                vehicle.rentVehicle(user);
                addTransaction(t);
                database.addObject(RentalTransaction.class, t.getTransactionID(), t);
            }
            database.addObject(Vehicle.class, vehicleId, vehicle);
            database.addObject(User.class, user.getID(), user);

            if (user instanceof Employee) {
                Employee e = (Employee) user;
                e.getEmployer().addEmployee(e.getID(), e);
                database.addObject(User.class, e.getEmployer().getID(), e.getEmployer());
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean returnVehicle(String vehicleId) {
        Vehicle vehicle = database.getObject(Vehicle.class, vehicleId);
        if (vehicle != null) {
            System.out.println("Zwrot :" + vehicleId);
            Customer user = (Customer) vehicle.getRenter();
            RentalTransaction t = findActiveTransactionByVehicleId(vehicleId);
            if (user.returnItem(t.getTransactionID())) {
                System.out.println("Znaleziono pojazd w obiekcie usera");
                 t.endRental();
                int rentalDays = (int) ChronoUnit.DAYS.between(t.getRentalStart(), t.getRentalEnd());
                user.addToSaldo(-1 * (rentalDays - 1) * vehicle.getPrice());
                vehicle.returnVehicle();
                if(vehicle.getRenter()==null){
                    System.out.println("Ustawiono usera na null w pojedzie");

                }

                if (vehicle.isElectric()) {
                    vehicle.setBatteryLevel(vehicle.getBatteryLevel() - rentalDays * 20);
                }
                database.addObject(RentalTransaction.class, t.getTransactionID(), t);
            }

            database.addObject(Vehicle.class, vehicleId, vehicle);
            database.addObject(User.class, user.getID(), user);

            return true;
        }
        return false;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(database.getAllObjects(Vehicle.class).values());
    }

    @Override
    public List<Vehicle> getRentedVehicles() {
        return database.getFilteredObjects(Vehicle.class, "STV").values().stream()
                .filter(Vehicle::isRented)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> getBrokenVehicle() {
        return database.getFilteredObjects(Vehicle.class, "broken").values().stream()
                .filter(e -> Objects.equals(e.getStatus(), SingleTrackVehicle.BROKEN))
                .collect(Collectors.toList());
    }

    @Override
    public boolean emailExistForPrivate(String email) {
        return database.existsObject(User.class, (User u) -> u.getEmail().equals(email));
    }

    @Override
    public boolean emailExistForBusiness(String email) {
        return database.existsObject(User.class, (BusinessCustomer u) -> u.getCompanyEmail().equals(email));
    }

    @Override
    public boolean peselExist(String pesel) {
        return database.existsObject(User.class, (Customer u) -> u.getPesel().equals(pesel));
    }

    @Override
    public boolean nipExist(String nip) {
        return database.existsObject(User.class, (BusinessCustomer u) -> u.getNipNumber().equals(nip));
    }

    @Override
    public boolean vehicleExistByID(String id) {
        return database.getObject(Vehicle.class, id) != null;
    }

    @Override
    public boolean brandExistByID(String id) {
        return database.getObject(VehicleBrand.class, id) != null;
    }

    @Override
    public User filterUserByEmail(String email) {
        // Zwraca użytkownika po emailu
        return FilterUserByEmail(email);
    }

    public Map<String, RentalTransaction> getRentalTransactionMap() {
        return Collections.unmodifiableMap(rentalTransactionMap);
    }

    public void addTransaction(RentalTransaction rentalTransaction) {
        this.rentalTransactionMap.put(rentalTransaction.getTransactionID(), rentalTransaction);
    }

    public User FilterUserByEmail(String email) {
        if (emailExistForPrivate(email)) {
            Map<String, User> map = CentralDatabase.getInstance().FilterObject(User.class,
                    (User user) -> user.getEmail().equals(email));
            return map.values().stream().findFirst().orElse(null);
        }
        return null;
    }

    public User FilterBusinessCustomerByNIP(String nip) {
        if (nipExist(nip)) {
            // Filtrujemy użytkowników, którzy są instancjami klasy BusinessCustomer
            Map<String, User> map = CentralDatabase.getInstance().FilterObject(User.class,
                    user -> user instanceof BusinessCustomer && ((BusinessCustomer) user).getNipNumber().equals(nip));
            return map.values().stream().findFirst().orElse(null);
        }
        return null;
    }

    public RentalTransaction findActiveTransactionByVehicleId(String itemId) {
        Map<String, RentalTransaction> transactionsMap =
                (Map<String, RentalTransaction>) database.getCachedData().get(RentalTransaction.class);

        if (transactionsMap != null) {
            for (Map.Entry<String, RentalTransaction> entry : transactionsMap.entrySet()) {
                RentalTransaction transaction = entry.getValue();
                if (transaction.isActive() && transaction.getVehicle().getVehicleId().equals(itemId)) {
                    return transaction;
                }
            }
        }
        return null;
    }

    public int getNumberOfActiveTransaction() {
        Map<String, RentalTransaction> transactionsMap =
                (Map<String, RentalTransaction>) database.getCachedData().get(RentalTransaction.class);

        if (transactionsMap != null) {
            return (int) transactionsMap.values().stream()
                    .filter((RentalTransaction::isActive))
                    .count();
        }
        return 0;
    }

    public int getAllNumberOfTransactions() {
        return database.getCachedData().get(RentalTransaction.class).keySet().size();
    }

    public int getNumberOfUsers() {
        return database.getCachedData().get(User.class).keySet().size();
    }

    public int getNumberOfVehicle() {
        return database.getCachedData().get(Vehicle.class).keySet().size();
    }

    public int getNumberOfVehicleBrand() {
        return database.getCachedData().get(VehicleBrand.class).keySet().size();
    }

}


