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

public class Client {
    public static void main(String[] args) throws InterruptedException {

        int serverPort = 1111;
        boolean isFile = false;

        try(Socket socket = new Socket("localhost", serverPort)){
            OutputStream output = socket.getOutputStream();
            output.write(b);
            InputStream input = socket.getInputStream();
            input.read(b);
        }
        catch (UnknownHostException e) {
            System.out.println("1");;
        } catch (IOException e) {
            System.out.println("2");;
        }
    }
    //TODO Чтение команд из консоли.
    public static void read_from_cmd(){
        Scanner scanner = new Scanner(System.in);
        String command;
        if (scanner.hasNext()){
            command = scanner.next();
            command_validation(command);
        }
    }
    //TODO Валидация вводимых данных
    public static void command_validation(String command)
    {

    }
    //TODO Сериализация введённой команды и её аргументов
    //TODO Отправка полученной команды и её аргументов на сервер
    public static void send(){

    }
    //TODO Обработка ответа от сервера (вывод результата исполнения команды в консоль).
    public static void receive(){

    }


}
