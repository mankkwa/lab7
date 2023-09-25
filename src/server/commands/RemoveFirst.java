package server.commands;

import server.dao.DAO;
import server.dao.PriorityQueueDAO;

public class RemoveFirst implements Command{
    private final DAO pqd;

    public RemoveFirst(DAO pqd){
        this.pqd = pqd;
    }

    @Override
    public Object execute(Object obj) {
        if (pqd.size() != 0) {
            pqd.remove(pqd.firstOrganization().getId());
            pqd.sort();
            return ("Первый элемент удален.");
        } else {
            return ("Коллекция пуста!");
        }
    }
}
