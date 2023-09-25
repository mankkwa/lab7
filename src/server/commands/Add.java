package server.commands;

import other.models.Organization;
import server.dao.*;

/**
 * Команда add - добавляем элемент в коллекцию
 */
public class Add implements Command {
    private final DAO priorityQueueDAO;

    public Add(DAO priorityQueueDAO) {
        this.priorityQueueDAO = priorityQueueDAO;
    }

    @Override
    public Object execute (Object newObject) {
        Organization org = (Organization) newObject;
        priorityQueueDAO.add(org);
        priorityQueueDAO.sort();
        return null;
    }
}
