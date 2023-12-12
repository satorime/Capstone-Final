import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TaskOpener extends JFrame {
    private JTextField taskTitleField;
    private JComboBox daysBox;
    private JComboBox monthsBox;
    private JComboBox yearsBox;
    private JTextArea taskDescriptionArea;
    private JButton saveButton;
    private JButton btDeleteTask;
    private JButton closeButton;
    private JPanel jpanel;
    private JLabel taskDescription;

    public TaskOpener(ToDoList.YourTaskClass task, ToDoList parentFrame) {
        setContentPane(jpanel);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        Border compoundBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        taskTitleField.setBorder(compoundBorder);
        taskDescriptionArea.setBorder(compoundBorder);

        taskTitleField.setText(task.getTaskName());
        taskDescriptionArea.setText(task.getTaskDescription());

        String[] dateParts = task.getTaskDate().split(" - ");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        daysBox.setSelectedIndex(day);
        monthsBox.setSelectedIndex(month);
        yearsBox.setSelectedItem(String.valueOf(year));

        // wrong pani tarong ra nako ni later
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ToDoList taskEase = new ToDoList();
                taskEase.setSize(460, 545);
                taskEase.setTitle("TaskEase - Main");
                taskEase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                taskEase.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskEase.getWidth()) / 2;
                int y = (screenSize.height - taskEase.getHeight()) / 2;
                taskEase.setLocation(x, y);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task.setTaskName(taskTitleField.getText());
                task.setTaskDescription(taskDescriptionArea.getText());
                String taskDate = String.format("%02d - %02d - %04d", daysBox.getSelectedIndex() + 1, monthsBox.getSelectedIndex() + 1, Integer.parseInt((String) yearsBox.getSelectedItem()));
                task.setTaskDate(taskDate);

                parentFrame.saveTasksToFile(ToDoList.tasks);

                dispose();
                ToDoList taskEase = new ToDoList();
                taskEase.setSize(450, 540);
                taskEase.setTitle("TaskEase - Main");
                taskEase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                taskEase.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskEase.getWidth()) / 2;
                int y = (screenSize.height - taskEase.getHeight()) / 2;
                taskEase.setLocation(x, y);
            }
        });

        btDeleteTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoList.tasks.remove(task);

                parentFrame.saveTasksToFile(ToDoList.tasks);

                dispose();

                ToDoList taskEase = new ToDoList();
                taskEase.setSize(460, 545);
                taskEase.setTitle("TaskEase - Main");
                taskEase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                taskEase.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskEase.getWidth()) / 2;
                int y = (screenSize.height - taskEase.getHeight()) / 2;
                taskEase.setLocation(x, y);
            }
        });


        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMemory();
                dispose();

                ToDoList taskEase = new ToDoList();
                taskEase.setSize(460, 545);
                taskEase.setTitle("TaskEase - Main");
                taskEase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                taskEase.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskEase.getWidth()) / 2;
                int y = (screenSize.height - taskEase.getHeight()) / 2;
                taskEase.setLocation(x, y);
            }
        });
    }

    private void clearMemory() {
        taskTitleField.setText("");
        taskDescriptionArea.setText("");
        daysBox.setSelectedIndex(0);
        monthsBox.setSelectedIndex(0);
        yearsBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ToDoList.YourTaskClass dummyTask = new ToDoList.YourTaskClass("Dummy Task", "Dummy Description", "01 - 01 - 2023");
                ToDoList parentFrame = new ToDoList();

                TaskOpener OpenFile = new TaskOpener(dummyTask, parentFrame);
                OpenFile.setSize(480, 470);
                OpenFile.setTitle("TaskEase - Help Section");
                OpenFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                OpenFile.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - OpenFile.getWidth()) / 2;
                int y = (screenSize.height - OpenFile.getHeight()) / 2;
                OpenFile.setLocation(x, y);
            }
        });
    }
}