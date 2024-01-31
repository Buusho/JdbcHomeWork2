package org.example;

import org.example.Services.EmployeeService;
import org.example.Services.Impl.EmployeeServiceImpl;
import org.example.Services.Impl.JobServiceImpl;
import org.example.Services.JobDaoService;
import org.example.model.Employee;
import org.example.model.Job;

import java.time.LocalDate;

public class App {

    public static void main( String[] args ){


        JobDaoService jobDaoService = new JobServiceImpl();
//        jobDaoService.createJobTable();
  //      System.out.println(jobDaoService.addJob(new Job("Best","inginier","good",1)));

        EmployeeService employeeService= new EmployeeServiceImpl();
//        employeeService.createEmployee();
        System.out.println(employeeService.addEmployee(new Employee("Adyl","Tolomushev", LocalDate.of(2005,2,17),"adyl@gmail.com",1)));




    }
}
