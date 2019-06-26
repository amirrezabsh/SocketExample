package ServerLogic;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable{
    private Socket ServerSocket;
    private PrintWriter writer;
    private DataInputStream dataInputStream;
    private ArrayList <String> sharedList = new ArrayList<>();
    private ArrayList <File> sharedListFile = new ArrayList<>();
    public ClientManager(Socket socket) throws IOException {
        ServerSocket = socket;
        dataInputStream = new DataInputStream(ServerSocket.getInputStream());
        writer= new PrintWriter(ServerSocket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            writer.println(sharedList.size());
            writer.flush();
            for (int i = 0; i < sharedList.size(); i++) {
                writer.println(sharedList.get(i));
                writer.flush();
            }
        }
    }

    public byte[] fileToByte (File file) throws IOException {
        FileInputStream f = new FileInputStream(file);
        byte [] fileToByte = new byte[(int) file.length()];
        f.read(fileToByte);
        return fileToByte;
    }
}
