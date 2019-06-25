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
        client = mserver.accept();
        System.out.println("Client connected");
        Thread thread = new Thread(new Connection(client));
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        ArrayList <Integer> PortList = new ArrayList<>();
        PortList.add(9090);
        PortList.add(9091);
        ArrayList <Server> ServerList = new ArrayList<>();
        for (int i = 0; i < PortList.size(); i++) {
            ServerList.add(new Server(PortList.get(i)));
        }

    }
}
