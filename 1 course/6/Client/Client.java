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
    public static void main(String[] args) throws InterruptedException {

        int serverPort = 1111;
        boolean isFile = false;

        try(Socket socket = new Socket("localhost", serverPort)){
            System.out.println("connected to server");
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            Package test_in = new Package();
            test_in.setCommand("GG_WP");
            Package test_out = new Package();
            output.writeObject(test_in);
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            test_out = (Package) input.readObject();
            System.out.println(test_out.getCommand());
        }
        catch (UnknownHostException e) {
            System.out.println("1");;
        } catch (IOException e) {
            System.out.println("2");;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
