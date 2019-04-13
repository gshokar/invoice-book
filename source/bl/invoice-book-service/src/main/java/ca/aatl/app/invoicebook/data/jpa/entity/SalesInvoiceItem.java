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
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author GShokar
 */

@Entity
@Table(name="salesinvoiceitem")
public class SalesInvoiceItem extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesInvoiceItemId", unique = true, nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "LineNo", nullable = false)
    private Integer lineNumber;
    
    @Column(name = "Description", nullable = false, length = 100)
    private String description;
    
    @Basic(optional = false)
    @Column(name = "Quantity", nullable = false, precision = 19, scale = 2)
    private BigDecimal quantity;
    
    @Basic(optional = false)
    @Column(name = "Rate", nullable = false, precision = 19, scale = 2)
    private BigDecimal rate = BigDecimal.ZERO;
    
    @Basic(optional = false)
    @Column(name = "Amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;
    
    @Basic(optional = false)
    @Column(name = "TaxAmount", nullable = false, precision = 19, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;
    
    @Basic(optional = false)
    @Column(name = "TotalAmount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @JoinColumn(name = "SalesInvoiceId", referencedColumnName = "SalesInvoiceId", nullable = false)
    @ManyToOne(optional = false)
    private SalesInvoice invoice;
    
    @JoinColumn(name = "SalesItemId", referencedColumnName = "SalesItemId", nullable = false)
    @ManyToOne(optional = false)
    private SalesItem salesItem;
    
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "invoiceItem")
    private List<SalesInvoiceItemTax> taxes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SalesInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(SalesInvoice invoice) {
        this.invoice = invoice;
    }

    public SalesItem getSalesItem() {
        return salesItem;
    }

    public void setSalesItem(SalesItem salesItem) {
        this.salesItem = salesItem;
    }

    public List<SalesInvoiceItemTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<SalesInvoiceItemTax> taxes) {
        this.taxes = taxes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final SalesInvoiceItem other = (SalesInvoiceItem) obj;
        
        return Objects.equals(this.getGuid(), other.getGuid());
    }
}
