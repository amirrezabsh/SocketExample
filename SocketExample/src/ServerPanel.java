import ServerLogic.Client;
import ServerLogic.ClientManager;
import ServerLogic.Friend;
import ServerLogic.Server;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

import static java.awt.image.ImageObserver.ABORT;

public class ServerPanel extends JPanel implements ActionListener {
    private JPanel serverPanel;
    private int counter =0;
    private ArrayList <Server> serverList = new ArrayList<>();
    private String title;
    private int buttonIndex;
    private File musicFile;
    private JLabel label;
    private JButton addFriend;
    private Client client = new Client();
    private ArrayList<Client> clientList = new ArrayList<>();
    private Server friendServer;
    private ArrayList<JButton> friendActibvity = new ArrayList<>();
    private Thread serverThread;
    private ArrayList<ClientManager> clientManagerList = new ArrayList<>();

    public ServerPanel() throws IOException, ClassNotFoundException, InterruptedException {
        client.loadFreindsList();
        serverPanel = new JPanel();
        serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.Y_AXIS));
        serverPanel.setBounds(40, 80, 200, 200);
        serverPanel.setBackground(Color.gray);
//        JFrame jFrame = new JFrame();
//        jFrame.setSize(400, 400);
//        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        jFrame.setLayout(null);
//        jFrame.add(serverPanel);
//        jFrame.setVisible(true);
        label = new JLabel();
        label.setText("Friends Activity");
        serverPanel.add(label);
        addFriend = new JButton("Add a Friend");
        addFriend.addActionListener(this);
        serverPanel.add(addFriend);
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        ServerPanel serverPanel = new ServerPanel();
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFriend) {
            createFrame();
        }
    }

    public void createFrame() {
        JFrame friendInformation = new JFrame("Enter your friend's information");
        friendInformation.setSize(400, 500);
        friendInformation.setVisible(true);
        friendInformation.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        friendInformation.setBackground(Color.GREEN);
        JPanel friendInfoPanel = new JPanel();
        friendInformation.add(friendInfoPanel);
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
                    client.addFriend(name.getText(), ip.getText(), Integer.parseInt(port.getText()));
                    if (name.getText().length() != 0 && ip.getText().length() != 0 && port.getText().length() != 0)
                        friendInformation.dispose();
                    friendActibvity.add(new JButton());
//                        serverList.add(new Server(client.getFriends().get(counter).getPort()));
//                        clientList.add(new Client(client.getFriends().get(counter).getIp(), client.getFriends().get(counter).getPort()));
                    clientManagerList.add(clientList.get(counter).getClientManager());
                    serverList.get(counter).getThread().start();
                    clientList.get(counter).getThread().start();
                    friendActibvity.get(counter).setText(clientManagerList.get(counter).getServerActivity());
                    friendActibvity.get(counter).setText("fuck you");
                    serverPanel.add(friendActibvity.get(counter));
                    buttonIndex = counter;
                    counter++;
                    friendActibvity.get(counter).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            title = clientManagerList.get(buttonIndex).getTitle();
                            musicFile = new File("C:\\Users\\ASUS\\Documents\\GitHub\\SocketExample\\Musics\\" + title + ".mp3");
                        }
                    });
                }
            }
        };
        port.addKeyListener(enterAction);
        ip.addKeyListener(enterAction);
        name.addKeyListener(enterAction);
    }

    public File getMusicFile() {
        return musicFile;
    }
}
