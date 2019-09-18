package Server.ServerLogic;

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


    /**
     * this method gets a string which is made of a name and a title
     * @param sc it is the channel which we use to get the string from server
     * @return it returns the message which we get from server
     * @throws IOException it is thrown if anything happens in connection
     */
    // baraye gerftn inke che kasi dare che chizi play mikone
    public String getMessage(SocketChannel sc) throws IOException {
        Charset charset = Charset.forName("ISO-8859-1");
        ByteBuffer b = ByteBuffer.allocate(32);
        int a = sc.read(b);
        b.flip();
        CharBuffer c = charset.decode(b);
        String message = c.toString();
        sc.close();
        if (sc.isOpen() || sc.isConnected() || sc.isRegistered()) {
            System.out.println("fuck");
        }

        return message;
    }

    /**
     * this method creates a channel to get a message from server
     * @param port it is the port of our server
     * @return it returns the socket channel for getting message through it
     */
// baraye sakhtan kanal ertebati
    public SocketChannel createServerSocketChannel(int port) {

        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        InetSocketAddress inetSocketAddress = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
//            serverSocketChannel.configureBlocking(null);
            inetSocketAddress = new InetSocketAddress(port);
            serverSocketChannel.socket().bind(inetSocketAddress);
            socketChannel = serverSocketChannel.accept();
            System.out.println("Connection established...." + socketChannel.getRemoteAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return socketChannel;
    }


}