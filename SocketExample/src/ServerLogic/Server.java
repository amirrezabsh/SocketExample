package ServerLogic;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Server {
    private static ServerSocket mserver;
    static Socket client;
    private Thread thread;
    private ArrayList <String> sharedMusicList = new ArrayList<>();
    public Server() throws IOException {
        mserver= new ServerSocket(9090);
        client=mserver.accept();
        ServerManager serverManager=new ServerManager(client);
        thread = new Thread(serverManager);
        Server nioClient = new Server();
        SocketChannel socketChannel = nioClient.createChannel();
//        nioClient.sendFile(socketChannel);

    }

    public SocketChannel createChannel() {

        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress("192.168.1.9", 9090);
            socketChannel.connect(socketAddress);
            System.out.println("Connected.");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketChannel;
    }


    public void sendFile(SocketChannel socketChannel,String title) throws IOException {
        int index=-1;
        for (int i = 0; i <sharedMusicList.size() ; i++) {
            File song = new File(sharedMusicList.get(i));
            FileInputStream file = new FileInputStream(sharedMusicList.get(i));
            int size = (int)song.length();
            file.skip(size - 128);
            byte[] last128 = new byte[128];
            file.read(last128);
            String id3 = new String(last128);
            if (title.equals(id3.substring(3,32))){
                index=i;
                break;
            }
        }
        if (index==-1){
            System.out.println("There is no music with this title in your shared list!");
            return;
        }
        RandomAccessFile aFile = null;
        try {
            File file = new File(sharedMusicList.get(index));
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
