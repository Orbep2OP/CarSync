package employeeacess;

import clientmenu.WelcomeMenuForm;
import com.toedter.calendar.JDateChooser;
import model.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.awt.Color.*;

public class MenuEmployeeManager extends JFrame implements ValidateInput {

    private Employee employee;
    private DataSource dataSource;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private final Color GREEN = new Color(0, 100, 0);
    private final Color RED = new Color(100, 0, 0);
    private final Color BLUE = new Color(0, 0, 100);
    private Logger logger;


    public MenuEmployeeManager(Employee employee) {
        dataSource = new DataSource();
        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        logger = Logger.getLogger(BackOfficeAdminMenu.class);


        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Employee with name: " + employee.getName() + " and NIF: "
                + employee.getNif() + " initialized the BackOffice Menu");


        JButton insertMenu = new JButton("Registration Menu");
        JButton updateMenu = new JButton("Update Menu");
        JButton taskMenu = new JButton("Task Menu");
        JButton searchMenu = new JButton("Search Menu");
        JButton deactivateMenu = new JButton("Deactivate Menu");
        JButton exitButton = new JButton("Sign Out");

        this.employee = employee;
        mainFrame = new JFrame("CarSync - Back Office");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " logged out");
            dataSource.close();
            mainFrame.dispose();
            new WelcomeMenuForm();
        });


        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel mainLabel = new JLabel("Welcome " + employee.getName());
        mainLabel.setFont(new Font("Arial", Font.BOLD, 80));
        mainLabel.setForeground(BLUE);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        mainPanel.add(mainLabel, gbc);


        JButton[] buttons = {insertMenu, deactivateMenu, updateMenu, searchMenu, taskMenu};
        gbc.gridwidth = 2;
        for (JButton button : buttons) {
            button.setBackground(GREEN);
            button.setForeground(Color.WHITE);
            gbc.gridy++;
            mainPanel.add(button, gbc);
        }

        gbc.gridy++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(exitButton, gbc);
        gbc.gridx = 1;
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        ActionListener goToPageListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                mainFrame.setVisible(false);

