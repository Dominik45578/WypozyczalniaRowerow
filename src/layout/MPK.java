package layout;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
public class MPK{
     public static void main(String[] args) {
        Address address = new Address("Warszawska 24", "31866", "Kraków");
        Biletomat biletomat = new Biletomat(address);

        System.out.println(biletomat);

        Pieniadz karta = new PlatnoscKarta(50);
        Pieniadz karta1 = new PlatnoscKarta(4);
        Pieniadz karta2 = new PlatnoscKarta(12);
        Pieniadz gotowka = new PlatnoscGotowka(0, 0, 0, 1, 2, 3, 3, 3, 12);
        Pieniadz gotowka1 = new PlatnoscGotowka(0, 0, 2, 1, 0, 0, 0, 0, 0);
        Pieniadz gotowka2= new PlatnoscGotowka(0, 0, 0, 1, 2, 0, 0, 0, 0);

        biletomat.sprzedajBilet("Normalny", 8, karta);
        biletomat.sprzedajBilet("Całodobowy Normalny", 1, karta1);
        biletomat.sprzedajBilet("Całodobowy Ulgowy", 1, karta2);
        biletomat.sprzedajBilet("Normalny", 2, gotowka);
        biletomat.sprzedajBilet("Ulgowy", 2, gotowka1);
        biletomat.sprzedajBilet("Całodobowy Normalny", 2, gotowka2);

        System.out.println("\nWszystkie transakcje:");
        biletomat.wydrukujTransakcje();

        System.out.println("\nTransakcje z dzisiejszej daty:");
        biletomat.wydrukujTransakcje(tr -> tr.getData().equals(LocalDate.now()));
        System.out.println("\nTransakcje z przed wczoraj :");
        biletomat.wydrukujTransakcje(tr -> tr.getData().equals(LocalDate.now().minusDays(2)));
        System.out.println("\nTransakcje z przed 5 dni :");
        biletomat.wydrukujTransakcje(tr -> tr.getData().equals(LocalDate.now().minusDays(5)));
        System.out.println("\nTransakcje z przed 5 dni oraz bilety ulgowe:");
        biletomat.wydrukujTransakcje(tr ->
                tr.getData().equals(LocalDate.now().minusDays(5)) &&
                tr.getTypBiletu().contains("Ulgowy")
        );

    }

}
class Bilet {
    private final String typ;
    private final double cena;

    public Bilet(String typ, double cena) {
        this.typ = typ;
        this.cena = cena;
    }

    public double getCena() {
        return cena;
    }

    @Override
    public String toString() {
        return "Typ biletu: " + typ + ", Cena: " + cena;
    }
}

// Klasa abstrakcyjna Pieniadz
abstract class Pieniadz {
    protected double suma;

    public double getSuma() {
        return suma;
    }

    public abstract String getMetoda();
}

// Klasa dla płatności kartą
class PlatnoscKarta extends Pieniadz {
    public PlatnoscKarta(double suma) {
        this.suma = suma;
    }

    @Override
    public String getMetoda() {
        return "Karta";
    }
}

// Klasa dla płatności gotówką
class PlatnoscGotowka extends Pieniadz {
    private final Map<Double, Integer> nominały = new HashMap<>();

    public PlatnoscGotowka(int _50, int _20, int _10, int _5, int _2, int _1, int _0_50, int _0_20, int _0_10) {
        nominały.put(50.0, _50);
        nominały.put(20.0, _20);
        nominały.put(10.0, _10);
        nominały.put(5.0, _5);
        nominały.put(2.0, _2);
        nominały.put(1.0, _1);
        nominały.put(0.5, _0_50);
        nominały.put(0.2, _0_20);
        nominały.put(0.1, _0_10);
        this.suma = obliczSume();
    }

    private double obliczSume() {
        return nominały.entrySet().stream()
                .mapToDouble(entry -> entry.getKey() * entry.getValue())
                .sum();
    }

