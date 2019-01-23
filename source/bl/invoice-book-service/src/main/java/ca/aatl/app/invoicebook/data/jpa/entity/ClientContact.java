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

import ca.aatl.app.invoicebook.data.jpa.entity.base.BusinessContactEntity;
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
@Table(name="clientcontact")
public class ClientContact extends BusinessContactEntity{
    
    @JoinColumn(name = "ContactEntityId", referencedColumnName = "EntityId", nullable = false)
    @ManyToOne(optional = false)
    private Client client;
    @JoinColumn(name = "ContactTypeId", referencedColumnName = "TypeId", nullable = false)
    @ManyToOne(optional = false)
    private ContactType contactType;
    @JoinColumn(name = "ContactId", referencedColumnName = "ContactId", nullable = false)
    @ManyToOne(cascade=CascadeType.PERSIST, optional = false)
    private Contact contact;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
}
