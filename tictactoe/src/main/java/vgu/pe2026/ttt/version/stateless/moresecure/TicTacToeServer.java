package vgu.pe2026.ttt.version.stateless.moresecure;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import vgu.pe2026.ttt.Board.Board;
import vgu.pe2026.ttt.Player.ComputerPlayer;

public class TicTacToeServer {
    private static final int PORT = 1816; // Use port 1816 for moresecure
    private static final String SECRET_KEY = System.getenv("SECRET_KEY") != null ? System.getenv("SECRET_KEY") : "default-secret";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Stateless - More Secure] Server started on port " + PORT);
            while (true) {
                Socket connection = serverSocket.accept();
                System.out.println("New client connected: " + connection.getInetAddress());
                handleClient(connection); // Chạy trên 1 thread duy nhất
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket connection) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true)
        ) {
            String clientMsg = in.readLine();
            if (clientMsg == null) return;

            String[] parts = clientMsg.split("\\|");
            if (parts.length < 3) return;

            int clientPos = Integer.parseInt(parts[0]);
            String boardStr = parts[1];
            String clientHMAC = parts[2];

            String calHMAC = generateHMAC(boardStr);
            if (!calHMAC.equals(clientHMAC)) {
                System.out.println("Invalid HMAC. Expected: " + calHMAC + ", Received: " + clientHMAC);
                return;
            }

            Board board = new Board();
            String[] values = boardStr.split(",");
            int[] cells = new int[9];
            for (int i = 0; i < 9; i++) {
                cells[i] = Integer.parseInt(values[i]);
            }
            board.setCells(cells);
            
            // Server's move
            ComputerPlayer computer = new ComputerPlayer(2);
            int serverMove = -1;
            if (!board.isFull() && !board.hasWon(1)) {
                try {
                    serverMove = computer.chooseMove(board);
                    if (serverMove != -1) {
                        board.markCell(serverMove, 2);
                    }
                } catch (Exception ignored) {}
            }

            // Update boardStr
            cells = board.getCells();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cells.length; i++) {
                sb.append(cells[i]);
                if (i < cells.length - 1) sb.append(",");
            }
            
            out.println(serverMove + "|" + sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); } catch (Exception ignored) {}
        }
    }

    private static String generateHMAC(String boardString) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256HMAC.init(secret_key);
            byte[] hmacData = sha256HMAC.doFinal(boardString.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
