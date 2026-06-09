package vgu.pe2026.ttt.version.multiuser.multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import vgu.pe2026.ttt.Game;
import vgu.pe2026.ttt.IO.SocketIO;

public class TicTacToeServer {

    public static void main(String[] args) throws IOException {

        int port = 1813;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("[No thread pool] Server started on port " + port);

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // One new thread per client — no limit on concurrent games.
                // If 10,000 clients connect simultaneously the JVM will run out of
                // resources (threads/memory) and crash.
                new Thread(() -> {
                    try {
                        SocketIO io = new SocketIO(clientSocket);
                        new Game(io).play(1);
                        System.out.println("Game ended normally");
                    } catch (IOException e) {
                        System.out.println("Game interrupted: " + e.getMessage());
                    } finally {
                        try { clientSocket.close(); } catch (IOException ignored) {}
                        System.out.println("Thread finished, socket closed");
                    }
                }).start();
            }
        }
    }
}
