package calculate;

import javafx.concurrent.Task;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by quintaartsen on 26-09-17.
 */
public abstract class ThreadManager extends Task<ArrayList<Edge>> implements  Observer{

    public KochFractal kochFractal;
    public ArrayList<Edge> edge = new ArrayList<>();

    public ThreadManager(int nxt){
        kochFractal = new KochFractal();
        kochFractal.setLevel(nxt);
        kochFractal.addObserver(this::update);
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

    public void generateRightEdge() {
        synchronized (this) {
            kochFractal.generateRightEdge();
        }
    }

    public int getNrOfEdges(){
        return kochFractal.getNrOfEdges();
    }

    public void update(Observable o, Object arg) {
        edge.add((Edge)arg);
        updateProgress(edge.size(), getNrOfEdges());
        updateMessage("Nr edges: " + edge.size());
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("Sleep stopped");
        }
    }
    public void cancelTask(){
        kochFractal.cancel();
        cancel();
    }
}
