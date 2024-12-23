import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskManagerGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel homePanel;
    private JPanel taskListPanel;
    private JScrollPane taskScrollPane;
    private JPanel addTaskPanel;
    private List<Task> tasks;

    public TaskManagerGUI() {
        tasks = new ArrayList<>();
        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        setupHomePanel();
        setupTaskListPanel();
        setupAddTaskPanel();
        frame.add(contentPanel);
        cardLayout.show(contentPanel, "Home");
        frame.setVisible(true);
    }

    private void setupHomePanel() {
        homePanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Task Manager", SwingConstants.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        title.setBackground(Color.PINK);
        title.setOpaque(true);
        title.setForeground(Color.PINK.darker());
        homePanel.add(title, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewTasksButton = new JButton("View Tasks");
        viewTasksButton.setForeground(Color.PINK.darker());
        viewTasksButton.setBackground(Color.PINK);
        viewTasksButton.addActionListener(e -> {
            updateTaskList(tasks);
            cardLayout.show(contentPanel, "TaskList");
        });
        buttonPanel.add(viewTasksButton);
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(e -> cardLayout.show(contentPanel, "AddTask"));
        buttonPanel.add(addTaskButton);
        addTaskButton.setForeground(Color.PINK.darker());
        addTaskButton.setBackground(Color.PINK);
        homePanel.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.add(homePanel, "Home");
    }

    private void setupTaskListPanel() {
        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskScrollPane = new JScrollPane(taskListPanel);
        taskScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Tasks", SwingConstants.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        title.setBackground(Color.PINK);
        title.setOpaque(true);
        title.setForeground(Color.PINK.darker());
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(taskScrollPane, BorderLayout.CENTER);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(contentPanel, "Home"));
        mainPanel.add(backButton, BorderLayout.SOUTH);
        backButton.setForeground(Color.PINK.darker());
        backButton.setBackground(Color.PINK);
        JLabel sorting = new JLabel("Sort By:");
        sorting.setBackground(Color.PINK);
        sorting.setOpaque(true);
        sorting.setForeground(Color.PINK.darker());
        String[] sortOptions = {"Default sort", "Sort By Due-Date"};
        JComboBox<String> sortOfTasks = new JComboBox<>(sortOptions);
        sortOfTasks.addActionListener(e -> {
            String selectedOption = (String) sortOfTasks.getSelectedItem();
            if (selectedOption.equals("Sort By Due-Date")) {
                updateTaskList(sortByDate());
            } else {
                updateTaskList(tasks);
            }
        });
        JPanel sortPanel = new JPanel();
        sortPanel.add(sorting);
        sortPanel.add(sortOfTasks);
        mainPanel.add(sortPanel, BorderLayout.NORTH);
        contentPanel.add(mainPanel, "TaskList");
    }
    
    private void updateTaskList(List<Task> tasksToDisplay) {
        taskListPanel.removeAll();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        for (Task task : tasksToDisplay) {
            JPanel taskPanel = new JPanel(new BorderLayout());
            taskPanel.setPreferredSize(new Dimension(400, 50));
            taskPanel.setMaximumSize(new Dimension(400, 50));
            taskPanel.setBorder(BorderFactory.createLineBorder(Color.PINK, 1));
            JLabel taskLabel = new JLabel(task.getTitle());
            taskLabel.setHorizontalAlignment(SwingConstants.LEFT);
            taskPanel.add(taskLabel, BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.PINK);
            JButton viewButton = new JButton("View Task");
            viewButton.addActionListener(e -> viewTask(task));
            viewButton.setBackground(Color.PINK);
            viewButton.setForeground(Color.PINK.darker());
            buttonPanel.add(viewButton);
            JButton editButton = new JButton("Edit");
            editButton.setBackground(Color.PINK);
            editButton.setForeground(Color.PINK.darker());
            editButton.addActionListener(e -> editTask(task));
            buttonPanel.add(editButton);
            JButton deleteButton = new JButton("Delete");
            deleteButton.setBackground(Color.PINK);
            deleteButton.setForeground(Color.PINK.darker());
            deleteButton.addActionListener(e -> {
                tasks.remove(task);
                updateTaskList(tasks);
            });
            buttonPanel.add(deleteButton);
            taskPanel.add(buttonPanel, BorderLayout.EAST);
            taskListPanel.add(taskPanel);
        }
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    private List<Task> sortByDate(){
        List<Task>sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks, Comparator.comparing(Task::getDueDate));
        return sortedTasks;
    }

    private void viewTask(Task task) {
        JPanel viewTaskPanel = new JPanel();
        viewTaskPanel.setLayout(new GridLayout(4, 2));
        viewTaskPanel.add(new JLabel("Title:"));
        viewTaskPanel.add(new JLabel(task.getTitle()));
        viewTaskPanel.add(new JLabel("Description:"));
        viewTaskPanel.add(new JLabel(task.getDescription()));
        viewTaskPanel.add(new JLabel("Due Date:"));
        viewTaskPanel.add(new JLabel(task.getDueDate().toString()));
        viewTaskPanel.add(new JLabel("Status:"));
        String status = task.getTaskCompletedStatus() ? "Completed" : "Not Completed";
        viewTaskPanel.add(new JLabel(status));
        JOptionPane.showMessageDialog(frame, viewTaskPanel, "View Task", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void editTask(Task task) {
        JTextField titleField = new JTextField(task.getTitle(), 20);
        JTextField descriptionField = new JTextField(task.getDescription(), 20);
        JTextField dateField = new JTextField(task.getDueDate().toString(), 10);
        String[] statusOptions = {"Completed", "Not Completed"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        if (task.getTaskCompletedStatus()) {
            statusComboBox.setSelectedIndex(0);
        } else {
            statusComboBox.setSelectedIndex(1);
        }
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(5, 2));
        JLabel title=new JLabel("Title");
        title.setForeground(Color.PINK.darker());
        editPanel.add(title);
        editPanel.add(titleField);
        JLabel description=new JLabel("Description");
        description.setForeground(Color.PINK.darker());
        editPanel.add(description);
        editPanel.add(descriptionField);
        JLabel dueDate=new JLabel("Due Date (YYYY-MM-DD):");
        dueDate.setForeground(Color.PINK.darker());
        editPanel.add(dueDate);
        editPanel.add(dateField);
        JLabel status=new JLabel("Status");
        status.setForeground(Color.PINK.darker());
        editPanel.add(status);
        editPanel.add(statusComboBox);
        int result = JOptionPane.showConfirmDialog(frame, editPanel, "Edit Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            task.setTitle(titleField.getText());
            task.setDescription(descriptionField.getText());
            task.setDueDate(LocalDate.parse(dateField.getText()));
            boolean isCompleted = statusComboBox.getSelectedItem().equals("Completed");
            task.setCompletedStatus(isCompleted);
            updateTaskList(tasks);
        }
    }
    
    private void setupAddTaskPanel() {
        addTaskPanel = new JPanel();
        addTaskPanel.setLayout(new GridLayout(6, 2));
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.PINK.darker());
        JTextField titleField = new JTextField("Title");
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.PINK.darker());
        JTextField descriptionField = new JTextField("Description");
        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        dueDateLabel.setForeground(Color.PINK.darker());
        JTextField dueDateField = new JTextField(LocalDate.now().toString());
        JLabel statusLabel = new JLabel("Status of Task (Completed/Not Completed):");
        statusLabel.setForeground(Color.PINK.darker());
        String[] statusOptions = {"Completed", "Not Completed"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        statusComboBox.setSelectedIndex(1);
        JButton addButton = new JButton("Add Task");
        addButton.setBackground(Color.PINK);
        addButton.setForeground(Color.PINK.darker());
            addButton.addActionListener(e -> {
                String title = titleField.getText();
                String description = descriptionField.getText();
                if(dueDateField.getText().equals("")){
                    JOptionPane.showMessageDialog(frame, "Please fill due-date.");
                }
                else{
                    LocalDate dueDate = LocalDate.parse(dueDateField.getText());
                String status = (String) statusComboBox.getSelectedItem();
                boolean isCompleted = status.equals("Completed");
                tasks.add(new Task(title, description, dueDate, isCompleted));
                titleField.setText("Title");
                descriptionField.setText("Description");
                dueDateField.setText(LocalDate.now().toString());
                statusComboBox.setSelectedIndex(1);
                JOptionPane.showMessageDialog(frame, "Task added successfully!");
                }  
            });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(contentPanel, "Home"));
        backButton.setBackground(Color.PINK);
        backButton.setForeground(Color.PINK.darker());
        addTaskPanel.add(titleLabel);
        addTaskPanel.add(titleField);
        addTaskPanel.add(descriptionLabel);
        addTaskPanel.add(descriptionField);
        addTaskPanel.add(dueDateLabel);
        addTaskPanel.add(dueDateField);
        addTaskPanel.add(statusLabel);
        addTaskPanel.add(statusComboBox);
        addTaskPanel.add(new JLabel());
        addTaskPanel.add(addButton);
        addTaskPanel.add(new JLabel());
        addTaskPanel.add(backButton);
        contentPanel.add(addTaskPanel, "AddTask");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManagerGUI::new);
    }
}