//                if (button == insertMenu) buildInsertMenuPage();
//                else if (button == deactivateMenu) buildDeactivateMenuPage();
//                else if (button == updateMenu) buildUpdateMenuPage();
//                else if (button == searchMenu) buildSearchMenuPage();
//                else if (button == taskMenu) buildTaskMenuPage();

                if (button == insertMenu) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Registration Menu Page");
                    buildInsertMenuPage();
                } else if (button == deactivateMenu) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Deactivation Menu Page");
                    buildDeactivateMenuPage();
                } else if (button == updateMenu) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Update Menu Page");
                    buildUpdateMenuPage();
                } else if (button == searchMenu) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Search Menu Page");
                    buildSearchMenuPage();
                } else if (button == taskMenu) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Task Menu Page");
                    buildTaskMenuPage();
                }
            }
        };

        insertMenu.addActionListener(goToPageListener);
        deactivateMenu.addActionListener(goToPageListener);
        updateMenu.addActionListener(goToPageListener);
        searchMenu.addActionListener(goToPageListener);
        taskMenu.addActionListener(goToPageListener);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + " NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void buildTaskMenuPage() {
        JFrame taskMenuFrame = new JFrame("CarSync");
        taskMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        taskMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel taskMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);

        JLabel taskLabel = new JLabel("Task Menu");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 80));
        taskLabel.setForeground(BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        taskMenuPanel.add(taskLabel, gbc);

        JLabel taskDisplayLabel = new JLabel("Next Task");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        taskMenuPanel.add(taskDisplayLabel, gbc);

        JTextArea taskDisplay = new JTextArea();
        TaskManagment taskManagment = new TaskManagment();
        Task task = taskManagment.getNextTask(employee.getAccess_level());
        taskDisplay.setEditable(false);
        taskDisplay.setLineWrap(true);
        taskDisplay.setWrapStyleWord(true);
        taskDisplay.setBackground(white);
        taskDisplay.setFont(new Font("Arial", Font.BOLD, 20));
        taskDisplay.setForeground(BLACK);
        String taskInDisplay = (task == null) ? "No tasks to display" : task.toString();
        taskDisplay.setText(taskInDisplay);
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
//        gbc.anchor = GridBagConstraints.CENTER;
        taskMenuPanel.add(taskDisplay, gbc);


        JButton executeTask = new JButton("Execute Task");
        JButton viewAllTasks = new JButton("View All Tasks");
//        exitButton.setBackground(RED);
//        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + "logged out");
            taskMenuFrame.dispose();
            mainFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " entered the Main Menu");
            mainFrame.setVisible(true);
            taskMenuFrame.setVisible(false);
            taskMenuFrame.dispose();
        });

        executeTask.setBackground(GREEN);
        executeTask.setForeground(Color.WHITE);
        executeTask.addActionListener(e -> {
            if (task == null) {
                JOptionPane.showMessageDialog(null, "Cannot execute task, no tasks to execute");
            } else {
                if (task.perFormTask(dataSource)) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " completed the task with ID: " + task.getTaskID());
                    JOptionPane.showMessageDialog(null, "Task Completed");
//                    taskDisplay.setText(new TaskManagment().getNextTask(employee.getAccess_level()).toString());
//                    TaskManagment taskManagment2 = new TaskManagment();
//                    Task task2 = taskManagment2.getNextTask(employee.getAccess_level());
//                    String taskInDisplay2 = (task2 == null) ? "No tasks to display" : task2.toString();
//                    taskDisplay.setText(taskInDisplay2);
                    taskMenuFrame.dispose();
                    buildTaskMenuPage();
                } else {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " failed to perform the task with ID: " + task.getTaskID());
                    JOptionPane.showMessageDialog(null, "Task Failed");
                }
            }
        });

        viewAllTasks.setBackground(BLUE);
        viewAllTasks.setForeground(Color.WHITE);
        viewAllTasks.addActionListener(e -> {

            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " entered the View All Tasks Page");

            taskMenuFrame.setVisible(false);
            new TaskTableNavigation(taskMenuFrame, employee.getAccess_level());
        });

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.33;
        taskMenuPanel.add(viewAllTasks, gbc);

        gbc.gridx = 2;
        taskMenuPanel.add(executeTask, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        taskMenuPanel.add(exitButton, gbc);

        gbc.gridx = 2;
        taskMenuPanel.add(backButton, gbc);


        taskMenuFrame.add(taskMenuPanel);
        taskMenuFrame.setVisible(true);

        taskMenuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void buildSearchMenuPage() {
        JFrame searchMenuFrame = new JFrame("CarSync");
        searchMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel searchMenuLabel = new JLabel("Search Menu");
        searchMenuLabel.setFont(new Font("Arial", Font.BOLD, 80));
        searchMenuLabel.setForeground(BLUE);


        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        JButton vehicleDisplay = new JButton("Vehicle Display Menu");
        JButton employeeDisplay = new JButton("Employee Display Menu");
        JButton customerDisplay = new JButton("Customer Display Menu");
        JButton ticketDisplay = new JButton("Ticket Display Menu");
        JButton insuranceDisplay = new JButton("Insurance Display Menu");
        JPanel searchMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        searchMenuPanel.add(searchMenuLabel, gbc);
        JButton[] buttons = {vehicleDisplay, employeeDisplay, customerDisplay, ticketDisplay, insuranceDisplay};

        gbc.gridwidth = 2;
        for (int i = 0; i < 5; i++) {
            gbc.gridy = i + 1;
            buttons[i].setBackground(GREEN);
            buttons[i].setForeground(WHITE);
            ;
            searchMenuPanel.add(buttons[i], gbc);
        }

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        searchMenuPanel.add(exitButton, gbc);
        gbc.gridx = 1;
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        searchMenuPanel.add(backButton, gbc);


        searchMenuFrame.add(searchMenuPanel);
        mainFrame.setVisible(false);
        searchMenuFrame.setVisible(true);
        backButton.addActionListener(e -> {

            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " went back to the main menu");

            mainFrame.setVisible(true);
            searchMenuFrame.setVisible(false);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " logged out");
            searchMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        ActionListener goToPageListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                searchMenuFrame.setVisible(false);

                if (button == vehicleDisplay) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Vehicle Display Page");
                    vehicleDisplayPage(searchMenuFrame);
                } else if (button == employeeDisplay) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Employee Display Page");
                    employeeDisplayPage(searchMenuFrame);
                } else if (button == customerDisplay) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Customer Display Page");
                    customerDisplayPage(searchMenuFrame);
                } else if (button == ticketDisplay) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Ticket Display Page");
                    ticketDisplayPage(searchMenuFrame);
                } else if (button == insuranceDisplay) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the Insurance Display Page");
                    insuranceDisplayPage(searchMenuFrame);
                }
            }
        };

        vehicleDisplay.addActionListener(goToPageListener);
        employeeDisplay.addActionListener(goToPageListener);
        customerDisplay.addActionListener(goToPageListener);
        ticketDisplay.addActionListener(goToPageListener);
        insuranceDisplay.addActionListener(goToPageListener);

        searchMenuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void insuranceDisplayPage(JFrame searchMenuFrame) {
        JFrame insuranceDisplayFrame = new JFrame("CarSync");
        insuranceDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insuranceDisplayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel insuranceDisplayPanel = new JPanel(new GridBagLayout());

        JLabel insuranceDisplayLabel = new JLabel("Insurance Display Menu");
        insuranceDisplayLabel.setFont(new Font("Arial", Font.BOLD, 50));
        insuranceDisplayLabel.setForeground(new Color(0, 0, 90));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        insuranceDisplayPanel.add(insuranceDisplayLabel, gbc);

        JLabel rowsPerPageLabel = new JLabel("Rows per page:");
        JComboBox<String> rowsPerPageOptions = new JComboBox<>(new String[]{"20", "30", "50", "100"});

        JLabel insuranceDisplaySearchLabel = new JLabel("Search by:");
        JComboBox<String> insuranceDisplaySearchOptions = new JComboBox<>(new String[]{" ", "General Search",
                "Search by Policy Number", "Search by Company", "Search by Plate",
                "Search by Category", "Search by Expiration Date", "Search by Issue Date"});

        JLabel insuranceDisplayOrderLabel = new JLabel("Order by:");
        JComboBox<String> insuranceDisplayOrderOptions = new JComboBox<>(new String[]{" ",
                "Order by Policy Number", "Order by Company", "Order by Plate",
                "Order by Category", "Order by Expiration Date", "Order by Issue Date"});

        JLabel inputForSearch = new JLabel("Input for search:");
        JTextField inputForSearchField = new JTextField(20);

        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(GREEN);

        insuranceDisplaySearchOptions.addActionListener(e -> {
            String searchOption = (String) insuranceDisplaySearchOptions.getSelectedItem();
            if(searchOption.equals("General Search")) {
                inputForSearchField.setEnabled(false);
            } else {
                inputForSearchField.setEnabled(true);
            }
        });

        searchButton.addActionListener(e -> {
            String searchOption = (String) insuranceDisplaySearchOptions.getSelectedItem();
            String orderType = (String) insuranceDisplayOrderOptions.getSelectedItem();
            String rowsPerPage = (String) rowsPerPageOptions.getSelectedItem();
            String input = inputForSearchField.getText();

            if (!searchOption.equals(" ") && !orderType.equals(" ")) {
                if (searchOption.equals("General Search")) {
                    displaySearchByOrderInsurance(rowsPerPage, dataSource.queryInsurances(), insuranceDisplayFrame, orderType);
                } else {
                    switch (searchOption) {

                        case "Search by Policy Number" -> {
                            if (isPolicy(input)) {
                                List<Insurance> insuranceList = new ArrayList<>();
                                for (Insurance insurance : dataSource.queryInsurances()) {
                                    if (insurance.getPolicy() == Integer.parseInt(input)) {
                                        insuranceList.add(insurance);
                                        break;
                                    }
                                }
                                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insuranceList);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the insurances with policy number: " + input);
                            } else {
                                JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please enter a valid policy number");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the insurances with policy number: " + input);

                            }
                        }

                        case "Search by Company" -> {
                            if (isValidString(input)) {
                                List<Insurance> insuranceList = new ArrayList<>();
                                for (Insurance insurance : dataSource.queryInsurances()) {
                                    if (insurance.getCompanyName().equals(input)) {
                                        insuranceList.add(insurance);
                                    }
                                }
                                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insuranceList);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the insurances for company: " + input);
                            } else {
                                JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please enter a valid company name");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the insurances for company: " + input);
                            }
                        }

                        case "Search by Plate" -> {
                            if (isPlate(input)) {
                                List<Insurance> insuranceList = new ArrayList<>();
                                for (Insurance insurance : dataSource.queryInsurances()) {
                                    if (insurance.getCarPlate().equals(input)) {
                                        insuranceList.add(insurance);
                                    }
                                }
                                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insuranceList);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the insurances for plate: " + input);
                            } else {
                                JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please enter a valid plate");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the insurances for plate: " + input);
                            }
                        }

                        case "Search by Category" -> {
                            if (isValidString(input)) {
                                List<Insurance> insuranceList = new ArrayList<>();
                                for (Insurance insurance : dataSource.queryInsurances()) {
                                    if (insurance.getExtraCategory().equals(input)) {
                                        insuranceList.add(insurance);
                                    }
                                }
                                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insuranceList);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the insurances with category: " + input);
                            } else {
                                JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please enter a valid category");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the insurances with category: " + input);
                            }
                        }

                        case "Search by Expiration Date" -> {
                            if (isValidExpirationDate(input)) {
                                List<Insurance> insuranceList = new ArrayList<>();
                                for (Insurance insurance : dataSource.queryInsurances()) {
                                    if (insurance.getExpDate().equals(input) || insurance.getExpDate().after(Date.valueOf(input))) {
                                        insuranceList.add(insurance);
                                    }
                                }
                                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insuranceList);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the insurances with expiration date: " + input);
                            } else {
                                JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please enter a valid date");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the insurances with expiration date: " + input);
                            }
                        }

                        case "Search by Issue Date" -> {
                            if (isDate(input)) {
                                List<Insurance> insuranceList = new ArrayList<>();
                                for (Insurance insurance : dataSource.queryInsurances()) {
                                    if (insurance.getStartDate().equals(input) || insurance.getStartDate().after(Date.valueOf(input))) {
                                        insuranceList.add(insurance);
                                    }
                                }
                                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insuranceList);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the insurances with issue date: " + input);
                            } else {
                                JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please enter a valid date");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the insurances with issue date: " + input);
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please select a search option and order option");
            }

        });

        gbc.gridwidth = 1;
        JLabel[] labels = {rowsPerPageLabel, insuranceDisplaySearchLabel, insuranceDisplayOrderLabel, inputForSearch};
        JComboBox[] comboBoxes = {rowsPerPageOptions, insuranceDisplaySearchOptions, insuranceDisplayOrderOptions};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            insuranceDisplayPanel.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (i == 3) {
                insuranceDisplayPanel.add(inputForSearchField, gbc);
            } else {
                insuranceDisplayPanel.add(comboBoxes[i], gbc);
            }
        }

        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " logged out");
            insuranceDisplayFrame.dispose();
            mainFrame.dispose();
            searchMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " went back to the search menu");
            searchMenuFrame.setVisible(true);
            insuranceDisplayFrame.setVisible(false);
        });

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insuranceDisplayPanel.add(searchButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insuranceDisplayPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        insuranceDisplayPanel.add(backButton, gbc);


        insuranceDisplayFrame.add(insuranceDisplayPanel);
        insuranceDisplayFrame.setVisible(true);

        insuranceDisplayFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void displaySearchByOrderInsurance(String rowsPerPage, List<Insurance> insurances, JFrame
            insuranceDisplayFrame, String orderType) {

        insurances.removeIf(Insurance::isDeactivated);
        switch (orderType) {
            case "Order by Policy Number" -> {
                insurances.sort(Comparator.comparing(Insurance::getPolicy));
                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insurances);
                logger.info("Employee with name: " + employee.getName()
                        + " NIF: " + employee.getNif() + " displayed all the insurances ordered by policy number");
            }
            case "Order by Company" -> {
                insurances.sort(Comparator.comparing(Insurance::getCompanyName));
                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insurances);
                logger.info("Employee with name: " + employee.getName()
                        + " NIF: " + employee.getNif() + " displayed all the insurances ordered by company");
            }
            case "Order by Plate" -> {
                insurances.sort(Comparator.comparing(Insurance::getCarPlate));
                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insurances);
                logger.info("Employee with name: " + employee.getName()
                        + " NIF: " + employee.getNif() + " displayed all the insurances ordered by plate");
            }
            case "Order by Category" -> {
                insurances.sort(Comparator.comparing(Insurance::getExtraCategory));
                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insurances);
                logger.info("Employee with name: " + employee.getName()
                        + " NIF: " + employee.getNif() + " displayed all the insurances ordered by category");
            }
            case "Order by Expiration Date" -> {
                insurances.sort(Comparator.comparing(Insurance::getExpDate));
                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insurances);
                logger.info("Employee with name: " + employee.getName()
                        + " NIF: " + employee.getNif() + " displayed all the insurances ordered by expiration date");
            }
            case "Order by Issue Date" -> {
                insurances.sort(Comparator.comparing(Insurance::getStartDate));
                new InsuranceTableNavigation(Integer.parseInt(rowsPerPage), insuranceDisplayFrame, insurances);
                logger.info("Employee with name: " + employee.getName()
                        + " NIF: " + employee.getNif() + " displayed all the insurances ordered by issue date");
            }
            default -> JOptionPane.showMessageDialog(insuranceDisplayFrame, "Please select an order option");
        }

    }

    private void ticketDisplayPage(JFrame searchMenuFrame) {
        JFrame ticketDisplayFrame = new JFrame("CarSync");
        ticketDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticketDisplayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel ticketDisplayPanel = new JPanel(new GridBagLayout());

        JLabel ticketDisplayLabel = new JLabel("Ticket Search Menu");
        ticketDisplayLabel.setFont(new Font("Arial", Font.BOLD, 50));
        ticketDisplayLabel.setForeground(new Color(0, 0, 90));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        ticketDisplayPanel.add(ticketDisplayLabel, gbc);

        JLabel rowsPerPageLabel = new JLabel("Rows per page:");
        JComboBox<String> rowsPerPageOptions = new JComboBox<>(new String[]{"20", "30", "50", "100"});

        JLabel ticketDisplaySearchLabel = new JLabel("Search by:");
        JComboBox<String> ticketDisplaySearchOptions = new JComboBox<>(new String[]{" ", "General Search",
                "Search by NIF", "Search by Plate", "Search by Reason", "Search by Issue Date", "Search by Expiration Date", "Search by Value",
                "Search by TicketID", "Search by Paid Y(y) or N(n)"});

        JLabel inputForSearch = new JLabel("Input for search: ");
        JTextField inputForSearchField = new JTextField(20);

        JLabel ticketDisplayOrderLabel = new JLabel("Order by:");
        JComboBox<String> ticketDisplayOrderOptions = new JComboBox<>(new String[]{" ", "Order by NIF",
                "Order by TicketID", "Order by Plate", "Order by Issua Date", "Order by Expiration Date",
                "Order by Reason", "Order by Value"});

        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(GREEN);

        ticketDisplaySearchOptions.addActionListener(e -> {
            String searchOption = (String) ticketDisplaySearchOptions.getSelectedItem();
            if(searchOption.equals("General Search")) {
                inputForSearchField.setEnabled(false);
            } else {
                inputForSearchField.setEnabled(true);
            }
        });

        searchButton.addActionListener(e -> {
            String searchOption = (String) ticketDisplaySearchOptions.getSelectedItem();
            String orderType = (String) ticketDisplayOrderOptions.getSelectedItem();
            String rowsPerPage = (String) rowsPerPageOptions.getSelectedItem();
            String input = inputForSearchField.getText();

            if (!searchOption.equals(" ") && !orderType.equals(" ")) {
                if (searchOption.equals("General Search")) {
                    displaySearchByOrderTicket(rowsPerPage, dataSource.queryTickets(), ticketDisplayFrame, orderType);
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " displayed all the tickets order by: " + orderType);
                } else {
                    switch (searchOption) {
                        case "Search by NIF" -> {
                            if (isNIF(input)) {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    if (ticket.getNif() == Integer.parseInt(input)) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with NIF: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter a valid NIF");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the tickets with NIF: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                        case "Search by Plate" -> {
                            if (isPlate(input)) {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    if (ticket.getPlate().equals(input)) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with plate: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter a valid plate");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the tickets with plate: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                        case "Search by Reason" -> {
                            if (isValidString(input)) {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    if (ticket.getReason().equals(input)) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with reason: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter a valid reason");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the tickets with reason: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                        case "Search by Expiration Date" -> {
                            if (isValidExpirationDate(input)) {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    if (ticket.getExpiry_date().equals(Date.valueOf(input)) || ticket.getExpiry_date().after(Date.valueOf(input))) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with expiration date: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter a valid date");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the tickets with expiration date: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                        case "Search by Value" -> {
                            if (isDouble(input)) {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    if (ticket.getValue() == Double.parseDouble(input) || ticket.getValue() > Double.parseDouble(input)) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with value: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter a valid value");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the tickets with value: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                        case "Search by TicketID" -> {
                            if (isInteger(input)) {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    if (ticket.getTicketID() == Integer.parseInt(input)) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with ticketID: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter a valid TicketID");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the tickets with ticketID: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                        case "Search by Paid Y(y) or N(n)" -> {
                            if (input.compareToIgnoreCase("Y(y)") != 0 && input.compareToIgnoreCase("N(n)") != 0) {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter Y(y) or N(n)");
                            } else {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    final boolean booleanInput = input.compareToIgnoreCase("Y(y)") == 0;
                                    if (ticket.isPaid() == booleanInput) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with paid: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                        case "Search by Issue Date" -> {
                            if (isDate(input)) {
                                List<Ticket> ticketList = new ArrayList<>();
                                dataSource.queryTickets().forEach(ticket -> {
                                    if (ticket.getDate().equals(Date.valueOf(input)) || ticket.getDate().after(Date.valueOf(input))) {
                                        ticketList.add(ticket);
                                    }
                                });
                                displaySearchByOrderTicket(rowsPerPage, ticketList, ticketDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the tickets with issue date: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please enter a valid date");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the tickets with issue date: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(ticketDisplayFrame, "Please select a search and order option");
            }
        });


        gbc.gridwidth = 1;
        JLabel[] labels = {rowsPerPageLabel, ticketDisplaySearchLabel, ticketDisplayOrderLabel, inputForSearch};
        JComboBox[] comboBoxes = {rowsPerPageOptions, ticketDisplaySearchOptions, ticketDisplayOrderOptions};

        for (int i = 0; i < labels.length; i++) {
           /* gbc.anchor = GridBagConstraints.LINE_START;
            gbc.gridwidth = 1;
            gbc.gridy++;
            insuranceDisplayPanel.add(labels[i], gbc);
            gbc.gridx++;
            insuranceDisplayPanel.add(comboBoxes[i], gbc);*/

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            ticketDisplayPanel.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (i == 3) {
                ticketDisplayPanel.add(inputForSearchField, gbc);
            } else {
                ticketDisplayPanel.add(comboBoxes[i], gbc);
            }
        }

        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            ticketDisplayFrame.dispose();
            searchMenuFrame.dispose();
            mainFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to search menu page");
            searchMenuFrame.setVisible(true);
            ticketDisplayFrame.setVisible(false);
            ticketDisplayFrame.dispose();
        });

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        ticketDisplayPanel.add(searchButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        ticketDisplayPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        ticketDisplayPanel.add(backButton, gbc);


        ticketDisplayFrame.add(ticketDisplayPanel);
        ticketDisplayFrame.setVisible(true);

        ticketDisplayFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });

    }

    private void displaySearchByOrderTicket(String rowsPerPage, List<Ticket> ticketList, JFrame
            ticketDisplayFrame, String orderType) {
        ticketList.removeIf(Ticket::isDeactivated);
        switch (orderType) {
            case "Order by NIF":
                ticketList.sort(Comparator.comparing(Ticket::getNif));
                new TicketTableNavigation(Integer.parseInt(rowsPerPage),
                        ticketDisplayFrame, ticketList);
                break;
            case "Order by TicketID":
                ticketList.sort(Comparator.comparing(Ticket::getTicketID));
                new TicketTableNavigation(Integer.parseInt(rowsPerPage),
                        ticketDisplayFrame, ticketList);
                break;
            case "Order by Plate":
                ticketList.sort(Comparator.comparing(Ticket::getPlate));
                new TicketTableNavigation(Integer.parseInt(rowsPerPage),
                        ticketDisplayFrame, ticketList);
                break;
            case "Order by Issue Date":
                ticketList.sort(Comparator.comparing(Ticket::getDate));
                new TicketTableNavigation(Integer.parseInt(rowsPerPage),
                        ticketDisplayFrame, ticketList);
                break;
            case "Order by Expiration Date":
                ticketList.sort(Comparator.comparing(Ticket::getExpiry_date));
                new TicketTableNavigation(Integer.parseInt(rowsPerPage),
                        ticketDisplayFrame, ticketList);
                break;
            case "Order by Reason":
                ticketList.sort(Comparator.comparing(Ticket::getReason));
                new TicketTableNavigation(Integer.parseInt(rowsPerPage),
                        ticketDisplayFrame, ticketList);
                break;
            case "Order by Value":
                ticketList.sort(Comparator.comparing(Ticket::getValue));
                new TicketTableNavigation(Integer.parseInt(rowsPerPage),
                        ticketDisplayFrame, ticketList);
                break;
        }

    }

    private void customerDisplayPage(JFrame searchMenuFrame) {
        JFrame customerDisplayFrame = new JFrame("CarSync");
        customerDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerDisplayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel customerDisplayPanel = new JPanel(new GridBagLayout());

        JLabel customerDisplayLabel = new JLabel("Customer Search Menu");
        customerDisplayLabel.setFont(new Font("Arial", Font.BOLD, 50));
        customerDisplayLabel.setForeground(new Color(0, 0, 90));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        customerDisplayPanel.add(customerDisplayLabel, gbc);

        JLabel rowsPerPageLabel = new JLabel("Rows per page:");
        JComboBox<String> rowsPerPageOptions = new JComboBox<>(new String[]{"20", "30", "50", "100"});

        JLabel customerDisplaySearchLabel = new JLabel("Search by:");
        JComboBox<String> customerDisplaySearchOptions = new JComboBox<>(new String[]{" ", "General Search",
                "Search by NIF", "Search by Name", "Search by Address", "Search by License Type",
                "Search by License Number"});

        JLabel inputForSearch = new JLabel("Input for search:");
        JTextField inputForSearchField = new JTextField(20);

        JLabel customerDisplayOrderLabel = new JLabel("Order by:");
        JComboBox<String> customerDisplayOrderOptions = new JComboBox<>(new String[]{" ", "Order by NIF",
                "Order by Name", "Order by Address", "Order by License Type", "Order by License Number",
                "Order by Date of Birth", "Order by Date of License Issue", "Order by Date of License Expiration"});

        customerDisplaySearchOptions.addActionListener(e -> {
            String searchOption = (String) customerDisplaySearchOptions.getSelectedItem();
            if(searchOption.equals("General Search")) {
                inputForSearchField.setEnabled(false);
            } else {
                inputForSearchField.setEnabled(true);
            }
        });

        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(GREEN);
        searchButton.addActionListener(e -> {
            String searchOption = (String) customerDisplaySearchOptions.getSelectedItem();
            String orderType = (String) customerDisplayOrderOptions.getSelectedItem();
            String rowsPerPage = (String) rowsPerPageOptions.getSelectedItem();
            String input = inputForSearchField.getText();

            if (!searchOption.equals(" ") && !orderType.equals(" ")) {
                if (searchOption.equals("General Search")) {
                    displaySearchByOrderCustomer(rowsPerPage,
                            dataSource.queryCustomers(), customerDisplayFrame, orderType);
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " displayed all the customers ordered by: " + orderType);
                } else {
                    switch (searchOption) {

                        case "Search by NIF" -> {
                            if (isNIF(input)) {
                                List<Customer> customerList = new ArrayList<>();
                                customerList.add(dataSource.getCustomerByNIF(Integer.parseInt(input)));
                                new CustomerTableNavigation(
                                        Integer.parseInt(rowsPerPage), customerDisplayFrame, customerList);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the customers with NIF: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(customerDisplayFrame, "Please enter a valid NIF");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the customers with NIF: " + input
                                        + "ordered by: " + orderType);

                            }
                        }

                        case "Search by Name" -> {
                            if (isValidString(input)) {
                                List<Customer> customerList = new ArrayList<>();
                                dataSource.queryCustomers().forEach(customer -> {
                                    if (customer.getName().contains(input)) {
                                        customerList.add(customer);
                                    }
                                });
                                displaySearchByOrderCustomer(rowsPerPage, customerList, customerDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the customers with name: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(customerDisplayFrame, "Please enter a valid name");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the customers with name: " + input
                                        + "ordered by: " + orderType);
                            }
                        }

                        case "Search by Address" -> {
                            if (isValidString(input)) {
                                List<Customer> customerList = new ArrayList<>();
                                dataSource.queryCustomers().forEach(customer -> {
                                    if (customer.getAddress().contains(input)) {
                                        customerList.add(customer);
                                    }
                                });
                                displaySearchByOrderCustomer(rowsPerPage, customerList, customerDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the customers with address: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(customerDisplayFrame, "Please enter a valid address");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the customers with address: " + input
                                        + "ordered by: " + orderType);
                            }
                        }

                        case "Search by License Type" -> {
                            if (isValidString(input)) {
                                List<Customer> customerList = new ArrayList<>();
                                dataSource.queryCustomers().forEach(customer -> {
                                    if (customer.getLicenseType().contains(input)) {
                                        customerList.add(customer);
                                    }
                                });
                                displaySearchByOrderCustomer(rowsPerPage, customerList, customerDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the customers with license type: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(customerDisplayFrame, "Please enter a valid license type");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the customers with license type: " + input
                                        + "ordered by: " + orderType);
                            }
                        }

                        case "Search by License Number" -> {
                            if (isDriverLicense(input)) {
                                List<Customer> customerList = new ArrayList<>();
                                for (Customer customer : dataSource.queryCustomers()) {
                                    if (customer.getDriverLicenseNum() == Integer.parseInt(input)) {
                                        customerList.add(customer);
                                        break;
                                    }
                                }
                                displaySearchByOrderCustomer(rowsPerPage, customerList, customerDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the customers with license number: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(customerDisplayFrame, "Please enter a valid license number");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the customers with license number: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(customerDisplayFrame, "Please select a search and order option");
            }
        });


        gbc.gridwidth = 1;
        JLabel[] labels = {rowsPerPageLabel, customerDisplaySearchLabel, customerDisplayOrderLabel, inputForSearch};
        JComboBox[] comboBoxes = {rowsPerPageOptions, customerDisplaySearchOptions, customerDisplayOrderOptions};

        for (int i = 0; i < labels.length; i++) {
           /* gbc.anchor = GridBagConstraints.LINE_START;
            gbc.gridwidth = 1;
            gbc.gridy++;
            insuranceDisplayPanel.add(labels[i], gbc);
            gbc.gridx++;
            insuranceDisplayPanel.add(comboBoxes[i], gbc);*/

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            customerDisplayPanel.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (i == 3) {
                customerDisplayPanel.add(inputForSearchField, gbc);
            } else {
                customerDisplayPanel.add(comboBoxes[i], gbc);
            }
        }

        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            customerDisplayFrame.dispose();
            mainFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to search menu page");
            searchMenuFrame.setVisible(true);
            customerDisplayFrame.setVisible(false);
        });

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        customerDisplayPanel.add(searchButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        customerDisplayPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        customerDisplayPanel.add(backButton, gbc);


        customerDisplayFrame.add(customerDisplayPanel);
        customerDisplayFrame.setVisible(true);

        customerDisplayFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });

    }

    private void displaySearchByOrderCustomer(String rowsPerPage, List<Customer> customerList,
                                              JFrame customerDisplayFrame, String orderType) {
        customerList.removeIf(Customer::isDeactivated);
        switch (orderType) {
            case "Order by NIF":
                customerList.sort(Comparator.comparing(Customer::getNif));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
            case "Order by Name":
                customerList.sort(Comparator.comparing(Customer::getName));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
            case "Order by Address":
                customerList.sort(Comparator.comparing(Customer::getAddress));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
            case "Order by License Type":
                customerList.sort(Comparator.comparing(Customer::getLicenseType));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
            case "Order by License Number":
                customerList.sort(Comparator.comparing(Customer::getDriverLicenseNum));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
            case "Order by Date of Birth":
                customerList.sort(Comparator.comparing(Customer::getBirht_date));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
            case "Order by Date of License Issue":
                customerList.sort(Comparator.comparing(Customer::getStartingDate));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
            case "Order by Date of License Expiration":
                customerList.sort(Comparator.comparing(Customer::getExpDate));
                new CustomerTableNavigation(Integer.parseInt(rowsPerPage),
                        customerDisplayFrame, customerList);
                break;
        }

    }

    private void employeeDisplayPage(JFrame searchMenuFrame) {
        JFrame employeeDisplayFrame = new JFrame("CarSync");
        employeeDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employeeDisplayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel employeeDisplayPanel = new JPanel(new GridBagLayout());

        JLabel customerDisplayLabel = new JLabel("Employee Search Menu");
        customerDisplayLabel.setFont(new Font("Arial", Font.BOLD, 50));
        customerDisplayLabel.setForeground(new Color(0, 0, 90));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        employeeDisplayPanel.add(customerDisplayLabel, gbc);

        JLabel rowsPerPageLabel = new JLabel("Rows per page:");
        JComboBox<String> rowsPerPageOptions = new JComboBox<>(new String[]{"20", "30", "50", "100"});

        JLabel employeeDisplaySearchLabel = new JLabel("Search by:");
        JComboBox<String> employeeDisplaySearchOptions = new JComboBox<>(new String[]{" ", "General Search",
                "Search by NIF", "Search by Name", "Search by Address", "Search by Access Level"});

        JLabel inputForSearch = new JLabel("Input for search:");
        JTextField inputForSearchField = new JTextField(20);

        JLabel employeeDisplayOrderLabel = new JLabel("Order by:");
        JComboBox<String> employeeDisplayOrderOptions = new JComboBox<>(new String[]{" ", "Order by NIF",
                "Order by Name", "Order by Address", "Order by Access Level", "Order by Date of Birth"});

        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(GREEN);

        employeeDisplaySearchOptions.addActionListener(e -> {
            String searchOption = (String) employeeDisplaySearchOptions.getSelectedItem();
            if(searchOption.equals("General Search")) {
                inputForSearchField.setEnabled(false);
            } else {
                inputForSearchField.setEnabled(true);
            }
        });

        searchButton.addActionListener(e -> {
            String searchOption = (String) employeeDisplaySearchOptions.getSelectedItem();
            String orderType = (String) employeeDisplayOrderOptions.getSelectedItem();
            String rowsPerPage = (String) rowsPerPageOptions.getSelectedItem();
            String input = inputForSearchField.getText();

            if (!searchOption.equals(" ") && !orderType.equals(" ")) {
                if (searchOption.equals("General Search")) {
                    displaySearchByOrderEmployee(rowsPerPage, dataSource.queryEmployees(),
                            employeeDisplayFrame, orderType);
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " displayed all the employees order by: " + orderType);
                } else {
                    switch (searchOption) {

                        case "Search by NIF" -> {
                            if (isNIF(input)) {
                                List<Employee> employees = new ArrayList<>();
                                for (Employee employee1 : dataSource.queryEmployees()) {
                                    if (employee1.getNif() == Integer.parseInt(input)) {
                                        employees.add(employee1);
                                        break;
                                    }
                                }
                                displaySearchByOrderEmployee(rowsPerPage, dataSource.queryEmployees(),
                                        employeeDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the employees with NIF: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(employeeDisplayFrame, "Please enter a valid NIF");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the employees with NIF: " + input
                                        + "ordered by: " + orderType);
                            }
                        }

                        case "Search by Name" -> {
                            if (isValidString(input)) {
                                List<Employee> employees = new ArrayList<>();
                                for (Employee employee1 : dataSource.queryEmployees()) {
                                    if (employee1.getName().equals(input)) {
                                        employees.add(employee1);
                                    }
                                }
                                displaySearchByOrderEmployee(rowsPerPage, dataSource.queryEmployees(),
                                        employeeDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the employees with name: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(employeeDisplayFrame, "Please enter a valid name");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the employees with name: " + input
                                        + "ordered by: " + orderType);
                            }
                        }

                        case "Search by Address" -> {
                            if (isValidString(input)) {
                                List<Employee> employees = new ArrayList<>();
                                for (Employee employee1 : dataSource.queryEmployees()) {
                                    if (employee1.getAddress().equals(input)) {
                                        employees.add(employee1);
                                    }
                                }
                                displaySearchByOrderEmployee(rowsPerPage, dataSource.queryEmployees(),
                                        employeeDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the employees with address: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(employeeDisplayFrame, "Please enter a valid address");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the employees with address: " + input
                                        + "ordered by: " + orderType);
                            }
                        }

                        case "Search by Access Level" -> {
                            if (isInteger(input)) {
                                List<Employee> employees = new ArrayList<>();
                                for (Employee employee1 : dataSource.queryEmployees()) {
                                    if (employee1.getAccess_level() == Integer.parseInt(input)) {
                                        employees.add(employee1);
                                    }
                                }
                                displaySearchByOrderEmployee(rowsPerPage, dataSource.queryEmployees(),
                                        employeeDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the employees with access level: " + input
                                        + "ordered by: " + orderType);
                            } else {
                                JOptionPane.showMessageDialog(employeeDisplayFrame, "Please enter a valid access level");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the employees with access level: " + input
                                        + "ordered by: " + orderType);
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(employeeDisplayFrame, "Please select a search option and an order option");
            }
        });

        gbc.gridwidth = 1;
        JLabel[] labels = {rowsPerPageLabel, employeeDisplaySearchLabel, employeeDisplayOrderLabel, inputForSearch};
        JComboBox[] comboBoxes = {rowsPerPageOptions, employeeDisplaySearchOptions, employeeDisplayOrderOptions};

        for (int i = 0; i < labels.length; i++) {
            /*gbc.gridx = 0;
            gbc.gridy++;
            employeeDisplayPanel.add(labels[i], gbc);
            gbc.gridx = 1;
            employeeDisplayPanel.add(comboBoxes[i], gbc);*/

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            employeeDisplayPanel.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (i == 3) {
//                gbc.fill = GridBagConstraints.HORIZONTAL;
                employeeDisplayPanel.add(inputForSearchField, gbc);
            } else {
//                gbc.fill = GridBagConstraints.NONE;
                employeeDisplayPanel.add(comboBoxes[i], gbc);
            }
        }

        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            employeeDisplayFrame.dispose();
            mainFrame.dispose();
            searchMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to search menu page");
            searchMenuFrame.setVisible(true);
            employeeDisplayFrame.setVisible(false);
        });

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        employeeDisplayPanel.add(searchButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        employeeDisplayPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        employeeDisplayPanel.add(backButton, gbc);

        employeeDisplayFrame.add(employeeDisplayPanel);
        employeeDisplayFrame.setVisible(true);

        employeeDisplayFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void displaySearchByOrderEmployee(String rowsPerPage, List<Employee> employees, JFrame
            employeeDisplayFrame, String orderType) {
        employees.removeIf(Employee::isDeactivated);
        switch (orderType) {
            case "Order by NIF":
                employees.sort(Comparator.comparing(Employee::getNif));
                new EmployeeTableNavigation(Integer.parseInt(rowsPerPage),
                        employeeDisplayFrame, employees);
                break;
            case "Order by Name":
                employees.sort(Comparator.comparing(Employee::getName));
                new EmployeeTableNavigation(Integer.parseInt(rowsPerPage),
                        employeeDisplayFrame, employees);
                break;
            case "Order by Address":
                employees.sort(Comparator.comparing(Employee::getAddress));
                new EmployeeTableNavigation(Integer.parseInt(rowsPerPage),
                        employeeDisplayFrame, employees);
                break;
            case "Order by Access Level":
                employees.sort(Comparator.comparing(Employee::getAccess_level));
                new EmployeeTableNavigation(Integer.parseInt(rowsPerPage),
                        employeeDisplayFrame, employees);
                break;
            case "Order by Date of Birth":
                employees.sort(Comparator.comparing(Employee::getBirht_date));
                new EmployeeTableNavigation(Integer.parseInt(rowsPerPage),
                        employeeDisplayFrame, employees);
                break;
        }
    }

    private void vehicleDisplayPage(JFrame searchMenuFrame) {
        JFrame vehicleDisplayFrame = new JFrame("CarSync");
        vehicleDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vehicleDisplayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel vehicleDisplayPanel = new JPanel(new GridBagLayout());
        JLabel vehicleDisplayLabel = new JLabel("Vehicle Display");
        vehicleDisplayLabel.setFont(new Font("Arial", Font.BOLD, 20));
        vehicleDisplayLabel.setForeground(new Color(0, 0, 90));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        vehicleDisplayPanel.add(vehicleDisplayLabel, gbc);

        JLabel rowsPerPageLabel = new JLabel("Rows per page: ");
        JComboBox<String> rowsPerPageOptions = new JComboBox<>(new String[]{"20",
                "30", "50", "100"});

        JLabel vehicleDisplayOrderLabel = new JLabel("Order by: ");
        JComboBox<String> vehicleDisplayOrderOptions = new JComboBox<>(new String[]{" ",
                "License Plate", "Brand", "Color", "Issue Date",
                "VIN", "NIF", "Category"});

        JLabel vehicleDisplaySearchLabel = new JLabel("Search by: ");
        JComboBox<String> vehicleDisplaySearchOptions = new JComboBox<>(new String[]{" ",
                "General Search", "Search by Color", "Search by Brand", "Search by NIF",
                "Search by Plate", "Search by VIN", "Search by Category", "Search by Issue Date"});

        JLabel inputForSearch = new JLabel("Input for search:");
        JTextField inputForSearchField = new JTextField(20);


        vehicleDisplaySearchOptions.addActionListener(e -> {
            String searchOption = (String) vehicleDisplaySearchOptions.getSelectedItem();
            if(searchOption.equals("General Search")) {
                inputForSearchField.setEnabled(false);
            } else {
                inputForSearchField.setEnabled(true);
            }
        });


        JButton searchButton = new JButton("Search");
        searchButton.setBackground(GREEN);
        searchButton.setForeground(Color.WHITE);

        searchButton.addActionListener(e -> {
            String searchOption = (String) vehicleDisplaySearchOptions.getSelectedItem();
            String orderType = (String) vehicleDisplayOrderOptions.getSelectedItem();
            String rowsPerPage = (String) rowsPerPageOptions.getSelectedItem();
            String input = (String) inputForSearchField.getText();

            if (!searchOption.equals(" ") && !orderType.equals(" ")) {
                if (searchOption.equals("General Search")) {
                    displaySearchByOrderVehicle(rowsPerPage, dataSource.queryVehicles(), vehicleDisplayFrame, orderType);
                } else {
                    switch (searchOption) {
                        case "Search by NIF" -> {
                            if (isNIF(input)) {
                                List<Vehicle> vehicleList = new ArrayList<>();
                                dataSource.queryVehicles().forEach(vehicle -> {
                                    if (vehicle.getNif() == Integer.parseInt(input)) {
                                        vehicleList.add(vehicle);
                                    }
                                });
                                displaySearchByOrderVehicle(rowsPerPage, vehicleList, vehicleDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the vehicles with NIF: " + input);
                            } else {
                                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please write a valid NIF");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the vehicles with NIF: " + input);
                            }
                        }

                        case "Search by Brand" -> {
                            if (isValidString(input)) {
                                List<Vehicle> vehicleList = new ArrayList<>();
                                dataSource.queryVehicles().forEach(vehicle -> {
                                    if (vehicle.getBrand().equals(input)) {
                                        vehicleList.add(vehicle);
                                    }
                                });
                                displaySearchByOrderVehicle(rowsPerPage, vehicleList, vehicleDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the vehicles with brand: " + input);
                            } else {
                                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please write a valid input");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the vehicles with brand: " + input);
                            }
                        }

                        case "Search by Color" -> {
                            if (isValidString(input)) {
                                List<Vehicle> vehicleList = new ArrayList<>();
                                dataSource.queryVehicles().forEach(vehicle -> {
                                    if (vehicle.getColor().equals(input)) {
                                        vehicleList.add(vehicle);
                                    }
                                });
                                displaySearchByOrderVehicle(rowsPerPage, vehicleList, vehicleDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the vehicles with color: " + input);
                            } else {
                                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please write a valid color");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the vehicles with color: " + input);
                            }
                        }

                        case "Search by Category" -> {
                            if (isValidString(input)) {
                                List<Vehicle> vehicleList = new ArrayList<>();
                                dataSource.queryVehicles().forEach(vehicle -> {
                                    if (vehicle.getCategory().equals(input)) {
                                        vehicleList.add(vehicle);
                                    }
                                });
                                displaySearchByOrderVehicle(rowsPerPage, vehicleList, vehicleDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the vehicles with category: " + input);
                            } else {
                                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please write a valid category");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the vehicles with category: " + input);
                            }
                        }

                        case "Search by Issue Date" -> {
                            if (isDate(input)) {
                                List<Vehicle> vehicleList = new ArrayList<>();
                                dataSource.queryVehicles().forEach(vehicle -> {
                                    if (vehicle.getregistrationDate().equals(input) || vehicle.getregistrationDate().after(Date.valueOf(input))) {
                                        vehicleList.add(vehicle);
                                    }
                                });
                                displaySearchByOrderVehicle(rowsPerPage, vehicleList, vehicleDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the vehicles with issue date: " + input);
                            } else {
                                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please write a valid Date");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the vehicles with issue date: " + input);
                            }
                        }

                        case "Search by VIN" -> {
                            if (isVIN(input)) {
                                List<Vehicle> vehicleList = new ArrayList<>();
                                dataSource.queryVehicles().forEach(vehicle -> {
                                    if (vehicle.getVin().equals(input)) {
                                        vehicleList.add(vehicle);
                                    }
                                });
                                displaySearchByOrderVehicle(rowsPerPage, vehicleList, vehicleDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the vehicles with VIN: " + input);
                            } else {
                                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please write a valid VIN");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the vehicles with VIN: " + input);
                            }
                        }

                        case "Search by Plate" -> {
                            if (isPlate(input)) {
                                List<Vehicle> vehicleList = new ArrayList<>();
                                dataSource.queryVehicles().forEach(vehicle -> {
                                    if (vehicle.getPlate().equals(input)) {
                                        vehicleList.add(vehicle);
                                    }
                                });
                                displaySearchByOrderVehicle(rowsPerPage, vehicleList, vehicleDisplayFrame, orderType);
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " displayed all the vehicles with plate: " + input);
                            } else {
                                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please write a valid plate");
                                logger.info("Employee with name: " + employee.getName()
                                        + " NIF: " + employee.getNif() + " tried to display all the vehicles with plate: " + input);
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please, select an option for each field");
            }
        });
        gbc.gridwidth = 1;
        JLabel[] labels = {rowsPerPageLabel, vehicleDisplaySearchLabel, vehicleDisplayOrderLabel, inputForSearch};
        JComboBox[] comboBoxes = {rowsPerPageOptions, vehicleDisplaySearchOptions, vehicleDisplayOrderOptions};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            vehicleDisplayPanel.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;

            if (i == 3) {
                vehicleDisplayPanel.add(inputForSearchField, gbc);
            } else {
                vehicleDisplayPanel.add(comboBoxes[i], gbc);
            }
        }

        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e ->

        {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " logged out");
            vehicleDisplayFrame.dispose();
            mainFrame.dispose();
            searchMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e ->

        {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " went back to the search menu");
            searchMenuFrame.setVisible(true);
            vehicleDisplayFrame.setVisible(false);
        });

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new

                Insets(20, 10, 5, 10);
        vehicleDisplayPanel.add(searchButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new

                Insets(20, 10, 5, 10);
        vehicleDisplayPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new

                Insets(20, 10, 5, 10);
        vehicleDisplayPanel.add(backButton, gbc);

        vehicleDisplayFrame.add(vehicleDisplayPanel);
        vehicleDisplayFrame.setVisible(true);

        vehicleDisplayFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });

    }

    private void displaySearchByOrderVehicle(String srowsPerPage, List<Vehicle> vehicles,
                                             JFrame vehicleDisplayFrame, String orderOption) {
        int rowsPerPage = Integer.parseInt(srowsPerPage);
        vehicles.removeIf(Vehicle::isDeactivated);
        switch (orderOption) {
            case "License Plate" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getPlate));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            case "NIF" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getNif));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            case "VIN" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getVin));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            case "Category" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getCategory));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            case "Issue Date" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getregistrationDate));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            case "Brand" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getBrand));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            case "Model" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getModel));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            case "Color" -> {
                vehicles.sort(Comparator.comparing(Vehicle::getColor));
                new VehicleTableNavigation(rowsPerPage, vehicleDisplayFrame, vehicles);
            }
            default -> {
                JOptionPane.showMessageDialog(vehicleDisplayFrame, "Please select an option for each field");
            }
        }

    }

    private void buildUpdateMenuPage() {
        JFrame updateMenuFrame = new JFrame("CarSync");
        updateMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel updateMenuLabel = new JLabel("update Menu");
        updateMenuLabel.setFont(new Font("Arial", Font.BOLD, 80));
        updateMenuLabel.setForeground(BLUE);


        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        JButton vehicleUpdate = new JButton("Vehicle Update Menu");
        JButton employeeUpdate = new JButton("Employee Update Menu");
        JButton customerUpdate = new JButton("Customer Update Menu");
        JButton ticketUpdate = new JButton("Ticket Update Menu");
        JPanel updateMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        updateMenuPanel.add(updateMenuLabel, gbc);

        gbc.gridy = 1;

        JButton[] buttons = {vehicleUpdate, employeeUpdate, customerUpdate, ticketUpdate};

        for (int i = 0; i < 4; i++) {
            gbc.gridy = i + 1;
            buttons[i].setBackground(GREEN);
            buttons[i].setForeground(WHITE);
            ;
            updateMenuPanel.add(buttons[i], gbc);
        }

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        updateMenuPanel.add(exitButton, gbc);
        gbc.gridx = 1;
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        updateMenuPanel.add(backButton, gbc);


        updateMenuFrame.add(updateMenuPanel);
        mainFrame.setVisible(false);
        updateMenuFrame.setVisible(true);

        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " went back to the update menu");
            mainFrame.setVisible(true);
            updateMenuFrame.setVisible(false);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + " NIF: " + employee.getNif() + " logged out");
            dataSource.close();
            updateMenuFrame.dispose();
            new WelcomeMenuForm();
        });

        ActionListener goToPageListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                updateMenuFrame.setVisible(false);

                if (button == vehicleUpdate) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the vehicle update menu");
                    vehicleUpdatePage(updateMenuFrame);
                } else if (button == employeeUpdate) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the employee update menu");
                    employeeUpdatePage(updateMenuFrame);
                } else if (button == customerUpdate) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the customer update menu");
                    customerUpdatePage(updateMenuFrame);
                } else if (button == ticketUpdate) {
                    logger.info("Employee with name: " + employee.getName()
                            + " NIF: " + employee.getNif() + " entered the ticket update menu");
                    ticketUpdatePage(updateMenuFrame);
                }
            }
        };

        vehicleUpdate.addActionListener(goToPageListener);
        employeeUpdate.addActionListener(goToPageListener);
        customerUpdate.addActionListener(goToPageListener);
        ticketUpdate.addActionListener(goToPageListener);

        updateMenuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });

    }

    private void ticketUpdatePage(JFrame updateMenuFrame) {
        JFrame updateTicketFrame = new JFrame("CarSync");
        updateTicketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateTicketFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Pay");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);

        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to the update menu");
            updateTicketFrame.setVisible(false);
            updateMenuFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            updateTicketFrame.dispose();
            mainFrame.dispose();
            updateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel updateTicketPanel = new JPanel(new GridBagLayout());

        JLabel updateTicketLabel = new JLabel("Pay Ticket");
        updateTicketLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        updateTicketPanel.add(updateTicketLabel, gbc);

        JLabel idLabel = new JLabel("ID of the ticket: ");
        JTextField idField = new JTextField(15);

        JLabel valueLabel = new JLabel("Value to pay: ");
        JTextField valueField = new JTextField(15);


        submit.addActionListener(e -> {
            String id = idField.getText();
            String value = valueField.getText();

            if
            (dataSource.payTicket(Integer.parseInt(id), Float.parseFloat(valueField.getText()))) {
                JOptionPane.showMessageDialog(updateTicketFrame, "Ticket successfully paid",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " paid ticket with id: " + id + " and value: " + value);
            } else {
                JOptionPane.showMessageDialog(updateTicketFrame, "Error paying ticket", "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to pay ticket with id: " + id + " and value: " + value);
            }
        });

        gbc.gridwidth = 1;
        JLabel[] labels = {idLabel, valueLabel};
        JTextField[] textFields = {idField, valueField};


        for (int row = 0; row < labels.length; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            updateTicketPanel.add(labels[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;

            updateTicketPanel.add(textFields[row], gbc);

        }

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 6, 10);
        updateTicketPanel.add(submit, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        updateTicketPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        updateTicketPanel.add(backButton, gbc);

        updateTicketFrame.add(updateTicketPanel);
        updateTicketFrame.setVisible(true);

        updateTicketFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void customerUpdatePage(JFrame updateMenuFrame) {
        JFrame updatePersonFrame = new JFrame("CarSync");
        updatePersonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updatePersonFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Submit");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to the update menu");
            updatePersonFrame.setVisible(false);
            updateMenuFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            updatePersonFrame.dispose();
            mainFrame.dispose();
            updateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel updatePersonPanel = new JPanel(new GridBagLayout());

        JLabel updatePersonLabel = new JLabel("Update Person");
        updatePersonLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        updatePersonPanel.add(updatePersonLabel, gbc);

        JLabel nifLabel = new JLabel("NIF: ");
        JTextField nifField = new JTextField(15);
//        JComboBox nifField = new JComboBox(arrayInfo(dataSource.queryCustomers()));

        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField(15);

        JButton submitButton1 = new JButton("Change Email");
        submitButton1.setBackground(GREEN);
        submitButton1.setForeground(Color.WHITE);
        submitButton1.addActionListener(e -> {
//            String nif = nifField.getSelectedItem().toString();
            String nif = nifField.getText();
            String email = emailField.getText();
            if (!isNIF(nif)) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Please write a valid NIF");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update email for person with NIF: " + nif);
            } else if (!isEmail(email)) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Wrong email format");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update email for person with NIF: " + nif);
            } else {
                if (dataSource.updatePersonEmail(Integer.parseInt(nif), email)) {
                    JOptionPane.showMessageDialog(updatePersonFrame, "Email successfully updated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to update email for person with NIF: " + nif);
                } else
                    JOptionPane.showMessageDialog(updatePersonFrame, "Error updating person", "Error", JOptionPane.ERROR_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " was not able to update email for person with NIF: " + nif);
            }
        });

        JLabel passwordLabel = new JLabel("Password: (8 - 10 characters)");
        JTextField passwordField = new JTextField(15);

        JButton submitButton2 = new JButton("Change Password");
        submitButton2.setBackground(GREEN);
        submitButton2.setForeground(Color.WHITE);
        submitButton2.addActionListener(e -> {
            String password = passwordField.getText();
//            String nif = nifField.getSelectedItem().toString();
            String nif = nifField.getText();
            if (!isNIF(nif)) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Please write a valid NIF");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update password for person with NIF: " + nif);
            } else if (!isPassword(passwordField.getText())) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Wrong password format");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update password for person with NIF: " + nif);
            } else {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
                if (dataSource.updatePersonPassword(Integer.parseInt(nif), password)) {
                    JOptionPane.showMessageDialog(updatePersonFrame, "Password successfully updated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + "updated email for person with NIF: " + nif);
                } else
                    JOptionPane.showMessageDialog(updatePersonFrame, "Error updating person", "Error", JOptionPane.ERROR_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " was not able to update email for person with NIF: " + nif);
            }
        });

        JLabel addressLabel = new JLabel("Address: ");
        JTextField addressField = new JTextField(36);

        JButton submitButton3 = new JButton("Change Address");
        submitButton3.setBackground(GREEN);
        submitButton3.setForeground(Color.WHITE);
        submitButton3.addActionListener(e -> {
            String address = addressField.getText();
//            String nif = nifField.getSelectedItem().toString();
            String nif = nifField.getText();
            if (!isNIF(nif)) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Please write a valid NIF");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update address for person with NIF: " + nif);
            } else if (!isValidString(address)) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Wrong address format");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update address for person with NIF: " + nif);
            } else {
                if (dataSource.updatePersonAddress(Integer.parseInt(nif), address)) {
                    JOptionPane.showMessageDialog(updatePersonFrame, "Address successfully updated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " updated address for person with NIF: " + nif);
                } else
                    JOptionPane.showMessageDialog(updatePersonFrame, "Error updating person", "Error", JOptionPane.ERROR_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " was not able to update address for person with NIF: " + nif);
            }
        });

        //Montar janela
        gbc.gridwidth = 1;
        JLabel[] labels = {nifLabel, emailLabel, passwordLabel, addressLabel};
