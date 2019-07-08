package com.universign.universigncs.reports.repository;

import com.universign.universigncs.reports.domain.ReportTemplate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ReportTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportTemplateRepository extends MongoRepository<ReportTemplate, String> {

}
