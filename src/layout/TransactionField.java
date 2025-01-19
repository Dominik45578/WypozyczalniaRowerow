package layout;

import dataclass.rental.RentalServices;
import dataclass.rental.RentalTransaction;

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
         if (transaction.isActive()){
              setLayout(new GridLayout(1, 2));
         }
         else{
              setLayout(new GridLayout(1, 1));
         }
       ; // Ustawienie układu na dwie sekcje
        // Górna część: szczegóły transakcji
        JPanel detailsPanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
        detailsPanel.setLayout(new GridLayout(3,2,10,10));
        startTimeLabel = createLabel("Start :"+transaction.getRentalStart(),20);
        endTimeLabel = createLabel("Koniec : " +
            (transaction.getRentalEnd() != null ? transaction.getRentalEnd() : "Aktywna"),20);
        vehicleLabel = createLabel("Pojazd : " + transaction.getVehicle().getVehicleId(),20);
        detailsPanel.add(startTimeLabel);
        detailsPanel.add(endTimeLabel);
        detailsPanel.add(vehicleLabel);


        JPanel buttonPanel = createRoundedPanel(Colors.DARK_BLUE_ACTIVE.getColor());
        buttonPanel.setLayout(new GridBagLayout());
        if (transaction.isActive()) {
            returnButton = createRoundedButton("Zwróć ->",20);
            returnButton.addActionListener(new ReturnButtonListener());
            buttonPanel.add(returnButton);
        }

        add(detailsPanel);
        add(buttonPanel);
    }

    // Listener dla przycisku "Zwróć"
    private class ReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RentalServices.getInstance().returnVehicle(transaction.getVehicle().getVehicleId());
            transaction.endRental();
            endTimeLabel.setText("Koniec : " + transaction.getRentalEnd()); // Aktualizacja wyświetlanych danych
            remove(returnButton); // Usunięcie przycisku
            MainScreen.instance.createHistoryPanel();
            revalidate();
            repaint();
        }
    }
}