//        JComboBox[] comboBoxes = {nifField};
        JTextField[] textFields = {nifField, emailField, passwordField, addressField};
        JButton[] buttons = {submitButton1, submitButton2, submitButton3};

        // Inside the for loop
        for (int row = 0; row < labels.length; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            updatePersonPanel.add(labels[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            updatePersonPanel.add(textFields[row], gbc);

            // Check if it's not the "NIF" line
            if (row != 0) {
                gbc.gridx = 2;
                gbc.anchor = GridBagConstraints.LINE_END;
                updatePersonPanel.add(buttons[row - 1], gbc);
            }
        }

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        updatePersonPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        updatePersonPanel.add(backButton, gbc);

        updatePersonFrame.add(updatePersonPanel);
        updatePersonFrame.setVisible(true);
        updateMenuFrame.setVisible(false);

        updatePersonFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });

    }

    private void employeeUpdatePage(JFrame updateMenuFrame) {
        JFrame updateEmployeeFrame = new JFrame("CarSync");
        updateEmployeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateEmployeeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Submit");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to the update menu");
            updateEmployeeFrame.setVisible(false);
            updateMenuFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            updateEmployeeFrame.dispose();
            mainFrame.dispose();
            updateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel updateEmployeePanel = new JPanel(new GridBagLayout());

        JLabel updateEmployeeLabel = new JLabel("Update Person");
        updateEmployeeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        updateEmployeePanel.add(updateEmployeeLabel, gbc);

        JLabel nifLabel = new JLabel("NIF: ");
        JTextField nifField = new JTextField(15);
