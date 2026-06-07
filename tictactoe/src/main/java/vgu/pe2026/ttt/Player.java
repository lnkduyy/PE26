package vgu.pe2026.ttt;

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