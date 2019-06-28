import ServerLogic.ClientFile;
import ServerLogic.ClientMessage;
import ServerLogic.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ServerPanel extends JPanel implements ActionListener {
    private JPanel serverPanel;
    private int counter =0;
    private String title;
    private String message;
    private String [] messageParts;
    private int buttonIndex;
    private File musicFile;
    private JLabel label;
//    private Server server = new Server();
    private JButton friendButton;
    private JButton addFriend;
    private Friend friend;
    private ClientMessage clientMessage = new ClientMessage();
//    private ArrayList<Client> clientList = new ArrayList<>();
//    private Server friendServ/er;
//    private ArrayList<JButton> friendActibvity = new ArrayList<>();
    private Thread serverThread;
    private SocketChannel socketChannel;
//    private ArrayList<ClientManager> clientManagerList = new ArrayList<>();

    public ServerPanel() throws IOException, ClassNotFoundException, InterruptedException {
        serverPanel = new JPanel();
        serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.Y_AXIS));
        serverPanel.setBounds(40, 80, 200, 200);
        serverPanel.setBackground(Color.gray);
        JFrame jFrame = new JFrame();
        jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.add(serverPanel);
        jFrame.setVisible(true);
        label = new JLabel();
        label.setText("Friends Activity");
        serverPanel.add(label);
        addFriend = new JButton("Add a Friend");
        addFriend.addActionListener(this);
        serverPanel.add(addFriend);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ServerPanel serverPanel = new ServerPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFriend) {
            createFrame();
        }
    }

    public void createFrame() {
        JFrame friendInfoFrame = new JFrame("Enter your friend's information");
        friendInfoFrame.setSize(400, 500);
        friendInfoFrame.setVisible(true);
        friendInfoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        friendInfoFrame.setBackground(Color.GREEN);
        JPanel friendInfoPanel = new JPanel();
        friendInfoFrame.add(friendInfoPanel);
        friendInfoPanel.setBounds(40, 80, 200, 200);
        friendInfoPanel.setLayout(new BoxLayout(friendInfoPanel, BoxLayout.Y_AXIS));
        JLabel namelablel = new JLabel("Enter name:");
        TextArea name = new TextArea();
        JLabel ipLabel = new JLabel("Enter IP:");
        TextArea ip = new TextArea();
        JLabel portLabel = new JLabel("Enter port:");
        TextArea port = new TextArea();
        friendInfoPanel.add(namelablel);
        friendInfoPanel.add(name);
        friendInfoPanel.add(ipLabel);
        friendInfoPanel.add(ip);
        friendInfoPanel.add(portLabel);
        friendInfoPanel.add(port);
        KeyAdapter enterAction = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    friend= new Friend(name.getText(),Integer.parseInt(port.getText()),ip.getText());
                    if (name.getText().length() != 0 && ip.getText().length() != 0 && port.getText().length() != 0)
                        friendInfoFrame.dispose();
                    try {
//                        socketChannel = client.createServerSocketChannel();
                        createAskframe();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    //                        message=client.getMessage();


//                    friendActibvity.add(new JButton());
//                        serverList.add(new Server(client.getFriends().get(counter).getPort()));
//                        clientList.add(new Client(client.getFriends().get(counter).getIp(), client.getFriends().get(counter).getPort()));
//                    clientManagerList.add(clientList.get(counter).getClientManager());
//                    serverList.get(counter).getThread().start();
//                    clientList.get(counter).getThread().start();
//                    friendActibvity.get(counter).setText(clientManagerList.get(counter).getServerActivity());
//                    friendActibvity.get(counter).setText("fuck you");
//                    serverPanel.add(friendActibvity.get(counter));
//                    buttonIndex = counter;
//                    counter++;
//                    friendActibvity.get(counter).addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            title = clientManagerList.get(buttonIndex).getTitle();
//                            musicFile = new File("C:\\Users\\ASUS\\Documents\\GitHub\\SocketExample\\Musics\\" + title + ".mp3");
//                        }
//                    });
                }
            }
        };
        port.addKeyListener(enterAction);
        ip.addKeyListener(enterAction);
        name.addKeyListener(enterAction);
    }
    public void createAskframe () throws IOException, ClassNotFoundException {
        JFrame jFrame = new JFrame("Choose an option:");
        jFrame.setSize(400, 500);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setBackground(Color.GREEN);
        JPanel jPanel=new JPanel();
        jFrame.add(jPanel);
        jPanel.setBounds(40, 80, 200, 200);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        ClientMessage clientMessage = new ClientMessage();
        SocketChannel socketChannel = clientMessage.createServerSocketChannel();
        message=clientMessage.getMessage(socketChannel);
        friendButton = new JButton(message);
        messageParts=message.split(":");
        friendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==friendButton){
                    ClientFile clientFile = new ClientFile();
                    SocketChannel socketChannel1 = clientFile.createServerSocketChannel();
                    try {
                        clientFile.readFileFromSocket(socketChannel1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    jFrame.dispose();
                }
            }
        });
        JButton cancelButton = new JButton("I don't want this music");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
        jPanel.add(cancelButton);
        jPanel.add(friendButton);
    }
    public File getMusicFile() {
        return musicFile;
    }
}
