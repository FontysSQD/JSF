package TCP;/*
 * run in cmd in bin directory: java clientserver/TCP.ObjectStreamClient
 */

import calculator.Edge;
import calculator.KochManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ObjectStreamClient implements Runnable {

    private ObjectOutputStream out;
    private KochManager kochManager;

    public ObjectStreamClient(KochManager kochManager) {
        this.kochManager = kochManager;
    }

    @Override
    public void run() {
        sendLevel(kochManager.getCurrentLevel());
    }

    public void sendLevel(int level) {
        try {
            Socket s = new Socket("localhost", 8189);
            try {
                System.out.println("Connected to server");
                OutputStream outStream = s.getOutputStream();
                InputStream inStream = s.getInputStream();

                // Let op: volgorde is van belang!
                out = new ObjectOutputStream(outStream);
                ObjectInputStream in = new ObjectInputStream(inStream);

                try {
                    out.writeObject(level);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Object result = in.readObject();
                while (result != null) {
                    if (result instanceof Edge) {
                        kochManager.drawGeneratedEdges((Edge) result);
                        result = in.readObject();
                    } else if (result instanceof String){
                        if (result.equals("Done")){
                            out.writeObject("Done");
                            result = null;
                        }
                    }
                }
            } finally {
                s.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
