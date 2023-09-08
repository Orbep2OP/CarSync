package employeeacess;

import model.*;
import org.mindrot.jbcrypt.BCrypt;
import util.UserWriterAux;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Para Fazer login chamar método authenticateUser e fazer a verificação; de seguida chamar o método getDummy
 * Falta SQL para:
 * - Insert, Update e Delete de Customers e Employees
 * - O update do customer corresponde a apenas atualizar a data de validade e a
 * data de inicio.
 */
public class DataSource {

    public static final String DB_NAME = "projeto_imt";

    //Este port number é o port number que aparece no XAMPP quando voçês dão start
    //para conectar à base de dados e no meu caso é 3306.
    public static final int PORT_NUMBER = 3306;
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:" + PORT_NUMBER + "/" + DB_NAME;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private PreparedStatement queryVehicles;
    private PreparedStatement queryInsurances;
    private PreparedStatement queryTickets;
    private PreparedStatement queryVehicleByPlate;
    private PreparedStatement queryCustomers;
    private PreparedStatement queryEmployee;
    private PreparedStatement queryPerson;

    private PreparedStatement insertIntoVehicle;
    private PreparedStatement insertIntoInsurance;
    private PreparedStatement insertIntoTicket;
    private PreparedStatement insertIntoCustomer;
    private PreparedStatement insertIntoEmployee;
    private PreparedStatement insertIntoPerson;

    private PreparedStatement updateVehicleColor;
    private PreparedStatement renewInsurance;
    private PreparedStatement updateTicket;
    private PreparedStatement updateVehicleOwner;
    private PreparedStatement payTicket;
    private PreparedStatement updateEmployeeAccessLevel;
    private PreparedStatement updatePersonPassword;
    private PreparedStatement updatePersonAddress;
    private PreparedStatement updateCustomerDriverLicenseDates;
    private PreparedStatement updatePersonEmail;

    private PreparedStatement deleteVehicle;
    private PreparedStatement deleteInsurance;
    private PreparedStatement deleteTicket;
    private PreparedStatement deletePerson;

    private PreparedStatement deactivateEmployee;
    private PreparedStatement deactivateCustomer;
    private PreparedStatement deactivateVehicle;
    private PreparedStatement deactivateInsurance;
    private PreparedStatement deactivateTicket;
    private PreparedStatement deactivatePerson;


    private Connection connection;

