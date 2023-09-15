package server.commands;

import server.dao.PriorityQueueDAO;
import models.Organization;

public class RemoveGreater implements Command{
    private PriorityQueueDAO pqd = new PriorityQueueDAO();

    @Override
    public void execute(Object obj) {
        Organization organizationFind = (Organization) obj;
        try {
            if (!pqd.getAll().contains(organizationFind)){
                pqd.add(organizationFind);
            }
            pqd.removeGreater(organizationFind);
            pqd.sort();
            System.out.println("Удаление прошло успешно!");
        } catch (NullPointerException e){
            System.out.println("remove_greater: Удаление не удалось.");
        }

    }
}
