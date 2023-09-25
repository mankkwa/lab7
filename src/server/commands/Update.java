package server.commands;

import server.dao.DAO;
import other.models.Organization;

/**
 * Команда упдате - обновить значение элемента коллекции
 */

public class Update implements Command {
    private final DAO priorityQueueDAO;

    public Update(DAO priorityQueueDAO) {
        this.priorityQueueDAO = priorityQueueDAO;
    }

    @Override
    public Object execute(Object newObject) {
        Organization org = (Organization) newObject;
        //тут вроде мы получаем айди переданного сюда org и если он null, то элемент не будет обновлен
        //в ином случае - обновлен
        if (priorityQueueDAO.get(org.getId()) != null){
                newObject = null;
                priorityQueueDAO.update(org.getId(), org);
                priorityQueueDAO.sort();
        }else {
            newObject = ("Элемента с таким id не нашлось.\n");
        }
        return newObject;
    }
}
