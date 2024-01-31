package org.example.Dao;

import org.example.model.Employee;
import org.example.model.Job;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    void createEmployee();
    boolean addEmployee(Employee newEmployee);
    void dropTable();
    void cleanTable();
   String updateEmployee(Long id,Employee employee);
    List<Employee> getAllEmployees();
    Employee findByEmail(String email);
    Map<Employee, Job> getEmployeeById(Long employeeId);
    List<Employee> getEmployeeByPosition(String position);
}
