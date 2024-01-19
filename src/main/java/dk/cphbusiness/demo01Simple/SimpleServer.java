package dk.cphbusiness.demo01Simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    protected ServerSocket serverSocket; // protected so that subclasses can access it
    protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port); // start the server on same computer as client, listening on "port"
            clientSocket = serverSocket.accept(); // blocking call
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
            System.out.println(greeting);
            out.println("Hello SimpleClient, Greetings from SimpleServer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
        server.start(6666);
    }
}