    public boolean open() {

        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            insertIntoInsurance = connection.prepareStatement(InsuranceEnum.getString(InsuranceEnum.INSERT_INSURANCE));
            insertIntoVehicle = connection.prepareStatement(VehicleEnum.getString(VehicleEnum.INSERT_VEHICLE));
            insertIntoTicket = connection.prepareStatement(TicketEnum.getString(TicketEnum.INSERT_TICKET));
            insertIntoEmployee = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.INSERT_INTO_TABLE_EMPLOYEE));
            insertIntoPerson = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.INSERT_INTO_PERSON));
            insertIntoCustomer = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.INSERT_INTO_TABLE_CUSTOMER));

            queryInsurances = connection.prepareStatement(InsuranceEnum.getString(InsuranceEnum.QUERY_TABLE_INSURANCE));
            queryTickets = connection.prepareStatement(TicketEnum.getString(TicketEnum.QUERY_TABLE_TICKET));
            queryVehicles = connection.prepareStatement(VehicleEnum.getString(VehicleEnum.QUERY_TABLE_VEHICLE));
            queryCustomers = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.QUERY_TABLE_CUSTOMER));
            queryVehicleByPlate = connection.prepareStatement(VehicleEnum.getString(VehicleEnum.QUERY_TABLE_VEHICLE_BY_PLATE));
            queryEmployee = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.QUERY_TABLE_EMPLOYEE));
            queryPerson = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.QUERY_TABLE_PERSON));

            renewInsurance = connection.prepareStatement(InsuranceEnum.getString(InsuranceEnum.RENEW_INSURANCE));
            updateVehicleColor = connection.prepareStatement(VehicleEnum.getString(VehicleEnum.UPDATE_VEHICLE_COLOR));
            updateVehicleOwner = connection.prepareStatement(VehicleEnum.getString(VehicleEnum.UPDATE_VEHICLE_OWNER));
            payTicket = connection.prepareStatement(TicketEnum.getString(TicketEnum.PAY_TICKET));
            updateEmployeeAccessLevel = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.UPDATE_EMPLOYER_ACCESS_LEVEL));
            updatePersonPassword = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.UPDATE_PERSON_PWD));
            updatePersonAddress = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.UPDATE_PERSON_ADDRESS));
            updateCustomerDriverLicenseDates = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.UPDATE_CUSTOMER_DRIVER_LICENSE_DATES));
            updatePersonEmail = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.UPDATE_PERSON_EMAIL));

            //Serve para atualizar o valor da multa e a data de validade quando o mesmo for necessário.
            updateTicket = connection.prepareStatement(TicketEnum.getString(TicketEnum.UPDATE_TICKET));

            deleteVehicle = connection.prepareStatement(VehicleEnum.getString(VehicleEnum.DELETE_VEHICLE));
            deleteInsurance = connection.prepareStatement(InsuranceEnum.getString(InsuranceEnum.DELETE_INSURANCE));
            deleteTicket = connection.prepareStatement(TicketEnum.getString(TicketEnum.DELETE_TICKET));
            deletePerson = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.DELETE_FROM_PERSON));


            deactivateCustomer = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.DEACTIVATE_CUSTOMER));
            deactivateVehicle = connection.prepareStatement(VehicleEnum.getString(VehicleEnum.DEACTIVATE_VEHICLE));
            deactivateInsurance = connection.prepareStatement(InsuranceEnum.getString(InsuranceEnum.DEACTIVATE_INSURANCE));
            deactivateTicket = connection.prepareStatement(TicketEnum.getString(TicketEnum.DEACTIVATE_TICKET));
            deactivatePerson = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.DEACTIVATE_PERSON));
            deactivateEmployee = connection.prepareStatement(PersonsEnum.getString(PersonsEnum.DEACTIVATE_EMPLOYEE));

            return true;
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {

            if (insertIntoTicket != null) {
                insertIntoTicket.close();
            }

            if (insertIntoInsurance != null) {
                insertIntoInsurance.close();
            }

            if (insertIntoVehicle != null) {
                insertIntoVehicle.close();
            }

            if (insertIntoCustomer != null) {
                insertIntoCustomer.close();
            }

            if (insertIntoEmployee != null) {
                insertIntoEmployee.close();
            }

            if (insertIntoPerson != null) {
                insertIntoPerson.close();
            }

            if (queryVehicles != null) {
                queryVehicles.close();
            }

            if (queryPerson != null) {
                queryPerson.close();
            }

            if (queryTickets != null) {
                queryTickets.close();
            }

            if (queryInsurances != null) {
                queryInsurances.close();
            }

            if (renewInsurance != null) {
                renewInsurance.close();
            }

            if (updateVehicleColor != null) {
                updateVehicleColor.close();
            }

            if (payTicket != null) {
                payTicket.close();
            }

            if (updateVehicleOwner != null) {
                updateVehicleOwner.close();
            }

            if (updateTicket != null) {
                updateTicket.close();
            }

            if (updateEmployeeAccessLevel != null) {
                updateEmployeeAccessLevel.close();
            }

            if (updatePersonPassword != null) {
                updatePersonPassword.close();
            }

            if (updatePersonAddress != null) {
                updatePersonAddress.close();
            }

            if (updateCustomerDriverLicenseDates != null) {
                updateCustomerDriverLicenseDates.close();
            }

            if (updatePersonEmail != null) {
                updatePersonEmail.close();
            }

            if (queryCustomers != null) {
                queryCustomers.close();
            }

            if (deletePerson != null) {
                deletePerson.close();
            }

            if (deleteTicket != null) {
                deleteTicket.close();
            }

            if (deleteInsurance != null) {
                deleteInsurance.close();
            }

            if (deleteVehicle != null) {
                deleteVehicle.close();
            }

            if (queryVehicleByPlate != null) {
                queryVehicleByPlate.close();
            }

            if (queryEmployee != null) {
                queryEmployee.close();
            }

            if (deactivateTicket != null) {
                deactivateTicket.close();
            }

            if (deactivateInsurance != null) {
                deactivateInsurance.close();
            }

            if (deactivateVehicle != null) {
                deactivateVehicle.close();
            }

            if (deactivateCustomer != null) {
                deactivateCustomer.close();
            }

            if (deactivatePerson != null) {
                deactivatePerson.close();
            }

            if (deactivateEmployee != null) {
                deactivateEmployee.close();
            }

            if (connection != null) {
                connection.close();
                System.out.println("Connection successfully closed");
            }

        } catch (SQLException e) {
            System.out.println("Couldn't close connection to database: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Devolve um arraylist com todos os veículos que estão na base de dados.
    //TESTED
    public List<Vehicle> queryVehicles() {

        List<Vehicle> vehicles = new ArrayList<>();
        try {
            ResultSet resultSet = queryVehicles.executeQuery();
            while (resultSet.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setPlate(resultSet.getString(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_PLATE)));
                vehicle.setCategory(resultSet.getString(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_CATEGORY)));
                vehicle.setBrand(resultSet.getString(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_BRAND)));
                vehicle.setColor(resultSet.getString(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_COLOR)));
                vehicle.setModel(resultSet.getString(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_MODEL)));
                vehicle.setRegistrationDate(resultSet.getDate(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_REGISTRATION_DATE)));
                vehicle.setVin(resultSet.getString(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_VIN)));
                vehicle.setNif(resultSet.getInt(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_NIF)));
                vehicle.setDeactivated(resultSet.getInt(VehicleEnum.getString(VehicleEnum.COLUMN_VEHICLE_DEACTIVATED)));
                vehicles.add(vehicle);
            }
            return vehicles;
        } catch (SQLException e) {
            System.out.println("Couldn't retrieve data from vehicle table: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Devolve um arraylist com todos os seguros que estão na base de dados.
    //TESTED
    public List<Insurance> queryInsurances() {
        List<Insurance> insurances = new ArrayList<>();
        try {
            ResultSet resultSet = queryInsurances.executeQuery();
            while (resultSet.next()) {
                Insurance insurance = new Insurance();
                insurance.setPolicy(resultSet.getInt(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_POLICY)));
                insurance.setCarPlate(resultSet.getString(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_PLATE)));
                insurance.setStartDate(resultSet.getDate(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_START_DATE)));
                insurance.setExtraCategory(resultSet.getString(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_EXTRA_CATEGORY)));
                insurance.setExpDate(resultSet.getDate(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_EXPIRY_DATE)));
                insurance.setCompanyName(resultSet.getString(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_COMPANY)));
                insurance.setDeactivated(resultSet.getInt(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_DEACTIVATED)));
                insurances.add(insurance);
            }
            insurances.forEach(i -> i.renew());
            return insurances;

        } catch (SQLException e) {
            System.out.println("Couldn't retrieve data from insurance table: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    //  public List<Insurance> queryInsurancesByNIF(int nif) {
    //    List<Insurance> insurances = new ArrayList<>();
    //  try {
    //    PreparedStatement preparedStatement = connection.prepareStatement(
    //         "SELECT * FROM insurance WHERE customer_nif = ?"
    //);
    //preparedStatement.setInt(1, nif);
    //ResultSet resultSet = preparedStatement.executeQuery();
    //while (resultSet.next()) {
    //Insurance insurance = new Insurance();
    //insurance.setPolicy(resultSet.getInt(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_POLICY)));
    //insurance.setCarPlate(resultSet.getString(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_PLATE)));
    //insurance.setStartDate(resultSet.getDate(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_START_DATE)));
    //insurance.setExtraCategory(resultSet.getInt(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_EXTRA_CATEGORY)));
    //insurance.setExpDate(resultSet.getDate(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_EXPIRY_DATE)));
    //insurance.setCompanyName(resultSet.getString(InsuranceEnum.getString(InsuranceEnum.COLUMN_INSURANCE_COMPANY)));
    //insurances.add(insurance);
    //    }
    //    return insurances;
    //  } catch (SQLException e) {
    //   System.out.println("Couldn't retrieve insurances for NIF " + nif + ": " + e.getMessage());
    //  e.printStackTrace();
    // return null;
    // }
    //}

    //Devolve um arraylist com todas as multas que estão na base de dados.
    //TESTED
    public List<Ticket> queryTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            ResultSet resultSet = queryTickets.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setNif(resultSet.getInt(TicketEnum.getString(TicketEnum.COLUMN_TICKET_NIF)));
                ticket.setPlate(resultSet.getString(TicketEnum.getString(TicketEnum.COLUMN_TICKET_PLATE)));
                ticket.setDate(resultSet.getDate(TicketEnum.getString(TicketEnum.COLUMN_TICKET_DATE)));
                ticket.setReason(resultSet.getString(TicketEnum.getString(TicketEnum.COLUMN_TICKET_REASON)));
                ticket.setValue(resultSet.getDouble(TicketEnum.getString(TicketEnum.COLUMN_TICKET_VALUE)));
                ticket.setExpiry_date(resultSet.getDate(TicketEnum.getString(TicketEnum.COLUMN_TICKET_EXPIRY_DATE)));
                ticket.setPaid(resultSet.getInt(TicketEnum.getString(TicketEnum.COLUMN_TICKET_PAID)));
                ticket.setTicketID(resultSet.getInt(TicketEnum.getString(TicketEnum.COLUMN_TICKET_ID)));
                ticket.setDeactivated(resultSet.getInt(TicketEnum.getString(TicketEnum.COLUMN_TICKET_DEACTIVATED)));
                tickets.add(ticket);
            }
            tickets.forEach(t -> t.renew());
            return tickets;

        } catch (SQLException e) {
            System.out.println("Couldn't retrieve data from ticket table: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Com hashmap
    public Map<Integer, Customer> queryCustomersHashMap() {
        Map<Integer, Customer> customers = new HashMap();
        try {
            ResultSet resultSet = queryCustomers.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                int nif = resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_NIF));
                customer.setNif(nif);
                customer.setExpDate(resultSet.getDate(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_EXPIRATION_DATE)));
                customer.setLicenseType(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_LICENSE_TYPE)));
                customer.setStartingDate(resultSet.getDate(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_START_DATE)));
                customer.setDriverLicenseNum(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_DRIVER_LICENSE)));
                customer.setDeactivated(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_DEACTIVATED)));
                customers.put(nif, customer);
            }

            resultSet = queryPerson.executeQuery();

            while (resultSet.next()) {
                for (Customer customer : customers.values()) {
                    if (customer.getNif() == resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_NIF))) {
                        customer.setName(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_NAME)));
                        customer.setAddress(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_ADDRESS)));
                        customer.setBirht_date(resultSet.getDate(PersonsEnum.getString(PersonsEnum.COLUMN_BIRTH_DATE)));
                        customer.setEmail(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_EMAIL)));
                        customer.setPwd(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_PWD)));
                        customer.setDeactivated(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_DEACTIVATED)));
                    }
                }
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Couldn't retrieve data from customer table: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //TESTED
    public List<Customer> queryCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = queryCustomers.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setNif(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_NIF)));
                customer.setExpDate(resultSet.getDate(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_EXPIRATION_DATE)));
                customer.setLicenseType(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_LICENSE_TYPE)));
                customer.setStartingDate(resultSet.getDate(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_START_DATE)));
                customer.setDriverLicenseNum(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_CUSTOMER_DRIVER_LICENSE)));
                customer.setDeactivated(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_DEACTIVATED)));
                customers.add(customer);
            }

            resultSet = queryPerson.executeQuery();

            while (resultSet.next()) {
                for (Customer customer : customers) {
                    if (customer.getNif() == resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_NIF))) {
                        customer.setName(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_NAME)));
                        customer.setAddress(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_ADDRESS)));
                        customer.setBirht_date(resultSet.getDate(PersonsEnum.getString(PersonsEnum.COLUMN_BIRTH_DATE)));
                        customer.setPwd(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_PWD)));
                        customer.setEmail(resultSet.getString(PersonsEnum.getString(PersonsEnum.COLUMN_EMAIL)));
                    }
                }
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Couldn't retrieve data from customer table: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Employee> queryEmployees() {

        List<Employee> employees = new ArrayList<>();
        try {
            ResultSet resultSet = queryEmployee.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setAccess_level(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_EMPLOYEE_ACCESS_LEVEL)));
                employee.setNif(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_NIF)));
                employee.setDeactivated(resultSet.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_DEACTIVATED)));
                employees.add(employee);
            }

            ResultSet resultSet1 = queryPerson.executeQuery();

            while (resultSet1.next()) {
                for (Employee employee : employees) {
                    if (employee.getNif() == resultSet1.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_NIF))) {
                        employee.setName(resultSet1.getString(PersonsEnum.getString(PersonsEnum.COLUMN_NAME)));
                        employee.setAddress(resultSet1.getString(PersonsEnum.getString(PersonsEnum.COLUMN_ADDRESS)));
                        employee.setBirht_date(resultSet1.getDate(PersonsEnum.getString(PersonsEnum.COLUMN_BIRTH_DATE)));
                        employee.setEmail(resultSet1.getString(PersonsEnum.getString(PersonsEnum.COLUMN_EMAIL)));
                        employee.setPwd(resultSet1.getString(PersonsEnum.getString(PersonsEnum.COLUMN_PWD)));
                        employee.setDeactivated(resultSet1.getInt(PersonsEnum.getString(PersonsEnum.COLUMN_DEACTIVATED)));
                    }
                }
            }

            return employees;

        } catch (SQLException e) {
            System.out.println("Couldn't retrieve data from employee table: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //TESTED
    public List<Vehicle> getVehicleByNIF(int nif) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Vehicle vehicle : queryVehicles()) {
            if (vehicle.getNif() == nif) {
                Vehicle vehicle1 = new Vehicle();
                vehicle1.setBrand(vehicle.getBrand());
                vehicle1.setCategory(vehicle.getCategory());
                vehicle1.setColor(vehicle.getColor());
                vehicle1.setModel(vehicle.getModel());
                vehicle1.setPlate(vehicle.getPlate());
                vehicle1.setVin(vehicle.getVin());
                vehicle1.setRegistrationDate(vehicle.getregistrationDate());
                vehicle1.setNif(vehicle.getNif());
                vehicles.add(vehicle1);
            }
        }
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found for nif: " + nif);
            return null;
        }
        return vehicles;
    }

    //TESTED
    public Customer getCustomerByNIF(int nif) {
        List<Customer> customers = queryCustomers();
        customers.removeIf(Customer::isDeactivated);
        for (Customer customer : customers) {
            if (customer.getNif() == nif) {
                Customer customer1 = new Customer();
                customer1.setNif(customer.getNif());
                customer1.setName(customer.getName());
                customer1.setAddress(customer.getAddress());
                customer1.setBirht_date(customer.getBirht_date());
                customer1.setExpDate(customer.getExpDate());
                customer1.setLicenseType(customer.getLicenseType());
                customer1.setPwd(customer.getPwd());
                customer1.setStartingDate(customer.getStartingDate());
                customer1.setEmail(customer.getEmail());
                return customer1;
            }
        }
        System.out.println("No customer found for nif: " + nif);
        return null;
    }

    //TESTED
    public boolean insertInsurance(int policy, String plate,
                                   Date startDate,
                                   String extraCategory, Date expDate,
                                   String companyName) {

        boolean worked = false;
        /*if (!isVehicleOwner(nif, plate)) {
            System.out.println("Not owner of vehicle with plate: " + plate);
            return worked;
        }*/

        for(Vehicle vehicle: queryVehicles()) {
            if(vehicle.getPlate().equals(plate) && vehicle.isDeactivated()) {
                System.out.println("Vehicle with plate: " + plate + " is deactivated");
                return worked;
            }
        }

        try {
            connection.setAutoCommit(false);
            insertIntoInsurance.setInt(4, policy);
            insertIntoInsurance.setString(2, plate);
            insertIntoInsurance.setDate(6, startDate);
            insertIntoInsurance.setString(5, extraCategory);
            insertIntoInsurance.setDate(3, expDate);
            insertIntoInsurance.setString(1, companyName);
            int affected = insertIntoInsurance.executeUpdate();

            if (affected == 1) {
                System.out.println("Inserted insurance with policy number: " + policy);
                connection.commit();
                worked = true;
            } else {
                throw new SQLException("Couldn't insert new insurance with policy number: " + policy);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't insert data into insurance table: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return worked;
    }


    //TESTED
    public boolean insertVehicle(String plate, String vin, String color,
                                 String brand, String model,
                                 Date registrationDate, String category, int nif) {

        boolean result = false;
        //Verfica se matricula é válida
        /*if (!plate.matches("^([0-9A-Z]{2}[\\-]{1}[0-9A-Z]{2}[\\-]{1}[0-9A-Z]{2})$") || categoryNumber <= 0 || categoryNumber > 6) {
            System.out.println("Wrong input: " + plate + " " + categoryNumber);
            return;
        }*/

        if(!isCustomer(nif)) {
            return false;
        }

        //Verifica se o veículo já existe na base de dados
        for (Vehicle v : queryVehicles()) {
            if (v.getPlate().equals(plate)) {
                System.out.printf("Plate number: %s already in database %n", v.getPlate());
                return result;
            }
        }

        //Confirma se o nif existe na base de dados
        if (!isCustomer(nif)) {
            System.out.println("Customer with nif: " + nif + " does not exist in database");
            return result;
        }

        try {
            connection.setAutoCommit(false);

            insertIntoVehicle.setString(1, model);
            insertIntoVehicle.setString(2, brand);
            insertIntoVehicle.setString(3, color);
            insertIntoVehicle.setString(4, plate);
            insertIntoVehicle.setString(5, category);
            insertIntoVehicle.setString(6, registrationDate.toString());
            insertIntoVehicle.setInt(7, nif);
            insertIntoVehicle.setString(8, vin);
            int affected = insertIntoVehicle.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Inserted vehicle with plate number: " + plate);
                connection.commit();
            } else {
                throw new SQLException("Couldn't insert new vehicle with plate number: " + plate);
            }

        } catch (SQLException e) {
            System.out.println("Insert vehicle exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Couldn't perform rollback after inserting vehicle went wrong");
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior after insert vehicle");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset default commit behavior after insert vehicle");
            }
        }
        return result;
    }


    //TESTED
    public boolean insertTicket(int nif, String plate, Date date,
                                String reason, double value, Date expiry_date) {

        boolean result = false;
        if (!isCustomer(nif) || !isVehicleOwner(nif, plate)) {
            System.out.println("Customer with nif: " + nif + " not owner of vehicle with plate: " + plate + " or does not exist in database");
            return false;
        }

        try {
            connection.setAutoCommit(false);
            insertIntoTicket.setInt(3, nif);
            insertIntoTicket.setString(2, plate);
            insertIntoTicket.setDate(1, date);
            insertIntoTicket.setString(5, reason);
            insertIntoTicket.setDouble(4, value);
            insertIntoTicket.setDate(6, expiry_date);
            int affected = insertIntoTicket.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Inserted ticket for plate number: " + plate + " with value: " + value + " date: " + date);
                connection.commit();
            } else {
                throw new SQLException("Couldn't insert new ticket with plate number: " + plate);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't insert data into ticket table: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }


    //TESTED
    public boolean updateVehicleColor(String plate, String color) {

        boolean result = false;
        /*if (!isVehicleOwner(nif, plate)) {
            System.out.println("Something wrong with provided info");
            return false;
        }*/

        for (Vehicle vehicle: queryVehicles()) {
            if (vehicle.getPlate().equals(plate)) {
                if(vehicle.isDeactivated()) {
                    System.out.println("Vehicle is deactivated");
                    return false;
                }
            }
        }

        try {
            connection.setAutoCommit(false);
            updateVehicleColor.setString(1, color);
            updateVehicleColor.setString(2, plate);
            int affected = updateVehicleColor.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Updated vehicle color for plate number: " + plate + " to: " + color);
                connection.commit();
            } else {
                throw new SQLException("Couldn't update vehicle color");
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update vehicle color: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }


    //TESTED
    public void changeVehicleOwner(String plate, int newNif) {

        if (!isCustomer(newNif)) {
            System.out.println("Something wrong with provided info");
            return;
        }

        try {
            connection.setAutoCommit(false);
            updateVehicleOwner.setInt(1, newNif);
            updateVehicleOwner.setString(2, plate);
            int affected = updateVehicleOwner.executeUpdate();

            if (affected == 1) {
                System.out.println("Updated vehicle owner for plate number: " + plate + " to: " + newNif);
                connection.commit();
            } else {
                throw new SQLException("Couldn't update vehicle owner");
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update vehicle owner: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    //NOT TESTED -
    public void updateExpiredTicket(Date oldDate, double newValue, Date expDate, int nif, String plate, Date todayDate) {

        try {
            connection.setAutoCommit(false);
            updateTicket.setDate(1, todayDate);
            updateTicket.setDouble(2, newValue);
            updateTicket.setDate(3, expDate);
            updateTicket.setInt(4, nif);
            updateTicket.setString(5, plate);
            updateTicket.setDate(6, oldDate);
            int affected = updateTicket.executeUpdate();

            if (affected == 1) {
                System.out.println("Updated ticket for plate number: " + plate + " to value: " + newValue + " and new date: " + todayDate);
                connection.commit();
            } else {
                throw new SQLException("Couldn't update ticket");
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update ticket: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void updateCustomerDriverLicenseDates(Date startDate, Date expDate, int driverLincese) {
        try {
            connection.setAutoCommit(false);
            updateCustomerDriverLicenseDates.setDate(1, startDate);
            updateCustomerDriverLicenseDates.setDate(2, expDate);
            updateCustomerDriverLicenseDates.setInt(3, driverLincese);
            int affected = updateCustomerDriverLicenseDates.executeUpdate();

            if (affected == 1) {
                System.out.println("Updated dates for customer with license number: " + driverLincese);
                connection.commit();
            } else {
                throw new SQLException("Couldn't update dates for customer with license number: " + driverLincese);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update customer driver license dates: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean updateEmployeeAccessLevel(int nif, int newAccessLevel) {

        if(!isEmployee(nif)) {
            return false;
        }

        boolean result = false;
        try {
            connection.setAutoCommit(false);
            updateEmployeeAccessLevel.setInt(1, newAccessLevel);
            updateEmployeeAccessLevel.setInt(2, nif);
            int affected = updateEmployeeAccessLevel.executeUpdate();

            if (affected == 1) {
                result = true;
//                System.out.println("Updated access level for employee with nif: " + nif);
                connection.commit();
            } else {
                throw new SQLException("Couldn't update access level for employee with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update employee access level: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean updatePersonPassword(int nif, String newPassword) {
        if(!isCustomerOrEmployee(nif)){
            System.out.println("Person with nif: " + nif + " is not a customer nor employee.");
            return false;
        }
        try {
            connection.setAutoCommit(false);
            updatePersonPassword.setString(1, newPassword);
            updatePersonPassword.setInt(2, nif);
            int affected = updatePersonPassword.executeUpdate();

            if (affected == 1) {
                System.out.println("Updated password for person with nif: " + nif);
                connection.commit();
                return true;
            } else {
                throw new SQLException("Couldn't update password for person with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update person password: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }


    public boolean updatePersonAddress(int nif, String newAddress) {
        if(!isCustomerOrEmployee(nif)){
            System.out.println("Person with nif: " + nif + " is not a customer nor employee.");
            return false;
        }
        try {
            connection.setAutoCommit(false);
            updatePersonAddress.setString(1, newAddress);
            updatePersonAddress.setInt(2, nif);
            int affected = updatePersonAddress.executeUpdate();

            if (affected == 1) {
                System.out.println("Updated address for person with nif: " + nif);
                connection.commit();
                return true;
            } else {
                throw new SQLException("Couldn't update address for person with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update person address: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updatePersonEmail(int nif, String newEmail) {

        if (!isCustomerOrEmployee(nif)) {
            System.out.println("Person with nif: " + nif + " is not a customer nor employee.");
            return false;
        }

        try {
            connection.setAutoCommit(false);
            updatePersonEmail.setString(1, newEmail);
            updatePersonEmail.setInt(2, nif);
            int affected = updatePersonEmail.executeUpdate();

            if (affected == 1) {
                System.out.println("Updated email for person with nif: " + nif);
                connection.commit();
                return true;
            } else {
                throw new SQLException("Couldn't update email for person with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't update person email: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }


    //TESTED
    public boolean insuranceExists(int policy, int nif) {
        List<Insurance> insuranceList = queryInsurances();
        insuranceList.removeIf(Insurance::isDeactivated);
        for (Insurance i : queryInsurances()) {
            if (isVehicleOwner(nif, i.getCarPlate())) {
                if (i.getPolicy() == policy) {
                    return true;
                }
            }
        }
        return false;
    }

    //TESTED
    public boolean isVehicleOwner(int nif, String plate) {
        List<Vehicle> vehicleList = queryVehicles();
        vehicleList.removeIf(Vehicle::isDeactivated);
        for (Vehicle v : queryVehicles()) {
            if (v.getPlate().equals(plate)) {
                if (v.getNif() == nif) {
                    return true;
                }
                System.out.println("Vehicle with plate: " + plate + " is not owned by NIF: " + nif);
                return false;
            }
        }
        System.out.println("NIF and plate aren't registered in the database");
        return false;
    }

    //TESTED
    public boolean isCustomer(int nif) {
        List<Customer> customers = queryCustomers();
        customers.removeIf(Customer::isDeactivated);
        for (Customer c : customers) {
            if (c.getNif() == nif) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmployee(int nif) {
        List<Employee> employees = queryEmployees();
        employees.removeIf(Employee::isDeactivated);
        for (Employee e : employees) {
            if (e.getNif() == nif) {
                return true;
            }
        }
        return false;
    }


    //TESTED
    public void renewInsurance(Date startDate, Date expiryDate, int Category, String companyName, int policy) {

       /* if (!insuranceExists(policy, nif)) {
            System.out.println("Wrong info, policy: " + policy + " NIF: " + nif);
            return;
        }*/

        try {
            connection.setAutoCommit(false);
            renewInsurance.setDate(1, startDate);
            renewInsurance.setDate(2, expiryDate);
            renewInsurance.setInt(3, Category);
            renewInsurance.setString(4, companyName);
            renewInsurance.setInt(5, policy);
            int affected = renewInsurance.executeUpdate();

            if (affected == 1) {
                System.out.println("Renewed insurance with policy number: " + policy);
                connection.commit();
            } else {
                throw new SQLException("Couldn't renew insurance with policy number: " + policy);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't renew insurance: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    //TESTED
    public boolean payTicket(int ticketID, double value) {
        boolean result = false;

        int paid = 1;

        List<Ticket> tickets = queryTickets();
        tickets.removeIf(Ticket::isDeactivated);
        for (Ticket t : tickets) {
            if (t.getTicketID() == ticketID) {
                if (t.isPaid()) {
                    System.out.println("Ticket is already paid");
                    return false;
                } else {
                    if (t.payTicket(value)) {
                        paid = 0;
                        System.out.println("Ticket paid successfully");
                        break;
                    }
                    System.out.println("Couldn't pay ticket, insufficient funds or wrong info");
                }
                return false;
            }
        }

        try {
            connection.setAutoCommit(false);
            payTicket.setInt(1, paid);
            payTicket.setInt(2, ticketID);
            int affected = payTicket.executeUpdate();

            if (affected == 1) {
                result = true;
                connection.commit();
            } else {
                throw new SQLException("Couldn't pay ticket");
            }

        } catch (SQLException e) {
            System.out.println("Couldn't pay ticket: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }


    //TESTED
    public boolean deleteVehicle(String plate) {

        /*if (!isVehicleOwner(nif, plate)) {
            System.out.println("Wrong information, plate: " + plate + " nif: " + nif);
            return;
        }*/

        boolean result = false;

        try {
            connection.setAutoCommit(false);
            deleteVehicle.setString(1, plate);
            int affected = deleteVehicle.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Vehicle succefuly deleted");
                connection.commit();
            } else {
                throw new SQLException("Couldn't delete vehicle with plate number: " + plate);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't delete vehicle: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }


    //NOT TESTED
    public boolean deleteInsurance(int policy) {

       /* if (!insuranceExists(policy, nif)) {
            System.out.println("No such insurance with policy number: " + policy +
                    "or wrong info");
            return;
        }*/

        boolean result = false;

        try {
            connection.setAutoCommit(false);
            deleteInsurance.setInt(1, policy);
            int affected = deleteInsurance.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Insurance with policy number:  " + policy + " succefuly deleted.");
                connection.commit();
            } else {
                throw new SQLException("Couldn't delete insurance with policy number: " + policy);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't delete insurance: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return result;
    }

    public boolean deleteTicket(int ticketID) {

       /* if (!isVehicleOwner(nif, plate)) {
            System.out.println("Wrong information, plate: " + plate + " nif: " + nif);
            return;
        }*/

        boolean result = false;

        try {
            connection.setAutoCommit(false);
            deleteTicket.setInt(1, ticketID);
            int affected = deleteTicket.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Ticket with ID: " + ticketID + " succefully deleted");
                connection.commit();
            } else {
                throw new SQLException("Couldn't delete ticket");
            }

        } catch (SQLException e) {
            System.out.println("Couldn't delete ticket: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Ticket succefully deleted now performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }


    public boolean deletePerson(int nif) {

        if (!isCustomerOrEmployee(nif)) {
            System.out.println("No such person with nif: " + nif);
            return false;
        }

        boolean result = false;

        try {
            connection.setAutoCommit(false);
            deletePerson.setInt(1, nif);
            int affected = deletePerson.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Person with nif:  " + nif + " succefuly deleted.");
                connection.commit();
            } else {
                throw new SQLException("Couldn't delete person with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't delete person: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean isCustomerOrEmployee(int nif) {

        List<Customer> customers = queryCustomers();
        customers.removeIf(Customer::isDeactivated);
        for (Customer customer : customers) {
            if (customer.getNif() == nif) {
                return true;
            }
        }

        List<Employee> employees = queryEmployees();
        employees.removeIf(Employee::isDeactivated);
        for (Employee employee : employees) {
            if (employee.getNif() == nif) {
                return true;
            }
        }

        return false;
    }


    private boolean insertPerson(int nif, String name, String address, Date bDate, String password, String email) {

        boolean result = false;
//        password = BCrypt.hashpw(password, BCrypt.gensalt());

        try {
            connection.setAutoCommit(false);
            insertIntoPerson.setInt(1, nif);
            insertIntoPerson.setString(2, name);
            insertIntoPerson.setDate(3, bDate);
            insertIntoPerson.setString(4, password);
            insertIntoPerson.setString(5, email);
            insertIntoPerson.setString(6, address);
            int affected = insertIntoPerson.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Inserted person with nif: " + nif + " and name: " + name);
                connection.commit();
            } else {
                throw new SQLException("Couldn't person with nif: " + nif + " and name: " + name);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't insert data into ticket table: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean insertCustomer(int nif, String name, String address,
                                  Date bDate, String password, String email,
                                  int driverLicense, String licenseType,
                                  Date registrationDate, Date expirationDate) {

        boolean result = false;

        if (!insertPerson(nif, name, address, bDate, password, email)) {
            System.out.println("Couldn't insert person with nif: " + nif);
            return result;
        }

        try {
            connection.setAutoCommit(false);
            insertIntoCustomer.setInt(1, nif);
            insertIntoCustomer.setInt(2, driverLicense);
            insertIntoCustomer.setString(3, licenseType);
            insertIntoCustomer.setDate(4, registrationDate);
            insertIntoCustomer.setDate(5, expirationDate);
            int affected = insertIntoCustomer.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Inserted customer with nif: " + nif + " and driver license number: " + driverLicense);
                connection.commit();
                UserWriterAux userWriterAux = new UserWriterAux(nif, password, "customer with driver license number: " + driverLicense);
            } else {
                deletePerson(nif);
                throw new SQLException("Couldn't insert customer with nif: " + nif + " and driver license number: " + driverLicense);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't insert data into ticket table: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }

        if(!result) {
            return deletePerson(nif);
        }
        return result;
    }

    public boolean insertEmployee(int nif, String name, String address,
                                  Date bDate, String password,
                                  String email,
                                  int accessLevel) {

        boolean result = false;

        if (!insertPerson(nif, name, address, bDate, password, email)) {
            System.out.println("Couldn't insert person with nif: " + nif);
            return result;
        }

        try {
            connection.setAutoCommit(false);
            insertIntoEmployee.setInt(2, accessLevel);
            insertIntoEmployee.setInt(1, nif);
            int affected = insertIntoEmployee.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Inserted employee with nif: " + nif + ", name: " + name + " and access level: " + accessLevel);
                connection.commit();
                UserWriterAux userWriterAux = new UserWriterAux(nif, password, "employee with access level: " + accessLevel);
                userWriterAux.writeToFile();

            } else {
                deletePerson(nif);
                throw new SQLException("Couldn't insert employee with nif: " + nif + ", name: " + name + "and access level: " + accessLevel);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't insert data into insurance table: " + e.getMessage());
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }

        if(!result) {
            return deletePerson(nif);
        }
        return result;
    }

    public boolean deactivatePerson(int nif) {

        boolean result = false;
        if (!isCustomerOrEmployee(nif)) {
            System.out.println("Person with nif: " + nif + " is not a customer or employee");
            return false;
        }

        try {
            connection.setAutoCommit(false);
            deactivatePerson.setInt(1, nif);
            int affected = deactivatePerson.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Deactivated person with nif: " + nif);
                connection.commit();
            } else {
                throw new SQLException("Couldn't deactivate person with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't deactivate person with nif: " + nif);
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deactivateCustomer(int nif) {

        if (!deactivatePerson(nif)) {
            System.out.println("Couldn't deactivate person with nif: " + nif);
            return false;
        }

        if (!isCustomer(nif)) {
            System.out.println("Person with nif: " + nif + " is not a customer");
            return false;
        }

        for (Customer c : queryCustomers()) {
            if (c.getNif() == nif) {
                if (c.isDeactivated()) {
                    System.out.println("Customer with nif: " + nif + " is already deactivated");
                    return false;
                }
            }
        }

        boolean result = false;

        try {
            connection.setAutoCommit(false);
            deactivateCustomer.setInt(1, nif);
            int affected = deactivateCustomer.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Deactivated customer with nif: " + nif);
                connection.commit();
            } else {
                throw new SQLException("Couldn't deactivate customer with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't deactivate customer with nif: " + nif);
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deactivateEmployee(int nif) {

        if (!deactivatePerson(nif)) {
            System.out.println("Couldn't deactivate person with nif: " + nif);
            return false;
        }

        if (!isEmployee(nif)) {
            System.out.println("Person with nif: " + nif + " is not an employee");
            return false;
        }

        for(Employee e: queryEmployees()) {
            if(e.getNif() == nif) {
                if(e.isDeactivated()) {
                    System.out.println("Employee with nif: " + nif + " is already deactivated");
                    return false;
                }
            }
        }

        boolean result = false;

        try {
            connection.setAutoCommit(false);
            deactivateEmployee.setInt(1, nif);
            int affected = deactivateEmployee.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Deactivated employee with nif: " + nif);
                connection.commit();
            } else {
                throw new SQLException("Couldn't deactivate employee with nif: " + nif);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't deactivate employee with nif: " + nif);
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deactivateTicket(int ticketID) {

        boolean result = false;

        for(Ticket t: queryTickets()) {
            if(t.getTicketID() == ticketID) {
                if(t.isDeactivated()) {
                    System.out.println("Ticket with ID: " + ticketID + " is already deactivated");
                    return false;
                }
            }
        }

        try {
            connection.setAutoCommit(false);
            deactivateTicket.setInt(1, ticketID);
            int affected = deactivateTicket.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Deactivated ticket with nif: " + ticketID);
                connection.commit();
            } else {
                throw new SQLException("Couldn't deactivate ticket with nif: " + ticketID);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't deactivate ticket with nif: " + ticketID);
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deactivateInsurance(int policy) {

        boolean result = false;

        for(Insurance i: queryInsurances()) {
            if(i.getPolicy() == policy) {
                if(i.isDeactivated()) {
                    System.out.println("Insurance with policy: " + policy + " is already deactivated");
                    return false;
                }
            }
        }

        try {
            connection.setAutoCommit(false);
            deactivateInsurance.setInt(1, policy);
            int affected = deactivateInsurance.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Deactivated insurance with policy: " + policy);
                connection.commit();
            } else {
                throw new SQLException("Couldn't deactivate insurance with policy: " + policy);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't deactivate insurance with policy: " + policy);
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deactivateVehicle(String plate) {

        boolean result = false;

        for(Vehicle v: queryVehicles()) {
            if(v.getPlate().equals(plate)) {
                if(v.isDeactivated()) {
                    System.out.println("Vehicle with plate: " + plate + " is already deactivated");
                    return false;
                }
            }
        }

        try {
            connection.setAutoCommit(false);
            deactivateVehicle.setString(1, plate);
            int affected = deactivateVehicle.executeUpdate();

            if (affected == 1) {
                result = true;
                System.out.println("Deactivated vehicle with plate: " + plate);
                connection.commit();
            } else {
                throw new SQLException("Couldn't deactivate vehicle with plate: " + plate);
            }

        } catch (SQLException e) {
            System.out.println("Couldn't deactivate vehicle with plate: " + plate);
            e.printStackTrace();
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Couldn't perform rollback: " + e2.getMessage());
                e2.printStackTrace();
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if (dataSource.open()) {
            List<Customer> customers = dataSource.queryCustomers();
            customers.forEach(System.out::println);
            customers.removeIf(Customer::isDeactivated);
            System.out.println("--------------------------------");
            customers.forEach(System.out::println);

        }
    }

}

