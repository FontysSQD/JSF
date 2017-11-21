package calculator;

import java.io.*;
import java.util.ArrayList;

/**
 * GuiApplicatie Created by Sven de Vries on 21-11-2017
 */
public class FileManager {

    private ArrayList<Edge> edges ;
    public ArrayList<Edge> getFileNoBuffer(int nxt) {
        edges = new ArrayList<>();
        String fileName = "/home/dane/Desktop/lv" + String.valueOf(nxt) + ".edgyboi";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while(true) {
                Edge e = (Edge) ois.readObject();
                if (e != null) {
                    edges.add(e);
                } else {
                    break;
                }
            }
            System.out.println("Ophalen geslaagd");
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return edges;
    }

    public ArrayList<Edge> getFileWithBuffer(int nxt) {
        edges = new ArrayList<>();
        String fileName = "/home/dane/Desktop/lv" + String.valueOf(nxt) + ".edgyboi";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while(true) {
                Edge e = (Edge) ois.readObject();
                if (e != null) {
                    edges.add(e);
                } else {
                    break;
                }
            }
            System.out.println("Ophalen geslaagd");
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return edges;
    }
}
