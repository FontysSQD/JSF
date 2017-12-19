package calculator;

import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.io.*;
import java.nio.Buffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

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

    public void drawEdgeFromMap(MappedByteBuffer mb, FileChannel fileChannel) throws IOException {
        this.buffer = mb;
        this.fc = fileChannel;
        application.clearKochPanel();
        ts.init();
        ts.setBegin("Begin read mappedByteBuffer without buffer");
        buffer.position(0);
        renderEdgeFromBuffer();
    }


    public void drawEdges() {
        if (edgeCount() != 0) {
            TimeStamp ts = new TimeStamp();
            ts.setBegin();
            application.clearKochPanel();
            buffer.position(0);
            renderEdgeFromBuffer();
            ts.setEnd("Einde tekenen");
            application.setTextDraw(ts.toString());
        }
    }

    private void renderEdgeFromBuffer() {
        long count = edgeCount();
        for (long i = 0; i < count; i++) {
            Edge e = new Edge();
            e.X1 = buffer.getDouble();
            e.Y1 = buffer.getDouble();
            e.X2 = buffer.getDouble();
            e.Y2 = buffer.getDouble();
            e.color = Color.RED;
            application.drawEdge(e);
        }
    }

    public void drawGeneratedEdges(Edge e) {
        e.color = Color.WHITE;
        application.drawEdge(e);
    }
}
