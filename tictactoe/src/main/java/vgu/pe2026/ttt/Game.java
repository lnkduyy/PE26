package vgu.pe2026.ttt;

import java.util.Scanner;

import vgu.pe2026.ttt.Board.Board;
import vgu.pe2026.ttt.Player.ComputerPlayer;
import vgu.pe2026.ttt.Player.HumanPlayer;

public class Game {

    private final Board board;
    private final HumanPlayer human;
    private final ComputerPlayer computer;

    public Game(int startingPlayer) {

        board = new Board();

        Scanner scanner = new Scanner(System.in);

        human = new HumanPlayer(1, scanner);
        computer = new ComputerPlayer(2);

        play(startingPlayer);
    }

    private void play(int currentPlayer) {

        System.out.println("Hello!");
        board.display();

        boolean gameOver = false;

        while (!gameOver) {

            System.out.println("Player#" + currentPlayer + "'s turn");

            int move;

            if (currentPlayer == 1) move = human.chooseMove(board);
            else move = computer.chooseMove(board);

            board.markCell(move, currentPlayer);

            if (board.hasWon(currentPlayer)) {
                board.display();
                System.out.println("Player#" + currentPlayer + " won!");
                gameOver = true;
            } 
            else if (board.isFull()) {
                board.display();
                System.out.println("It is a draw!");
                gameOver = true;
            } 
            else {
                board.display();
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            }
        }
    }
}