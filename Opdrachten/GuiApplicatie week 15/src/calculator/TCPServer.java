package calculator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class TCPServer implements Runnable{

    private static final String TAG = "TCPServer: ";
    private static final int portNumber = 1234;

    public TCPServer() {
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        ArrayList<TCPAnchor> anchors = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(portNumber);
            String ip = String.valueOf(InetAddress.getLocalHost());
            String[] parts = ip.split("/");
            System.out.println(TAG + "TCPServer online!");
            System.out.println(TAG + "Connection socket created on IP: " + parts[1] + " and port: " + portNumber);
            while (true) {
                try {
                    System.out.println(TAG + "Waiting for new Connection.");
                    TCPAnchor anchor = new TCPAnchor(serverSocket.accept());
                    anchors.add(anchor);
                } catch (Exception e) {
                    System.err.println(TAG + "Accept failed.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(TAG + "Could not listen on portNumber: " + portNumber);
            System.exit(1);
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
            } catch (IOException e) {
                System.err.println(TAG + "Could not close portNumber: " + portNumber);
                System.exit(1);
            }
        }
    }
}

