import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDoList extends JFrame {
    private JPanel jpanel;
    private JButton addTaskButton;
    private JButton deleteTaskButton;
    private JButton helpButton;
    private JButton searchButton;
    private JPanel appName_Header;
    private JButton openTaskButton;
    private JList<String> displayTaskTitle;
    private JList<String> displayTaskDate;
    private JPanel taskInfoDisplay;
    private JPanel optionPanel;
    private JLabel taskTitle;
    private JLabel dateCreated;

    private final DefaultListModel<String> taskTitleListModel;
    private final DefaultListModel<String> taskDateListModel;
    public static ArrayList<YourTaskClass> tasks = new ArrayList<YourTaskClass>();
    // Specify the full path or use a location where your program has write permissions
    private static final String FILE_PATH = "tasks.txt";

    public ToDoList() {
        setContentPane(jpanel);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        Border compoundBorder = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5));
        displayTaskTitle.setBorder(compoundBorder);
        displayTaskDate.setBorder(compoundBorder);

        // Create DefaultListModel for task titles and dates
        taskTitleListModel = new DefaultListModel<>();
        taskDateListModel = new DefaultListModel<>();

        // Set models to JLists
        displayTaskTitle.setModel(taskTitleListModel);
        displayTaskDate.setModel(taskDateListModel);

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TaskCreator taskCreatorFrame = new TaskCreator(ToDoList.this);
                taskCreatorFrame.setSize(500, 650);
                taskCreatorFrame.setTitle("TaskEase - Task Creator");
                taskCreatorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                taskCreatorFrame.setVisible(true);

                YourTaskClass newTask = TaskCreator.getTask();
                if (newTask != null) {
                    updateTaskDisplay(newTask.getTaskName(), newTask.getTaskDate());
                    //List<YourTaskClass> remainingTasks = getTasksFromGUI();
                    saveTasksToFile(tasks);
                }

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskCreatorFrame.getWidth()) / 2;
                int y = (screenSize.height - taskCreatorFrame.getHeight()) / 2;
                taskCreatorFrame.setLocation(x, y);
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                int selectedIndex = displayTaskTitle.getSelectedIndex();
                if (selectedIndex == -1) {
                    return;
                }
                ArrayList<String> temp = new ArrayList<>();
                String tmp;
                BufferedReader bfr = new BufferedReader(new FileReader(new File("Tasks.txt")));
                BufferedWriter bfw = null;
                while (true) {

                    if (((tmp = bfr.readLine()) != null)) {
                        temp.add(tmp);
                    }
                    else{
                        break;
                    }
                }

                bfw = new BufferedWriter(new FileWriter("tasks.txt"));
                for (String s:temp
                ) {
                    if(temp.indexOf(s)==selectedIndex){
                        continue;
                    }
                        bfw.write((String) s+"\n");

                    }
                    bfw.close();

                    taskTitleListModel.remove(selectedIndex);
                    taskDateListModel.remove(selectedIndex);
            }catch(Exception kk) {
                    throw new RuntimeException(kk);
                }
        }});



        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement help functionality here
            }
        });

        openTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTask();
            }
        });

        // Load tasks from file when the application starts
        loadTasksFromFile();
    }

    private void saveTasksToFile(List<YourTaskClass> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (YourTaskClass task : tasks) {
                String desc = task.getTaskDescription();
                desc = desc.replace("\n", ";");
                System.out.println(desc);
                writer.write(task.getTaskName() + "," + desc + "," + task.getTaskDate() );
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks to file.");
        }
    }

    private void loadTasksFromFile() {
        tasks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String taskName = parts[0];
                    String taskDescription = parts[1];
                    taskDescription = taskDescription.replaceAll(";", "\n");
                    String taskDate;
                    if (parts.length >= 3) {
                        taskDate = parts[2];
                    } else {
                        taskDate = "";
                    }

                    YourTaskClass task = new YourTaskClass(taskName, taskDescription, taskDate);
                    updateTaskDisplay(taskName, taskDate);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks from file.");
        }
    }

    protected void updateTaskDisplay(String title, String date) {
        // Add task to the list models
        taskTitleListModel.addElement(title);
        taskDateListModel.addElement(date);

    }

    private List<YourTaskClass> getTasksFromGUI() {
        List<YourTaskClass> tasks = new ArrayList<>();
        for (int i = 0; i < taskTitleListModel.getSize(); i++) {
            String taskName = taskTitleListModel.getElementAt(i);
            String taskDate = taskDateListModel.getElementAt(i);

            String taskDescription = "";

            tasks.add(new YourTaskClass(taskName, taskDescription, taskDate));
        }
        return tasks;
    }

    private void openTask() {
        int selectedIndex = displayTaskTitle.getSelectedIndex();
        if (selectedIndex != -1) {
            String title = taskTitleListModel.getElementAt(selectedIndex);
            String description = taskDateListModel.getElementAt(selectedIndex);
            JOptionPane.showMessageDialog(this, String.format("Task Title: %s\nTask Description: %s", title, description));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ToDoList taskEase = new ToDoList();
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

    public static class YourTaskClass {
        private String taskName;
        private String taskDescription;
        private String taskDate;

        public YourTaskClass(String taskName, String taskDescription, String taskDate) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.taskDate = taskDate;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public String getTaskDate() {
            return taskDate;
        }

        public void setTaskDate(String taskDate) {
            this.taskDate = taskDate;
        }

        @Override
        public String toString() {
            return taskName;
        }
    }
}
