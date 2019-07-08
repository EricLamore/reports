package com.universign.universigncs.reports.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * A ReportTemplate.
 */
@Document(collection = "report_template")
public class ReportTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("group_report_id")
    private String groupReportId;

    @Field("report_id")
    private String reportId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupReportId() {
        return groupReportId;
    }

    public ReportTemplate groupReportId(String groupReportId) {
        this.groupReportId = groupReportId;
        return this;
    }

    public void setGroupReportId(String groupReportId) {
        this.groupReportId = groupReportId;
    }

    public String getReportId() {
        return reportId;
    }

    public ReportTemplate reportId(String reportId) {
        this.reportId = reportId;
        return this;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportTemplate)) {
            return false;
        }
        return id != null && id.equals(((ReportTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReportTemplate{" +
            "id=" + getId() +
            ", groupReportId='" + getGroupReportId() + "'" +
            ", reportId='" + getReportId() + "'" +
            "}";
    }
}
