package dk.cphbusiness.demo02_multiplerequests;

import dk.cphbusiness.demo01_singlerequest.SimpleClient;
import dk.cphbusiness.demo01_singlerequest.SimpleServer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/*
* Purpose of this demo is to show how to start the simple server and send a message to it from the client
* Author: Thomas Hartmann
 */
class Demo01SimpleClientServerTest {

    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private static SimpleServer simpleServer = new SimpleServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");
        new Thread(()->simpleServer.startConnection(PORT)).start();

    }
    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("tearDown");
        simpleServer.stopConnection();
    }

    @Test
    @DisplayName("Test simple server and client")
    public void testSimpleServerAndClient() {
        SimpleClient client = new SimpleClient();
        client.startConnection("localhost", PORT);
        client.sendMessage("Hello SimpleServer");
        assertEquals("Hello SimpleClient, Greetings from SimpleServer", client.getResponse() );
        client.stopConnection();
    }
}