package org.example.Dao.Impl;

import org.example.Dao.EmployeeDao;
import org.example.config.jdbcConfig;
import org.example.model.Employee;
import org.example.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private  final Connection connection = jdbcConfig.getConnection();
    @Override
    public void createEmployee() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
            create  table if not exists employees (
            id serial  primary key ,
            first_name varchar,
            last_name varchar,
            date_of_birth date,
            email varchar,
        
            job_id int references jobs(id));

        """);
            statement.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean addEmployee(Employee newEmployee) {
            String query = """
        insert into employees (first_name,last_name,date_of_birth,email,job_id)
        values(?, ?, ?, ?, ?);
        """;
            int execute = 0;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

                preparedStatement.setString(1,newEmployee.getFirstName());
                preparedStatement.setString(2, newEmployee.getLastName());
                preparedStatement.setDate(3,Date.valueOf( newEmployee.getDate_of_birth()));
                preparedStatement.setString(4,newEmployee.getEmail());
                preparedStatement.setLong(5,newEmployee.getJob_id());
                execute= preparedStatement.executeUpdate();

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

            return execute != 0;
        }


    @Override
    public void dropTable() {
            String query = "drop table employees;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println(preparedStatement.execute());
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }

    }

    @Override
    public void cleanTable() {

            String query = "delete from employees;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                System.out.println(preparedStatement.execute());
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        String query = "update employees" +
                "set first_name = ?," +
                "last_name = ?," +
                "date_of_birth = ?," +
                "email = ?," +
                "job_id =?," +
                "where id = ?";
        int execute = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setDate(3,Date.valueOf(employee.getDate_of_birth()));
            preparedStatement.setString(4,employee.getEmail());
            preparedStatement.setLong(5,employee.getJob_id());
            preparedStatement.setLong(6,id);
            execute= preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return execute !=0 ? "Updated" : "Error";

    }

    @Override
    public List<Employee> getAllEmployees() {
List<Employee> employees = new ArrayList<>();

String query = " select * from employees;";
try(Statement statement = connection.createStatement()){
ResultSet resultSet = statement.executeQuery(query);
    while(resultSet.next()) {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setDate_of_birth(resultSet.getDate("date_of_birth").toLocalDate());
       employee.setEmail(resultSet.getString("email"));
       employee.setJob_id(resultSet.getInt("job_id"));
       employees.add(employee);

    }
}catch (SQLException e){
    System.out.println(e.getMessage());
}

        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
    Employee employees = new Employee();
String query ="select * from  employees where email = ?;";
try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
preparedStatement.setString(1,email);
ResultSet resultSet = preparedStatement.executeQuery();
if(!resultSet.next()){
throw  new RuntimeException("Employee with email "+email+"not found");
}
employees.setId(resultSet.getLong("id"));
employees.setFirstName(resultSet.getString("first_name"));
employees.setLastName(resultSet.getString("last_name"));
employees.setDate_of_birth(resultSet.getDate("date_od_birth").toLocalDate());
employees.setEmail(resultSet.getString("email"));
employees.setJob_id(resultSet.getInt("job_id"));
resultSet.close();
}catch (SQLException e){
    System.out.println(e.getMessage());
}

       return employees;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
Map<Employee,Job> result = new HashMap<>();
String query = "SELECT e.*, j.* " +
        "FROM employees e " +
        "JOIN jobs j ON e.job_id = j.job_id " +
        "WHERE e.id = ?";
try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
    preparedStatement.setString(1, String.valueOf(employeeId));
    ResultSet resultSet = preparedStatement.executeQuery();
    if(!resultSet.next()){
        throw  new RuntimeException("User with id "+employeeId+"not found");
    }
    Employee employee = new Employee();
    employee.setId(resultSet.getLong("e.id"));
    employee.setFirstName(resultSet.getString("e.first_name"));
    employee.setLastName(resultSet.getString("e.last_name"));
    employee.setDate_of_birth(resultSet.getDate("e.date_of_birth").toLocalDate());
    employee.setEmail(resultSet.getString("e.email"));
    employee.setJob_id(resultSet.getInt("e.job_id"));

    Job job = new Job();
    job.setId(resultSet.getLong("j.id"));
    job.setPosition(resultSet.getString("positions"));
    job.setProfession(resultSet.getString("profession"));
    job.setDescription(resultSet.getString("description"));
    job.getExperience(resultSet.getInt("experience"));
    result.put(employee, job);
resultSet.close();
}catch (SQLException e){
    System.out.println(e.getMessage());
}
        return result;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
String query = "select * from employees e inner join jobs j on j.id=e.id where j.position=?";
List<Employee> employees = new ArrayList<>();
try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
    Employee employee = new Employee();
    ResultSet resultSet = preparedStatement.executeQuery();
    employee.setId(resultSet.getLong("id"));
    employee.setFirstName(resultSet.getString("first_name"));
    employee.setLastName(resultSet.getString("last_name"));
    employee.setDate_of_birth(resultSet.getDate("date_of_birth").toLocalDate());
    employee.setEmail(resultSet.getString("email"));
    employee.setJob_id(resultSet.getInt("job_id"));
    resultSet.close();
}catch (SQLException e){
    System.out.println(e.getMessage());
}


        return employees;
    }
}
