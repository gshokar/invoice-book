/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-28  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="timeentry")
public class TimeEntry extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TimeEntryId", nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "TimeEntryDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Basic(optional = false)
    @Column(name = "StartTime", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date startTime;
    
    @Basic(optional = false)
    @Column(name = "EndTime", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date endTime;
    
    @Basic(optional = false)
    @Column(name = "WorkedHours", nullable = false, precision = 19, scale = 2)
    private BigDecimal hours;
    
    @Basic(optional = false)
    @Column(name = "Approved", nullable = false)
    private boolean approved;
    
    @JoinColumn(name = "EmployeeId", referencedColumnName = "EntityId")
    @ManyToOne(optional = false)
    private Employee employee;
    
    @JoinColumn(name = "TimeCodeId", referencedColumnName = "TypeId")
    @ManyToOne(optional = false)
    private TimeCode timeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        calcHours();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        calcHours();
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public TimeCode getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(TimeCode timeCode) {
        this.timeCode = timeCode;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimeEntry)) {
            return false;
        }
        TimeEntry other = (TimeEntry) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return this.getGuid().equals(other.getGuid());
    }

    private void calcHours() {
        if(getStartTime() != null && getEndTime() != null){
            double diff = getEndTime().getTime() - getStartTime().getTime();
            double _hours = diff /1000 / 60 / 60;
            setHours(BigDecimal.valueOf(_hours).setScale(2, RoundingMode.HALF_UP));
        }else{
            setHours(BigDecimal.ZERO);
        }
    }
}
