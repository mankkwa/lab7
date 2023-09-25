package client;

import client.handlers.InputHandler;
import other.MessageManager;
import other.exceptions.ErrorType;
import other.exceptions.WrongArgumentException;
import other.models.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;


public class AskIn {
    private static MessageManager msg = new MessageManager();

    public void removeLastElement() {
        MessageManager.getFileHistory().remove(
                MessageManager.getFileHistory().size()-1
        );
    }

    public Organization askInputManager(CommandType commandType, InputHandler in) throws WrongArgumentException{
        Organization newOrg = new Organization();
        try {
            // итератор для перемещения по нужным для команды методам
            Iterator<String> iterator = Arrays.stream(commandType.getCommandFields()).iterator();
            if(iterator.hasNext()) {
                // название нужного для запроса поля в массиве энама выбранной команды
                String commandName = iterator.next();
                // цикл foreach для полей newHuman
                for (Field fields : newOrg.getClass().getDeclaredFields()) {
                    // название нынешнего поля newHuman
                    String fieldName = fields.getName().toLowerCase();
                    // если поле массива энама команды совпадает с перебираемым полем экземпляра newHuman
                    if (fieldName.equals(commandName.substring(3).toLowerCase())) {
                        // беру ссылку на необходимый для запроса метод
                        Method method = this.getClass().getDeclaredMethod(commandName, InputHandler.class);
                        // ставлю разрешение на использование метода
                        method.setAccessible(true);
                        // вызываю нужный метод и получаю уже проверенное введенное значение
                        Object o = method.invoke(this, in);
                        // ставлю разрешение на изменение приватного поля newHuman
                        fields.setAccessible(true);
                        // изменяю значение приватного поля
                        fields.set(newOrg, o);
                        // перехожу к следующему необходимому для команды полю
                        if(iterator.hasNext()) commandName = iterator.next();
                        else break;
                    }
                }
                if(Objects.equals(commandName, "askFileName")) {
                    ReaderManager.turnOnFile(askFileName(in));
                    throw new WrongArgumentException(ErrorType.SWITCH_READER);
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            // здесь считаю строки
            throw new WrongArgumentException(ErrorType.IGNORE_STRING);
        }
        return newOrg;
    }


    /**
     * Запрос команды
     */
    public CommandType askCommand(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("команду");
            try {
                return isCorrectCommand(ih.readInput());
            } catch (IOException e) {
                msg.printErrorMessageIO();
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
            }
        } while (MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_COMMAND);
    }

    /**
     * Запрос id
     */

    public Long askId(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("id");
            try {
                return isCorrectLong(ih.readInput());
            } catch (IOException e) {
                msg.printErrorMessageIO();
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
            }
        } while (MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    /**
     * Запрос name
     */

    public String askName(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("имя");
            try {
                return isCorrectString(ih.readInput());
            } catch (IOException e) {
                msg.printErrorMessageIO();
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
            }
        } while (MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    /**
     * Запрос coordinates
     */

    public Coordinates askCoordinates(InputHandler ih) throws WrongArgumentException {
        msg.printMessage("местоположение");
        return new Coordinates(askX(ih), askY(ih));
    }

    private Double askX(InputHandler in) throws WrongArgumentException {
        do {
            msg.printMessage("координату x");
            try {
                return isCorrectDouble(in.readInput());
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        } while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    private Long askY(InputHandler in) throws WrongArgumentException {
        do {
            msg.printMessage("координату y");
            try {
                return isCorrectLong(in.readInput(), 873);
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }
        while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    /**
     * Запрос annualTurnover
     */

    public Float askAnnualTurnover(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("годовой оборот");
            try {
                return isCorrectFloat(ih.readInput());
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }
        while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    /**
     * Запрос fullName
     */

    public String askFullName(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("полное имя");
            try {
                String input = ih.readInput();
                if(input.isEmpty()) {
                    msg.printWarningMessage();
                    return null;
                } return input;
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        } while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    /**
     * Запрос employeesCount
     */

    public Integer askEmployeesCount(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("количество сотрудников");
            try {
                return isCorrectInteger(ih.readInput());
            } catch (WrongArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    /**
     * Запрос type
     */

    public OrganizationType askType(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("тип организации");
            try {
                return isCorrectType(ih.readInput());
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }
        while (MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    /**
     * Запрос postalAddress
     */
    private Float askYFloat(InputHandler in) throws WrongArgumentException {
        do {
            msg.printMessage("координату y");
            try {
                return isCorrectFloat(in.readInput());
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }
        while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    private Double askXDouble(InputHandler in) throws WrongArgumentException {
        do {
            msg.printMessage("координату x");
            try {
                return isCorrectDouble(in.readInput());
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }
        while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    private Long askZLong(InputHandler in) throws WrongArgumentException {
        do {
            msg.printMessage("координату z");
            try {
                return isCorrectLong(in.readInput());
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }
        while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    private String askNameForAddress(InputHandler ih) throws WrongArgumentException {
        do {
            msg.printMessage("название");
            try {
                return isCorrectString(ih.readInput());
            } catch(WrongArgumentException e) {
                msg.printErrorMessage(e);
            } catch (IOException e) {
                msg.printErrorMessageIO();
            }
        }
        while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }


    private String askStreet(InputHandler in) throws WrongArgumentException{
        do {
            msg.printMessage("улицу");
            try {
                return isCorrectString(in.readInput());
            } catch (IOException e) {
                msg.printErrorMessageIO();
            } catch (WrongArgumentException e){
                msg.printErrorMessage(e);
            }
        } while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }

    public Address askPostalAddress(InputHandler ih) throws WrongArgumentException {
        msg.printMessage("местоположение");
        return new Address(askStreet(ih), new Location(askXDouble(ih), askYFloat(ih), askZLong(ih),askStreet(ih)));
    }

    public BufferedReader askFileName(InputHandler ih) throws WrongArgumentException {
        do{
            msg.printMessage("путь до файла");
            try {
                String fileName = ih.readInput();
                BufferedReader reader = new BufferedReader(isCorrectFile(fileName));
                MessageManager.getFileHistory().add(fileName);
                return reader;
            } catch (IOException e) {
                msg.printErrorMessageIO();
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
            }
        }while(MessageManager.isFriendlyInterface());
        throw new WrongArgumentException(ErrorType.IGNORE_STRING);
    }


    private String isCorrectString(String input) throws WrongArgumentException {
        if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
        return input;
    }

    /**
     * Функция для проверки валидности введённого целочисленного значения
     * @param input - строка, введённая пользователем
     * @return если строка валидна - возращает целое число, иначе выбрасывает следующее исключение
     */
    private Integer isCorrectInteger(String input) throws WrongArgumentException {
        try {
            Integer.parseInt(input);
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            else throw new WrongArgumentException(ErrorType.WRONG_TYPE);
        }
        return Integer.parseInt(input);
    }

    private Double isCorrectDouble(String input) throws WrongArgumentException {
        try {
            Double.parseDouble(input);
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            else throw new WrongArgumentException(ErrorType.WRONG_TYPE);
        }
        return Double.parseDouble(input);
    }

    /**
     * Функция для проверки валидности введённого целого числа с установкой нижней границы
     * @param input - строка, введённая пользователем
     * @param last - граница для данного поля
     * @return если строка валидна - возращает целое число, иначе выбрасывает следующее исключение
     */
    private Long isCorrectLong(String input, int last) throws WrongArgumentException {
        try {
            if (Integer.parseInt(input) > last) {
                throw new WrongArgumentException(ErrorType.OUT_OF_RANGE);
            }
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            else throw new WrongArgumentException(ErrorType.WRONG_TYPE);
        }
        return Long.parseLong(input);
    }

    private Long isCorrectLong(String input) throws WrongArgumentException {
        try {
            Long.parseLong(input);
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            else throw new WrongArgumentException(ErrorType.WRONG_TYPE);
        }
        return Long.parseLong(input);
    }

    /**
     * Функция для проверки валидности введённого дробного числа с установкой нижней границы
     * @param input - строка, введённая пользователем
     * @param last - граница для данного поля
     * @return если строка валидна - возращает целое число, иначе выбрасывает следующее исключение
     */
    private Float isCorrectFloat(String input, int last) throws WrongArgumentException {
        try {
            if (Float.parseFloat(input) > last) {
                throw new WrongArgumentException(ErrorType.OUT_OF_RANGE);
            }
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            else throw new WrongArgumentException(ErrorType.WRONG_TYPE);
        }
        return Float.parseFloat(input);
    }

    private Float isCorrectFloat(String input) throws WrongArgumentException {
        try {
            Float.parseFloat(input);
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            else throw new WrongArgumentException(ErrorType.WRONG_TYPE);
        }
        return Float.parseFloat(input);
    }

    private CommandType isCorrectCommand(String input) throws WrongArgumentException {
        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            throw new WrongArgumentException(ErrorType.UNKNOWN);
        }
    }

    private OrganizationType isCorrectType(String input) throws WrongArgumentException {
        try {
            return OrganizationType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(ErrorType.EMPTY);
            else throw new WrongArgumentException(ErrorType.UNKNOWN);
        }
    }

    private FileReader isCorrectFile(String input) throws WrongArgumentException{
        try {
            if(MessageManager.getFileHistory().contains(input)) {
                throw new WrongArgumentException(ErrorType.ALREADY_EXECUTED);
            }
            return new FileReader(input);
        } catch (FileNotFoundException e) {
            throw new WrongArgumentException(ErrorType.NOT_FOUND);
        }
    }
    }