//        JComboBox nifField = new JComboBox(arrayInfo(dataSource.queryCustomers()));

        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField(15);

        JButton submitButton1 = new JButton("Change Email");
        submitButton1.setBackground(GREEN);
        submitButton1.setForeground(Color.WHITE);
        submitButton1.addActionListener(e -> {
//            String nif = nifField.getSelectedItem().toString();
            String nif = nifField.getText();
            String email = emailField.getText();
            if (!isNIF(nif)) {
                JOptionPane.showMessageDialog(updateEmployeeFrame, "Please write a valid NIF");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update email for person with NIF: " + nif);
            } else if (!isEmail(email)) {
                JOptionPane.showMessageDialog(updateEmployeeFrame, "Wrong email format");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update email for person with NIF: " + nif);
            } else {
                if (dataSource.updatePersonEmail(Integer.parseInt(nif), email)) {
                    JOptionPane.showMessageDialog(updateEmployeeFrame, "Email successfully updated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to update email for person with NIF: " + nif);
                } else
                    JOptionPane.showMessageDialog(updateEmployeeFrame, "Error updating person", "Error", JOptionPane.ERROR_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " was not able to update email for person with NIF: " + nif);
            }
        });

        JLabel passwordLabel = new JLabel("Password: (8 - 10 characters)");
        JTextField passwordField = new JTextField(15);


        JButton submitButton2 = new JButton("Change Password");
        submitButton2.setBackground(GREEN);
        submitButton2.setForeground(Color.WHITE);
        submitButton2.addActionListener(e -> {
            String password = passwordField.getText();
//            String nif = nifField.getSelectedItem().toString();
            String nif = nifField.getText();
            if (!isNIF(nif)) {
                JOptionPane.showMessageDialog(updateEmployeeFrame, "Please write a valid NIF");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update password for person with NIF: " + nif);
            } else if (!isPassword(passwordField.getText())) {
                JOptionPane.showMessageDialog(updateEmployeeFrame, "Wrong password format");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update password for person with NIF: " + nif);
            } else {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
                if (dataSource.updatePersonPassword(Integer.parseInt(nif), password)) {
                    JOptionPane.showMessageDialog(updateEmployeeFrame, "Password successfully updated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + "updated email for person with NIF: " + nif);
                } else
                    JOptionPane.showMessageDialog(updateEmployeeFrame, "Error updating person", "Error", JOptionPane.ERROR_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " was not able to update email for person with NIF: " + nif);
            }
        });

        JLabel addressLabel = new JLabel("Address: ");
        JTextField addressField = new JTextField(36);

        JButton submitButton3 = new JButton("Change Address");
        submitButton3.setBackground(GREEN);
        submitButton3.setForeground(Color.WHITE);
        submitButton3.addActionListener(e -> {
            String address = addressField.getText();
//            String nif = nifField.getSelectedItem().toString();
            String nif = nifField.getText();
            if (!isNIF(nif)) {
                JOptionPane.showMessageDialog(updateEmployeeFrame, "Please write a valid NIF");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update address for person with NIF: " + nif);
            } else if (!isValidString(address)) {
                JOptionPane.showMessageDialog(updateEmployeeFrame, "Wrong address format");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update address for person with NIF: " + nif);
            } else {
                if (dataSource.updatePersonAddress(Integer.parseInt(nif), address)) {
                    JOptionPane.showMessageDialog(updateEmployeeFrame, "Address successfully updated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " updated address for person with NIF: " + nif);
                } else
                    JOptionPane.showMessageDialog(updateEmployeeFrame, "Error updating person", "Error", JOptionPane.ERROR_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " was not able to update address for person with NIF: " + nif);
            }
        });

        //Montar janela
        gbc.gridwidth = 1;
        JLabel[] labels = {nifLabel, emailLabel, passwordLabel, addressLabel};
//        JComboBox[] comboBoxes = {nifField};
        JTextField[] textFields = {nifField, emailField, passwordField, addressField};
        JButton[] buttons = {submitButton1, submitButton2, submitButton3};

        // Inside the for loop
        for (int row = 0; row < labels.length; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            updateEmployeePanel.add(labels[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            updateEmployeePanel.add(textFields[row], gbc);

            // Check if it's not the "NIF" line
            if (row != 0) {
                gbc.gridx = 2;
                gbc.anchor = GridBagConstraints.LINE_END;
                updateEmployeePanel.add(buttons[row - 1], gbc);
            }
        }

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        updateEmployeePanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        updateEmployeePanel.add(backButton, gbc);

        updateEmployeeFrame.add(updateEmployeePanel);
        updateEmployeeFrame.setVisible(true);
        updateMenuFrame.setVisible(false);

        updateEmployeeFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });

    }

    private void vehicleUpdatePage(JFrame updateMenuFrame) {
        updateMenuFrame.setVisible(false);
        JFrame updateVehicleFrame = new JFrame("CarSync");
        updateVehicleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateVehicleFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Submit");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to update menu page");
            updateVehicleFrame.setVisible(false);
            updateMenuFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            updateVehicleFrame.dispose();
            mainFrame.dispose();
            updateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel updateVehiclePanel = new JPanel(new GridBagLayout());

        JLabel updateVehicleLabel = new JLabel("Update Vehicle");
        updateVehicleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        updateVehiclePanel.add(updateVehicleLabel, gbc);

        JLabel plate = new JLabel("Plate: ");
        JTextField plateField = new JTextField(15);

        JLabel color = new JLabel("Color: ");
        JComboBox<String> colorField = new JComboBox<>(new String[]{" ", "Black", "White",
                "Red", "Blue", "Green", "Yellow", "Gray", "Silver", "Brown", "Orange"});

        submit.addActionListener(e -> {
            String plateText = plateField.getText();
            String colorText = (String) colorField.getSelectedItem();
            if (!isPlate(plateText)) {
                JOptionPane.showMessageDialog(updateVehicleFrame, "Please write a valid plate with " +
                        "format XX-XX-XX");
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to update vehicle with plate: " + plateText + " and new color: " + colorText);
            } else {
                if (dataSource.updateVehicleColor(plateText, colorText)) {
                    JOptionPane.showMessageDialog(updateVehicleFrame, "Vehicle successfully updated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " updated vehicle with plate " + plateText + " and new color: " + colorText);
                } else {
                    JOptionPane.showMessageDialog(updateVehicleFrame, "Error updating vehicle", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to update vehicle with plate: " + plateText + " and new color: " + colorText);

                }
            }
            updateVehicleFrame.setVisible(false);
            vehicleUpdatePage(updateMenuFrame);
        });

        gbc.gridwidth = 1;
        JLabel[] labels = {plate, color};
        JTextField[] textFields = {plateField
        };

        for (int row = 0; row < labels.length; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            updateVehiclePanel.add(labels[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (row == 0) {
                updateVehiclePanel.add(textFields[row], gbc);
            } else {
                updateVehiclePanel.add(colorField, gbc);
            }

        }

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 6, 10);
        updateVehiclePanel.add(submit, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        updateVehiclePanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        updateVehiclePanel.add(backButton, gbc);

        updateVehicleFrame.add(updateVehiclePanel);
        updateVehicleFrame.setVisible(true);

        updateVehicleFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    void buildDeactivateMenuPage() {
        JFrame deactivateMenuFrame = new JFrame("CarSync");
        deactivateMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deactivateMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel deactivateMenuLabel = new JLabel("Deactivation Menu");
        deactivateMenuLabel.setFont(new Font("Arial", Font.BOLD, 80));
        deactivateMenuLabel.setForeground(BLUE);

        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        JButton vehicleDeactivation = new JButton("Vehicle Deactivation Menu");
        JButton employeeDeactivation = new JButton("Employee Deactivation Menu");
        JButton customerDeactivation = new JButton("Customer Deactivation Menu");
        JButton ticketDeactivation = new JButton("Ticket Deactivation Menu");
        JButton insuranceDeactivation = new JButton("Insurance Deactivation Menu");
        JPanel deactivationMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        deactivationMenuPanel.add(deactivateMenuLabel, gbc);

        gbc.gridy = 1;
        JButton[] buttons = {vehicleDeactivation, employeeDeactivation, customerDeactivation, ticketDeactivation, insuranceDeactivation};

        for (int i = 0; i < 5; i++) {
            gbc.gridy = i + 1;
            buttons[i].setBackground(GREEN);
            buttons[i].setForeground(WHITE);
            ;
            deactivationMenuPanel.add(buttons[i], gbc);
        }

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        deactivationMenuPanel.add(exitButton, gbc);
        gbc.gridx = 1;
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        deactivationMenuPanel.add(backButton, gbc);

        deactivateMenuFrame.add(deactivationMenuPanel);
        mainFrame.setVisible(false);
        deactivateMenuFrame.setVisible(true);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to update menu page");
            mainFrame.setVisible(true);
            deactivateMenuFrame.setVisible(false);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            dataSource.close();
            mainFrame.dispose();
            deactivateMenuFrame.dispose();
            new WelcomeMenuForm();
        });

        ActionListener goToPageListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                deactivateMenuFrame.setVisible(false);
                if (button == vehicleDeactivation) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " went to vehicle deactivation page");
                    deactivateVehiclePage(deactivateMenuFrame);
                } else if (button == customerDeactivation) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " went to customer deactivation page");
                    deactivateCustomerPage(deactivateMenuFrame);
                } else if (button == ticketDeactivation) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " went to ticket deactivation page");
                    deactivateTicketPage(deactivateMenuFrame);
                } else if (button == insuranceDeactivation) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " went to insurance deactivation page");
                } else if (button == employeeDeactivation) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " went to employee deactivation page");
                    deactivateInsurancePage(deactivateMenuFrame);
                }
            }
        };

        vehicleDeactivation.addActionListener(goToPageListener);
        customerDeactivation.addActionListener(goToPageListener);
        ticketDeactivation.addActionListener(goToPageListener);
        insuranceDeactivation.addActionListener(goToPageListener);
        employeeDeactivation.addActionListener(goToPageListener);

        deactivateMenuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void deactivateCustomerPage(JFrame deactivateMenuFrame) {
        JFrame deactivateCustomerFrame = new JFrame("CarSync");
        deactivateCustomerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deactivateCustomerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Deactivate");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);

        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went to back to the deactivation menu");
            deactivateCustomerFrame.setVisible(false);
            deactivateMenuFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            deactivateCustomerFrame.dispose();
            mainFrame.dispose();
            deactivateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel deactivateCustomerPanel = new JPanel(new GridBagLayout());

        JLabel deactivateCustomerLabel = new JLabel("Deactivate Customer");
        deactivateCustomerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        deactivateCustomerPanel.add(deactivateCustomerLabel, gbc);

        JLabel nifLabel = new JLabel("NIF: ");
        JTextField nifField = new JTextField(15);

        submit.addActionListener(e -> {
            String nif = nifField.getText();
            if (isNIF(nif)) {
                if (dataSource.deactivateCustomer(Integer.parseInt(nif))) {
                    JOptionPane.showMessageDialog(deactivateCustomerFrame, "Customer successfully deactivated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " deactivated customer with NIF: " + nif);
                } else {
                    JOptionPane.showMessageDialog(deactivateCustomerFrame, "Error deactivating Customer", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to deactivate customer with NIF: " + nif);
                }
            } else {
                JOptionPane.showMessageDialog(deactivateCustomerFrame, "Please write a valid NIF");
                logger.error("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to deactivate customer with NIF: " + nif);
            }
        });

        gbc.gridwidth = 1;
        JLabel[] labels = {nifLabel};
        JTextField[] textFields = {nifField};

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        deactivateCustomerPanel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        deactivateCustomerPanel.add(textFields[0], gbc);

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 6, 10);
        deactivateCustomerPanel.add(submit, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateCustomerPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateCustomerPanel.add(backButton, gbc);

        deactivateCustomerFrame.add(deactivateCustomerPanel);
        deactivateCustomerFrame.setVisible(true);

        deactivateCustomerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void deactivateTicketPage(JFrame deactivateMenuFrame) {
        JFrame deactivateTicketFrame = new JFrame("CarSync");
        deactivateTicketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deactivateTicketFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Deactivate");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);

        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to the deactivation menu");
            deactivateTicketFrame.setVisible(false);
            deactivateMenuFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            mainFrame.dispose();
            deactivateTicketFrame.dispose();
            deactivateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel deactivateTicketPanel = new JPanel(new GridBagLayout());

        JLabel deactivateTicketLabel = new JLabel("Deactivate Ticket");
        deactivateTicketLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        deactivateTicketPanel.add(deactivateTicketLabel, gbc);

        JLabel idLabel = new JLabel("Ticket ID: ");
        JTextField idField = new JTextField(15);

        submit.addActionListener(e -> {
            String id = idField.getText();
            if (dataSource.deactivateTicket(Integer.parseInt(id))) {
                JOptionPane.showMessageDialog(deactivateTicketFrame, "Ticket successfully deactivated",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " deactivate the ticket with ID: " + id);
            } else {
                JOptionPane.showMessageDialog(deactivateTicketFrame, "Error deactivating Ticket", "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to deactivate the ticket with ID: " + id);
            }

        });

        gbc.gridwidth = 1;
        JLabel[] labels = {idLabel};
        JTextField[] textFields = {idField};

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        deactivateTicketPanel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        deactivateTicketPanel.add(textFields[0], gbc);

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 6, 10);
        deactivateTicketPanel.add(submit, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateTicketPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateTicketPanel.add(backButton, gbc);

        deactivateTicketFrame.add(deactivateTicketPanel);
        deactivateTicketFrame.setVisible(true);

        deactivateTicketFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void deactivateInsurancePage(JFrame deactivateMenuFrame) {
        JFrame deactivateInsuranceFrame = new JFrame("CarSync");
        deactivateInsuranceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deactivateInsuranceFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Deactivate");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);

        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went to back to deactivation page");
            deactivateInsuranceFrame.setVisible(false);
            deactivateMenuFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            deactivateInsuranceFrame.dispose();
            mainFrame.dispose();
            deactivateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel deactivateInsurancePanel = new JPanel(new GridBagLayout());

        JLabel deactivateInsuranceLabel = new JLabel("Deactivate Insured Policy ");
        deactivateInsuranceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        deactivateInsurancePanel.add(deactivateInsuranceLabel, gbc);

        JLabel policyLabel = new JLabel("Policy number: ");
        JTextField policyField = new JTextField(15);

        submit.addActionListener(e -> {
            String policyNum = policyField.getText();
            if (dataSource.deactivateInsurance(Integer.parseInt(policyNum))) {
                JOptionPane.showMessageDialog(deactivateMenuFrame, "Policy successfully deactivated",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " deactivated the policy with number: " + policyNum);
            } else {
                JOptionPane.showMessageDialog(deactivateMenuFrame, "Error deactivating Policy", "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to deactivate the policy with number: " + policyNum);
            }

        });

        gbc.gridwidth = 1;
        JLabel[] labels = {policyLabel};
        JTextField[] textFields = {policyField};

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        deactivateInsurancePanel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        deactivateInsurancePanel.add(textFields[0], gbc);

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 6, 10);
        deactivateInsurancePanel.add(submit, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateInsurancePanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateInsurancePanel.add(backButton, gbc);

        deactivateInsuranceFrame.add(deactivateInsurancePanel);
        deactivateInsuranceFrame.setVisible(true);

        deactivateInsuranceFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void deactivateVehiclePage(JFrame deactivateMenuFrame) {
        JFrame deactivateVehicleFrame = new JFrame("CarSync");
        deactivateVehicleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deactivateVehicleFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Deactivate");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);

        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to deactivation page");
            deactivateVehicleFrame.setVisible(false);
            deactivateMenuFrame.setVisible(true);
        });

        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            deactivateVehicleFrame.dispose();
            mainFrame.dispose();
            deactivateMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP for exit buttons
        JPanel deactivateVehiclePanel = new JPanel(new GridBagLayout());

        JLabel deactivateVehicleLabel = new JLabel("Pay Ticket");
        deactivateVehicleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        deactivateVehiclePanel.add(deactivateVehicleLabel, gbc);

        JLabel plateLabel = new JLabel("Plate: ");
        JTextField plateField = new JTextField(15);

        submit.addActionListener(e -> {
            String plate = plateField.getText();
            if (isPlate(plate)) {
                if (dataSource.deactivateVehicle(plate)) {
                    JOptionPane.showMessageDialog(deactivateVehicleFrame, "Vehicle successfully deactivated",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " deactivated the vehicle with plate: " + plate);
                } else {
                    JOptionPane.showMessageDialog(deactivateVehicleFrame, "Error deactivating vehicle", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to deactivate the vehicle with plate: " + plate);
                }
            } else {
                JOptionPane.showMessageDialog(deactivateVehicleFrame, "Please write a valid plate with " +
                        "format XX-XX-XX");
                logger.error("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " failed to deactivate the vehicle with plate: " + plate);
            }
        });

        gbc.gridwidth = 1;
        JLabel[] labels = {plateLabel};
        JTextField[] textFields = {plateField};

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        deactivateVehiclePanel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        deactivateVehiclePanel.add(textFields[0], gbc);

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 6, 10);
        deactivateVehiclePanel.add(submit, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateVehiclePanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        deactivateVehiclePanel.add(backButton, gbc);

        deactivateVehicleFrame.add(deactivateVehiclePanel);
        deactivateVehicleFrame.setVisible(true);

        deactivateVehicleFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    void buildInsertMenuPage() {
        JFrame insertMenuFrame = new JFrame("CarSync");
        insertMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel insertMenuLabel = new JLabel("Registration Menu");
        insertMenuLabel.setFont(new Font("Arial", Font.BOLD, 80));
        insertMenuLabel.setForeground(BLUE);

        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        JButton vehicleInsert = new JButton("Vehicle Registration Page");
        JButton employeeInsert = new JButton("Employee Registration Page");
        JButton customerInsert = new JButton("Customer Registration Page");
        JButton ticketInsert = new JButton("Ticket Registration Page");
        JButton insuranceInsert = new JButton("Insurance Registration Page");
        JPanel insertMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        insertMenuPanel.add(insertMenuLabel, gbc);

        gbc.gridy = 1;
        JButton[] buttons = {vehicleInsert, employeeInsert, customerInsert, ticketInsert, insuranceInsert};
        for (int i = 0; i < 5; i++) {
            gbc.gridy = i + 1;
            buttons[i].setBackground(GREEN);
            buttons[i].setForeground(WHITE);
            ;
            insertMenuPanel.add(buttons[i], gbc);
        }

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        insertMenuPanel.add(exitButton, gbc);
        gbc.gridx = 1;
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        insertMenuPanel.add(backButton, gbc);


        insertMenuFrame.add(insertMenuPanel);
        mainFrame.setVisible(false);
        insertMenuFrame.setVisible(true);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to main menu");
            mainFrame.setVisible(true);
            insertMenuFrame.setVisible(false);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            dataSource.close();
            mainFrame.dispose();
            insertMenuFrame.dispose();
            new WelcomeMenuForm();
        });
        ActionListener goToPageListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                insertMenuFrame.setVisible(false);

                if (button == vehicleInsert) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " entered the vehicle registration page");
                    insertVehicle(insertMenuFrame);
                } else if (button == employeeInsert) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " entered the employee registration page");
                    insertEmployee(insertMenuFrame);
                } else if (button == customerInsert) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " entered the customer registration page");
                    insertCustomer(insertMenuFrame);
                } else if (button == ticketInsert) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " entered the ticket registration page");
                    insertTicket(insertMenuFrame);
                } else if (button == insuranceInsert) {
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " entered the insurance registration page");
                    insertInsurance(insertMenuFrame);
                }
            }
        };

        vehicleInsert.addActionListener(goToPageListener);
        customerInsert.addActionListener(goToPageListener);
        employeeInsert.addActionListener(goToPageListener);
        ticketInsert.addActionListener(goToPageListener);
        insuranceInsert.addActionListener(goToPageListener);

        insertMenuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void insertInsurance(JFrame insertMenuFrame) {
        JFrame insertInsuranceFrame = new JFrame("CarSync");
        insertInsuranceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertInsuranceFrame.setExtendedState((JFrame.MAXIMIZED_BOTH));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel insertInsurancePanel = new JPanel(new GridBagLayout());

        JLabel insertInsuranceLabel = new JLabel("Insurance Registration Page");
        insertInsuranceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        insertInsuranceLabel.setForeground(BLACK);
        gbc.gridwidth = 3;
//      gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        insertInsurancePanel.add(insertInsuranceLabel, gbc);

        JLabel plateLabel = new JLabel("Plate: ");
        JTextField plateField = new JTextField(15);

        JLabel insuranceCategoryLabel = new JLabel("Insurance Category: ");
        JComboBox<String> insuranceCategoryField = new JComboBox<>(new String[]{" ", "Third Party",
                "Third Party Fire and Theft", "Third Party Fire and Auto-Liabitlity", "Comprehensive"});

        JLabel policyLabel = new JLabel("Policy: ");
        JTextField policyField = new JTextField(15);

        JLabel startDateLabel = new JLabel("Start Date: ");
//        JTextField startDateField = new JTextField(15);

        JDateChooser startDateField = new JDateChooser();
        startDateField.setDateFormatString("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JLabel endDateLabel = new JLabel("End Date: ");
//        JTextField endDateField = new JTextField(15);

        JDateChooser endDateField = new JDateChooser();
        endDateField.setDateFormatString("yyyy-MM-dd");


        JLabel companyNameLabel = new JLabel("Company Name: ");
        JTextField companyNameField = new JTextField(15);

        JComboBox<String> companyNameField2 = new JComboBox<>(new String[]{" ", "Tranquilidade",
                "Generalli", "Fidelidade", "Logo", "Ok!", "AGEAS Seguros", "Cofidis", "ACP Seguuros", "UNO Seguros", "Allianz"});

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {

            while (true) {
                String plate = plateField.getText();
                String insuranceCategory = (String) insuranceCategoryField.getSelectedItem();
                String policy = policyField.getText();
//                String startDate = startDateField.getText();
                String startDate = sdf.format(startDateField.getDate());
//                String endDate = endDateField.getText();
                String endDate = sdf.format(endDateField.getDate());
                String companyName = companyNameField.getText();
                String companyName2 = (String) companyNameField2.getSelectedItem();

                if (isPlate(plate) && isDate(startDate) && isValidExpirationDate(endDate) && isPolicy(policy)
                        && !insuranceCategory.equals(" ") && !companyName2.equals(" ")) {
                    if (dataSource.insertInsurance(Integer.parseInt(policy), plate, Date.valueOf(startDate),
                            insuranceCategory, Date.valueOf(endDate), companyName2)) {
                        JOptionPane.showMessageDialog(insertInsuranceFrame, "Ticket successfully registered", "Success", JOptionPane.INFORMATION_MESSAGE);
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " registered a new insurance and went back to the registration menu");
                        insertInsuranceFrame.setVisible(false);
                        insertInsuranceFrame.dispose();
                        insertInsurance(insertMenuFrame);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(insertInsuranceFrame, "Error registering ticket", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " tried to register a new insurance but failed");
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(insertInsuranceFrame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " tried to register a new insurance but failed");
                    break;
                }
            }
        });

        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e ->

        {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            insertInsuranceFrame.dispose();
            dataSource.close();
            mainFrame.dispose();
            insertMenuFrame.dispose();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e ->

        {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " went back to the registration menu page");
            insertMenuFrame.setVisible(true);
            insertInsuranceFrame.setVisible(false);
        });

        gbc.gridwidth = 1;
