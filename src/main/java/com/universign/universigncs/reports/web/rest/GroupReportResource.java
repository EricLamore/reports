package com.universign.universigncs.reports.web.rest;

import com.universign.universigncs.reports.domain.GroupReport;
import com.universign.universigncs.reports.repository.GroupReportRepository;
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
 * REST controller for managing {@link com.universign.universigncs.reports.domain.GroupReport}.
 */
@RestController
@RequestMapping("/api")
public class GroupReportResource {

    private final Logger log = LoggerFactory.getLogger(GroupReportResource.class);

    private static final String ENTITY_NAME = "groupReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupReportRepository groupReportRepository;

    public GroupReportResource(GroupReportRepository groupReportRepository) {
        this.groupReportRepository = groupReportRepository;
    }

    /**
     * {@code POST  /group-reports} : Create a new groupReport.
     *
     * @param groupReport the groupReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupReport, or with status {@code 400 (Bad Request)} if the groupReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-reports")
    public ResponseEntity<GroupReport> createGroupReport(@RequestBody GroupReport groupReport) throws URISyntaxException {
        log.debug("REST request to save GroupReport : {}", groupReport);
        if (groupReport.getId() != null) {
            throw new BadRequestAlertException("A new groupReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupReport result = groupReportRepository.save(groupReport);
        return ResponseEntity.created(new URI("/api/group-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-reports} : Updates an existing groupReport.
     *
     * @param groupReport the groupReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupReport,
     * or with status {@code 400 (Bad Request)} if the groupReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-reports")
    public ResponseEntity<GroupReport> updateGroupReport(@RequestBody GroupReport groupReport) throws URISyntaxException {
        log.debug("REST request to update GroupReport : {}", groupReport);
        if (groupReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupReport result = groupReportRepository.save(groupReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-reports} : get all the groupReports.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupReports in body.
     */
    @GetMapping("/group-reports")
    public ResponseEntity<List<GroupReport>> getAllGroupReports(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of GroupReports");
        Page<GroupReport> page = groupReportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-reports/:id} : get the "id" groupReport.
     *
     * @param id the id of the groupReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-reports/{id}")
    public ResponseEntity<GroupReport> getGroupReport(@PathVariable String id) {
        log.debug("REST request to get GroupReport : {}", id);
        Optional<GroupReport> groupReport = groupReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupReport);
    }

    /**
     * {@code DELETE  /group-reports/:id} : delete the "id" groupReport.
     *
     * @param id the id of the groupReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-reports/{id}")
    public ResponseEntity<Void> deleteGroupReport(@PathVariable String id) {
        log.debug("REST request to delete GroupReport : {}", id);
        groupReportRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
