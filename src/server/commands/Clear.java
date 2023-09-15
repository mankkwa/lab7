package server.commands;

import server.dao.DAO;
import server.dao.PriorityQueueDAO;

/**
 * Команда клеар - очищаем коллекцию
 */
public class Clear implements Command{
    private static final DAO priorityQueueDAO = new PriorityQueueDAO();

    @Override
    public void execute(Object obj) {
        //тут я воспользовалась просто методом clear, не стала изобретать велосипед
        priorityQueueDAO.clear();
        System.out.println("Коллекция очищена.");
    }
}