//        JLabel[] labels = {plateLabel, policyLabel,
//                startDateLabel, endDateLabel, companyNameLabel, insuranceCategoryLabel};
//        JTextField[] fields = {plateField, policyField,
//                startDateField, endDateField, companyNameField};

        JLabel[] labels = {plateLabel, policyLabel
                , insuranceCategoryLabel, startDateLabel, endDateLabel, companyNameLabel};
        JTextField[] fields = {plateField, policyField};

        for (
                int row = 0;
                row < labels.length; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            insertInsurancePanel.add(labels[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (row == 2) {
                insertInsurancePanel.add(companyNameField2, gbc);
            } else if (row == 3) {
                insertInsurancePanel.add(insuranceCategoryField, gbc);
            } else if (row == 4) {
                insertInsurancePanel.add(startDateField, gbc);
            } else if (row == 5) {
                insertInsurancePanel.add(endDateField, gbc);
            } else {
                insertInsurancePanel.add(fields[row], gbc);
            }
        }

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new

                Insets(20, 10, 5, 10);
        insertInsurancePanel.add(submitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new

                Insets(20, 10, 5, 10);
        insertInsurancePanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new

                Insets(20, 10, 5, 10);
        insertInsurancePanel.add(backButton, gbc);

        insertInsuranceFrame.add(insertInsurancePanel);
        insertInsuranceFrame.setVisible(true);

        insertInsuranceFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });

    }

    private void insertTicket(JFrame insertMenuFrame) {
        JFrame insertTicketFrame = new JFrame("CarSync");
        insertTicketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertTicketFrame.setExtendedState((JFrame.MAXIMIZED_BOTH));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel insertTicketPanel = new JPanel(new GridBagLayout());

        JLabel insertTicketLabel = new JLabel("Ticket Registration Page");
        insertTicketLabel.setFont(new Font("Arial", Font.BOLD, 20));
        insertTicketLabel.setForeground(BLACK);
        gbc.gridwidth = 3;
//      gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        insertTicketPanel.add(insertTicketLabel, gbc);

        JLabel plateLabel = new JLabel("Plate: ");
        JTextField plateField = new JTextField(15);

        JLabel dateLabel = new JLabel("Date: ");
//        JTextField dateField = new JTextField(15);

        JDateChooser dateField = new JDateChooser();
        dateField.setDateFormatString("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JLabel expirationDateLabel = new JLabel("Expiration Date: ");
//        JTextField expirationDateField = new JTextField(15);

        JDateChooser expirationDateField = new JDateChooser();
        expirationDateField.setDateFormatString("yyyy-MM-dd");

        JLabel valueLabel = new JLabel("Value: ");
        JTextField valueField = new JTextField(15);

        JLabel nifLabel = new JLabel("NIF: ");
        JTextField nifField = new JTextField(15);

        JLabel reasonLabel = new JLabel("Reason: ");
        JComboBox<String> reasonFiel = new JComboBox<>(new String[]{" ", "Illegal arking",
                "Speeding", "Red light", "Reckless driving", "DUI"});

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            while (true) {
                String plate = plateField.getText();
//                String date = dateField.getText();

                String date = sdf.format(dateField.getDate());

//                String expirationDate = expirationDateField.getText();
                String expirationDate = sdf.format(expirationDateField.getDate());

                String value = valueField.getText();
                String nif = nifField.getText();
                String reason = (String) reasonFiel.getSelectedItem();

                if (isPlate(plate) && isDate(date) && isValidExpirationDate(expirationDate) && isDouble(value) && isNIF(nif) && reason != " ") {
                    if (dataSource.insertTicket(Integer.parseInt(nif), plate, Date.valueOf(date),
                            reason, Double.parseDouble(value), Date.valueOf(expirationDate))) {
                        JOptionPane.showMessageDialog(insertTicketFrame, "Ticket successfully registered", "Success", JOptionPane.INFORMATION_MESSAGE);
                        insertTicketFrame.setVisible(false);
                        insertTicketFrame.dispose();
                        insertTicket(insertMenuFrame);
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " registered a new ticket for customer with NIF: " + nif + " and Date: " + date);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(insertTicketFrame, "Error registering ticket", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " tried to register a new ticket for customer with NIF: " + nif + " and Date: " + date);
                   break;
                    }
                } else {
                    JOptionPane.showMessageDialog(insertTicketFrame, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " tried to register a new ticket for customer with NIF: " + nif + " and Date: " + date);
                    break;
                }
            }
        });

        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            insertTicketFrame.dispose();
            dataSource.close();
            mainFrame.dispose();
            insertMenuFrame.dispose();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " returned to the registration menu page");
            insertMenuFrame.setVisible(true);
            insertTicketFrame.setVisible(false);
        });

        gbc.gridwidth = 1;
        JLabel[] labels = new JLabel[]{plateLabel, valueLabel, nifLabel, reasonLabel, dateLabel, expirationDateLabel};
        JTextField[] fields = new JTextField[]{plateField, valueField, nifField};

        for (int row = 0; row < 6; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            insertTicketPanel.add(labels[row], gbc);
            gbc.gridx = 1;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (row == 3) {
                insertTicketPanel.add(reasonFiel, gbc);
            } else if (row == 4) {
                insertTicketPanel.add(dateField, gbc);
            } else if (row == 5) {
                insertTicketPanel.add(expirationDateField, gbc);
            } else {
                insertTicketPanel.add(fields[row], gbc);
            }
        }

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertTicketPanel.add(submitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertTicketPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertTicketPanel.add(backButton, gbc);

        insertTicketFrame.add(insertTicketPanel);
        insertTicketFrame.setVisible(true);

        insertTicketFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void insertEmployee(JFrame insertMenuFrame) {
        JFrame insertEmployeeFrame = new JFrame("CarSync");
        insertEmployeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertEmployeeFrame.setExtendedState((JFrame.MAXIMIZED_BOTH));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel insertEmployeePanel = new JPanel(new GridBagLayout());

        JLabel insertEmployeeLabel = new JLabel("Employee Registration Page");
        insertEmployeeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        insertEmployeeLabel.setForeground(BLACK);
        gbc.gridwidth = 3;
//      gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        insertEmployeePanel.add(insertEmployeeLabel, gbc);

        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(15);

        JLabel addressLabel = new JLabel("Address: ");
        JTextField addressField = new JTextField(15);

        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField(15);

        JLabel birthDateLabel = new JLabel("Birth Date: ");
//        JTextField birthDateField = new JTextField(15);

        JDateChooser birthDateField = new JDateChooser();
        birthDateField.setDateFormatString("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JLabel passwordLabel = new JLabel("Password: (8 - 10 characters)");
        JTextField passwordField = new JTextField(15);

        JLabel nifLabel = new JLabel("NIF: ");
        JTextField nifField = new JTextField(15);

        JLabel accessLevelLabel = new JLabel("Access Level: ");
        JComboBox<String> accessLevelField = new JComboBox<>(
                new String[]{" ", "0", "1", "2"});

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {

            while (true) {
                String name = nameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
//                String birthDate = birthDateField.getText();

                String birthDate = sdf.format(birthDateField.getDate());
                String password = passwordField.getText();
                String nif = nifField.getText();
                String accessLevel = (String) accessLevelField.getSelectedItem();

                if (isValidString(name) && isValidString(address) && isEmail(email) && isValidBirthDate(birthDate) && isPassword(password) && isNIF(nif) && !accessLevel.equals(" ")) {
                    password = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
                    if (dataSource.insertEmployee(Integer.parseInt(nif),
                            name, address, Date.valueOf(birthDate), password,
                            email, Integer.parseInt(accessLevel))) {
                        JOptionPane.showMessageDialog(insertEmployeeFrame, "Employee registered successfully");
                        insertEmployeeFrame.dispose();
                        insertEmployeeFrame.setVisible(false);
                        insertEmployee(insertMenuFrame);
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " registered a new employee with NIF: " + nif);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(insertEmployeeFrame, "Error registering employee");
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " failed to register a new employee with NIF: " + nif);
                   break;
                    }
                } else {
                    JOptionPane.showMessageDialog(insertEmployeeFrame, "Please fill all the fields");
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to registered a new employee with NIF: " + nif);
                    break;
                }
            }
        });

        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            insertEmployeeFrame.dispose();
            mainFrame.dispose();
            insertMenuFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " returned to registration menu page");
            insertMenuFrame.setVisible(true);
            insertEmployeeFrame.setVisible(false);
        });

        gbc.gridwidth = 1;
