package server.commands;

import server.dao.DAO;

/**
 * Команда клеар - очищаем коллекцию
 */
public class Clear implements Command{
    private final DAO priorityQueueDAO;

    public Clear(DAO priorityQueueDAO) {
        this.priorityQueueDAO = priorityQueueDAO;
    }

    @Override
    public Object execute(Object obj) {
        //тут я воспользовалась просто методом clear, не стала изобретать велосипед
        priorityQueueDAO.clear();
        return "Коллекция успешно очищена.";
    }
}
