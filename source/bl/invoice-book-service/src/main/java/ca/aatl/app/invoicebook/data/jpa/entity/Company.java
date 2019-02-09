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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="company")
public class Company extends BusinessEntity{
    
    @Column(name = "TaxRegNumber", nullable = true, length = 30)
    private String taxRegNumber;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "company")
    private List<CompanyAddress> addresses;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "company")
    private List<CompanyContact> contacts;

    public String getTaxRegNumber() {
        return taxRegNumber;
    }

    public void setTaxRegNumber(String taxRegNumber) {
        this.taxRegNumber = taxRegNumber;
    }

    public List<CompanyAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CompanyAddress> addresses) {
        this.addresses = addresses;
    }

    public List<CompanyContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<CompanyContact> contacts) {
        this.contacts = contacts;
    }
    
    public CompanyContact primaryContact() {
        CompanyContact cc = null;
        
        if (contacts != null) {
            
            for(CompanyContact c : contacts){
                if(c.getPrimary()){
                    cc = c;
                    break;
                }
            }
            
        }
        return cc;
    }

    public CompanyAddress primaryAddress() {
        CompanyAddress ca = null;
        if (addresses != null) {
            for(CompanyAddress c : addresses){
                if(c.getPrimary()){
                    ca = c;
                    break;
                }
            }
        }
        return ca;
    }
    
    public boolean hasAddress(){
        CompanyAddress ca = primaryAddress();
        return ca != null && ca.getAddress() != null;
    }
    
    public boolean hasContact(){
        CompanyContact cc = primaryContact();
        return cc != null && cc.getContact() != null;
    }
    
}
