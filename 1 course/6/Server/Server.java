import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
public class Server {
    public static void main(String[] args) throws InterruptedException {
        boolean client_is_connect = false;
        byte b[] = new byte[1];
        int serverPort = 1111;
        try (ServerSocket server = new ServerSocket(serverPort)){
            Socket client = server.accept();
            client_is_connect = true;
            System.out.println("Connection accepted");
            Package test = new Package();
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            test = (Package) input.readObject();
            System.out.println(test.getCommand());
            test.setCommand("GG_WP_too_ez");
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(test);
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("3");
        }
    }
    //TODO Модуль чтения запроса.
    public static void receive(){

    }
    //TODO Модуль отправки ответов клиенту.
    public static void send(){

    }
    //TODO Модуль приёма подключений.
    public static void connection(){

    }
    //TODO логирование
}
