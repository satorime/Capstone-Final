import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class welcomeDialog extends JFrame{
    private JButton getStartedButton;
    private JPanel jpanel;
    private JLabel welcomeMesssage3;
    private JLabel welcomeMesssage1;
    private JLabel welcomeMesssage2;
    private JLabel imageContainer;

    public welcomeDialog() {
        welcomeDialog message = this;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ToDoList taskEase = new ToDoList();
                taskEase.setSize(460, 545);
                taskEase.setTitle("TaskEase - Main");
                taskEase.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                taskEase.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskEase.getWidth()) / 2;
                int y = (screenSize.height - taskEase.getHeight()) / 2;
                taskEase.setLocation(x, y);
            }
        });

        getStartedButton.addActionListener(e -> {
            dispose();
            ToDoList taskEase = new ToDoList();
            taskEase.setSize(450, 540);
            taskEase.setTitle("TaskEase - Main");
            taskEase.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            taskEase.setVisible(true);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - taskEase.getWidth()) / 2;
            int y = (screenSize.height - taskEase.getHeight()) / 2;
            taskEase.setLocation(x, y);
            taskEase.dateChecker(ToDoList.tasks);
        });
    }

    public static void main(String[] args) {
        welcomeDialog welcomeMsg = new welcomeDialog();
        welcomeMsg.setContentPane(welcomeMsg.jpanel);
        welcomeMsg.setSize(520, 400);
        welcomeMsg.setTitle("TaskEase");
        welcomeMsg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomeMsg.setVisible(true);

        // para nig run mo display sa tunga sa screen ang GUI
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - welcomeMsg.getWidth()) / 2;
        int y = (screenSize.height - welcomeMsg.getHeight()) / 2;
        welcomeMsg.setLocation(x, y);
    }
}
