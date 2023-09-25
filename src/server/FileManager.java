package server;

import client.Converter;
import com.google.gson.*;
import other.models.Organization;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

public class FileManager {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new Converter()).create();
    private static String envVariable = null;

    public FileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    public static void writeCollection(Collection<?> data) {
        if (envVariable != null) {
            try (OutputStreamWriter writer = new FileWriter(envVariable)) {
                writer.write(gson.toJson(data));
                writer.close();
                System.out.println("Коллекция сохранена: " + envVariable);
            } catch (IOException e) {

                ServerLauncher.log.error("Файл не может быть открыт.");
            }
        } else ServerLauncher.log.error("Данный файл не найден!");
    }

    public static PriorityQueue<Organization> readCollection() {
        if (envVariable != null) {
            try {
                Scanner scanner = new Scanner(new File(envVariable));
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
                ServerLauncher.log.info("Коллекция загружена!");
                return collection;
            } catch (FileNotFoundException exception) {
                ServerLauncher.log.error("Файл не найден! Коллекция будет создана автоматически.");
            } catch (NoSuchElementException exception) {
                ServerLauncher.log.error("Файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                ServerLauncher.log.error("В загрузочном файле не обнаружена корректная коллекция.");
            } catch (IllegalStateException exception) {
                ServerLauncher.log.error("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else ServerLauncher.log.error("Системная переменная с загрузочным файлом не найдена!");
            return new PriorityQueue<>();
        }
    }
