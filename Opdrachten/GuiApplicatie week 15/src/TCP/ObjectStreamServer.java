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
        try {
            // establish server socket
            ServerSocket s = new ServerSocket(8189);

            // wait for client connection
            Socket incoming = s.accept();
            System.out.println("Connected");
            try {
                OutputStream outStream = incoming.getOutputStream();
                InputStream inStream = incoming.getInputStream();

                in = new ObjectInputStream(inStream);
                out = new ObjectOutputStream(outStream);

                // echo client Object input
                boolean done = false;
                Object inObject = null;
                while (!done) {
                    try {
                        inObject = in.readObject();
                        if (inObject instanceof Integer) {
                            int level = (int) inObject;
                            main.generateEdges(level);
                        }
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        System.out.println("Object type not known");
                    }
                    //
                }
            } finally {
                incoming.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEdge(Edge edge) {
        try {
            out.writeObject(edge);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
