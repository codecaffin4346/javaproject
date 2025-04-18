import java.io.*;
import java.util.*;

public class DataManager {
    public static List<HealthRecord> readData(String filename) {
        List<HealthRecord> records = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    int pulse = Integer.parseInt(parts[2]);
                    int bp = Integer.parseInt(parts[3]);
                    records.add(new HealthRecord(name, age, pulse, bp));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return records;
    }

    public static void exportToCSV(List<HealthRecord> records, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Name,Age,Pulse,BP");
            for (HealthRecord r : records) {
                writer.println(r.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error writing CSV.");
        }
    }
}
