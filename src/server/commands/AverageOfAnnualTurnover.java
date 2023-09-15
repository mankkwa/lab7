package server.commands;

import server.dao.PriorityQueueDAO;
import models.exceptions.EndException;

public class AverageOfAnnualTurnover implements Command{
    private PriorityQueueDAO pqd = new PriorityQueueDAO();

    @Override
    public void execute(Object obj) {
        try {
            Float average = pqd.averageOfAnnual();
            if (average == 0) throw new EndException("Коллекция пуста!");
            System.out.println("Среднее значение годового оборота: " + average);
        } catch (EndException e){
            System.err.println(e.getMessage());
        }

    }
}
