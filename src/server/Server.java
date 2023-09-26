package server;

import client.CommandType;
import other.Request;
import other.Response;
import other.exceptions.ErrorType;
import other.exceptions.WrongArgumentException;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    private final InetSocketAddress address;
    private Selector selector;
    private final Set<SocketChannel> session;
    private boolean work = true;
    private Response response;
    private HandleCommands commandManager = new HandleCommands();
    private ExecutorService readThreadPool;
    private ForkJoinPool processThreadPool;
    private ForkJoinPool sendThreadPool;
    private Request request;


    public Server(String host, int port) {
        this.address = new InetSocketAddress(host, port);
        this.session = new HashSet<SocketChannel>();
        // Используем кэшированный пул потоков для чтения запросов
        this.readThreadPool = Executors.newCachedThreadPool();
        // Используем ForkJoinPool для обработки запросов
        this.processThreadPool = new ForkJoinPool();
        // Используем ForkJoinPool для отправки ответов
        this.sendThreadPool = new ForkJoinPool();
    }

    public void start() throws IOException {
        if (!openSocket()) return;
        try {
            while (work) {
                //блокируем текущий поток пока что-нибудь не произойдет на каналах
                selector.select();
                //итератор для обхода зарегистрированных ключей в селекторе
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        //получение реквеста от клиента
                        readThreadPool.submit(() -> {
                                    try {
                                        request = read(key);
                                        processThreadPool.submit(() -> processRequest(key, request));
                                    } catch (WrongArgumentException e) {
                                        // Handle WrongArgumentException
                                        e.printStackTrace();
                                    }
                        });
                    }
                }
            }
            stopSocket();
        }catch(IOException e){
            ServerLauncher.log.error("Сервер завершает свою работу... :(");
            }
        }

    private void processRequest(SelectionKey key, Request request) {
        if (request == null) {
            key.cancel();
        } else if (!request.getCommand().equals(CommandType.EXIT)) {
            // Используем ForkJoinPool для отправки ответов
            sendThreadPool.submit(() -> write(key, commandManager.handleRequest(request)));
        } else {
            response = new Response(Response.Status.SERVER_EXIT, "Сервер завершает свою работу.");
            commandManager.exit();
            work = false;
        }
    }

    private boolean openSocket(){
        try {
            ServerLauncher.log.info("Запуск сервера...");
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //привязываем к определенному адресу
            serverSocketChannel.socket().bind(address);
            serverSocketChannel.configureBlocking(false);
            //регистрируем для ожидания новых операций
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            ServerLauncher.log.info("Сервер запущен!");
            return true;
        } catch (ClosedChannelException e) {
            ServerLauncher.log.fatal("Сервер был принудительно закрыт");
            return false;
        } catch(BindException e) {
            ServerLauncher.log.fatal("На исходных хосте и порту уже запущен сервер");
            return false;
        } catch (IOException e) {
            ServerLauncher.log.fatal("Ошибка запуска сервера");
            return false;
        }

    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            session.add(channel);
            ServerLauncher.log.info("Клиент "+channel.socket().getRemoteSocketAddress()+
                    " успешно подсоединился к серверу");
        } catch (IOException e) {
            ServerLauncher.log.error("Ошибка селектора");
        }
    }

    private void stopSocket() {
        try {
            ServerLauncher.log.info("Закрытие сервера...");
            serverSocketChannel.close();
            ServerLauncher.log.info("Сервер успешно закрыт");
        } catch (IOException e) {
            ServerLauncher.log.error("Ошибка закрытия сервера");
        }
    }

    /**
     * Получаем запрос от клиента
     * */
    private Request read(SelectionKey key) throws WrongArgumentException {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            // Создаем буфер для чтения данных размером 4096 байт
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            // Читаем данные из SocketChannel в буфер и возвращаем количество прочитанных байт
            int bytesRead = channel.read(buffer);

            // Проверяем, были ли прочитаны какие-то данные
            // Если количество прочитанных байт равно -1, закрываем канал и отменяем ключ
            if (bytesRead == -1) {
                channel.close();
                key.cancel();
                // Возвращаем null, так как поток данных закрыт
                return null;
            }
            // Преобразуем данные из буфера в строку, используя только фактически прочитанные байты
            String requestJson = new String(buffer.array(), 0, bytesRead);
            // Пытаемся преобразовать строку JSON в объект запроса
            Request request = Request.fromJson(requestJson);
            return request;
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new WrongArgumentException(ErrorType.CONNECTED_REFUSE);
        }
    }
/**
 * Отправка результата выполнения запроса клиенту
 * */
    private void write(SelectionKey key, Response response){
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            // Преобразуем JSON-строку в байтовый массив
            byte[] jsonData = response.toJson().getBytes(StandardCharsets.UTF_8);
            // Вычисляем размер данных в байтах
            int dataSize = jsonData.length;
            // Рассчитываем количество пакетов для отправки (размер данных деленный на 4096 байт, с округлением вверх)
            int count = dataSize/4096 + (dataSize%4096 == 0 ? 0 : 1);

            // преобразуем кол-во пакетов в строку
            String countPackage = Integer.toString(count);
            //
            channel.write(ByteBuffer.wrap(countPackage.getBytes(StandardCharsets.UTF_8)));

            if(dataSize > 4096) {
                for(int i = 0; i < dataSize; i+=4096) {
                    if(i + 4096 > dataSize) {
                        // Если это последний пакет, отправляем часть данных, оставшуюся до конца
                        channel.write(ByteBuffer.wrap(
                                Arrays.copyOfRange(response.toJson().getBytes(StandardCharsets.UTF_8), i, dataSize))
                        );
                    } else {
                        // Отправляем пакет данных размером 4096 байт
                        channel.write(ByteBuffer.wrap(
                                Arrays.copyOfRange(response.toJson().getBytes(StandardCharsets.UTF_8), i, i+4096))
                        );
                    }
                }
            } else {
                // Если размер данных меньше или равен 4096 байт, отправляем один пакет
                channel.write(ByteBuffer.wrap(response.toJson().getBytes(StandardCharsets.UTF_8)));
            }
            ServerLauncher.log.info("Отправка выполнена успешно");
        } catch (IOException e){
            ServerLauncher.log.error("Отправка не удалась");
        }
    }

    }
