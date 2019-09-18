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
    private int counter = 0;
    private ArrayList<String> sharedMusicList = new ArrayList<>();
    // in bayad bad az in ke zadi be friend connect she ejra she

    public static void main(String[] args) throws IOException {
        ServerMessage server = new ServerMessage();
        server.createChannel("127.0.0.1",9090);
//        socketChannel = server.createChannel("127.0.0.1", 9091);
//        server.sendMessage(socketChannel, "Amir", "Marshmello-One-Thing-Right-(Ft-Kane-Brown).mp3");
    }

    /**
     * this method send the name of the music which is being played by the server also it sends the name of the server
     *
     * @param sc    it is a socket channel for sending so called strings
     * @param name  it is the name of the server which is created
     * @param title it is the name of the song which is played by the server
     * @throws IOException
     */
    public void sendMessage(SocketChannel sc, String name, String title) throws IOException {
        Charset charset = Charset.forName("ISO-8859-1");
        CharBuffer c = CharBuffer.wrap(name + ":" + title);
        ByteBuffer b = charset.encode(c);
        b.compact();
        b.flip();
        int a = 0;
        while (b.hasRemaining()) {
            a += sc.write(b);
        }
        sc.close();
    }

    /**
     * it creates a channel to send the name and the title to the client
     *
     * @param ipAddress it is the ip address which the channel is based on
     * @param port      it is the port which the channel is based on
     * @return it returns the socket channel for sending name and title
     */
    public void createChannel(String ipAddress, int port) {
        while (true) {
            SocketChannel socketChannel = null;
            try {
                socketChannel = SocketChannel.open();
//                socketChannel.configureBlocking(true);
                SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
                System.out.println(port);
                socketChannel.connect(socketAddress);
                if (socketChannel.isConnected()){
                    port++;
                }
                System.out.println("Connected.");
                sendMessage(socketChannel,"Amir","Marshmello-One-Thing-Right-(Ft-Kane-Brown).mp3");
                socketChannel.close();
                socketChannel.finishConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
