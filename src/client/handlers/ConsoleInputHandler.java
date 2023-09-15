package client.handlers;

import java.util.Scanner;

public class ConsoleInputHandler extends InputHandler{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readInput() {
        String input = scanner.nextLine().trim().split(" ")[0];
        return input;
    }
}
