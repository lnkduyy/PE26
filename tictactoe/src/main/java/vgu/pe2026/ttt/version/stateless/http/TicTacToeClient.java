package vgu.pe2026.ttt.version.stateless.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import vgu.pe2026.ttt.Board.Board;

public class TicTacToeClient {
    private static final String HOST = "localhost";
    private static final int PORT = 1817;

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("Hello! HTTP Client (java.net.http.HttpClient)");
        
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

            // Connect to HTTP Server using HttpClient
            try {
                String boardString = boardToString(board);
                String url = "http://" + HOST + ":" + PORT + "/play?pos=" + position + "&board=" + boardString;
                
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                
                if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    String[] parts = responseBody.split("\\|");
                    if (parts.length >= 2) {
                        updateBoard(board, parts[1]);
                    }
                } else {
                    System.out.println("Server returned status code: " + response.statusCode());
                }
            } catch (Exception e) {
                System.out.println("Connection error: " + e.getMessage());
                break;
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
