package model;

import employeeacess.DataSource;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Date;
import java.util.Objects;

public class Task implements Comparable<Task> {

    private int taskID;
    private TaskType taskType;
    private String taskStatus;
    private String taskDate;
    private int nif;
    private String values;

    public Task(int taskID, TaskType taskType, String taskStatus, String taskDate, int nif, String values) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.taskDate = taskDate;
        this.nif = nif;
        this.values = values;
    }

    public Task(int taskID, TaskType taskType, String taskDate, int nif, String values) {
        new Task(taskID, taskType, "Open", taskDate, nif, values);
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getTaskStatus() {
        return Objects.equals(taskStatus, "Open") ? "Open" : "Closed";
    }

    public boolean perFormTask(DataSource dataSource) {
        String[] allValues = values.split(",");
        TaskManagment tk = new TaskManagment();

        switch (taskType) {
            case CUSTOMER_REGISTRATION -> {
                if (dataSource.insertCustomer(nif, allValues[0], allValues[1],
                        Date.valueOf(allValues[2]), allValues[3], allValues[4],
                        Integer.parseInt(allValues[5]), allValues[6], Date.valueOf(allValues[7]),
                        Date.valueOf(allValues[8]))) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case VEHICLE_REGISTRATION -> {
                if (dataSource.insertVehicle(allValues[0], allValues[1], allValues[2],
                        allValues[3], allValues[4], Date.valueOf(allValues[5]),
                        allValues[6], nif)) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case INSURANCE_REGISTRATION -> {
                if (dataSource.insertInsurance(Integer.parseInt(allValues[0]), allValues[1],
                        Date.valueOf(allValues[2]), allValues[3], Date.valueOf(allValues[4]), allValues[5])) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case TICKET_REGISTRATION -> {
                if (dataSource.insertTicket(Integer.parseInt(allValues[0]), allValues[1],
                        Date.valueOf(allValues[2]), allValues[3], Double.parseDouble(allValues[4]), Date.valueOf(allValues[5]))) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case CUSTOMER_DEACTIVATION -> {
                if (dataSource.isCustomer(nif)) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case VEHICLE_DEACTIVATION -> {
                if (dataSource.isVehicleOwner(nif, allValues[0])) {
                    if (dataSource.deactivateVehicle(allValues[0])) {
                        tk.updateTaskStatus("Closed", taskID);
                        return true;
                    }
                }
            }

            case INSURANCE_DEACTIVATION -> {
                if (dataSource.insuranceExists(Integer.parseInt(allValues[0]), nif)) {
                    if (dataSource.deactivateInsurance(Integer.parseInt(allValues[0]))) {
                        tk.updateTaskStatus("Closed", taskID);
                        return true;
                    }
                }
            }

            case TICKET_DEACTIVATION -> {
                for (Ticket ticket : dataSource.queryTickets()) {
                    if (ticket.getNif() == nif) {
                        if (dataSource.deactivateTicket(Integer.parseInt(allValues[0]))) {
                            tk.updateTaskStatus("Closed", taskID);
                            return true;
                        }
                    }
                }
            }

            case EMPLOYEE_DEACTIVATION -> {
                if (dataSource.isEmployee(nif)) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case CUSTOMER_UPDATE_ADDRESS -> {
                if (dataSource.updatePersonAddress(nif, allValues[1])) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case CUSTOMER_UPDATE_EMAIL -> {
                if(dataSource.updatePersonEmail(nif, allValues[1])) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case CUSTOMER_UPDATE_PASSWORD -> {
                allValues[1] = BCrypt.hashpw(allValues[1], BCrypt.gensalt());
                if(dataSource.updatePersonPassword(nif, allValues[1])) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }

            case VEHICLE_UPDATE_COLOR -> {
                if (dataSource.isVehicleOwner(nif, allValues[0])) {
                    if(dataSource.updateVehicleColor(allValues[0], allValues[1])) {
                        tk.updateTaskStatus("Closed", taskID);
                        return true;
                    }
                }
            }

            case TICKET_UPDATE_PAY -> {
                for (Ticket ticket : dataSource.queryTickets()) {
                    if (ticket.getNif() == nif) {
                        if(dataSource.payTicket(Integer.parseInt(allValues[0]), Double.parseDouble(allValues[1]))) {
                            tk.updateTaskStatus("Closed", taskID);
                            return true;
                        }
                    }
                }
            }

            case EMPLOYEE_UPDATE_ACCESS_LEVEL -> {
                if(dataSource.updateEmployeeAccessLevel(nif, Integer.parseInt(allValues[0]))) {
                    tk.updateTaskStatus("Closed", taskID);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = """
                Task ID: %d
                    Type: %s
                    Status: %s
                    Date: %s
                    NIF: %d
                    Values: %s
                                
                """;
        return String.format(s, taskID, TaskType.getDescription(taskType), taskStatus, taskDate, nif, values);
    }

    public String showTask() {
        String s = """
                Task ID: %d
                    Type: %s
                    Status: %s
                    Date: %s            
                """;
        return String.format(s, taskID, TaskType.getDescription(taskType), taskStatus, taskDate);
    }

    @Override
    public int compareTo(Task o) {
        return taskID - o.taskID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus.equals("Closed") ? "Closed" : "Open";
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Date getTaskDate() {
        return Date.valueOf(taskDate);
    }

    public String getTaskInfo() {
        return values;
    }
}
