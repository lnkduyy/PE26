package vgu.pe2026.ttt.version.stateless;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import vgu.pe2026.ttt.Board.Board;

public class TicTacToeClient {
    private static final String HOST = "localhost";
    private static final int PORT = 1815;

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        System.out.println("Hello!");
        
        while (!gameOver) {
            board.display();
            
            if (board.hasWon(2)) {
                System.out.println("Player#2 won!");
                break;
            } else if (board.hasWon(1)) {
                System.out.println("Player#1 won!");
                break;
            } else if (board.isFull()) {
                System.out.println("It is a draw!");
                break;
            }
            
            System.out.println("Player#1's turn");
            int position = -1;
            boolean validMove = false;
            
            while (!validMove) {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("q")) {
                    System.out.println("End of the game");
                    return;
                }
                try {
                    position = Integer.parseInt(input);
                    if (position >= 1 && position <= 9 && board.isFree(position)) {
                        validMove = true;
                    } else if (position < 1 || position > 9) {
                        System.out.println("Please, input a valid number [1-9]");
                        System.out.println("Player#1's turn");
                    } else {
                        System.out.println("The cell is occupied!");
                        System.out.println("Player#1's turn");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, input a valid number [1-9]");
                    System.out.println("Player#1's turn");
                }
            }
            
            board.markCell(position, 1);
            
            if (board.hasWon(1) || board.isFull()) {
                board.display();
                if (board.hasWon(1)) System.out.println("Player#1 won!");
                else System.out.println("It is a draw!");
                break;
            }

            // Connect to server to let Computer move
            try (Socket socket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                 
                String boardString = boardToString(board);
                String message = position + "|" + boardString;
                
                out.println(message);
                
                String response = in.readLine();
                if (response != null) {
                    String[] parts = response.split("\\|");
                    if (parts.length >= 2) {
                        updateBoard(board, parts[1]);
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection error: " + e.getMessage());
                break;
            } finally {
                scanner.close();
            }
        }
    }

    private static String boardToString(Board board) {
        int[] cells = board.getCells();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            sb.append(cells[i]);
            if (i < cells.length - 1) sb.append(",");
        }
        return sb.toString();
    }

    private static void updateBoard(Board board, String boardString) {
        String[] values = boardString.split(",");
        int[] cells = new int[9];
        for (int i = 0; i < values.length && i < 9; i++) {
            cells[i] = Integer.parseInt(values[i]);
        }
        board.setCells(cells);
    }
}
