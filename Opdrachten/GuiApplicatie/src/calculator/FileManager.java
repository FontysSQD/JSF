package calculator;

import javafx.scene.paint.Color;
import timeutil.TimeStamp;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * GuiApplicatie Created by Sven de Vries on 21-11-2017
 */
public class FileManager {
    private ArrayList<Edge> edges;
    TimeStamp ts = new TimeStamp();

    public ArrayList<Edge> getObjectNoBuffer(int nxt) {
        ts.init();
        ts.setBegin("Begin read byte without buffer");
        edges = new ArrayList<>();
        String fileName = "C:\\test\\lv" + String.valueOf(nxt) + ".edgyboi";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
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
        finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }
        return edges;
    }

    public ArrayList<Edge> getObjectWithBuffer(int nxt) {
        ts.init();
        ts.setBegin("Begin read byte with buffer");
        edges = new ArrayList<>();
        String fileName = "C:\\test\\blv" + String.valueOf(nxt) + ".edgyboi";
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            while (true) {
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
        finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }

        return edges;
    }

    public ArrayList<Edge> getStringWithBuffer(int nxt) {
        ts.init();
        ts.setBegin("Begin read string with buffer");
        edges = new ArrayList<>();
        String fileName = "C:\\test\\blv" + String.valueOf(nxt) + ".edgystringyboi";
        try (InputStreamReader isr = new InputStreamReader(new BufferedInputStream(new FileInputStream(fileName)))) {
            parseStringFiles(String.valueOf(isr.read()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }
        return edges;
    }

    public ArrayList<Edge> getStringNoBuffer(int nxt) {
        ts.init();
        ts.setBegin("Begin read string without buffer");
        edges = new ArrayList<>();
        String fileName = "C:\\test\\lv" + String.valueOf(nxt) + ".edgystringyboi";
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName))) {
           parseStringFiles(String.valueOf(isr.read()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ts.setEnd("End");
            System.out.println(ts.toString());
        }
        return edges;
    }

    private void parseStringFiles(String zooj) {
        String[] objects =  zooj.split(";");
        for(String object : objects) {
            String[] fields = object.split(",");
            edges.add(new Edge(Double.parseDouble(fields[0]), Double.parseDouble(fields[0]), Double.parseDouble(fields[0]), Double.parseDouble(fields[0]), Color.CHOCOLATE));
        }
    }
}
