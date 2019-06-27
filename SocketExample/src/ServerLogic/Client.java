package ServerLogic;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;


public class Client {
    private Socket serverSocket;
    private ArrayList<Friend> friends = new ArrayList<>();
    private Path savePath = FileSystems.getDefault().getPath("C:\\Users\\ASUS\\Documents\\GitHub\\SocketExample\\Server saved data\\", "friendsInfo.ser");
    private ClientManager clientManager;
    private Thread thread;

    public Client () throws IOException, ClassNotFoundException {
        Client nioServer = new Client();
        SocketChannel socketChannel = nioServer.createServerSocketChannel();
        nioServer.readFileFromSocket(socketChannel);
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

    /**
     * Reads the bytes from socket and writes to file
     *
     * @param socketChannel
     */
    public void readFileFromSocket(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("Marshmello-One-Thing-Right-(Ft-Kane-Brown).mp3", "rw");
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void addFriend (String name,String ip,int port){
        boolean isSame = false;
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getName().equals(name)) {
                friends.get(i).setIp(ip);
                friends.get(i).setPort(port);
                isSame = true;
            }
        }
        if (isSame != true) {
            Friend friend = new Friend(ip, port, name);
            friends.add(friend);
        }
        Serialization.serialize(friends, savePath);
    }

    public ClientManager getClientManager () {
        return clientManager;
    }

    public void loadFreindsList () throws IOException, ClassNotFoundException {
        friends.clear();
        Object obj = Serialization.deserialize(savePath);
        if (obj instanceof ArrayList) {
            if ((((ArrayList) obj).get(0) instanceof Friend)) {
                ArrayList<Friend> loadedFriendsList = (ArrayList<Friend>) obj;
                for (int i = 0; i < loadedFriendsList.size(); i++) {
                    friends.add(loadedFriendsList.get(i));
                }
            }
        }
    }

    public ArrayList<Friend> getFriends () {
        return friends;
    }

    public Thread getThread () {
        return thread;
    }
}