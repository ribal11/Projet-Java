import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import java.util.Iterator;

public class Task extends MyObservable
        implements Comparable<Task>, Serializable, CalcCost, CalcDuration, CheckState, MyObserver {
    int next = 1;
    int id;
    String name;
    Set<Process> processes;
    double cost;
    String state;
    int duration;

    public Task(String name) {
        id = next++;
        this.name = name;
        processes = new TreeSet<>();
        cost = 0;
        state = "No processes added";
        duration = 0;
    }

    public int compareTo(Task task) {
        return Integer.compare(this.id, task.id);
    }

    public String toString() {
        return id + ", " + cost + ", " + state + ", " + duration + "h";
    }

    public void addProcess(Process process) {
        if (processes.add(process)) {
            process.addObserver(this);
            updateTask();
        }
    }

    public void removeProcess(Process process) {
        if (processes.remove(process)) {
            process.removeObserver(this);
            updateTask();
        }
    }

    public void calcCost() {
        this.cost = 0;
        Iterator<Process> it = processes.iterator();
        while (it.hasNext()) {
            Process process = it.next();
            cost += process.cost;
        }

    }

    public void calcDuration() {
        this.duration = 0;
        Iterator<Process> it = processes.iterator();
        while (it.hasNext()) {
            Process process = it.next();
            this.duration += process.duration;
        }

    }

    public void checkState() {

        Iterator<Process> it = processes.iterator();
        while (it.hasNext()) {
            Process process = it.next();
            if (process.state != "finished")
                return;
        }
        this.state = "finished";

    }

    public void updateTask() {
        calcCost();
        calcDuration();
        checkState();

        setChanged();
        notifyObservers();
    }

    public void update() {
        updateTask();
    }
}
