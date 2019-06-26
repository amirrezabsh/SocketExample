import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable{
    private Socket ServerSocket;
    private PrintWriter writer;
    private DataInputStream dataInputStream;
    private ArrayList <String> sharedList = new ArrayList<>();
    private String title;
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
    public File addMusic () throws IOException {
        JFileChooser f=new JFileChooser();
        f. showSaveDialog (null) ;
        String path=f.getSelectedFile().getAbsolutePath ();
        File song = new File(path);
        for (int i = 0; i <sharedListFile.size() ; i++) {
            if (sharedListFile.get(i)==song){
                return song;
            }
        }
        sharedListFile.add(song);
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
    public byte[] fileToByte (File file) throws IOException {
        FileInputStream f = new FileInputStream(file);
        byte [] fileToByte = new byte[(int) file.length()];
        f.read(fileToByte);
        return fileToByte;
    }
}
