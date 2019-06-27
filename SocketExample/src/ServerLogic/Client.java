package ServerLogic;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket serverSocket;
    private ArrayList <Friend> friends = new ArrayList<>();

    public Client() {
    }

    public Client(String IP, int port) throws IOException {
        serverSocket = new Socket(IP,port);
        Thread thread = new Thread(new ClientManager(serverSocket));
        thread.start();
    }
    public void addFriend (String name,String ip,int port){
        for (int i = 0; i <friends.size() ; i++) {
            if (friends.get(i).getName()==name){
                friends.get(i).setIp(ip);
                friends.get(i).setPort(port);
                return;
            }
        }
        Friend friend = new Friend(ip,port,name);
        friends.add(friend);
    }
    public ArrayList<Friend> getFriends() {
        return friends;
    }

}