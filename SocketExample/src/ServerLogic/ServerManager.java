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
            songName = dataInputStream.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < sharedList.size(); i++) {
            try {
                fileInputStream = new FileInputStream(sharedList.get(i));
                int size = (int) sharedList.get(i).length();
                byte[] file = new byte[size];
                fileInputStream.skip(size - 128);
                byte[] last128 = new byte[128];
                fileInputStream.read(last128);
                String id3 = new String(last128);
                this.title = id3.substring(3, 32);
                if (this.title == songName) {
                    fileInputStream.read(file);
                    dataOutputStream.write(file);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public File addMusic() throws IOException {
        JFileChooser f = new JFileChooser();
        f.showSaveDialog(null);
        String path = f.getSelectedFile().getAbsolutePath();
        File song = new File(path);
        for (int i = 0; i < sharedList.size(); i++) {
            if (sharedList.get(i) == song) {
                return song;
            }
        }
        sharedList.add(song);
        return song;
    }

    public ArrayList<File> getSharedList() {
        return sharedList;
    }


}