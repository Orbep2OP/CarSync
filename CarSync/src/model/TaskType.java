package model;

public enum TaskType {
    CUSTOMER_REGISTRATION, VEHICLE_REGISTRATION,
    INSURANCE_REGISTRATION, TICKET_REGISTRATION,
//    EMPLOYEE_REGISTRATION,

    CUSTOMER_DEACTIVATION, VEHICLE_DEACTIVATION,
    INSURANCE_DEACTIVATION, TICKET_DEACTIVATION, EMPLOYEE_DEACTIVATION,

    CUSTOMER_UPDATE_EMAIL, CUSTOMER_UPDATE_PASSWORD, CUSTOMER_UPDATE_ADDRESS,
    VEHICLE_UPDATE_COLOR, TICKET_UPDATE_PAY, EMPLOYEE_UPDATE_ACCESS_LEVEL;


    public static TaskType getTaskType(String taskType) {

        return switch (taskType) {
            case "Customer Registration" -> CUSTOMER_REGISTRATION;
            case "Vehicle Registration" -> VEHICLE_REGISTRATION;
            case "Insurance Registration" -> INSURANCE_REGISTRATION;
            case "Ticket Registration" -> TICKET_REGISTRATION;
//            case "Employee Registration" -> EMPLOYEE_REGISTRATION;
            case "Customer Deactivation" -> CUSTOMER_DEACTIVATION;
            case "Vehicle Deactivation" -> VEHICLE_DEACTIVATION;
            case "Insurance Deactivation" -> INSURANCE_DEACTIVATION;
            case "Ticket Deactivation" -> TICKET_DEACTIVATION;
            case "Employee Deactivation" -> EMPLOYEE_DEACTIVATION;
            case "Update Customer Email" -> CUSTOMER_UPDATE_EMAIL;
            case "Update Customer Password" -> CUSTOMER_UPDATE_PASSWORD;
            case "Update Customer Address" -> CUSTOMER_UPDATE_ADDRESS;
            case "Update Vehicle Color" -> VEHICLE_UPDATE_COLOR;
            case "Ticket Payment" -> TICKET_UPDATE_PAY;
            case "Employee Access Level Update" -> EMPLOYEE_UPDATE_ACCESS_LEVEL;
            default -> null;
        };
    }

    public static String getDescription(TaskType taskType) {
        return switch (taskType) {
            case CUSTOMER_REGISTRATION -> "Customer Registration";
            case VEHICLE_REGISTRATION -> "Vehicle Registration";
            case INSURANCE_REGISTRATION -> "Insurance Registration";
            case TICKET_REGISTRATION -> "Ticket Registration";
//            case EMPLOYEE_REGISTRATION -> "Employee Registration";
            case CUSTOMER_DEACTIVATION -> "Customer Deactivation";
            case VEHICLE_DEACTIVATION -> "Vehicle Deactivation";
            case INSURANCE_DEACTIVATION -> "Insurance Deactivation";
            case TICKET_DEACTIVATION -> "Ticket Deactivation";
            case EMPLOYEE_DEACTIVATION -> "Employee Deactivation";
            case CUSTOMER_UPDATE_EMAIL -> "Update Customer Email";
            case CUSTOMER_UPDATE_PASSWORD -> "Update Customer Password";
            case CUSTOMER_UPDATE_ADDRESS -> "Update Customer Address";
            case VEHICLE_UPDATE_COLOR -> "Update Vehicle Color";
            case TICKET_UPDATE_PAY -> "Ticket Payment";
            case EMPLOYEE_UPDATE_ACCESS_LEVEL -> "Employee Access Level Update";
        };
    }
}

