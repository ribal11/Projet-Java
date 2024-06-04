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

   
    
    public void setHourlyRate(double hourlyRate) {
    	this.hourlyRate = hourlyRate;
    	setChanged();
    	notifyObservers();
    }

    public void updateHumanResources(String name, String spec, String job, Set<String> tasks) {
        
        this.job = job;
        this.name = name;
        this.speciality = spec;
        this.taskAllowed = tasks;

    }

    public String toString() {
        return super.toString() + speciality + ", " + job + ", " + hourlyRate;
    }
}