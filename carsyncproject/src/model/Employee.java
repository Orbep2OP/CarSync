package model;

import employeeacess.DataSource;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;

public class Employee extends Person {
    private int access_level;

    public int getAccess_level() {
        return access_level;
    }

    public void setAccess_level(int access_level) {
        this.access_level = access_level;
    }

    @Override
    public int compareTo(Person o) {
        return super.nif - o.nif;
    }

    @Override
    public String toString() {

        String print = """
                Employee info:
                - NIF: %d
                - Name: %s
                - Birth Date: %s
                - Address: %s
                - Access Level: %d
                """;
        return String.format(print, nif, name, birht_date, address, access_level);
    }

    public static class EmployeeTableModel extends AbstractTableModel {
        private final String[] columnNames = {"NIF", "Name", "Birth Date", "Address", "Access Level"};
        private List<Employee> employees = new DataSource().queryEmployees();
        private static int size;
        public EmployeeTableModel(int order, boolean originalList) {
            if(originalList) { employees = new DataSource().queryEmployees();}
            size = employees.size();
            if (order == 1) {
                employees.sort(Comparator.comparing(Person::getNif));
            } else if (order == 2) {
                employees.sort(Comparator.comparing(Person::getName));
            } else if (order == 3) {
                employees.sort(Comparator.comparing(Employee::getBirht_date));
            } else if (order == 4) {
                employees.sort(Comparator.comparing(Person::getAddress));
            }

        }

        public static int getSize() {
            return size;
        }

        public EmployeeTableModel(int order, int numericFilter) {
            List<Employee> employeeList = new DataSource().queryEmployees();
            for (Employee employee : employees) {
                if (employee.getNif() == numericFilter) {
                    employeeList.add(employee);
                }
                if (employee.getAccess_level() == numericFilter) {
                    employeeList.add(employee);
                }
            }
            employees = employeeList;
            new EmployeeTableModel(order, false);
        }

        @Override
        public int getRowCount() {
            return employees.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Employee employee = employees.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> employee.getNif();
                case 1 -> employee.getName();
                case 2 -> employee.getBirht_date();
                case 3 -> employee.getAddress();
                case 4 -> employee.getAccess_level();
                default -> null;
            };
        }
    }
}
