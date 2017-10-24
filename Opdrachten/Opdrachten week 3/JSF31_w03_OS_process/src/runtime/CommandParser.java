package runtime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandParser implements Runnable {
    private String[] commands;

    public CommandParser(String[] commands) {
        this.commands = commands;
    }

    @Override
    public void run() {
        int count = 0;
        for (String s : commands) {
            count++;
            TimeStamp ts = new TimeStamp();
            ts.setBegin();
            Thread t = new Thread(() -> {
                try {
                    ProcessBuilder pb = new ProcessBuilder(s);
                    Process p = pb.start();

                    InputStream is = p.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
            t.start();
            ts.setEnd("EINDE THREAD " + count);
            System.out.println(ts.toString());
        }
    }
}
