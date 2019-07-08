package com.universign.universigncs.reports.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * A GroupReport.
 */
@Document(collection = "group_report")
public class GroupReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("universign_organization_id")
    private String universignOrganizationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GroupReport name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public GroupReport description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniversignOrganizationId() {
        return universignOrganizationId;
    }

    public GroupReport universignOrganizationId(String universignOrganizationId) {
        this.universignOrganizationId = universignOrganizationId;
        return this;
    }

    public void setUniversignOrganizationId(String universignOrganizationId) {
        this.universignOrganizationId = universignOrganizationId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupReport)) {
            return false;
        }
        return id != null && id.equals(((GroupReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GroupReport{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", universignOrganizationId='" + getUniversignOrganizationId() + "'" +
            "}";
    }
}
