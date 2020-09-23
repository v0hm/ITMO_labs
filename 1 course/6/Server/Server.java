import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {

    static int serverPort = 1111;
    static ServerSocket server;
    static Socket client;

    static {
        try {
            server = new ServerSocket(serverPort);
            System.out.println("Server is up");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Package message_from_server;
    static boolean client_is_connect = false;
    static boolean in_work = true;

    public static void main(String[] args) throws InterruptedException {

        try {
            while (in_work){
                if (client_is_connect){
                    message_from_server = receive();
                    if(message_from_server != null){
                        System.out.println(message_from_server.toString());
                    }
                }
                else{
                    client = server.accept();
                    client_is_connect = true;
                    System.out.println("Client connected");
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("3");
        }
    }
    //TODO Модуль чтения запроса.
    public static Package receive() throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
        if (input.available()>0){
            System.out.println(input.available());
            Package receive_message;
            receive_message = (Package) input.readObject();
            input.close();
            return receive_message;
        }
        else{
            input.close();
            return null;
        }
    }
    //TODO Модуль отправки ответов клиенту.
    public static void send(Package object_to_send) throws IOException{
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        output.writeObject(object_to_send);
        output.close();
    }
    //TODO Модуль приёма подключений.
    public static void connection(){

    }
    //TODO логирование
}
