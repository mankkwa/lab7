package server.commands;

import server.dao.PriorityQueueDAO;

public class RemoveFirst implements Command{
    private PriorityQueueDAO pqd = new PriorityQueueDAO();
    @Override
    public void execute(Object obj) {
        if (pqd.size() != 0) {
            pqd.remove(pqd.firstOrganization().getId());
            pqd.sort();
            System.out.println("Первый элемент удален.");
        } else {
            System.err.println("Коллекция пуста!");
        }
    }
}
