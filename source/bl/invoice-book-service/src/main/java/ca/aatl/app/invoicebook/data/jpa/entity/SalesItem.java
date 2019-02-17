/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-16  GShokar         Created
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="salesitem")
public class SalesItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesItemId", unique = true, nullable = false)
    private Integer id;
    
    @NotNull(message = "Please provide the name")
    @Size(min =1, max = 100)
    @Column(name = "Name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "SalesItemCode", nullable = false, length = 12)
    private String code;
    
    @Basic(optional = false)
    @Column(name = "Active", nullable = false)
    private boolean active;
    
    @Column(name = "TerminatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date terminatedDate;
    
    @Column(name = "Description", length = 200)
    private String description;
    
    @Basic(optional = false)
    @Column(name = "rate", nullable = false, precision = 19, scale = 2)
    private BigDecimal rate;
    
    @JoinColumn(name = "SalesItemTypeId", referencedColumnName = "TypeId", nullable = false)
    @ManyToOne(optional = false)
    private SalesItemType itemType;
    
    @JoinColumn(name = "ItemUnitId", referencedColumnName = "TypeId", nullable = false)
    @ManyToOne(optional = false)
    private ItemUnit unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTerminatedDate() {
        return terminatedDate;
    }

    public void setTerminatedDate(Date terminatedDate) {
        this.terminatedDate = terminatedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public SalesItemType getItemType() {
        return itemType;
    }

    public void setItemType(SalesItemType itemType) {
        this.itemType = itemType;
    }

    public ItemUnit getUnit() {
        return unit;
    }

    public void setUnit(ItemUnit unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final SalesItem other = (SalesItem) obj;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return Objects.equals(this.getGuid(), other.getGuid());
    }
    
}
