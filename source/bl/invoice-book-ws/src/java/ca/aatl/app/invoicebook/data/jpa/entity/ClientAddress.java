/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-04  GShokar         Created
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
@Table(name="clientaddress")
public class ClientAddress extends BusinessAddressEntity{
    
    @JoinColumn(name = "AddresseeId", referencedColumnName = "EntityId", nullable = false)
    @ManyToOne(optional = false)
    private Client client;
    
    @JoinColumn(name = "AddressTypeId", referencedColumnName = "TypeId", nullable = false)
    @ManyToOne(optional = false)
    private AddressType addressType;
    
    @JoinColumn(name = "AddressId", referencedColumnName = "AddressId", nullable = false)
    @ManyToOne(cascade=CascadeType.PERSIST, optional = false)
    private Address address;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
