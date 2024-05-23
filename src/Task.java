import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import java.util.Iterator;

public class Task implements Comparable<Task>, Serializable, CalcCost, CalcDuration, CheckState {
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

    public void calcCost() {
        Iterator<Process> it = processes.iterator();
        while (it.hasNext()) {
            Process process = it.next();
            cost += process.cost;
        }
    }

    public void calcDuration() {
        Iterator<Process> it = processes.iterator();
        while (it.hasNext()) {
            Process process = it.next();
            this.duration += process.duration;
        }
    }

    public void checkState() {
        boolean finished = true;
        Iterator<Process> it = processes.iterator();
        while (it.hasNext()) {
            Process process = it.next();
            if (process.state != "finished")
                finished = false;
        }

        if (finished)
            this.state = "finished";
    }
}
