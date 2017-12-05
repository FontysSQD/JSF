package calculator;

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
    ArrayList<Edge> edges = new ArrayList<>();
    TimeStamp ts = new TimeStamp();

    public static void main(String[] args) {
        Main main = new Main();
        while (true) {
            main.edges.clear();
            KochFractal kochFractal = new KochFractal();
            kochFractal.addObserver(main);
            new File("edges.dat").delete();
            System.out.print("Welk level moeten de edges gegenereerd worden? \n");
            Scanner input = new Scanner(System.in);
            level = input.nextInt();
            kochFractal.setLevel(level);
            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
            kochFractal.generateLeftEdge();
            System.out.print("Totaal aantal edges: " + kochFractal.getNrOfEdges() + "\n");
//            main.writeToByteWithoutBuffer();
//            main.writeToByteWithBuffer();
//            main.writeToTextWithoutBuffer();
//            main.writeToTextWithBuffer();
            try {
                main.ts.init();
                main.ts.setBegin("Begin write byte without buffer");
                RandomAccessFile ras = new RandomAccessFile("edges.dat", "rw");
                FileChannel fc = ras.getChannel();
                MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_WRITE, 1, main.edges.size() * (Double.BYTES * 4));

                FileLock lock;


                for (Edge e : main.edges) {
                    lock = fc.lock(0, 58, false);
                    buffer.putDouble(e.X1);
                    buffer.putDouble(e.Y1);
                    buffer.putDouble(e.X2);
                    buffer.putDouble(e.Y2);
                    lock.release();
                    ras.seek(0);
                    ras.write(0);
                    sleep(10);
                }

                System.out.println("Finished writing");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                main.ts.setEnd("End");
                System.out.println(main.ts.toString());
            }
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }
}
