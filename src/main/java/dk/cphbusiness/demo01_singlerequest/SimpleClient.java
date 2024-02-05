package dk.cphbusiness.demo01_singlerequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient
{

    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String response = "";

    public static void main(String[] args)
    {
        SimpleClient client = new SimpleClient();
        client.startConnection(IP, PORT);
        client.sendMessage("hello server");
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
