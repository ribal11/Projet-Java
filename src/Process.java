import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Process implements Comparable<Process>, Serializable, CalcCost {
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

    public void calcCost() {
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

    public String toString() {
        return this.id + ", " + this.cost + ", " + this.state + this.duration;
    }
}
