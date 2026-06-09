package vgu.pe2026.ttt.Player;

import java.io.IOException;

import vgu.pe2026.ttt.Board.Board;
import vgu.pe2026.ttt.IO.GameIO;

public class HumanPlayer extends Player {

    private final GameIO io;

    public HumanPlayer(int number, GameIO io) {
        super(number);
        this.io = io;
    }

    @Override
    public int chooseMove(Board board) throws IOException {

        boolean validMove = false;
        int chosenCell = -1;

        while (!validMove) {

            String input = io.readLine();

            if (input == null || "q".equals(input)) {
                throw new IOException("quit");
            }

            try {
                chosenCell = Integer.parseInt(input);

                if (chosenCell < 1 || chosenCell > 9) {
                    io.println("Please, input a valid number [1-9]");
                    io.println("Player#" + number + "'s turn");
                } else if (!board.isFree(chosenCell)) {
                    io.println("The cell is occupied!");
                    io.println("Player#" + number + "'s turn");
                } else {
                    validMove = true;
                }

            } catch (NumberFormatException e) {
                io.println("Please, input a valid number [1-9]");
                io.println("Player#" + number + "'s turn");
            }
        }

        return chosenCell;
    }
}