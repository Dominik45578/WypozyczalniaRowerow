package dataclass.rental;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartGenerator {

    public static JFreeChart createVehicleChart() {
        RentalServices rentalService = RentalServices.getInstance();

        // Pobranie danych o pojazdach
        int totalVehicles = rentalService.getAllVehicles().size();
        int rentedVehicles = rentalService.getRentedVehicles().size();
        int brokenVehicles = rentalService.getBrokenVehicle().size();
        int availableVehicles = totalVehicles - rentedVehicles - brokenVehicles;

        // Tworzenie danych dla wykresu
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Total Vehicles", totalVehicles);
        dataset.setValue("Rented Vehicles", rentedVehicles);
        dataset.setValue("Broken Vehicles", brokenVehicles);
        dataset.setValue("Available Vehicles", availableVehicles);

        // Tworzenie wykresu
        return ChartFactory.createPieChart(
                "Vehicle Statistics",
                dataset,
                true, true, false
        );
    }

    public static JFreeChart createTransactionChart() {
        RentalServices rentalService = RentalServices.getInstance();

        // Pobieranie historii transakcji i grupowanie ich według miesiąca
        Map<Month, Long> transactionsByMonth = rentalService.getRentalTransactionMap().values().stream()
                .collect(Collectors.groupingBy(
                        transaction -> transaction.getRentalStart().getMonth(), // Grupowanie po miesiącu
                        Collectors.counting() // Liczenie transakcji
                ));

        // Tworzenie danych dla wykresu
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Month month : Month.values()) {
            long count = transactionsByMonth.getOrDefault(month, 0L);
            dataset.addValue(count, "Transactions", month.name());
        }

        // Tworzenie wykresu
        return ChartFactory.createLineChart(
                "Monthly Transactions",
                "Month",
                "Number of Transactions",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
    }
}


