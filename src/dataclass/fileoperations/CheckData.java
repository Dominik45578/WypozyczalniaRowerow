package dataclass.fileoperations;

import dataclass.user.Customer;
import dataclass.user.PrivateCustomer;
import dataclass.user.User;

import java.util.regex.Pattern;

public class CheckData {

    // Sprawdzenie poprawności adresu (np. "Stanisława Skarżyńskiego")
    public static boolean isValidAdres(String adres) {
        if (adres == null || adres.isEmpty()) {
            return false;
        }
        return adres.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+( [A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+)*$");
    }

    // Sprawdzenie poprawności numeru budynku (np. "4b/d1", "123", "4/4")
    public static boolean isValidHouseNumber(String houseNumber) {
        if (houseNumber == null || houseNumber.isEmpty()) {
            return false;
        }
        return houseNumber.matches("^[0-9]+[a-zA-Z]?(/[0-9]+[a-zA-Z]?)?$");
    }

    public static boolean isValidAStreetAndNumber(String adres) {
    if (adres == null || adres.isEmpty()) {
        return false;
    }

    // Użycie wyrażenia regularnego do podziału adresu na nazwę ulicy i numer
    String regex = "^(?<street>[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+(?: [A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+)*?) (?<number>[0-9]+[a-zA-Z]?(/[0-9]+[a-zA-Z]?)?)$";
    var matcher = java.util.regex.Pattern.compile(regex).matcher(adres);

    if (!matcher.matches()) {
        return false;
    }

    // Pobranie grup dla walidacji szczegółowej (opcjonalnie, jeśli potrzebujesz)
    String streetName = matcher.group("street");
    String houseNumber = matcher.group("number");

    // Możesz dodać dodatkowe sprawdzenia, jeśli potrzebne

    return true; // Adres pasuje do całego wzorca
}


    // Sprawdzenie poprawności numeru PESEL
    public static boolean isValidPesel(String pesel) {
        if (pesel == null || !pesel.matches("\\d{11}")) {
            return false;
        }
//        if(CentralDatabase.getInstance().existsObject(
//                User.class,
//                (Customer customer)->customer.getPesel().equals(pesel)
//                )){
//            return false;
//        }
//
//        int[] weights = {9, 7, 3, 1, 9, 7, 3, 1, 9, 7, 1};
//        int sum = 0;
//        for (int i = 0; i < 11; i++) {
//            sum += (pesel.charAt(i) - '0') * weights[i];
//        }
//
//        return sum % 10 == 0;
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (CentralDatabase.getInstance().emailExists(email)) {
            return false;
        }
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
     public static boolean isValidExistingEmail(String email) {
        if (CentralDatabase.getInstance().emailExists(email)) {
            return true;
        }
        return false;
    }

    // Sprawdzenie, czy dwa hasła są takie same
    public static boolean arePasswordsMatching(String password1, String password2) {
        if (password1 == null || password2 == null) {
            return false;
        }

        return password1.equals(password2) && isValidPassword(password1) && isValidPassword(password2);
    }

    public static boolean isValidPassword(String password) {
        // Sprawdzenie minimalnej długości
        if (password.length() < 8) {
            return false;
        }

        // Sprawdzenie obecności dużej litery
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Sprawdzenie obecności cyfry
        if (!password.matches(".*[0-9].*")) {
            return false;
        }

        // Jeśli wszystkie warunki są spełnione
        return true;
    }


    // Sprawdzenie poprawności kodu pocztowego
    public static boolean isValidPostalCode(String postalCode) {
        if (postalCode == null) {
            return false;
        }
        return postalCode.matches("\\d{5}") || postalCode.matches("\\d{2}-\\d{3}");
    }

    // Sprawdzenie poprawności numeru telefonu (9 cyfr)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches("\\d{9}");
    }

    // Sprawdzenie, czy podany tekst to liczba
    public static boolean isNumber(String input) {
        if (input == null) {
            return false;
        }
        return input.matches("\\d+");
    }

    // Sprawdzenie, czy podany tekst to poprawna data (format YYYY-MM-DD)
    public static boolean isValidDate(String date) {
        if (date == null) {
            return false;
        }
        return date.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    public static boolean isValidName(String name) {
        if (name == null || name.length() < 3) {
            return false;
        }
        return Pattern.matches("[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*", name);
    }
    public static boolean isValidNIP(String nip) {
        return nip != null && nip.matches("\\d{10}");
    }
}

