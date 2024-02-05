package dk.cphbusiness.demo02_multiplerequests;

import dk.cphbusiness.demo04_httpparsing.RequestDataClient;
import dk.cphbusiness.demo04_httpparsing.RequestDataServer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Purpose of this demo is to show how to start the RequestData server check that all the data from the request is read by the server.
 * Author: Thomas Hartmann
 */
class Demo04RequestDataServerTest
{

    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private static RequestDataServer requestDataServer = new RequestDataServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");

    }
    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
        new Thread(()-> requestDataServer.startConnection(PORT)).start();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDownEach");
        requestDataServer.stopConnection();
    }

    @Test
    @DisplayName("Test Meta Data Server and Client")
    public void testMetaDataServerAndClient() {
        String postData = "key1=value1&key2=value2";
        String httpRequest =
                "POST /path/to/endpoint?query1=firstValue&query2=secondValue HTTP/1.1" + System.lineSeparator() +
                "Host: " + "localhost" + System.lineSeparator() +
                "User-Agent: SimpleWebClient" + System.lineSeparator() +
                "Accept: */*" + System.lineSeparator() +
                "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() +
                "Content-Length: " + postData.length() + System.lineSeparator() +
                System.lineSeparator() + // Important, else the server will not know when the header ends and the request body begins
                postData;
        RequestDataClient client = new RequestDataClient();
        client.startConnection(IP, PORT);
        client.sendMessage(httpRequest);
        String expected = "requestLine='POST /path/to/endpoint?query1=firstValue&query2=secondValue HTTP/1.1', headers={Accept= */*, User-Agent= SimpleWebClient, Host= localhost, Content-Length= 23, Content-Type= application/x-www-form-urlencoded}, queryParams={query1=firstValue, query2=secondValue}, requestBody={key1=value1, key2=value2}";
        assertEquals(expected, client.getResponse());
    }

    @Test
    @DisplayName("Test with simple request")
    public void testWithSimpleRequest() {
        String httpRequest =
                "GET /path/to/endpoint HTTP/1.1" + System.lineSeparator() +
                "Host: " + "localhost" + System.lineSeparator() +
                "User-Agent: SimpleWebClient" + System.lineSeparator() +
                "Accept: */*" + System.lineSeparator();
        RequestDataClient client = new RequestDataClient();
        client.startConnection(IP, PORT);
        client.sendMessage(httpRequest);
        String expected = "requestLine='GET /path/to/endpoint HTTP/1.1', headers={Accept= */*, User-Agent= SimpleWebClient, Host= localhost}, queryParams={}, requestBody={}";
        assertEquals(expected, client.getResponse());
    }
}