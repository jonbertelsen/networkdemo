package dk.cphbusiness.demo04FileServer;

import dk.cphbusiness.demo01Simple.SimpleClient;

public class RequestFileClient extends SimpleClient {

    public String sendRequest(String httpRequest, String host, int port){
        RequestFileClient client = new RequestFileClient();
        client.startConnection(host, port);
        String response = client.sendMessage(httpRequest);
        client.stopConnection();
        return response;
    }
}
