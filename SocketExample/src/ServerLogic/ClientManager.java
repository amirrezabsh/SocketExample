package ServerLogic;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable{
    private Socket serverSocket;
    private String title;
    private PrintWriter writer;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private InputStream inputStream;
    private BufferedOutputStream bufferedOutputStream;
    private DataInputStream dataInputStream;
    private ArrayList <String> sharedList = new ArrayList<>();
    private ArrayList <File> sharedListFile = new ArrayList<>();
    public ClientManager(Socket socket) throws IOException {
        serverSocket = socket;
        inputStream = serverSocket.getInputStream();
        dataInputStream = new DataInputStream(serverSocket.getInputStream());
        writer= new PrintWriter(serverSocket.getOutputStream());
    }

    @Override
    public void run() {
        writer.println(title);
        writer.flush();
        try {
            byte[] myByteArray=new byte[1024] ;
            fileOutputStream= new FileOutputStream(title+".mp3");
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int bytesRead = inputStream.read(myByteArray,0,myByteArray.length);
            bufferedOutputStream.write(myByteArray,0,bytesRead);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] fileToByte (File file) throws IOException {
        FileInputStream f = new FileInputStream(file);
        byte [] fileToByte = new byte[(int) file.length()];
        f.read(fileToByte);
        return fileToByte;
    }
}
