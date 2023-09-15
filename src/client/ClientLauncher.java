package client;

import server.dao.PriorityQueueDAO;

public class ClientLauncher {

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
        /*
        PriorityQueueDAO pqd = new PriorityQueueDAO();
        String output = null;
        try {
            output = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Информация о аргументе командной строки отсутствует. Работа прекращена.");
            System.exit(0);
        }

        pqd.readCollection(output);
        AskIn.chooseFriendly();
        ReaderManager.turnOnConsole();
        CommandManager.whichCommand(output);
        */

    }
}
