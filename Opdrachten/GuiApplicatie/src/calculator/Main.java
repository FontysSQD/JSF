package calculator;

import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;
import timeutil.TimeStamp;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Main implements Observer{
    private static int level;
    ArrayList<Edge> edges = new ArrayList<>();
    TimeStamp ts = new TimeStamp();

    public static void main(String[] args) {
        Main main = new Main();
        while(true){
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
            main.writeToByteWithoutBuffer();
            main.writeToByteWithBuffer();
            main.writeToTextWithoutBuffer();
            main.writeToTextWithBuffer();
        }
    }

    public void writeToByteWithoutBuffer(){
        ts.init();
        ts.setBegin("Begin byte without buffer");
        String fileName = "/home/dane/Desktop/lv" + String.valueOf(level) + ".edgyboi";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for(Edge e : edges){
                oos.writeObject(e);
            }
            System.out.println("Wegschrijven byte zonder buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ts.setEnd("End byte without buffer");
        }
    }

    public void writeToByteWithBuffer(){
        ts.init();
        ts.setBegin("Begin byte without buffer");
        String fileName = "/home/dane/Desktop/blv" + String.valueOf(level) + ".edgyboi";
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for(Edge e : edges){
                oos.writeObject(e);
            }
            System.out.println("Wegschrijven byte met buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ts.setEnd("End byte with buffer");
        }
    }

    public void writeToTextWithoutBuffer(){
        ts.init();
        ts.setBegin("Begin text without buffer");
        String fileName = "/home/dane/Desktop/lv" + String.valueOf(level) + ".edgystringboi";
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
            for(Edge e : edges){
                writer.print(e.X1 + "," + e.Y1 + "," + e.X2 + "," + e.Y2 + ";");
            }
            System.out.println("Wegschrijven text zonder buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ts.setEnd("End text without buffer");
        }
    }

    public void writeToTextWithBuffer(){
        ts.init();
        ts.setBegin("Begin text without buffer");
        String fileName = "/home/dane/Desktop/blv" + String.valueOf(level) + ".edgystringboi";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
            for(Edge e : edges){
                writer.append(e.X1 + "," + e.Y1 + "," + e.X2 + "," + e.Y2 + ";");
            }
            System.out.println("Wegschrijven text met buffer geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ts.setEnd("End text with buffer");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);
    }
}
