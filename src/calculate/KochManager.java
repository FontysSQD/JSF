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
    private ArrayList<Edge> calculatingEdges;
    private int count = 0;
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
    }

    public void changeLevel(int nxt) {
        calculatingEdges = new ArrayList<>();
        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        Thread t1 = new Thread(new ThreadManager(nxt) {
            @Override
            public void run() {
                generateLeftEdge();
                checkThread(this.edge);
            }
        });
        t1.start();

        Thread t2 = new Thread(new ThreadManager(nxt) {
            @Override
            public void run() {
                generateBottomEdge();
                checkThread(this.edge);
            }
        });
        t2.start();

        Thread t3 = new Thread(new ThreadManager(nxt) {
            @Override
            public void run() {
                generateRightEdge();
                checkThread(this.edge);
            }
        });
        t3.start();
        ts.setEnd("Einde generate");

        application.setTextCalc(ts.toString());
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
    }

    private void checkThread(ArrayList<Edge> edge) {
        synchronized (this) {
            count++;
            calculatingEdges.addAll(edge);
            if(count >= 3) {
                edges = calculatingEdges;
                count = 0;
                application.requestDrawEdges();
            }
        }
    }
}
