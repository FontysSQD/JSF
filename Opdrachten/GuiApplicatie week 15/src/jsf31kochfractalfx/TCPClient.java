package jsf31kochfractalfx;

import calculator.Edge;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * GuiApplicatie week 15 Created by Sven de Vries on 12-12-2017
 */
public class TCPClient {
    private static final int portNumber = 1234;
    DataOutputStream outToServer;
    BufferedReader inFromServer;

    public TCPClient(){
        try (Socket clientSocket = new Socket("localhost", portNumber)){
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            inFromServer.rea;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
