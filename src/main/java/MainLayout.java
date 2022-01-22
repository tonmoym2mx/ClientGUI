import client.ServerConnector;
import client.ServerConnectorListener;

import model.ComboItem;
import model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class MainLayout{
    private JPanel panel1;
    private JButton button1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JComboBox comboBox1;
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
                Data data1 = new Data(message,(String) comboBox1.getSelectedItem() ,serverConnector.getUsername());
                serverConnector.sendMessage(data1);
                String ms = textArea1.getText() +" You: "+ message + "\n";
                textArea1.setText(ms);
                textField1.setText("");
            }
        });
        comboBox1.addItem("All");

    }

    private void sendMessage() {

        textField1.getText();
    }

    public void open(){

        JFrame jFrame = new JFrame();

        jFrame.setContentPane(panel1);
        jFrame.setTitle(serverConnector.getUsername());
        jFrame.setPreferredSize(new Dimension(500,500));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

                if(serverConnector !=null){
                    serverConnector.close();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        jFrame.setVisible(true);

    }

    public void setServerConnector(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
        this.serverConnector.readingMessage();
        this.serverConnector.setServerConnectorListener(new ServerConnectorListener() {
            @Override
            public void onMessageReceived(Data data) {
                if(!data.getFromClient().equals(serverConnector.getUsername())) {
                    String msg = textArea1.getText() + data.getFromClient() + " : " + data.getMessage() + "\n";
                    textArea1.setText(msg);
                }

            }

            @Override
            public void userListUpdate(List<String> userList) {
                System.out.println(userList.toString());
                userList.remove(serverConnector.getUsername());
                comboBox1.removeAllItems();
                comboBox1.addItem("All");
                for(String user:userList){
                    comboBox1.addItem(user);
                }
            }
        });

    }

}
