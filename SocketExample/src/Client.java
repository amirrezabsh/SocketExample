import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket serverSocket;
    public Client(String IP,int port) throws IOException {
        serverSocket = new Socket(IP,port);
        Thread thread = new Thread(new ClientManager(serverSocket));
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        ArrayList <String> IPList = new ArrayList<>();
        ArrayList <Integer> PortList = new ArrayList<>();
        IPList.add("127.0.0.1");
        IPList.add("127.0.0.1");
        PortList.add(9090);
        PortList.add(9091);
        for (int i = 0; i <IPList.size() ; i++) {
            new Client(IPList.get(i),PortList.get(i));
        }
    }
}