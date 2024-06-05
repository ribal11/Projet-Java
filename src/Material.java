import java.util.Set;

public class Material extends Resource {
    String materialType;
    double unitCost;
    String description;

    public Material(String name, String type, double unitCost, String description, Set<String> taskAllowed) {
        super("Material", name, taskAllowed);
        this.description = description;
        this.materialType = type;
        this.unitCost = unitCost;
    }

    public Material(int id, String name, String type, double unitCost, String description, Set<String> taskAllowed) {
        super(id, "Material", name, taskAllowed);
        this.materialType = type;
        this.unitCost = unitCost;
        this.description = description;
        setChanged();
    	notifyObservers();

    }
    
    public void setUnitCost(double unitCost) {
    	this.unitCost = unitCost;
    	setChanged();
    	notifyObservers();
    }
   

    public void updateMaterialResources(String name, String type, String desc, Set<String> tasks) {
        this.description = desc;
        this.materialType = type;
        this.name = name;
        this.taskAllowed = tasks;
    }

    public String toString() {
        return super.toString() + materialType + ", " + "," + description + "," + unitCost;
    }
}
