package org.example.networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServer {
    private static final int PORT = 6060;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Chat server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedWriter out;
        private String name;
        private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                synchronized (clients) {
                    clients.add(this);
                }

                out.write("Enter your name: \n");
                out.flush();
                name = in.readLine();

                out.write("Welcome, " + name + "! Type 'exit' to leave.\n");
                out.flush();

                // Start sender thread
                new Thread(() -> {
                    try {
                        while (true) {
                            String msg = messageQueue.take();
                            out.write(msg + "\n");
                            out.flush();
                        }
                    } catch (IOException | InterruptedException e) {
                        System.err.println("Failed to send to client " + name);
                    }
                }).start();

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        messageQueue.offer("Goodbye!");
                        break;
                    }

                    System.out.println(name + ": " + message);
                    broadcastMessage(this, message);
                }

                in.close();
                out.close();
                socket.close();
                synchronized (clients) {
                    clients.remove(this);
                }

            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            }
        }

        private void broadcastMessage(ClientHandler sender, String message) {
            synchronized (clients) {
                for (ClientHandler client : clients) {
                    if (client == sender) {
                        client.messageQueue.offer("you: " + message);
                    } else {
                        client.messageQueue.offer(sender.name + ": " + message);
                    }
                }
            }
        }
    }
}
