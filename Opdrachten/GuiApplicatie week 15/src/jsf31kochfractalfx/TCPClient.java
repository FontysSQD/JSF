package jsf31kochfractalfx;

import calculator.Edge;

import java.io.*;
import java.net.Socket;

/**
 * GuiApplicatie week 15 Created by Sven de Vries on 12-12-2017
 */
public class TCPClient {
    private static final int portNumber = 1234;
    ObjectOutputStream outToServer;
    ObjectInputStream inFromServer;

    public TCPClient(){
        try (Socket clientSocket = new Socket("localhost", portNumber)){
            OutputStream outStream = clientSocket.getOutputStream();
            InputStream inStream = clientSocket.getInputStream();

            outToServer = new ObjectOutputStream(outStream);
            inFromServer = new ObjectInputStream(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLevel(int level){
        try {
            outToServer.write(level);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Edge getEdge(){
        try {
            return (Edge) inFromServer.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
