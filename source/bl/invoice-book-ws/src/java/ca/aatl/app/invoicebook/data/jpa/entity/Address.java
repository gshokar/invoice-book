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

import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import ca.aatl.app.invoicebook.data.jpa.entity.base.AddressEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */
@Entity
@Table(name = "address")
public class Address extends AddressEntity {

    @JoinColumn(name = "CountryId", referencedColumnName = "CountryId", nullable = false)
    @ManyToOne(optional = false)
    private Country country;
    @JoinColumn(name = "ProvinceId", referencedColumnName = "ProvinceId", nullable = true)
    @ManyToOne(optional = true)
    private Province province;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address")
//    private List<AgentAddress> agentAddresses;
//    
//    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "address")
//    private List<InstitutionAddress> institutionAddress;
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
//    @XmlTransient
//    public List<InstitutionAddress> getInstitutionAddress() {
//        return institutionAddress;
//    }
//
//    public void setInstitutionAddress(List<InstitutionAddress> institutionAddress) {
//        this.institutionAddress = institutionAddress;
//    }
    
    public String cityProvince(){
        String value = this.city;

        if(value != null && this.province != null){
            value = value + ", " + province.getCode();
        }
        return value;
    }
}

