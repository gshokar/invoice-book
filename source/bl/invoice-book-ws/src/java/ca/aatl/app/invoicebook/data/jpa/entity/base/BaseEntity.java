/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GShokar
 */

@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "AddedBy", nullable = false)
    protected int addedBy;
    @Basic(optional = false)
    @Column(name = "AddedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date addedDate;
    @Basic(optional = false)
    @Column(name = "LastUpdatedBy", nullable = false)
    protected int lastUpdatedBy;
    @Basic(optional = false)
    @Column(name = "LastUpdatedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastUpdatedDate;
    @Basic(optional = false)
    @Column(name = "GUID", nullable = false, length = 50)
    protected String guid;

    public int getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getGuid() {
        if (guid == null) {
            guid = java.util.UUID.randomUUID().toString().toUpperCase();
        }
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
