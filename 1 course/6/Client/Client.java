import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



public class Client{

    static Socket socket;
    static ObjectInputStream input;
    static ObjectOutputStream output;
    static Scanner scanner;

    static Gson gson = new Gson();
    static InputStream defInput = System.in;

    static boolean client_is_connect = false;
    static boolean auto_connect = true;
    static boolean in_work = true;
    static boolean isFile = false;
    static int serverPort = 1111;
    static int max_number_of_auto_connect_attempts = 3;
    static int number_of_auto_connect_attempts;
    static String file_name;
    static String file_name_for_script;

    public static void main(String[] args) throws IOException {

        try{
            auto_connect();
            scanner = new Scanner(defInput);
            while (!client_is_connect){
                if(scanner.hasNext()) {
                    if ((scanner.next()).equals("connect")){
                        connect();
                    }
                }
            }
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Client initialized");
            while (in_work){
                read_from_cmd();
            }
        }
        catch (UnknownHostException e) {
            System.out.println("1");
        } catch (ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            socket.close();
            scanner.close();
        }
    }
    public static void read_from_cmd() throws IOException, ClassNotFoundException, InterruptedException {
        String command;
        while (scanner.hasNext()) {
            if(isFile && !scanner.hasNext()){
                isFile = false;
                System.setIn(defInput);
                scanner = new Scanner(System.in);
            }
            command = scanner.next();
            command_validation(command);
        }
        return;
    }
    public static void command_validation(String command) throws IOException, ClassNotFoundException, InterruptedException {
        Package command_object = new Package();
        switch(command) {
            case "help":{
                System.out.println(" help : справка по доступным командам \n info : информация о коллекции \n show : вывод всех элементов коллекции \n insert key {element} : добавить новый элемент с заданным ключом \n update id {element} : обновить значение элемента коллекции по id \n remove_key key : удалить элемент из коллекции по ключу \n clear : очистить коллекцию \n save : сохранить коллекцию в файл \n execute_script file_name : считать и исполнить скрипт из указанного файла \n exit : завершить программу (без сохранения в файл) \n remove_lower {element} : удаление из коллекции всех элементов, меньших, чем заданный \n replace_if_greater key {element} : замена значения по ключу, если новое значение больше старого \n remove_lower_key key : удаление из коллекции всех элементов, ключ которых меньше, чем заданный \n remove_all_by_difficulty difficulty : удаление из коллекции всех элементов, значение поля difficulty которого эквивалентно заданному \n min_by_coordinates : вывод любого объектв из коллекции, значение поля coordinates которого является минимальным \n print_field_ascending_author author : вывод значения поля author в порядке возрастания");
                break;
            }
            case "info":
            case "clear":
            case "min_by_coordinates":
            case "show":{
                command_object.setCommand(command);
                send(command_object);
                System.out.println(receive());
                break;
            }
            case "replace_if_greater":
            case "update":
            case "insert":{
                command_object.setCommand(command);
                if (scanner.hasNextLong())
                {
                    command_object.setKey(scanner.nextLong());
                    try{
                        String element;
                        element = scanner.nextLine();
                        command_object.setElement(gson.fromJson(element, LabWork.class));
                        send(command_object);
                        System.out.println(receive());
                    }
                    catch (java.lang.NullPointerException e) {
                        System.out.println("Не введён element");
                    }
                    catch (IllegalStateException e){
                        System.out.println("Неправильно введён element");
                    }
                }else{
                    System.out.println("Неправильно введён key");
                }
                break;
            }
            case "remove_lower_key":
            case "remove_key":{
                command_object.setCommand(command);
                if (scanner.hasNextLong()) {
                    command_object.setKey(scanner.nextLong());
                    send(command_object);
                    System.out.println(receive());
                }
                else{
                    System.out.println("Неправильно введён key");
                }
                break;
            }
            case "execute_script": {
                isFile = true;
                try {
                    file_name = scanner.next();
                    if (file_name.equals(file_name_for_script))
                        System.out.println("В файле присутствует рекурсия выполнение сего файла");
                    else {
                        file_name_for_script = file_name;
                        try {
                            FileReader fr = new FileReader(file_name);
                            BufferedReader reader = new BufferedReader(fr);
                            String line = reader.readLine();
                            do {
                                InputStream Input_for_System_in = new ByteArrayInputStream(line.getBytes("UTF-8"));
                                System.setIn(Input_for_System_in);
                                scanner = new Scanner(System.in);
                                command_validation(scanner.next());
                                System.setIn(System.in);
                                line = reader.readLine();
                            } while (line != null);
                        } catch (java.io.FileNotFoundException e)
                        {
                            System.out.println("Неверно введён файл");
                        }
                        break;
                    }
                }
                catch (java.lang.NullPointerException e){
                    System.out.println("Не введён file_name");
                }
                file_name_for_script = null;
                break;
            }
            case "remove_lower": {
                command_object.setCommand(command);
                try{
                    String element;
                    element = scanner.nextLine();
                    command_object.setElement(gson.fromJson(element, LabWork.class));
                    send(command_object);
                    System.out.println(receive());
                }
                catch (java.lang.NullPointerException e) {
                    System.out.println("Не введён element");
                }
                catch (IllegalStateException e){
                    System.out.println("Неправильно введён element");
                }
                break;
            }
            case "remove_all_by_difficulty":{
                command_object.setCommand(command);
                if (scanner.hasNext()){
                    command_object.setDifficulty(scanner.next());
                    send(command_object);
                    System.out.println(receive());
                }
                else{
                    System.out.println("Не введён difficulty");
                }
            }
            case "print_field_ascending_author":{
                try {
                    command_object.setCommand(command);
                    String autor = scanner.nextLine();
                    LabWork laba_temp = new LabWork();
                    laba_temp.setAuthor(gson.fromJson(autor, Person.class));
                    command_object.setElement(laba_temp);
                }
                catch (java.lang.NullPointerException e) {
                    System.out.println("Не введён author");
                }
                catch (IllegalStateException e){
                    System.out.println("Неправильно введён author");
                }
                break;
            }
            case "exit":{
                in_work = false;
                break;
            }
            default:{
                System.out.println("Неизвестная команда");
                break;
            }
            case "":{
            }
        }
        return;
    }
    public static void send(Package package_to_send) throws IOException, InterruptedException, ClassNotFoundException {
        try{
            output.writeObject(package_to_send);
            System.out.println("Package sended");
        }catch (Exception e){
            System.out.println("Server_isn't_available");
            command_validation("exit");
        }
        return;
    }
    public static String receive() throws IOException, ClassNotFoundException {
        Package receive_message;
        receive_message = (Package) input.readObject();
        System.out.println("Package received");
        return receive_message.getMessage();
    }
    public static void auto_connect() throws InterruptedException {
        number_of_auto_connect_attempts = 0;
        connect();
        while (auto_connect) {
            if (!client_is_connect){
                System.out.println("Try to connect in 10 sec");
                Thread.sleep(10000);
                connect();
                number_of_auto_connect_attempts++;
                if (number_of_auto_connect_attempts == max_number_of_auto_connect_attempts) {
                    auto_connect = false;
                }
            }
            else{
                auto_connect = false;
            }
        }
        System.out.println("Auto connect disabled");
    }
    public static boolean connect(){
        try{
            socket = new Socket("localhost", serverPort);
            client_is_connect = true;
            System.out.println("connected to server");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Server isn't available");
            return false;
        }
        finally {
            return true;
        }
    }
}