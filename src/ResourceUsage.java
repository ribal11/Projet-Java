import java.io.Serializable;

public abstract class ResourceUsage extends MyObservable implements Comparable<ResourceUsage>, Serializable, MyObserver {
	static int next = 1;
    int id;
    
    public ResourceUsage() {
    	this.id = next++;
    }
    
    public int compareTo(ResourceUsage r) {
    	return Integer.compare(this.id, r.id);
    }
}
