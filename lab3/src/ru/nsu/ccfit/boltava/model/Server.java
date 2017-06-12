package ru.nsu.ccfit.boltava.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(1077);
            Socket clientSocket = server.accept();

            System.out.println("Client connected: " + clientSocket.getInetAddress());

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            Thread reader = new Thread(() -> {
                String input;
                try {
                    while((input = in.readLine()) != null) {
                        System.out.println("Incoming message: " + input);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            reader.start();

            Thread writer = new Thread(() -> {
                String input;
                try {
                    while((input = stdIn.readLine()) != null) {
                        System.out.println("Outcoming message: " + input);
                        out.println(input);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.start();

            clientSocket.close();
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
