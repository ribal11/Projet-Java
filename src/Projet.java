import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;
public class Projet extends MyObservable
        implements Comparable<Projet>, Serializable, CalcCost, CalcDuration, CheckState, MyObserver {
    static int next = 10;
   
    TreeMap<String, Boolean> possibleTasks ;
    

    String name;
    int projId;
    Set<Task> tasks;
    String state;
    double cost;
    int duration;
    boolean finished = false;
    public Projet(String name) {
        this.name = name;
        projId = next;
        next += 10;
        tasks = new TreeSet<>();
        state = "No tasks added";
        cost = 0;
        duration = 0;
        this.possibleTasks = new TreeMap<>();
        this.possibleTasks.put("conception", true);
        this.possibleTasks.put("rawMaterial", true);
        this.possibleTasks.put("fabrication", true);
        this.possibleTasks.put("assemblage", true);
        this.possibleTasks.put("test", true);
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
        this.possibleTasks.put(task, false);
//        updateProject();
    }
    
    public void cleanUp() {
    	Iterator<Task> itTask = this.tasks.iterator();
    	while(itTask.hasNext()) {
    		Task task = itTask.next();
    		task.removeObserver(this);
    		itTask.remove();  		
    	}    	
    }
    
    public void removeTask(Task task) {
    	task.removeObserver(this);
    	this.tasks.remove(task);
    	this.possibleTasks.put(task.type, true);
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
            if (!task.finished) {
            	
            	this.state = "Running";
            	this.finished = false;
            	return;
            }
                
        }

        this.state = "finished";
        this.finished = true;

    }
    public void addObserverToTasks() {
        for (Task task : tasks) {
            task.addObserver(this);
            task.addObserverToProcesses();
        }
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
