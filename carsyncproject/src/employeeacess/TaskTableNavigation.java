package employeeacess;

import model.Task;
import model.TaskManagment;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static java.awt.Color.BLACK;

public class TaskTableNavigation {
    private JFrame frame;
    private JTable table;
    private TaskTableModel tableModel;
    private JButton prevButton;
    private JButton nextButton;
    private JLabel pageInfoLabel;
    private int currentPage = 1;
    private int rowsPerPage = 30;
    private DataSource dataSource = new DataSource();
    private final Color GREEN = new Color(0, 100, 0);
    private final Color RED = new Color(130, 0, 0);
    private final Color WHITE = new Color(255, 255, 255);

    private java.util.List<Task> data;

    //Idea is have a table with the next task that the employee can do
    //This class also returns a String[] with the TicketID's of the tasks in the table
    //The String[] will be used in the JComboxBox in the GUI and if the user selects a ticket
    //thought the JComboxBox the user will be taken to the page where the task can be completed
    //then the user only has to submit because the page will be already filled with the data


    public TaskTableNavigation(JFrame originalFrame, int accessLevel) {

        int rowsPerPage = 30;
        originalFrame.setVisible(false);
        this.data = new TaskManagment().getTaskList(accessLevel);

        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new TaskTableModel(new String[]{"Task ID", "Type", "Status", "Date", "Info"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableColumn column = table.getColumnModel().getColumn(4);
        column.setPreferredWidth(750);
        TableColumn column1 = table.getColumnModel().getColumn(2);
        column.setPreferredWidth(160);
        TableColumn column2 = table.getColumnModel().getColumn(0);
        column2.setPreferredWidth(80);

        frame = new JFrame("Tasks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        prevButton.setBackground(GREEN);
        prevButton.setForeground(WHITE);
        nextButton.setBackground(GREEN);
        nextButton.setForeground(WHITE);

        pageInfoLabel = new JLabel();

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateTable();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentPage < getTotalPages()) {
                currentPage++;
                updateTable();
            }
        });

        JButton backButton = new JButton("Back");
//        JButton exitButton = new JButton("Exit");
//        exitButton.setBackground(RED);
//        exitButton.setForeground(Color.WHITE);
//        exitButton.addActionListener(e -> {
//            frame.dispose();
//            dataSource.close();
//            System.exit(0);
//        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            originalFrame.setVisible(true);
            frame.dispose();
            frame.setVisible(false);
        });

        JPanel navPanel = new JPanel();
//        navPanel.add(exitButton);
        navPanel.add(prevButton);
        navPanel.add(pageInfoLabel);
        navPanel.add(nextButton);
        navPanel.add(backButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(navPanel, BorderLayout.SOUTH);

//        frame.setSize(1200, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataSource.close();
            }
        });

        updateTable();
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) data.size() / rowsPerPage);
    }

    private void updateTable() {
        tableModel.updateData(currentPage, rowsPerPage);
        pageInfoLabel.setText(String.format("Page %d of %d", currentPage, getTotalPages()));
        updateButtonState();
    }

    private void updateButtonState() {
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < getTotalPages());
    }

    class TaskTableModel extends AbstractTableModel {
        private final String[] columnNames;
        private java.util.List<Task> taskRows;

        public TaskTableModel(String[] columnNames) {
            this.columnNames = columnNames;
            taskRows = new ArrayList<>();
        }

        @Override
        public int getRowCount() {
            return taskRows.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Task task = taskRows.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> task.getTaskID();
                case 1 -> task.getTaskType().toString();
                case 2 -> task.getTaskStatus();
                case 3 -> task.getTaskDate();
                case 4 -> task.getTaskInfo();
                default -> null;
            };
        }

        private void updateData(int currentPage, int rowsPerPage) {
            taskRows.clear();
            int start = (currentPage - 1) * rowsPerPage;


            int end = Math.min(start + rowsPerPage, data.size());
            for (int i = start; i < end; i++) {

                taskRows.add(data.get(i));
            }
            fireTableDataChanged();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskTableNavigation(new JFrame(), 2);
            }
        });
    }

}
