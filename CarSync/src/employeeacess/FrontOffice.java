package employeeacess;

import model.Customer;
import model.Insurance;
import model.Ticket;
import model.Vehicle;

import java.util.List;
import java.util.Scanner;

public class FrontOffice {
    private DataSource dataSource;
    private Customer customer;
    private Scanner scan;

    public FrontOffice(DataSource dataSource, Customer customer) {
        this.dataSource = dataSource;
        this.customer = customer;
        this.scan = new Scanner(System.in);
        showMenu();

    }

    public static FrontOffice startFrontOffice(int customerNif) {
        DataSource dataSource = new DataSource();

        if (!dataSource.open()) {
            throw new IllegalStateException("Cannot connect to database");
        }

        Customer customer = dataSource.getCustomerByNIF(customerNif);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found for NIF: " + customerNif);
        }

        return new FrontOffice(dataSource, customer);
    }


    public void viewCustomerVehicles() {
        List<Vehicle> customerVehicles = dataSource.getVehicleByNIF(customer.getNif());

        if (customerVehicles == null || customerVehicles.isEmpty()) {
            System.out.println("No vehicles found for the customer.");
        } else {
            System.out.println("Vehicles for Customer " + customer.getNif() + ":");
            for (Vehicle vehicle : customerVehicles) {
                System.out.println(vehicle);
            }
        }
    }

// public void viewCustomerInsurances() {


// List<Insurance> insurances = dataSource.queryInsurancesByNIF(customer.getNif());

// if (insurances == null || insurances.isEmpty()) {
// System.out.println("No insurances found for the customer.");
// } else {
// System.out.println("Insurances for Customer " + customer.getNif() + ":");
// for (Insurance insurance : insurances) {
// System.out.println(insurance);
// }
// }

// }

    private void viewCustomerTickets() {
        List<Ticket> customerTickets = dataSource.queryTickets();

        if (customerTickets == null || customerTickets.isEmpty()) {
            System.out.println("No tickets found for the customer.");
        } else {
            System.out.println("Tickets for Customer " + customer.getNif() + ":");
            for (Ticket ticket : customerTickets) {
                System.out.println(ticket);
            }
        }
    }


    private void showMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Front Office Menu ===");
            System.out.println("1. View Menu");
            System.out.println("2. Insert Menu");
            System.out.println("3. Update Menu");
            System.out.println("4. Delete Menu");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    insertMenu();
                    break;
                case 3:
                    updateMenu();
                    break;
                case 4:
                    deleteMenu();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting Front Office...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== View Menu ===");
            System.out.println("1. View Vehicles");
            System.out.println("2. View Insurances");
            System.out.println("3. View Tickets");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewCustomerVehicles();
                    break;
                case 2:
                    viewCustomerInsurances();
                    break;
                case 3:
                    viewCustomerTickets();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting View Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewCustomerInsurances() {
        List<Vehicle> vehicleList = dataSource.queryVehicles();
        List<Insurance> insuranceList = dataSource.queryInsurances();
        for (Vehicle v : vehicleList) {
            if (v.getNif() == customer.getNif()) {
                for (Insurance t : insuranceList) {
                    if (t.getCarPlate().equals(v.getPlate())) {
                        System.out.println(t);
                        return;
                    }
                }
            }
        }
        System.out.println("No insurances to be displayed.");
    }

    private void insertMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Insert Menu ===");
            System.out.println("1. Insert Vehicle");
            System.out.println("2. Insert Insurance");
            System.out.println("3. Insert Ticket");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    insertVehicle();
                    break;
                case 2:
                    insertInsurance();
                    break;
                case 3:
                    insertTicket();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting Insert Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void insertVehicle() {
        System.out.println("Insert B to go back, anything else to continue");
        String s = scan.nextLine().trim();
        if (s.compareToIgnoreCase("B") == 0) {
            System.out.println("Going back to delete menu..." + "\n");
            return;
        }
        System.out.println("Insert the plate of the vehicle you want to insert: ");
        /*String plate = getPlate(scan);
        System.out.println("Insert the brand of the vehicle you want to insert: ");
        String brand = getString(scan);
        System.out.println("Insert the model of the vehicle you want to insert: ");
        String model = getString(scan);
        System.out.println("Insert the registration date of the vehicle you want to insert: ");
        java.sql.Date registrationDate = getDate(scan);
        System.out.println("Insert the VIN of the owner of the vehicle you want to insert: ");
        String vin = getVIN(scan);*/


    }

    private void insertInsurance() {
    }

    private void insertTicket() {
    }


    private void updateMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Update Menu ===");
            System.out.println("1. Update Vehicles");
            System.out.println("2. Update Insurances");
            System.out.println("3. Update Tickets");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    updateVehicles();
                    break;
                case 2:
                    updateInsurances();
                    break;
                case 3:
                    updateTickets();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting Update Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateTickets() {
    }

    private void updateInsurances() {
    }

    private void updateVehicles() {
    }

    private void deleteMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Front Office Menu ===");
            System.out.println("1. Delete Vehicles");
            System.out.println("2. Delete Insurances");
            System.out.println("3. Delete Tickets");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    deleteVehicles();
                    break;
                case 2:
                    deleteInsurances();
                    break;
                case 3:
                    deleteTickets();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting Delete Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void deleteTickets() {
    }

    private void deleteInsurances() {
    }

    private void deleteVehicles() {
    }


    public static void main(String[] args) {
//processo de login dps
        int customerNif = 200000001; /*dps meter o metodo de login que apanha o nif*/

        DataSource dataSource = new DataSource();

        if (!dataSource.open()) {
            throw new IllegalStateException("Cannot connect to database");
        }

        Customer customer = dataSource.getCustomerByNIF(customerNif);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found for NIF: " + customerNif);
        }

        FrontOffice frontOffice = new FrontOffice(dataSource, customer);
        frontOffice.showMenu();
    }
}