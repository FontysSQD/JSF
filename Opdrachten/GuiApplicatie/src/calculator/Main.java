package calculator;

import timeutil.TimeStamp;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

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
            System.out.print("Welk level moeten de edges gegenereerd worden? \n");
            Scanner input = new Scanner(System.in);
            level = input.nextInt();
            kochFractal.setLevel(level);
            kochFractal.generateBottomEdge();
            kochFractal.generateLeftEdge();
            kochFractal.generateRightEdge();
            System.out.print("Totaal aantal edges: " + kochFractal.getNrOfEdges() + "\n");
//            main.writeToByteWithoutBuffer();
//            main.writeToByteWithBuffer();
//            main.writeToTextWithoutBuffer();
//            main.writeToTextWithBuffer();
            try {
                main.ts.init();
                main.ts.setBegin("Begin write byte without buffer");
                RandomAccessFile ras = new RandomAccessFile(String.valueOf(level) + ".dat", "rw");
                FileChannel fc = ras.getChannel();
                MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, main.edges.size() * (Double.BYTES * 4));

                for(Edge e : main.edges)
                {
                    buffer.putDouble(e.X1);
                    buffer.putDouble(e.Y1);
                    buffer.putDouble(e.X2);
                    buffer.putDouble(e.Y2);
                }
                System.out.println("Finished writing");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                main.ts.setEnd("End");
                System.out.println(main.ts.toString());
            }
        }
    }

    public void writeToByteWithoutBuffer() {
        ts.init();
        ts.setBegin("Begin write byte without buffer");
        String fileName = "C:\\test\\lv" + String.valueOf(level) + ".edgyboi";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Edge e : edges) {
                oos.writeObject(e);
            }
            System.out.println("Wegschrijven byte zonder buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }
    }

    public void writeToByteWithBuffer() {
        ts.init();
        ts.setBegin("Begin write byte with buffer");
        String fileName = "C:\\test\\blv" + String.valueOf(level) + ".edgyboi";
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for (Edge e : edges) {
                oos.writeObject(e);
            }
            System.out.println("Wegschrijven byte met buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }
    }

    public void writeToTextWithoutBuffer() {
        ts.init();
        ts.setBegin("Begin write text without buffer");
        String fileName = "C:\\test\\lv" + String.valueOf(level) + ".edgystringyboi";
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
            for (Edge e : edges) {
                writer.print(e.X1 + "," + e.Y1 + "," + e.X2 + "," + e.Y2 + ";");
            }
            System.out.println("Wegschrijven text zonder buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }
    }

    public void writeToTextWithBuffer() {
        ts.init();
        ts.setBegin("Begin write text with buffer");
        String fileName = "C:\\test\\blv" + String.valueOf(level) + ".edgystringyboi";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
            for (Edge e : edges) {
                writer.append(e.X1 + "," + e.Y1 + "," + e.X2 + "," + e.Y2 + ";");
            }
            System.out.println("Wegschrijven text met buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }
}
