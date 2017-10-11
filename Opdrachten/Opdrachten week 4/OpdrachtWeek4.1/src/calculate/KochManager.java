package calculate;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class KochManager implements Observer {
    private KochFractal kochFractal = new KochFractal();
    private JSF31KochFractalFX application;
    private ArrayList<Edge> edges = new ArrayList<>();

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        kochFractal.addObserver(this::update);
    }

    public void changeLevel(int nxt) {
        kochFractal.setLevel(nxt);
        drawEdges();
    }

    public void drawEdges() {
        application.clearKochPanel();
        kochFractal.generateLeftEdge();
        kochFractal.generateBottomEdge();
        kochFractal.generateRightEdge();

        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        for(Edge e : edges){
            application.drawEdge(e);
        }
        ts.setEnd("Einde tekenen");
        application.setTextCalc(ts.toString());
        application.setTextNrEdges(Integer.toString(kochFractal.getNrOfEdges()));
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);
    }
}
