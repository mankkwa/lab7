package server;

import models.Request;
import models.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    private static final int PORT = 8081;

    public void start() throws IOException {
        try {
            // Создание ServerSocketChannel и его привязка к определенному порту
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            System.out.println("Сервер запущен, прослушиваю порт: " + PORT);

            while (true) {
                // Принятие входящего подключения от клиента
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("Клиент подключен.");
                // Обработка запроса от клиента
                handleClient(socketChannel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод для обработки входящих сообщений от клиента
    private static void handleClient(SocketChannel socketChannel) throws IOException {
        //создаем буфер размером в 1024 байта
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // read(buffer) читает данные из канала и записывает их в буфер
        // количество возвращаемых байтов - bytesRead
        int bytesRead = socketChannel.read(buffer);
        if (bytesRead == -1) {
            socketChannel.close();
            return;
        }

        //преобразуем данные из буффера в строку, чтобы сформировать джсон
        String requestJson = new String(buffer.array(), 0, bytesRead);
        //преобразовываем в реквест
        Request request = Request.fromJson(requestJson);
        System.out.println("Сервер получил сообщение: " + request.getMessage());

        // Обработка запроса и отправка ответа
        String responseData = "Привет от сервера!";
        Response response = new Response("success", responseData);

        // Отправка ответа клиенту
        String responseJson = response.toJson(); //преобразуем ответ
        ByteBuffer responseBuffer = ByteBuffer.wrap(responseJson.getBytes()); //оборачиваем в байтовый буфер
        socketChannel.write(responseBuffer); //отправляем обратно клиенту

        // Закрытие канала
        socketChannel.close();
    }

    }
