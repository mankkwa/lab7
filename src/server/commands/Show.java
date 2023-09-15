package server.commands;

import server.dao.PriorityQueueDAO;

public class Show implements Command{
    private static PriorityQueueDAO pqd = new PriorityQueueDAO();
    @Override
    public void execute(Object obj) {
        System.out.println(pqd.show());
    }
}
