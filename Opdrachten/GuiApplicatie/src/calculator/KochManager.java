package calculator;

import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;

public class KochManager {
    private JSF31KochFractalFX application;
    private FileManager fileManager;
    private ArrayList<Edge> edges = new ArrayList<>();

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        fileManager = new FileManager();
    }

    public void changeLevel(int nxt) {
        edges = fileManager.getStringWithBuffer(nxt);
        drawEdges();
    }

    public void drawEdges() {
        if(!edges.isEmpty()){
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

    public void drawGeneratedEdges(Edge e) {
        e.color = Color.WHITE;
        application.drawEdge(e);
    }
}
