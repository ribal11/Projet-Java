import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Projet implements Comparable<Projet>, Serializable, CalcCost, CalcDuration, CheckState {
    static int next = 10;
    ArrayList<String> possibleTasks = new ArrayList<>(
            Arrays.asList("Conception", "Préparation", "Fabrication", "Assemblage", "Test"));
    String name;
    int projId;
    Set<Task> tasks;
    String state;
    double cost;
    int duration;

    public Projet(String name) {
        this.name = name;
        projId = next;
        next += 10;
        tasks = new TreeSet<>();
        state = "No tasks added";
        cost = 0;
        duration = 0;
    }

    public int compareTo(Projet projet) {
        return Integer.compare(projId, projet.projId);
    }

    public String toString() {
        return projId + ", " + state + ", " + cost + ", " + duration + "h";
    }

    public void removeTask(String task) {
        this.possibleTasks.remove(task);
    }

    public void calcCost() {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            this.cost += task.cost;
        }
    }

    public void calcDuration() {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            this.duration += task.duration;
        }
    }

    public void checkState() {
        boolean finished = true;
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            if (task.state != "finished")
                finished = false;
        }
        if (finished) {
            this.state = "finished";
        }
    }
}
