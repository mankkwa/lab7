package server.commands;

import server.dao.PriorityQueueDAO;
import models.Organization;

public class RemoveById implements Command{
    private PriorityQueueDAO dao = new PriorityQueueDAO();
    @Override
    public void execute(Object obj) {
        Organization org = (Organization) obj;
        try{
            if(dao.get(org.getId()) != null) {
                dao.remove(org.getId());
                dao.sort();
                System.out.println("Элемент был удален!");
            }
        } catch (NullPointerException e){
            System.err.println(e.getMessage());
        }

    }
}
