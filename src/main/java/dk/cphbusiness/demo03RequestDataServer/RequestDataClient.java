package dk.cphbusiness.demo03RequestDataServer;

import dk.cphbusiness.demo01Simple.SimpleClient;

public class RequestDataClient extends SimpleClient {

    public static void main(String[] args) {
        String postData = "key1=value1&key2=value2";
        String httpRequest = "POST /path/to/endpoint HTTP/1.1\r\n" +
                "Host: " + "localhost" + "\r\n" +
                "User-Agent: SimpleWebClient\r\n" +
                "Accept: */*\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: " + postData.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                postData;
        new RequestDataClient().sendRequest(httpRequest, "localhost", 8080);
    }
    public String sendRequest(String httpRequest, String host, int port){
        RequestDataClient client = new RequestDataClient();
        client.startConnection(host, port);
        String response = client.sendMessage(httpRequest);
        System.out.println("response: " + response);
        client.stopConnection();
        return response;
    }
}
