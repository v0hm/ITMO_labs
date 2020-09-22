import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        int serverPort = 1111;
        byte b[] = {0,1,2,3,4,5,6,7,8,9};
        try(Socket socket = new Socket("localhost", serverPort)){
            OutputStream output = socket.getOutputStream();
            output.write(b);
            InputStream input = socket.getInputStream();
            input.read(b);
            Arrays.stream(b).forEach(System.out.println());
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
