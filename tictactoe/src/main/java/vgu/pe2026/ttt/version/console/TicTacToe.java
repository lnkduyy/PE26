package vgu.pe2026.ttt.version.console;

import java.io.IOException;

import vgu.pe2026.ttt.Game;
import vgu.pe2026.ttt.IO.ConsoleIO;

public class TicTacToe {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please, input a valid option [1-2]");
            return;
        }

        int firstPlayer;

        try { firstPlayer = Integer.parseInt(args[0]); }
        catch (NumberFormatException e) {
            System.out.println("Please, input a valid option [1-2]");
            return;
        }

        if (firstPlayer != 1 && firstPlayer != 2) {
            System.out.println("Please, input a valid option [1-2]");
            return;
        }

        try {
            new Game(new ConsoleIO()).play(firstPlayer);
        } catch (IOException e) {
            System.out.println("End of the game");
        }
    }
}