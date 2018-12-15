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

import ca.aatl.app.invoicebook.data.jpa.entity.base.BusinessEntity;
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
@Table(name="client")
public class Client extends BusinessEntity{
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "client")
    private List<ClientAddress> addresses;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "client")
    private List<ClientContact> contacts;

    public List<ClientAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<ClientAddress> addresses) {
        this.addresses = addresses;
    }

    public List<ClientContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<ClientContact> contacts) {
        this.contacts = contacts;
    }
    
    public ClientContact primaryContact() {
        ClientContact cc = null;
        
        if (contacts != null) {
            
            for(ClientContact c : contacts){
                if(c.getPrimary()){
                    cc = c;
                    break;
                }
            }
            
        }
        return cc;
    }

    public ClientAddress primaryAddress() {
        ClientAddress ca = null;
        if (addresses != null) {
            for(ClientAddress c : addresses){
                if(c.getPrimary()){
                    ca = c;
                    break;
                }
            }
        }
        return ca;
    }
    
    public boolean hasAddress(){
        ClientAddress ca = primaryAddress();
        return ca != null && ca.getAddress() != null;
    }
    
    public boolean hasContact(){
        ClientContact cc = primaryContact();
        return cc != null && cc.getContact() != null;
    }
}
