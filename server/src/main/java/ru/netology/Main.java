package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                System.out.println("\nОжидание нового клиента...");
                try (Socket clientSocket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.printf("Новое подключение принято. Порт клиента: %d\n", clientSocket.getPort());

                    out.println("Здравствуйте! Как Вас зовут?");
                    String name = in.readLine();
                    System.out.printf("Клиент ответил, что его зовут: %s\n", name);

                    out.println(String.format("Приятно познакомиться, %s! Вы ребенок? (да/нет)", name));
                    String response = in.readLine();
                    System.out.printf("Клиент ответил на вопрос о возрасте: %s\n", response);

                    if ("да".equalsIgnoreCase(response)) {
                        out.println(String.format("Добро пожаловать в детскую зону, %s! Давай играть!", name));
                        System.out.printf("Отправлен ответ для ребенка %s\n", name);
                    } else if ("нет".equalsIgnoreCase(response)) {
                        out.println(String.format("Добро пожаловать во взрослую зону, %s! Хорошего дня!", name));
                        System.out.printf("Отправлен ответ для взрослого %s\n", name);
                    } else {
                        out.println("Некорректный ответ. До свидания!");
                        System.out.printf("Клиент %s ввел некорректный ответ\n", name);
                    }

                    System.out.printf("Диалог с клиентом %s завершен.\n", name);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера!");
            e.printStackTrace();
        }
    }
}