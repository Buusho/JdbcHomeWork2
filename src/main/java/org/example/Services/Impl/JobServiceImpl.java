package org.example.Services.Impl;

import org.example.Dao.Impl.JobDaoImpl;
import org.example.Dao.JobDao;
import org.example.Services.JobDaoService;
import org.example.model.Job;

import java.util.List;

public class JobServiceImpl implements JobDaoService {
    private final JobDao jobDao = new JobDaoImpl();
    @Override
    public void createJobTable() {
        this.jobDao.createJobTable();
    }

    @Override
    public boolean addJob(Job job) {
        return jobDao.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }

    @Override
    public void deleteDescriptionColumn() {
this.jobDao.deleteDescriptionColumn();
    }
}
