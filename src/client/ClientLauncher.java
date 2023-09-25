package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import other.MessageManager;
import other.exceptions.WrongArgumentException;
import server.dao.PriorityQueueDAO;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;

public class ClientLauncher {
    public static final Logger log = LogManager.getLogger(ClientLauncher.class.getName());
    public static void main(String[] args) throws WrongArgumentException {
        String ServerHost = "localhost";
        int ServerPort = 65100;
        // Новый клиент
        Client client = new Client(ServerHost, ServerPort);
        // Включение считывание с консоли
        ReaderManager.turnOnConsole();
        // Запрос на включение дружественного интерфейса
        new MessageManager().turnOnFriendly();
        //Запускаем логику клиента
        client.start();
    }
}
