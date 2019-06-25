import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Connection implements Runnable {

    private String host;
    private int port;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private PrintWriter writer;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket serverSocket;
    private volatile boolean running = true;

    public Connection(Socket socket) throws IOException {
        this.serverSocket = socket;
        inputStream= serverSocket.getInputStream();
        outputStream = serverSocket.getOutputStream();
        dataInputStream = new DataInputStream(inputStream);
        writer = new PrintWriter(outputStream);
    }
    @Override
    public synchronized void run() {
        try {
            String message = dataInputStream.readLine();
            writer.println(message + "Hello buddy");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {

            try {
                this.wait();
            } catch (InterruptedException e) {
                terminate();
            }
        }
    }

    public void terminate() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
    public synchronized void getMessage(){

    }
}