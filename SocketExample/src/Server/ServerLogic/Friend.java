package Server.ServerLogic;
public class Friend implements java.io.Serializable{
    private String ip;
    private int port;
    private String name;

    /**
     * it is the constructor for friend class to save friend's information
     * @param ip it is the ip for our friend
     * @param port it is the port for our friend
     * @param name it is our friend's name
     */
    public Friend(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }
}
