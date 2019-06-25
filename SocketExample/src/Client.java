import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket serverSocket;
    public Client(String IP,int port) throws IOException {
        serverSocket = new Socket(IP,port);
        Thread thread = new Thread(new ClientManager(serverSocket));
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        new Client("127.0.0.1",9090);
    }
}