package dk.cphbusiness.demo02MultipleRequests;

import dk.cphbusiness.demo03RequestDataServer.RequestDataClient;
import dk.cphbusiness.demo03RequestDataServer.RequestDataServer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Purpose of this demo is to show how to start the RequestData server check that all the data from the request is read by the server.
 * Author: Thomas Hartmann
 */
class Demo03RequestDataServerTest {
    private static RequestDataServer requestDataServer = new RequestDataServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");

    }
    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
        new Thread(()-> requestDataServer.start(6668)).start();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDownEach");
        requestDataServer.stop();
    }

    @Test
    @DisplayName("Test Meta Data Server and Client")
    public void testMetaDataServerAndClient() {
        String postData = "key1=value1&key2=value2";
        String httpRequest = "POST /path/to/endpoint?query1=firstValue&query2=secondValue HTTP/1.1\r\n" +
                "Host: " + "localhost" + "\r\n" +
                "User-Agent: SimpleWebClient\r\n" +
                "Accept: */*\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: " + postData.length() + "\r\n" +
                "\r\n" + // Important, else the server will not know when the header ends and the request body begins
                postData;
        String response = new RequestDataClient().sendRequest(httpRequest, "localhost", 6668);
        String expected = "requestLine='POST /path/to/endpoint?query1=firstValue&query2=secondValue HTTP/1.1', headers={Accept= */*, User-Agent= SimpleWebClient, Host= localhost, Content-Length= 23, Content-Type= application/x-www-form-urlencoded}, queryParams={query1=firstValue, query2=secondValue}, requestBody={key1=value1, key2=value2}";
        assertEquals(expected, response);
    }

    @Test
    @DisplayName("Test with simple request")
    public void testWithSimpleRequest() {
        String httpRequest = "GET /path/to/endpoint HTTP/1.1\r\n" +
                "Host: " + "localhost" + "\r\n" +
                "User-Agent: SimpleWebClient\r\n" +
                "Accept: */*\r\n";
        String response = new RequestDataClient().sendRequest(httpRequest, "localhost", 6668);
        String expected = "requestLine='GET /path/to/endpoint HTTP/1.1', headers={Accept= */*, User-Agent= SimpleWebClient, Host= localhost}, queryParams={}, requestBody={}";
        assertEquals(expected, response);
    }
}