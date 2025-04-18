import java.util.List;

public class SummaryGenerator {
    public static void generateSummary(List<HealthRecord> records) {
        int highBPCount = 0;
        int totalPulse = 0;

        System.out.println("\nHealth Summary:");
        for (HealthRecord r : records) {
            totalPulse += r.getPulse();
            if (r.getBP() > 140) {
                highBPCount++;
            }
        }

        double avgPulse = totalPulse / (double) records.size();

        System.out.println("- Average Pulse: " + avgPulse);
        System.out.println("- Students with high BP (>140): " + highBPCount);

        System.out.println("\nSimple Pulse Graph:");
        for (HealthRecord r : records) {
            System.out.printf("%s: ", r.getName());
            for (int i = 0; i < r.getPulse() / 2; i++) System.out.print("|");
            System.out.println(" (" + r.getPulse() + ")");
        }
    }
}
