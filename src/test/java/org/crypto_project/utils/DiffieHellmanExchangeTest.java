package org.crypto_project.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class DiffieHellmanExchangeTest {

    @ParameterizedTest
    @CsvSource({
            "2, 11, 4, 3, 5, 8, 0000000000000004",
            "5, 23, 6, 7, 8, 17, 000000000000000c",
            "7, 29, 10, 15, 24, 7, 0000000000000018",
            "13, 41, 8, 12, 10, 4, 0000000000000012",
            "100169, 10019, 23456, 54321, 5330, 6945, 0000000000000b6a",
            "1931, 256, 345, 764, 235, 209, 0000000000000051",
            "789, 67891, 2345, 9876, 55462, 11419, 000000000000f87f",
            "200037, 4537891, 1234567, 987654, 2708201, 300489, 0000000000438418"
    })
    void DiffieHellmanExchange(int base, int prime, int client_key, int server_key, int expectedClientPub, int expectedServerPub, String expectedGeneratedKey) throws Exception {
        final int PORT = 6667;

        // Init sockets and objets
        TCPServer server = Utilities.serverInit("AES", PORT);
        TCPClient client = Utilities.clientInit("AES", "127.0.0.1", PORT);
        var exchanger = new DiffieHellmanExchange(base, prime, client_key, server_key);

        // Run Exchange
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> serverKeyGeneration = executor.submit(() -> runServerInBackgroundAndTest(server, exchanger));
        String clientGeneratedKey = exchanger.clientExchange(client);

        // End client and get server generated key
        client.stopConnection();
        String serverGeneratedKey = serverKeyGeneration.get();

        // check public keys
        assertEquals(expectedServerPub, exchanger.getServerPub(), "Incorrect server public key");
        assertEquals(expectedClientPub, exchanger.getClientPub(), "Incorrect client public key");

        // check keys
        assertEquals(expectedGeneratedKey, serverGeneratedKey, "Incorrect generated key by server");
        assertEquals(expectedGeneratedKey, clientGeneratedKey, "Incorrect generated key by client");
    }

    private static String runServerInBackgroundAndTest(TCPServer server, DiffieHellmanExchange exchanger) throws InterruptedException {
        String serverGeneratedKey = null;
        try {
            serverGeneratedKey = exchanger.serverExchange(server);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return serverGeneratedKey;
    }
}