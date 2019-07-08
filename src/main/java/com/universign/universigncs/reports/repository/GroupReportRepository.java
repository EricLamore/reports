package com.universign.universigncs.reports.repository;

import com.universign.universigncs.reports.domain.GroupReport;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the GroupReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupReportRepository extends MongoRepository<GroupReport, String> {

}
