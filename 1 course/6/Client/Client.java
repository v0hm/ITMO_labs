import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;


public class Client{

    static Socket socket;
    static ObjectInputStream input;
    static ObjectOutputStream output;
    static Scanner scanner;

    static Gson gson = new Gson();

    static boolean client_is_connect = false;
    static boolean in_work = true;
    static boolean isFile = false;

    public static void main(String[] args) throws IOException {

        int serverPort = 1111;

        try{
            socket = new Socket("localhost", serverPort);
            System.out.println("connected to server");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            scanner = new Scanner(System.in);
            System.out.println("Client initialized");
            while (in_work){
                read_from_cmd();
                System.out.println(receive());
            }
        }
        catch (UnknownHostException e) {
            System.out.println("1");;
        } catch (IOException e) {
            System.out.println("2");;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            socket.close();
            scanner.close();
            input.close();
            output.close();
        }
    }
    //TODO Чтение команд из консоли.
    public static void read_from_cmd() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String command;
        if (scanner.hasNext()){
            command = scanner.next();
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
    public static void send(Package package_to_send) throws IOException {
        output.writeObject(package_to_send);
        System.out.println("Package sended");
    }
    //TODO Обработка ответа от сервера (вывод результата исполнения команды в консоль).
    public static String receive() throws IOException, ClassNotFoundException {
        Package receive_message;
        receive_message = (Package) input.readObject();
        System.out.println("Package received");
        return receive_message.getMessage();
    }
}