    @Override
    public String getMetoda() {
        return "Gotówka";
    }
}

// Klasa Address
class Address {
    private final String street;
    private final String zipcode;
    private final String city;

    public Address(String street, String zipcode, String city) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + zipcode + " " + city;
    }
}

// Klasa Biletomat
class Biletomat {
    private final Address address;
    private final Map<String, Bilet> bilety = new HashMap<>();
    private final List<Transakcja> transakcje = new ArrayList<>();

    public Biletomat(Address address) {
        this.address = address;
        bilety.put("Ulgowy", new Bilet("Ulgowy", 3));
        bilety.put("Normalny", new Bilet("Normalny", 6));
        bilety.put("Całodobowy Normalny", new Bilet("Całodobowy Normalny", 20));
        bilety.put("Całodobowy Ulgowy", new Bilet("Całodobowy Ulgowy", 10));
        inicjalizacja();
    }

    public void sprzedajBilet(String typBiletu, int liczba, Pieniadz pieniadz) {
        if (!bilety.containsKey(typBiletu)) {
            System.out.println("Nieprawidłowy typ biletu!");
            return;
        }

        Bilet bilet = bilety.get(typBiletu);
        double koszt = bilet.getCena() * liczba;

        if (pieniadz.getSuma() < koszt) {
            System.out.println("Niewystarczające środki! Potrzebujesz: " + koszt+" zł");
            return;
        }

        double reszta = pieniadz.getSuma() - koszt;
        if (pieniadz instanceof PlatnoscGotowka && reszta > 0) {
            System.out.println("Sprzedaż udana: " + liczba + "x " + typBiletu + " za " + koszt+"- Reszta : "+Math.round(reszta)+" zł");
        }
        else{
            System.out.println("Sprzedaż udana: " + liczba + "x " + typBiletu + " za " + koszt+" zł");
        }

        transakcje.add(new Transakcja(LocalDate.now(), typBiletu, liczba, koszt));

    }
    void inicjalizacja(){
        transakcje.add(new Transakcja(LocalDate.now().minusDays(5),"Ulgowy",5,15));
        transakcje.add(new Transakcja(LocalDate.now().minusDays(5),"Normalny",4,24));
        transakcje.add(new Transakcja(LocalDate.now().minusDays(5),"Ulgowy",3,15));
        transakcje.add(new Transakcja(LocalDate.now().minusDays(5),"Ulgowy",8,24));
        transakcje.add(new Transakcja(LocalDate.now().minusDays(2),"Ulgowy",12,36));
        transakcje.add(new Transakcja(LocalDate.now().minusDays(2),"Ulgowy",2,6));
        transakcje.add(new Transakcja(LocalDate.now().minusDays(2),"Normalny",4,24));
    }

    public void wydrukujTransakcje() {
        for (Transakcja t : transakcje) {
            System.out.println(t);
        }
    }

    public void wydrukujTransakcje(Predicate<Transakcja> filtr) {
        transakcje.stream()
                .filter(filtr) // Filtrowanie transakcji na podstawie predykatu
                .forEach(System.out::println); // Wypisanie przefiltrowanych transakcji
    }

    @Override
    public String toString() {
        return "Biletomat na adresie: " + address;
    }

    // Klasa wewnętrzna Transakcja
    public static class Transakcja {
        private final LocalDate data;
        private final String typBiletu;
        private final int liczba;
        private final double dochod;

        public Transakcja(LocalDate data, String typBiletu, int liczba, double dochod) {
            this.data = data;
            this.typBiletu = typBiletu;
            this.liczba = liczba;
            this.dochod = dochod;
        }

        public LocalDate getData() {
            return data;
        }

        public String getTypBiletu() {
            return typBiletu;
        }

        @Override
        public String toString() {
            return data + " : " + typBiletu + " : " + liczba + " : " + dochod+"zł";
        }
    }
}

