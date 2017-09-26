package calculate;

import javafx.application.Platform;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class KochManager {
    private JSF31KochFractalFX application;
    private ArrayList<Edge> edges = new ArrayList<>();
    private int count = 0;
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
    }

    public void changeLevel(int nxt) {
        edges.clear();
        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        Thread t1 = new Thread(new ThreadManager(nxt) {
            @Override
            public void run() {
                synchronized (edges) {
                    generateLeftEdge();
                    count++;
                    if(count >= 3) {
                        application.requestDrawEdges();
                        count = 0;
                    }
                    edges.addAll(this.edge);
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new ThreadManager(nxt) {
            @Override
            public void run() {
                synchronized (edges) {
                    generateBottomEdge();
                    count++;
                    if(count >= 3) {
                        application.requestDrawEdges();
                        count = 0;
                    }
                    edges.addAll(this.edge);
                }
            }
        });
        t2.start();

        Thread t3 = new Thread(new ThreadManager(nxt) {
            @Override
            public void run() {
                synchronized (edges) {
                    generateRightEdge();
                    count++;
                    if(count >= 3) {
                        application.requestDrawEdges();
                        count = 0;
                    }
                    edges.addAll(this.edge);
                }
            }
        });
        t3.start();
        ts.setEnd("Einde generate");

       // application.setTextCalc(ts.toString());
    }

    public void drawEdges() {

        application.clearKochPanel();

        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        for(Edge e : edges){
            application.drawEdge(e);
        }
        ts.setEnd("Einde tekenen");

        application.setTextDraw(ts.toString());
        // application.setTextNrEdges(Integer.toString(kochFractal.getNrOfEdges()));
    }
}
