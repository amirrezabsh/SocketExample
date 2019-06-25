
public class Client {

    public static void main(String[] args) {

        Connection w1 = new Connection("localhost", 2346);
        w1.start();

        Connection w2 = new Connection("localhost", 2346);
        w2.start();

        Connection w3 = new Connection("localhost", 2347);
        w3.start();

        w1.sendMessage("Hello ");
        w2.sendMessage("Coffee ");
        w1.sendMessage("world!");
        w2.sendMessage("break!");
        w3.sendMessage("Beer!");

        w1.terminate();
        w2.terminate();
        w3.terminate();
    }
}