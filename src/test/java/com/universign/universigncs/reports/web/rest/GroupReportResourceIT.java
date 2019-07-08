package com.universign.universigncs.reports.web.rest;

import com.universign.universigncs.reports.ReportsApp;
import com.universign.universigncs.reports.domain.GroupReport;
import com.universign.universigncs.reports.repository.GroupReportRepository;
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
 * Integration tests for the {@Link GroupReportResource} REST controller.
 */
@SpringBootTest(classes = ReportsApp.class)
public class GroupReportResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_UNIVERSIGN_ORGANIZATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIVERSIGN_ORGANIZATION_ID = "BBBBBBBBBB";

    @Autowired
    private GroupReportRepository groupReportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restGroupReportMockMvc;

    private GroupReport groupReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupReportResource groupReportResource = new GroupReportResource(groupReportRepository);
        this.restGroupReportMockMvc = MockMvcBuilders.standaloneSetup(groupReportResource)
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
    public static GroupReport createEntity() {
        GroupReport groupReport = new GroupReport()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .universignOrganizationId(DEFAULT_UNIVERSIGN_ORGANIZATION_ID);
        return groupReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupReport createUpdatedEntity() {
        GroupReport groupReport = new GroupReport()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .universignOrganizationId(UPDATED_UNIVERSIGN_ORGANIZATION_ID);
        return groupReport;
    }

    @BeforeEach
    public void initTest() {
        groupReportRepository.deleteAll();
        groupReport = createEntity();
    }

    @Test
    public void createGroupReport() throws Exception {
        int databaseSizeBeforeCreate = groupReportRepository.findAll().size();

        // Create the GroupReport
        restGroupReportMockMvc.perform(post("/api/group-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupReport)))
            .andExpect(status().isCreated());

        // Validate the GroupReport in the database
        List<GroupReport> groupReportList = groupReportRepository.findAll();
        assertThat(groupReportList).hasSize(databaseSizeBeforeCreate + 1);
        GroupReport testGroupReport = groupReportList.get(groupReportList.size() - 1);
        assertThat(testGroupReport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupReport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGroupReport.getUniversignOrganizationId()).isEqualTo(DEFAULT_UNIVERSIGN_ORGANIZATION_ID);
    }

    @Test
    public void createGroupReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupReportRepository.findAll().size();

        // Create the GroupReport with an existing ID
        groupReport.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupReportMockMvc.perform(post("/api/group-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupReport)))
            .andExpect(status().isBadRequest());

        // Validate the GroupReport in the database
        List<GroupReport> groupReportList = groupReportRepository.findAll();
        assertThat(groupReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllGroupReports() throws Exception {
        // Initialize the database
        groupReportRepository.save(groupReport);

        // Get all the groupReportList
        restGroupReportMockMvc.perform(get("/api/group-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupReport.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].universignOrganizationId").value(hasItem(DEFAULT_UNIVERSIGN_ORGANIZATION_ID.toString())));
    }
    
    @Test
    public void getGroupReport() throws Exception {
        // Initialize the database
        groupReportRepository.save(groupReport);

        // Get the groupReport
        restGroupReportMockMvc.perform(get("/api/group-reports/{id}", groupReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupReport.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.universignOrganizationId").value(DEFAULT_UNIVERSIGN_ORGANIZATION_ID.toString()));
    }

    @Test
    public void getNonExistingGroupReport() throws Exception {
        // Get the groupReport
        restGroupReportMockMvc.perform(get("/api/group-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGroupReport() throws Exception {
        // Initialize the database
        groupReportRepository.save(groupReport);

        int databaseSizeBeforeUpdate = groupReportRepository.findAll().size();

        // Update the groupReport
        GroupReport updatedGroupReport = groupReportRepository.findById(groupReport.getId()).get();
        updatedGroupReport
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .universignOrganizationId(UPDATED_UNIVERSIGN_ORGANIZATION_ID);

        restGroupReportMockMvc.perform(put("/api/group-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupReport)))
            .andExpect(status().isOk());

        // Validate the GroupReport in the database
        List<GroupReport> groupReportList = groupReportRepository.findAll();
        assertThat(groupReportList).hasSize(databaseSizeBeforeUpdate);
        GroupReport testGroupReport = groupReportList.get(groupReportList.size() - 1);
        assertThat(testGroupReport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupReport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGroupReport.getUniversignOrganizationId()).isEqualTo(UPDATED_UNIVERSIGN_ORGANIZATION_ID);
    }

    @Test
    public void updateNonExistingGroupReport() throws Exception {
        int databaseSizeBeforeUpdate = groupReportRepository.findAll().size();

        // Create the GroupReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupReportMockMvc.perform(put("/api/group-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupReport)))
            .andExpect(status().isBadRequest());

        // Validate the GroupReport in the database
        List<GroupReport> groupReportList = groupReportRepository.findAll();
        assertThat(groupReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteGroupReport() throws Exception {
        // Initialize the database
        groupReportRepository.save(groupReport);

        int databaseSizeBeforeDelete = groupReportRepository.findAll().size();

        // Delete the groupReport
        restGroupReportMockMvc.perform(delete("/api/group-reports/{id}", groupReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupReport> groupReportList = groupReportRepository.findAll();
        assertThat(groupReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupReport.class);
        GroupReport groupReport1 = new GroupReport();
        groupReport1.setId("id1");
        GroupReport groupReport2 = new GroupReport();
        groupReport2.setId(groupReport1.getId());
        assertThat(groupReport1).isEqualTo(groupReport2);
        groupReport2.setId("id2");
        assertThat(groupReport1).isNotEqualTo(groupReport2);
        groupReport1.setId(null);
        assertThat(groupReport1).isNotEqualTo(groupReport2);
    }
}
