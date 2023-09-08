package model;

import java.sql.Date;

public class Customer extends Person {
    private int driverLicenseNum;
    private String licenseType;
    private Date startingDate;
    private Date expDate;

    public int getDriverLicenseNum() {
        return driverLicenseNum;
    }

    public void setDriverLicenseNum(int driverLicenseNum) {
//        LogUtil.info("Setting Driver License Number: " + driverLicenseNum);
        this.driverLicenseNum = driverLicenseNum;
    }


    public String getLicenseType() {
        return licenseType;
    }

    public int getLicenseTypeNumber() {
        int licenseTypen = switch (this.licenseType) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            case "D" -> 4;
            default -> throw new IllegalStateException("No such category");
        };
        return licenseTypen;
    }

    public void setLicenseType(String licenseType) {
//        LogUtil.info("Setting License Type: " + licenseType);
        this.licenseType = licenseType;
    }


   /* public void setLicenseType(int sLicenseType) {
        String licenseType = switch (sLicenseType) {
            case 1 -> this.licenseType = "A";
            case 2 -> this.licenseType = "B";
            case 3 -> this.licenseType = "C";
            case 4 -> this.licenseType = "D";
            default -> throw new IllegalStateException("No such category");
        };
//        LogUtil.info("Setting License Type: " + licenseType);
//        this.licenseType = licenseType;
    }*/


    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
//        LogUtil.info("Setting Starting Date: " + startingDate);
        this.startingDate = startingDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
//        LogUtil.info("Setting Expiration Date: " + expDate);
        this.expDate = expDate;
    }

    @Override
    public String toString() {
        String print = """
                Customer info:
                - NIF: %d
                - Name: %s
                - Birth Date: %s
                - Address: %s
                - Driver's license number: %d
                - License Category: %s
                - Registration Date: %s
                - Expiration Data: %s
                - Email: %s
                """;
        return String.format(print, nif, name, birht_date, address, driverLicenseNum, licenseType, startingDate, expDate, email);
    }

    @Override
    public int compareTo(Person o) {
        return super.nif - o.nif;
    }

/*    public static class CustomerTableModel extends AbstractTableModel {
        private final String[] columnNames = {"NIF", "Name", "Birth Date", "Address", "Driver's license number", "License Category",
                "Registration Date", "Expiration Data"};
        private List<Customer> customers = new DataSource().queryCustomers();
        private static int size;

        public CustomerTableModel(int order, boolean fullList) {
            if(fullList) { customers = new DataSource().queryCustomers();}
            size = customers.size();
            if (order == 1) {
                customers.sort(Comparator.comparing(Person::getNif));
            } else if (order == 2) {
                customers.sort(Comparator.comparing(Person::getName));
            } else if (order == 3) {
                customers.sort(Comparator.comparing(Person::getBirht_date));
            } else if (order == 4) {
                customers.sort(Comparator.comparing(Person::getAddress));
            } else if (order == 5) {
                customers.sort(Comparator.comparing(Customer::getDriverLicenseNum));
            } else if (order == 6) {
                customers.sort(Comparator.comparing(Customer::getLicenseType));
            } else if (order == 7) {
                customers.sort(Comparator.comparing(Customer::getStartingDate));
            } else if (order == 8) {
                customers.sort(Comparator.comparing(Customer::getExpDate));
            }
        }

        public static int getSize() {
            return size;
        }

        public CustomerTableModel(int order, int numericFilter) {
            List<Customer> customerList = new DataSource().queryCustomers();
            for (Customer customer : customers) {
                if (customer.getNif() == numericFilter) {
                    customerList.add(customer);
                } else if (customer.getDriverLicenseNum() == numericFilter) {
                    customerList.add(customer);
                }
            }
            customers = customerList;
            new CustomerTableModel(order, false);
        }

        public CustomerTableModel(int order, String stringFilter, String filterType) {
            List<Customer> customerList = new DataSource().queryCustomers();

            switch (filterType) {
                case "Name" -> {
                    for (Customer customer : customers) {
                        if (customer.getName().contains(stringFilter)) {
                            customerList.add(customer);
                        }
                    }
                }
                case "Address" -> {
                    for (Customer customer : customers) {
                        if (customer.getAddress().equals(stringFilter)) {
                            customerList.add(customer);
                        }
                    }
                }
                case "License Type" -> {
                    for (Customer customer : customers) {
                        if (customer.getLicenseType().equals(stringFilter)) {
                            customerList.add(customer);
                        }
                    }
                }
            }

            customers = customerList;
            new CustomerTableModel(order, false);
        }

        public CustomerTableModel(int order, Date dateFilter, String filterType) {
            List<Customer> customerList = new DataSource().queryCustomers();

            switch (filterType) {
                case "Birth Date" -> {
                    for (Customer customer : customers) {
                        if (customer.getBirht_date().equals(dateFilter)) {
                            customerList.add(customer);
                        }
                    }
                }
                case "Registration Date" -> {
                    for (Customer customer : customers) {
                        if (customer.getStartingDate().equals(dateFilter)) {
                            customerList.add(customer);
                        }
                    }
                }
                case "Expiration Date" -> {
                    for (Customer customer : customers) {
                        if (customer.getExpDate().equals(dateFilter)) {
                            customerList.add(customer);
                        }
                    }
                }
            }

            customers = customerList;
            new CustomerTableModel(order, false);
        }

        @Override
        public int getRowCount() {
            return customers.size();
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
            Customer customer = customers.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> customer.getNif();
                case 1 -> customer.getName();
                case 2 -> customer.getBirht_date();
                case 3 -> customer.getAddress();
                case 4 -> customer.getDriverLicenseNum();
                case 5 -> customer.getLicenseType();
                case 6 -> customer.getStartingDate();
                case 7 -> customer.getExpDate();
                default -> null;
            };
        }

    }*/

}