package org.example.Dao.Impl;

import org.example.Dao.JobDao;
import org.example.config.jdbcConfig;
import org.example.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private  final Connection connection = jdbcConfig.getConnection();
    @Override
    public void createJobTable() {
try{
    Statement statement = connection.createStatement();
    statement.executeUpdate("""
create table if not exists  jobs(
id serial primary key,
position varchar,
profession varchar,
description varchar,
experience int);
""");
    statement.close();
}catch (SQLException e){
    System.out.println(e.getMessage());
}

    }

    @Override
    public boolean addJob(Job job) {


        String query = """
        insert into jobs (position,profession,description,experience)
        values(?, ?, ?, ?);
        """;
        int execute = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1,job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3,job.getDescription());
          preparedStatement.setInt(4,job.getExperience(1));
            execute= preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return execute != 0;
    }

    @Override
    public Job getJobById(Long jobId) {
    Job jobs = new Job();
    String query = "select * from jobs where id = ?;";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
        preparedStatement.setLong(1,jobId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()){
            throw new RuntimeException("Job with id "+jobId+"not found");
        }
        jobs.setId(resultSet.getLong("id"));
        jobs.setPosition(resultSet.getString("position"));
        jobs.setProfession(resultSet.getString("profession"));
        jobs.setDescription(resultSet.getString("description"));
        jobs.setExperience(resultSet.getInt("experience"));
        resultSet.close();
    }catch (SQLException e){
        System.out.println(e.getMessage());
    }


        return jobs;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        String query = "select s from jobs   order by experience"+ascOrDesc;
        List<Job> jobs = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.getResultSet();
            Job job = new Job();
            job.setPosition(resultSet.getString("position"));
            job.setProfession(resultSet.getString("profession"));
            job.setDescription(resultSet.getString("description"));
            job.setExperience(resultSet.getInt("experience"));
            jobs.add(job);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String query  = "select s from employees where id =(select job_id from  employees where id=?); ";
      Job job = new Job();
      try{
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          ResultSet resultSet = preparedStatement.getResultSet();
          preparedStatement.setLong(1,employeeId);
          job.setId(resultSet.getLong("id"));
          job.setPosition(resultSet.getString("position"));
          job.setProfession(resultSet.getString("profession"));
          job.setDescription(resultSet.getString("description"));
          job.setExperience(resultSet.getInt("experience"));
          resultSet.close();
      }catch (SQLException e){
          System.out.println(e.getMessage());
      }

        return job;
    }

    @Override
    public void deleteDescriptionColumn() {
String query="alter table jobs drop column description";
try{
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.executeUpdate(query);
    System.out.println("success deleted");
}catch (SQLException e){
    System.out.println(e.getMessage());
}


    }
}
