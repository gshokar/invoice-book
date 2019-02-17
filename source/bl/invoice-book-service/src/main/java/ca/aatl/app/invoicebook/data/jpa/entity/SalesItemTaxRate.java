/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-17  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="salesitemtaxrate")
public class SalesItemTaxRate extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesItemTaxRateId", unique = true, nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "rate", nullable = false, precision = 19, scale = 2)
    private BigDecimal rate;
    
    @Basic(optional = false)
    @Column(name = "FromDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    
    @Column(name = "ToDate", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date toDate;
    
    @JoinColumn(name = "SalesItemId", referencedColumnName = "SalesItemId", nullable = false)
    @ManyToOne(optional = false)
    private SalesItem item;
    
    @JoinColumn(name = "SalesTaxId", referencedColumnName = "TypeId", nullable = false)
    @ManyToOne(optional = false)
    private SalesTax tax;
    
    @JoinColumn(name = "CountryId", referencedColumnName = "CountryId", nullable = true)
    @ManyToOne(optional = true)
    private Country country;
    
    @JoinColumn(name = "ProvinceId", referencedColumnName = "ProvinceId", nullable = true)
    @ManyToOne(optional = true)
    private Province province;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public SalesItem getItem() {
        return item;
    }

    public void setItem(SalesItem item) {
        this.item = item;
    }

    public SalesTax getTax() {
        return tax;
    }

    public void setTax(SalesTax tax) {
        this.tax = tax;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalesItemTaxRate other = (SalesItemTaxRate) obj;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return Objects.equals(this.getGuid(), other.getGuid());
    }
}
