package model;

import employeeacess.DataSource;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Insurance implements Comparable<Insurance> {
    private int policy;
    private Date startDate;
    private Date expDate;
    private String companyName;
    private String extraCategory;
    private String carPlate;
    private boolean deactivated = false;

    public int getPolicy() {
        return policy;
    }

    public void deactivate() {
        this.deactivated = true;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public void setPolicy(int policy) {
        if (policy < 100000000 || policy > 199999999) System.out.println("Policy number must be 10 digits long");
        this.policy = policy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate() {
        this.startDate = new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime());
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExtraCategory() {
        return extraCategory;
    }

   /* public void setExtraCategory(int extraCategory) {
        switch (extraCategory) {
            case 1 -> this.extraCategory = "Comprehensive Insurance";
            case 2 -> this.extraCategory = "Auto Liability Insurance";
            case 3 -> this.extraCategory = "Theft Insurance";
            default -> this.extraCategory = "Default";
        };
    }*/

    public void setExtraCategory(String extraCategory) {
        this.extraCategory = extraCategory;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    @Override
    public String toString() {
        return "Insurance policy number - " + policy + ", info:" + "\n"
                + "- Start date: " + startDate + "\n"
                + "- Expiration date: " + expDate + "\n"
                + "- Company name: " + companyName + "\n"
                + "- Extra category: " + extraCategory + "\n"
                + "- Car plate: " + carPlate + "\n";
    }

    //Default way of comparing
    @Override
    public int compareTo(Insurance o) {
        return this.policy - o.policy;
    }

    public void renew() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
        if(expDate.after(sqlDate)) {
            LocalDate newDate = currentDate.plusYears(1);
            expDate = java.sql.Date.valueOf(newDate);
        }
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

    public static class InsuranceTableModel extends AbstractTableModel {

        private final String[] columnNames = {"Policy", "Category", "Car Plate",
                "Company Name", "Start Date", "Expiration Date"};

        private List<Insurance> insurances = new DataSource().queryInsurances();

        @Override
        public int getRowCount() {
            return insurances.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Insurance insurance = insurances.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> insurance.getPolicy();
                case 1 -> insurance.getExtraCategory();
                case 2 -> insurance.getCarPlate();
                case 3 -> insurance.getCompanyName();
                case 4 -> insurance.getStartDate();
                case 5 -> insurance.getExpDate();
                default -> null;
            };
        }
    }

}
