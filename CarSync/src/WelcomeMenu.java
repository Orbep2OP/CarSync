import clientmenu.WelcomeMenuForm;
import employeeacess.DataSource;
import employeeacess.FrontOffice;
import org.mindrot.jbcrypt.BCrypt;
import util.Mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class WelcomeMenu extends WelcomeMenuForm {

    public void run() throws SQLException, MessagingException {
        System.out.println("Welcome to IMT (but better). For every menu you'll have a few options to choose and you'll"
                + " have to type the number of the option you want to choose. \n" + "For any question, contact us at 217 949 000");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        DataSource dataSource = new DataSource();
        if (!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        switch (option) {
            case 1:
                Login login = new Login();
                login.run(dataSource);
                break;
            case 2:
                Register register = new Register();
                register.run(dataSource);
                break;
            case 3:
                System.out.println("Bye bye");

                break;
            default:
                System.out.println("Invalid option. Please try again");
                run();
        }
        scanner.close();
    }
}

class Register {
    private static final String SUCCESSFUL_REGISTER = "Register successful, please login";
    private static final String NIF_ALREADY_REGISTERED = "NIF already registered in our System. Please login";

    public static final String DB_NAME = "projeto_imt";
    //Este port number é o port number que aparece no XAMPP quando voçês dão start
    //para conectar à base de dados e no meu caso é 3306.
    public static final int PORT_NUMBER = 3306;

    private static final String URL = "jdbc:mysql://localhost:" + PORT_NUMBER + "/" + DB_NAME;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    DataSource dataSource;

    private Connection connection;

    public void run(DataSource dataSource) throws SQLException, MessagingException {
        this.dataSource = dataSource;

        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Scanner scanner = new Scanner(System.in);
        System.out.println("------ Register ------");

        int nif = validateNIF(scanner, dataSource);

        System.out.println("Enter name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter address:");
        String address = scanner.nextLine().trim();

        System.out.println("Enter birthdate (YYYY-MM-DD):");
        String birthdate = validateDate(scanner);

        String email = validateEmail(scanner);

        //Password
        String password = validatePWD(scanner);

        //Other info
        System.out.println("Enter driver license:");
        String driver_license = scanner.nextLine().trim();
        System.out.println("Enter License Type:");
        System.out.println("1. A\n" + "2. B\n" + "3. C\n" + "4. D\n" + "(enter the number please)");
        int license_type = scanner.nextInt();
        System.out.println("Starting date (YYYY-MM-DD):");
        String starting_date = validateDate(scanner);
        System.out.println("Expiration date (YYYY-MM-DD):");
        String expiration_date = validateDate(scanner);

        /*código SQL para registar na base de dados*/
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        dataSource.insertCustomer(nif, name, address, Date.valueOf(birthdate), hashedPassword, email, Integer.parseInt(driver_license),
                String.valueOf(license_type), Date.valueOf(starting_date), Date.valueOf(expiration_date));


        /*Passamos para o login*/
        System.out.println(SUCCESSFUL_REGISTER);
        Login login = new Login();
        login.run(dataSource);
    }

    private String validateEmail(Scanner scanner) {
        System.out.println("Enter email:");
        String email = scanner.nextLine().trim();

        while (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Invalid email. Please try again");
            System.out.println("Enter email:");
            email = scanner.nextLine().trim();
        }
        return email;
    }

    //check if the passowrd is                                                                                                                                                                                                                                                                                                                                                                at least 8 characters long
    private String validatePWD(Scanner scanner) {
        System.out.println("Enter password (must be at least 8 characters long):");
        String password = scanner.nextLine().trim();

        while ((password.length() < 8)) {
            System.out.println("Password must be at least 8 characters long");
            System.out.println("Enter password (must be at least 8 characters long):");
            password = scanner.nextLine().trim();
        }
        while (true) {
            System.out.println("Confirm password:");
            String confirmPassword = scanner.nextLine().trim();
            if (password.equals(confirmPassword)) {
                return password;
            } else {
                System.out.println("Passwords don't match. Please try again");
                validatePWD(scanner);
            }
        }
    }

    //this method will verify if there's already a nif registered in the Customer SQL table
    private int validateNIF(Scanner scanner, DataSource dataSource) {

        while (true) {
            System.out.println("Enter nif:");
            String input = scanner.nextLine().trim();
            if (!input.matches("\\d{9}")) {
                System.out.println(input);
                System.out.println(input.length());
                System.out.println("Invalid NIF. Please try again");
                validateNIF(scanner, dataSource);
            }
            try {
                if (dataSource.isCustomerOrEmployee(Integer.parseInt(input))) {
                    System.out.println("NIF already registered in our System. Please login");
                    Login login = new Login();
                    login.run(dataSource);
                } else {
                    return Integer.parseInt(input);
                }
            } catch (SQLException | MessagingException e) {
                System.out.println("Invalid NIF. Please try again" + e.getMessage());
            }
        }
    }

    private static String validateDate(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            try {
                LocalDate.parse(input); // This will throw an exception if the format is invalid
                return input; // If no exception is thrown, the input is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
            }
        }
    }
}

