package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by quintaartsen on 26-09-17.
 */
public class ThreadManager implements Runnable, Observer {

    private KochFractal kochFractal;
    public ArrayList<Edge> edge = new ArrayList<>();

    public ThreadManager(int nxt){
        kochFractal = new KochFractal();
        kochFractal.setLevel(nxt);
        kochFractal.addObserver(this::update);
    }

    @Override
    public void run() {

    }

    public void generateLeftEdge(){
        synchronized (this) {
            kochFractal.generateLeftEdge();
        }
    }

    public void generateBottomEdge(){
        synchronized (this) {
            kochFractal.generateBottomEdge();
        }
    }

    public void generateRightEdge(){
        synchronized (this) {
            kochFractal.generateRightEdge();
        }
    }


    public void update(Observable o, Object arg) {
        edge.add((Edge)arg);
    }
}
