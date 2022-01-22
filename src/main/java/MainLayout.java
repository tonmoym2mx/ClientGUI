import client.ServerConnector;
import client.ServerConnectorListener;
import model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLayout{
    private JPanel panel1;
    private JButton button1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JTextField textField2;

    private ServerConnector serverConnector;
    public MainLayout() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField1.getText();
                Data data1 = new Data(message,"","");
                serverConnector.sendMessage(data1);
            }
        });


    }

    private void sendMessage() {

        textField1.getText();
    }

    public void open(){

        JFrame jFrame = new JFrame();

        jFrame.setContentPane(panel1);
        jFrame.setPreferredSize(new Dimension(500,500));
        jFrame.pack();
        jFrame.setVisible(true);

    }

    public void setServerConnector(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
        this.serverConnector.readingMessage();
        this.serverConnector.setServerConnectorListener(new ServerConnectorListener() {
            @Override
            public void onMessageReceived(Data data) {
                String msg = textArea1.getText() + data.getMessage() + "\n";
                textArea1.setText(msg);

            }
        });
    }

}
