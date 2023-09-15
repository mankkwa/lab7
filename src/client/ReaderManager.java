package client;

import client.handlers.ConsoleInputHandler;
import client.handlers.FileInputHandler;
import client.handlers.InputHandler;

import java.io.BufferedInputStream;
import java.util.ArrayList;

public class ReaderManager {
    private static InputHandler handler;
    private static final ArrayList<InputHandler> handlers = new ArrayList<>();

    public static InputHandler getHandler() {
        return handler;
    }

    public static void turnOnConsole() {
        handler = new ConsoleInputHandler();
        handlers.add(handler);
        AskIn.returnFriendly();
    }

    public static void removeLast() {
        handlers.remove(handlers.size() - 1);
    }

    public static void returnOnPreviousReader() {
        handler = handlers.get(handlers.size()-1);
        AskIn.returnFriendly();
    }

    public static void turnOnFile(BufferedInputStream reader) {
        handler = new FileInputHandler(reader);
        handlers.add(handler);
        AskIn.turnOffFriendly();
    }

}
