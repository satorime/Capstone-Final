import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HelpSection extends JFrame{
    private JButton backButton;
    private JPanel jpanel;

    public HelpSection() {
        HelpSection help = this;
        setContentPane(jpanel);

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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HelpSection helpSection = new HelpSection();
                helpSection.setSize(620, 590);
                helpSection.setTitle("TaskEase - Help Section");
                helpSection.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                helpSection.setVisible(true);

                // para nig run mo display sa tunga sa screen ang GUI
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - helpSection.getWidth()) / 2;
                int y = (screenSize.height - helpSection.getHeight()) / 2;
                helpSection.setLocation(x, y);
            }
        });
    }
}
