package vgu.pe2026.ttt.version.singleuser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import vgu.pe2026.ttt.Game;
import vgu.pe2026.ttt.IO.SocketIO;

public class TicTacToeServer {

    public static void main(String[] args) throws IOException {

        int port = 1812;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server started on port " + port);

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                try {
                    SocketIO io = new SocketIO(clientSocket);
                    new Game(io).play(1); 
                    System.out.println("Game ended normally");
                } 
                catch (IOException e) {
                    System.out.println("Game interrupted: " + e.getMessage());
                } 
                finally {
                    clientSocket.close();
                    System.out.println("Ready for next client");
                }
            }
        }
    }
}
