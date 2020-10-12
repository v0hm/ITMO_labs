import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    static ServerSocket server;
    static Socket client;
    static ObjectInputStream input;
    static ObjectOutputStream output;
    static Scanner scanner;

    static Gson gson = new Gson();

    static HashMap<Long, LabWork> collection = new HashMap<>();

    static java.time.LocalDate Date_of_initialization = LocalDate.now();

    static Package message_from_server;
    static Package receive_message;
    static boolean client_is_connect = false;
    static boolean in_work = true;
    static String file_name;
    static String start_of_saving = "[ ";
    static String end_of_saving = "]";
    static String comma = ", ";


    public static void main(String[] args) {

        int serverPort = 1111;
        file_name = args[0];
        try {
            JsonReader file_reader = new JsonReader(new BufferedReader(new FileReader(file_name)));
            System.out.println("file opened");
            JsonParser jsonParser = new JsonParser();
            JsonArray collection_array = jsonParser.parse(file_reader).getAsJsonArray();
            System.out.println("file converted to array");
            for (JsonElement collection_element_json : collection_array) {
                LabWork collection_element_labwork = gson.fromJson(collection_element_json, LabWork.class);
                collection.put(collection_element_labwork.getId(),collection_element_labwork);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Неверно введён файл");
            System.exit(1);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введён файл");
            System.exit(2);
        }

        try {
            server = new ServerSocket(serverPort);
            System.out.println("Server is up");
            connection();
            while (in_work){
                //read_from_cmd();
                package_validation(receive());
            }
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("3");
        }
    }
    public static Package receive() throws IOException, ClassNotFoundException {
        receive_message = (Package) input.readObject();
        System.out.println("Package received");
        return receive_message;
    }
    public static void send(Package package_to_send) throws IOException {
        output.writeObject(package_to_send);
        System.out.println("Package sended");
    }
    public static void connection() throws IOException {
        client = server.accept();
        System.out.println("Client connected");
        input = new ObjectInputStream(client.getInputStream());
        output = new ObjectOutputStream(client.getOutputStream());
        System.out.println("Server initialized");
    }
    public static void package_validation(Package package_from_client) throws IOException {
        Package package_to_client = new Package();
        switch (package_from_client.getCommand()){
            case "info":{
                package_to_client.setMessage("Тип коллекции: HashMap" + "\n" + "Дата инициализации:" + Date_of_initialization + "\n" + "Колличество элементов коллекции: " /*+ collection.size()*/);
                break;
            }
            case "show":{
                if(collection.isEmpty())
                    package_to_client.setMessage("Коллекция пуста");
                else {
                    StringBuilder message = new StringBuilder();
                    for (Map.Entry<Long, LabWork> entry : collection.entrySet()) {
                        message.append(entry.getValue().toString());
                        message.append("\n");
                    }
                    package_to_client.setMessage(message.toString());
                }
                break;
            }
            case "insert":{
                if (collection.containsKey(package_from_client.getKey()))
                    package_to_client.setMessage("Элемент с таким key уже существует");
                else
                collection.put(package_from_client.getKey(),package_from_client.getElement());
                break;
            }
            case "update":{
                if (!collection.containsKey(package_from_client.getKey()))
                    package_to_client.setMessage("Элемента с таким key не существует");
                else{
                    collection.remove(package_from_client.getKey());
                    collection.put(package_from_client.getKey(),package_from_client.getElement());
                }
                break;
            }
            case "remove_key":{
                if (!collection.containsKey(package_from_client.getKey()))
                    package_to_client.setMessage("Элемента с таким key не существует");
                else {
                    collection.remove(package_from_client.getKey());
                    StringBuilder message = new StringBuilder();
                    message.append("Был удалён элемент с key = ");
                    message.append(package_from_client.getKey());
                    package_to_client.setMessage(message.toString());
                }
                break;
            }
            case "clear":{
                collection.clear();
                package_to_client.setMessage("Коллекция была успешно отчищена");
                break;
            }
            case "remove_lower":{
                HashMap<Long, LabWork> collection_clone = (HashMap<Long, LabWork>) collection.clone();
                for (Map.Entry<Long, LabWork> entry : collection_clone.entrySet()) {
                    if (package_from_client.getElement().compareTo(entry.getValue()) > 0) {
                        collection.remove(entry.getKey());
                    }
                }
                collection_clone.clear();
                break;
            }
            case "replace_if_greater":{
                if (!collection.containsKey(package_from_client.getKey()))
                    package_to_client.setMessage("Элемента с таким key не существует");
                else {
                    try {
                        if (package_from_client.getElement().compareTo(collection.get(package_from_client.getKey())) > 0) {
                            collection.put(package_from_client.getKey(), package_from_client.getElement());
                        }
                    } catch (JsonSyntaxException e) {
                        package_to_client.setMessage("Неправильно введён элемент");
                    }
                }
                break;
            }
            case "remove_lower_key":{
                HashMap<Long, LabWork> collection_clone = (HashMap<Long, LabWork>) collection.clone();
                for (Map.Entry<Long, LabWork> entry : collection_clone.entrySet())
                    if (entry.getKey() < package_from_client.getKey())
                        collection.remove(entry.getKey());
                collection_clone.clear();
                break;
            }
            case "remove_all_by_difficulty":{
                try {
                    HashMap<Long, LabWork> collection_clone = (HashMap<Long, LabWork>) collection.clone();
                    for (Map.Entry<Long, LabWork> entry : collection_clone.entrySet())
                        if (entry.getValue().getDifficulty() == Difficulty.valueOf(package_from_client.getDifficulty()))
                            collection.remove(entry.getKey());
                    collection_clone.clear();
                } catch (IllegalArgumentException e) {
                    package_to_client.setMessage("Такой сложности не существует");
                }
                break;
            }
            case "min_by_coordinates":{
                Coordinates min_coordinates = new Coordinates();
                if (collection.size() == 0)
                    package_to_client.setMessage("Коллекция пуста");
                else
                {
                    for (Map.Entry<Long, LabWork> entry : collection.entrySet()) {
                        min_coordinates = collection.get(entry.getKey()).getCoordinates();
                        break;
                    }
                    for (Map.Entry<Long, LabWork> entry : collection.entrySet()) {
                        if (entry.getValue().getCoordinates().compareTo(min_coordinates) < 0 ) {
                            min_coordinates = collection.get(entry.getKey()).getCoordinates();
                        }
                    }
                    StringBuilder message = new StringBuilder();
                    message.append("Минимальные coordinates: ");
                    message.append(min_coordinates.toString());
                    package_to_client.setMessage(message.toString());
                }
                break;
            }
            case "print_field_ascending_author":{
                StringBuilder message = new StringBuilder();
                for (Map.Entry<Long, LabWork> entry : collection.entrySet()) {
                    if (collection.get(entry.getKey()).getAuthor() != null && collection.get(entry.getKey()).getAuthor().equals(package_from_client.getElement().getAuthor()))
                    {
                        message.append(collection.get(entry.getKey()));
                        message.append("/n");
                    }
                }
                break;
            }
        }
        command_validation("save");
        send(package_to_client);
    }
    public static void read_from_cmd() throws IOException {
        scanner = new Scanner(System.in);
        //try{
        if (scanner.hasNext()) {
            command_validation(scanner.nextLine());
        }
        //catch (NoSuchElementException e) {}

    }
    public static void command_validation(String command) throws IOException {
        switch (command){
            case "save":{
                FileOutputStream writer = new FileOutputStream(file_name);
                writer.write(start_of_saving.getBytes());
                int i = 1;
                if (collection.size() == 0)
                    System.out.println("Была записана пустая коллекция");
                else {
                    for (Map.Entry<Long, LabWork> entry : collection.entrySet()) {
                        writer.write(gson.toJson(entry.getValue()).getBytes());
                        if (i != collection.size()) {
                            writer.write(comma.getBytes());
                            i++;
                        }
                    }
                    System.out.println("Было записано " + i + " эллемента(ов) коллекции");
                }
                writer.write(end_of_saving.getBytes());
                writer.close();
                break;
            }
            case "exit":{
                command_validation("save");
                server.close();
                System.exit(0);
                break;
            }
            default:{
                System.out.println("Неизвестная команда");
            }
            case "":{
            }
        }
    }
}