public class HumanResourceUsage extends ResourceUsage implements LaborCostCalculator {
    
    int workingHour;
    HumanResource labor;

    public HumanResourceUsage(int workingHour, HumanResource labor) {
        super();
        this.workingHour = workingHour;
        this.labor = labor;
        this.labor.addObserver(this);
    }

    

    public double calcLaborCost(int nbrHours, double hourlyRate) {
        return nbrHours * hourlyRate;
    }
    
    public void setWorkingHour(int hours) {
    	this.workingHour = hours;
    	setChanged();
    	notifyObservers();
    }

    public String toString() {
        return super.id + ", " + workingHour + ", " + labor.name + ", " +  calcLaborCost(workingHour, labor.hourlyRate);
    }
    public String toComboBoxString() {
    	return this.id + ", " + labor.name;
    }
    
    public void cleanUp() {
    	this.labor.removeObserver(this);
    	setChanged();
    	notifyObservers();
    }
    
    public void update() {
    	setChanged();
    	notifyObservers();
    }
}
