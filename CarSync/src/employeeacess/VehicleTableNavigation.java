package employeeacess;

import model.Vehicle;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.BLACK;

public class VehicleTableNavigation {
    private JFrame frame;
    private JTable table;
    private VehicleTableModel tableModel;
    private JButton prevButton;
    private JButton nextButton;
    private JLabel pageInfoLabel;
    private int currentPage = 1;
    private int rowsPerPage = 30;
    private DataSource dataSource = new DataSource();
    private final Color GREEN = new Color(0, 100, 0);
    private final Color RED = new Color(130, 0, 0);
    private final Color WHITE = new Color(255, 255, 255);


    private java.util.List<Vehicle> data;

    public VehicleTableNavigation(int rowsPerPage, JFrame originalFrame, java.util.List<Vehicle> vehicleList) {
        originalFrame.setVisible(false);
        this.data = vehicleList;
        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new VehicleTableModel(new String[]{"Plate", "NIF", "VIN", "Brand",
                "Model", "Color", "Category", "Registration Date"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Vehicle Search");
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
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            frame.dispose();
            dataSource.close();
            System.exit(0);
        });

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

    public VehicleTableNavigation(int rowsPerPage, JFrame originalFrame, java.util.List<Vehicle> vehicleList, String admin) {
        originalFrame.setVisible(false);
        this.data = vehicleList;
        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new VehicleTableModel(new String[]{"Plate", "NIF", "VIN", "Brand",
                "Model", "Color", "Category", "Registration Date", "Deactivated"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Vehicle Search");
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
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            frame.dispose();
            dataSource.close();
            System.exit(0);
        });

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


    public VehicleTableNavigation(int rowsPerPage, JFrame originalFrame) {
        originalFrame.setVisible(false);

        if (rowsPerPage > 0) {
            this.rowsPerPage = rowsPerPage;
        }

        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        tableModel = new VehicleTableModel(new String[]{"NIF", "Name", "Birth Date", "Address", "License number", "License Category",
                "Registration Date", "Expiration Date"});

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame = new JFrame("Vehicle Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        data = dataSource.queryVehicles();

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
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            frame.dispose();
            dataSource.close();
            System.exit(0);
        });

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

        frame.setSize(1200, 600);
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


    class VehicleTableModel extends AbstractTableModel {
        private final String[] columnNames;
        private List<Vehicle> vehiclesRow;

        public VehicleTableModel(String[] columnNames) {
            vehiclesRow = new ArrayList<>();
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return vehiclesRow.size();
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
            Vehicle vehicle = vehiclesRow.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> vehicle.getPlate();
                case 1 -> vehicle.getNif();
                case 2 -> vehicle.getVin();
                case 3 -> vehicle.getBrand();
                case 4 -> vehicle.getModel();
                case 5 -> vehicle.getColor();
                case 6 -> vehicle.getCategory();
                case 7 -> vehicle.getregistrationDate();
                case 8 -> vehicle.isDeactivated() ? "Yes" : "No";
                default -> null;
            };
        }

        private void updateData(int currentPage, int rowsPerPage) {
            vehiclesRow.clear();
            int start = (currentPage - 1) * rowsPerPage;


            int end = Math.min(start + rowsPerPage, data.size());
            for (int i = start; i < end; i++) {

                vehiclesRow.add(data.get(i));
            }
            fireTableDataChanged();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VehicleTableNavigation(1, new JFrame());
            }
        });
    }
}