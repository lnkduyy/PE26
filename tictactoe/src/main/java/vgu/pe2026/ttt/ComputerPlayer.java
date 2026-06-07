package vgu.pe2026.ttt;

public class ComputerPlayer extends Player {

    public ComputerPlayer(int number) {
        super(number);
    }

    @Override
    public int chooseMove(Board board) {
        return board.firstAvailableCell();
    }
}