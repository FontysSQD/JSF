package calculator;

import java.io.*;
import java.util.ArrayList;

/**
 * GuiApplicatie Created by Sven de Vries on 21-11-2017
 */
public class FileManager {
    public ArrayList<Edge> getFile(int nxt) {
        ArrayList<Edge> edges = new ArrayList<>();
        String fileName = "C:\\test\\lvl" + String.valueOf(nxt) + ".ser";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (ois.available() != 0) {
                Edge edge = (Edge) ois.readObject();
                edges.add(edge);
            }
            System.out.println("Ophalen geslaagd");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return edges;
    }
}
