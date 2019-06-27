package ServerLogic;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public class Client {
    private Socket serverSocket;
    private ArrayList<Friend> friends = new ArrayList<>();
    private Path savePath = FileSystems.getDefault().getPath("C:\\Users\\ASUS\\Documents\\GitHub\\SocketExample\\Server saved data\\", "friendsInfo.ser");
    private ClientManager clientManager;
    private Thread thread;

    public Client() {
    }

    public Client(String IP, int port) throws IOException {
        serverSocket = new Socket(IP, port);
        clientManager=new ClientManager(serverSocket);
        thread = new Thread(clientManager);
    }

    public void addFriend(String name, String ip, int port) {
        boolean isSame = false;
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getName().equals(name)) {
                friends.get(i).setIp(ip);
                friends.get(i).setPort(port);
                isSame = true;
            }
        }
        if (!isSame) {
            Friend friend = new Friend(ip, port, name);
            friends.add(friend);
        }
        Serialization.serialize(friends, savePath);
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void loadFreindsList() throws IOException, ClassNotFoundException {
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

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public Thread getThread() {
        return thread;
    }
}