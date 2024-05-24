import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Projet extends MyObservable
        implements Comparable<Projet>, Serializable, CalcCost, CalcDuration, CheckState, MyObserver {
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
        return projId + ", " + name + ", " + state + ", " + cost + ", " + duration + "h";
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public void addTask(Task task) {
        if (tasks.add(task)) {
            task.addObserver(this);
            updateProject();
        }
    }

    public void removePossibleTask(String task) {
        this.possibleTasks.remove(task);
        updateProject();
    }

    public void calcCost() {
        this.cost = 0;
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            this.cost += task.cost;
        }
    }

    public void calcDuration() {
        this.duration = 0;
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task task = it.next();
            this.duration += task.duration;
        }

    }

    public void checkState() {

        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {

            Task task = it.next();
            if (task.state != "finished")
                return;
        }

        this.state = "finished";

    }

    public void updateProject() {
        calcCost();
        calcDuration();
        checkState();
        setChanged();
        notifyObservers();
    }

    public void update() {
        updateProject();
    }

}
