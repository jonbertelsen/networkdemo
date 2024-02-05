package dk.cphbusiness.demo02_multiplerequests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Purpose of this demo is to show how to start the Echo server and send a message to it from the client (and how to close the connection)
 * Author: Thomas Hartmann
 */
class Demo02EchoClientServerTest {

    private static final int PORT = 9090;
    private static final String IP = "127.0.0.1";

    private static EchoServer echoServer = new EchoServer();

    @BeforeAll
    public static void setup() {
        System.out.println("setup");
    }

    @BeforeEach
    public void setupEach() {
        System.out.println("setupEach");
        new Thread(()->echoServer.startConnection(PORT)).start();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tearDownEach");
        echoServer.stopConnection();
    }

    @Test
    @DisplayName("Test Echo Server and Client")
    public void testEchoServerAndClient() {
        EchoClient client = new EchoClient();
        client.startConnection(IP, PORT);
        client.sendMessage("This is a message from the client");
        assertEquals("This is a message from the client", client.getResponse());
        client.stopConnection();
    }

    @Test
    @DisplayName("Test Echo Server stop request")
    public void testStopRequest() {
        EchoClient client = new EchoClient();
        client.startConnection(IP, PORT);
        client.sendMessage("bye");
        assertEquals("Good bye ... closing down", client.getResponse());
        client.stopConnection();
    }
}