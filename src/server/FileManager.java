package server;

import client.Converter;
import com.google.gson.*;
import models.Organization;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

public class FileManager {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new Converter()).create();

    public static void writeCollection(Collection<?> data, String output) {
        if (output != null) {
            try (OutputStreamWriter writer = new FileWriter(output)) {
                writer.write(gson.toJson(data));
                writer.close();
                System.out.println("Коллекция сохранена: " + output);
            } catch (IOException e) {
                System.err.println("Файл не может быть открыт.");
            }
        } else System.err.println("Данный файл не найден!");
    }

    public static PriorityQueue<Organization> readCollection(String input) {
        if (input != null) {
            try {
                Scanner scanner = new Scanner(new File(input));
                //здесь использую стринг буилдер, чтобы нормально собрать строку, тк просто стринг неизменяемый
                StringBuilder builder = new StringBuilder();
                PriorityQueue<Organization> collection = new PriorityQueue<>();
                //здесь просто по сути склеиваем из говна и палок одну целую строку, пока сканер что то видит
                //append - как раз таки им и склеиваем
                while (scanner.hasNext()) {
                    builder.append(scanner.next());
                }
                //тута происходит десериализация строки builder.toString() в массив organization
                //потом просто преобразуем в список, тк приорити куеуе не умеет так,
                // а потом уже с addAll все добавляем в коллекцию
                collection.addAll(Arrays.asList(gson.fromJson(builder.toString(), Organization[].class)));
                System.out.println("Коллекция загружена!");
                return collection;
            } catch (FileNotFoundException exception) {
                System.err.println("Файл не найден! Коллекция будет создана автоматически.");
            } catch (NoSuchElementException exception) {
                System.err.println("Файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                System.err.println("Некорректные данные!");
            }
        } else System.err.println("Аргумент командной строки не был передан!");
            return new PriorityQueue<>();
        }
    }
