package calculator;

import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Main implements Observer{
    private static int level;
    ArrayList<Edge> edges = new ArrayList<>();


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
            main.writeToFile();
        }
    }

    public void writeToFile(){
        String fileName = "/home/dane/Desktop/lv" + String.valueOf(level) + ".edgyboi";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for(Edge e : edges){
                oos.writeObject(e);
            }
            System.out.println("Wegschrijven geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);
    }
}
