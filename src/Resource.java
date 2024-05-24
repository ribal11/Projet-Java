import java.io.Serializable;
import java.util.Set;

public abstract class Resource implements Serializable, Comparable<Resource> {
    static int next = 1;
    int id;
    String type;
    String name;
    Set<String> taskAllowed;

    public Resource(String type, String name, Set<String> taskAllowed) {
        id = next++;
        this.type = type;
        this.name = name;
        this.taskAllowed = taskAllowed;
    }

    public Resource(int id, String type, String name, Set<String> taskAllowed) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.taskAllowed = taskAllowed;
    }

    public int compareTo(Resource resource) {
        return Integer.compare(this.id, resource.id);
    }

    public String toString() {
        return id + ", " + name + ", ";
    }
}
