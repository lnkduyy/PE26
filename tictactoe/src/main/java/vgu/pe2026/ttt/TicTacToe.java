package vgu.pe2026.ttt;

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

        new Game(firstPlayer);
    }
}