import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ServerSocket mserver;
    static Socket client;
    public Server(int port) throws IOException {
        mserver= new ServerSocket(port);
        while (true) {
            client = mserver.accept();
            System.out.println(" New client connected");
            Thread thread = new Thread(new Connection(client));
            thread.start();
        }
    }




    public static void main(String[] args) throws IOException {
        ArrayList <Server> ServerList = new ArrayList<>();


    }
}
