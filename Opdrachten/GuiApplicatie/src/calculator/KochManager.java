package calculator;

import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class KochManager {
    private JSF31KochFractalFX application;
    private FileManager fileManager;
    private ArrayList<Edge> edges = new ArrayList<>();
    TimeStamp ts = new TimeStamp();

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
//        fileManager = new FileManager();
    }

    public void changeLevel(int nxt) {
//        edges = fileManager.getStringNoBuffer(nxt);
//        edges = fileManager.getObjectNoBuffer(nxt);
//        edges = fileManager.getStringWithBuffer(nxt);
//        edges = fileManager.getObjectWithBuffer(nxt);

        edges.clear();
        try {
            ts.init();
            ts.setBegin("Begin read mappedByteBuffer without buffer");
            RandomAccessFile ras = new RandomAccessFile(String.valueOf(nxt) + ".dat", "rw");
            FileChannel fc = ras.getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            byte[] bytes = new byte[buffer.limit()];

            for (int i = 0; i < buffer.limit(); i++) {
                bytes[i] = buffer.get(i);
            }

            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(in);
            edges.addAll((ArrayList<Edge>) is.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }

        drawEdges();
    }

    public void drawEdges() {
        if (!edges.isEmpty()) {
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
