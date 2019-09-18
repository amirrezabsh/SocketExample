package Server;

import Server.ServerLogic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class ServerPanel extends JPanel implements ActionListener {
    private JPanel serverPanel;
    private JFrame jFrame;
    private int counter = 0;
    private String title;
    private String message;
    private String[] messageParts;
    private int buttonIndex;
    private File musicFile;
    private JLabel label;
    private JButton friendButton;
    private JButton addFriend;
    private Friend friend;
    private ClientMessage clientMessage = new ClientMessage();
    private Thread serverThread;
    private SocketChannel socketChannel;

    /**
     * it is a constructor to create a panel and we get
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    // inja avalin paneli ke mibini sakhte mishe ya hamon panel asli
    public ServerPanel() throws IOException, ClassNotFoundException, InterruptedException {
        serverPanel = new JPanel();
        serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.Y_AXIS));
        serverPanel.setBounds(40, 80, 200, 200);
        serverPanel.setBackground(Color.gray);
        // ina baraye teste
        jFrame = new JFrame();
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
        jFrame.revalidate();
    }
//ina baraye teste
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Server.ServerPanel serverPanel = new Server.ServerPanel();
    }

    @Override
    // inja action listener add friend sakhte mishe ke baes mishe ye panel dg baraye vared kardan etelaat friend baz beshe
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFriend) {
            createFrame();
        }
    }
// hamin frame baraye vared kardan etelaat friend
    public void createFrame() {
        jFrame.dispose();
        JFrame friendInfoFrame = new JFrame("Enter your friend's information");
        friendInfoFrame.setSize(400, 500);
        friendInfoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        friendInfoFrame.setBackground(Color.GREEN);
        friendInfoFrame.setVisible(true);
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
            // inja baraye hamon frame vared kardan etelaat friend e ke ino baraye in zdm ke vaghti enter zadi string hayi ke vared kardi ye shey besazn az class friend ke etelaat har friend ro zakhire mikone
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    boolean isSame = false;
                    friend = new Friend(name.getText(), Integer.parseInt(port.getText()), ip.getText());
                    if (name.getText().length() != 0 && ip.getText().length() != 0 && port.getText().length() != 0)
                        friendInfoFrame.dispose();
                    else {
                        System.out.println("You didn't put any data");
                        return;
                    }
                    JButton buttonName = new JButton("Connect to "+name.getText());
                    serverPanel.add(buttonName);
                    serverPanel.revalidate();
                    serverPanel.repaint();
                    jFrame.setVisible(true);
                    // in baraye ine ke be friend connect she va method getMessage run mishe ke befhmim chi dare play mikone yaro
                    buttonName.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                createAskframe();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        };
        port.addKeyListener(enterAction);
        ip.addKeyListener(enterAction);
        name.addKeyListener(enterAction);
    }
// baraye hamon frame ke az yaro miporse chi kar mikhay bokoni hala ke connect shodi
    public void createAskframe() throws IOException, ClassNotFoundException {
        JFrame jFrame = new JFrame("Choose an option:");
        jFrame.setSize(400, 500);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setBackground(Color.GREEN);
        JPanel jPanel = new JPanel();
        jFrame.add(jPanel);
        jPanel.setBounds(40, 80, 200, 200);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        ClientMessage clientMessage = new ClientMessage();
        System.out.println(friend.getPort());
        SocketChannel socketChannel = clientMessage.createServerSocketChannel(friend.getPort());
        //inja bayad ServerMessage ejra she
        message = clientMessage.getMessage(socketChannel);
        friendButton = new JButton(message);
        messageParts = message.split(":");
        // baraye ine ke age khasti ahang ro begiri az yaro
        friendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == friendButton) {
                    ClientFile clientFile = new ClientFile();
                    SocketChannel socketChannel1 = clientFile.createServerSocketChannel(friend.getPort());

                    // inja bayad ServerFile ejra she
                    counter++;
                    int port = friend.getPort()+counter;
                    friend.setPort(port);
                    clientFile.setCounter(counter);
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
        //baraye mogheyi ke nemikhay ahang ro begiri
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
