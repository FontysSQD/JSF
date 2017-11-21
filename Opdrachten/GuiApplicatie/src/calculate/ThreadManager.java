package calculate;

import javafx.concurrent.Task;
import jsf31kochfractalfx.JSF31KochFractalFX;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by quintaartsen on 26-09-17.
 */
public abstract class ThreadManager extends Task<ArrayList<Edge>> implements  Observer{

    public KochFractal kochFractal;
    public ArrayList<Edge> edge = new ArrayList<>();
    private JSF31KochFractalFX application;

    public ThreadManager(int nxt, JSF31KochFractalFX application){
        kochFractal = new KochFractal();
        kochFractal.setLevel(nxt);
        kochFractal.addObserver(this::update);
        this.application = application;
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
        if(edge.size() > 3) {
            application.requestGeneratedDrawEdge(((Edge) arg).clone());
        }
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
