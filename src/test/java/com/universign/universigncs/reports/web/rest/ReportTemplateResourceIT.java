package com.universign.universigncs.reports.web.rest;

import com.universign.universigncs.reports.ReportsApp;
import com.universign.universigncs.reports.domain.ReportTemplate;
import com.universign.universigncs.reports.repository.ReportTemplateRepository;
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
 * Integration tests for the {@Link ReportTemplateResource} REST controller.
 */
@SpringBootTest(classes = ReportsApp.class)
public class ReportTemplateResourceIT {

    private static final String DEFAULT_GROUP_REPORT_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_REPORT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_ID = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_ID = "BBBBBBBBBB";

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restReportTemplateMockMvc;

    private ReportTemplate reportTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportTemplateResource reportTemplateResource = new ReportTemplateResource(reportTemplateRepository);
        this.restReportTemplateMockMvc = MockMvcBuilders.standaloneSetup(reportTemplateResource)
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
    public static ReportTemplate createEntity() {
        ReportTemplate reportTemplate = new ReportTemplate()
            .groupReportId(DEFAULT_GROUP_REPORT_ID)
            .reportId(DEFAULT_REPORT_ID);
        return reportTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportTemplate createUpdatedEntity() {
        ReportTemplate reportTemplate = new ReportTemplate()
            .groupReportId(UPDATED_GROUP_REPORT_ID)
            .reportId(UPDATED_REPORT_ID);
        return reportTemplate;
    }

    @BeforeEach
    public void initTest() {
        reportTemplateRepository.deleteAll();
        reportTemplate = createEntity();
    }

    @Test
    public void createReportTemplate() throws Exception {
        int databaseSizeBeforeCreate = reportTemplateRepository.findAll().size();

        // Create the ReportTemplate
        restReportTemplateMockMvc.perform(post("/api/report-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportTemplate)))
            .andExpect(status().isCreated());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ReportTemplate testReportTemplate = reportTemplateList.get(reportTemplateList.size() - 1);
        assertThat(testReportTemplate.getGroupReportId()).isEqualTo(DEFAULT_GROUP_REPORT_ID);
        assertThat(testReportTemplate.getReportId()).isEqualTo(DEFAULT_REPORT_ID);
    }

    @Test
    public void createReportTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportTemplateRepository.findAll().size();

        // Create the ReportTemplate with an existing ID
        reportTemplate.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportTemplateMockMvc.perform(post("/api/report-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllReportTemplates() throws Exception {
        // Initialize the database
        reportTemplateRepository.save(reportTemplate);

        // Get all the reportTemplateList
        restReportTemplateMockMvc.perform(get("/api/report-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportTemplate.getId())))
            .andExpect(jsonPath("$.[*].groupReportId").value(hasItem(DEFAULT_GROUP_REPORT_ID.toString())))
            .andExpect(jsonPath("$.[*].reportId").value(hasItem(DEFAULT_REPORT_ID.toString())));
    }
    
    @Test
    public void getReportTemplate() throws Exception {
        // Initialize the database
        reportTemplateRepository.save(reportTemplate);

        // Get the reportTemplate
        restReportTemplateMockMvc.perform(get("/api/report-templates/{id}", reportTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportTemplate.getId()))
            .andExpect(jsonPath("$.groupReportId").value(DEFAULT_GROUP_REPORT_ID.toString()))
            .andExpect(jsonPath("$.reportId").value(DEFAULT_REPORT_ID.toString()));
    }

    @Test
    public void getNonExistingReportTemplate() throws Exception {
        // Get the reportTemplate
        restReportTemplateMockMvc.perform(get("/api/report-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateReportTemplate() throws Exception {
        // Initialize the database
        reportTemplateRepository.save(reportTemplate);

        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();

        // Update the reportTemplate
        ReportTemplate updatedReportTemplate = reportTemplateRepository.findById(reportTemplate.getId()).get();
        updatedReportTemplate
            .groupReportId(UPDATED_GROUP_REPORT_ID)
            .reportId(UPDATED_REPORT_ID);

        restReportTemplateMockMvc.perform(put("/api/report-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReportTemplate)))
            .andExpect(status().isOk());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
        ReportTemplate testReportTemplate = reportTemplateList.get(reportTemplateList.size() - 1);
        assertThat(testReportTemplate.getGroupReportId()).isEqualTo(UPDATED_GROUP_REPORT_ID);
        assertThat(testReportTemplate.getReportId()).isEqualTo(UPDATED_REPORT_ID);
    }

    @Test
    public void updateNonExistingReportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();

        // Create the ReportTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportTemplateMockMvc.perform(put("/api/report-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteReportTemplate() throws Exception {
        // Initialize the database
        reportTemplateRepository.save(reportTemplate);

        int databaseSizeBeforeDelete = reportTemplateRepository.findAll().size();

        // Delete the reportTemplate
        restReportTemplateMockMvc.perform(delete("/api/report-templates/{id}", reportTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportTemplate.class);
        ReportTemplate reportTemplate1 = new ReportTemplate();
        reportTemplate1.setId("id1");
        ReportTemplate reportTemplate2 = new ReportTemplate();
        reportTemplate2.setId(reportTemplate1.getId());
        assertThat(reportTemplate1).isEqualTo(reportTemplate2);
        reportTemplate2.setId("id2");
        assertThat(reportTemplate1).isNotEqualTo(reportTemplate2);
        reportTemplate1.setId(null);
        assertThat(reportTemplate1).isNotEqualTo(reportTemplate2);
    }
}
