package server.commands;

import server.dao.DAO;
import server.dao.PriorityQueueDAO;

public class Show implements Command{
    private final DAO pqd;

    public Show(DAO pqd) {
        this.pqd = pqd;
    }

    @Override
    public Object execute(Object obj) {
        if (pqd.show() != null) {
            return pqd.show();
        } else {
            return "Коллекция пустая.";
        }
    }
}
