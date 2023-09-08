package clientmenu;

import employeeacess.DataSource;
import employeeacess.ValidateInput;
import model.Task;
import model.TaskManagment;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Color.*;

public class UpdateMenuForm extends JFrame implements ValidateInput {

    private int nifNum;
    private final Color GREEN = new Color(0, 100, 0);
    private final Color RED = new Color(100, 0, 0);
    private final Color BLUE = new Color(0, 0, 100);

    public UpdateMenuForm(int nifNum) {
        this.nifNum = nifNum;
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Update Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel headerLabel = new JLabel("Update Menu");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(headerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 1;
        JButton updateCarButton = new JButton("Update Car Details");
        updateCarButton.setBackground(new Color(6, 65, 16));
        updateCarButton.setPreferredSize(new Dimension(180, 40));
        updateCarButton.setForeground(Color.white);
        contentPanel.add(updateCarButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 1;
        JButton updateDetailsButton = new JButton("Update Personal Details");
        updateDetailsButton.setBackground(new Color(6, 65, 16));
        updateDetailsButton.setPreferredSize(new Dimension(180, 40));
        updateDetailsButton.setForeground(Color.white);
        contentPanel.add(updateDetailsButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBackground(new Color(32, 32, 32));
        goBackButton.setForeground(Color.white);
        goBackButton.setPreferredSize(new Dimension(120, 40));
        contentPanel.add(goBackButton, gbc);

        updateCarButton.addActionListener(e -> handleUpdateCarDetails());
        goBackButton.addActionListener(e -> handleGoBackButton());
        updateDetailsButton.addActionListener(e -> updateClientDetails());

        GridBagConstraints frameGbc = new GridBagConstraints();
//        frameGbc.gridy = 0;
//        add(new JPanel(), frameGbc);
//        frameGbc.gridy = 1;
//        add(contentPanel, frameGbc);
//        frameGbc.gridy = 2;
//        add(new JPanel(), frameGbc);

        add(contentPanel);
        setVisible(true);
    }

    private void updateClientDetails() {
        JFrame updatePersonFrame = new JFrame("CarSync");
        updatePersonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updatePersonFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        DataSource dataSource = new DataSource();
        if(!dataSource.open()){
            JOptionPane.showMessageDialog(this, "Error, going back to update page", "Error", JOptionPane.ERROR_MESSAGE);
            updatePersonFrame.dispose();
            this.dispose();
            new UpdateMenuForm(nifNum);
        }

//        JButton exitButton = new JButton("Sign Out");
//        exitButton.setBackground(RED);
//        exitButton.setForeground(WHITE);
        JButton backButton = new JButton("Back");
        backButton.setBackground(BLACK);
        backButton.setForeground(WHITE);
        JButton submit = new JButton("Submit");
        submit.setBackground(GREEN);
        submit.setForeground(WHITE);
        backButton.addActionListener(e -> {
            updatePersonFrame.setVisible(false);
            updatePersonFrame.dispose();
            this.dispose();
            dataSource.close();
            new UpdateMenuForm(nifNum);
        });
//        exitButton.addActionListener(e -> {
//            updatePersonFrame.dispose();
//            this.dispose();
//            dataSource.close();
//            new WelcomeMenuForm();
//        });

        //SET UP for exit buttons
        JPanel updatePersonPanel = new JPanel(new GridBagLayout());

        JLabel updatePersonLabel = new JLabel("Request Details Update");
        updatePersonLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        updatePersonPanel.add(updatePersonLabel, gbc);

//        JLabel nifLabel = new JLabel("NIF: ");
//        JTextField nifField = new JTextField(15);
//        JComboBox nifField = new JComboBox(arrayInfo(dataSource.queryCustomers()));

        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField(15);

        JButton submitButton1 = new JButton("Change Email");
        submitButton1.setBackground(GREEN);
        submitButton1.setForeground(Color.WHITE);
        submitButton1.addActionListener(e -> {
//            String nif = nifField.getSelectedItem().toString();
//            String nif = nifField.getText();
            String email = emailField.getText();

            TaskManagment tk = new TaskManagment();
            if (!isEmail(email)) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Wrong email format");
            } else {
                if (tk.createTask("Update Customer Email", nifNum, String.valueOf(nifNum), email)) {
                    JOptionPane.showMessageDialog(updatePersonFrame, "Request Succefully created",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(updatePersonFrame, "Error creating request", "Error", JOptionPane.ERROR_MESSAGE);
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
            TaskManagment tk = new TaskManagment();
//            String nif = nifField.getText();
             if (!isPassword(passwordField.getText())) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Wrong password format");
            } else {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
                if (tk.createTask("Update Customer Password", nifNum, String.valueOf(nifNum), password)) {
                    JOptionPane.showMessageDialog(updatePersonFrame, "Request Succefully created",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(updatePersonFrame, "Error creating request", "Error", JOptionPane.ERROR_MESSAGE);
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
//            String nif = nifField.getText();
            TaskManagment tk = new TaskManagment();
            if (!isValidString(address)) {
                JOptionPane.showMessageDialog(updatePersonFrame, "Wrong address format");
            } else {
                if (tk.createTask("Update Customer Address", nifNum, String.valueOf(nifNum), address)) {
                    JOptionPane.showMessageDialog(updatePersonFrame, "Request Succefully created",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(updatePersonFrame, "Error Creating Request", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Montar janela
        gbc.gridwidth = 1;
        JLabel[] labels = {emailLabel, passwordLabel, addressLabel};
//        JComboBox[] comboBoxes = {nifField};
        JTextField[] textFields = {emailField, passwordField, addressField};
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
//            if (row != 0) {
                gbc.gridx = 2;
                gbc.anchor = GridBagConstraints.LINE_END;
                updatePersonPanel.add(buttons[row], gbc);
//            }
        }

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(21, 10, 5, 10);
//        updatePersonPanel.add(exitButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.insets = new Insets(21, 10, 5, 10);
        updatePersonPanel.add(backButton, gbc);

        updatePersonFrame.add(updatePersonPanel);
//        JOptionPane.showConfirmDialog(this, updatePersonPanel, "Update Personal Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        updatePersonFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataSource.close();
                updatePersonFrame.dispose();
                System.exit(0);
            }
        });

    }

    private void handleUpdateCarDetails() {
        JPanel updateVehiclePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel updateVehicleLabel = new JLabel("Update Vehicle Color");
        updateVehicleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        updateVehiclePanel.add(updateVehicleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel plateLabel = new JLabel("Plate: ");
        updateVehiclePanel.add(plateLabel, gbc);

        gbc.gridx = 1;
        JTextField plateField = new JTextField(15);
        updateVehiclePanel.add(plateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel colorLabel = new JLabel("Color: ");
        updateVehiclePanel.add(colorLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> colorField = new JComboBox<>(new String[]{" ", "Black", "White", "Red", "Blue", "Green", "Yellow", "Gray", "Silver", "Brown", "Orange"});
        updateVehiclePanel.add(colorField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String plateText = plateField.getText();
            String colorText = (String) colorField.getSelectedItem();
            if (!isPlate(plateText)) {
                JOptionPane.showMessageDialog(this, "Please write a valid plate with format XX-XX-XX");
            } else {
                TaskManagment taskManager = new TaskManagment();
                taskManager.createTask("Update Vehicle Color", nifNum, plateText, colorText);
                JOptionPane.showMessageDialog(this, "Task created successfully.");
            }
        });
        updateVehiclePanel.add(submitButton, gbc);

        JOptionPane.showConfirmDialog(this, updateVehiclePanel, "Update Vehicle Color Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    private void handleGoBackButton() {
        this.dispose();
        CustomerForm customerForm = new CustomerForm(nifNum);
        customerForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateMenuForm updateMenuForm = new UpdateMenuForm(-1);
//            updateMenuForm.setVisible(true);
        });
    }
}
