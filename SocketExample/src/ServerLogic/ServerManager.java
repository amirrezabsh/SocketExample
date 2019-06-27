package ServerLogic;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class ServerManager implements Runnable {

    private String title;
    private FileInputStream fileInputStream;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private InputStream inputStream;
    private OutputStream outputStream;
    private FileOutputStream fileOutputStream;
    private Socket clientSocket;
    private String songName;
    private ArrayList<File> sharedList = new ArrayList<>();
    private File sharedMusic;

    public ServerManager(Socket socket) throws IOException {
        this.clientSocket = socket;
        inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        dataInputStream = new DataInputStream(inputStream);
        dataOutputStream = new DataOutputStream(outputStream);
    }

    @Override
    public synchronized void run() {
        try {
            dataOutputStream.writeChars("Amir");
            dataOutputStream.writeChars(title);
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

    public ArrayList<File> getSharedList() {
        return sharedList;
    }

    public void setSharedMusic(File sharedMusic) {
        this.sharedMusic = sharedMusic;
    }
}