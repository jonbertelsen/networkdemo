package dk.cphbusiness.demo04_httpparsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestDataClient
{

    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String response = "";

    public static void main(String[] args)
    {
        RequestDataClient client = new RequestDataClient();
        client.startConnection(IP, PORT);

        String postData = "key1=value1&key2=value2";
        String httpRequest = "POST /path/to/endpoint HTTP/1.1" + System.lineSeparator() +
                "Host: " + "localhost" + System.lineSeparator() +
                "User-Agent: SimpleWebClient" + System.lineSeparator() +
                "Accept: */*" + System.lineSeparator() +
                "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() +
                "Content-Length: " + postData.length() + System.lineSeparator() +
                "Connection: close" + System.lineSeparator() +
                System.lineSeparator() +
                postData;
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
