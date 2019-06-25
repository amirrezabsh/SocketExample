import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientManager implements Runnable{
    private Socket ServerSocket;
    private PrintWriter writer;
    private DataInputStream dataInputStream;
    public ClientManager(Socket socket) throws IOException {
        ServerSocket = socket;
        dataInputStream = new DataInputStream(ServerSocket.getInputStream());
        writer= new PrintWriter(ServerSocket.getOutputStream());
    }

    @Override
    public void run() {

    }
}
