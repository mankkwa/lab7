package server.commands;

import models.*;
import server.dao.*;

/**
 * Команда add - добавляем элемент в коллекцию
 */
public class Add implements Command {
        private static final DAO priorityQueueDAO = new PriorityQueueDAO();

    @Override
    public void execute (Object newObject) {
        Organization org = (Organization) newObject;
        if (org != null) {
            priorityQueueDAO.add(org);
            priorityQueueDAO.sort();
            System.out.println("Элемент добавлен в коллекцию!");
        }
    }
}
