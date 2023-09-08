package employeeacess;

public enum TicketEnum {

    TABLE_TICKET,
    COLUMN_TICKET_NIF,
    COLUMN_TICKET_PLATE,
    COLUMN_TICKET_DATE,
    COLUMN_TICKET_EXPIRY_DATE,
    COLUMN_TICKET_VALUE,
    COLUMN_TICKET_REASON,
    COLUMN_TICKET_PAID,
    COLUMN_TICKET_DEACTIVATED,
    COLUMN_TICKET_ID,
    INSERT_TICKET,
    DELETE_TICKET,
    UPDATE_TICKET,
    PAY_TICKET,
    DEACTIVATE_TICKET,
    QUERY_TABLE_TICKET;

    public static String getString(TicketEnum tcs) {

        String s = switch (tcs) {
            case TABLE_TICKET -> "ticket";
            case COLUMN_TICKET_DATE -> "date";
            case COLUMN_TICKET_NIF -> "nif";
            case COLUMN_TICKET_EXPIRY_DATE -> "expiry_date";
            case COLUMN_TICKET_PLATE -> "plate";
            case COLUMN_TICKET_VALUE -> "value";
            case COLUMN_TICKET_REASON -> "reason";
            case COLUMN_TICKET_PAID -> "paid";
            case COLUMN_TICKET_DEACTIVATED -> "deactivated";
            case COLUMN_TICKET_ID -> "ticketID";
            case INSERT_TICKET -> "INSERT INTO " + getString(TABLE_TICKET) + '('
                    + getString(COLUMN_TICKET_DATE) + ", " +
                    getString(COLUMN_TICKET_PLATE) + ", " +
                    getString(COLUMN_TICKET_NIF) + ", " +
                    getString(COLUMN_TICKET_VALUE) + ", " +
                    getString(COLUMN_TICKET_REASON) + ", " +
                    getString(COLUMN_TICKET_EXPIRY_DATE) + ") VALUES(?, ?, ?, ?, ?, ?)";
            case DELETE_TICKET -> "DELETE FROM " + getString(TicketEnum.TABLE_TICKET) + " WHERE " +
                    getString(TicketEnum.COLUMN_TICKET_ID) + " = ? ";
            case QUERY_TABLE_TICKET -> "SELECT * FROM " + getString(TicketEnum.TABLE_TICKET);
            case UPDATE_TICKET -> "UPDATE " + getString(TicketEnum.TABLE_TICKET) + " SET " +
                    getString(TicketEnum.COLUMN_TICKET_DATE) + " = ?, " +
                    getString(TicketEnum.COLUMN_TICKET_VALUE) + " = ?, " +
                    getString(TicketEnum.COLUMN_TICKET_EXPIRY_DATE) + " = ? " +
                    " WHERE " + getString(TicketEnum.COLUMN_TICKET_NIF) + " = ? AND " +
                    getString(TicketEnum.COLUMN_TICKET_PLATE) + " = ? AND " +
                    getString(TicketEnum.COLUMN_TICKET_DATE) + " = ? ";
            case PAY_TICKET -> "UPDATE " + getString(TicketEnum.TABLE_TICKET) + " SET " +
                    getString(TicketEnum.COLUMN_TICKET_PAID) + " = ? " +
                    " WHERE " + getString(TicketEnum.COLUMN_TICKET_ID) + " = ?";
            case DEACTIVATE_TICKET -> "UPDATE " + getString(TicketEnum.TABLE_TICKET) + " SET " +
                    getString(TicketEnum.COLUMN_TICKET_DEACTIVATED) + " = 0 WHERE " +
                    getString(TicketEnum.COLUMN_TICKET_ID) + " = ?";
            default -> throw new IllegalArgumentException("No such column or operation for vehicle table");
        };
        return s;
    }
}
