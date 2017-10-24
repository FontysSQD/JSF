package runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSF31_w03_OS_Runtime {

    public static void main(String[] args) throws IOException {

        //onderdeel 11
        CommandParser cp = new CommandParser(args);
        cp.run();
    }
        /*
        System.out.println("Beschikbare procesoren: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Totale hoeveelheid beschikbaar geheugen: " + Runtime.getRuntime().totalMemory() + " byte");
        System.out.println("Maximale hoeveelheid geheugen: " + Runtime.getRuntime().maxMemory() + " byte");
        System.out.println("Vrije hoeveelheid geheugen: " + Runtime.getRuntime().freeMemory() + " byte");
        System.out.println("Hoeveelheid geheugen dat momenteel wordt gebruikt: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+ " byte");
        String s;
        for(int i=0; i<100000; i++) {
            s = "Hello" + i;
        }
        System.out.println("Hoeveelheid geheugen dat momenteel wordt gebruikt: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+ " byte");
        System.gc();
        System.out.println("Hoeveelheid geheugen dat momenteel wordt gebruikt: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())+ " byte");


        //Onderdeel 5 gnome calculator
        TimeStamp ts = new TimeStamp();
        ts.setBegin();

        ts.setEnd("BEGIN ONDERDEEL 5");
        ProcessBuilder pb = new ProcessBuilder("gnome-calculator");
        Process p = pb.start();

        try{
            Thread.sleep(3000);
        }
        catch (InterruptedException ex){
            System.out.println("Error: " + ex.getMessage());
        }

        p.destroy();
        ts.setEnd("Onderdeel 5");



        //onderdeel 6
        pb = new ProcessBuilder("ls");
        Process p2 = pb.start();

        InputStream is = p2.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ( (line = br.readLine()) != null ) {
            System.out.println(line);
        }
        br.close();

        ts.setEnd("Onderdeel 6");
        System.out.println(ts.toString());
        */
}
