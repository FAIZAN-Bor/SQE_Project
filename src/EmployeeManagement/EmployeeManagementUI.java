package EmployeeManagement;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class EmployeeManagementUI extends JFrame {
    private EmployeeBLL employeeBLL = new EmployeeBLL();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtSalary;
    private JComboBox<String> cbDepartment;

    public EmployeeManagementUI() {
        setTitle("Employee Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Department", "Salary"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Department:"));
        cbDepartment = new JComboBox<>(new String[]{"All", "IT", "HR", "Sales", "SE"});
        inputPanel.add(cbDepartment);

        inputPanel.add(new JLabel("Salary:"));
        txtSalary = new JTextField();
        inputPanel.add(txtSalary);

        JPanel buttonsPanel = new JPanel();
        JButton btnAdd = new JButton("Add Employee");
        JButton btnUpdate = new JButton("Update Employee");
        JButton btnDelete = new JButton("Delete Employee");
        JButton btnSort = new JButton("Sort by Salary");
        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnUpdate);
        buttonsPanel.add(btnDelete);
        //buttonsPanel.add(btnSort);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnSort.addActionListener(e -> sortBySalary());

        cbDepartment.addActionListener(e -> filterByDepartment(cbDepartment.getSelectedItem().toString()));

        loadEmployees();
    }

    private void loadEmployees() {
        try {
            tableModel.setRowCount(0);
            List<Employee> employees = employeeBLL.getAllEmployees();
            for (Employee emp : employees) {
                tableModel.addRow(new Object[]{emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()});
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        } catch (IllegalStateException e) {
            showError("System failure: " + e.getMessage());
        }
    }

    private void filterByDepartment(String department) {
        try {
            tableModel.setRowCount(0);
            List<Employee> employees = department.equals("All") ? employeeBLL.getAllEmployees() : employeeBLL.getEmployeesByDepartment(department);
            for (Employee emp : employees) {
                tableModel.addRow(new Object[]{emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()});
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        } catch (IllegalStateException e) {
            showError("System failure: " + e.getMessage());
        }
    }

    private void addEmployee() throws NumberFormatException {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String department = cbDepartment.getSelectedItem().toString();
            double salary = Double.parseDouble(txtSalary.getText());
            employeeBLL.addEmployee(id, name, department, salary);
            loadEmployees();
        } catch (SQLException e) {
            showError(e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Invalid input. Please check the fields.");
        }
    }

    private void updateEmployee() throws NumberFormatException {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String department = cbDepartment.getSelectedItem().toString();
            double salary = Double.parseDouble(txtSalary.getText());
            employeeBLL.updateEmployee(id, name, department, salary);
            loadEmployees();
        } catch (SQLException e) {
            showError(e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Invalid input. Please check the fields.");
        }
    }

    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(txtId.getText());
            employeeBLL.deleteEmployee(id);
            loadEmployees();
        } catch (SQLException e) {
            showError(e.getMessage());
        } catch (NumberFormatException e) {
            showError("Invalid ID.");
        }
    }

    private void sortBySalary() {
        // Sorting logic here (optional)
        // You can sort employees by salary if needed
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementUI ui = new EmployeeManagementUI();
            ui.setVisible(true);
        });
    }
}