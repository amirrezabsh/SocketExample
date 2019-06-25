import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static ServerSocket mserver;
    static Socket client;
    public Server(int port) throws IOException {
        mserver= new ServerSocket(port);
        client = mserver.accept();
        System.out.println("Client connected");
        Thread thread = new Thread(new Connection(client));
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        new Server(9090);
        new Server(9091);

    }
}
