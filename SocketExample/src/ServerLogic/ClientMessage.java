package ServerLogic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class ClientMessage {
    private Socket serverSocket;
    private ArrayList<Friend> friends = new ArrayList<>();
    //    private Path savePath = FileSystems.getDefault().getPath("C:\\Users\\ASUS\\Documents\\GitHub\\SocketExample\\Server saved data\\", "friendsInfo.ser");
    private ClientManager clientManager;
    private Thread thread;

    public ClientMessage() throws IOException, ClassNotFoundException {
//        Client nioServer = new Client();
//        SocketChannel socketChannel = nioServer.createServerSocketChannel();
//        nioServer.readFileFromSocket(socketChannel);
    }

    public Thread getThread() {
        return thread;
    }

    public String getMessage(SocketChannel sc) throws IOException {
        Charset charset = Charset.forName("ISO-8859-1");
        ByteBuffer b = ByteBuffer.allocate(32);
        //b.flip();//Don't flip the ByteBuffer here because it sets the position to 0 and limit to 0 also. Hence no read.
        int a = sc.read(b);
        b.flip();//sets the Position to 0 and limit to the number of bytes to be read.
        CharBuffer c = charset.decode(b);
        String message = c.toString();
        //c.flip();//Don't flip the ChharBuffer. Because it is setting the position to zero and limit to previous position i.e zero
//        System.out.println("Got " + c);
//        System.out.println("read " + a);
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