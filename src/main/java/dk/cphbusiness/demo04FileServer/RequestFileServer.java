package dk.cphbusiness.demo04FileServer;

import dk.cphbusiness.demo03RequestDataServer.RequestDataServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Purpose of this demo is to show how to get the data from the request headers etc.
 * Author: Thomas Hartmann
 */
public class RequestFileServer extends RequestDataServer {
    public static void main(String[] args) {
        RequestFileServer server = new RequestFileServer();
        server.start(8080);
    }

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept(); // blocking call
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // read the request from the client
            RequestDTO requestDTO = generateRequestObject(in);
            String requestLine = requestDTO.getRequestLine();
            String ressource = requestLine.split(" ")[1];



            // Get the file from the ressource
            String response = getFile(ressource);

            clientSocket.getOutputStream().write(response.getBytes());

            // Close the socket
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFile(String ressource) {
        ressource = reformatRessource(ressource); // remove leading / and add .html if not present

        String response = "";
        Path path = Path.of(ressource);

        try {
            // Get the URI of the resource using getResource
            URI resourceUri = RequestFileServer.class.getClassLoader().getResource(ressource).toURI();
            // Use Paths.get with the URI to create a Path
            Path resourcePath = Paths.get(resourceUri);

            // Read the content of the resource using Files.readString
            response = Files.readString(resourcePath);

//            String content = Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File not found");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
    private String reformatRessource(String ressource) {
        if (!ressource.endsWith(".html")) {
            ressource += "/index.html";
        }
        if (ressource.equals("/")) {
            ressource = "index.html";
        }
        if (ressource.startsWith("/")) {
            ressource = ressource.substring(1);
        }
        return ressource;
    }
}