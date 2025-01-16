import dataclass.fileoperations.CentralDatabase;
import dataclass.fileoperations.FileDataManager;
import dataclass.rental.RentalTransaction;
import dataclass.user.*;
import dataclass.vehicle.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            // Inicjalizacja menedżera danych dla klientów
//            FileDataManager<User> customerManager = new FileDataManager<>("User", User.BUSINESS_C_PREFIX);
//           CentralDatabase.getInstance().registerManager(User.class,);
            CentralDatabase cd = CentralDatabase.getInstance();
            cd.loadAll();
            // Tworzenie klientów biznesowych
            BusinessCustomer business1 = new BusinessCustomer(cd.getNextID(User.class,User.BUSINESS_C_PREFIX), "Anna", "Nowak", "Manager", "123456789",
                    "31-500", "Kraków", "Rynek Główny 1", "anna@firma.pl", "Firma1", cd.getNextID(User.class,User.BUSINESS_C_PREFIX), "Warszawska 10", "31-400", "Kraków", "Kraków1");
            CentralDatabase.getInstance().addObject(User.class, business1.getID(), business1);
            BusinessCustomer business2 = new BusinessCustomer(cd.getNextID(User.class,User.BUSINESS_C_PREFIX), "Jan", "Kowalski", "Dyrektor", "987654321",
                    "31-600", "Kraków", "Floriańska 5", "jan@firma.pl", "Firma2", cd.getNextID(User.class,User.BUSINESS_C_PREFIX), "Mogilska 20", "31-700", "Kraków", "Kraków1");
             CentralDatabase.getInstance().addObject(User.class, business2.getID(), business2);
            // Tworzenie pracowników i przypisanie ich do klientów biznesowych
            Employee employee1 = new Employee(cd.getNextID(User.class,User.EMPLOYEE_C_PREFIX), "Tomasz", "Marek", "Nowak", "111111111",
                    "31-700", "Kraków", "Długa 15", "tomasz@firma1.pl", "Tomasz1");
             CentralDatabase.getInstance().addObject(User.class, employee1.getID(),employee1);
            Employee employee2 = new Employee(cd.getNextID(User.class,User.EMPLOYEE_C_PREFIX), "Katarzyna", "Anna", "Kowalska", "222222222",
                    "31-800", "Kraków", "Krótka 20", "katarzyna@firma2.pl", "Katarzyna1");
             CentralDatabase.getInstance().addObject(User.class, employee2.getID(),employee2);

            business1.addEmployee(employee1.getEmployeeId(), employee1);
            business2.addEmployee(employee2.getEmployeeId(), employee2);

            // Tworzenie klientów prywatnych
            PrivateCustomer private1 = new PrivateCustomer(cd.getNextID(User.class,User.PRIVATE_C_PREFIX), "Michał", "Jan", "Wiśniewski", "04292600775",
                    "31-900", "Kraków", "Lipowa 5", "michal@priv.pl", "Michał 1");
            CentralDatabase.getInstance().addObject(User.class, private1.getID(), private1);
            PrivateCustomer private2 = new PrivateCustomer(cd.getNextID(User.class,User.PRIVATE_C_PREFIX), "Agnieszka", "Ewa", "Nowicka", "444444444",
                    "31-100", "Kraków", "Różana 10", "agnieszka@priv.pl", "Agnieszka1");

            // Dodanie klientów do bazy danych



            CentralDatabase.getInstance().addObject(User.class, private2.getID(), private2);

            // Tworzenie marek i modeli pojazdów
            VehicleBrand brand1 = new VehicleBrand("Kross");
            brand1.addModel(new VehicleModel("Hexagon"));
            brand1.addModel(new VehicleModel("Level"));

             CentralDatabase.getInstance().addObject(VehicleBrand.class , brand1.getName(), brand1);

            VehicleBrand brand2 = new VehicleBrand("Giant");
            brand2.addModel(new VehicleModel("Talon"));
            brand2.addModel(new VehicleModel("Escape"));

            CentralDatabase.getInstance().addObject(VehicleBrand.class , brand2.getName(), brand2);

            // Tworzenie pojazdów
            Bike bike1 = new Bike(cd.getNextID(Vehicle.class,SingleTrackVehicle.STV_BIKE_PREFIX), "Rower górski", brand1.getModels().toString(), true);
            EBike ebike1 = new EBike(cd.getNextID(Vehicle.class,SingleTrackVehicle.STV_BIKE_PREFIX), "Rower elektryczny", brand2.getModels().toString(), 50);
            Scooter scooter1 = new Scooter(cd.getNextID(Vehicle.class,SingleTrackVehicle.STV_BIKE_PREFIX), "Hulajnoga elektryczna", brand1.getModels().toString(), 25);

            CentralDatabase.getInstance().addObject(Vehicle.class ,bike1.getVehicleId(), bike1);
            CentralDatabase.getInstance().addObject(Vehicle.class ,ebike1.getVehicleId(), ebike1);
            CentralDatabase.getInstance().addObject(Vehicle.class ,scooter1.getVehicleId(), scooter1);
            // Tworzenie transakcji wynajmu
           User user = CentralDatabase.getInstance().FilterUser("dkkd3046@gmail.com");

            RentalTransaction transaction1 = new RentalTransaction(bike1, private1);
            private1.getRentedHistory().put(transaction1.getTransactionID(), transaction1);

            RentalTransaction transaction2 = new RentalTransaction(ebike1, business1);
            business1.getRentedHistory().put(transaction2.getTransactionID(), transaction2);
               user.getRentedHistory().put("T001", transaction1);
               user.getRentedHistory().put("T002", transaction2);
               CentralDatabase.getInstance().save(User.class, "PC3");


            // Zapisanie wszystkich danych do plików
            CentralDatabase.getInstance().saveAll();
            CentralDatabase.getInstance().loadAll();
            System.out.println(CentralDatabase.getInstance().getNextID(User.class, User.PRIVATE_C_PREFIX));
            Map<String , User> maps= CentralDatabase.getInstance().getAllObjects(User.class);

            System.out.println("Testowe dane zostały pomyślnie zapisane.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
