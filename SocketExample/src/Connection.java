import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Connection implements Runnable {

    private String host;
    private int port;
    private PrintWriter os;

    private volatile boolean running = false;
    private ConcurrentLinkedQueue<String> queue;

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
        this.queue = new ConcurrentLinkedQueue<String>();
    };

    public void start() {
        try {
            this.os = new PrintWriter(new Socket(host, port).getOutputStream());
        } catch (IOException e) {
            return;
        }

        running = true;
        new Thread(this).start();
    }

    @Override
    public void run() {

        while(running) {
            // send messages in queue
            while(!queue.isEmpty()) {
                os.print(queue.poll());
            }
            // wait to be notified about new messages
            try {
                this.wait();
            } catch (InterruptedException e) {
                terminate();
            }
        }
    }

    public synchronized void sendMessage(String msg) {
        queue.add(msg);
        this.notify();
    }

    public void terminate() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}