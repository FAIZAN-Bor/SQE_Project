package EmployeeManagement;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAL {
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdb", "root", "");
    }

    public List<Employee> getAllEmployees() throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
        List<Employee> employees = new ArrayList<>();
        while (rs.next()) {
            employees.add(new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("department"), rs.getDouble("salary")));
        }
        conn.close();
        return employees;
    }

    public void addEmployee(Employee employee) throws SQLException {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)");
        stmt.setInt(1, employee.getId());
        stmt.setString(2, employee.getName());
        stmt.setString(3, employee.getDepartment());
        stmt.setDouble(4, employee.getSalary());
        stmt.executeUpdate();
        conn.close();
    }

    public void updateEmployee(Employee employee) throws SQLException {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement("UPDATE employees SET name=?, department=?, salary=? WHERE id=?");
        stmt.setString(1, employee.getName());
        stmt.setString(2, employee.getDepartment());
        stmt.setDouble(3, employee.getSalary());
        stmt.setInt(4, employee.getId());
        stmt.executeUpdate();
        conn.close();
    }

    public List<Employee> getEmployeesByDepartment(String department) throws SQLException {
        Connection conn = connect();
        String query = "SELECT * FROM employees WHERE department = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, department);
        ResultSet rs = stmt.executeQuery();

        List<Employee> employees = new ArrayList<>();
        while (rs.next()) {
            Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("department"), rs.getDouble("salary"));
            employees.add(employee);
        }
        conn.close();
        return employees;
    }

    public void deleteEmployee(int id) throws SQLException {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM employees WHERE id=?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        conn.close();
    }
}