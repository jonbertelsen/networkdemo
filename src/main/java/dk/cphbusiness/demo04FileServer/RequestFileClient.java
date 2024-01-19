package dk.cphbusiness.demo04FileServer;

import dk.cphbusiness.demo01Simple.SimpleClient;

public class RequestFileClient extends SimpleClient {

    public static void main(String[] args) {
        String httpRequest = "GET /ressources/HelloWorld.html HTTP/1.1\r\n" +
                "Host: " + "localhost" + "\r\n";
        new RequestFileClient().sendRequest(httpRequest, "localhost", 8080);
    }
    public String sendRequest(String httpRequest, String host, int port){
        RequestFileClient client = new RequestFileClient();
        client.startConnection(host, port);
        String response = client.sendMessage(httpRequest);
        client.stopConnection();
        return response;
    }
}
