package com.universign.universigncs.reports.web.rest;

import com.universign.universigncs.reports.ReportsApp;
import com.universign.universigncs.reports.domain.ReportResult;
import com.universign.universigncs.reports.repository.ReportResultRepository;
import com.universign.universigncs.reports.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static com.universign.universigncs.reports.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ReportResultResource} REST controller.
 */
@SpringBootTest(classes = ReportsApp.class)
public class ReportResultResourceIT {

    private static final String DEFAULT_GROUP_REPORT_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_REPORT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_ID = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_ID = "BBBBBBBBBB";

    @Autowired
    private ReportResultRepository reportResultRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restReportResultMockMvc;

    private ReportResult reportResult;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportResultResource reportResultResource = new ReportResultResource(reportResultRepository);
        this.restReportResultMockMvc = MockMvcBuilders.standaloneSetup(reportResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportResult createEntity() {
        ReportResult reportResult = new ReportResult()
            .groupReportId(DEFAULT_GROUP_REPORT_ID)
            .reportId(DEFAULT_REPORT_ID);
        return reportResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportResult createUpdatedEntity() {
        ReportResult reportResult = new ReportResult()
            .groupReportId(UPDATED_GROUP_REPORT_ID)
            .reportId(UPDATED_REPORT_ID);
        return reportResult;
    }

    @BeforeEach
    public void initTest() {
        reportResultRepository.deleteAll();
        reportResult = createEntity();
    }

    @Test
    public void createReportResult() throws Exception {
        int databaseSizeBeforeCreate = reportResultRepository.findAll().size();

        // Create the ReportResult
        restReportResultMockMvc.perform(post("/api/report-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportResult)))
            .andExpect(status().isCreated());

        // Validate the ReportResult in the database
        List<ReportResult> reportResultList = reportResultRepository.findAll();
        assertThat(reportResultList).hasSize(databaseSizeBeforeCreate + 1);
        ReportResult testReportResult = reportResultList.get(reportResultList.size() - 1);
        assertThat(testReportResult.getGroupReportId()).isEqualTo(DEFAULT_GROUP_REPORT_ID);
        assertThat(testReportResult.getReportId()).isEqualTo(DEFAULT_REPORT_ID);
    }

    @Test
    public void createReportResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportResultRepository.findAll().size();

        // Create the ReportResult with an existing ID
        reportResult.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportResultMockMvc.perform(post("/api/report-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportResult)))
            .andExpect(status().isBadRequest());

        // Validate the ReportResult in the database
        List<ReportResult> reportResultList = reportResultRepository.findAll();
        assertThat(reportResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllReportResults() throws Exception {
        // Initialize the database
        reportResultRepository.save(reportResult);

        // Get all the reportResultList
        restReportResultMockMvc.perform(get("/api/report-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportResult.getId())))
            .andExpect(jsonPath("$.[*].groupReportId").value(hasItem(DEFAULT_GROUP_REPORT_ID.toString())))
            .andExpect(jsonPath("$.[*].reportId").value(hasItem(DEFAULT_REPORT_ID.toString())));
    }
    
    @Test
    public void getReportResult() throws Exception {
        // Initialize the database
        reportResultRepository.save(reportResult);

        // Get the reportResult
        restReportResultMockMvc.perform(get("/api/report-results/{id}", reportResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportResult.getId()))
            .andExpect(jsonPath("$.groupReportId").value(DEFAULT_GROUP_REPORT_ID.toString()))
            .andExpect(jsonPath("$.reportId").value(DEFAULT_REPORT_ID.toString()));
    }

    @Test
    public void getNonExistingReportResult() throws Exception {
        // Get the reportResult
        restReportResultMockMvc.perform(get("/api/report-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateReportResult() throws Exception {
        // Initialize the database
        reportResultRepository.save(reportResult);

        int databaseSizeBeforeUpdate = reportResultRepository.findAll().size();

        // Update the reportResult
        ReportResult updatedReportResult = reportResultRepository.findById(reportResult.getId()).get();
        updatedReportResult
            .groupReportId(UPDATED_GROUP_REPORT_ID)
            .reportId(UPDATED_REPORT_ID);

        restReportResultMockMvc.perform(put("/api/report-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReportResult)))
            .andExpect(status().isOk());

        // Validate the ReportResult in the database
        List<ReportResult> reportResultList = reportResultRepository.findAll();
        assertThat(reportResultList).hasSize(databaseSizeBeforeUpdate);
        ReportResult testReportResult = reportResultList.get(reportResultList.size() - 1);
        assertThat(testReportResult.getGroupReportId()).isEqualTo(UPDATED_GROUP_REPORT_ID);
        assertThat(testReportResult.getReportId()).isEqualTo(UPDATED_REPORT_ID);
    }

    @Test
    public void updateNonExistingReportResult() throws Exception {
        int databaseSizeBeforeUpdate = reportResultRepository.findAll().size();

        // Create the ReportResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportResultMockMvc.perform(put("/api/report-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportResult)))
            .andExpect(status().isBadRequest());

        // Validate the ReportResult in the database
        List<ReportResult> reportResultList = reportResultRepository.findAll();
        assertThat(reportResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteReportResult() throws Exception {
        // Initialize the database
        reportResultRepository.save(reportResult);

        int databaseSizeBeforeDelete = reportResultRepository.findAll().size();

        // Delete the reportResult
        restReportResultMockMvc.perform(delete("/api/report-results/{id}", reportResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportResult> reportResultList = reportResultRepository.findAll();
        assertThat(reportResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportResult.class);
        ReportResult reportResult1 = new ReportResult();
        reportResult1.setId("id1");
        ReportResult reportResult2 = new ReportResult();
        reportResult2.setId(reportResult1.getId());
        assertThat(reportResult1).isEqualTo(reportResult2);
        reportResult2.setId("id2");
        assertThat(reportResult1).isNotEqualTo(reportResult2);
        reportResult1.setId(null);
        assertThat(reportResult1).isNotEqualTo(reportResult2);
    }
}
