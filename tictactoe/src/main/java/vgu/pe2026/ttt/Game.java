package vgu.pe2026.ttt;

import java.io.IOException;

import vgu.pe2026.ttt.Board.Board;
import vgu.pe2026.ttt.IO.GameIO;
import vgu.pe2026.ttt.Player.Player;

public class Game {

    private final Board board;
    private final Player player1;
    private final Player player2;
    private final GameIO io;

    public Game(GameIO io, Player player1, Player player2) {
        this.io = io;
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
    }

    public void play(int startingPlayer) throws IOException {

        io.println("Hello!");
        io.println(board.toString());

        int currentPlayer = startingPlayer;
        boolean gameOver = false;

        while (!gameOver) {

            io.println("Player#" + currentPlayer + "'s turn");

            int move;

            try {
                Player currentPlayerObj = (currentPlayer == 1) ? player1 : player2;
                move = currentPlayerObj.chooseMove(board);
            } catch (IOException e) {
                io.println("End of the game");
                return;
            }

            board.markCell(move, currentPlayer);

            if (board.hasWon(currentPlayer)) {
                io.println(board.toString());
                io.println("Player#" + currentPlayer + " won!");
                gameOver = true;
            } 
            else if (board.isFull()) {
                io.println(board.toString());
                io.println("It is a draw!");
                gameOver = true;
            } 
            else {
                io.println(board.toString());
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            }
        }
    }
}