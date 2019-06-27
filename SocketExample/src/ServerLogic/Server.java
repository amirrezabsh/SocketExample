package ServerLogic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket mserver;
    static Socket client;
    private Thread thread;
    public Server(int port) throws IOException {
        mserver= new ServerSocket(port);
        while (true) {
            client = mserver.accept();
            System.out.println(" New client connected");
            thread = new Thread(new ServerManager(client));
        }
    }

    public Thread getThread() {
        return thread;
    }
}
