package vgu.pe2026.ttt.version.stateless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import vgu.pe2026.ttt.Board.Board;
import vgu.pe2026.ttt.Player.ComputerPlayer;

public class TicTacToeServer {
    private static final int PORT = 1815;

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Stateless] Server started on port " + PORT);
            while (true) {
                Socket connection = serverSocket.accept();
                System.out.println("New client connected: " + connection.getInetAddress());
                handleClient(connection); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void handleClient(Socket connection) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true)
        ) {
            String clientMsg = in.readLine();
            if (clientMsg == null) return;

            String[] parts = clientMsg.split("\\|");
            if (parts.length < 2) return;

            int clientPos = Integer.parseInt(parts[0]);
            String boardStr = parts[1];



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
                } catch (IOException ignored) {}
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
            try { connection.close(); } catch (IOException ignored) {}
        }
    }
}
