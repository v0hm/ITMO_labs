import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            InputStream input = client.getInputStream();
            input.read(b);
            System.out.println(b);
            OutputStream output = client.getOutputStream();
            output.write(b);
        }
        catch (IOException e) {
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
