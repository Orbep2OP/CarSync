package employeeacess;

import model.Employee;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.BLACK;

public class EmployeeTableNavigation {
    private JFrame frame;
    private JTable table;
    private EmployeeTableModel tableModel;
    private JButton prevButton;
    private JButton nextButton;
    private JLabel pageInfoLabel;
    private int currentPage = 1;
    private int rowsPerPage = 30;
    private DataSource dataSource = new DataSource();
    private final Color GREEN = new Color(0, 100, 0);
    private final Color RED = new Color(130, 0, 0);
    private final Color WHITE = new Color(255, 255, 255);


    private List<Employee> data;

    public EmployeeTableNavigation(int rowsPerPage, JFrame originalFrame, List<Employee> employeeList) {

        originalFrame.setVisible(false);

        this.data = employeeList;
        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new EmployeeTableModel(new String[]{"NIF", "Name", "Birth Date", "Address", "Email", "Access Level"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Employee Search");
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

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setSize(1200, 600);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataSource.close();
            }
        });

        updateTable();
    }

    public EmployeeTableNavigation(int rowsPerPage, JFrame originalFrame, List<Employee> employeeList, String admin) {

        originalFrame.setVisible(false);

        this.data = employeeList;
        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new EmployeeTableModel(new String[]{"NIF", "Name", "Birth Date", "Address", "Email", "Access Level", "Deactivated"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Employee Search");
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

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setSize(1200, 600);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataSource.close();
            }
        });

        updateTable();
    }


    public EmployeeTableNavigation(int rowsPerPage, JFrame originalFrame) {
       originalFrame.setVisible(false);

        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new EmployeeTableModel(new String[]{"NIF", "Name", "Birth Date", "Address", "Email", "Access Level"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Employee Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        data = dataSource.queryEmployees();

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

    private void updateTable() {
        tableModel.updateData(currentPage, rowsPerPage);
        pageInfoLabel.setText(String.format("Page %d of %d", currentPage, getTotalPages()));
        updateButtonState();
    }

    private void updateButtonState() {
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < getTotalPages());
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) data.size() / rowsPerPage);
    }


    class EmployeeTableModel extends AbstractTableModel {
        private final String[] columnNames;
        private List<Employee> employeesRow;

        public EmployeeTableModel(String[] columnNames) {
            employeesRow = new ArrayList<>();
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return employeesRow.size();
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
            Employee employee = employeesRow.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> employee.getNif();
                case 1 -> employee.getName();
                case 2 -> employee.getBirht_date();
                case 3 -> employee.getAddress();
                case 4 -> employee.getEmail();
                case 5 -> employee.getAccess_level();
                case 6 -> employee.isDeactivated() ? "Yes" : "No";
                default -> null;
            };
        }

        private void updateData(int currentPage, int rowsPerPage) {
            employeesRow.clear();
            int start = (currentPage - 1) * rowsPerPage;


            int end = Math.min(start + rowsPerPage, data.size());
            for (int i = start; i < end; i++) {

                employeesRow.add(data.get(i));
            }
            fireTableDataChanged();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeeTableNavigation(1, new JFrame());
            }
        });
    }
}