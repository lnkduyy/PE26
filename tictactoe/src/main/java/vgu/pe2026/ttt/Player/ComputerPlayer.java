package vgu.pe2026.ttt.Player;

import vgu.pe2026.ttt.Board.Board;

public class ComputerPlayer extends Player {

    public ComputerPlayer(int number) {
        super(number);
    }

    @Override
    public int chooseMove(Board board) {
        return board.firstAvailableCell();
    }
}