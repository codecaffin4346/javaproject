public class HealthRecord {
    private String name;
    private int age;
    private int pulse;
    private int bp;

    public HealthRecord(String name, int age, int pulse, int bp) {
        this.name = name;
        this.age = age;
        this.pulse = pulse;
        this.bp = bp;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public int getPulse() { return pulse; }
    public int getBP() { return bp; }

    public String toCSV() {
        return name + "," + age + "," + pulse + "," + bp;
    }
}
