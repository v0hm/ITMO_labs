import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        boolean client_is_connect = false;
        byte b[] = new byte[10];
        int serverPort = 1111;
        try (ServerSocket server = new ServerSocket(serverPort)){
            Socket client = server.accept();
            client_is_connect = true;
            System.out.println("Connection accepted");
            InputStream input = client.getInputStream();
            input.read(b);
            for (int j = 0; j < 10; j++) {
                b[j] *= 2;
            }
            OutputStream output = client.getOutputStream();
            output.write(b);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
