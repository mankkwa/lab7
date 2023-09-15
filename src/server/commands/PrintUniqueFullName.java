package server.commands;

import server.dao.PriorityQueueDAO;
import models.Organization;

import java.util.ArrayList;
import java.util.List;

public class PrintUniqueFullName implements Command{
    private PriorityQueueDAO pqd = new PriorityQueueDAO();

    @Override
    public void execute(Object obj) {
        List<String> uniqueFullName = new ArrayList<String>();
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
    }
}
