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

import ca.aatl.app.invoicebook.data.jpa.entity.base.BusinessAddressEntity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="companyaddress")
public class CompanyAddress extends BusinessAddressEntity{
    
    @JoinColumn(name = "AddresseeId", referencedColumnName = "EntityId", nullable = false)
    @ManyToOne(optional = false)
    private Company company;
    
    @JoinColumn(name = "AddressTypeId", referencedColumnName = "TypeId", nullable = false)
    @ManyToOne(optional = false)
    private AddressType addressType;
    
    @JoinColumn(name = "AddressId", referencedColumnName = "AddressId", nullable = false)
    @ManyToOne(cascade=CascadeType.PERSIST, optional = false)
    private Address address;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
}
