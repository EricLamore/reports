package com.universign.universigncs.reports.repository;

import com.universign.universigncs.reports.domain.ReportResult;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ReportResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportResultRepository extends MongoRepository<ReportResult, String> {

}
