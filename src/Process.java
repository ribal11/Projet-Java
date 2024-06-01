import java.io.Serializable;
import java.util.Iterator;

import java.util.Set;
import java.util.TreeSet;

public class Process extends MyObservable implements Comparable<Process>, Serializable, CalcCost, MyObserver {
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
    	
    	Iterator<HumanResourceUsage> itHuman = humanResources.iterator();
    	Iterator<MaterialUsage> itMaterial = materials.iterator();
    	while(itHuman.hasNext()) {
    		HumanResourceUsage humanUsage = itHuman.next();
    		humanUsage.removeObserver(this);
    		itHuman.remove();
    	}
    	
    	while(itMaterial.hasNext()) {
    		MaterialUsage materialUsage = itMaterial.next();
    		materialUsage.removeObserver(this);
    		itMaterial.remove();
    		}
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
        materialUsage.addObserver(this);
        update();
    }

    public void addHumanResourceUsage(HumanResourceUsage humanResourceUsage) {
        humanResources.add(humanResourceUsage);
        humanResourceUsage.addObserver(this);
        update();
    }
    
    public void removeMaterialUsage(MaterialUsage materialUsage) {
    	materials.remove(materialUsage);
    	materialUsage.removeObserver(this);
    	update();
    }
    
    public void removeHumanUsage(HumanResourceUsage humanResourceUsage) {
    	humanResources.remove(humanResourceUsage);
    	humanResourceUsage.removeObserver(this);
    	update();
    }
    
    public void addObserverToHumanUsable(Set<HumanResource> humanResourceSet) {
    	Iterator<HumanResourceUsage> it = this.humanResources.iterator();
    	while(it.hasNext()) {
    		HumanResourceUsage human = it.next();
    		human.addObserver(this);
    		Iterator<HumanResource> itRc = humanResourceSet.iterator();
    		
    		
    		while(itRc.hasNext()) {
    			HumanResource humanRc = itRc.next();
    			if (humanRc.id == human.labor.id) {
    				human.addObserverToLabor(humanRc);
    			}
    			
    		}
    		
    	}
    }
    
    public void addObserverToMaterialUsable(Set<Material> materialResourceSet) {
    	Iterator<MaterialUsage> it = this.materials.iterator();
    	while(it.hasNext()) {
    		MaterialUsage mt = it.next();
    		mt.addObserver(this);
    		Iterator<Material> itMt = materialResourceSet.iterator();
    		
    		
    		while(itMt.hasNext()) {
    			Material material = itMt.next();
    			if (material.id == mt.material.id) {
    				mt.addObserverToMaterial(material);
    			}
    			
    		}
    		
    	}
    }
    public void addResourcesToSet(Set<HumanResourceUsage> humanUsageSet, Set<MaterialUsage> materialUsageSet) {
    	Iterator<HumanResourceUsage> itHuman = this.humanResources.iterator();
    	Iterator<MaterialUsage> itMaterial = this.materials.iterator();
    	int maxResourceUsageId = 1;
    	while(itHuman.hasNext()) {
    		HumanResourceUsage humanResource = itHuman.next();
    		humanUsageSet.add(humanResource);
    		maxResourceUsageId = Math.max(maxResourceUsageId, humanResource.id);
    	}
    	while(itMaterial.hasNext()) {
    		MaterialUsage materialUsage = itMaterial.next();
    		materialUsageSet.add(materialUsage);
    		maxResourceUsageId = Math.max(maxResourceUsageId, materialUsage.id);
    	}
    	
    	ResourceUsage.next = maxResourceUsageId + 1;
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
        
    }
    
    public void update() {
    	calcCost();
    	setChanged();
    	notifyObservers();
    }

    public String toString() {
        return this.id + ", " + this.cost + ", " + this.state + this.duration;
    }
    
    public String toComboBoxString() {
    	return  name;
    }
}
