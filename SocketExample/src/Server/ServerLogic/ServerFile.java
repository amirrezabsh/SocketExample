package Server.ServerLogic;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class ServerFile {
    private  ServerSocket mserver;
    private  SocketChannel socketChannel;
    static Socket client;
    private  Thread thread;
    private int counter=0;
    private ArrayList<String> pathsharedMusicList = new ArrayList<>();
  // in bayad vaghti ejra she ke to mizani ahang daryaft she
    // nokte kheyli mohem in ke aval bayad client ro ejra koni ke in toye server panel hast bad server ro va inke aval bayad server message ejra she toye ejraye server ha
//    public static void main(String[] args) throws IOException {
//        SeverFile severFile = new SeverFile();
//        SocketChannel socketChannel = severFile.createChannel("127.0.0.1",9090);
//        severFile.sendFile(socketChannel);
//    }
    //baraye sakhtan rah ertebati
    public SocketChannel createChannel(String ipAddress,int port) throws IOException {

        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(ipAddress, (port+1));
            socketChannel.connect(socketAddress);
            System.out.println("Connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketChannel;
    }
    //baraye ferestadan file ke bayad be jaye in path az pathi estefade koni ke to laptop va to list pathSharedMusicList hast estefade koni albate motanaseb ba on ahangi ke darkhast shode
    public void sendFile(SocketChannel socketChannel) throws IOException {
        int index=-1;
        RandomAccessFile aFile = null;
        try {
            File file = new File("C:\\Users\\ASUS\\Downloads\\Music\\Marshmello-One-Thing-Right-(Ft-Kane-Brown).mp3");
            aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
            Thread.sleep(1000);
            System.out.println("End of file reached..");
            socketChannel.close();
            aFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //baraye add kardan path music ha be yek list baraye estefade az on
    public String addMusic(String pathFile) throws IOException {
        String path = pathFile;
        for (int i = 0; i < pathsharedMusicList.size(); i++) {
            if (pathsharedMusicList.get(i).equals(path)) {
                System.out.println("You already have it");
                return null;
            }
        }
        pathsharedMusicList.add(path);
        return path;
    }
    //baraye pack kardan ye ahang az list shared music
    public void removeMusic(String path) {
        for (int i = 0; i < pathsharedMusicList.size(); i++) {
            if (pathsharedMusicList.get(i).equals(path)) {
                pathsharedMusicList.remove(i);
                return;
            }
        }
    }
}
