package vgu.pe2026.ttt.version.multiuser.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TicTacToeClient {

    public static void main(String[] args) throws IOException {

        String host = "localhost";
        int port = 1813;

        try (
            Socket socket = new Socket(host, port);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
            Scanner stdin = new Scanner(System.in)
        ) {
            String line;

            while ((line = fromServer.readLine()) != null) {

                System.out.println(line);

                if ("Player#1's turn".equals(line)) {
                    String input = stdin.nextLine();
                    toServer.println(input);
                }

                if (line.contains("won!") || line.contains("draw!") || line.contains("End of the game")) {
                    break;
                }
            }
        }

        System.out.println("Disconnected from server.");
    }
}
