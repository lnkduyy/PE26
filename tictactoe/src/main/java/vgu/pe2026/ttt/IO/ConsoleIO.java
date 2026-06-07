package vgu.pe2026.ttt.IO;

import java.util.Scanner;

public class ConsoleIO implements GameIO {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }
}
