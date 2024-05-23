public class HumanResourceUsage implements Comparable<HumanResourceUsage>, LaborCostCalculator {
    static int next = 1;
    int id;
    int workingHour;
    HumanResource labor;

    public HumanResourceUsage(int workingHour, HumanResource labor) {
        id = next++;
        this.workingHour = workingHour;
        this.labor = labor;
    }

    public int compareTo(HumanResourceUsage other) {
        return Integer.compare(this.id, other.id);
    }

    public double calcLaborCost(int nbrHours, double hourlyRate) {
        return nbrHours * hourlyRate;
    }

    public String toString() {
        return id + ", " + workingHour + ", " + labor.name + calcLaborCost(workingHour, labor.hourlyRate);
    }
}
