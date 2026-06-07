package vgu.pe2026.ttt;

import java.io.IOException;

import vgu.pe2026.ttt.Board.Board;
import vgu.pe2026.ttt.IO.GameIO;
import vgu.pe2026.ttt.Player.ComputerPlayer;
import vgu.pe2026.ttt.Player.HumanPlayer;

public class Game {

    private final Board board;
    private final HumanPlayer human;
    private final ComputerPlayer computer;
    private final GameIO io;

    public Game(GameIO io) {
        this.io = io;
        this.board = new Board();
        this.human = new HumanPlayer(1, io);
        this.computer = new ComputerPlayer(2);
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
                if (currentPlayer == 1) move = human.chooseMove(board);
                else move = computer.chooseMove(board);
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