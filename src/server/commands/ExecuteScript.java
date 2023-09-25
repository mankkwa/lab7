package server.commands;

import client.AskIn;
import client.ReaderManager;
import other.MessageManager;
import server.dao.DAO;
import server.dao.PriorityQueueDAO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.Reader;

public class ExecuteScript implements Command{
    private DAO dao;

    public ExecuteScript(DAO dao){
        this.dao = dao;
    }

    @Override
    public Object execute(Object obj) {
        try {
            if(obj == null) throw new NullPointerException();
            Reader fileInputStream = (Reader) obj;
            BufferedReader bufferedInputStream = new BufferedReader(fileInputStream);
            ReaderManager.turnOnFile(bufferedInputStream);
            MessageManager.turnOffFriendly();
        } catch (NullPointerException e){
            System.err.println("Скрипт не будет выполнен.");
        }
        return null;
    }
}
