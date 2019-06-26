import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;

import static java.awt.image.ImageObserver.ABORT;

public class ServerPanel extends JComponent implements Accessible {

    public static void main(String[] args) {
        //ServerPanel serverPanel = new ServerPanel();
       JPanel serverPanel= new JPanel();
//        serverPanel.setVisible(true);
        serverPanel.setLayout(new BoxLayout(serverPanel,BoxLayout.Y_AXIS));
        serverPanel.setBounds(40,80,200,200);
        serverPanel.setBackground(Color.gray);
        JFrame jFrame = new JFrame();
//        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(serverPanel,BorderLayout.CENTER);
        jFrame.setVisible(true);
        JLabel jLabel = new JLabel();
        jLabel.setText("fuck");
        JLabel jLabel1 = new JLabel();
        jLabel1.setText("suck you");
        serverPanel.add(jLabel);
        serverPanel.add(jLabel1); 

    }
}
