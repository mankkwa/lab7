package server.commands;

import client.AskIn;
import client.ReaderManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class ExecuteScript implements Command{

    @Override
    public void execute(Object obj) {
        try {
            if(obj == null) throw new NullPointerException();
            FileInputStream fileInputStream = (FileInputStream) obj;
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ReaderManager.turnOnFile(bufferedInputStream);
            AskIn.turnOffFriendly();
        } catch (NullPointerException e){
            System.err.println("Скрипт не будет выполнен.");
        }

    }
}
