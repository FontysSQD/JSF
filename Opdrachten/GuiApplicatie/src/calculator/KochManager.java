package calculator;

import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

public class KochManager {
    private JSF31KochFractalFX application;
    private FileManager fileManager;
//    private ArrayList<Edge> edges = new ArrayList<>();
    TimeStamp ts = new TimeStamp();
    FileChannel fc;
    MappedByteBuffer buffer;

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
//        fileManager = new FileManager();
    }

    public void changeLevel(int nxt) {

    }

    public long edgeCount() {
        try {
            return fc.size() / (Double.BYTES * 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void drawEdgeFromMap(List<Edge> edges) throws IOException {
       for(Edge e : edges) {
            application.drawEdge(e);
        }
    }


    public void drawEdges() {
        if (edgeCount() != 0) {
            application.clearKochPanel();

            TimeStamp ts = new TimeStamp();
            ts.setBegin();

            long count = edgeCount();
            buffer.position(0);
            for (long i = 0; i < count; i++) {
                Edge e = new Edge();
                e.X1 = buffer.getDouble();
                e.Y1 = buffer.getDouble();
                e.X2 = buffer.getDouble();
                e.Y2 = buffer.getDouble();
                e.color = Color.RED;
                application.drawEdge(e);
            }

//              for (Edge e : edges) {
//                  application.drawEdge(e);
//              }
            ts.setEnd("Einde tekenen");

            application.setTextDraw(ts.toString());
        }
    }

    public void drawGeneratedEdges(Edge e) {
        e.color = Color.WHITE;
        application.drawEdge(e);
    }
}
