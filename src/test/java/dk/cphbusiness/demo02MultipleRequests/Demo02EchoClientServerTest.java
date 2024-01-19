package dk.cphbusiness.demo02MultipleRequests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Purpose of this demo is to show how to start the Echo server and send a message to it from the client (and how to close the connection)
 * Author: Thomas Hartmann
 */
class Demo02EchoClientServerTest {
    private static EchoServer echoServer = new EchoServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");

    }
    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
        new Thread(()->echoServer.start(6667)).start();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDownEach");
        echoServer.stop();
    }

    @Test
    @DisplayName("Test Echo Server and Client")
    public void testEchoServerAndClient() {
        EchoClient client = new EchoClient();
        client.startConnection("localhost", 6667);
        String response = client.sendMessage("This is a message from the client");
        assertEquals("This is a message from the client", response);
        client.stopConnection();
    }

    @Test
    @DisplayName("Test Echo Server stop request")
    public void testStopRequest() {
        EchoClient client = new EchoClient();
        client.startConnection("localhost", 6667);
        String response = client.sendMessage("!");
        assertEquals("good bye", response);
        client.stopConnection();
    }
}