package calculate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * Created by quintaartsen on 26-09-17.
 */
public class ThreadManager implements Callable<ArrayList<Edge>>, Observer {

    private KochFractal kochFractal;
    public ArrayList<Edge> edge = new ArrayList<>();

    public ThreadManager(int nxt){
        kochFractal = new KochFractal();
        kochFractal.setLevel(nxt);
        kochFractal.addObserver(this::update);
    }

    public void generateLeftEdge(){
        synchronized (kochFractal) {
            kochFractal.generateLeftEdge();
        }
    }

    public void generateBottomEdge(){
        synchronized (kochFractal) {
            kochFractal.generateBottomEdge();
        }
    }

    public void generateRightEdge(){
        synchronized (kochFractal) {
            kochFractal.generateRightEdge();
        }
    }


    public void update(Observable o, Object arg) {
        edge.add((Edge)arg);
    }

    @Override
    public ArrayList<Edge> call() throws Exception {
        return null;
    }
}
