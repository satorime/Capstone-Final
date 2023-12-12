import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class ToDoList extends JFrame {
    private JPanel jpanel;
    private JButton addTaskButton;
    private JButton deleteTaskButton;
    private JButton helpButton;
    private JButton searchButton;
    private JPanel appName_Header;
    private JButton openTaskButton;
    JList<String> displayTaskTitle;
    private JList<String> displayTaskDate;
    private JPanel taskInfoDisplay;
    private JPanel optionPanel;
    private JLabel taskTitle;
    private JLabel dateCreated;
    private final DefaultListModel<String> taskTitleListModel;
    private final DefaultListModel<String> taskDateListModel;
    public static ArrayList<YourTaskClass> tasks = new ArrayList<YourTaskClass>();
    private static final String FILE_PATH = "tasks.txt";

    public ToDoList() {
        setContentPane(jpanel);

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
                taskCreatorFrame.setSize(480, 470);
                taskCreatorFrame.setTitle("TaskEase - Task Creator");
                taskCreatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                        } else{
                            break;
                        }
                    }

                    bfw = new BufferedWriter(new FileWriter("tasks.txt"));

                    for (String s:temp) {
                        if(temp.indexOf(s) == selectedIndex){
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
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                HelpSection helpSection = new HelpSection();
                helpSection.setSize(620, 590);
                helpSection.setTitle("TaskEase - Help Section");
                helpSection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                helpSection.setVisible(true);

                // para nig run mo display sa tunga sa screen ang GUI
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - helpSection.getWidth()) / 2;
                int y = (screenSize.height - helpSection.getHeight()) / 2;
                helpSection.setLocation(x, y);
            }
        });

        openTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = displayTaskTitle.getSelectedIndex();

                if (selectedIndex != -1) {
                    YourTaskClass selectedTask = tasks.get(selectedIndex);

                    // Create an instance of TaskOpener
                    TaskOpener taskOpener = new TaskOpener(selectedTask, ToDoList.this);
                    taskOpener.setSize(480, 470);
                    taskOpener.setTitle("TaskEase - Task Opener");
                    taskOpener.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    taskOpener.setVisible(true);

                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int x = (screenSize.width - taskOpener.getWidth()) / 2;
                    int y = (screenSize.height - taskOpener.getHeight()) / 2;
                    taskOpener.setLocation(x, y);

                    dispose();
                }else {
                    JOptionPane.showMessageDialog(ToDoList.this, "Please select a task to open.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        loadTasksFromFile();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaskSearch taskSearch = new TaskSearch(ToDoList.this);
                taskSearch.setSize(530, 240);
                taskSearch.setTitle("TaskEase - Search Section");
                taskSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                taskSearch.setVisible(true);

                // para nig run mo display sa tunga sa screen ang GUI
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskSearch.getWidth()) / 2;
                int y = (screenSize.height - taskSearch.getHeight()) / 2;
                taskSearch.setLocation(x, y);
            }
        });
    }

    protected void saveTasksToFile(List<YourTaskClass> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (YourTaskClass task : tasks) {
                String desc = task.getTaskDescription();
                desc = desc.replace("\n", ";");

//                System.out.println(desc);
                writer.write(task.getTaskName() + "|" + desc + "|" + task.getTaskDate() );
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks to file.");
        }
    }

    private void loadTasksFromFile() {
        tasks.clear();
        taskTitleListModel.clear();
        taskDateListModel.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length >= 2) {
                    YourTaskClass task = getYourTaskClass(parts);
                    tasks.add(task);
                }
            }

            Collections.sort(tasks);

            for (YourTaskClass t : tasks) {
                taskTitleListModel.addElement(t.getTaskName());
                taskDateListModel.addElement(t.getTaskDate());
            }

            repaint();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks from file.");
        }
    }

    private static YourTaskClass getYourTaskClass(String[] parts) {
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
        task.setIntTaskDate();  // make sure ra if na-call ba ni nga method
        return task;
    }

    protected void updateTaskDisplay(String title, String date) {
        // Add mga task sa list models
        taskTitleListModel.addElement(title);
        taskDateListModel.addElement(date);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
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

    public static class YourTaskClass implements Comparable<YourTaskClass> {
        private String taskName;
        private String taskDescription;
        private String taskDate;
        private int intTaskDate;

        public YourTaskClass(String taskName, String taskDescription, String taskDate) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.taskDate = taskDate;
        }
        public void setIntTaskDate() {
            try {
                String[] date = taskDate.split(" - ");
                int day = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);

                intTaskDate = (year * 10000) + (month * 100) + day;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        public String getTaskName() {
            return taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public String getTaskDate() {
            return taskDate;
        }

        @Override
        public String toString() {
            return taskName;
        }

        public int compareTo(YourTaskClass o) {
            return Integer.compare(this.getIntTaskDate(), o.getIntTaskDate());
        }

        public int getIntTaskDate() {
            return intTaskDate;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public void setTaskDate(String taskDate) {
            this.taskDate = taskDate;
            setIntTaskDate();
        }
    }
}