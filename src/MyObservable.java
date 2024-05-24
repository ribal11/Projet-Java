import java.util.ArrayList;
import java.util.Iterator;

abstract class MyObservable {
    boolean changed;
    java.util.List<MyObserver> observers;

    public MyObservable() {
        changed = false;
        observers = new ArrayList<MyObserver>();
    }

    public void setChanged() {
        changed = true;
    }

    public void addObserver(MyObserver ob) {
        observers.add(ob);
    }

    public void removeObserver(MyObserver ob) {
        observers.remove(ob);
    }

    public void notifyObservers() {
        for (Iterator<MyObserver> it = observers.iterator(); it.hasNext();)
            ((MyObserver) it.next()).update();
        changed = false;
    }
}
