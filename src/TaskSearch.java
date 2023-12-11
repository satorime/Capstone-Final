import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskSearch extends ToDoList {
    private JPanel jpanel;
    private JTextField taskTitleInput;
    private JButton searchButton;

    public TaskSearch(ToDoList parentToDoList) {

        taskTitleInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (taskTitleInput.getText().equals(" Enter a task title...")) {
                    taskTitleInput.setText("");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        taskTitleInput.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (taskTitleInput.getText().equals(" Enter a task title...")) {
                    taskTitleInput.setText("");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        setContentPane(jpanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTitle = taskTitleInput.getText().trim();
                int index = findTaskIndexByTitle(searchTitle);

                if (index != -1) {
                    parentToDoList.displayTaskTitle.setSelectedIndex(index);
                    dispose();
                } else {
                    // Task not found
                    JOptionPane.showMessageDialog(null, "Task not found.");
                }
            }
        });
    }

    private int findTaskIndexByTitle(String searchTitle) {
        for (int i = 0; i < ToDoList.tasks.size(); i++) {
            YourTaskClass task = ToDoList.tasks.get(i);
            if (task.getTaskName().equalsIgnoreCase(searchTitle)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                ToDoList taskEase = new ToDoList();
                TaskSearch taskSearch = new TaskSearch(taskEase);
                taskSearch.setSize(530, 240);
                taskSearch.setTitle("TaskEase - Search Section");
                taskSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                taskSearch.setVisible(true);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - taskSearch.getWidth()) / 2;
                int y = (screenSize.height - taskSearch.getHeight()) / 2;
                taskSearch.setLocation(x, y);
            }
        });
    }
}
