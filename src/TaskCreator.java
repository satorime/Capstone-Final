import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class TaskCreator extends JFrame {
    private static ToDoList.YourTaskClass task;
    private JPanel jpanel;
    private JTextField taskTitleField;
    private JComboBox daysBox;
    private JComboBox monthsBox;
    private JComboBox yearsBox;
    private JButton createTaskButton;
    private JLabel taskCreatorHeader;
    private JLabel taskTitle;
    private JLabel completionDay;
    private JButton clearAllButton;
    private JLabel taskDescription;
    private JButton cancelButton;
    private JTextArea taskDescriptionArea;
    private JTextArea displayArea;

    public TaskCreator(ToDoList toDoList) {
        TaskCreator create = this;
        setContentPane(jpanel);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        Border compoundBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        taskTitleField.setBorder(compoundBorder);
        taskDescriptionArea.setBorder(compoundBorder);
        displayArea.setBorder(compoundBorder);

        createTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedDay = Integer.parseInt(Objects.requireNonNull(daysBox.getSelectedItem()).toString());
                    int selectedYear = Integer.parseInt(Objects.requireNonNull(yearsBox.getSelectedItem()).toString());
                    int selectedMonth = monthsBox.getSelectedIndex() + 1;  // Add 1 to get the correct month
                    String taskDate = String.format("%02d - %02d - %d", selectedDay, selectedMonth, selectedYear);
                    String taskTitleText = taskTitleField.getText().trim();
                    String taskDescriptionText = taskDescriptionArea.getText().trim();

                    if (taskTitleText.isEmpty() && taskDescriptionText.isEmpty()) {
                        JOptionPane.showMessageDialog(create, "Please enter a task title and description.");
                    } else if (taskTitleText.isEmpty()) {
                        JOptionPane.showMessageDialog(create, "Please enter a task title.");
                    } else if (taskDescriptionText.isEmpty()) {
                        JOptionPane.showMessageDialog(create, "Please enter task description.");
                    } else {
                        task = new ToDoList.YourTaskClass(taskTitleText, taskDescriptionText, taskDate);
                        saveTaskToFile(task);
                        ToDoList.tasks.add(task);

                        // Make sure this method adds the task to the ToDoList display
                        toDoList.updateTaskDisplay(taskTitleText, taskDate);
                    }
                    dispose();
                    ToDoList taskEase = new ToDoList();
                    taskEase.setSize(650, 750);
                    taskEase.setTitle("TaskEase");
                    taskEase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    taskEase.setVisible(true);

                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int x = (screenSize.width - taskEase.getWidth()) / 2;
                    int y = (screenSize.height - taskEase.getHeight()) / 2;
                    taskEase.setLocation(x, y);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(create, "Please enter all necessary information.");
                }
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMemory();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMemory();
                dispose();

                ToDoList taskEase = new ToDoList();
                taskEase.setSize(650, 750);
                taskEase.setTitle("TaskEase");
                taskEase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                taskEase.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskEase.getWidth()) / 2;
                int y = (screenSize.height - taskEase.getHeight()) / 2;
                taskEase.setLocation(x, y);
            }
        });
    }

    public static ToDoList.YourTaskClass getTask() {
        return task;
    }

    public static void setTask(ToDoList.YourTaskClass task) {
        TaskCreator.task = task;
    }

    private void saveTaskToFile(ToDoList.YourTaskClass task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt", true))) {
            writer.write(task.getTaskName() + "," + task.getTaskDescription() + "," + task.getTaskDate());
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving task to file.");
        }
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
                ToDoList taskEase = new ToDoList();
                TaskCreator createTask = new TaskCreator(taskEase);
                createTask.setSize(500, 650);
                createTask.setTitle("TaskEase");
                createTask.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                createTask.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - createTask.getWidth()) / 2;
                int y = (screenSize.height - createTask.getHeight()) / 2;
                createTask.setLocation(x, y);
            }
        });
    }
}
