package client;

import models.Request;
import models.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public void start() throws IOException {
        try {
            Socket socket = new Socket("localhost", 8081);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            System.out.print("Введите сообщение для сервера: ");
            while ((userInput = inputReader.readLine()) != null) {
                Request request = new Request(userInput);
                String jsonRequest = request.toJson();
                out.println(jsonRequest);
                String jsonResponse = in.readLine();
                Response response = Response.fromJson(jsonResponse);

                System.out.println("Сервер прислал ответ: " + response.getMessage());

                System.out.print("Введите сообщение для сервера: ");
            }
            in.close();
            out.close();
            inputReader.close();
            socket.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