//        JLabel[] labels = {nameLabel, addressLabel, emailLabel, birthDateLabel,
//                passwordLabel, nifLabel, accessLevelLabel};
//        JTextField[] fields = {nameField, addressField, emailField, birthDateField,
//                passwordField, nifField};

        JLabel[] labels = {nameLabel, addressLabel, emailLabel,
                passwordLabel, nifLabel, accessLevelLabel, birthDateLabel};
        JTextField[] fields = {nameField, addressField, emailField,
                passwordField, nifField};

        for (int row = 0; row < 7; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            insertEmployeePanel.add(labels[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (row == 6) {
                insertEmployeePanel.add(birthDateField, gbc);
            } else if (row == 5) {
                insertEmployeePanel.add(accessLevelField, gbc);
            } else {
                insertEmployeePanel.add(fields[row], gbc);
            }
        }

        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertEmployeePanel.add(submitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertEmployeePanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertEmployeePanel.add(backButton, gbc);

        insertEmployeeFrame.add(insertEmployeePanel);
        insertEmployeeFrame.setVisible(true);

        insertEmployeeFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void insertCustomer(JFrame insertMenuFrame) {
        JFrame insertCustomerFrame = new JFrame("CarSync");
        insertCustomerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertCustomerFrame.setExtendedState((JFrame.MAXIMIZED_BOTH));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel insertCustomerPanel = new JPanel(new GridBagLayout());

        JLabel insertCustomerLabel = new JLabel("Customer Registration Page");
        insertCustomerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        insertCustomerLabel.setForeground(BLACK);
        gbc.gridwidth = 3;
//      gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        insertCustomerPanel.add(insertCustomerLabel, gbc);

        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(15);

        JLabel addressLabel = new JLabel("Address: ");
        JTextField addressField = new JTextField(15);

        JLabel emailLabel = new JLabel("Email: ([...]@[...].com or [...]@[...].pt");
        JTextField emailField = new JTextField(15);

        JLabel birthDateLabel = new JLabel("Birth Date: ");
//        JTextField birthDateField = new JTextField(15);

        JDateChooser birthDateField = new JDateChooser();
        birthDateField.setDateFormatString("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JLabel passwordLabel = new JLabel("Password: (8 - 10 characters)");
        JTextField passwordField = new JTextField(15);

        JLabel nifLabel = new JLabel("NIF: (9 digits)");
        JTextField nifField = new JTextField(15);

        JLabel driverLicenseLabel = new JLabel("Driver License: (8 digits)");
        JTextField driverLicenseField = new JTextField(15);

        JLabel licenseTypeLabel = new JLabel("License Type: (Select an option");
        JComboBox<String> licenseType = new JComboBox<>(new String[]{" ", "A", "B", "C", "D"});

        JLabel licenseDateLabel = new JLabel("License Date: ");
//        JTextField licenseDateField = new JTextField(15);

        JDateChooser licenseDateField = new JDateChooser();
        licenseDateField.setDateFormatString("yyyy-MM-dd");

        JLabel licenseExpirationLabel = new JLabel("License Expiration: ");
//        JTextField licenseExpirationField = new JTextField(15);

        JDateChooser licenseExpirationField = new JDateChooser();
        licenseExpirationField.setDateFormatString("yyyy-MM-dd");

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {

            while (true) {
                String name = nameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
//                String birthDate = birthDateField.getText();
                String birthDate = sdf.format(birthDateField.getDate());

                String password = passwordField.getText();
                String nif = nifField.getText();
                String driverLicense = driverLicenseField.getText();
                String selectedLicenseType = (String) licenseType.getSelectedItem();
//                String licenseDate = licenseDateField.getText();
                String licenseDate = sdf.format(licenseDateField.getDate());

//                String licenseExpiration = licenseExpirationField.getText();
                String licenseExpiration = sdf.format(licenseExpirationField.getDate());

                if (isValidString(name) && isValidString(address) && isNIF(nif) && isDate(licenseDate)
                        && isValidExpirationDate(licenseExpiration) && isEmail(email) && isValidBirthDate(birthDate)
                        && isPassword(password) && isDriverLicense(driverLicense) && selectedLicenseType != (" ")) {
                    password = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
                    if (dataSource.insertCustomer(Integer.parseInt(nif), name, address, Date.valueOf(birthDate),
                            password, email, Integer.parseInt(driverLicense), selectedLicenseType, Date.valueOf(licenseDate), Date.valueOf(licenseExpiration))) {
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " registered a new customer with NIF: " + nif);
                        JOptionPane.showMessageDialog(insertCustomerFrame, "Customer inserted successfully");
                        insertCustomerFrame.setVisible(false);
                        insertCustomerFrame.dispose();
                        insertVehicle(insertMenuFrame);

                        break;
                    } else {
                        JOptionPane.showMessageDialog(insertCustomerFrame, "Error inserting customer");
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " failed to register new employee with NIF: " + nif);
                    break;
                    }
                } else {
                    JOptionPane.showMessageDialog(insertCustomerFrame, "Please fill all the fields");
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to register new employee with NIF: " + nif);
                    break;
                }
            }
        });

        JButton exitButton = new JButton("Sign Out");
        JButton backButton = new JButton("Back");
        exitButton.setBackground(RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            insertCustomerFrame.dispose();
            dataSource.close();
            mainFrame.dispose();
            insertMenuFrame.dispose();
            new WelcomeMenuForm();
        });

        backButton.setBackground(BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " returned to registration menu page");
            insertMenuFrame.setVisible(true);
            insertCustomerFrame.setVisible(false);
        });

        gbc.gridwidth = 1;
