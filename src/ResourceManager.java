import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class ResourceManager extends MyObservable implements Serializable {
    static int next = 1;
    int id;
    Set<HumanResource> employeeManager;
    Set<Material> materialManager;

    public ResourceManager() {
        id = next++;
        employeeManager = new TreeSet<>();
        materialManager = new TreeSet<>();
    }

    public void addEmployee(HumanResource emp) {
        if (employeeManager.add(emp)) {
            setChanged();
            notifyObservers();
        }
    }

    public void addMaterial(Material material) {
        if (materialManager.add(material)) {
            setChanged();
            notifyObservers();
        }
    }
}
