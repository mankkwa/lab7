package server.commands;

import server.dao.DAO;
import server.dao.PriorityQueueDAO;
import other.models.Organization;

public class RemoveById implements Command{
    private final DAO dao;

    public RemoveById(DAO dao){
        this.dao = dao;
    }
    @Override
    public Object execute(Object obj) {
        Organization org = (Organization) obj;
        if (dao.get(org.getId()) != null) {
            dao.remove(org.getId());
            return null;
        } else {
            return "Человека с таким id не существует!";
        }

    }
}
