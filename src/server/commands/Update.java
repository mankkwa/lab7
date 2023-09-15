package server.commands;

import server.dao.DAO;
import models.Organization;

/**
 * Команда упдате - обновить значение элемента коллекции
 */

public class Update implements Command {
    private final DAO priorityQueueDAO;

    public Update(DAO priorityQueueDAO) {
        this.priorityQueueDAO = priorityQueueDAO;
    }

    @Override
    public void execute(Object newObject) {
        Organization org = (Organization) newObject;
        //тут вроде мы получаем айди переданного сюда org и если он null, то элемент не будет обновлен
        //в ином случае - обновлен
        try {
            if (priorityQueueDAO.get(org.getId()) != null){
                priorityQueueDAO.update(org.getId(), org);
                priorityQueueDAO.sort();
                System.out.print("Элемент обновлён :)\n");
            }
        } catch (NullPointerException e){
            System.err.println(e.getMessage());
        }

    }
}
