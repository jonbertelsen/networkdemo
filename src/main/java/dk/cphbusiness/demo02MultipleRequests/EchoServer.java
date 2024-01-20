package dk.cphbusiness.demo02MultipleRequests;

import dk.cphbusiness.demo01Simple.SimpleServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/*
* Purpose of this demo is to show how to read the client request and send it back from the server
* This is a TCP server example (not HTTP, wchich means it is not a web server and cannot work with a browser)
* Author: Thomas Hartmann
**/
public class EchoServer extends SimpleServer {
    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        server.start(8080);
    }
    /*
    * Purpose of this demo is to show how to accept multiple requests from the client while keeping the connection open
    */
    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept(); // blocking call
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            // Keep connection open until client sends a "!"
            while ((inputLine = in.readLine()) != null) {
                if ("!".equals(inputLine)) {
                    out.println("good bye");
                    break;
                }
                out.println(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
