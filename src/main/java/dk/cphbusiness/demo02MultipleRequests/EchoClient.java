package dk.cphbusiness.demo02MultipleRequests;

import dk.cphbusiness.demo01Simple.SimpleClient;

public class EchoClient extends SimpleClient {

        public static void main(String[] args) {
            EchoClient client = new EchoClient();
            client.startConnection("localhost", 8080);
            String response = client.sendMessage("Hello SimpleServer");
            System.out.println("response: " + response);
            client.stopConnection();
        }
}
