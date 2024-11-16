package org.crypto_project.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }

    public void listenToNewClient() throws IOException {
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String readClientAndClose() throws IOException {
        listenToNewClient();
        String message = readMessage();
        closeClient();
        return message;
    }

    public String readMessage() throws IOException {
        boolean clientDisconnected = clientSocket == null || clientSocket.isClosed();
        String message;

        // waiting for new client
        if (clientDisconnected || (message = in.readLine()) == null) {
            throw new IOException("Client disconnected");
        }

        return message;
    }

    public void sendMessage(String message) {
        // if there is no client
        if (clientSocket == null || clientSocket.isClosed()) {
            throw new IllegalStateException("Client not connected");
        }
        out.println(message);
    }

    public void closeClient() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void stop() throws IOException {
        closeClient();
        serverSocket.close();
    }
}