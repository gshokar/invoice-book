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

import ca.aatl.app.invoicebook.data.jpa.entity.base.BusinessEntity;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="clientlocation")
public class ClientLocation extends BusinessEntity{
    
    @Basic(optional = false)
    @Column(name = "HeadOffice", nullable=false)
    private boolean headOffice;
    
    @JoinColumn(name = "AddressId", referencedColumnName = "AddressId")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Address address;
    
    @JoinColumn(name = "ContactId", referencedColumnName = "ContactId")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Contact contact;
    
    @JoinColumn(name = "ClientId", referencedColumnName = "EntityId")
    @ManyToOne(optional = false)
    private Client client;
        
    public boolean isHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(boolean headOffice) {
        this.headOffice = headOffice;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public String displayName(){
        
        String value = "";
        
        if(this.getName() != null){
            
           if(!this.getActive()){
               
               value = "X - ";
           }
           
           value = value + this.getName();
        }
        return value;
    }

    @Override
    public String toString() {
        return this.getActive() ? super.toString() : displayName();
    }
    
}
