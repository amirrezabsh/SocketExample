import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Connection implements Runnable {

    private String host;
    private int port;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private PrintWriter writer;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket clientSocket;
    private int size;
    private String title;
    private volatile boolean running = true;
    private ArrayList <String> sharedList = new ArrayList<>();
    private CountDownLatch latch = new CountDownLatch(1);

    public Connection(Socket socket) throws IOException {
        this.clientSocket = socket;
        inputStream= clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        dataInputStream = new DataInputStream(inputStream);
        writer = new PrintWriter(outputStream);
    }
    @Override
    public synchronized void run() {
        while (true) {
            try {
                size = dataInputStream.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < size; i++) {
                try {
                    title = dataInputStream.readLine();
                    if (sharedList.contains(title))
                        continue;
                    sharedList.add(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public ArrayList<String> getSharedList() {
        return sharedList;
    }

    public void terminate() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
    public synchronized void getMessage(){

    }
    public boolean waitForResponse() throws InterruptedException {
        boolean result = latch.await(10, TimeUnit.SECONDS);
        return result;
        // check result and react correspondingly
    }

    public void notifyOKCommandReceived() {
        latch.countDown();
    }
}