package client.to_server;

import client.ClientLauncher;
import client.CommandType;
import other.Request;
import other.Response;
import other.exceptions.ErrorType;
import other.exceptions.WrongArgumentException;
import other.models.Organization;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ServerAPIImpl implements ServerAPI{
    private final String serverHost;
    private final int serverPort;
    public int attempts = 0;
    public int connectionTimeout = 2000;
    Socket socket = new Socket();

    public ServerAPIImpl(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public int getAttempts() {
        return attempts;
    }

    public Response executeCommand(CommandType command, Organization organization) throws WrongArgumentException {
        Request request = new Request(
                command,
                organization
        );
        try {
            return sendToServer(request);
        } catch (ConnectException e) {
            throw new WrongArgumentException(ErrorType.NOT_STARTED);
        } catch (IOException e) {
            throw new WrongArgumentException(ErrorType.CONNECTED_REFUSE);
        }
    }

    private Response sendToServer(Request request) throws IOException {
        try {
            // создаю сокет
            Socket socket = new Socket();
            // коннекчу сокет на нужные хост и порт
            socket.connect(new InetSocketAddress(serverHost, serverPort));
            // отправка данных серверу
            // через сокет открываем поток записи, запрос парсим в json и сериализуем
            socket.getOutputStream().write(request.toJson().getBytes(StandardCharsets.UTF_8));
            // создаем массив c максимально возможным размером, в который запишем полученные от сервера байтики
            byte[] buffer = new byte[4096];
            int amount = socket.getInputStream().read(buffer);
            byte[] countPackage = new byte[amount];
            System.arraycopy(buffer, 0, countPackage, 0, amount);

            int count = Integer.parseInt(new String(countPackage, StandardCharsets.UTF_8));
            StringBuilder json = new StringBuilder();
            while(count != 0) {
                // размер считанного массива. 0 если 0 байт, -1 если eof
                amount = socket.getInputStream().read(buffer);
                // массив для записи считанных байтов (может быть меньше чем 4096,
                // поэтому создаем строго фиксированный под кол-во считанных байт)
                if (amount == -1) {
                    throw new EOFException();
                }
                byte[] responseBytes = new byte[amount];
                System.arraycopy(buffer, 0, responseBytes, 0, amount);
                String add = new String(responseBytes, StandardCharsets.UTF_8);
                json.append(add);
                count--;
            }
            // парсим из json'а
            return Response.fromJson(json.toString());
        } catch (NegativeArraySizeException e){
            closeConnection();
            System.exit(0);
            return null;
        }

    }

    public boolean connectToServer(){
        try {
            if (attempts > 0)
                System.out.println("Попытка переподключиться...");
                attempts++;
                socket = new Socket(serverHost, serverPort);
        } catch (UnknownHostException e) {
            System.err.println("Неизвестный хост: " + serverHost + ".\n");
            return false;
        } catch (IOException exception) {
            System.err.println("Ошибка открытия порта " + serverPort + ".\n");
            return false;
        }
        return true;
    }

    public void closeConnection(){
        try{
            socket.getInputStream().close();
            socket.getOutputStream().close();
            socket.close();
            System.out.println("Соединение успешно закрыто.");
        } catch (IOException exception) {
            System.err.println("Соединение экстренно прекращено.");
        }
    }
}
