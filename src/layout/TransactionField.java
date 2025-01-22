package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.rental.RentalServices;
import dataclass.rental.RentalTransaction;
import dataclass.user.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionField extends ContentField {
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel vehicleLabel;
    private JButton returnButton;
    private RentalTransaction transaction;

    public TransactionField(RentalTransaction transaction) {
        super(transaction.getTransactionID(), Colors.DARK_BLUE_ACTIVE.getColor(), new Dimension(0, 100));
        this.transaction = transaction;
        initializeLayout();
    }

    private void initializeLayout() {
        // Pobieranie aktualnego użytkownika
        var currentUser = CentralDatabase.getInstance().getCurrentUser();

        // Sprawdzenie układu w zależności od typu użytkownika i stanu transakcji
        if (transaction.isActive() && currentUser.getID().equals(transaction.getUser().getID())) {
            // Układ dla właściciela aktywnej transakcji z przyciskiem "Zwróć"
            setLayout(new GridLayout(1, 2));
        } else if (!transaction.isActive() || currentUser.getUserType() == Users.ROOT) {
            // Układ dla zakończonej transakcji lub gdy użytkownik to Root
            setLayout(new GridLayout(1, 1));
        } else {
            setLayout(new GridLayout(1, 2)); // Domyślny układ
        }

        // Panel szczegółów transakcji
        JPanel detailsPanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
        detailsPanel.setLayout(new GridLayout(currentUser.getUserType() == Users.ROOT ? 5 : 3, 2, 10, 10));
        startTimeLabel = createLabel("Start : " + transaction.getRentalStart(), 16);
        endTimeLabel = createLabel("Koniec : " + (transaction.getRentalEnd() != null ? transaction.getRentalEnd() : "Aktywna"), 16);
        vehicleLabel = createLabel("Pojazd : " + transaction.getVehicle().getVehicleId(), 16);
        detailsPanel.add(startTimeLabel);
        detailsPanel.add(endTimeLabel);
        detailsPanel.add(vehicleLabel);

        // Dodatkowe dane dla użytkownika Root
        if (currentUser.getUserType() == Users.ROOT) {
            JLabel userIdLabel = createLabel("ID Użytkownika: " + transaction.getUser().getID(), 16);
            JLabel userNameLabel = createLabel("Użytkownik: " + transaction.getUser().getFirstName() + " " + transaction.getUser().getLastName(), 16);
            detailsPanel.add(userIdLabel);
            detailsPanel.add(userNameLabel);
        }

        // Panel przycisku "Zwróć"
        JPanel buttonPanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
        buttonPanel.setLayout(new GridBagLayout());
        if (transaction.isActive() && currentUser.getID().equals(transaction.getUser().getID())) {
            returnButton = createRoundedButton("Zwróć ->", 20);
            returnButton.addActionListener(new ReturnButtonListener());
            buttonPanel.add(returnButton);
        }

        // Dodanie paneli do głównego układu
        add(detailsPanel);
        if (transaction.isActive() && currentUser.getID().equals(transaction.getUser().getID())) {
            add(buttonPanel);
        }
    }


    // Listener dla przycisku "Zwróć"
    private class ReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RentalServices.getInstance().returnVehicle(transaction.getVehicle().getVehicleId());
            endTimeLabel.setText("Koniec : " + transaction.getRentalEnd()); // Aktualizacja wyświetlanych danych
            remove(returnButton); // Usunięcie przycisku
            MainScreen.instance.createHistoryPanel();
            revalidate();
            repaint();
        }
    }
}
