package employeeacess;


import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public interface ValidateInput {

    default boolean isDate(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate localDate = LocalDate.parse(s, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    default boolean isValidExpirationDate(String s) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date sqlDateToday = Date.valueOf(currentDate);

        try {
            LocalDate localDate = LocalDate.parse(s, dateFormatter);
            Date sqlDate = Date.valueOf(localDate);

            if (sqlDateToday.after(sqlDate)) {
                return false;
            } else {
                return true;
            }

        } catch (DateTimeParseException e) {
            return false;
        }
    }


    default boolean isValidBirthDate(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(s, formatter);
            LocalDate today = LocalDate.now();
            if (Period.between(localDate, today).getYears() >= 18) {
                return true;
            } else {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    default boolean isNIF(String s) {
        return s.matches("\\d{9}");
    }


    default boolean isDriverLicense(String s) {
        return s.matches("\\d{8}");
    }


    default boolean isPolicy(String s) {
        return s.matches("\\d{9}");
    }

    default boolean isPlate(String s) {
        return s.matches("^([0-9A-Z]{2}[\\-]{1}[0-9A-Z]{2}[\\-]{1}[0-9A-Z]{2})$");
    }

    default boolean isValidString(String s) {

        if(!s.isEmpty() && !s.isBlank()) {
            return true;
        }
       return false;
    }


    default boolean isVIN(String s) {
        return s.matches("^[A-Z0-9]{17}$");
    }

    default boolean isDouble(String s) {
        return s.matches("^\\d+\\.\\d{2}$|^\\d+\\.00$");
    }

//    default int printValues(Scanner scan, Logger logger, Object... vs) {
//        boolean validInput = false;
//        int decision = -1;
//        int i = 1;
//        System.out.println("The values entered are: ");
//        for (Object o : vs) {
//            System.out.println("Value " + i + ": " + o.toString());
//            i++;
//        }
//        System.out.println("If values are correct, press Y(y) to continue, N(n) to cancel.");
//        do {
//            String s = scan.nextLine().trim();
//            if (s.compareToIgnoreCase("Y") == 0) {
//                System.out.println("Values confirmed.");
//                decision = 1;
//                validInput = true;
//            } else if (s.compareToIgnoreCase("N") == 0) {
//                System.out.println("Procedure cancelled.");
//                validInput = true;
//            } else {
//                System.out.println("Invalid input. Please try again.");
////                logger.warn("Invalid input received.");
//            }
//        } while (!validInput);
//
//        return decision;
//    }


    default boolean isPassword(String s) {
        if(s.length() > 10 || s.length() < 8) {
            return false;
        }
        return true;
    }

    default boolean isInteger(String s) {
        return s.matches("[0-9]+");
    }

    default void displayList(List<?> objects, int rowsPerPage) {
//        logger.info("Displaying list...");
        Scanner scanner = new Scanner(System.in);
        int currentPosition = 0; // Always start from the first position
        int totalObjects = objects.size();

        int pages = (int) Math.ceil((double) totalObjects / rowsPerPage);
        int aux = 1;

        while (currentPosition < totalObjects) {
            System.out.println("Displaying from page " + aux + " of " + pages + ":");

            for (int i = currentPosition; i < Math.min(currentPosition + rowsPerPage, totalObjects); i++) {
                System.out.println(objects.get(i));
            }

            System.out.println("Displaying from page " + aux + " of " + pages + ":");
            System.out.print("\nOptions: (C)ontinue, (P)revious, (B)ack to menu: ");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "C" -> {
                    aux++;
                    currentPosition += rowsPerPage;
                }
                case "P" -> {
                    aux--;
                    aux = Math.max(1, aux);
                    currentPosition = Math.max(0, currentPosition - rowsPerPage);
                }
                case "B" -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
//                    logger.warn("Invalid input received.");
                }
            }
        }
//        logger.info("Displaying list completed.");
    }

    default boolean isEmail(String s) {
        return s.matches("^[a-zA-Z0-9]{2,}@([a-zA-Z0-9]{2,}\\.com|[a-zA-Z0-9]{2,}\\.pt)$");
    }

}
