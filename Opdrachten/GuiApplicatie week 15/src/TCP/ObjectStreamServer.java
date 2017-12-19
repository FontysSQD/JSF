package TCP;

import calculator.Edge;
import calculator.KochFractal;
import calculator.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ObjectStreamServer implements Runnable {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Main main;

    public ObjectStreamServer(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // establish server socket
                ServerSocket s = new ServerSocket(8189);

                // wait for client connection
                System.out.println("Waiting for client");
                Socket incoming = s.accept();
                System.out.println("Connected");
                try {
                    OutputStream outStream = incoming.getOutputStream();
                    InputStream inStream = incoming.getInputStream();

                    in = new ObjectInputStream(inStream);
                    out = new ObjectOutputStream(outStream);

                    // echo client Object input
                    Object inObject = in.readObject();
                    while (inObject != null) {
                        if (inObject instanceof Integer) {
                            int level = (int) inObject;
                            main.generateEdges(level);
                            out.writeObject("Done");
                            inObject = in.readObject();
                        } else if (inObject instanceof String) {
                            if (inObject.equals("Done")) {
                                inObject = null;
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    incoming.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEdge(Edge edge) {
        try {
            out.writeObject(edge);
            out.flush();
            System.out.println("Edge send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
