package org.example.Services.Impl;

import org.example.Dao.EmployeeDao;
import org.example.Dao.Impl.EmployeeDaoImpl;
import org.example.Services.EmployeeService;
import org.example.model.Employee;
import org.example.model.Job;

import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {
    private  final EmployeeDao employeeDao = new EmployeeDaoImpl();
    @Override
    public void createEmployee() {
this.employeeDao.createEmployee();
    }

    @Override
    public boolean addEmployee(Employee newEmployee) {
        return employeeDao.addEmployee(newEmployee);
    }

    @Override
    public void dropTable() {
this.employeeDao.dropTable();
    }

    @Override
    public void cleanTable() {
this.employeeDao.cleanTable();
    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        return employeeDao.updateEmployee(id,employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeDao.findByEmail(email);
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        return employeeDao.getEmployeeByPosition(position);
    }
}
