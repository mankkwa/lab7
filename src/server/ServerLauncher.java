package server;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();
    }
}
