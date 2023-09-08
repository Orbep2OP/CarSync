package employeeacess;

import model.Customer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.BLACK;

public class CustomerTableNavigation {
    private JFrame frame;
    private JTable table;
    private CustomerTableModel tableModel;
    private JButton prevButton;
    private JButton nextButton;
    private JLabel pageInfoLabel;
    private int currentPage = 1;
    private int rowsPerPage = 30;
    private DataSource dataSource = new DataSource();
    private final Color GREEN = new Color(0, 100, 0);
    private final Color RED = new Color(130, 0, 0);
    private final Color WHITE = new Color(255, 255, 255);


    private List<Customer> data;

    public CustomerTableNavigation(int rowsPerPage, JFrame originalFrame, List<Customer> customerList) {

        originalFrame.setVisible(false);
        this.data = customerList;
        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new CustomerTableModel(new String[]{"NIF", "Name", "Birth Date", "Address", "License Number", "License Category",
                "Registration Date", "Expiration Date", "Email"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Customer Search");
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
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridy = 0;
//        gbc.gridx = 0;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.LINE_START;
//        navPanel.add(exitButton, gbc);
//        gbc.gridx = 1;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.LINE_END;
//        navPanel.add(backButton, gbc);
//        gbc.gridx = 2;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        navPanel.add(prevButton, gbc);
//        gbc.gridx = 3;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        navPanel.add(pageInfoLabel, gbc);
//        gbc.gridx = 4;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        navPanel.add(nextButton, gbc);

//        navPanel.add(exitButton);
        navPanel.add(prevButton);
        navPanel.add(pageInfoLabel);
        navPanel.add(nextButton);
        navPanel.add(backButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(navPanel, BorderLayout.SOUTH);

//        frame.add(navPanel);
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


    public CustomerTableNavigation(int rowsPerPage, JFrame originalFrame, List<Customer> customerList, String admin) {

        originalFrame.setVisible(false);
        this.data = customerList;
        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new CustomerTableModel(new String[]{"NIF", "Name", "Birth Date", "Address", "License Number", "License Category",
                "Registration Date", "Expiration Date", "Email", "Deactivated"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Customer Search");
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
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridy = 0;
//        gbc.gridx = 0;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.LINE_START;
//        navPanel.add(exitButton, gbc);
//        gbc.gridx = 1;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.LINE_END;
//        navPanel.add(backButton, gbc);
//        gbc.gridx = 2;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        navPanel.add(prevButton, gbc);
//        gbc.gridx = 3;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        navPanel.add(pageInfoLabel, gbc);
//        gbc.gridx = 4;
//        gbc.weightx = 0.5;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        navPanel.add(nextButton, gbc);

//        navPanel.add(exitButton);
        navPanel.add(prevButton);
        navPanel.add(pageInfoLabel);
        navPanel.add(nextButton);
        navPanel.add(backButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(navPanel, BorderLayout.SOUTH);

//        frame.add(navPanel);
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
    public CustomerTableNavigation(int rowsPerPage, JFrame originalFrame) {
        originalFrame.setVisible(false);

        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new CustomerTableModel(new String[]{"NIF", "Name", "Birth Date", "Address", "License number", "License Category",
                "Registration Date", "Expiration Date", "Email"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Customer Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        data = dataSource.queryCustomers();

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


    class CustomerTableModel extends AbstractTableModel {
        private final String[] columnNames;
        private List<Customer> customersRow;

        public CustomerTableModel(String[] columnNames) {
            customersRow = new ArrayList<>();
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return customersRow.size();
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
            Customer customer = customersRow.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> customer.getNif();
                case 1 -> customer.getName();
                case 2 -> customer.getBirht_date();
                case 3 -> customer.getAddress();
                case 4 -> customer.getDriverLicenseNum();
                case 5 -> customer.getLicenseType();
                case 6 -> customer.getStartingDate();
                case 7 -> customer.getExpDate();
                case 8 -> customer.getEmail();
                case 9 -> customer.isDeactivated() ? "Yes" : "No";
                default -> null;
            };
        }

        private void updateData(int currentPage, int rowsPerPage) {
            customersRow.clear();
            int start = (currentPage - 1) * rowsPerPage;


            int end = Math.min(start + rowsPerPage, data.size());
            for (int i = start; i < end; i++) {

                customersRow.add(data.get(i));
            }
            fireTableDataChanged();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerTableNavigation(1, new JFrame());
            }
        });
    }
}