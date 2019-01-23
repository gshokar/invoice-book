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

import ca.aatl.app.invoicebook.data.jpa.entity.base.TypeEntity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name = "contacttype")
public class ContactType extends TypeEntity{
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "contactType")
    private List<ClientContact> clientContacts;

    public List<ClientContact> getClientContacts() {
        return clientContacts;
    }

    public void setClientContacts(List<ClientContact> clientContacts) {
        this.clientContacts = clientContacts;
    }
    
}
