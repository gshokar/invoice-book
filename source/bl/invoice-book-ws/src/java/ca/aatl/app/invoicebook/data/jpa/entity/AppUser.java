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
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GShokar
 */
@Entity
@Table(name = "appuser")
public class AppUser extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AppUserId")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "UserName", nullable = false, length = 50)
    private String name;
    
    @Basic(optional = false)
    @Column(name = "LoginId", nullable = false, length = 50)
    private String loginId;
    
    @Basic(optional = false)
    @Column(name = "LoginPassword", nullable = false, length = 200)
    private String loginPassword;
    
    @Basic(optional = false)
    @Column(name = "Active", nullable = false)
    private boolean active;
    
    @Column(name = "LastDeactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDeactivatedDate;
    
    @Column(name = "Comments", length = 200)
    private String comments;
    
    @Basic(optional = false)
    @Column(name = "PasswordChangedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordChangedDate;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getLastDeactivatedDate() {
        return lastDeactivatedDate;
    }

    public void setLastDeactivatedDate(Date lastDeactivatedDate) {
        this.lastDeactivatedDate = lastDeactivatedDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getPasswordChangedDate() {
        return passwordChangedDate;
    }

    public void setPasswordChangedDate(Date passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final AppUser other = (AppUser) obj;
        
        if (!Objects.equals(this.id, other.id)) {
            return Objects.equals(this.guid, other.guid);
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "ca.aatl.app.invoicebook.bl.jpa.entities.AppUser[ id=" + id + " ]";
    }
    
}
