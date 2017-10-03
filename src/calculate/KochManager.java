package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KochManager {
    private JSF31KochFractalFX application;
    private ArrayList<Edge> edges = new ArrayList<>();
    private ExecutorService pool = Executors.newFixedThreadPool(4);

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
    }

    public void changeLevel(int nxt) {
        edges.clear();
        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        Future<ArrayList<Edge>> leftEdges = pool.submit(new ThreadManager(nxt) {
            @Override
            public ArrayList<Edge> call() {
                generateLeftEdge();
                return this.edge;
            }
        });

        Future<ArrayList<Edge>> bottomEdges = pool.submit(new ThreadManager(nxt) {
            @Override
            public ArrayList<Edge> call() {
                generateBottomEdge();
                return this.edge;
            }
        });

        Future<ArrayList<Edge>> rightEdges = pool.submit(new ThreadManager(nxt) {
            @Override
            public ArrayList<Edge> call() {
                generateRightEdge();
                return this.edge;
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    edges.addAll(leftEdges.get());
                    edges.addAll(bottomEdges.get());
                    edges.addAll(rightEdges.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                application.requestDrawEdges();
            }
        });

        ts.setEnd("Einde generate");

        application.setTextCalc(ts.toString());
    }

    public void drawEdges() {

        application.clearKochPanel();

        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        for (Edge e : edges) {
            application.drawEdge(e);
        }
        ts.setEnd("Einde tekenen");

        application.setTextDraw(ts.toString());
    }
}
