package ServerLogic;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class ServerMessage {
    private static ServerSocket mserver;
    private static SocketChannel socketChannel;
    static Socket client;
    private static Thread thread;
    private static ServerManager serverManager;
    private int counter=0;
    private ArrayList <String> sharedMusicList = new ArrayList<>();
    public ServerMessage() throws IOException {
//        Server nioClient = new Server();
//        SocketChannel socketChannel = nioClient.createChannel();
//        nioClient.sendFile(socketChannel);

    }

    public static void main(String[] args) throws IOException {
        ServerMessage server = new ServerMessage();
        socketChannel = server.createChannel();
        server.sendMessage(socketChannel,"Amir","Marshmello-One-Thing-Right-(Ft-Kane-Brown).mp3");
    }

    public Thread getThread() {
        return thread;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }
    public void sendMessage (SocketChannel sc,String name,String title) throws IOException {
        Charset charset = Charset.forName("ISO-8859-1");
        CharBuffer c = CharBuffer.wrap(name+":"+title);
//        System.out.println("writing " + c);
        ByteBuffer b = charset.encode(c);
//        System.out.println(new String(b.array()));
        //sc.configureBlocking(true);
        b.compact();
//        System.out.println(b.capacity() + " "+ b.position() + " " + b.limit());
        b.flip();
//        System.out.println(b.capacity() + " "+ b.position() + " " + b.limit());
        int a = 0;
        while (b.hasRemaining())
        {
            a += sc.write(b);
        }
        sc.close();
//        System.out.println("wrote " + a);
    }
    public SocketChannel createChannel() {

        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress("192.168.43.49", 9090);
            socketChannel.connect(socketAddress);
            System.out.println("Connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socketChannel;
    }





}
