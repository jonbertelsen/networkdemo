package dk.cphbusiness.demo03_httprequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The purpose of this demo is to show a simple http request and response
 * The request should be sent from a browser
 * Author: Jon Bertelsen
 */

public class HttpServer
{
    private static final int PORT = 9090;

    private Socket clientSocket;
    private PrintWriter out;

    public static void main(String[] args)
    {
        HttpServer server = new HttpServer();
        System.out.println("Now get this webpage from a browser, tiger!");
        System.out.println("On http://localhost:" + PORT);
        server.startConnection(PORT);
    }

    public void startConnection(int port)
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            clientSocket = serverSocket.accept(); // wait for client request
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String responseHeader = "HTTP/1.1 200 OK" + System.lineSeparator() +
                    "Date: Mon, 23 May 2022 22:38:34 GMT" + System.lineSeparator() +
                    "Server: Apache/2.4.1 (Unix)\n" +
                    "Content-Type: text/html; charset=UTF-8" + System.lineSeparator() +
                    "Content-Length: 87" + System.lineSeparator() +
                    "Connection: close" + System.lineSeparator();

            String responseBody = "<html><head><title>hello world</title></head><body><h1>Hello World</h1></body></html>";

            out.println(responseHeader);
            out.println(System.lineSeparator() + System.lineSeparator()); // separate header and payload section
            out.println(responseBody);
        }
        catch (IOException e)
        {
            System.out.println("An error has occured during network I/O");
            throw new RuntimeException(e);
        }
        finally
        {
            stopConnection();
        }

    }

    public void stopConnection()
    {
        try
        {
            System.out.println("Closing down client socket");
            out.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
