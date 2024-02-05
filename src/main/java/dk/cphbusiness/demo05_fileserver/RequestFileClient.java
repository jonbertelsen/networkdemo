package dk.cphbusiness.demo05_fileserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestFileClient
{
    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String response = "";

    public static void main(String[] args)
    {
        RequestFileClient client = new RequestFileClient();
        client.startConnection(IP, PORT);
        String httpRequest =
                "GET /pages/index.html HTTP/1.1" + System.lineSeparator() +
                        "Host: " + IP + System.lineSeparator() +
                 "Content-Type: text/html; charset=UTF-8" + System.lineSeparator() +
                "Content-Length: 87" + System.lineSeparator() +
                "Connection: close" + System.lineSeparator();
        client.sendMessage(httpRequest);
        System.out.println(client.response);
        client.stopConnection();
    }

    public void startConnection(String ip, int port)
    {
        try
        {
            System.out.println("Starting client socket talking to server on IP: " +
                    IP + " and port number: " + PORT);
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String msg)
    {
        try
        {
            out.println(msg);
            response = in.readLine();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void stopConnection()
    {
        try
        {
            System.out.println("Closing down client socket");
            in.close();
            out.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String getResponse()
    {
        return response;
    }
}
