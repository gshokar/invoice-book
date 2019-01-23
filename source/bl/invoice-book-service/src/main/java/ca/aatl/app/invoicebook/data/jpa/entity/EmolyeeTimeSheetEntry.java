/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-26  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity;
import java.util.Date;
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
@Table(name="employeetimesheetentry")
public class EmolyeeTimeSheetEntry extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TimeSheetEntryId", unique = true, nullable = false)
    private Long id;

    @Column(name = "TimeSheetEntryDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @JoinColumn(name = "EmployeeId", referencedColumnName = "EntityId")
    @ManyToOne(optional = false)
    private Employee employee;
    
    @JoinColumn(name = "TimeCodeId", referencedColumnName = "EntityId")
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
}
