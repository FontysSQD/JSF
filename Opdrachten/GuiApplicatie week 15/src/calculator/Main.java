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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main implements Observer {
    private KochFractal kochFractal;
    private ObjectStreamServer objectStreamServer;
    private ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        Main main = new Main();
        main.kochFractal = new KochFractal(main);
        main.startTCPServer();
    }

    private void startTCPServer(){
        objectStreamServer = new ObjectStreamServer(this);
        pool.submit(objectStreamServer);
    }

    public void generateEdges(int level){
        kochFractal.setLevel(level);
        kochFractal.generateBottomEdge();
        kochFractal.generateRightEdge();
        kochFractal.generateLeftEdge();
    }


    @Override
    public void update(Observable o, Object arg) {
        objectStreamServer.sendEdge((Edge)arg);
    }
}
