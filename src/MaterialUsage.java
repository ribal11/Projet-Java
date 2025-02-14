

public class MaterialUsage extends ResourceUsage implements MaterialCostCalculator {
    
    Material material;
    
    int qty;

    public MaterialUsage(Material material, int qty) {
        super();
        this.material = material;
        this.material.addObserver(this);
        this.qty = qty;

    }

    public double calculateTotalCost(int unitCost, double qty) {
        return unitCost * qty;
    }
    
    public void addObserverToMaterial(Material mt) {
    	this.material = mt;
    	
    	this.material.addObserver(this);
    }
    
    public void setQty(int qty) {
    	this.qty = qty;
    	setChanged();
    	notifyObservers();
    }
    

    public String toString() {
        return super.id + ", " + material.name + ", " + qty + ", " + calculateTotalCost(qty, material.unitCost);
    }
    
    public void cleanUp() {
    	this.material.removeObserver(this);
    	setChanged();
    	notifyObservers();
    }
    
    public String toComboBoxString() {
    	return id + ", " + this.material.name;
    }
    public void update() {
    	setChanged();
    	notifyObservers();
    }
}
