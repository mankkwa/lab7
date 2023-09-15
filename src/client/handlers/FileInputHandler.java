package client.handlers;

import client.ReaderManager;

import java.io.BufferedInputStream;
import java.io.IOException;

public class FileInputHandler extends InputHandler {
    private final BufferedInputStream bufferedInput;

    public FileInputHandler(BufferedInputStream bufferedInput) {
        this.bufferedInput = bufferedInput;
    }

    @Override
    public String readInput() throws IOException {
        String line = "";
        int i;
        while ((i = bufferedInput.read()) != -1) {
            if (i != '\n' && i != '\r') {
                line += (char) i;
            } else if (i == '\n') break;
        } if (i==-1){
            ReaderManager.returnOnPreviousReader();
            ReaderManager.removeLast();
        }
        return line.split(" ")[0];
    }
}
