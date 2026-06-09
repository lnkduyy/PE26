package vgu.pe2026.ttt.version.multiuser.multithread.threadpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vgu.pe2026.ttt.Game;
import vgu.pe2026.ttt.IO.SocketIO;

public class TicTacToeServer {

    private static final int POOL_SIZE = 4;

    public static void main(String[] args) throws IOException {

        int port = 1814;

        // Fixed thread pool: at most POOL_SIZE games run concurrently.
        // A 5th client connects and waits in the OS backlog until a thread is free.
        // Under a flood of 10,000 connections the server stays alive because no new
        // threads are created beyond the pool size.
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("[Thread pool " + POOL_SIZE + "] Server started on port " + port);

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                pool.submit(() -> {
                    try {
                        SocketIO io = new SocketIO(clientSocket);
                        new Game(io).play(1);
                        System.out.println("Game ended normally");
                    } catch (IOException e) {
                        System.out.println("Game interrupted: " + e.getMessage());
                    } finally {
                        try { clientSocket.close(); } catch (IOException ignored) {}
                        System.out.println("Task finished, socket closed");
                    }
                });
            }
        }
    }
}
