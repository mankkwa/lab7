package server;

import models.Request;
import models.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void start() throws IOException {
        try {
                ServerSocket serverSocket = new ServerSocket(8081);
                System.out.println("Сервер запущен и ожидает подключения клиента...");

                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился.");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    Request request = Request.fromJson(inputLine);
                    System.out.println("Сервер получил сообщение: " + request.getMessage());
                    String message = "Привет от сервера!";
                    Response response = new Response(message);
                    String jsonResponse = response.toJson();
                    out.println(jsonResponse);
                    System.out.println("Сервер отправил ответ: " + jsonResponse);
                }
                in.close();
                out.close();
                clientSocket.close();
                serverSocket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
