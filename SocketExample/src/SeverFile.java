import ServerLogic.ServerManager;

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

public class SeverFile {
    private static ServerSocket mserver;
    private static SocketChannel socketChannel;
    static Socket client;
    private static Thread thread;
    private static ServerManager serverManager;
    private int counter=0;
    private ArrayList<String> sharedMusicList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        SeverFile severFile = new SeverFile();
        SocketChannel socketChannel = severFile.createChannel();
        severFile.sendFile(socketChannel);
    }
    public SocketChannel createChannel() throws IOException {

        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress("192.168.43.49", 9091);
            socketChannel.connect(socketAddress);
            System.out.println("Connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketChannel;
    }
    public void sendFile(SocketChannel socketChannel) throws IOException {
        int index=-1;
//        for (int i = 0; i <sharedMusicList.size() ; i++) {
//            File song = new File(sharedMusicList.get(i));
//            FileInputStream file = new FileInputStream(sharedMusicList.get(i));
//            int size = (int)song.length();
//            file.skip(size - 128);
//            byte[] last128 = new byte[128];
//            file.read(last128);
//            String id3 = new String(last128);
//            if (title.equals(id3.substring(3,32))){
//                index=i;
//                break;
//            }
//        }
//        if (index==-1){
//            System.out.println("There is no music with this title in your shared list!");
//            return;
//        }Charset charset = Charset.forName("ISO-8859-1");

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
    public String addMusic() throws IOException {
        JFileChooser f = new JFileChooser();
        f.showSaveDialog(null);
        String path = f.getSelectedFile().getAbsolutePath();
        for (int i = 0; i < sharedMusicList.size(); i++) {
            if (sharedMusicList.get(i).equals(path)) {
                System.out.println("You already have it");
                return null;
            }
        }
        sharedMusicList.add(path);
        return path;
    }
    public void removeMusic(File file) {
        for (int i = 0; i < sharedMusicList.size(); i++) {
            if (sharedMusicList.get(i).equals(file.getAbsolutePath())) {
                sharedMusicList.remove(i);
                return;
            }
        }
    }
}
