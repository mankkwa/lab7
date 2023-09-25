package server.commands;

import server.dao.DAO;

public class AverageOfAnnualTurnover implements Command{
    private final DAO pqd;

    public AverageOfAnnualTurnover(DAO pqd) {
        this.pqd = pqd;
    }

    @Override
    public Object execute(Object obj) {
        if (!(pqd.size() == 0)) {
            Float average = pqd.averageOfAnnual();
            return ("Среднее значение годового оборота: " + average);
        }
        else {
            return "Коллекция пуста.";
        }
    }
}
