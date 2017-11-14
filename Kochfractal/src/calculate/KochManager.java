package calculate;

import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KochManager {
    private JSF31KochFractalFX application;
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Edge> calculatingEdges;
    private ThreadManager left;
    private ThreadManager bottom;
    private ThreadManager right;
    private ExecutorService pool = Executors.newFixedThreadPool(4);

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
    }

    public void changeLevel(int nxt) {
        calculatingEdges = new ArrayList<>();
        if (left != null) {
            application.progressBarLeft.progressProperty().unbind();
            application.progressLeftEdges.textProperty().unbind();
            left.cancelTask();
        }
        if (bottom != null) {
            application.progressBarBottom.progressProperty().unbind();
            application.progressBottomEdges.textProperty().unbind();
            bottom.cancelTask();
        }
        if (right != null) {
            application.progressBarRight.progressProperty().unbind();
            application.progressRightEdges.textProperty().unbind();
            right.cancelTask();
        }
        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        left = new ThreadManager(nxt, this.application) {
            @Override
            public ArrayList<Edge> call() {
                generateLeftEdge();
                return this.edge;
            }
        };
        pool.submit(left);
        application.progressBarLeft.progressProperty().bind(left.progressProperty());
        application.progressLeftEdges.textProperty().bind(left.messageProperty());


        bottom = new ThreadManager(nxt, this.application) {
            @Override
            public ArrayList<Edge> call() {
                generateBottomEdge();
                return this.edge;
            }
        };
        pool.submit(bottom);
        application.progressBarBottom.progressProperty().bind(bottom.progressProperty());
        application.progressBottomEdges.textProperty().bind(bottom.messageProperty());

        right = new ThreadManager(nxt, this.application) {
            @Override
            public ArrayList<Edge> call() {
                generateRightEdge();
                return this.edge;
            }
        };
        pool.submit(right);
        application.progressBarRight.progressProperty().bind(right.progressProperty());
        application.progressRightEdges.textProperty().bind(right.messageProperty());


        pool.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    calculatingEdges.addAll(left.get());
                    calculatingEdges.addAll(bottom.get());
                    calculatingEdges.addAll(right.get());
                    edges = calculatingEdges;
                    application.requestDrawEdges();
                } catch (InterruptedException e) {
                    System.out.println("Thread stopped");
                } catch (ExecutionException e) {
                    System.out.println("Thread stopped");
                } catch (CancellationException e) {
                    System.out.println("Thread stopped");
                }
            }
        });

        ts.setEnd("Einde generate");

        int nrOfEdges = right.getNrOfEdges() + bottom.getNrOfEdges() + left.getNrOfEdges();
        application.setTextNrEdges(String.valueOf(nrOfEdges));
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

    public void drawGeneratedEdges(Edge e) {
        e.color = Color.WHITE;
        application.drawEdge(e);
    }
}
