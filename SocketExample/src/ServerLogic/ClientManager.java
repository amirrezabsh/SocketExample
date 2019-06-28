package ServerLogic;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {
    private Socket serverSocket;
    private String name;
    private String title;
    private PrintWriter writer;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private InputStream inputStream;
    private BufferedOutputStream bufferedOutputStream;
    private DataInputStream dataInputStream;
    private ArrayList<String> sharedList = new ArrayList<>();
    private ArrayList<File> sharedListFile = new ArrayList<>();

    public ClientManager(Socket socket) throws IOException {
        serverSocket = socket;
        inputStream = serverSocket.getInputStream();
        dataInputStream = new DataInputStream(serverSocket.getInputStream());
        writer = new PrintWriter(serverSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            name = dataInputStream.readLine();
            title = dataInputStream.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
