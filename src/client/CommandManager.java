package client;

import server.commands.*;
import server.dao.PriorityQueueDAO;
import models.Address;
import models.Organization;
import models.exceptions.EndException;
import models.exceptions.ReaderException;


import java.io.FileInputStream;

import static client.AskIn.*;


/**
 * Класс для работы с командами
 */
public class CommandManager {

    private static PriorityQueueDAO priorityQueueDAO = new PriorityQueueDAO();

    static {
        priorityQueueDAO.setAvailableId();
    }

    public static final Command[] commands = {
            new Add(),
            new Clear(),
            new Update(priorityQueueDAO),
            new Exit(),
            new Save(),
            new Show(),
            new RemoveById(),
            new ExecuteScript(),
            new RemoveGreater(),
            new Help(),
            new RemoveFirst(),
            new AverageOfAnnualTurnover(),
            new PrintUniqueFullName(),
            new CountGreaterThanPostalAddress(),
    };


    /**
     * whichFunction - функция для работы с запросом пользователя
     */
    public static Object whichFunction (int commandIndex, String fileName) throws EndException {
        Organization org = new Organization();
        AskIn ask = new AskIn();
        PriorityQueueDAO collection = new PriorityQueueDAO();
        Long id = 1L;
        switch (commandIndex){
            case 0:
                printMessageClass("< Вызвана команда add >");
                try {
                    org.setName(ask.askName(ReaderManager.getHandler()));
                    org.setType(ask.askType(ReaderManager.getHandler()));
                    org.setPostalAddress(ask.askPostalAddress(ReaderManager.getHandler()));
                    org.setCoordinates(ask.askCoordinates(ReaderManager.getHandler()));
                    org.setEmployeesCount(ask.askEmployeesCount(ReaderManager.getHandler()));
                    org.setAnnualTurnover(ask.askAnnualTurnover(ReaderManager.getHandler()));
                    org.setFullName(ask.askFullName(ReaderManager.getHandler()));
                } catch (EndException e){
                    System.err.println(e.getMessage());
                    org = null;
                    break;
                }
                break;
            case  1:
                printMessageClass("< Вызвана команда clear >");
                break;
            case 2:
                printMessageClass("< Вызвана команда update >");
                    try{
                        id = ask.askId(ReaderManager.getHandler());
                        if (collection.get(id) != null){
                            try{
                                org.setName(ask.askName(ReaderManager.getHandler()));
                                org.setType(ask.askType(ReaderManager.getHandler()));
                                org.setPostalAddress(ask.askPostalAddress(ReaderManager.getHandler()));
                                org.setCoordinates(ask.askCoordinates(ReaderManager.getHandler()));
                                org.setEmployeesCount(ask.askEmployeesCount(ReaderManager.getHandler()));
                                org.setAnnualTurnover(ask.askAnnualTurnover(ReaderManager.getHandler()));
                                org.setFullName(ask.askFullName(ReaderManager.getHandler()));
                                org.setId(id);
                            } catch (EndException e){
                                System.err.println(e.getMessage());
                            }
                        } else {
                            System.err.println("Такого элемента нет!");
                        }
                    } catch (EndException e){
                        System.err.println(e.getMessage());
                        break;
                    }
                break;
            case 3:
                printMessageClass("< Вызвана команда exit >");
                break;
            case 4:
                printMessageClass("< Вызвана команда save >");
                priorityQueueDAO.saveCollectionToFile(fileName);
                break;
            case 5:
                printMessageClass("< Вызвана команда show >");
                break;
            case 6:
                printMessageClass("< Вызвана команда remove_by_id >");
                    try {
                        id = ask.askId(ReaderManager.getHandler());
                        if (collection.get(id) != null) {
                            collection.get(id).setId(id);
                            return collection.get(id);
                        } else {
                            System.err.println("Такого элемента нет!");
                        }
                    } catch (EndException e) {
                        System.err.println(e.getMessage());
                    }
                break;
            case 7:
                printMessageClass("< Вызвана команда execute_script >");
                FileInputStream fileInputStream = null;
                try {
                   fileInputStream = ask.askFileName(ReaderManager.getHandler());
                   return fileInputStream;
                } catch (EndException e){
                    System.err.print(e.getMessage());
                    return null;
                }
            case 8:
                printMessageClass("< Вызвана команда remove_greater >");
                try {
                    return new Organization(
                            ask.askName(ReaderManager.getHandler()),
                            ask.askCoordinates(ReaderManager.getHandler()),
                            ask.askAnnualTurnover(ReaderManager.getHandler()),
                            ask.askFullName(ReaderManager.getHandler()),
                            ask.askEmployeesCount(ReaderManager.getHandler()),
                            ask.askType(ReaderManager.getHandler()),
                            ask.askPostalAddress(ReaderManager.getHandler())
                    );
                } catch (EndException e){
                    System.err.println(e.getMessage());
                }
                break;
            case 9:
                printMessageClass("< Вызвана команда help >");
                break;
            case 10:
                printMessageClass("< Вызвана команда remove_first >");
                break;
            case 11:
                printMessageClass("< Вызвана команда average_of_annual_turnover >");
                break;
            case 12:
                printMessageClass("< Вызвана команда print_unique_full_name >");
                break;
            case 13:
                printMessageClass("< Вызвана команда count_greater_than_postal_address >");
                try {
                    Address postalAddress = ask.askPostalAddress(ReaderManager.getHandler());
                    org.setPostalAddress(postalAddress);
                } catch (EndException e){
                    System.err.println(e.getMessage());
                }
                break;
        }
        return org;
    }

    public static void whichCommand(String fileName){
        String command;
        try {
            command = AskIn.askCommand(ReaderManager.getHandler());
        } catch (EndException | ReaderException e){
            System.err.println(e.getMessage());
            whichCommand(fileName);
            return;
        }
        int commandIndex = CommandType.valueOf(command.toUpperCase()).ordinal();
        Object o = null;
        try {
            o = whichFunction(commandIndex, fileName);
        } catch (EndException e) {
            System.err.println(e.getMessage());
        }
        commands[commandIndex].execute(o);
        whichCommand(fileName);
        }
    }
