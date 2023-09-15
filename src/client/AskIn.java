package client;

import client.CommandType;
import client.handlers.ConsoleInputHandler;
import client.handlers.FileInputHandler;
import client.handlers.InputHandler;
import jdk.internal.util.xml.impl.Input;
import models.Address;
import models.Coordinates;
import models.Location;
import models.OrganizationType;
import models.exceptions.EndException;
import models.exceptions.ReaderException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class AskIn {
    private static boolean FRIEND_DETECTOR;
    private static boolean friendlyInterface;
    private static ArrayList<String> historyOfFiles = new ArrayList<String>();

    public static boolean chooseFriendly() {
        System.out.println("You хочешь friendly интерфейс? Yes or нет?");
        ConsoleInputHandler cih = new ConsoleInputHandler();
        String answer = cih.readInput().toLowerCase();
        try {
            if (answer.equals("да") || answer.equals("yes")){
                FRIEND_DETECTOR = true;
            } else if (answer.equals("нет") || answer.equals("no")) {
                FRIEND_DETECTOR = false;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Ваш ответ некорректный!");
            chooseFriendly();
        }
        return FRIEND_DETECTOR;
    }

    public static void turnOffFriendly() {
        friendlyInterface = false;
    }

    public static void returnFriendly() {
        friendlyInterface = FRIEND_DETECTOR;
    }

    /**
     * Запрос команды
     */
    public static String askCommand(InputHandler ih) throws EndException, ReaderException {
        String command = null;
        while (command == null) {
            printMessageClass("Введите команду: ");
            try {
                command = ih.readInput();
                CommandType.valueOf(command.toUpperCase()).ordinal();
            } catch (IllegalArgumentException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!command.isEmpty()) {
                        System.err.println("Такой команды не существует!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    command = null;
                } else if (ih != ReaderManager.getHandler() && Objects.equals(command, "")) {
                    throw new ReaderException("");
                } else throw new EndException("Команды "+ command + " не существует!");
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        return command;
    }

    /**
     * Запрос id
     */

    public Long askId(InputHandler ih) throws EndException {
        String inputId = null;
        while (inputId == null) {
            printMessage("Введите id: ");
            try {
                inputId = ih.readInput();
                if (Integer.parseInt(inputId) <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputId.isEmpty()) {
                        System.err.println("Некорректный ввод поля ID!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputId = null;
                } else {
                    throw new EndException("Поле ID введено неверно. Команда будет проигнорирована.");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
        }
        }
        return Long.parseLong(inputId);
    }

    /**
     * Запрос name
     */

    public String askName(InputHandler ih) throws EndException {
        String inputName = null;
        while (inputName == null) {
            printMessage("Введите имя:");
            try {
                inputName = ih.readInput();
                if (inputName.isEmpty()) throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    System.err.println("Ты ввел пустоту!");
                    inputName = null;
                } else {
                    throw new EndException("Некорректный ввод поля name. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        return inputName;
    }

    /**
     * Запрос coordinates
     */

    public Coordinates askCoordinates(InputHandler ih) throws EndException {
        printMessage("< < Введите координаты > >");
        String inputCoordinates = null;

        while (inputCoordinates == null) {
            printMessage("Введите координату x: ");
            try {
                inputCoordinates = ih.readInput();
                Double.parseDouble(inputCoordinates);
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputCoordinates.isEmpty()) {
                        System.err.println("Некорректный ввод поля x!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputCoordinates = null;
                } else {
                    throw new EndException("Некорректный ввод поля x. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        Double x = Double.parseDouble(inputCoordinates);
        inputCoordinates = null;

        while (inputCoordinates == null) {
            printMessage("Введите координату y: ");
            try {
                inputCoordinates = ih.readInput();
                if(Long.parseLong(inputCoordinates) > 873) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputCoordinates.isEmpty()) {
                        System.err.println("Некорректный ввод поля y! (y <= 873)");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputCoordinates = null;
                } else {
                    throw new EndException("Некорректный ввод поля y. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        Long y = Long.parseLong(inputCoordinates);

        return new Coordinates(x, y);
    }


    /**
     * Запрос annualTurnover
     */

    public Float askAnnualTurnover(InputHandler ih) throws EndException{
        String inputAnnualTurnover = null;
        while (inputAnnualTurnover == null) {
            printMessage("Введите годовой оборот: ");
            try {
                inputAnnualTurnover = ih.readInput();
                if (Float.parseFloat(inputAnnualTurnover)<=0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputAnnualTurnover.isEmpty()) {
                        System.err.println("Некорректный ввод поля annualTurnover!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputAnnualTurnover = null;
                } else {
                    throw new EndException("Некорректный ввод поля annualTurnover. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        return Float.parseFloat(inputAnnualTurnover);
    }

    /**
     * Запрос fullName
     */

    public String askFullName(InputHandler ih) throws EndException{
        String inputFullName = null;
        while (inputFullName == null) {
            printMessage("Введите полное имя: ");
            try {
                inputFullName = ih.readInput();
                if (inputFullName.isEmpty() || inputFullName.length()>1637) throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputFullName.isEmpty()) {
                        System.err.println("Некорректный ввод поля fullName!");
                    } else {
                        System.err.println("Ты ввел пустоту! Поэтому поле примет значение null >:)");
                        return null;
                    }
                    inputFullName = null;
                } else {
                    throw new EndException("Некорректный ввод поля fullName. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        return inputFullName;
    }

    /**
     * Запрос employeesCount
     */

    public Integer askEmployeesCount(InputHandler ih) throws EndException{
        String inputEmployeesCount = null;
        while (inputEmployeesCount == null) {
            printMessage("Введите количество сотрудников: ");
            try {
                inputEmployeesCount = ih.readInput();
                if (Integer.parseInt(inputEmployeesCount)<=0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputEmployeesCount.isEmpty()) {
                        System.err.println("Некорректный ввод поля employeesCount!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputEmployeesCount = null;
                } else {
                    throw new EndException("Некорректный ввод поля employeesCount. Команда будет проигнорирована!");
                }
            }catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        return Integer.parseInt(inputEmployeesCount);
    }

    /**
     * Запрос type
     */

    public OrganizationType askType(InputHandler ih) throws EndException{
        String inputOrgType = null;
        while (inputOrgType == null) {
            printMessage("Введите тип организации: ");
            try {
                inputOrgType = ih.readInput();
                OrganizationType.valueOf(inputOrgType.toUpperCase());
            } catch (IllegalArgumentException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputOrgType.isEmpty()) {
                        System.err.println("Некорректный ввод поля type!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputOrgType = null;
                } else {
                    throw new EndException("Некорректный ввод поля type. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        return OrganizationType.valueOf(inputOrgType.toUpperCase());
    }

    /**
     * Запрос postalAddress
     */

    public Address askPostalAddress(InputHandler ih) throws EndException{
        printMessage("< < Введите адрес организации > >");
        String inputPostalAddress = null;
        String street;
        while (inputPostalAddress == null) {
            printMessage("Введите улицу: ");
            try {
                inputPostalAddress = ih.readInput();
                if (inputPostalAddress.isEmpty()) throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputPostalAddress.isEmpty()) {
                        System.err.println("Некорректный ввод поля postalAddress!");
                    } else {
                        System.err.println("Ты ввел пустое поле!");
                    }
                    inputPostalAddress = null;
                } else{
                    throw new EndException("Некорректный ввод поля postalAddress. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        street = inputPostalAddress;
        inputPostalAddress = null;

        printMessage("< < Введите местоположение города > >");
        while (inputPostalAddress == null) {
            printMessage("Введите координату x: ");
            try {
                inputPostalAddress = ih.readInput();
                Double.parseDouble(inputPostalAddress);
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputPostalAddress.isEmpty()) {
                        System.err.println("Некорректный ввод поля x!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputPostalAddress = null;
                } else {
                    throw new EndException("Некорректный ввод поля x. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        Double x = Double.parseDouble(inputPostalAddress);
        inputPostalAddress = null;

        while (inputPostalAddress==null){
            printMessage("Введите координату y: ");
            try {
                inputPostalAddress = ih.readInput();
                Float.parseFloat(inputPostalAddress);
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputPostalAddress.isEmpty()) {
                        System.err.println("Некорректный ввод поля y!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputPostalAddress = null;
                } else {
                    throw new EndException("Некорректный ввод поля y. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        Float y = Float.parseFloat(inputPostalAddress);
        inputPostalAddress = null;

        while (inputPostalAddress==null){
            printMessage("Введите координату z: ");
            try {
                inputPostalAddress = ih.readInput();
                Long.parseLong(inputPostalAddress);
            } catch (NumberFormatException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputPostalAddress.isEmpty()) {
                        System.err.println("Некорректный ввод поля z!\n");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputPostalAddress = null;
                } else {
                    throw new EndException("Некорректный ввод поля z. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
            }
        Long z = Long.parseLong(inputPostalAddress);
        inputPostalAddress = null;

        while (inputPostalAddress==null){
            printMessage("Введите название локации: ");
            try {
                inputPostalAddress = ih.readInput();
            } catch (IllegalArgumentException e) {
                if (friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    if (!inputPostalAddress.isEmpty()) {
                        System.err.println("Некорректный ввод поля name!");
                    } else {
                        System.err.println("Ты ввел пустоту!");
                    }
                    inputPostalAddress = null;
                } else {
                    throw new EndException("Некорректный ввод поля name. Команда будет проигнорирована!");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        String name = inputPostalAddress;
        Location town = new Location(x, y, z, name);

        return new Address(street, town);
    }

    public FileInputStream askFileName(InputHandler ih) throws EndException{
        FileInputStream fileInput = null;
        while (fileInput == null) {
            printMessage("Введите путь до файла: ");
            try {
                String fileName = ih.readInput();
                fileInput = new FileInputStream(fileName);
                if(historyOfFiles.contains(fileName)) {
                    throw new EndException("Этот файл уже был вызван ранее.\n");
                } else {
                    historyOfFiles.add(fileName);
                }
            } catch (FileNotFoundException e) {
                if(friendlyInterface && ih.getClass() == ConsoleInputHandler.class) {
                    System.err.println("Файл не найден. Проверьте корректность указанного пути и повторите попытку!");
                    fileInput = null;
                } else {
                    throw new EndException("Файл не найден. Команда будет проигнорирована.\n");
                }
            } catch (IOException e){
                ReaderManager.returnOnPreviousReader();
                throw new EndException("Невозможно прочитать данные из файла.\n");
            }
        }
        return fileInput;
    }

    private static void printMessage(String message){
        if (friendlyInterface) {
            System.out.println(message);
        }
    }
    public static void printMessageClass(String message){
        if (friendlyInterface && ReaderManager.getHandler().getClass() == ConsoleInputHandler.class) {
            System.out.println(message);
        }
    }
    }
