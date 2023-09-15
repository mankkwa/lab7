package server.commands;

public class Exit implements Command {
    @Override
    public void execute(Object obj){
        System.out.println("Выхожу из программы...");
        System.exit(0);
    }
}

