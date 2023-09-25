package server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLauncher {
    public static final Logger log = LogManager.getLogger(ServerLauncher.class.getName());
    public static void main(String[] args) throws Exception {

        String fileName = null;
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Аргумент командной строки не был передан!");
            System.exit(0);
        }
        FileManager fileManager = new FileManager(fileName);
        Server server = new Server("localhost", 65100);
        server.start();
    }
}
