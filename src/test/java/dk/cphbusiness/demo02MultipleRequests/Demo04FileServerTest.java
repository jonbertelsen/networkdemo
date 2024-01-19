package dk.cphbusiness.demo02MultipleRequests;

import dk.cphbusiness.demo03RequestDataServer.RequestDataClient;
import dk.cphbusiness.demo03RequestDataServer.RequestDataServer;
import dk.cphbusiness.demo04FileServer.RequestFileClient;
import dk.cphbusiness.demo04FileServer.RequestFileServer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Demo04FileServerTest {
    private static RequestFileServer rfs = new RequestFileServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");

    }
    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
        new Thread(()->rfs.start(6669)).start();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDownEach");
        rfs.stop();
    }

    @Test
    @DisplayName("Test getting a file from the server")
    public void testGettingFileFromServer() {
        String httpRequest = "GET /pages/index.html HTTP/1.1\r\n" +
                "Host: " + "localhost";
        String response = new RequestFileClient().sendRequest(httpRequest, "localhost", 6669);
        String expected = "<html><head><title>hello world</title></head><body><h1>Hello World</h1></body></html>";
        assertEquals(expected, response);
    }

    @Test
    @DisplayName("Test getting a file from the server without index.html")
    public void testGettingFileFromServerNoHtml() {
        String httpRequest = "GET /pages HTTP/1.1\r\n" +
                "Host: " + "localhost";
        String response = new RequestFileClient().sendRequest(httpRequest, "localhost", 6669);
        String expected = "<html><head><title>hello world</title></head><body><h1>Hello World</h1></body></html>";
        assertEquals(expected, response);
    }
}