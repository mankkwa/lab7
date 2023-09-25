package client.handlers;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInputHandler extends InputHandler{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readInput() {
        try {
            String input = scanner.nextLine().trim().split(" ")[0];
            return input;
        } catch (
                NoSuchElementException e) {
            System.out.print("Программа завершает свою работу без сохранения данных.\n");
            System.exit(0);
        }
        return null;
    }
}
