package server.commands;

public class Exit implements Command {

    @Override
    public Object execute(Object obj){
        return "exit";
    }
}

