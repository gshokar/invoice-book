/*
 * Copyright (c) 2014 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2014-Aug-07  GShokar         Created
 * =============================================================================
 */

package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.TypeEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */
@Entity
@Table(name = "addresstype")
public class AddressType extends TypeEntity {

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressType")
//    private List<AgentAddress> agentAddresses;
//    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "addressType")
//    private List<InstitutionAddress> institutionAddresses;
//
//    @XmlTransient
//    public List<AgentAddress> getAgentAddresses() {
//        return agentAddresses;
//    }
//
//    public void setAgentAddresses(List<AgentAddress> agentAddresses) {
//        this.agentAddresses = agentAddresses;
//    }
//
//
//    @XmlTransient
//    public List<InstitutionAddress> getInstitutionAddresses() {
//        return institutionAddresses;
//    }
//
//    public void setInstitutionAddresses(List<InstitutionAddress> institutionAddresses) {
//        this.institutionAddresses = institutionAddresses;
//    }
    
}
