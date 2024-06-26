import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import java.util.Iterator;

public class Task extends MyObservable
        implements Comparable<Task>, Serializable, CalcCost, CalcDuration, CheckState, Observer {
    static int next = 1;
    int id;
    String name;
    Set<Process> processes;
    double cost;
    String state;
    int duration;
    String type;
    boolean finished = false;

    public Task(String name, String type) {
        id = next++;
        this.name = name;
        this.type = type;
        processes = new TreeSet<>();
        cost = 0;
        state = "No processes added";
        duration = 0;
    }

    public int compareTo(Task task) {
        return Integer.compare(this.id, task.id);
    }

    public String toComboBoxString() {
    	return  name;
    }
    public String toString() {
        return id + ", " + name + ", " + cost + ", " + state + ", " + duration + "h";
    }

    public void addProcess(Process process) {
        if (processes.add(process)) {
            process.addObserver(this);
            updateTask();
        }
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public void removeProcess(Process process) {
        if (processes.remove(process)) {
            process.removeObserver(this);
            updateTask();
        }
    }
    
    public void cleanUp() {
    	Iterator<Process> itProcess = this.processes.iterator();
    	while(itProcess.hasNext()) {
    		Process process = itProcess.next();
    		process.removeObserver(this);
    		itProcess.remove();
    		
    	}
    }
    
    public void addProcessesToSet(Set<Process> processSet, Set<HumanResourceUsage> humanUsage, Set<MaterialUsage> materialUsage) {
    	Iterator<Process> itProcess = this.processes.iterator();
    	int maxProcessId = 1;
    	Iterator<Process> itProcessSet = processSet.iterator();
    	while(itProcessSet.hasNext()) {
    		maxProcessId = itProcessSet.next().id;
    	}
    	while(itProcess.hasNext()) {
    		Process process = itProcess.next();
    		processSet.add(process);
    		process.addResourcesToSet(humanUsage, materialUsage);
    		maxProcessId = Math.max(maxProcessId, process.id);
    		
    	}
    	
    	Process.next = maxProcessId + 1;
    	
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
            if (!process.finished) {
            	this.state= "Running";
                this.finished = false;
                return;
            }
            	
        }
        this.state = "finished";
        this.finished = true;

    }

    public void addObserverToProcesses(Set<HumanResource> humanResourceSet, Set<Material> materialResourceSet) {
        for (Process process : processes) {
        	
            process.addObserver(this);
            process.addObserverToHumanUsable(humanResourceSet);
            process.addObserverToMaterialUsable(materialResourceSet);
        }
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
