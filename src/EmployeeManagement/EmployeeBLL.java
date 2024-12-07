package EmployeeManagement;


import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class EmployeeBLL {
    private EmployeeDAL employeeDAL = new EmployeeDAL();

    public List<Employee> getAllEmployees() throws SQLException {
        // Chaos: Randomly simulate failure in business logic
        if (new Random().nextInt(10) < 2) {  // 20% chance to fail
            throw new IllegalStateException("Simulated Business Logic Failure.");
        }
        return employeeDAL.getAllEmployees();
    }

    public void addEmployee(int id, String name, String department, double salary) throws SQLException {
        if (name.isEmpty() || department.isEmpty() || salary <= 0) {
            throw new IllegalArgumentException("Invalid input data.");
        }
        Employee employee = new Employee(id, name, department, salary);

        // Chaos: Randomly simulate failure during the add process
        if (new Random().nextInt(10) < 3) {  // 30% chance to fail
            throw new SQLException("Simulated Failure While Adding Employee.");
        }
        employeeDAL.addEmployee(employee);
    }

    public void updateEmployee(int id, String name, String department, double salary) throws SQLException {
        if (name.isEmpty() || department.isEmpty() || salary <= 0) {
            throw new IllegalArgumentException("Invalid input data.");
        }
        Employee employee = new Employee(id, name, department, salary);

        // Chaos: Randomly simulate failure during the update process
        if (new Random().nextInt(10) < 3) {  // 30% chance to fail
            throw new SQLException("Simulated Failure While Updating Employee.");
        }
        employeeDAL.updateEmployee(employee);
    }

    public void deleteEmployee(int id) throws SQLException {
        // Chaos: Randomly simulate failure during the delete process
        if (new Random().nextInt(10) < 2) {  // 20% chance to fail
            throw new SQLException("Simulated Failure While Deleting Employee.");
        }
        employeeDAL.deleteEmployee(id);
    }

    public List<Employee> getEmployeesByDepartment(String department) throws SQLException {
        // Chaos: Randomly simulate failure during the department filtering process
        if (new Random().nextInt(10) < 2) {  // 20% chance to fail
            throw new IllegalStateException("Simulated Failure While Fetching Employees by Department.");
        }
        return employeeDAL.getEmployeesByDepartment(department);
    }
}