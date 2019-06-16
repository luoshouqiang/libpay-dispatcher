package com.libpay.dispatcher.repository;

import com.libpay.dispatcher.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobDetailsRepository extends JpaRepository<JobDetails,Integer> {
    public List<JobDetails> findByEnable(boolean enable);
}
