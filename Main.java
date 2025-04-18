
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<HealthRecord> records = DataManager.readData("data/health_records.txt");

        System.out.println("Student Health Tracker");
        for (HealthRecord r : records) {
            System.out.println("- " + r.getName() + ", Age: " + r.getAge() + ", Pulse: " + r.getPulse() + ", BP: " + r.getBP());
        }

        SummaryGenerator.generateSummary(records);
        DataManager.exportToCSV(records, "export/health_records.csv");
        System.out.println("\nData exported to export/health_records.csv");
    }
}
