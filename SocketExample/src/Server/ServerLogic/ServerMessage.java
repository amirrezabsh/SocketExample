package Server.ServerLogic;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class ServerMessage {
    private static ServerSocket mserver;
    private static SocketChannel socketChannel;
    static Socket client;
    private static Thread thread;
    private int counter=0;
    private ArrayList <String> sharedMusicList = new ArrayList<>();
    // in bayad bad az in ke zadi be friend connect she ejra she

//    public static void main(String[] args) throws IOException {
//        ServerMessage server = new ServerMessage();
//        socketChannel = server.createChannel("127.0.0.1",9090);
//        server.sendMessage(socketChannel,"Amir","Marshmello-One-Thing-Right-(Ft-Kane-Brown).mp3");
//    }

    public Thread getThread() {
        return thread;
    }

    public void sendMessage (SocketChannel sc,String name,String title) throws IOException {
        Charset charset = Charset.forName("ISO-8859-1");
        CharBuffer c = CharBuffer.wrap(name+":"+title);
        ByteBuffer b = charset.encode(c);
        b.compact();
        b.flip();
        int a = 0;
        while (b.hasRemaining())
        {
            a += sc.write(b);
        }
        sc.close();
    }
    public SocketChannel createChannel(String ipAddress,int port) {

        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
            socketChannel.connect(socketAddress);
            System.out.println("Connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socketChannel;
    }





}
