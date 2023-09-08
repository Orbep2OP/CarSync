package model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ticket implements Comparable<Ticket> {

    private int nif;
    private String plate;
    private Date date;
    private Date expiry_date;
    private double value;
    private String reason;
    private boolean isPaid = false;
    private boolean deactivated = false;
    private int ticketID;


    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
    public int getTicketID() {
        return ticketID;
    }

    public void deactivate() {
        this.deactivated = true;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public boolean isPaid() {
        boolean b = isPaid;
        return b;
    }
    public boolean isDeactivated() {
        return deactivated;
    }

    public boolean setDeactivated(int deactivated) {
        if (deactivated == 0) {
            this.deactivated = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean setPaid(int paid) {
        if (paid == 0) {
            isPaid = true;
            return true;
        }
        return false;
    }

    public boolean payTicket(double value) {
        if (isPaid == true) {
            System.out.println("Ticket already paid");
            return false;
        }
        if (value >= this.value) {
            setPaid(1);
            return true;
        }
        System.out.println("Value not enough to pay ticket");
        return false;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value <= 0 ? 0 : value;
    }

    public String getReason() {
        return reason;
    }

//    public void setReason(int reason) {
//        String s = switch (reason) {
//            case 1 -> this.reason = "Speeding";
//            case 2 -> this.reason = "Red light";
//            case 3 -> this.reason = "Illegal parking";
//            case 4 -> this.reason = "Reckless driving";
//            case 5 -> this.reason = "DUI";
//            default -> throw new IllegalStateException("No such reason");
//        };
//    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        if (nif < 200000000 || nif > 299999999) {
            throw new IllegalArgumentException("NIF must have 9 digits");
        }
        this.nif = nif;
    }

    public String toString() {
        String s = isPaid ? "Paid" : "Not paid";
        return "Ticket info: " + "\n" +
                "- Driver's NIF: " + nif + "\n" +
                "- Car plate: " + plate + "\n" +
                "- TIcket date: " + date + "\n"
                + "- Ticket expiry date: " + expiry_date + "\n"
                + "- Value: " + value + "\n"
                + "- Ticket reason: " + reason + "\n" +
                "- Ticket status: " + s + "\n";
    }

    @Override
    public int compareTo(Ticket o) {
        return this.nif - o.nif;
    }

    public void renew() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date sqlDate = Date.valueOf(currentDate);
        if(expiry_date.after(sqlDate)) {
            LocalDate newDate = currentDate.plusMonths(3);
            expiry_date = Date.valueOf(newDate);
        }
    }


}
