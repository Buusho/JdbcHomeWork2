package org.example.Dao;

import org.example.model.Job;

import java.util.List;

public interface JobDao {



    void createJobTable();
   boolean addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(String ascOrDesc);
    Job getJobByEmployeeId(Long employeeId);
    void deleteDescriptionColumn();

}
