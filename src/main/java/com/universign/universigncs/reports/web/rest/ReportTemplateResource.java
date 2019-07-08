package com.universign.universigncs.reports.web.rest;

import com.universign.universigncs.reports.domain.ReportTemplate;
import com.universign.universigncs.reports.repository.ReportTemplateRepository;
import com.universign.universigncs.reports.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.universign.universigncs.reports.domain.ReportTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ReportTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ReportTemplateResource.class);

    private static final String ENTITY_NAME = "reportTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportTemplateRepository reportTemplateRepository;

    public ReportTemplateResource(ReportTemplateRepository reportTemplateRepository) {
        this.reportTemplateRepository = reportTemplateRepository;
    }

    /**
     * {@code POST  /report-templates} : Create a new reportTemplate.
     *
     * @param reportTemplate the reportTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportTemplate, or with status {@code 400 (Bad Request)} if the reportTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-templates")
    public ResponseEntity<ReportTemplate> createReportTemplate(@RequestBody ReportTemplate reportTemplate) throws URISyntaxException {
        log.debug("REST request to save ReportTemplate : {}", reportTemplate);
        if (reportTemplate.getId() != null) {
            throw new BadRequestAlertException("A new reportTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportTemplate result = reportTemplateRepository.save(reportTemplate);
        return ResponseEntity.created(new URI("/api/report-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-templates} : Updates an existing reportTemplate.
     *
     * @param reportTemplate the reportTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportTemplate,
     * or with status {@code 400 (Bad Request)} if the reportTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-templates")
    public ResponseEntity<ReportTemplate> updateReportTemplate(@RequestBody ReportTemplate reportTemplate) throws URISyntaxException {
        log.debug("REST request to update ReportTemplate : {}", reportTemplate);
        if (reportTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportTemplate result = reportTemplateRepository.save(reportTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-templates} : get all the reportTemplates.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportTemplates in body.
     */
    @GetMapping("/report-templates")
    public ResponseEntity<List<ReportTemplate>> getAllReportTemplates(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ReportTemplates");
        Page<ReportTemplate> page = reportTemplateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-templates/:id} : get the "id" reportTemplate.
     *
     * @param id the id of the reportTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-templates/{id}")
    public ResponseEntity<ReportTemplate> getReportTemplate(@PathVariable String id) {
        log.debug("REST request to get ReportTemplate : {}", id);
        Optional<ReportTemplate> reportTemplate = reportTemplateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportTemplate);
    }

    /**
     * {@code DELETE  /report-templates/:id} : delete the "id" reportTemplate.
     *
     * @param id the id of the reportTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-templates/{id}")
    public ResponseEntity<Void> deleteReportTemplate(@PathVariable String id) {
        log.debug("REST request to delete ReportTemplate : {}", id);
        reportTemplateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
