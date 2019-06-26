import ServerLogic.Client;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

import static java.awt.image.ImageObserver.ABORT;

public class ServerPanel extends JPanel implements ActionListener {
    private JPanel serverPanel;
    private JLabel label;
    private JButton addFriend;
    private Client client=new Client();

    public ServerPanel() {
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

    public static void main(String[] args) {
        ServerPanel serverPanel = new ServerPanel();
    }

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
//        friendInfoPanel.setVisible(true);
        friendInfoPanel.setBounds(40, 80, 200, 200);
        friendInfoPanel.setLayout(new BoxLayout(friendInfoPanel, BoxLayout.Y_AXIS));
        TextArea name = new TextArea();
        TextArea ip = new TextArea();
        TextArea port = new TextArea();
        friendInfoPanel.add(name);
        friendInfoPanel.add(ip);
        friendInfoPanel.add(port);
        KeyAdapter enterAction = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    client.addFriend(name.getText(), ip.getText(), Integer.parseInt(port.getText()));
                    if (name.getText().length() != 0 && ip.getText().length() != 0 && port.getText().length() != 0)
                        friendInformation.dispose();
                    System.out.println(client.getFriends().get(0).getName());
                }
            }
        };
        port.addKeyListener(enterAction);
        ip.addKeyListener(enterAction);
        name.addKeyListener(enterAction);
    }

}
