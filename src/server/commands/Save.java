package server.commands;

import server.dao.PriorityQueueDAO;

public class Save implements Command{
    private static PriorityQueueDAO pqd = new PriorityQueueDAO();

    @Override
    public void execute(Object obj) {
            pqd.sort();
    }
}
