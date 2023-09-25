package client.handlers;

import client.ReaderManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class FileInputHandler extends InputHandler {
    private final BufferedReader reader;

    public FileInputHandler(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public String readInput() throws IOException {
        return reader.readLine().trim().split(" ")[0];
    }
}
