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

    public void drawGeneratedEdges(Edge e) {
        e.color = Color.RED;
        application.drawEdge(e);
    }
}
