package dk.cphbusiness.demo02_multiplerequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Purpose of this demo is to show how to read the client request and send it back from the server
 * This is a TCP server example (not HTTP, which means it is not a web server and cannot work with a browser)
 * Author: Thomas Hartmann and Jon Bertelsen
 **/
public class EchoServer
{
    private static final int PORT = 9090;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args)
    {
        EchoServer server = new EchoServer();
        server.startConnection(PORT);
    }

    /*
     * Purpose of this demo is to show how to accept multiple requests from the client
     * while keeping the connection open
     */
    public void startConnection(int port)
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            clientSocket = serverSocket.accept(); // blocking call
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            do
            {
                inputLine = in.readLine();
                if ("bye".equals(inputLine))
                {
                    out.println("Good bye ... closing down");
                } else if (inputLine != null)
                {
                    out.println(inputLine);
                }
            } while (inputLine != null && !inputLine.equals("bye"));
        }
        catch (IOException e)
        {
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
            System.out.println("Closing Echo server socket down ....");
            in.close();
            out.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}
