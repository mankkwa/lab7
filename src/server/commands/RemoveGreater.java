package server.commands;

import server.dao.DAO;
import server.dao.PriorityQueueDAO;
import other.models.Organization;

public class RemoveGreater implements Command{
    private final DAO pqd;
    public RemoveGreater(DAO pqd) {
        this.pqd = pqd;
    }

    @Override
    public Object execute(Object obj) {
        Organization organizationFind = (Organization) obj;
        if (!(pqd.size() == 0)) {
            pqd.removeGreater(organizationFind);
            return null;
        } else {
            return ("Коллекция пуста.");
        }

    }
}
