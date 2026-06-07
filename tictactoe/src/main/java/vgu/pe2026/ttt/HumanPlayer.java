package vgu.pe2026.ttt;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner scanner;

    public HumanPlayer(int number, Scanner scanner) {
        super(number);
        this.scanner = scanner;
    }

    @Override
    public int chooseMove(Board board) {

        boolean validMove = false;
        int chosenCell = -1;

        while (!validMove) {

            String input = scanner.nextLine();

            if ("q".equals(input)) {
                System.out.println("End of the game");
                System.exit(0);
            }

            try {
                chosenCell = Integer.parseInt(input);

                if (chosenCell < 1 || chosenCell > 9) {
                    System.out.println("Please, input a valid number [1-9]");
                    System.out.println("Player#1's turn");
                }
                else if (!board.isFree(chosenCell)) {
                    System.out.println("The cell is occupied!");
                    System.out.println("Player#1's turn");
                }
                else validMove = true;

            } catch (NumberFormatException e) {
                System.out.println("Please, input a valid number [1-9]");
                System.out.println("Player#1's turn");
            }
        }

        return chosenCell;
    }
}