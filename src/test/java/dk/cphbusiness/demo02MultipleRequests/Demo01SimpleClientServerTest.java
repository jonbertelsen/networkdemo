package dk.cphbusiness.demo02MultipleRequests;

import dk.cphbusiness.demo01Simple.SimpleClient;
import dk.cphbusiness.demo01Simple.SimpleServer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/*
* Purpose of this demo is to show how to start the simple server and send a message to it from the client
* Author: Thomas Hartmann
 */
class Demo01SimpleClientServerTest {
    private static SimpleServer simpleServer = new SimpleServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");
        new Thread(()->simpleServer.start(6666)).start();

    }
    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("tearDown");
        simpleServer.stop();

    }

    @Test
    @DisplayName("Test simple server and client")
    public void testSimpleServerAndClient() {
        SimpleClient client = new SimpleClient();
        client.startConnection("localhost", 6666);
        String response = client.sendMessage("Hello SimpleServer");
        assertEquals("Hello SimpleClient, Greetings from SimpleServer", response);
        client.stopConnection();
    }
}