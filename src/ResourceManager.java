import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class ResourceManager extends MyObservable implements Serializable, Observer {
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
        	emp.addObserver(this);
            setChanged();
            notifyObservers();
        }
    }

    public void addMaterial(Material material) {
        if (materialManager.add(material)) {
        	material.addObserver(this);
            setChanged();
            notifyObservers();
        }
    }
    
    public void removeMaterial(Material material) {
    	if (materialManager.remove(material)) {
    		material.removeObserver(this);
    		setChanged();
            notifyObservers();
    	}
    }
    
    public void removeEmployee(HumanResource emp) {
    	if (employeeManager.remove(emp)) {
    		emp.removeObserver(this);
    		setChanged();
            notifyObservers();
    	}
    }
    
    public void update() {
    	setChanged();
    	notifyObservers();
    }
}
