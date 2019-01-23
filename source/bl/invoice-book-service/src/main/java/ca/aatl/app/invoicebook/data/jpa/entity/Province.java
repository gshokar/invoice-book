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

import ca.aatl.app.invoicebook.data.jpa.entity.base.ProvinceEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */
@Entity
@Table(name = "province")
public class Province extends ProvinceEntity{

    @JoinColumn(name = "CountryId", referencedColumnName = "CountryId", nullable = false)
    @ManyToOne(optional = false)
    private Country country;
    
    @OneToMany(mappedBy = "province")
    private List<Address> addresses;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
}
