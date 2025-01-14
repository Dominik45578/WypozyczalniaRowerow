package dataclass.fileoperations;

import java.util.regex.Pattern;

public class CheckData {

    // Sprawdzenie poprawności adresu e-mail
    public static boolean isValidEmail(String email) {
        if (email == null || email.length() < 6) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }

    // Sprawdzenie, czy dwa hasła są takie same
    public static boolean arePasswordsMatching(String password1, String password2) {
        if (password1 == null || password2 == null) {
            return false;
        }

        return password1.equals(password2);
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
        if (name == null || name.isEmpty()) {
            return false;
        }
        return Pattern.matches("[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*", name);
    }

    public static boolean isValidAdres(String adres){
        return !adres.matches(".*[0-9].*");
    }
}

