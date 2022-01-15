import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLayout{
    private JPanel panel1;
    private JButton button1;
    private JTextField textField1;

    public MainLayout() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
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
}
