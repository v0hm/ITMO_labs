import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{

    static int serverPort = 1111;
    static Socket socket;
    static Scanner scanner;
    static ObjectInputStream input;
    static ObjectOutputStream output;

    static {
        try {
            socket = new Socket("localhost", serverPort);
            System.out.println("connected to server");
            scanner = new Scanner(System.in);
            System.out.println("scanner is up");
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("input is up");
            output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("client initialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean in_work = true;
    static boolean is_File = false;

    public static void main(String[] args) throws IOException {

        try{
            while (in_work){
                read_from_cmd();
                String message_from_server = receive();
                if(message_from_server != "NULL"){
                    System.out.println(message_from_server);
                }
            }
        }
        catch (UnknownHostException e) {
            System.out.println("1");
        } catch (IOException e) {
            System.out.println("2");
        } catch (ClassNotFoundException e) {
            System.out.println("3");
        } finally {
            socket.close();
            scanner.close();
            input.close();
            output.close();
        }
    }
    //TODO Чтение команд из консоли.
    public static void read_from_cmd() throws IOException {
        String command;
        if (scanner.hasNext()){
            command = scanner.next();
            System.out.println(command);
            command_validation(command);
        }
    }
    //TODO Валидация вводимых данных
    public static void command_validation(String command) throws IOException {
        Package command_object = new Package();
        switch(command) {
            case "help":{
                System.out.println(" help : справка по доступным командам \n info : информация о коллекции \n show : вывод всех элементов коллекции \n insert key {element} : добавить новый элемент с заданным ключом \n update id {element} : обновить значение элемента коллекции по id \n remove_key key : удалить элемент из коллекции по ключу \n clear : очистить коллекцию \n save : сохранить коллекцию в файл \n execute_script file_name : считать и исполнить скрипт из указанного файла \n exit : завершить программу (без сохранения в файл) \n remove_lower {element} : удаление из коллекции всех элементов, меньших, чем заданный \n replace_if_greater key {element} : замена значения по ключу, если новое значение больше старого \n remove_lower_key key : удаление из коллекции всех элементов, ключ которых меньше, чем заданный \n remove_all_by_difficulty difficulty : удаление из коллекции всех элементов, значение поля difficulty которого эквивалентно заданному \n min_by_coordinates : вывод любого объектв из коллекции, значение поля coordinates которого является минимальным \n print_field_ascending_author author : вывод значения поля author в порядке возрастания");
                break;
            }
            case "info":{
                command_object.setCommand(command);
                System.out.println(command_object.getCommand());
                send(command_object);
                break;
            }
            case "exit":{
                in_work = false;
                break;
            }
        }
    }
    //TODO Сериализация введённой команды и её аргументов

    //TODO Отправка полученной команды и её аргументов на сервер
    public static void send(Package object_to_send) throws IOException {
        System.out.println("12");
        output.writeObject(object_to_send);
        System.out.println("13");
        output.close();
        System.out.println("14");
    }
    //TODO Обработка ответа от сервера (вывод результата исполнения команды в консоль).
    public static String receive() throws IOException, ClassNotFoundException {
        if (input.available()>0){
            Package receive_message;
            receive_message = (Package) input.readObject();
            return receive_message.getMessage();
        }
        else{
            return "NULL";
        }
    }
}
