import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientManager implements Runnable{
    private Socket ServerSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private PrintWriter writer;
    private DataInputStream dataInputStream;
    public ClientManager(Socket socket) throws IOException {
        ServerSocket = socket;
        dataInputStream = new DataInputStream(ServerSocket.getInputStream());
        writer= new PrintWriter(ServerSocket.getOutputStream());
    }

    @Override
    public void run() {
        String message = "Hey! ";
        writer.println(message);
        writer.flush();
        try {
            System.out.println(dataInputStream.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(dataInputStream.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
