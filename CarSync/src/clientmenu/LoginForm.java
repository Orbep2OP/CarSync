package clientmenu;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

import employeeacess.BackOfficeAdminMenu;
import employeeacess.DataSource;
import employeeacess.MenuEmployee;
import employeeacess.MenuEmployeeManager;
import model.Customer;
import model.Employee;
import org.mindrot.jbcrypt.BCrypt;
import clientmenu.*;

public class LoginForm extends JPanel {

    public static final String DB_NAME = "projeto_imt";
    public static final int PORT_NUMBER = 3306;
    public static final String URL = "jdbc:mysql://localhost:" + PORT_NUMBER + "/" + DB_NAME;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private JTextField nifField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private int loggedInNif = -1;

    public LoginForm() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel headerLabel = new JLabel("Login");
        headerLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(headerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        contentPanel.add(new JLabel("NIF:"), gbc);
        gbc.gridx = 1;
        nifField = new JTextField();
        nifField.setPreferredSize(new Dimension(250, 40));
        contentPanel.add(nifField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        contentPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 40));
        contentPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(6, 65, 16));
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setForeground(Color.white);
        contentPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBackground(new Color(32, 32, 32));
        goBackButton.setForeground(Color.white);
        goBackButton.setPreferredSize(new Dimension(120, 40));
        contentPanel.add(goBackButton, gbc);

        goBackButton.addActionListener(e -> handleGoBackButton());
        loginButton.addActionListener(e -> performLogin());

        gbc.gridy = 0;
        add(new JPanel(), gbc);
        gbc.gridy = 1;
        add(contentPanel, gbc);
        gbc.gridy = 2;
        add(new JPanel(), gbc);
    }

    private void performLogin() {
        String nif = nifField.getText();
        String password = new String(passwordField.getPassword());
        String result = authenticateUser(nif, password);
        if (result.equals("Success")) {
            DataSource dataSource = new DataSource();
            if(dataSource.open()) {
                if(dataSource.isCustomer(loggedInNif)) {
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(LoginForm.this);
                    currentFrame.dispose();
                    CustomerForm customerForm = new CustomerForm(loggedInNif);
                    customerForm.setVisible(true);
                    dataSource.close();
                } else {
                    for(Employee employee: dataSource.queryEmployees()) {
                        if(employee.getNif() == loggedInNif) {
                            switch (employee.getAccess_level()) {
//                                Logger logger = Logger.getLogger("Back Office Log");
                                case 0 -> {
                                    dataSource.close();
                                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                                    frame.setVisible(false);
                                    frame.dispose();
                                    new MenuEmployee(employee);
                                }
                                case 1 -> {
                                    dataSource.close();
                                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                                    frame.setVisible(false);
                                    frame.dispose();
                                    new MenuEmployeeManager(employee);
                                }
                                case 2 -> {
                                    dataSource.close();
                                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                                    frame.setVisible(false);
                                    frame.dispose();
                                    Logger logger = Logger.getLogger("Back Office Log");
                                    new BackOfficeAdminMenu(employee);
                                }
                                default -> {
                                    dataSource.close();
                                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                                    frame.setVisible(false);
                                    frame.dispose();
                                    System.out.println("Error: Invalid access level.");
                                    new WelcomeMenuForm();
                                }
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            showLoginErrorMessage(result);
        }
    }

    private String authenticateUser(String nif, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DataSource dataSource = new DataSource();

        if(!dataSource.open()) {
            return "An error occurred. Please try again";
        }

        List<Customer> customerList = dataSource.queryCustomers();
        customerList.removeIf(Customer::isDeactivated);
        if(nif.matches("\\d{9}") && dataSource.isCustomerOrEmployee(Integer.parseInt(nif))) {
            if(dataSource.isCustomer(Integer.parseInt(nif))) {
                for (Customer customer : customerList) {
                    if (customer.getNif() == Integer.parseInt(nif)) {
                        String hashedPassword = customer.getPwd();
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            loggedInNif = Integer.parseInt(nif);
                            return "Success";
                        } else {
                            return "Invalid credentials. Please try again.";
                        }
                    }
                }
            } else {
                List<Employee> employeeList = dataSource.queryEmployees();
                employeeList.removeIf(Employee::isDeactivated);
                for (Employee employee : employeeList) {
                    if (employee.getNif() == Integer.parseInt(nif)) {
                        String hashedPassword = employee.getPwd();
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            loggedInNif = Integer.parseInt(nif);
                            return "Success";
                        } else {
                            return "Invalid credentials. Please try again.";
                        }
                    }
                }
            }
        }

//        try {
//            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            String sql = "SELECT password FROM person WHERE nif = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, nif);
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                String hashedPassword = rs.getString("password");
//                if (BCrypt.checkpw(password, hashedPassword)) {
//                    loggedInNif = Integer.parseInt(nif);
//                    return "Success";
//                } else {
//                    return "Invalid credentials. Please try again.";
//                }
//            } else {
//                return "Invalid credentials. Please try again.";
//            }
//        } catch (Exception e) {
//            return "An error occurred. Please try again later.";
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (pstmt != null) pstmt.close();
//                if (conn != null) conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return "Invalid credentials. Please try again.";
    }

    private void showLoginErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleGoBackButton() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        WelcomeMenuForm welcomeMenuForm = new WelcomeMenuForm(loggedInNif);
        welcomeMenuForm.show();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Form");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(new LoginForm());
            frame.setVisible(true);
        });
    }
}
