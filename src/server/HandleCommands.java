package server;

import client.CommandType;
import other.Request;
import other.Response;
import other.models.Organization;
import server.commands.*;
import server.dao.DAO;
import server.dao.PriorityQueueDAO;

import java.io.IOException;

public class HandleCommands {
    private static final DAO database;

    static {
        database = new PriorityQueueDAO(FileManager.readCollection());
        database.setAvailableId();
    }

    public void exit() {
        try {
            database.save();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Response handleRequest(Request request) {
        return executeCommand(request.getCommand(), request.getArgumentAs(Organization.class));
    }

    private Response executeCommand(CommandType command, Object commandArgument){
        int commandIndex = command.ordinal();
        commandArgument = commands[commandIndex].execute(commandArgument);
        ServerLauncher.log.info("Запрос успешно обработан");
        return new Response(Response.Status.OK, commandArgument);
    }

    public static final Command[] commands = {
            new Add(database),
            new Clear(database),
            new Update(database),
            new Exit(),
            new Show(database),
            new RemoveById(database),
            new ExecuteScript(database),
            new RemoveGreater(database),
            new Help(),
            new RemoveFirst(database),
            new AverageOfAnnualTurnover(database),
            new PrintUniqueFullName(database),
            new CountGreaterThanPostalAddress(database),
    };
}
