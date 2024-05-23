import java.util.Set;

public class HumanResource extends Resource {
    String speciality;
    String job;
    double hourlyRate;

    public HumanResource(String name, String speciality, String job, double hourlyRate, Set<String> tasksAllowed) {
        super("Human", name, tasksAllowed);
        this.speciality = speciality;
        this.job = job;
        this.hourlyRate = hourlyRate;

    }

    public HumanResource(int id, String name, String speciality, String job, double hourlyRate,
            Set<String> tasksAllowed) {
        super(id, "Human", name, tasksAllowed);
        this.speciality = speciality;
        this.job = job;
        this.hourlyRate = hourlyRate;

    }

    public String toString() {
        return super.toString() + speciality + ", " + job + ", " + hourlyRate;
    }
}