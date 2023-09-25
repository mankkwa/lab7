package server.commands;

import server.dao.DAO;
import server.dao.PriorityQueueDAO;
import other.models.Organization;

import java.util.ArrayList;
import java.util.List;

public class PrintUniqueFullName implements Command{
    private final DAO pqd;

    public PrintUniqueFullName(DAO pqd){
        this.pqd = pqd;
    }

    @Override
    public Object execute(Object obj) {
        List<String> uniqueFullName = new ArrayList<String>();
        if (pqd.size() != 0) {
        for(Organization organization: pqd.getAll()) {
            String fullName = organization.getFullName();
            if(!uniqueFullName.contains(fullName)) {
                uniqueFullName.add(fullName);
            } else {
                uniqueFullName.remove(fullName);
            }
        }
        for(String field: uniqueFullName) {
            System.out.println(field);
        }
        return obj;
    } else {
            return "Коллекция пустая.";
        }
    }
}
