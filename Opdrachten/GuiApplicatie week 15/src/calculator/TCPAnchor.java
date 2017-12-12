package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TCPAnchor extends Thread
{

    private static final String TAG = "TCPAnchor: ";
    private static Socket clientSocket = null;
    private String clientIP;
    private BufferedReader in;
    private PrintWriter out;
    private ProtocolHandler handler;

    TCPAnchor(Socket clientSoc) {
        clientSocket = clientSoc;
        handler = new ProtocolHandler();
        String[] parts = clientSoc.getRemoteSocketAddress().toString().split(":");
        clientIP = String.valueOf(parts[0]);
        start();
    }

    @Override
    public void run() {
        System.out.println(TAG + "New TCPAnchor thread started");
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("Disconnect")) {
                    break;
                }
                System.out.println(TAG + " " +clientIP + " send a new message: " + inputLine);
                handler.messageHandler(inputLine);
            }
            System.out.println(TAG + " " +clientIP +" disconnected");
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(TAG + "Problem with Communication Server");
        }
    }
}
