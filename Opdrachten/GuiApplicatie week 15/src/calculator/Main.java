package calculator;

import TCP.ObjectStreamServer;
import timeutil.TimeStamp;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import static java.lang.Thread.sleep;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main implements Observer {
    private static int level;
    private KochFractal kochFractal;
    private ObjectStreamServer objectStreamServer;
    ArrayList<Edge> edges = new ArrayList<>();
    TimeStamp ts = new TimeStamp();

    public static void main(String[] args) {
        Main main = new Main();
        main.kochFractal = new KochFractal();
        main.startTCPServer();
    }

    private void startTCPServer(){
        objectStreamServer = new ObjectStreamServer(this);
        Thread thread = new Thread(objectStreamServer);
//        thread.start();
        objectStreamServer.run();
    }

    public void generateEdges(int level){
        kochFractal.setLevel(level);
        kochFractal.generateBottomEdge();
        kochFractal.generateRightEdge();
        kochFractal.generateLeftEdge();
    }


    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
        objectStreamServer.sendEdge((Edge)arg);
    }
}
