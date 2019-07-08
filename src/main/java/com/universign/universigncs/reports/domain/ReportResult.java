package com.universign.universigncs.reports.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * A ReportResult.
 */
@Document(collection = "report_result")
public class ReportResult implements Serializable {

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

    public ReportResult groupReportId(String groupReportId) {
        this.groupReportId = groupReportId;
        return this;
    }

    public void setGroupReportId(String groupReportId) {
        this.groupReportId = groupReportId;
    }

    public String getReportId() {
        return reportId;
    }

    public ReportResult reportId(String reportId) {
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
        if (!(o instanceof ReportResult)) {
            return false;
        }
        return id != null && id.equals(((ReportResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReportResult{" +
            "id=" + getId() +
            ", groupReportId='" + getGroupReportId() + "'" +
            ", reportId='" + getReportId() + "'" +
            "}";
    }
}
