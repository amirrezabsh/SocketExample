package ServerLogic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class ClientMessage {
    private Socket serverSocket;
    private ArrayList<Friend> friends = new ArrayList<>();
    //    private Path savePath = FileSystems.getDefault().getPath("C:\\Users\\ASUS\\Documents\\GitHub\\SocketExample\\Server saved data\\", "friendsInfo.ser");
    private Thread thread;


    public Thread getThread() {
        return thread;
    }

    public String getMessage(SocketChannel sc) throws IOException {
        Charset charset = Charset.forName("ISO-8859-1");
        ByteBuffer b = ByteBuffer.allocate(32);
        int a = sc.read(b);
        b.flip();
        CharBuffer c = charset.decode(b);
        String message = c.toString();
        sc.close();
        return message;
    }

    public SocketChannel createServerSocketChannel() {

        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9090));
            socketChannel = serverSocketChannel.accept();
            System.out.println("Connection established...." + socketChannel.getRemoteAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return socketChannel;
    }


}