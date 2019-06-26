package ServerLogic;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class Connection implements Runnable {

    private String host;
    private String title;
    private int port;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private PrintWriter writer;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket clientSocket;
    private int size;
    private ArrayList <File> sharedList = new ArrayList<>();
    private File sharedMusic ;

    public void setSharedMusic(File sharedMusic) {

        this.sharedMusic = sharedMusic;
    }

    public Connection(Socket socket) throws IOException {
        this.clientSocket = socket;
        inputStream= clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        dataInputStream = new DataInputStream(inputStream);
        writer = new PrintWriter(outputStream);
    }
    @Override
    public synchronized void run() {
        while (true) {
            try {
                size = dataInputStream.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < size; i++) {
                try {
                    title = dataInputStream.readLine();
                    if (sharedList.contains(title))
                        continue;
                    sharedList.add(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public File addMusic () throws IOException {
        JFileChooser f=new JFileChooser();
        f. showSaveDialog (null) ;
        String path=f.getSelectedFile().getAbsolutePath ();
        File song = new File(path);
        for (int i = 0; i <sharedList.size() ; i++) {
            if (sharedList.get(i)==song){
                return song;
            }
        }
        sharedList.add(song);
        FileInputStream file = new FileInputStream(path);
        int size = (int)song.length();
        file.skip(size - 128);
        byte[] last128 = new byte[128];
        file.read(last128);
        String id3 = new String(last128);
        this.title = id3.substring(3,32);
        sharedList.add(title);
        file.close();
        return song;
    }
    public ArrayList<File> getSharedList() {
        return sharedList;
    }


}