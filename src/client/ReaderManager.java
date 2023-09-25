package client;

import client.handlers.ConsoleInputHandler;
import client.handlers.FileInputHandler;
import client.handlers.InputHandler;
import other.MessageManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.util.ArrayList;

public class ReaderManager {
    private static InputHandler handler;
    private static final ArrayList<InputHandler> handlers = new ArrayList<>();

    public static InputHandler getHandler() {
        return handler;
    }

    public static void turnOnConsole() {
        // новый экземпляр класса считывания
        handler = new ConsoleInputHandler();
        // добавляем в массив хендлеров, чтобы потом к нему вернуться
        handlers.add(handler);
        // Возврат к дружественному интерфейсу после считывания с файла, если оно было
        MessageManager.returnFriendly();
        // Добавляем косоль в список активных вкладок приложения
        MessageManager.getFileHistory().add("Console");
    }

    public static void returnOnPreviousReader() {
        handlers.remove(handlers.size() - 1);
        handler = handlers.get(handlers.size()-1);
        MessageManager.returnFriendly();
    }

    public static void turnOnFile(BufferedReader reader) {
        handler = new FileInputHandler(reader);
        handlers.add(handler);
        MessageManager.turnOffFriendly();
    }

}
