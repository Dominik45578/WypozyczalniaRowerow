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
            //FileDataManager<Customer> customerManager = new FileDataManager<>("customer", User.BUSINESS_C_PREFIX);
            // CentralDatabase.getInstance().registerManager(Customer.class, customerManager);

            // Tworzenie klientów biznesowych
            BusinessCustomer business1 = new BusinessCustomer("BC001", "Anna", "Nowak", "Manager", "123456789",
                    "31-500", "Kraków", "Rynek Główny 1", "anna@firma.pl", "Firma1", "BC001", "Warszawska 10", "31-400", "Kraków");
            BusinessCustomer business2 = new BusinessCustomer("BC002", "Jan", "Kowalski", "Dyrektor", "987654321",
                    "31-600", "Kraków", "Floriańska 5", "jan@firma.pl", "Firma2", "BC002", "Mogilska 20", "31-700", "Kraków");

            // Tworzenie pracowników i przypisanie ich do klientów biznesowych
            Employee employee1 = new Employee("E001", "Tomasz", "Marek", "Nowak", "111111111",
                    "31-700", "Kraków", "Długa 15", "tomasz@firma1.pl");
            Employee employee2 = new Employee("E002", "Katarzyna", "Anna", "Kowalska", "222222222",
                    "31-800", "Kraków", "Krótka 20", "katarzyna@firma2.pl");

            business1.addEmployee(employee1.getEmployeeId(), employee1);
            business2.addEmployee(employee2.getEmployeeId(), employee2);

            // Tworzenie klientów prywatnych
            PrivateCustomer private1 = new PrivateCustomer("PC001", "Michał", "Jan", "Wiśniewski", "333333333",
                    "31-900", "Kraków", "Lipowa 5", "michal@priv.pl");
            PrivateCustomer private2 = new PrivateCustomer("PC002", "Agnieszka", "Ewa", "Nowicka", "444444444",
                    "31-100", "Kraków", "Różana 10", "agnieszka@priv.pl");

            // Dodanie klientów do bazy danych
            CentralDatabase.getInstance().addObject(BusinessCustomer.class, business1.getID(), business1);
            CentralDatabase.getInstance().addObject(BusinessCustomer.class, business2.getID(), business2);
            CentralDatabase.getInstance().addObject(PrivateCustomer.class, private1.getID(), private1);
            CentralDatabase.getInstance().addObject(PrivateCustomer.class, private2.getID(), private2);

            // Tworzenie marek i modeli pojazdów
            VehicleBrand brand1 = new VehicleBrand("Kross");
            brand1.addModel(new VehicleModel("Hexagon"));
            brand1.addModel(new VehicleModel("Level"));

            VehicleBrand brand2 = new VehicleBrand("Giant");
            brand2.addModel(new VehicleModel("Talon"));
            brand2.addModel(new VehicleModel("Escape"));

            // Tworzenie pojazdów
            Bike bike1 = new Bike(SingleTrackVehicle.STV_BIKE_PREFIX + "001", "Rower górski", brand1.getModels().toString(), true);
            EBike ebike1 = new EBike(SingleTrackVehicle.STV_E_BIKE_PREFIX + "001", "Rower elektryczny", brand2.getModels().toString(), 50);
            Scooter scooter1 = new Scooter(SingleTrackVehicle.STV_SCOOTER_PREFIX + "001", "Hulajnoga elektryczna", brand1.getModels().toString(), 25);

            // Tworzenie transakcji wynajmu
            RentalTransaction transaction1 = new RentalTransaction(bike1, private1);
            private1.getRentedHistory().put(transaction1.getTransactionID(), transaction1);

            RentalTransaction transaction2 = new RentalTransaction(ebike1, business1);
            business1.getRentedHistory().put(transaction2.getTransactionID(), transaction2);

            // Zapisanie wszystkich danych do plików
            CentralDatabase.getInstance().saveAll();
            CentralDatabase.getInstance().loadAll();
            System.out.println(CentralDatabase.getInstance().getCount(PrivateCustomer.class));
            Map<String , PrivateCustomer> maps= CentralDatabase.getInstance().getAllObjects(PrivateCustomer.class);

            System.out.println("Testowe dane zostały pomyślnie zapisane.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


