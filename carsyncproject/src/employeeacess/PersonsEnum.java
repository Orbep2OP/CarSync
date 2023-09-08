package employeeacess;

public enum PersonsEnum {
    // constants for the variables of the table Person
    TABLE_PERSON,
    COLUMN_NIF,
    COLUMN_NAME,
    COLUMN_BIRTH_DATE,
    COLUMN_PWD,
    COLUMN_ADDRESS,
    COLUMN_EMAIL,
    COLUMN_PERSON_TYPE_EMPLOYEE,
    COLUMN_PERSON_TYPE_CUSTOMER,
    COLUMN_DEACTIVATED,
    DEACTIVATE_PERSON,
    UPDATE_PERSON_EMAIL,


    //contants for the varibles of the table Customer
    TABLE_CUSTOMER,
    COLUMN_CUSTOMER_DRIVER_LICENSE,
    COLUMN_CUSTOMER_LICENSE_TYPE,
    COLUMN_CUSTOMER_START_DATE,
    COLUMN_CUSTOMER_EXPIRATION_DATE,
    QUERY_TABLE_CUSTOMER,
    DEACTIVATE_CUSTOMER,

    //Constants for the variables of the table Employee
    TABLE_EMPLOYEE,
    UPDATE_EMPLOYEE_PASSWORD, UPDATE_EMPLOYEE_EMAIL, UPDATE_EMPLOYEE_ADDRESS,
    COLUMN_EMPLOYEE_ACCESS_LEVEL, INSERT_INTO_PERSON,
    INSERT_INTO_EMPLOYEE, INSERT_INTO_CUSTOMER, DELETE_FROM_PERSON,
    DELETE_FROM_EMPLOYEE, DELETE_FROM_CUSTOMER, QUERY_TABLE_CUSTOMER_BY_NIF, QUERY_TABLE_EMPLOYEE_BY_NIF,
    QUERY_TABLE_EMPLOYEE, QUERY_TABLE_PERSON, UPDATE_CUSTOMER_DRIVER_LICENSE, UPDATE_CUSTOMER_DRIVER_LICENSE_DATES,
    UPDATE_EMPLOYER_ACCESS_LEVEL, UPDATE_PERSON_ADDRESS, UPDATE_PERSON_PWD, TRIGGER_INSERT_INTO_PERSON_AND_CUSTOMER,
    QUERY_TABLE_PERSON_BY_NIF, INSERT_INTO_TABLE_CUSTOMER, INSERT_INTO_TABLE_EMPLOYEE, DEACTIVATE_EMPLOYEE;

