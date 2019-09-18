package Server.ServerLogic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientFile {
    private int counter;
    // baraye sakhtan kanal ertebati
    public SocketChannel createServerSocketChannel(int port) {
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
//            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress((port+1)));
            socketChannel = serverSocketChannel.accept();
            System.out.println("Connection established...." + socketChannel.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketChannel;
    }
// baraye gereftn file

    public void readFileFromSocket(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("MusicNumber"+counter+".mp3", "rw");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileChannel fileChannel = aFile.getChannel();
            while (socketChannel.read(buffer) > 0) {
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
            }
            Thread.sleep(1000);
            fileChannel.close();
            System.out.println("End of file reached..Closing channel");
            socketChannel.close();
            if (socketChannel.isOpen() || socketChannel.isConnected() || socketChannel.isRegistered()){
                System.out.println("fuck");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
