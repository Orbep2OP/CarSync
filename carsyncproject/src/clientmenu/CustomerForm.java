package clientmenu;

import javax.swing.*;
import java.awt.*;

public class CustomerForm extends JFrame {

    private int nifNum;

    public CustomerForm(int nifNum) {
        this.nifNum = nifNum;

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Customer Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        panel.add(welcomeLabel, gbc);

        JButton viewMenuButton = new JButton("View Menu");
        viewMenuButton.setBackground(new Color(6, 65, 16));
        viewMenuButton.setForeground(Color.white);
        viewMenuButton.setPreferredSize(new Dimension(250, 40));
        viewMenuButton.addActionListener(e -> {
            openViewMenuForm();
            dispose();
        });
        gbc.gridy = 1;
        panel.add(viewMenuButton, gbc);

        JButton insertMenuButton = new JButton("Registration Menu");
        insertMenuButton.setBackground(new Color(6, 65, 16));
        insertMenuButton.setForeground(Color.white);
        insertMenuButton.setPreferredSize(new Dimension(250, 40));
        insertMenuButton.addActionListener(e -> {
            openInsertMenuForm();
            dispose();
        });
        gbc.gridy = 2;
        panel.add(insertMenuButton, gbc);

        JButton updateMenuButton = new JButton("Update Menu");
        updateMenuButton.setBackground(new Color(6, 65, 16));
        updateMenuButton.setForeground(Color.white);
        updateMenuButton.setPreferredSize(new Dimension(250, 40));
        updateMenuButton.addActionListener(e -> {
            openUpdateMenuForm();
            dispose();
        });
        gbc.gridy = 3;
        panel.add(updateMenuButton, gbc);

        JButton deleteMenuButton = new JButton("Deactivate Menu");
        deleteMenuButton.setBackground(new Color(6, 65, 16));
        deleteMenuButton.setForeground(Color.white);
        deleteMenuButton.setPreferredSize(new Dimension(250, 40));
        deleteMenuButton.addActionListener(e -> {
            openDeactivateMenuForm();
            dispose();
        });
        gbc.gridy = 4;
        panel.add(deleteMenuButton, gbc);

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(new Color(32, 32, 32));
        logoutButton.setForeground(Color.white);
        logoutButton.setPreferredSize(new Dimension(250, 40));
        logoutButton.addActionListener(e -> {
            dispose();
            new WelcomeMenuForm();
        });
        gbc.gridy = 5;
        panel.add(logoutButton, gbc);

        add(panel);
    }


    private void openViewMenuForm() {
        SwingUtilities.invokeLater(() -> {
            ViewMenuForm viewMenuForm = new ViewMenuForm(nifNum);
            viewMenuForm.setVisible(true);
        });
    }

    private void openInsertMenuForm() {
        SwingUtilities.invokeLater(() -> {
            InsertMenuForm insertMenuForm = new InsertMenuForm(nifNum);
            insertMenuForm.setVisible(true);
        });
    }

    private void openUpdateMenuForm() {
        SwingUtilities.invokeLater(() -> {
            UpdateMenuForm updateMenuForm = new UpdateMenuForm(nifNum);
            updateMenuForm.setVisible(true);
        });
    }

    private void openDeactivateMenuForm() {
        SwingUtilities.invokeLater(() -> {
            DeactivateMenuForm deactivateMenuForm = new DeactivateMenuForm(nifNum);
            deactivateMenuForm.show();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerForm customerForm = new CustomerForm(260856140);
            customerForm.setVisible(true);
        });
    }
}
