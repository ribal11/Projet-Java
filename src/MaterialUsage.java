import java.io.Serializable;

public class MaterialUsage implements MaterialCostCalculator, Serializable, Comparable<MaterialUsage> {
    static int next = 1;
    Material material;
    int id;
    int qty;

    public MaterialUsage(Material material, int qty) {
        id = next++;
        this.material = material;
        this.qty = qty;

    }

    public double calculateTotalCost(int unitCost, double qty) {
        return unitCost * qty;
    }

    public int compareTo(MaterialUsage other) {
        return Integer.compare(this.id, other.id);
    }

    public String toString() {
        return id + ", " + material.name + ", " + qty + ", " + calculateTotalCost(qty, material.unitCost);
    }
}
