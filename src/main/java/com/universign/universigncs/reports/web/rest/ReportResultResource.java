package com.universign.universigncs.reports.web.rest;

import com.universign.universigncs.reports.domain.ReportResult;
import com.universign.universigncs.reports.repository.ReportResultRepository;
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
 * REST controller for managing {@link com.universign.universigncs.reports.domain.ReportResult}.
 */
@RestController
@RequestMapping("/api")
public class ReportResultResource {

    private final Logger log = LoggerFactory.getLogger(ReportResultResource.class);

    private static final String ENTITY_NAME = "reportResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportResultRepository reportResultRepository;

    public ReportResultResource(ReportResultRepository reportResultRepository) {
        this.reportResultRepository = reportResultRepository;
    }

    /**
     * {@code POST  /report-results} : Create a new reportResult.
     *
     * @param reportResult the reportResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportResult, or with status {@code 400 (Bad Request)} if the reportResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-results")
    public ResponseEntity<ReportResult> createReportResult(@RequestBody ReportResult reportResult) throws URISyntaxException {
        log.debug("REST request to save ReportResult : {}", reportResult);
        if (reportResult.getId() != null) {
            throw new BadRequestAlertException("A new reportResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportResult result = reportResultRepository.save(reportResult);
        return ResponseEntity.created(new URI("/api/report-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-results} : Updates an existing reportResult.
     *
     * @param reportResult the reportResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportResult,
     * or with status {@code 400 (Bad Request)} if the reportResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-results")
    public ResponseEntity<ReportResult> updateReportResult(@RequestBody ReportResult reportResult) throws URISyntaxException {
        log.debug("REST request to update ReportResult : {}", reportResult);
        if (reportResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportResult result = reportResultRepository.save(reportResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-results} : get all the reportResults.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportResults in body.
     */
    @GetMapping("/report-results")
    public ResponseEntity<List<ReportResult>> getAllReportResults(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ReportResults");
        Page<ReportResult> page = reportResultRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-results/:id} : get the "id" reportResult.
     *
     * @param id the id of the reportResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-results/{id}")
    public ResponseEntity<ReportResult> getReportResult(@PathVariable String id) {
        log.debug("REST request to get ReportResult : {}", id);
        Optional<ReportResult> reportResult = reportResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportResult);
    }

    /**
     * {@code DELETE  /report-results/:id} : delete the "id" reportResult.
     *
     * @param id the id of the reportResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-results/{id}")
    public ResponseEntity<Void> deleteReportResult(@PathVariable String id) {
        log.debug("REST request to delete ReportResult : {}", id);
        reportResultRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
