package dk.cphbusiness.demo02_multiplerequests;

import dk.cphbusiness.demo05_fileserver.RequestFileClient;
import dk.cphbusiness.demo05_fileserver.RequestFileServer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Purpose of this demo is to show how to get a html file from the server.
 * Author: Thomas Hartmann and Jon Bertelsen
 */
class Demo05FileServerTest
{

    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private static RequestFileServer rfs = new RequestFileServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");

    }
    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
        new Thread(()->rfs.startConnection(PORT)).start();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDownEach");
        rfs.stopConnection();
    }

    @Test
    @DisplayName("Test getting a file from the server")
    public void testGettingFileFromServer() {
        String httpRequest =
                "GET /pages/index.html HTTP/1.1" + System.lineSeparator() +
                "Host: " + "localhost";
        RequestFileClient client = new RequestFileClient();
        client.startConnection(IP, PORT);
        client.sendMessage(httpRequest);
        String expected = "<html><head><title>hello world</title></head><body><h1>Hello World</h1></body></html>";
        assertEquals(expected, client.getResponse());
    }

    @Test
    @DisplayName("Test getting a file from the server without index.html")
    public void testGettingFileFromServerNoHtml() {
        String httpRequest =
                "GET /pages HTTP/1.1" + System.lineSeparator() +
                "Host: " + "localhost";
        RequestFileClient client = new RequestFileClient();
        client.startConnection(IP, PORT);
        client.sendMessage(httpRequest);
        String expected = "<html><head><title>hello world</title></head><body><h1>Hello World</h1></body></html>";
        assertEquals(expected, client.getResponse());
    }
}