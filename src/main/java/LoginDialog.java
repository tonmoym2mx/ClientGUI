import client.ConnectionListener;
import client.ServerConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField a19216801TextField;
    private JTextField a5555TextField;
    private JTextField textField3;



    public LoginDialog() {
        setContentPane(contentPane);
     //   setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String host = a19216801TextField.getText();
                int port = Integer.parseInt(a5555TextField.getText());
                String username = textField3.getText();
                ServerConnector serverConnector = new ServerConnector(host,port,username);

                serverConnector.connect(new ConnectionListener() {
                    @Override
                    public void connected() {
                        MainLayout mainLayout = new MainLayout();
                        mainLayout.setServerConnector(serverConnector);
                        mainLayout.open();
                        onCancel();
                    }

                    @Override
                    public void connectionFailed(String message) {
                        System.out.println(message);
                    }
                });



            }
        });



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LoginDialog dialog = new LoginDialog();
        dialog.setContentPane(dialog.contentPane);
        dialog.setPreferredSize(new Dimension(500,200));
        dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}
