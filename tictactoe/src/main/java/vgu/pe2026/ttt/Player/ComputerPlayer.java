package vgu.pe2026.ttt.Player;

import java.io.IOException;

import vgu.pe2026.ttt.Board.Board;

public class ComputerPlayer extends Player {

    public ComputerPlayer(int number) {
        super(number);
    }

    @Override
    public int chooseMove(Board board) throws IOException {
        return board.firstAvailableCell();
    }
}