class RegisterEmployee {
    private static final String SUCCESSFUL_REGISTER = "Register successful, please login";
    private static final String NIF_ALREADY_REGISTERED = "NIF already registered in our System. Please login";

    public static final String DB_NAME = "projeto_imt";
    //Este port number é o port number que aparece no XAMPP quando voçês dão start
    //para conectar à base de dados e no meu caso é 3306.
    public static final int PORT_NUMBER = 3306;

    private static final String URL = "jdbc:mysql://localhost:" + PORT_NUMBER + "/" + DB_NAME;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    DataSource dataSource;

    private Connection connection;

    public void run(DataSource dataSource) throws SQLException, MessagingException {
        this.dataSource = dataSource;

        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Scanner scanner = new Scanner(System.in);
        System.out.println("------ Register Employee ------");

        int nif = validateNIF(scanner, dataSource);

        System.out.println("Enter name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter address:");
        String address = scanner.nextLine().trim();

        System.out.println("Enter birthdate (YYYY-MM-DD):");
        String birthdate = validateDate(scanner);

        //Password
        String password = validatePWD(scanner);
        String email = validateEmail(scanner);

        System.out.println("Access Level: ");
        System.out.println("0. Employee\n" +
                "1. Employee Manager\n" +
                "Inser the number of the access level");
        int access_level = scanner.nextInt();
        System.out.println("");

        /*código SQL para registar na base de dados*/
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        dataSource.insertEmployee(nif, name, address, Date.valueOf(birthdate), hashedPassword, email, access_level);


        /*Passamos para o login*/
        System.out.println(SUCCESSFUL_REGISTER);
        Login login = new Login();
        login.run(dataSource);
    }

    private String validateEmail(Scanner scanner) {
        System.out.println("Enter email:");
        String email = scanner.nextLine().trim();

        while (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Invalid email. Please try again");
            System.out.println("Enter email:");
            email = scanner.nextLine().trim();
        }
        return email;
    }

    //check if the passowrd is at least 8 characters long
    private String validatePWD(Scanner scanner) {
        System.out.println("Enter password (must be at least 8 characters long):");
        String password = scanner.nextLine().trim();

        while ((password.length() < 8)) {
            System.out.println("Password must be at least 8 characters long");
            System.out.println("Enter password (must be at least 8 characters long):");
            password = scanner.nextLine().trim();
        }
        while (true) {
            System.out.println("Confirm password:");
            String confirmPassword = scanner.nextLine().trim();
            if (password.equals(confirmPassword)) {
                return password;
            } else {
                System.out.println("Passwords don't match. Please try again");
                validatePWD(scanner);
            }
        }
    }

    //this method will verify if there's already a nif registered in the Customer SQL table
    private int validateNIF(Scanner scanner, DataSource dataSource) {

        while (true) {
            System.out.println("Enter nif:");
            String input = scanner.nextLine().trim();
            if (!input.matches("\\d{9}")) {
                System.out.println(input);
                System.out.println(input.length());
                System.out.println("Invalid NIF. Please try again");
                validateNIF(scanner, dataSource);
            }
            try {
                if (dataSource.isCustomerOrEmployee(Integer.parseInt(input))) {
                    System.out.println("NIF already registered in our System. Please login");
                    Login login = new Login();
                    login.run(dataSource);
                } else {
                    return Integer.parseInt(input);
                }
            } catch (SQLException | MessagingException e) {
                System.out.println("Invalid NIF. Please try again" + e.getMessage());
            }
        }
    }

