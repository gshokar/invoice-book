/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-25  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.PersonEntity;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="employee")
public class Employee extends PersonEntity{
    
    @Column(name = "EmployeeNumber", nullable = true, length = 12)
    private String number;
    
    @Basic(optional = false)
    @Column(name = "Active", nullable = false)
    protected boolean active;
    @Column(name = "TerminatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date terminatedDate;
    
    @JoinColumn(name = "AddressId", referencedColumnName = "AddressId", nullable = false)
    @ManyToOne(cascade=CascadeType.PERSIST, optional = false)
    private Address address;
    
    @JoinColumn(name = "ContactId", referencedColumnName = "ContactId", nullable = false)
    @ManyToOne(cascade=CascadeType.PERSIST, optional = false)
    private Contact contact;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTerminatedDate() {
        return terminatedDate;
    }

    public void setTerminatedDate(Date terminatedDate) {
        this.terminatedDate = terminatedDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
        
}
