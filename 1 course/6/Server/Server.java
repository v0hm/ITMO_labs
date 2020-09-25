import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class Server {

    static ServerSocket server;
    static Socket client;
    static ObjectInputStream input;
    static ObjectOutputStream output;
    static Scanner scanner;

    static Gson gson = new Gson();

    static HashMap<Long, LabWork> collection = new HashMap<Long, LabWork>();

    static java.time.LocalDate Date_of_initialization = LocalDate.now();

    static Package message_from_server;
    static boolean client_is_connect = false;
    static boolean in_work = true;
    static String file_name;

    public static void main(String[] args) {

        int serverPort = 1111;
        file_name = args[0];
        scanner = new Scanner(System.in);

        try {
            server = new ServerSocket(serverPort);
            System.out.println("Server is up");
            connection();
            while (in_work){
                package_validation(receive());
            }
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("3");
        }
    }
    //TODO Модуль чтения запроса.
    public static Package receive() throws IOException, ClassNotFoundException {
        Package receive_message;
        receive_message = (Package) input.readObject();
        System.out.println("Package received");
        return receive_message;
    }
    //TODO Модуль отправки ответов клиенту.
    public static void send(Package package_to_send) throws IOException {
        output.writeObject(package_to_send);
        System.out.println("Package sended");
    }
    //TODO Модуль приёма подключений.
    public static void connection() throws IOException {
        client = server.accept();
        System.out.println("Client connected");
        input = new ObjectInputStream(client.getInputStream());
        output = new ObjectOutputStream(client.getOutputStream());
        System.out.println("Server initialized");
    }
    //TODO логирование
    public static void package_validation(Package package_from_client) throws IOException {
        Package package_to_client = new Package();
        switch (package_from_client.getCommand()){
            case "info":{
                package_to_client.setMessage("Тип коллекции: HashMap" + "\n" + "Дата инициализации:" + Date_of_initialization + "\n" + "Колличество элементов коллекции: " /*+ collection.size()*/);
            }
        }
        send(package_to_client);
        package_to_client = null;
    }
}