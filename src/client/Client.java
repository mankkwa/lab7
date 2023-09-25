package client;

import client.to_server.ServerAPI;
import client.to_server.ServerAPIImpl;
import other.MessageManager;
import other.Request;
import other.Response;
import other.exceptions.ErrorType;
import other.exceptions.WrongArgumentException;
import other.models.Organization;

import java.io.*;
import java.net.Socket;

public class Client {
    private final AskIn ask = new AskIn();
    private final MessageManager msg = new MessageManager();
    private final String serverHost;
    private final int serverPort;
    private final int connectionAttempts = 20;
    private final int connectionTimeout = 2000;
    private ServerAPI serverAPI;

    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        boolean run = true;
        serverAPI = new ServerAPIImpl(serverHost, serverPort);
        while (!serverAPI.connectToServer()) {
                if (serverAPI != null) {
                    if (serverAPI.getAttempts() > connectionAttempts) {
                        ClientLauncher.log.error("Превышено количество попыток подключиться.");
                        return;
                    }
                }
                try {
                        Thread.sleep(connectionTimeout);
                    } catch (InterruptedException e) {
                        ClientLauncher.log.error("Произошла ошибка при попытке ожидания подключения!");
                        ClientLauncher.log.info("Повторное подключение будет произведено немедленно.");
                    }
                }

                while (run) {
                    int connectAttempts = 0;  // Счетчик попыток подключения
                    try {
                        // получаю проверенный тип команды, которую ввел пользователь
                        CommandType commandType = ask.askCommand(ReaderManager.getHandler());
                        // получаю сформированный объект, содержащий аргументы, необходимые для команды
                        Organization organization = ask.askInputManager(commandType, ReaderManager.getHandler());
                        // делаю запрос на сервер, передаю CommandType типа команды и
                        // объект класса Organization, получаю ответ
                        Response response = serverAPI.executeCommand(commandType, organization);
                        // если статус ответа от сервера - ОК, соообщаю об этом на консоль
                        if (response.status == Response.Status.OK) {
                            ClientLauncher.log.info("Команда успешно выполнена.");
                            if (response.getArgumentAs(String.class) != null) {
                                System.out.println(response.getArgumentAs(String.class));
                            }
                        } else if (response.status == Response.Status.SERVER_EXIT) {
                            ClientLauncher.log.info("Клиент завершает свою работу.");
                            serverAPI.closeConnection();
                            run = false;
                        } else if (response.status == Response.Status.ERROR) {
                            ClientLauncher.log.error("В процессе выполнения данной команды произошла ошибка.");
                        }
                    } catch (NullPointerException e) {
                        ReaderManager.returnOnPreviousReader();
                        ask.removeLastElement();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        ClientLauncher.log.error("Непредвиденная ошибка.");
                    } catch (WrongArgumentException e) {
                        msg.printErrorMessage(e);
                        if (e.getType() == ErrorType.CONNECTION_BROKEN) {
                            System.out.println("Попытка переподключиться..");
                            while (!serverAPI.connectToServer()) {
                                if (serverAPI.getAttempts() > connectionAttempts) {
                                    ClientLauncher.log.error("Превышено количество попыток подключиться.");
                                    return;
                                }
                                try {
                                    Thread.sleep(connectionTimeout);
                                } catch (InterruptedException exception) {
                                    ClientLauncher.log.error("Произошла ошибка при попытке ожидания подключения.");
                                }
                            }

                        }
                        if (e.getType() == ErrorType.CONNECTED_REFUSE) {
                            run = false;
                        }
                        if (e.getType() == ErrorType.NOT_STARTED) {
                            System.out.println("Попытка переподключиться..");
                            while (!serverAPI.connectToServer()) {
                                if (serverAPI.getAttempts() > connectionAttempts) {
                                    ClientLauncher.log.error("Превышено количество попыток подключиться.");
                                    run = false;
                                }
                                try {
                                    Thread.sleep(connectionTimeout);
                                } catch (InterruptedException exception) {
                                    ClientLauncher.log.error("Произошла ошибка при попытке ожидания подключения.");
                                }
                            }
                        }
                    }

                }
            }
        }

