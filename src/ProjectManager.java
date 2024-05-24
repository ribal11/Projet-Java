import java.util.Set;
import java.util.TreeSet;

public class ProjectManager extends MyObservable implements MyObserver {
    static int next = 100;
    int id;
    public Set<Projet> myProjects;

    public ProjectManager() {
        id = next;
        next += 100;
        myProjects = new TreeSet<>();
    }

    public void addProject(Projet projet) {
        myProjects.add(projet);
        projet.addObserver(this);
        setChanged();
        notifyObservers();
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
