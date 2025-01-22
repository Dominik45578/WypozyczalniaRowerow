package layout;

import dataclass.fileoperations.CentralDatabase;
import dataclass.user.Employee;
import dataclass.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditiableEmployeeViewPanel extends ContentField {
    private Employee employee;

    public EditiableEmployeeViewPanel(Employee employee) {
        super("ID : " + employee.getID(), Colors.DARK_BLUE_ACTIVE.getColor(), new Dimension(700, 100));
        this.employee = employee;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createEmployeePanel();
    }

    private void createEmployeePanel() {
        // Wyświetlanie imienia i nazwiska pracownika
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        infoPanel.setOpaque(false);
        infoPanel.add(createLabel(employee.getFirstName() + " " + employee.getSecondName() + " " + employee.getLastName(), 16));
        infoPanel.add(createLabel( "Liczba pojazdów : " + employee.getNumberOfRentedItems()+ " Saldo :" + employee.getSaldoString(), 16));
        add(infoPanel, BorderLayout.CENTER);

        // Panel akcji (przyciski) po prawej
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);

        // Przycisk dodawania salda
        JButton addSaldoButton = createRoundedButton("Doładuj saldo", 20);
        addSaldoButton.addActionListener(e -> {
            // Pole tekstowe do wprowadzania kwoty
            String input = JOptionPane.showInputDialog(
                    this,
                    "Wpisz kwotę do doładowania dla: " + employee.getFirstName(),
                    "Doładowanie salda",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (input != null && !input.isEmpty()) {
                try {
                    float amount = Integer.parseInt(input);
                    if (amount > 0) {
                        employee.addToSaldo(amount); // Zakładam, że istnieje metoda addSaldo w klasie Employee
                        JOptionPane.showMessageDialog(
                                this,
                                "Saldo zostało doładowane o " + amount + " dla " + employee.getFirstName(),
                                "Sukces",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        CentralDatabase.getInstance().addObject(User.class,employee.getID(),employee);
                        CentralDatabase.getInstance().addObject(User.class,employee.getEmployer().getID(),employee.getEmployer());
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Kwota musi być większa od zera.",
                                "Błąd",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Wprowadź prawidłową liczbę.",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Operacja została anulowana.",
                        "Informacja",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        actionPanel.add(addSaldoButton);

        // Przycisk usuwania pracownika (widoczny, gdy brak aktywnych wypożyczeń)
        if (employee.getNumberOfRentedItems() == 0) {
            JButton removeEmployeeButton = createRoundedButton("Usuń", 20);
            removeEmployeeButton.addActionListener(e -> {
                // Usunięcie pracownika
                employee.getEmployer().getEmployees().remove(employee.getID());
                CentralDatabase.getInstance().delete(User.class, employee.getID());
                JOptionPane.showMessageDialog(this, "Pracownik usunięty: " + employee.getFirstName());
            });
            actionPanel.add(removeEmployeeButton);
        }

        add(actionPanel, BorderLayout.EAST);
    }
}
