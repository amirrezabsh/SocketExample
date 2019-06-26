import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket serverSocket;
    private ArrayList<String> IPList ;
    private ArrayList<Integer> PortList ;
    private ArrayList <Friend> friends = new ArrayList<>();

    public Client() {
    }

    public Client(String IP, int port) throws IOException {
        serverSocket = new Socket(IP,port);
        Thread thread = new Thread(new ClientManager(serverSocket));
        thread.start();
    }
    public void addServer (){
        if (friends.size()==0){
            System.out.println("Your friends list is empty");
            return;
        }
        IPList=new ArrayList<>();
        PortList=new ArrayList<>();
        for (int i = 0; i <friends.size() ; i++) {
                IPList.add(friends.get(i).getIp());
                PortList.add(friends.get(i).getPort());
        }
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

    public ArrayList<String> getIPList() {
        return IPList;
    }

    public ArrayList<Integer> getPortList() {
        return PortList;
    }

    public static void main(String[] args) throws IOException {

    }
}