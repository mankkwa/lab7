package client.to_server;

import client.CommandType;
import other.Response;
import other.exceptions.WrongArgumentException;
import other.models.Organization;

public interface ServerAPI {
    Response executeCommand(CommandType command, Organization organization) throws WrongArgumentException;
    void closeConnection();
    boolean connectToServer();
    int getAttempts();
}
