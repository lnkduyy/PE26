package vgu.pe2026.ttt.Board;

public class Board {

    private final int[] cells = new int[9];

    public boolean isFree(int cell) {
        return cells[cell - 1] == 0;
    }

    public void markCell(int cell, int player) {
        cells[cell - 1] = player;
    }

    public boolean isFull() {
        for (int cell : cells) {
            if (cell == 0) return false;
        }
        return true;
    }

    public int firstAvailableCell() {
        for (int i = 0; i < 9; i++) {
            if (cells[i] == 0) return i + 1;
        }
        return -1;
    }

    public boolean hasWon(int player) {

        int[][] wins = {
                { 0, 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8 },
                { 0, 3, 6 },
                { 1, 4, 7 },
                { 2, 5, 8 },
                { 0, 4, 8 },
                { 2, 4, 6 }
        };

        for (int[] w : wins) {
            if (cells[w[0]] == player &&
                    cells[w[1]] == player &&
                    cells[w[2]] == player) {
                return true;
            }
        }

        return false;
    }

    public void display() {

        System.out.println("| " + cells[0] + " | " + cells[1] + " | " + cells[2] + " |");
        System.out.println("| " + cells[3] + " | " + cells[4] + " | " + cells[5] + " |");
        System.out.println("| " + cells[6] + " | " + cells[7] + " | " + cells[8] + " |");
    }
}