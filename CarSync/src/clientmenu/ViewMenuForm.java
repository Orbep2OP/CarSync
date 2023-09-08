package clientmenu;

import employeeacess.DataSource;
import model.Insurance;
import model.Ticket;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewMenuForm extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/projeto_imt";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private int nifNum;

    public ViewMenuForm(int nifNum) {
        this.nifNum = nifNum;
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeUI();
    }

    private void initializeUI() {
        setTitle("View Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel headerLabel = new JLabel("View Menu");
        headerLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(headerLabel, gbc);
        gbc.gridy = 1;
        JButton viewVehiclesButton = new JButton("View Vehicles");
        viewVehiclesButton.setBackground(new Color(6, 65, 16));
        viewVehiclesButton.setForeground(Color.white);
        viewVehiclesButton.setPreferredSize(new Dimension(120, 40));
        viewVehiclesButton.addActionListener(e -> viewCustomerVehicles());
        panel.add(viewVehiclesButton, gbc);
        gbc.gridy = 2;
        JButton viewInsurancesButton = new JButton("View Insurances");
        viewInsurancesButton.setBackground(new Color(6, 65, 16));
        viewInsurancesButton.setForeground(Color.white);
        viewInsurancesButton.setPreferredSize(new Dimension(120, 40));
        viewInsurancesButton.addActionListener(e -> viewCustomerInsurances());
        panel.add(viewInsurancesButton, gbc);
        gbc.gridy = 3;
        JButton viewTicketsButton = new JButton("View Tickets");
        viewTicketsButton.setBackground(new Color(6, 65, 16));
        viewTicketsButton.setForeground(Color.white);
        viewTicketsButton.setPreferredSize(new Dimension(120, 40));
        viewTicketsButton.addActionListener(e -> viewCustomerTickets());
        panel.add(viewTicketsButton, gbc);
        gbc.gridy = 4;
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBackground(new Color(32, 32, 32));
        goBackButton.setForeground(Color.white);
        goBackButton.setPreferredSize(new Dimension(120, 40));
        goBackButton.addActionListener(e -> handleGoBackButton());
        panel.add(goBackButton, gbc);
        add(panel);
    }

    private void viewCustomerVehicles() {
        List<String> vehicles = getVehiclesForCustomer(nifNum);
        if (vehicles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No vehicles found for the customer.");
        } else {
            StringBuilder message = new StringBuilder("Here are your vehicles customer " + nifNum + ":\n");
            for (String vehicle : vehicles) {
                message.append(vehicle).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString());
        }
    }

    private List<String> getVehiclesForCustomer(int nif) {
        List<String> vehicles = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM vehicle WHERE nif = ?")) {
            statement.setInt(1, nif);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                String regDate = resultSet.getString("registration_date");
                String plate = resultSet.getString("plate");
                String vin = resultSet.getString("vin");
                String category = resultSet.getString("category");
                String vehicleDetails = String.format("Brand: %s, Model: %s, Color: %s, Registration Date: %s, Plate: %s, VIN: %s, Category: %s", brand, model, color, regDate, plate, vin, category);
                vehicles.add(vehicleDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private void viewCustomerInsurances() {
        List<String> insurances = getInsurancesForCustomer();
        if (insurances.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No insurances found for the customer.");
        } else {
            StringBuilder message = new StringBuilder("Here are your insurances customer " + nifNum + ":\n");
            for (String insurance : insurances) {
                message.append(insurance).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString());
        }
    }

//    private List<String> getInsurancesForCustomer(int nif) {
//        List<String> insurances = new ArrayList<>();
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM insurance WHERE nif = ?")) {
//            statement.setInt(1, nif);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                String policy = resultSet.getString("policy");
//                String expiryDate = resultSet.getString("expiry_date");
//                String company = resultSet.getString("company");
//                String startDate = resultSet.getString("start_date");
//                String extraCategory = resultSet.getString("extra_category");
//                String insuranceDetails = String.format("Policy: %s, Expiry Date: %s, Company: %s, Start Date: %s, Extra Category: %s", policy, expiryDate, company, startDate, extraCategory);
//                insurances.add(insuranceDetails);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return insurances;
//    }


    private List<String> getInsurancesForCustomer() {
        DataSource dataSource = new DataSource();
        List<String> insurances = new ArrayList<>();
        if(dataSource.open()) {
            dataSource.queryInsurances().forEach(i -> {
                if(dataSource.insuranceExists(i.getPolicy(), nifNum) && !i.isDeactivated()) {
                    insurances.add(i.toString());
                }
            });
        }
        return insurances;
    }

    private void viewCustomerTickets() {

        JPanel ticketsPanel = new JPanel();

        ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));


        List<Ticket> ticketList = getTicketsForCustomerDetailed(nifNum);

        if (ticketList.isEmpty()) {

            JOptionPane.showMessageDialog(this, "No tickets found for the customer.");

            return;

        }


        for (Ticket ticket : ticketList) {

            JPanel ticketPanel = new JPanel(new FlowLayout());

            ticketPanel.add(new JLabel(ticket.toString()));


            if (!ticket.isPaid()) {

                JButton payButton = new JButton("Pay");

                payButton.addActionListener(e -> {
                    if(handleTicketPayment(ticket)) {
                        ticket.setPaid(0);
                    }
                });

                ticketPanel.add(payButton);

            } else {

                JLabel paidLabel = new JLabel("Paid");

                ticketPanel.add(paidLabel);

            }


            ticketsPanel.add(ticketPanel);

        }


        JScrollPane scrollPane = new JScrollPane(ticketsPanel);

        JOptionPane.showMessageDialog(this, scrollPane, "Your Tickets", JOptionPane.PLAIN_MESSAGE);

    }

    private List<Ticket> getTicketsForCustomerDetailed(int nif) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE nif = ?")) {
            statement.setInt(1, nif);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setNif(resultSet.getInt("nif"));
                ticket.setPlate(resultSet.getString("plate"));
                ticket.setDate(resultSet.getDate("date"));
                ticket.setExpiry_date(resultSet.getDate("expiry_date"));
                ticket.setValue(resultSet.getDouble("value"));
                ticket.setReason(resultSet.getString("reason"));
                ticket.setPaid(resultSet.getInt("paid"));
                ticket.setTicketID(resultSet.getInt("ticketID"));
                tickets.add(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }

    private boolean handleTicketPayment(Ticket ticket) {
        boolean result2 = false;
        if (ticket.isPaid()) {
            JOptionPane.showMessageDialog(this, "This ticket is already paid.");
            return result2;
        }
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to pay this ticket?", "Pay Ticket", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            result2 = true;
            payTicketInDatabase(ticket);
            JOptionPane.showMessageDialog(this, "Ticket paid successfully.");
        }
        return result2;
    }

    private void payTicketInDatabase(Ticket ticket) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE ticket SET paid = 0 WHERE ticketID = ?")) {
            statement.setInt(1, ticket.getTicketID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleGoBackButton() {
        this.dispose();
        CustomerForm customerForm = new CustomerForm(nifNum);
        customerForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewMenuForm viewMenuForm = new ViewMenuForm(-1);
            viewMenuForm.setVisible(true);
        });
    }
}
