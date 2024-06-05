public class HumanResourceUsage extends ResourceUsage implements LaborCostCalculator {
    
    int workingHour;
    HumanResource labor;

    public HumanResourceUsage(int workingHour, HumanResource labor) {
        super();
        this.workingHour = workingHour;
        this.labor = labor;
        this.labor.addObserver(this);;
    }

    

    public double calcLaborCost(int nbrHours, double hourlyRate) {
        return nbrHours * hourlyRate;
    }
    
    public void setWorkingHour(int hours) {
    	this.workingHour = hours;
    	super.setChanged();
    	super.notifyObservers();
    }

    public String toString() {
        return super.id + ", " + workingHour + ", " + labor.name + ", " +  calcLaborCost(workingHour, labor.hourlyRate);
    }
    public String toComboBoxString() {
    	return this.id + ", " + labor.name;
    }
    
    public void addObserverToLabor(HumanResource humanRc) {
    	this.labor = humanRc;
    	this.labor.addObserver(this);
    	
    }
    public void cleanUp() {
    	this.labor.removeObserver(this);
    	super.setChanged();
    	super.notifyObservers();
    }
    
    public void update() {
    	
    	super.setChanged();
    	super.notifyObservers();
    }
}
