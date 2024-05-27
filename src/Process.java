import java.io.Serializable;
import java.util.Iterator;

import java.util.Set;
import java.util.TreeSet;

public class Process extends MyObservable implements Comparable<Process>, Serializable, CalcCost {
    static int next = 1;
    int id;
    String name;
    Set<MaterialUsage> materials;
    Set<HumanResourceUsage> humanResources;
    double cost;
    String state;
    int duration;
    boolean finished;
    public Process( String name, int duration) {
        id = next++;
        materials = new TreeSet<>();
        humanResources = new TreeSet<>();
        this.cost = 0;
        state = "Running";
        this.name = name;
        this.finished = false;
        this.duration = duration;
    }

    public int compareTo(Process process) {
        return Integer.compare(this.id, process.id);
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    public void finishProcess() {
        this.state = "finished";
        this.finished = true;
        setChanged();
        notifyObservers();
    }
    
    public void cleanUp() {
    	this.materials.clear();
    	this.humanResources.clear();
    	setChanged();
    	notifyObservers();
    }
    
    public void stillProcessing() {
    	this.state = "Running";
    	this.finished = false;
    	setChanged();
    	notifyObservers();
    }

    public void addMaterialUsage(MaterialUsage materialUsage) {
        materials.add(materialUsage);
        calcCost();
    }

    public void addHumanResourceUsage(HumanResourceUsage humanResourceUsage) {
        humanResources.add(humanResourceUsage);
        calcCost();
    }

    public void updateHumanResourceUsage(HumanResourceUsage rec) {

        for (HumanResourceUsage usage : humanResources) {
            if (usage.id == rec.id) {
                usage.labor = rec.labor;
                usage.workingHour = rec.workingHour;
                calcCost();
                return;
            }
        }

    }

    public void updateMaterialResourceUsage(MaterialUsage rec) {

        for (MaterialUsage usage : materials) {
            if (usage.id == rec.id) {
                usage.material = rec.material;
                usage.qty = rec.qty;

                calcCost();
            }
        }

    }
    
    public void setDuration(int duration) {
    	this.duration = duration;
    	setChanged();
    	notifyObservers();
    	
    }

    public void calcCost() {
        cost = 0;
        Iterator<MaterialUsage> itMaterial = materials.iterator();
        Iterator<HumanResourceUsage> itHumanResource = humanResources.iterator();

        while (itMaterial.hasNext()) {
            MaterialUsage mat = itMaterial.next();
            this.cost += mat.calculateTotalCost(mat.qty, mat.material.unitCost);
        }

        while (itHumanResource.hasNext()) {
            HumanResourceUsage hum = itHumanResource.next();
            this.cost += hum.calcLaborCost(hum.workingHour, hum.labor.hourlyRate);
        }
        setChanged();
        notifyObservers();
    }

    public String toString() {
        return this.id + ", " + this.cost + ", " + this.state + this.duration;
    }
}
