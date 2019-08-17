/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Aug-11  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data.jpa.entity;

import ca.aatl.app.invoicebook.data.jpa.entity.base.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    
    @JoinColumn(name = "SalesItemId", referencedColumnName = "SalesItemId", nullable = false)
    @ManyToOne(optional = false)
    private SalesItem item;
    
    @JoinColumn(name = "SalesTaxRateId", referencedColumnName = "SalesTaxRateId", nullable = false)
    @ManyToOne(optional = false)
    private SalesTaxRate taxRate;
    
    
    public SalesItem getItem() {
        return item;
    }

    public void setItem(SalesItem item) {
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SalesTaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(SalesTaxRate taxRate) {
        this.taxRate = taxRate;
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
