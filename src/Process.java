import java.io.Serializable;
import java.util.Iterator;

import java.util.Set;
import java.util.TreeSet;

public class Process extends MyObservable implements Comparable<Process>, Serializable, CalcCost {
    int next = 1;
    int id;
    Set<MaterialUsage> materials;
    Set<HumanResourceUsage> humanResources;
    double cost;
    String state;
    int duration;

    public Process(int duration) {
        id = next++;
        materials = new TreeSet<>();
        humanResources = new TreeSet<>();
        this.cost = 0;
        state = "Running";
        this.duration = duration;
    }

    public int compareTo(Process process) {
        return Integer.compare(this.id, process.id);
    }

    public void finishProcess() {
        this.state = "finished";
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