    //Method to get the string of the enum
    public static String getString(PersonsEnum pes) {
        String s = switch (pes) {
            case TABLE_PERSON -> "person";
            case COLUMN_NIF -> "nif";
            case COLUMN_NAME -> "name";
            case COLUMN_BIRTH_DATE -> "b_date";
            case COLUMN_PWD -> "password";
            case COLUMN_ADDRESS -> "address";
            case TABLE_CUSTOMER -> "customer";
            case COLUMN_PERSON_TYPE_CUSTOMER -> "CUSTOMER";
            case COLUMN_CUSTOMER_DRIVER_LICENSE -> "driver_license_number";
            case COLUMN_CUSTOMER_LICENSE_TYPE -> "license_type";
            case COLUMN_CUSTOMER_START_DATE -> "start_date";
            case COLUMN_CUSTOMER_EXPIRATION_DATE -> "expiration_date";
            case TABLE_EMPLOYEE -> "employee";
            case COLUMN_PERSON_TYPE_EMPLOYEE -> "EMPLOYEE";
            case COLUMN_EMPLOYEE_ACCESS_LEVEL -> "access_level";
            case COLUMN_DEACTIVATED -> "deactivated";
            case COLUMN_EMAIL -> "email";

            case UPDATE_EMPLOYEE_ADDRESS -> "UPDATE " + getString(PersonsEnum.TABLE_PERSON) + " SET " +
                    getString(PersonsEnum.COLUMN_ADDRESS) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case UPDATE_EMPLOYEE_EMAIL -> "UPDATE " + getString(PersonsEnum.TABLE_PERSON) + " SET " +
                    getString(PersonsEnum.COLUMN_EMAIL) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case UPDATE_EMPLOYEE_PASSWORD -> "UPDATE " + getString(PersonsEnum.TABLE_PERSON) + " SET " +
                    getString(PersonsEnum.COLUMN_PWD) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case DEACTIVATE_PERSON -> "UPDATE " + getString(PersonsEnum.TABLE_PERSON) + " SET " +
                    getString(PersonsEnum.COLUMN_DEACTIVATED) + " = 0 WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case DEACTIVATE_CUSTOMER -> "UPDATE " + getString(PersonsEnum.TABLE_CUSTOMER) + " SET " +
                    getString(PersonsEnum.COLUMN_DEACTIVATED) + " = 0 WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case DEACTIVATE_EMPLOYEE -> "UPDATE " + getString(PersonsEnum.TABLE_EMPLOYEE) + " SET " +
                    getString(PersonsEnum.COLUMN_DEACTIVATED) + " = 0 WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case INSERT_INTO_PERSON -> "INSERT INTO " + getString(PersonsEnum.TABLE_PERSON) + '('
                    + getString(PersonsEnum.COLUMN_NIF) + ", " +
                    getString(PersonsEnum.COLUMN_NAME) + ", " +
                    getString(PersonsEnum.COLUMN_BIRTH_DATE) + ", " +
                    getString(PersonsEnum.COLUMN_PWD) + ", " +
                    getString(PersonsEnum.COLUMN_EMAIL) + ", " +
                    getString(PersonsEnum.COLUMN_ADDRESS) + ") VALUES(?, ?, ?, ?, ?, ?)";

            case UPDATE_PERSON_EMAIL -> "UPDATE " + getString(PersonsEnum.TABLE_PERSON) + " SET " +
                    getString(PersonsEnum.COLUMN_EMAIL) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case INSERT_INTO_TABLE_EMPLOYEE -> "INSERT INTO " + getString(PersonsEnum.TABLE_EMPLOYEE) + '('
                    + getString(PersonsEnum.COLUMN_NIF) + ", " +
                    getString(PersonsEnum.COLUMN_EMPLOYEE_ACCESS_LEVEL) + ") VALUES(?, ?)";

            case INSERT_INTO_TABLE_CUSTOMER -> "INSERT INTO " + getString(PersonsEnum.TABLE_CUSTOMER) + '('
                    + getString(PersonsEnum.COLUMN_NIF) + ", " +
                    getString(PersonsEnum.COLUMN_CUSTOMER_DRIVER_LICENSE) + ", " +
                    getString(PersonsEnum.COLUMN_CUSTOMER_LICENSE_TYPE) + ", " +
                    getString(PersonsEnum.COLUMN_CUSTOMER_START_DATE) + ", " +
                    getString(PersonsEnum.COLUMN_CUSTOMER_EXPIRATION_DATE) + ") VALUES(?, ?, ?, ?, ?)";


            case UPDATE_CUSTOMER_DRIVER_LICENSE_DATES -> "UPDATE " + getString(PersonsEnum.TABLE_CUSTOMER) + " SET " +
                    getString(PersonsEnum.COLUMN_CUSTOMER_START_DATE) + " = ?, " +
                    getString(PersonsEnum.COLUMN_CUSTOMER_EXPIRATION_DATE) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_CUSTOMER_DRIVER_LICENSE) + " = ?";

            case UPDATE_EMPLOYER_ACCESS_LEVEL -> "UPDATE " + getString(PersonsEnum.TABLE_EMPLOYEE) + " SET " +
                    getString(PersonsEnum.COLUMN_EMPLOYEE_ACCESS_LEVEL) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case UPDATE_PERSON_PWD -> "UPDATE " + getString(PersonsEnum.TABLE_PERSON) + " SET " +
                    getString(PersonsEnum.COLUMN_PWD) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case UPDATE_PERSON_ADDRESS -> "UPDATE " + getString(PersonsEnum.TABLE_PERSON) + " SET " +
                    getString(PersonsEnum.COLUMN_ADDRESS) + " = ? WHERE " +
                    getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case DELETE_FROM_PERSON ->
                    "DELETE FROM " + getString(PersonsEnum.TABLE_PERSON) + " WHERE " + getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case DELETE_FROM_CUSTOMER ->
                    "DELETE FROM " + getString(PersonsEnum.TABLE_CUSTOMER) + " WHERE " + getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case DELETE_FROM_EMPLOYEE ->
                    "DELETE FROM " + getString(PersonsEnum.TABLE_EMPLOYEE) + " WHERE " + getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case QUERY_TABLE_CUSTOMER -> "SELECT * FROM " + getString(TABLE_CUSTOMER);

            case QUERY_TABLE_EMPLOYEE -> "SELECT * FROM " + getString(TABLE_EMPLOYEE);

            case QUERY_TABLE_PERSON -> "SELECT * FROM " + getString(TABLE_PERSON);

            case QUERY_TABLE_CUSTOMER_BY_NIF ->
                    "SELECT * FROM " + getString(PersonsEnum.TABLE_CUSTOMER) + " WHERE " + getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case QUERY_TABLE_EMPLOYEE_BY_NIF ->
                    "SELECT * FROM " + getString(PersonsEnum.TABLE_EMPLOYEE) + " WHERE " + getString(PersonsEnum.COLUMN_NIF) + " = ?";

            case QUERY_TABLE_PERSON_BY_NIF ->
                    "SELECT * FROM " + getString(PersonsEnum.TABLE_PERSON) + " WHERE " + getString(PersonsEnum.COLUMN_NIF) + " = ?";

            default -> throw new IllegalArgumentException("No such column or operation for person table");

        };
        return s;
    }


}
