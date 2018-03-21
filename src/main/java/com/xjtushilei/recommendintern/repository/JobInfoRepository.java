package com.xjtushilei.recommendintern.repository;

import com.xjtushilei.recommendintern.entity.JobInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobInfoRepository extends JpaRepository<JobInfo, String> {

    Page<JobInfo> findAllByTitleContains(String key, Pageable pageable);

    Page<JobInfo> findAllByTitleContainsOrContentContains(String key, String key2, Pageable pageable);

}
