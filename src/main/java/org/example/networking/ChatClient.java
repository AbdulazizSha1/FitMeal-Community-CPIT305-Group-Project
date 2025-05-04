package org.example.networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6060;

    private static final List<String> BAD_WORDS = Arrays.asList("stupid", "idiot", "dumb", "bad", "hate", "ugly");

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to chat server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            System.out.print(in.readLine());
            String name = scanner.nextLine();
            out.write(name + "\n");
            out.flush();

            Thread receiveThread = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.err.println("Connection closed.");
                }
            });
            receiveThread.start();

            while (true) {
                String message = scanner.nextLine();
                String filteredMessage = filterBadWords(message);
                out.write(filteredMessage + "\n");
                out.flush();
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Could not connect to server: " + e.getMessage());
        }
    }

    private static String filterBadWords(String input) {
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (BAD_WORDS.contains(word.toLowerCase())) {
                result.append("**** ");
            } else {
                result.append(word).append(" ");
            }
        }

        return result.toString().trim();
    }
}