//        JLabel[] labels = {nameLabel, addressLabel, emailLabel, birthDateLabel,
//                passwordLabel, nifLabel, driverLicenseLabel, licenseDateLabel,
//                licenseExpirationLabel, licenseTypeLabel};
//        JTextField[] fields = {nameField, addressField, emailField, birthDateField,
//                passwordField, nifField, driverLicenseField,
//                licenseDateField, licenseExpirationField};

        JLabel[] labels = {nameLabel, addressLabel, emailLabel,
                passwordLabel, nifLabel, driverLicenseLabel, licenseDateLabel,
                licenseExpirationLabel, licenseTypeLabel, birthDateLabel};
        JTextField[] fields = {nameField, addressField, emailField,
                passwordField, nifField, driverLicenseField};


//        for (int row = 0; row < 10; row++) {
//            gbc.gridx = 0;
//            gbc.gridy = row + 1;
//            gbc.anchor = GridBagConstraints.LINE_START;
//            insertCustomerPanel.add(labels[row], gbc);
//
//            gbc.gridx = 1;
//            gbc.anchor = GridBagConstraints.LINE_END;
//            if (row == 9) {
//                insertCustomerPanel.add(licenseType, gbc);
//            } else if (row == 3) {
//                insertCustomerPanel.add(birthDateField, gbc);
//            } else {
//                insertCustomerPanel.add(fields[row], gbc);
//            }
//        }

        for (int row = 0; row < 10; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            insertCustomerPanel.add(labels[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (row == 6) {
                insertCustomerPanel.add(licenseDateField, gbc);
            } else if (row == 7) {
                insertCustomerPanel.add(licenseExpirationField, gbc);
            } else if (row == 8) {
                insertCustomerPanel.add(licenseType, gbc);
            } else if (row == 9) {
                insertCustomerPanel.add(birthDateField, gbc);
            } else {
                insertCustomerPanel.add(fields[row], gbc);
            }
        }

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertCustomerPanel.add(submitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertCustomerPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        insertCustomerPanel.add(backButton, gbc);

        insertCustomerFrame.add(insertCustomerPanel);
        insertCustomerFrame.setVisible(true);

        insertCustomerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    private void insertVehicle(JFrame insertMenuFrame) {
        JFrame insertVehicleFrame = new JFrame("CarSync");
        insertVehicleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertVehicleFrame.setExtendedState((JFrame.MAXIMIZED_BOTH));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JButton exitButton = new JButton("Sign Out");
        exitButton.setBackground(RED);
        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Submit");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);
        backButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            insertMenuFrame.setVisible(true);
            insertVehicleFrame.setVisible(false);
        });
        exitButton.addActionListener(e -> {
            logger.info("Employee with name: " + employee.getName()
                    + "NIF: " + employee.getNif() + " logged out");
            insertVehicleFrame.dispose();
            dataSource.close();
            new WelcomeMenuForm();
        });

        //SET UP para botoes de exit
        JPanel insertVehiclePanel = new JPanel(new GridBagLayout());

        JLabel insertVehicleLabel = new JLabel("Vehicle Registration Page");
        insertVehicleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        insertVehiclePanel.add(insertVehicleLabel, gbc);

        JLabel brand = new JLabel("Brand: (Select an option)");
        JComboBox<String> brandField = new JComboBox<>(new String[]{" ", "Abarth", "Alfa Romeo", "Aston Martin", "Audi",
                "Bentley", "BMW", "Bugatti", "Cadillac", "Chevrolet", "Chrysler", "Citroen", "Dacia", "Daewoo",
                "Daihatsu", "Dodge", "Donkervoort", "DS", "Ferrari", "Fiat", "Fisker", "Ford", "Honda", "Hummer",
                "Hyundai", "Infiniti", "Iveco", "Jaguar", "Jeep", "Kia", "KTM", "Lada", "Lamborghini", "Lancia",
                "Land Rover", "Landwind", "Lexus", "Lotus", "Maserati", "Maybach", "Mazda", "McLaren", "Mercedes-Benz",
                "MG", "Mini", "Mitsubishi", "Morgan", "Nissan", "Opel", "Peugeot", "Porsche", "Renault", "Rolls-Royce",
                "Rover", "Saab", "Seat", "Skoda", "Smart", "SsangYong", "Subaru", "Suzuki", "Tesla", "Toyota",
                "Volkswagen", "Volvo"});

        JLabel model = new JLabel("Model");
        JTextField modelField = new JTextField(15);

        JLabel plate = new JLabel("Plate: XX-XX-XX ");
        JTextField plateField = new JTextField(15);

        JLabel color = new JLabel("Color: (Select an option)");
        JComboBox<String> colorField = new JComboBox<>(new String[]{" ", "Black", "White",
                "Red", "Blue", "Green", "Yellow", "Gray", "Silver", "Brown", "Orange"});

        JLabel registrationDate = new JLabel("Registration Date: ");
//        JTextField registrationDateField = new JTextField(15);

        JDateChooser registrationDateField = new JDateChooser();
        registrationDateField.setDateFormatString("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JLabel vin = new JLabel("VIN: (17 characters)");
        JTextField vinField = new JTextField(15);

        JLabel nif = new JLabel("NIF: (9 digits)");
        JTextField nifField = new JTextField(15);

        JLabel category = new JLabel("Category: (Select an option)");
        JComboBox<String> categoryField = new JComboBox<>(new String[]{" ", "Light Commercial Vehicle",
                "Light Passenger Vehicle",
                "Heavy-duty Commercial Vehicle",
                "Heavy-duty Passenger Vehicle",
                "Motorcycle", "Moped",
                "Heavy-duty Passenger Vehicle"});

        submit.addActionListener(e -> {
            while (true) {
                String plateText = plateField.getText();
                String modelText = modelField.getText();
                String colorText = (String) colorField.getSelectedItem();
//                String registrationDateText = registrationDateField.getText();

                String registrationDateText = sdf.format(registrationDateField.getDate());

                String brandText = (String) brandField.getSelectedItem();
                String vinText = vinField.getText();
                String nifText = nifField.getText();
                String categoryText = (String) categoryField.getSelectedItem();

                if (isPlate(plateText) && isValidString(modelText) && isDate(registrationDateText) && isVIN(vinText) && isNIF(nifText) && !categoryText.equals(" ") && !colorText.equals(" ") && !brandText.equals(" ")) {
                    if (dataSource.insertVehicle(plateText, vinText, colorText, brandText,
                            modelText, Date.valueOf(registrationDateText), categoryText, Integer.parseInt(nifText))) {
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " registered a new vehicle with plate: " + plateText);
                        JOptionPane.showMessageDialog(insertVehicleFrame, "Vehicle succefully registered");
                        insertVehicleFrame.setVisible(false);
                        insertVehicleFrame.dispose();
                        insertCustomer(insertMenuFrame);
                        break;

                    } else {
                        JOptionPane.showMessageDialog(insertVehicleFrame, "Error inserting vehicle");
                        logger.info("Employee with name: " + employee.getName()
                                + "NIF: " + employee.getNif() + " failed to registered a new vehicle with plate: " + plateText);
                    break;
                    }
                } else {
                    JOptionPane.showMessageDialog(insertVehicleFrame, "Please fill all the fields properly");
                    logger.info("Employee with name: " + employee.getName()
                            + "NIF: " + employee.getNif() + " failed to registered a new vehicle with plate: " + plateText);
                    break;
                }
            }
        });

        gbc.gridwidth = 1;
//        JLabel[] labels = {model, plate, registrationDate,
//                vin, nif, category, brand, color};
//        JTextField[] textFields = {modelField, plateField,
//                registrationDateField, vinField, nifField};

        JLabel[] labels2 = {model, plate,
                vin, nif, category, brand, color, registrationDate};
        JTextField[] textFields2 = {modelField, plateField,
                vinField, nifField};

        for (int row = 0; row < 8; row++) {
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            insertVehiclePanel.add(labels2[row], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            if (row == 4) {
                insertVehiclePanel.add(categoryField, gbc);
            } else if (row == 5) {
                insertVehiclePanel.add(brandField, gbc);
            } else if (row == 6) {
                insertVehiclePanel.add(colorField, gbc);

            } else if (row == 7) {
                insertVehiclePanel.add(registrationDateField, gbc);
            } else {
                insertVehiclePanel.add(textFields2[row], gbc);
            }
        }

        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.RELATIVE;
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 6, 10);
        insertVehiclePanel.add(submit, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
        insertVehiclePanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        insertVehiclePanel.add(backButton, gbc);

        insertVehicleFrame.add(insertVehiclePanel);
        insertVehicleFrame.setVisible(true);

        insertVehicleFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("Employee with name: " + employee.getName()
                        + "NIF: " + employee.getNif() + " logged out");
                dataSource.close();
            }
        });
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("C:\\Users\\PedroOriakhi\\OneDrive - Polarising, Unipessoal, Lda\\Documentos\\GitHub\\IMTT-alike\\resources\\log4j.properties");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Employee employee = new Employee();
                employee.setName("John Doe");
                employee.setAccess_level(2);
                MenuEmployeeManager menuEmployeeManager = new MenuEmployeeManager(employee);
            }
        });
    }

}