    private static String validateDate(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            try {
                LocalDate.parse(input); // This will throw an exception if the format is invalid
                return input; // If no exception is thrown, the input is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
            }
        }
    }
}


class Login {


    public static final String SUCCESSFUL_LOGIN = "Login successful";
    public static final String WRONG_PASSWORD = "Wrong password. Wanna go back? (y/n)";
    public static final String NIF_NOT_REGISTERED = "NIF is not registered in our System. Please register first";

    private static final String LOGIN = "------ Login ------";
    private static final String ENTER_NIF = "Please enter your NIF:";
    private static final String ENTER_PASSWORD = "Please enter your password:";

    private static final String FORGOT_PASSWORD = "Forgot password? (y/n)";

    public static final String DB_NAME = "projeto_imt";
    //Este port number é o port number que aparece no XAMPP quando voçês dão start
    //para conectar à base de dados e no meu caso é 3306.
    public static final int PORT_NUMBER = 3306;

    private static final String URL = "jdbc:mysql://localhost:" + PORT_NUMBER + "/" + DB_NAME;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private Connection connection;

    Scanner scanner;

    public void run(DataSource dataSource) throws SQLException, MessagingException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Session newSession = null;
        boolean isCorrect = false;
        boolean goBack = false;
        String nif = "";
        int nif_num = 0;
        scanner = new Scanner(System.in);
        System.out.println(LOGIN);
        while (!isCorrect || !goBack) {
            System.out.println(ENTER_NIF);
            nif = scanner.nextLine().trim();
            System.out.println(ENTER_PASSWORD);
            String password = scanner.nextLine().trim();
            String result = authenticateUser(nif, password);
            switch (result) {
                case SUCCESSFUL_LOGIN:
                    System.out.println(SUCCESSFUL_LOGIN);
                    nif_num = Integer.parseInt(nif);
                    isCorrect = true;
                    break;
                case WRONG_PASSWORD:
                    System.out.println(WRONG_PASSWORD);
                    String answer = scanner.nextLine().trim();
                    if (answer.equals("y")) {
                        Mail mail = new Mail();
                        mail.setupServerProperties(newSession);
                        String tempPassword = mail.generateRandomPassword(8);
                        String name = dataSource.queryCustomersHashMap().get(nif_num).getName();
                        MimeMessage mimeMessage = mail.draftEmail(name, newSession);
                        mail.sendEmail(newSession, mimeMessage);

                        System.out.println("Check your email for the new password and insert it here:");
                        String tempInput = scanner.nextLine().trim();

                        if (tempInput.equals(tempPassword)) {
                            String newPassword = validateNewPWD(scanner);
                            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                            dataSource.updatePersonPassword(nif_num, hashedPassword);
                        } else System.out.println("Wrong password");
                    }
                    break;
                case NIF_NOT_REGISTERED:
                    System.out.println(NIF_NOT_REGISTERED);
                    break;
            }
            if (isCorrect) break;
        }

        if (isCorrect) {
            if (dataSource.isCustomer(nif_num)) {
                //call Frontoffice
                FrontOffice.startFrontOffice(nif_num);

            }
//                BackOffice.startBackOffice(nif_num);

        } else {
            System.out.println("Going back to main menu");
        }
        dataSource.close();
    }


    //check if the passowrd is at least 8 characters long
    private String validateNewPWD(Scanner scanner) {
        String password;
        do {
            System.out.println("Enter a new password (must be at least 8 characters long):");
            password = scanner.nextLine().trim();

        } while ((password.length() < 8));

        String confirmPassword;
        do {
            System.out.println("Confirm password:");
            confirmPassword = scanner.nextLine().trim();
        } while (!password.equals(confirmPassword));

        return password;

    }

    public String authenticateUser(String nif, String password) {
        Statement statement = null;
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE nif = '" + nif + "'");

            if (resultSet.next()) {
                if (BCrypt.checkpw(password, resultSet.getString("password"))) return SUCCESSFUL_LOGIN;
                else return WRONG_PASSWORD;
            } else return NIF_NOT_REGISTERED;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
