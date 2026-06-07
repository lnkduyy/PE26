package vgu.pe2026.ttt.Player;

import vgu.pe2026.ttt.Board.Board;

public abstract class Player {

    protected final int number;

    protected Player(int number) { 
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
    
    public abstract int chooseMove(Board board);
}