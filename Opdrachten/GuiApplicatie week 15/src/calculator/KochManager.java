package calculator;

import TCP.ObjectStreamClient;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KochManager {
    private Thread thread;
    private JSF31KochFractalFX application;
    private ObjectStreamClient tcpClient;
    private int currentLevel;
    private TimeStamp ts = new TimeStamp();
    private FileChannel fc;
    private MappedByteBuffer buffer;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        thread = new Thread(new ObjectStreamClient(this));
    }

    public void changeLevel(int nxt) {
        this.currentLevel = nxt;
        thread.run();
    }

    public long edgeCount() {
        try {
            return fc.size() / (Double.BYTES * 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
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

            ts.setEnd("Einde tekenen");

            application.setTextDraw(ts.toString());
        }
    }

    public void drawGeneratedEdges(Edge e) {
        e.color = Color.WHITE;
        application.drawEdge(e);
    }
}
