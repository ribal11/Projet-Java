import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class MyObservable {
    boolean changed;
    List<Observer> observers;
    
    public MyObservable() {
        changed = false;
        
        observers = new ArrayList<Observer>();
    }

    public void setChanged() {
        changed = true;
    }

    public void addObserver(Observer ob) {
        observers.add(ob);
    }

    public void removeObserver(Observer ob) {
        observers.remove(ob);
    }

    public void notifyObservers() {
        for (Iterator<Observer> it = observers.iterator(); it.hasNext();)
            ((Observer) it.next()).update();
        changed = false;
    }
}
